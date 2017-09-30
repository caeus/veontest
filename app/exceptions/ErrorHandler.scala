package exceptions

import com.google.inject.{Inject, Provider}
import play.api.http.DefaultHttpErrorHandler
import play.api.libs.json.{JsNull, Json}
import play.api.mvc.{RequestHeader, Result, Results}
import play.api.routing.Router
import play.api.{Configuration, Environment, OptionalSourceMapper}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

class ErrorHandler @Inject()(env: Environment,
                             config: Configuration,
                             sourceMapper: OptionalSourceMapper,
                             router: Provider[Router])(implicit executionContext: ExecutionContext)
  extends DefaultHttpErrorHandler(env, config, sourceMapper, router) {
  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {

    if (request.uri.startsWith("/v1/"))
      Future.fromTry(Try {
        Results.Status(statusCode)(Json.toJson(ErrorInfo(statusCode, message, JsNull)))
      }).recover {
        case e => Results.InternalServerError(Json.toJson(ErrorInfo(500, s"Internal Error!->\n${e.getMessage}", JsNull)))
      }
    else
      super.onClientError(request, statusCode, message)
  }

  override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    //exception.printStackTrace()
    if (request.uri.startsWith("/v1/"))
      Future.fromTry(Try {
        exception match {
          case ManagedException(e) => Results.Status(e.status)(Json.toJson(e))
          case e => throw e
        }
      }).recover {
        case e => e.printStackTrace()
          Results.InternalServerError(Json.toJson(ErrorInfo(500, s"Internal Error!->\n${e.getMessage}", JsNull)))
      }
    else {
      super.onServerError(request, exception)
    }
  }
}