package models

import play.api.Play.current
import java.util.Date
import com.novus.salat._
import com.novus.salat.annotations._
import com.novus.salat.dao._
import com.mongodb.casbah.Imports._
import se.radley.plugin.salat._
import mongoContext._
import twitter4j.Status
import application.Global._
import sindi.core._
import Services.Tick

case class User(
                 id: String = new ObjectId().toString,
                 username: String,
                 tweets: List[String],
                 strikes: Int
                 )


object User extends ModelCompanion[User, ObjectId] {
  val dao = new SalatDAO[User, ObjectId](collection = mongoCollection("users")) {}

  def findOneByUsername(username: String): Option[User] = dao.findOne(MongoDBObject("username" -> username))

  def findByStrikes = dao.find(ref = MongoDBObject("strikes" -> MongoDBObject("$gte" -> 2)))
    .sort(orderBy = MongoDBObject("strikes" -> -1))
    .limit(5)
    .toList.map(m=>m.username)

  def updateStrikes(user:String) = {
    val foundUser = findOneByUsername(user).get
    val x = foundUser.copy(strikes = (foundUser.strikes + 1))
    dao.save(x, wc = WriteConcern.Safe)
  }

  def saveTweet(tweet: Status) {
    val username: Option[User] = findOneByUsername(tweet.getUser.getScreenName)
    val userTweets: List[String] = username.map {
      m => m.tweets
    }.getOrElse(List.empty[String])
    val strikes = username.map(m => m.strikes).getOrElse(0)
    val user = User(username = tweet.getUser.getScreenName, tweets = (userTweets ::: List(tweet.getText)), strikes = strikes)
    User.insert(user)
    streamingProcessor.tweetActor ! Tick
  }

  def getTotalJailedUsers = dao.find(ref = MongoDBObject("strikes" -> MongoDBObject("$gte" -> 3))).size


}