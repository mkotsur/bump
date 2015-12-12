package io.github.mkotsur.bump

object TravelSpecBuilder {


  def buildSpecs(preference: TravelPreference): Stream[TravelSpec] = {
    val (minDuration, _) = preference.tripDuration
    if (preference.earliestStart.plusYears(minDuration.getYears).plusMonths(minDuration.getMonths).plusDays(minDuration.getDays)
      .isAfter(preference.latestStart)) {
      Stream.empty
    } else {
      val headSpec: TravelSpec = ???
      Stream.cons(headSpec, buildSpecs(preference))
    }
  }
}
