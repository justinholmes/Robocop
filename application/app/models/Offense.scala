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

case class Offenses(
                 id: String = new ObjectId().toString,
                 offense:String
                 )


object Offenses extends ModelCompanion[Offenses, ObjectId] {
  val dao = new SalatDAO[Offenses, ObjectId](collection = mongoCollection("offenses")) {}

  def getOffenses = dao.find(MongoDBObject()).toList

  def saveOffense(offense:String) = {

    val offensesModel = Offenses(offense = offense)
    dao.save(offensesModel)
  }


}