import sbt._
class Plugins(info: ProjectInfo) extends PluginDefinition(info)
{
  val twitterMaven = "twitter.com" at "http://maven.twttr.com/"
  val eclipse = "de.element34" % "sbt-eclipsify" % "0.5.4"
}
