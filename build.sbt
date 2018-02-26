
name := "cs372s18p1b"

version := "0.1"

scalaVersion := "2.12.4"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked")

libraryDependencies ++= Seq(
  "org.scalatest"     %% "scalatest"  % "3.0.5" % Test,
  "org.scalamock"     %% "scalamock"  % "4.0.0" % Test,
  "com.storm-enroute" %% "scalameter" % "0.9"   % Test
)

testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework")

logBuffered := false

parallelExecution in Test := false

enablePlugins(JavaAppPackaging)