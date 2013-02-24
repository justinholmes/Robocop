package Services

import twitter4j.Status
import play.libs.Akka
import akka.actor.{Actor, Props}
import application.Global._
import sindi.core._

trait StreamProcessor {
  val tweetActor = Akka.system.actorOf(Props[TweetActor], name = "counter")

  def processTweet(tweet: Status): Unit
}

class StreamProcessorImpl extends StreamProcessor {

  def processTweet(tweet: Status) {

    tweetActor ! SaveTweet(tweet)
    tweetActor ! ProcessTweet(tweet)

  }
}


class TweetActor extends Actor {
  var count = 0

  def receive = {
    case Tick => count = count + 1
    case Get => sender ! count
    case Processed => count = count - 1
    case SaveTweet(tweet) => storageService.saveTweet(tweet)
    case ProcessTweet(tweet) => tweetProcessor.process(tweet)
  }
}


case object Tick

case class SaveTweet(tweet:Status)

case class ProcessTweet(tweet:Status)


case object Get

case object Processed
