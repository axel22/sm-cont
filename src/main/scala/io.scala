

import scala.util.continuations._


object io {
  
  class Channel {
    var buffer: String = null
    
    def hasData = buffer != null
    
    def pull() = {
      val r = buffer
      buffer = null
      r
    }
    
    def push(more: String) {
      if (buffer == null) buffer = ""
      buffer = buffer + more
    }
  }
  
  trait Blocker {
    var continuation: String => Unit = (c: String) => reset {
      run()
    }
    var blockedOn: Channel = null
    
    def live = continuation != null
    
    def blocked = blockedOn != null
    
    def ready = !blocked || blockedOn.hasData
    
    def write(channel: Channel, s: String) = channel.push(s)
    
    def read(channel: Channel) = {
      val s = channel.pull()
      if (s != null) s
      else shift {
        (k: String => Unit) =>
        blockedOn = channel
        continuation = s => {
          continuation = null
          blockedOn = null
          k(s)
        }
      }
    }
    
    def start() = kernel.blockers ::= this
    
    def run(): Unit @suspendable
  }
  
  object kernel {
    var blockers: List[Blocker] = Nil  
  }
  
  def startKernel() {
    while (kernel.blockers.exists(b => b.live && b.ready)) {
      for (b <- kernel.blockers) {
        if (b.blocked) {
          if (b.ready) {
            val data = b.blockedOn.pull()
            b.continuation(data)
          }
        } else if (b.live) {
          b.continuation(null)
        }
      }
    }
  }
  
  def main(args: Array[String]) {
    val toServer = new Channel
    val toClient = new Channel
    
    val server = new Blocker {
      def process(request: String): Unit @suspendable = {
        println("server: got request: " + request)
        val numbers = request.split("\\+")
        val response = numbers(0).toInt + numbers(1).toInt
        write(toClient, response.toString)
      }
      
      def run(): Unit @suspendable = while (true) {
        process(read(toServer))
      }
    }
    server.start()
    
    val client = new Blocker {
      def run(): Unit @suspendable = {
        write(toServer, "3+2")
        val resp0 = read(toClient)
        println("client: got response: " + resp0)
        
        write(toServer, "4+5")
        val resp1 = read(toClient)
        println("client: got response: " + resp1)
        
        write(toServer, "9+1")
        val resp2 = read(toClient)
        println("client: got response: " + resp2)
      }
    }
    client.start()
    
    startKernel()
  }
  
}









