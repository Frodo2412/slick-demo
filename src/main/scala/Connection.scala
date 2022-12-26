package me.brunolemus.slick

import slick.jdbc.PostgresProfile.api._

object Connection {

  val db = Database.forConfig("postgres")

}
