package Services


import twitter4j.Status


import play.Logger

trait StorageService {

  def updateStrikes(user:String): Unit

  def getStrikeCount: Int

  def getTotalJailed: Int

  def addOffensiveWord(word: String): Unit

  def saveTweet(tweet: Status): Unit

}

class StorageServiceImpl extends StorageService {

  def updateStrikes(user:String) = models.User.updateStrikes(user)

  def getStrikeCount: Int = {
    1
  }

  def getTotalJailed: Int = models.User.getTotalJailedUsers

  def addOffensiveWord(word: String) = models.Offenses.saveOffense(word)

  def saveTweet(tweet: Status) {
    models.User.saveTweet(tweet)
  }
}
