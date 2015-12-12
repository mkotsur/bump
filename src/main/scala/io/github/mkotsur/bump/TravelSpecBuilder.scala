package io.github.mkotsur.bump

object TravelSpecBuilder {


  def buildSpecs(preference: TravelPreference): Stream[TravelSpec] = {

    Stream.cons(
    preference.earliestStart.

    for (dep <- preference.start;
         ret <-preference.`return`) yield TravelSpec()
  }

}
