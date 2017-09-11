package io.github.mkotsur.bump

import java.time.{LocalDate, DayOfWeek, Period}
import java.util.Date
import java.util.concurrent.Executors

import org.python.core.PySystemState
import org.python.util.PythonInterpreter
import scala.concurrent.duration._

import scala.concurrent.{ExecutionContext, Await, Future}
import scala.language.postfixOps

object JythonRunner extends App {

  def runScript(fileName: String, input: Map[String, Any]): Map[String, Any] = {
    val interpreter = new PythonInterpreter()
    input.foreach { case (k, v) => interpreter.set(k, v) }
    interpreter.execfile(getClass.getResourceAsStream(fileName))
    Map()
  }

  val departurePref = Seq((DayOfWeek.THURSDAY, Morning), (DayOfWeek.FRIDAY, Morning))
  val returnPref = Seq((DayOfWeek.SUNDAY, Morning), (DayOfWeek.MONDAY, Morning))
  val thisWeekThursday = LocalDate.of(2016, 12, 10)
  val nextWeekMonday = LocalDate.of(2016, 12, 31)

  val travelPref = TravelPreference(
    thisWeekThursday, nextWeekMonday,
    departurePref, returnPref,
    (Period.ofDays(2), Period.ofDays(4))
  )

  import AppConfig.implicits.scriptExecutionContext

  val responseFutures = TravelSpecBuilder.buildSpecs(travelPref).map { ts =>
    Future {
      println(s"Working with $ts")
      runScript("/script.py", Map(
        "dep_date" -> ts.departure._1,
        "back_date" -> ts.`return`._1,
        "destination_preference" -> DestinationPreference("AMS", "NRT", includeNearbyAirports = true)
      ))
    }
  }

  val allDoneFuture: Future[Seq[_]] = Future.sequence(responseFutures)

  Await.ready(allDoneFuture, 1 hour)

  println("DONE")
  System.exit(0)

}
