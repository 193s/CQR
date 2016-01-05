name := "cqr"

version := "1.0"

scalaVersion := "2.11.4"

// libraryDependencies += "org.scala-lang" % "jline" % "2.10.4"
libraryDependencies ++= Seq(
  ("org.scala-lang" % "jline" % "2.10.4").
    exclude("org.fusesource.jansi", "jansi")
)

libraryDependencies += "com.github.scopt" %% "scopt" % "3.3.0"

resolvers += Resolver.sonatypeRepo("public")

// sbt-assembly

mainClass in assembly := Some("cqr.Main")

assemblyOutputPath in assembly := file(s"./${name.value}/")

assemblyOption in assembly := (assemblyOption in assembly).value.copy(prependShellScript = Some(Seq("#!/usr/bin/env sh", """exec java -jar "$0" "$@"""" )))

assemblyJarName in assembly := s"${name.value}"


