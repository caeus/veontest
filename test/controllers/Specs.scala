package controllers

import daos.{MoviesDao, TestMoviesDao}
import engines.MoviesEngine
import middleware.ImdbApi
import models.{Movie, MovieSeed, Omit}
import org.scalatestplus.play._
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test._

/**
  * Add your spec here.
  * You can mock out a whole application including requests, plugins etc.
  *
  * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
  */
class Specs extends PlaySpec with Injecting {
  val app = new GuiceApplicationBuilder()
    .overrides(bind[MoviesDao].to[TestMoviesDao])
    .build()
  "App" should {

    "Interstella FTW" in {
      inject[ImdbApi].titleById("tt0816692").await mustEqual Some("Interstellar")
    }

    "Insert interstellar and get it by id" in {
      inject[MoviesEngine].create(MovieSeed(imdbId = "tt0816692",
        screenId = "screen_12345", availableSeats = 88)).await mustEqual Omit


    }

    "get interestellar by id" in {
      inject[MoviesEngine].get(imdbId = "tt0816692", screenId = "screen_12345").await.get mustEqual
        Movie(imdbId = "tt0816692",
          screenId = "screen_12345",
          title = "Interstellar",
          availableSeats = 88,
          reservedSeats = 0)

    }
    "reserve for interstellar" in {
      inject[MoviesEngine].reserve(imdbId = "tt0816692",
        screenId = "screen_12345").await
      inject[MoviesEngine].get(imdbId = "tt0816692", screenId = "screen_12345").await.get mustEqual
        Movie(imdbId = "tt0816692",
          screenId = "screen_12345",
          title = "Interstellar",
          availableSeats = 88,
          reservedSeats = 1)
    }
  }
}
