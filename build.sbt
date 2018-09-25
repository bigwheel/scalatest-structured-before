name := "scalatest-structured-before"
organization := "com.github.bigwheel"
version := "1.0"
scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  "org.scalactic" %% "scalactic" % "[3.0.5,)",
  "org.scalatest" %% "scalatest" % "[3.0.5,)",
  "org.scalaz" %% "scalaz-core" % "[7.2.26,)",
)

// about maven publish
publishMavenStyle := true
publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}
publishArtifact in Test := false
licenses := Seq("BSD-3-Clause" -> url("https://github.com/bigwheel/scalatest-structured-before/blob/master/LICENSE"))
homepage := Some(url("https://github.com/bigwheel/scalatest-structured-before"))
pomExtra := (
  <scm>
    <url>git@github.com:bigwheel/scalatest-structured-before.git</url>
    <connection>scm:git:git@github.com:bigwheel/scalatest-structured-before.git</connection>
  </scm>
    <developers>
      <developer>
        <id>bigwheel</id>
        <name>k.bigwheel</name>
        <url>https://github.com/bigwheel</url>
      </developer>
    </developers>
  )
