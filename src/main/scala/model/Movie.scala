package me.brunolemus.slick
package model

import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape

import java.time.LocalDate

case class Movie(id: Long, name: String, releaseDate: LocalDate, lengthInMin: Int)


class MovieTable(tag: Tag) extends Table[Movie](tag, Some("movies"), "Movie") {

  def id = column[Long]("movie_id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def releaseDate = column[LocalDate]("release_date")
  def lengthInMin = column[Int]("length_in_min")

  override def * : ProvenShape[Movie] = (id, name, releaseDate, lengthInMin) <> (Movie.tupled, Movie.unapply)

}

