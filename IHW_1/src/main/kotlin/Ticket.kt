import kotlinx.serialization.Serializable
@Serializable
class Ticket(val customer: Customer, val sessionId: Int, val uniqueID: Int, val seatNumber: Int)