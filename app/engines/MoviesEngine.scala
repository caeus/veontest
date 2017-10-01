package engines

import com.google.inject.{Inject, Singleton}
import daos.MoviesDao
import exceptions.ResourceNotFoundException
import middleware.ImdbApi
import models.{Movie, MovieSeed, Omit}
import util.Idioms._

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by caeus on 30/09/17.
  */
@Singleton
class MoviesEngine @Inject()(dao: MoviesDao,
                             imdbApi: ImdbApi)(implicit executionContext: ExecutionContext) {
  def reserve(imdbId: String, screenId: String): Future[Omit] = {
    dao.reserve(imdbId = imdbId,screenId = screenId)
  }


  def create(seed: MovieSeed) = {
    imdbApi.titleById(seed.imdbId)
      .otherwise(ResourceNotFoundException(s"There's no movie with id ${seed.imdbId}"))
      .flatMap {
        title =>
          import seed._
          dao.create(Movie(
            imdbId = imdbId: String,
            screenId = screenId: String,
            movieTitle = title: String,
            availableSeats = availableSeats: Int,
            reservedSeats = 0: Int
          ))
      }
  }


  def get(imdbId: String, screenId: String): Future[Option[Movie]] = {

    dao.get(imdbId,screenId)

  }


}
