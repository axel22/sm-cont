

import scala.util.continuations._



object coroutines {
  
  /* coroutine API */
  
  trait Coroutine {
    var continuation = () => reset {
      body()
    }
    
    def apply(): Unit = {
      continuation()
      /* if you want to make a coroutine spin forever... look below:
      continuation = () => reset {
        body()
      }
      apply()
      */
    }
    
    def body(): Unit @suspendable
  }
  
  def yieldTo(from: Coroutine, to: Coroutine): Unit @suspendable = shift {
    (k: Unit => Unit) =>
    from.continuation = () => k()
    to.apply()
  }
  
  def coroutine(thunk: =>Unit @suspendable): Coroutine = new Coroutine {
    def body() = thunk
  }
  
  /* ----------------------------------------------------------------------- */
  
  /* our example */
  
  var stack: List[Int] = Nil
  
  val producer: Coroutine = coroutine {
    var i = 0
    while (i < 10) {
      println("push")
      stack ::= i
      yieldTo(producer, consumer)
      i += 1
    }
  }
  
  val consumer: Coroutine = coroutine {
    while (true) {
      while (stack.nonEmpty) {
        println("popping " + stack.head)
        stack = stack.tail
      }
      yieldTo(consumer, producer)
    }
  }
  
  def coroutineEnvironment(block: =>Unit @suspendable) = reset {
    block
  }
  
  def main(args: Array[String]) {
    coroutineEnvironment {
      producer()
    }
  }
  
}
