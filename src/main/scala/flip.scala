

import scala.util.continuations._


object flip {
  
  def main(args: Array[String]) {
    var continuation: () => Unit = null
    def pause() = shift { (k: Unit => Unit) =>
      continuation = () => k()
    }
    
    reset {
      println("Code before pause call")
      pause()
      println("Code after pause call")
    }
    
    println("We're out of the reset block and we will now call the continuation")
    continuation()
  }
  
}
