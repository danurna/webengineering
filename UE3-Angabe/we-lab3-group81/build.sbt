name := "we-lab3-group81"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaCore,
  javaJpa,
  "org.hibernate" % "hibernate-entitymanager" % "4.3.1.Final",
  "com.google.code.gson" % "gson" % "2.2"
)     

templatesImport += "scale.collection._"
templatesImport += "ac.at.tuwien.big.we14.lab2.api_"

play.Project.playJavaSettings
