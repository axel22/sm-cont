

import scala.util.continuations._



object types {
  
  def main(args: Array[String]) {
    val b = reset {
      shift { (k: List[Int] => Int) =>
        k(List(1, 2, 3)) > 2
      }.size
    }
    println(b)
  }
  
}
