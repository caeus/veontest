package sql.queries

import com.google.inject.{Inject, Singleton}
import slick.lifted.ProvenShape
import sql.drivers.ExtendedPGProfile.api._

@Singleton
class MoviesQuery @Inject()() extends TableQuery(new MovieSchema(_))

/**
  * Created by caeus on 24/06/17.
  */
case class MovieRow(
                     imdbId: String,
                     screenId: String,
                     title: String,
                     availableSeats: Int,
                     reservedSeats: Int
                   )


class MovieSchema(tag: Tag) extends Table[MovieRow](tag, "movie") {

  def imdbId = column[String]("imdb_id")

  def screenId = column[String]("screen_id")

  def title = column[String]("title")

  def availableSeats = column[Int]("available_seats")

  def reservedSeats = column[Int]("reserved_seats")


  def id = index("imdb_screen_uq", imdbId -> screenId, unique = true)

  override def * : ProvenShape[MovieRow] = (imdbId,
    screenId,
    title,
    availableSeats,
    reservedSeats) <> (MovieRow.tupled, MovieRow.unapply)
}
