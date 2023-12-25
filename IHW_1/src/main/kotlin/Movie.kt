import java.time.Duration
import kotlinx.serialization.Serializable
@Serializable
class Movie(var title: String, var description: String, var duration: Int)