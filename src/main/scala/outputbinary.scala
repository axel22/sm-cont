

import scala.util.continuations._



object outputbinary {
  
  def flip: Int @suspendable = shift {
    (k: Int => Unit) =>
    k(0)
    k(1)
  }
  
  def output(wdt: Int, lst: List[Int]): Unit @suspendable = {
    if (wdt == 0) println(lst.reverse.mkString)
    else output(wdt - 1, flip :: lst)
  }
  
  def main(args: Array[String]) {
    reset {
      output(5, Nil)
    }
  }
  
  def output2(wdt: Int, lst: List[Int]): Unit @suspendable = {
    if (wdt == 0) {
      println(lst.reverse.mkString): Unit
    } else {
      {
        val rest: List[Int] = (shift {
          (k: Int => Unit) =>
          k(0)
          k(1)
        }: Int @suspendable) :: lst
        // above is: ::(lst)(shift { ... }) : List[Int] comp(epsilon * @cpsParam[Unit, Unit] * epsilon) == List[Int] @suspendable
        
        // below is: output2(wdt - 1)(rest) : Unit comp(epsilon * @cpsParam[Unit, Unit] * @cpsParam[Unit, Unit])
        output2(wdt - 1, rest)
      }: Unit @suspendable
    }
  }
  
}
