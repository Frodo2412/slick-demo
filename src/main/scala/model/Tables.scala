package me.brunolemus.slick
package model

import slick.jdbc.PostgresProfile.api._

object Tables {

  lazy val movies = TableQuery[MovieTable]

}
