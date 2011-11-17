

import scala.util.continuations._



object generators {
  
  class Generator(block: Generator => Unit @suspendable) extends Iterator[Int] {
    var continuation: () => Unit = null
    var current: Int = _
    
    reset {
      block(this)
    }
    
    def hasNext: Boolean = continuation != null
    def next(): Int = {
      val res = current
      continuation()
      res
    }
  }
  
  def generate(block: Generator => Unit @suspendable) = new Generator(block)
  
  def `yield`(g: Generator, x: Int) = {
    g.current = x
    shift {
      (k: Unit => Unit) =>
      g.continuation = () => {
        g.continuation = null
        k()
      }
    }
  }
  
  def main(args: Array[String]) {
    val it: Generator = generate {
      g =>
      var i = 0
      while (i < 10) {
        `yield`(g, i)
        i += 1
      }
    }
    
    while (it.hasNext) println(it.next())
  }
  
  /* HOMEWORK: write a primes generator */
  
  /* HOMEWORK: write a binary tree generator */
  
  /* HOMEWORK: write a generator which is generic in the type of values it yields */
  
  /* HOMEWORK: See what SAM types are and find the relevant Scala mailing list discussions. It's been proposed they could simplify the STM APIs. Think about how they could simplify this exercise. Which other exercise(s) here could be simplified using SAM types and how? */
  
}
