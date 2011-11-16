

import scala.util.continuations._


object fork {
  
  def fork(): Int @cpsParam[Unit, Unit] = shift {
    (k: Int => Unit) =>
    new Thread {
      override def run() {
        k(0)
      }
    }.start()
    k(1)
  }
  
  def main(args: Array[String]) {
    println("Outside reset.")
    
    reset {
      println("Inside reset, before fork.")
      if (fork() != 0) println("Inside reset on the original thread: " + Thread.currentThread.getName)
      else println("Inside reset on the forked thread: " + Thread.currentThread.getName)
    }
  }
  
}
