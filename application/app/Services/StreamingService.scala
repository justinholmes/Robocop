package Services

import play.api.Logger
import akka.actor._
import akka.pattern.ask
import akka.util.Timeout
import twitter4j._
import sindi.core._
import application.Global._
import controllers.Streamer

trait StreamingService {
  def start: Unit

  def stop: Unit

}

class StreamingServiceImpl extends StreamingService {

  val twitterStream = new TwitterStreamFactory(Util.config).getInstance

  var running:Boolean = false

  def start {
    if(running == false){
    Logger.info("Starting Steaming")

    twitterStream.addListener(Util.simpleStatusListener)
    twitterStream.sample
    running = true
    } else{
      Logger.warn("Streaming already started")
    }
  }

  def stop {
    Logger.info("Stopping Steaming")

    twitterStream.cleanUp
    twitterStream.shutdown
    running = false
  }
}





//  time { run }
// println("Count is " + Await.result(count, timeout.duration).asInstanceOf[Int])
// system.shutdown()

//
//  def time[A](f: => A) = {
//    val s = System.nanoTime
//    val ret = f
//    println("time: "+(System.nanoTime-s)/1e6+"ms")
//    ret
//  }


object Util {
  val config = new twitter4j.conf.ConfigurationBuilder()
    .setOAuthConsumerKey("")
    .setOAuthConsumerSecret("")
    .setOAuthAccessToken("")
    .setOAuthAccessTokenSecret("")
    .build

  def simpleStatusListener = new StatusListener() {
    def onStatus(status: Status) {
      streamingProcessor.processTweet(status)
    }

    def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) {}

    def onTrackLimitationNotice(numberOfLimitedStatuses: Int) {}

    def onException(ex: Exception) {
      println(ex.getMessage)
      streamingService.stop
      Streamer.streamingState = "Start Streaming"
    }

    def onScrubGeo(arg0: Long, arg1: Long) {}

    def onStallWarning(warning: StallWarning) {}
  }
}

