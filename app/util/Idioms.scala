package util

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by caeus on 30/09/17.
  */
object Idioms {

  implicit class FutureOptionOps[T](val m: Future[Option[T]]) extends AnyVal {
    def otherwise(exc: => Throwable)(implicit executionContext: ExecutionContext) = {
      m.flatMap {
        case Some(t) => Future.successful(t)
        case None => Future.failed(exc)
      }
    }
  }

}
