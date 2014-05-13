name := "scaladin-todomvc"

scalaVersion := "2.10.3"

libraryDependencies ++= Seq(
  "com.vaadin" % "vaadin-server" % "7.1.15",
  "com.vaadin" % "vaadin-client-compiled" % "7.1.15",
  "com.vaadin" % "vaadin-themes" % "7.1.15",
  "com.vaadin" % "vaadin-push" % "7.1.15",
  "org.eclipse.jetty.aggregate" % "jetty-all" % "8.1.15.v20140411",
  "org.eclipse.jetty" % "jetty-webapp" % "8.1.15.v20140411" % "container",
  "org.eclipse.jetty" % "jetty-plus" % "8.1.15.v20140411" % "container",
  "org.eclipse.jetty" % "jetty-annotations" % "8.1.15.v20140411" % "container",
  "com.typesafe.slick" %% "slick" % "2.0.2",
  "com.h2database" % "h2" % "1.3.170"
)

seq(webSettings :_*)
