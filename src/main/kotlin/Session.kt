import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

@Serializable
class Session(private var movie: Movie, var start: LocalDateTime, var end: LocalDateTime, var seats: Array<Boolean>) {
    fun getAvailableSeats(): String {
        val stringBuilder = StringBuilder()
        for (i in seats.indices) {
            if (i == seats.size - 1) {
                break
            }
            if (!seats[i]) {
                stringBuilder.append("${i + 1}, ")
            }
        }
        stringBuilder.append("${seats.size}")
        return stringBuilder.toString()
    }

    fun reserveSeat(seat: Int?): Boolean {
        if (seat == null) {
            return false
        }
        if (seat < 1 || seat > seats.size || seats[seat - 1]) {
            return false
        }
        seats[seat - 1] = true
        return true
    }

    fun freeSeat(seat: Int?): Boolean {
        if (seat == null) {
            return false
        }
        if (seat < 1 || seat > seats.size || !seats[seat - 1]) {
            return false
        }
        seats[seat - 1] = false
        return true
    }

    fun isFull(): Boolean {
        return seats.all { it }
    }

    private fun durationInMinutes(): Int {
        return (end.toInstant(TimeZone.currentSystemDefault()) - start.toInstant(TimeZone.currentSystemDefault())).inWholeMinutes.toInt()
    }

    fun changeDuration(start: LocalDateTime, end: LocalDateTime): Boolean {
        val tempStart = this.start
        val tempEnd = this.end
        this.start = start
        this.end = end
        if (durationInMinutes() < movie.duration) {
            this.start = tempStart
            this.end = tempEnd
            return false
        }
        return true
    }
}
