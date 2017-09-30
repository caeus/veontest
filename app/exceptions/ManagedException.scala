package exceptions

import play.api.libs.json.{JsValue, Json}

/**
  * Created by caeus on 25/06/17.
  * Self type annotation, so that any kind of exception we create can be a ManagedException
  */
trait ManagedException {
  self: Throwable =>
  val httpStatus: Int
  val message: String
  val details: JsValue
}

case class ErrorInfo(status: Int, message: String, details: JsValue)

object ErrorInfo {
  implicit def writes = Json.writes[ErrorInfo]
}

/**
  * Extractors, I like them!
  */
object ManagedException {
  def unapply(me: ManagedException): Option[ErrorInfo] = Some(ErrorInfo(me.httpStatus, me.message, me.details))
}



