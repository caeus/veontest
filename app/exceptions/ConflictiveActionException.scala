package exceptions

import play.api.libs.json.{JsNull, JsValue}

/**
  * Created by caeus on 25/06/17.
  */
case class ConflictiveActionException(message: String) extends Exception with ManagedException {
  override val httpStatus: Int = 409
  override val details: JsValue = JsNull
}
