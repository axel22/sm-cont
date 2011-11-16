

import scala.util.continuations._


object exit {
  
  def exit(): Unit @cpsParam[Unit, Unit] = shift {
    (k: Unit => Unit) =>
  }
  
  def main(args: Array[String]) {
    println("Outside reset.")
    
    reset {
      println("Inside reset, before exit.")
      exit()
      println("Inside reset, after exit.")
    }
  }
  
}
