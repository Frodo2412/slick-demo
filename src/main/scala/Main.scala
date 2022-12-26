package me.brunolemus.slick

import model.{Movie, Tables}

import java.time.LocalDate
import java.util.concurrent.{ExecutorService, Executors}
import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}

object MainExecutionContext {
  val executor: ExecutorService = Executors.newFixedThreadPool(4)
  implicit val executionContext: ExecutionContextExecutor = ExecutionContext.fromExecutor(executor)
}


object Main {

  import slick.jdbc.PostgresProfile.api._
  import MainExecutionContext._


  private val shawshankRedemption = Movie(1L, "Shawshank's Redemption", LocalDate.of(1994, 9, 23), 162)
  private val theMatrix = Movie(2L, "The Matrix", LocalDate.of(1999, 3, 31), 134)

  def demoInsertMovie(): Unit = {
    val queryDescription = Tables.movies += theMatrix
    val futureId: Future[Int] = Connection.db.run(queryDescription)
    futureId.onComplete {
      case Success(newId) => println(s"Success: movie inserted with id $newId")
      case Failure(exception) => println(s"Failure: ${exception.getMessage}")
    }
  }

  def readAllMovies(): Unit = {
    val resultFuture: Future[Seq[Movie]] = Connection.db.run(Tables.movies.result)
    resultFuture onComplete {
      case Success(movies) => s"Success: ${movies.mkString(",")}"
      case Failure(ex) => s"Failure: ${ex.getMessage}"
    }
  }

  def readMatrix(): Unit = {
    val resultFuture = Connection.db.run(Tables.movies.filter(_.name like "%Matrix%").result)
    resultFuture onComplete {
      case Success(movies) => s"Success: ${movies.mkString(",")}"
      case Failure(ex) => s"Failure: ${ex.getMessage}"
    }
    Thread.sleep(10000)
  }

  def main(args: Array[String]): Unit = {
    readMatrix()
  }
}