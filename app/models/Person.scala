package models

import play.api.libs.json._
import play.api.libs.functional.syntax._

import org.joda.time.DateTime
import services.NeoService
import scala.concurrent.Await
import scala.concurrent.duration._

case class Country(name: String)
case class Person(tmdbId: Long, name: String, born: DateTime, country: Country) extends TmdbIdentifiable {
	def neoService = new NeoService()

	def save = {
		Await.result(neoService.createPerson(this), 10.seconds)
	}
}

object Person {
	implicit val countryWrites = Json.writes[Country]
	implicit val countryReads = Json.reads[Country]
	implicit val personWrites = Json.writes[Person]
	implicit val personReads = Json.reads[Person]
}