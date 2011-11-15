

import scala.util.continuations._


object basic {
  
  def example1 {
    val r = reset {
      shift { (k: Int => Int) =>
        k(7)
      } + 1
    }
    println(r)
  }
  
  def example2 {
    val r = reset {
      shift { (k: Int => Int) =>
        k(7) + k(7)
      } + 1
    }
    println(r)
  }
  
  def example3 {
    val r = reset {
      shift { (k: Int => Int) =>
        k(k(k(7)))
      } + 1
    } * 2
    println(r)
  }
  
  def example4 {
    val r = reset {
      val x = 5
      shift { (k: Int => Int) =>
        k(k(7))
      } + x
    }
    println(r)
  }
  
  def example5 {
    val r = reset {
      shift { (k: Int => Int) =>
        k(k(7)) * 2
      } + 1
    }
    println(r)
  }
  
  def example6 {
    val r = reset {
      shift { (k: Int => Int) =>
        k(7) + 1
        k(7)
      } * 2
    }
    println(r)
  }
  
  def main(args: Array[String]) {
    example1
    example2
    example3
    example4
    example5
    example6
  }
  
}




