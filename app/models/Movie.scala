package models

import play.api.libs.json.Json

/**
  * Created by caeus on 30/09/17.
  */
case class MovieSeed(imdbId: String,
                     screenId: String,
                     availableSeats: Int)
object MovieSeed{
  implicit def reads = Json.reads[MovieSeed]
}
case class Movie(
                  imdbId: String,
                  screenId: String,
                  title: String,
                  reservedSeats: Int,
                  availableSeats: Int
                )

object Movie{
  implicit def writer = Json.writes[Movie]
}