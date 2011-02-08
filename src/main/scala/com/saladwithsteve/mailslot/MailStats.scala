/** Copyright 2009 Steve Jenson under the Apache 2.0 License */
package com.saladwithsteve.mailslot

import com.twitter.stats.Stats.Counter
import com.twitter.stats.Stats.Timing
import java.util.concurrent.atomic.AtomicInteger

object MailStats {
  val bytesWritten = new Counter
  val totalSessions = new AtomicInteger(0)
  val closedSessions = new Counter
  val sessionErrors = new Counter
  val mailRouterLatency = new Timing
}
