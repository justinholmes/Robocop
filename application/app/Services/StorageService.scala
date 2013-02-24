package Services


import twitter4j.Status


import play.Logger

trait StorageService {

  def saveOffensiveTweet: Unit

  def getStrikeCount: Int

  def getTotalJailed: Int

  def addOffensiveWord(word: String): Unit

  def saveTweet(tweet: Status): Unit

}

class StorageServiceImpl extends StorageService {

  def saveOffensiveTweet {}

  def getStrikeCount: Int = {
    1
  }

  def getTotalJailed: Int = {
    1
  }

  def addOffensiveWord(word: String) {}

  def saveTweet(tweet: Status) {
    models.User.saveTweet(tweet)
  }
}
