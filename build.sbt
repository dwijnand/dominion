import pre.SbtKit._

organization := "net.mox9.dominion"
        name := "dominion"
     version := "0.1-SNAPSHOT"

      scalaVersion := "2.11.6"
crossScalaVersions := Seq(scalaVersion.value)

scalacOptions         ++= Seq("-encoding", "utf8")
scalacOptions         ++= Seq("-deprecation", "-feature", "-unchecked", "-Xlint")
scalacOptions          += "-language:postfixOps"
scalacOptions          += "-Xfatal-warnings"
scalacOptions          += "-Xfuture"
scalacOptions          += "-Yinline-warnings"
scalacOptions          += "-Yno-adapted-args"
scalacOptions in Test  += "-Yrangepos"
scalacOptions          += "-Ywarn-dead-code"
scalacOptions          += "-Ywarn-numeric-widen"
scalacOptions         ++= "-Ywarn-unused-import".ifScala211Plus.value.toSeq
scalacOptions          += "-Ywarn-value-discard"

maxErrors := 5
triggeredMessage := Watched.clearWhenTriggered

   wartremoverWarnings += Wart.Any
   wartremoverWarnings += Wart.Any2StringAdd
   wartremoverWarnings += Wart.AsInstanceOf
   wartremoverWarnings += Wart.EitherProjectionPartial
   wartremoverWarnings += Wart.IsInstanceOf
   wartremoverWarnings += Wart.ListOps
   wartremoverWarnings += Wart.JavaConversions
   wartremoverWarnings += Wart.MutableDataStructures
   wartremoverWarnings += Wart.NonUnitStatements
   wartremoverWarnings += Wart.Null
   wartremoverWarnings += Wart.OptionPartial
// wartremoverWarnings += Wart.Product                 // Makes Seq[ case objects a bitch
   wartremoverWarnings += Wart.Return
// wartremoverWarnings += Wart.Serializable            // Makes Seq[ case objects a bitch
   wartremoverWarnings += Wart.TryPartial
   wartremoverWarnings += Wart.Var

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

libraryDependencies += "org.specs2" %% "specs2-core" % "3.6" % "test"

initialCommands in console := "import net.mox9.dominion._"

watchSources ++= (baseDirectory.value * "*.sbt").get
watchSources ++= (baseDirectory.value / "project" * "*.scala").get
