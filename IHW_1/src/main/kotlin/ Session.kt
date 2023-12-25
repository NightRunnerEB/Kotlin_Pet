import java.time.LocalDateTime
import kotlinx.serialization.Serializable
@Serializable
class Session(var movie: Movie, var start: LocalDateTime, var end: LocalDateTime, var seats: Array<Boolean>) {
}