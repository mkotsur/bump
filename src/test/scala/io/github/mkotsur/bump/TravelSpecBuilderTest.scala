package io.github.mkotsur.bump

import java.time.{LocalDate, Period, DayOfWeek}

import org.scalatest.{Matchers, Spec, FunSuite}

class TravelSpecBuilderTest extends Spec with Matchers {

  object `Travel spec builder` {

    def `should generate specs for a weekend`() = {

      val pref1 = Seq((DayOfWeek.THURSDAY, Afternoon), (DayOfWeek.FRIDAY, Morning))
      val pref2 = Seq((DayOfWeek.SUNDAY, Afternoon), (DayOfWeek.MONDAY, Morning))

      val thisWeekThursday = new LocalDate(2015, 12, 10)
      val nextWeekMonday = new LocalDate(2015, 12, 14)

      val travelPref = TravelPreference(
        thisWeekThursday, nextWeekMonday,
        pref1, pref2,
        (Period.ofDays(2), Period.ofDays(4))
      )

      val specs = TravelSpecBuilder.buildSpecs(travelPref).toList

      specs.size shouldBe 4

    }

  }

}
