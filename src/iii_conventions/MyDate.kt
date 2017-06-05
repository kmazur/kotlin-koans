package iii_conventions

import java.util.*

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {

    operator fun plus(interval: TimeInterval): MyDate {
        return this.addTimeIntervals(interval, 1)
    }

    operator fun plus(interval: RepeatedTimeInterval): MyDate {
        return this.addTimeIntervals(interval.interval, interval.times)
    }

    override fun compareTo(other: MyDate): Int {
        val yearComp = year.compareTo(other.year)
        if(yearComp != 0) {
            return yearComp
        }
        val monthComp = month.compareTo(other.month)
        if(monthComp != 0) {
            return monthComp
        }
        val dayComp = dayOfMonth.compareTo(other.dayOfMonth)
        if(dayComp != 0) {
            return dayComp
        }
        return 0
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange {
    return DateRange(this, other)
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

operator fun TimeInterval.times(n: Int): RepeatedTimeInterval {
    return RepeatedTimeInterval(this, n)
}

data class RepeatedTimeInterval(val interval: TimeInterval, val times: Int) {

}

class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> {

        var current: Optional<MyDate> = Optional.empty<MyDate>()
        return object : Iterator<MyDate> {

            override fun next(): MyDate {
                current = Optional.of<MyDate>(current.map { it.nextDay() }.orElse(start))
                return current.orElse(start)
            }

            override fun hasNext(): Boolean {
                return current.orElse(start) < endInclusive
            }

        };
    }
}
