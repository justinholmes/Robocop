package Services

import twitter4j.Status
import play.api.Logger

trait TweetProcessor {
  def process(tweet: Status): Unit
}

class TweetProcessorImpl extends TweetProcessor {
  def process(tweet: Status) {
    val text = tweet.getText
    val user:String = tweet.getUser.getScreenName
    models.Offenses.getOffenses.foreach {
      f=>
        if (text.contains(f.offense))
        {
          //Logger.info("Success: "+user +" offense: "+f.offense)
          models.User.updateStrikes(user)
        }
    }
  }

  protected def processor(string:String) = {

  }
}