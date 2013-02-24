package controllers

import play.api.mvc._

object Offenses extends Controller {

  import play.api.data._
  import play.api.data.Forms._

  val offenseForm = Form(
    single(
      "offense" -> nonEmptyText)
  )

  def index = Action {
    val list = models.Offenses.getOffenses
    Ok(views.html.offense(list, offenseForm))
  }

  def submit = Action {
    implicit request =>
      offenseForm.bindFromRequest.fold(
        errors => Redirect("/offenses?error"),
        success => {
          val (text) = offenseForm.bindFromRequest.get
          models.Offenses.saveOffense(text)
          Redirect("/offenses")
        })


  }
}
