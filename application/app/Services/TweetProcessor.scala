package Services

import twitter4j.Status
import play.api.Logger
import sindi.core._
import application.Global._
trait TweetProcessor {
  def process(tweet: Status): Unit
}

class TweetProcessorImpl extends TweetProcessor {
  def process(tweet: Status) {
    val text = tweet.getText
    val user:String = tweet.getUser.getScreenName
    models.Offenses.getOffenses.par.foreach {
      f=>
        if (text.toLowerCase.contains(f.offense.toLowerCase))
        {
          //Logger.info("Success: "+user +" offense: "+f.offense)
          storageService.updateStrikes(user)
        }
    }
  }

  protected def processor(string:String) = {

  }
}