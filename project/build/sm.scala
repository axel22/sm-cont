


import sbt._



class SmContProject(info: ProjectInfo) extends DefaultProject(info) with AutoCompilerPlugins {
  
  val cont = compilerPlugin("org.scala-lang.plugins" % "continuations" % "2.9.0")
  override def compileOptions = super.compileOptions ++ compileOptions("-P:continuations:enable")
  
}
