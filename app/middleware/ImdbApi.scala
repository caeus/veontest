package middleware

import java.net.URLEncoder

import com.google.inject.{Inject, Singleton}
import org.jsoup.Jsoup
import play.api.libs.ws.WSClient

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

/**
  * Created by caeus on 30/09/17.
  */
@Singleton
class ImdbApi @Inject()(ws: WSClient)(implicit executionContext: ExecutionContext) {


  def titleById(id: String): Future[Option[String]] = {
    ws.url(s"http://www.imdb.com/title/${URLEncoder.encode(id, "utf-8")}/")
      .get()
      .flatMap {
        case wr if 200 until 300 contains wr.status =>
          Future fromTry Try{
            Some(Jsoup.parse(wr.body).select("div#star-rating-widget").attr("data-title"))
          }

        case wr if wr.status == 404 =>
          Future.successful(None)
        case _ => Future.failed(new Exception("Whateer fix this"))
      }
  }
}
