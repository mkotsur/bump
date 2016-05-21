package io.github.mkotsur

import java.time.LocalDate

package object bump {


  import java.time.{DayOfWeek, LocalTime, Period}

  type DepartPref = (DayOfWeek, DayPart)
  type TravelEventTimeSpec = (LocalDate, DayPart)

  type DurationPref = (Period, Period)

  case class DestinationPreference(originAirportCode: String,
                                   destinationAirportCode: String,
                                   includeNearbyAirports: Boolean = false)

  case class TravelPreference(earliest: LocalDate,
                              latest: LocalDate,
                              start: Seq[DepartPref],
                              `return`: Seq[DepartPref],
                              tripDuration: DurationPref)

  case class TravelSpec(departure: TravelEventTimeSpec, `return`: TravelEventTimeSpec)

  sealed trait DayPart {

    def from: LocalTime

    def till: LocalTime
  }

  object WholeDay extends DayPart {

    def from: LocalTime = LocalTime.MIN

    def till: LocalTime = LocalTime.MAX

  }

  object Morning extends DayPart {

    override def toString: String = "MORNING"

    def from: LocalTime = LocalTime.MIN

    def till: LocalTime = LocalTime.NOON
  }

  object Afternoon extends DayPart {

    override def toString: String = "AFTERNOON"

    def from: LocalTime = LocalTime.NOON

    def till: LocalTime = LocalTime.MAX
  }

  implicit class LocalDateWithAddition(date: LocalDate) {
    def plusPeriod(duration: Period): LocalDate = {
      date.plusYears(duration.getYears).plusMonths(duration.getMonths).plusDays(duration.getDays)
    }
  }

  implicit class LocalDateWithRange(start: LocalDate) {

    def `to`(end: LocalDate):Stream[LocalDate] = {
      if (start.isAfter(end)) {
        Stream.empty
      } else {
        Stream.cons(start, start.plusDays(1) to end)}
    }

  }

  implicit class StreamOfDatesExtended(stream: Stream[LocalDate]) {
    def toDayParts(preference: Seq[(DayOfWeek, DayPart)]): Stream[TravelEventTimeSpec] = streamRangeOfDateParts filter {
      case (date, partOfDay) => preference.flatMap {
          case (d, `WholeDay`) => Seq((d, Morning), (d, Afternoon))
          case p => Seq(p)
        } contains((date.getDayOfWeek, partOfDay))
    }

    private def streamRangeOfDateParts: Stream[TravelEventTimeSpec] = {
      stream.flatMap { case date =>
        Seq((date, Morning), (date, Afternoon))
      }
    }
  }

}
