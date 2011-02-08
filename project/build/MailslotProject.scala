import sbt._
import de.element34.sbteclipsify._

class MailslotProject(info: ProjectInfo) extends DefaultProject(info) with Eclipsify
{
  //val netty = "org.jboss.netty" % "netty" % "3.2.3.Final"
  //val ostrich = "com.twitter" % "ostrich" % "3.0.4"
  val twitterRepo = "Twitter Maven 2 Repo" at "http://maven.twttr.com/"

  // scala actors library with fork-join replaced by java 5 util.concurrent:
  // FIXME: we should investigate akka actors.
  val twitter_actors = "com.twitter" % "twitteractors_2.8.0" % "2.0.1"
  val mina = "org.apache.mina" % "mina-core" % "2.0.2"
  val naggati = "net.lag" % "naggati_2.8.0" % "0.7.4"
  val configgy = "net.lag" % "configgy" % "2.0.2"
  //val stats = "com.twitter" % "scala-stats_2.8.1" % "0.0.1-SNAPSHOT"
  val mail = "javax.mail" % "mail" % "1.4"

  // for tests:
  //val specs = "org.scala-tools.testing" %% "specs" % "1.6.6" % "test"
  //val jmock = "org.jmock" % "jmock" % "2.4.0" % "test"
  //val hamcrest_all = "org.hamcrest" % "hamcrest-all" % "1.1" % "test"
  //val cglib = "cglib" % "cglib" % "2.1_3" % "test"
  //val asm = "asm" % "asm" % "1.5.3" % "test"
  //val objenesis = "org.objenesis" % "objenesis" % "1.1" % "test"
}
