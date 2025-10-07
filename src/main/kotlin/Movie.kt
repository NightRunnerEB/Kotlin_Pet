import kotlinx.serialization.Serializable

@Serializable
class Movie(private var title: String, var description: String, var duration: Int)
