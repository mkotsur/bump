package io.github.mkotsur.bump

import java.time.{LocalDate, Period, DayOfWeek}

import org.scalatest.{Matchers, Spec}

class TravelSpecBuilderTest extends Spec with Matchers {

  object `Travel spec builder` {

    val departurePref = Seq((DayOfWeek.THURSDAY, Afternoon), (DayOfWeek.FRIDAY, Morning))
    val returnPref = Seq((DayOfWeek.SUNDAY, Afternoon), (DayOfWeek.MONDAY, Morning))
    val thisWeekThursday = LocalDate.of(2015, 12, 10)
    val nextWeekMonday = LocalDate.of(2015, 12, 14)

    def `should return empty stream if it is not possible to generate long enough date range`() = {
        val travelPref = TravelPreference(thisWeekThursday, nextWeekMonday,
          departurePref, returnPref, (Period.ofMonths(6), Period.ofMonths(2)))

      TravelSpecBuilder.buildSpecs(travelPref) shouldBe Stream.empty
    }

    def `should generate specs for a weekend`() = {
      val travelPref = TravelPreference(
        thisWeekThursday, nextWeekMonday,
        departurePref, returnPref,
        (Period.ofDays(2), Period.ofDays(4))
      )

      val specs = TravelSpecBuilder.buildSpecs(travelPref).toList

      specs.size shouldBe 4

    }

  }

}
