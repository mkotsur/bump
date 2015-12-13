package io.github.mkotsur.bump

import java.time.temporal.ChronoUnit
import java.time.{DayOfWeek, LocalDate, Period}

import org.scalatest.{Matchers, Spec}

class TravelSpecBuilderTest extends Spec with Matchers {

  object `Travel spec builder` {

    val departurePref = Seq((DayOfWeek.THURSDAY, Afternoon), (DayOfWeek.FRIDAY, Morning))
    val returnPref = Seq((DayOfWeek.SUNDAY, Afternoon), (DayOfWeek.MONDAY, Morning))
    val thisWeekThursday = LocalDate.of(2015, 12, 10)
    val nextWeekMonday = LocalDate.of(2015, 12, 14)

    def `should return empty stream if it is not possible to generate long enough date range`() = {
      val travelPref = TravelPreference(thisWeekThursday, nextWeekMonday,
        departurePref, returnPref, (Period.ofMonths(2), Period.ofMonths(6)))

      TravelSpecBuilder.buildSpecs(travelPref) shouldBe Stream.empty
    }

    def `should generate specs for a weekend`() = {
      val travelPref = TravelPreference(
        thisWeekThursday, nextWeekMonday,
        departurePref, returnPref,
        (Period.ofDays(2), Period.ofDays(4))
      )

      val specs = TravelSpecBuilder.buildSpecs(travelPref).toList

      val thu = thisWeekThursday
      val fri = thu.plus(1, ChronoUnit.DAYS)
      val sun = thu.plus(3, ChronoUnit.DAYS)
      val mon = thu.plus(4, ChronoUnit.DAYS)

      specs should contain(TravelSpec((thu, Afternoon), (sun, Afternoon)))
      specs should contain(TravelSpec((thu, Afternoon), (mon, Morning)))
      specs should contain(TravelSpec((fri, Morning), (sun, Afternoon)))
      specs should contain(TravelSpec((fri, Morning), (mon, Morning)))

      specs.size shouldBe 4
    }

  }

}
