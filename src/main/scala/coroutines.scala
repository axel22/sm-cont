

import scala.util.continuations._



object coroutines {
  
  trait Coroutine {
    var continuation = () => reset {
      apply()
    }
    
    def apply(): Unit @suspendable
  }
  
  /* method used for coroutine declaration */
  def coroutine(body: () => Unit @suspendable) = new Coroutine {
    def apply() = body()
  }
  
  def main(args: Array[String]) {
    
  }
  
}
