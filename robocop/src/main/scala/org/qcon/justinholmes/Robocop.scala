package org.qcon.justinholmes

import akka.actor._
import akka.pattern.ask
import akka.util.duration._
import akka.util.Timeout
import akka.dispatch.Await

case object Tick
case object Get
case object Processed

class Counter extends Actor {
  var count = 0

  def receive = {
    case Tick => count += 1
    case Get  => sender ! count
    case Processed => count = count - 1
  }
}

object Robocop extends App {
  val system = ActorSystem("Robocop")
  var countInt = 0
  val counter = system.actorOf(Props[Counter])
    implicit val timeout = Timeout(500 seconds)

  val count = {(counter ? Get) onSuccess {
    case count => count

  }
  }

  def run = {
  while(countInt < 1000000){
    counter ! Tick
     //println("Count is " + Await.result(count, timeout.duration).asInstanceOf[Int])
    //counter ! Processed
  countInt = countInt + 1
  }

 

  
  

  }

  time { run }
  println("Count is " + Await.result(count, timeout.duration).asInstanceOf[Int])
  system.shutdown()


def time[A](f: => A) = {
  val s = System.nanoTime
  val ret = f
  println("time: "+(System.nanoTime-s)/1e6+"ms")
  ret
}

}
