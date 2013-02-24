package Services

import twitter4j.Status
import play.libs.Akka
import akka.actor.{Actor, Props}

trait StreamProcessor {
  val counter = Akka.system.actorOf(Props[Counter], name = "counter")

  def processTweet(tweet: Status): Unit
}

class StreamProcessorImpl extends StreamProcessor {

  def processTweet(tweet: Status) {
    counter ! Tick
  }
}


class Counter extends Actor {
  var count = 0

  def receive = {
    case Tick => count = count + 1
    case Get => sender ! count
    case Processed => count = count - 1
  }
}


case object Tick

case object Get

case object Processed
