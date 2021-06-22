package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> = allDrivers - trips.map { it.driver }

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
    allPassengers
        .filter { passenger ->
            trips.count { passenger in it.passengers } >= minTrips
        }
        .toSet()

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
    allPassengers
        .filter { passenger ->
            trips.count { it.driver == driver && passenger in it.passengers } > 1
        }
        .toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
    allPassengers
        .filter { passenger ->
            val (withoutDiscount, withDiscount) =
                trips
                    .filter { passenger in it.passengers }
                    .partition { it.discount == null }
            return@filter withoutDiscount.count() < withDiscount.count()
        }
        .toSet()

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there are no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    return trips
        .groupBy {
            val start = it.duration / 10 * 10
            val end = start + 9
            start..end
        }
        .maxByOrNull { (_, trips) -> trips.size }
        ?.key
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    if (trips.isEmpty()) return false

    val totalIncome = trips.sumOf(Trip::cost)
    val sortedIncome = trips
        .groupBy(Trip::driver)
        .map { (_, trips) -> trips.sumOf(Trip::cost) }
        .sortedDescending()
    val numberOfTopDrivers = (0.2 * allDrivers.size).toInt()
    val incomeByTop = sortedIncome
        .take(numberOfTopDrivers)
        .sum()

    return incomeByTop >= 0.8 * totalIncome
}