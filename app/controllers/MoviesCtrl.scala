package controllers

import com.google.inject.{Inject, Singleton}
import engines.MoviesEngine
import exceptions.ResourceNotFoundException
import models.{Movie, MovieSeed}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents, Request}
import util.Idioms._

import scala.concurrent.ExecutionContext

/**
  * Created by caeus on 30/09/17.
  */
@Singleton
class MoviesCtrl @Inject()(moviesEngine: MoviesEngine,
                           cc: ControllerComponents)
                          (implicit executionContext: ExecutionContext) extends AbstractController(cc) {

  def create = Action.async(parse.json[MovieSeed]) {
    implicit req: Request[MovieSeed] =>
      moviesEngine.create(req.body).map { _ =>
        Ok
          .withHeaders("location" -> controllers.routes.MoviesCtrl.get(req.body.imdbId, req.body.screenId).absoluteURL())
      }

  }

  def get(imdbId: String, screenId: String) = Action.async {
    moviesEngine.get(imdbId = imdbId, screenId = screenId)
      .otherwise(ResourceNotFoundException(s"There's no movie with id $imdbId:$screenId"))
      .map {
        (movie: Movie) =>
          Ok(Json.toJson(movie))
      }
  }

  def reserve(imdbId: String, screenId: String) = Action.async {
    req =>
      moviesEngine.reserve(imdbId, screenId)
        .map {
          _ =>
            Ok
        }

  }

}
