package daos

import com.google.inject.{ImplementedBy, Inject, Singleton}
import exceptions.{ConflictiveActionException, ResourceNotFoundException}
import models.{Movie, Omit}
import org.postgresql.util.PSQLException
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import sql.drivers.ExtendedPGProfile.api._
import sql.queries.{MovieRow, MoviesQuery}

import scala.collection.mutable
import scala.concurrent.{ExecutionContext, Future}
import scala.util.control.NonFatal

/**
  * Created by caeus on 30/09/17.
  */
@ImplementedBy(classOf[DefaultMoviesDao])
trait MoviesDao {
  def create(movie: Movie): Future[Omit]

  def get(imdbId: String, screenId: String): Future[Option[Movie]]

  def reserve(imdbId: String, screenId: String): Future[Omit]
}

@Singleton
class DefaultMoviesDao @Inject()(query: MoviesQuery,
                                 val dbConfigProvider: DatabaseConfigProvider)
                                (implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] with MoviesDao {



  def get(imdbId: String, screenId: String): Future[Option[Movie]] = {
    db.run(query.filter {
      schema =>
        schema.imdbId === imdbId && schema.screenId === screenId
    }
      .result.headOption)
      .map {
        maybeRow =>
          maybeRow.map {
            row: MovieRow =>
              Movie(
                imdbId = row.imdbId: String,
                screenId = row.screenId: String,
                title = row.title: String,
                reservedSeats = row.reservedSeats: Int,
                availableSeats = row.availableSeats: Int
              )
          }

      }
  }


  def create(obj: Movie): Future[Omit] = {
    import obj._
    db.run(query += MovieRow(imdbId: String,
      screenId: String,
      title: String,
      availableSeats: Int,
      reservedSeats: Int)).map(_ => Omit)
      .recoverWith{
        //code for duplications
        case e: PSQLException if e.getSQLState=="23505"=>
          Future.failed(ConflictiveActionException(s"There's already a movie with id ${obj.imdbId}:${obj.screenId}"))
      }
  }

  override def reserve(imdbId: String, screenId: String): Future[Omit] = {
    val movieAction = query.filter(t => t.imdbId === imdbId && t.screenId === screenId)
      .result.headOption.flatMap {
      case Some(row) if row.availableSeats <= row.reservedSeats =>
        DBIO.failed(ConflictiveActionException("Movie already full"))
      case Some(row) =>
        DBIO.successful(row)
      case None =>
        DBIO.failed(ResourceNotFoundException(s"There's no movie with id $imdbId:$screenId"))
      case _ => DBIO.failed(ConflictiveActionException("Movie is completely full"))
    }

    def updateSeatsAction(seats: Int) = {
      query.filter(t => t.imdbId === imdbId && t.screenId === screenId)
        .map(_.reservedSeats).update(seats)
    }

    db.run((for {
      movie <- movieAction
      _ <- updateSeatsAction(movie.reservedSeats + 1)
    } yield Omit).transactionally)

  }
}

@Singleton
class TestMoviesDao extends MoviesDao {


  private val movies: mutable.Map[(String, String), Movie] = mutable.Map.empty

  def reserve(imdbId: String, screenId: String): Future[Omit] = {
    movies.get(imdbId -> screenId) match {
      case Some(movie) if movie.reservedSeats >= movie.availableSeats =>
        Future.failed(ConflictiveActionException("Movie already full"))
      case Some(movie) =>
        movies.put(imdbId -> screenId, movie.copy(reservedSeats = movie.reservedSeats + 1))
        Future.successful(Omit)
      case None =>
        Future.failed(ResourceNotFoundException(s"There's no movie with id $imdbId:$screenId"))
    }
  }

  override def create(movie: Movie): Future[Omit] = {
    val key = movie.imdbId -> movie.screenId
    if (!(movies contains key)) {

      movies.put(key, movie)
      Future.successful(Omit)
    } else {
      Future.failed(ConflictiveActionException(s"There's already a movie with id ${movie.imdbId}:${movie.screenId}"))
    }

  }

  override def get(imdbId: String, screenId: String): Future[Option[Movie]] = Future.successful {
    println(movies)
    movies.get(imdbId -> screenId)
  }
}