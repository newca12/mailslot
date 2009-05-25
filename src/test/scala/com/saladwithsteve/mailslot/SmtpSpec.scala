/** Copyright 2009 Steve Jenson, released under Apache 2.0 License */
package com.saladwithsteve.mailslot.smtp

import net.lag.extensions._
import net.lag.naggati._
import org.apache.mina.core.buffer.IoBuffer
import org.apache.mina.core.filterchain.IoFilter
import org.apache.mina.core.session.{DummySession, IoSession}
import org.apache.mina.filter.codec._
import org.specs._

object SmtpCodecSpec extends Specification {

  private var fakeSession = new DummySession

  private val fakeDecoderOutput = new ProtocolDecoderOutput {
    override def flush(nextFilter: IoFilter.NextFilter, s: IoSession) = {}
    override def write(obj: AnyRef) = {
      written = obj :: written
    }
  }

  private var written: List[AnyRef] = Nil

  private var decoder = smtp.Codec.decoder

  "smtp" should {
    doBefore {
      fakeSession = new DummySession
      decoder = smtp.Codec.decoder
      written = Nil
    }

    "HELO" >> {
      "throw an exception with a bare HELO" >> {
        decoder.decode(fakeSession, IoBuffer.wrap("HELO\r\n".getBytes), fakeDecoderOutput) must throwA[ProtocolError]
      }

      "accept a two-argument HELO" >> {
        decoder.decode(fakeSession, IoBuffer.wrap("HELO localhost\r\n".getBytes), fakeDecoderOutput)
        written mustEqual List(Request(List("HELO", "localhost"), None))
      }
    }

    "MAIL" >> {
      "Bare MAIL causes an error" >> {
          decoder.decode(fakeSession, IoBuffer.wrap("MAIL\r\n".getBytes), fakeDecoderOutput) must throwA[ProtocolError]
      }

      "MAIL FROM" >> {
        "MAIL FROM errors without a email address" >> {
          decoder.decode(fakeSession, IoBuffer.wrap("MAIL FROM:\r\n".getBytes), fakeDecoderOutput) must throwA[ProtocolError]
        }

        "MAIL FROM works with an email address" >> {
          decoder.decode(fakeSession, IoBuffer.wrap("MAIL FROM: stevej@pobox.com\r\n".getBytes), fakeDecoderOutput)
          written mustEqual List(Request(List("MAIL", "FROM:", "stevej@pobox.com"), None))
        }
      }
    }

    "RCPT" >> {
      "RCPT TO:" >> {
        "RCPT TO: errors without an email address" >> {
          decoder.decode(fakeSession, IoBuffer.wrap("RCPT TO:\r\n".getBytes), fakeDecoderOutput) must throwA[ProtocolError]
        }

        "RCPT TO: works with an email address" >> {
          decoder.decode(fakeSession, IoBuffer.wrap("RCPT TO: stevej@pobox.com\r\n".getBytes), fakeDecoderOutput)
          written mustEqual List(Request(List("RCPT", "TO:", "stevej@pobox.com"), None))
        }
      }
    }

    "DATA" >> {
       "DATA requires a body" >> {
         decoder.decode(fakeSession, IoBuffer.wrap("DATA\r\n".getBytes), fakeDecoderOutput) must throwA[ProtocolError]
       }

      "DATA accepts a single-line body" >> {
        decoder.decode(fakeSession, IoBuffer.wrap("DATA\r\n\r\n this is an email\r\n.\r\n".getBytes), fakeDecoderOutput)
        written(0) match {
          case Request(commands, Some(data)) => {
            commands mustEqual List("DATA")
            new String(data) mustEqual "\r\n this is an email\r\n.\r\n"
          }
          case _ => fail
        }
      }
    }

    "HELP responds" >> {
      decoder.decode(fakeSession, IoBuffer.wrap("HELP\r\n".getBytes), fakeDecoderOutput)
      written mustEqual List(Request(List("HELP"), None))
    }

    "VRFY responds" >> {
      decoder.decode(fakeSession, IoBuffer.wrap("VRFY <stevej@pobox.com>\r\n".getBytes), fakeDecoderOutput)
      written mustEqual List(Request(List("VRFY", "<stevej@pobox.com>"), None))
    }
  }
}
