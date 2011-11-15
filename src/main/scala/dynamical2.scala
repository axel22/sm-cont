

import scala.util.continuations._


object dynamical2 {
  
  def foo(): Int @cpsParam[Int, Int] = shift {
    (k: Int => Int) => k(k(7))
  } + 1
  
  def bar(): Int @cpsParam[Int, Int] = foo() * 2
  
  def baz(): Int = reset(bar())
  
  def main(args: Array[String]) {
    println(baz())
  }
  
}
