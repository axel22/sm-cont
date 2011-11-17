

import scala.util.continuations._



object dynamical {
  
  def foo() = shift {
    (k: Int => Int) => k(k(7))
  } + 1
  
  def bar() = foo() * 2
  
  def baz() = reset(bar())
  
  def main(args: Array[String]) {
    println(baz())
  }
  
}
