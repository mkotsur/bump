package io.github.mkotsur

import java.time.LocalDate

package object bump {


  import java.time.{DayOfWeek, LocalTime, Period}

  type DepartPref = (DayOfWeek, DayPart)
  type TravelEventTimeSpec = (LocalDate, DayPart)

  type DurationPref = (Period, Period)

  case class TravelPreference(earliestStart: LocalDate,
                              latestStart: LocalDate,
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

    def from: LocalTime = LocalTime.MIN

    def till: LocalTime = LocalTime.NOON
  }

  object Afternoon extends DayPart {

    def from: LocalTime = LocalTime.NOON

    def till: LocalTime = LocalTime.MAX
  }


}
