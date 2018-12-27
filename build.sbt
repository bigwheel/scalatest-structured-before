name := "scalatest-structured-before"
organization := "com.github.bigwheel"
version := "1.0.1"
scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  "org.scalactic" %% "scalactic" % "3.0.5",
  "org.scalatest" %% "scalatest" % "3.0.5",
  "org.scalaz" %% "scalaz-core" % "7.2.26",
)

// about maven publish
publishMavenStyle := true
publishTo := sonatypePublishTo.value
publishArtifact in Test := false

import xerial.sbt.Sonatype._
licenses := Seq("BSD-3-Clause" -> url("https://github.com/bigwheel/scalatest-structured-before/blob/master/LICENSE"))
sonatypeProjectHosting := Some(GitHubHosting("bigwheel", name.value, "k.bigwheel", "k.bigwheel+eng@gmail.com"))
