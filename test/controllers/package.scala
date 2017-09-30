import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

/**
  * Created by caeus on 30/09/17.
  */
package object controllers {

  implicit class BlockingFutureOps[T](val value: Future[T]) extends AnyVal {
    def await: T = {
      Await.result(value, 20.seconds)
    }
  }

}
