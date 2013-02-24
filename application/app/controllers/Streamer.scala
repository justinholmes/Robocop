package controllers

import play.api._

import libs.json.Json
import play.api.mvc._
import sindi.core._
import Services.{Get, StreamingService}
import application.Global._
import concurrent.Future
import akka.util.Timeout
import models.User

object Streamer extends Controller {

  var streamingState = "Start Streaming"

  def index = Action {
    Ok(views.html.streamer(streamingState))
  }

  def streamer = Action {
    implicit request =>
      val input = request.body.asFormUrlEncoded.getOrElse(Map()).get("s").head.head
      input match {
        case "Start Streaming" => {
          streamingService.start
          streamingState = "Stop Streaming"
          Ok("Stop Streaming")
        }
        case "Stop Streaming" => {
          streamingService.stop
          streamingState = "Start Streaming"
          Ok("Start Streaming")
        }
      }

  }
  import akka.pattern.{ ask, pipe }
  import scala.concurrent.duration._
  import play.api.libs.concurrent.Execution.Implicits._
  implicit val timeout = Timeout(100 seconds)
  def streamingStats = Action {

  val number =       ask(streamingProcessor.tweetActor, Get).mapTo[Int]

    Async{
      number.map(i=> Ok(""+i))
    }
  }

  def strikes = Action{
    Ok(""+ models.User.findByStrikes.mkString("<br>"))
  }

  def getTotalJailed = Action {
    Ok(""+models.User.getTotalJailedUsers)
  }


}
