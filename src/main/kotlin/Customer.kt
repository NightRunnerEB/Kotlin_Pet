import kotlinx.serialization.Serializable

@Serializable
data class Customer(val name: String, val card: Long)