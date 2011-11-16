

import scala.util.continuations._


object reactors {
  
  trait Reactor {
    var continue = () => reset { body() }
    
    def stop(): Unit @suspendable = shift {
      (k: Unit => Unit) =>
      continue = () => k()
    }
    
    def body(): Unit @suspendable
  }
  
  def main(args: Array[String]) {
    val r = new Reactor {
      def body() = {
        println("1")
        stop()
        println("2")
        stop()
        println("3")
      }
    }
    
    r.continue()
    r.continue()
  }
  
}









