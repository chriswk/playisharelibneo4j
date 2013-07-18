package models

import play.api.libs.json._
import play.api.libs.functional.syntax._

import org.joda.time.DateTime
import services.NeoService
import scala.concurrent.Await
import scala.concurrent.duration._

case class Movie(tmdbId: Long, title: String, releaseDate: DateTime)


object Movie {
  implicit val movieReads = Json.reads[Movie]
  implicit val movieWrites = Json.writes[Movie]
  val neoService = new NeoService()

  def create(movie: Movie) = {
    Await.result(neoService.createNode(Json.toJson(movie)), 10.seconds)
  }
}

