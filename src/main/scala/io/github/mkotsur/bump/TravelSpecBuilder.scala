package io.github.mkotsur.bump

object TravelSpecBuilder {


  def buildSpecs(preference: TravelPreference): Stream[TravelSpec] = {
    val (minDuration, _) = preference.tripDuration
    if (preference.earliest.plusPeriod(minDuration).isAfter(preference.latest)) {
      Stream.empty
    } else {
      (preference.earliest to preference.latest).toDayParts(preference.start).flatMap { case(tripStartDate, tripStartPOD) =>

        val (shortestDuration, longestDuration) = preference.tripDuration

        (tripStartDate.plusPeriod(shortestDuration) to tripStartDate.plusPeriod(longestDuration)).toDayParts(preference.`return`)
          .filterNot { case (tripEndDate, _) => tripEndDate.isAfter(preference.latest)}
          .map (TravelSpec((tripStartDate, tripStartPOD), _ ))
      }
    }
  }



}
