



object noncps {
  
  def bar(x: Int, y: Boolean) {
    println(x, y)
  }
  
  def foo(x: Int) {
    bar(x, true)
    bar(x, false)
  }
  
  def main(args: Array[String]) {
    foo(5)
    println("done!")
  }
  
}


object cps {
  
  def bar(x: Int, y: Boolean, cont: () => Unit) {
    println(x, y)
    cont()
  }
  
  def foo(x: Int, cont: () => Unit) {
    bar(x, true, () => {
      bar(x, false, () => {
        cont()
      })
    })
  }
  
  def main(args: Array[String]) {
    foo(5, () => {
      println("done!")
    })
  }
  
}

