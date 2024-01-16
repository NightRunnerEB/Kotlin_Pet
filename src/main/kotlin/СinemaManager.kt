import kotlinx.datetime.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class CinemaManager( val cinemaHall: CinemaHall, val movies: MutableList<Movie>, val sessions: MutableList<Session>, var tickets: MutableList<Ticket>) {


    private var uniqueID: Int = 1

    fun refundTicket(id: Int) {
        println("Билет с номером $id, возвращен!")
        val find: Ticket? = tickets.find { ticket: Ticket -> ticket.uniqueID == id }
        if(find != null) {
            sessions[find.sessionId].seats[find.seatNumber] = true
            tickets.removeIf{ x -> x.uniqueID == id}
        } else
            println("Билет с таким номером не найден!")
    }

    fun displaySeatsStatus(sessionIndex: Int) {
        println("Все места на сеанс:")
        for (item in 0..<cinemaHall.totalSeats) {
            if (item % 5 == 4)
                println()
            print(if (sessions[sessionIndex].seats[item]) "$item.free " else "$item.booked ")
        }
        println()
    }
    fun editMovieData(movieId: Int) {
        print("Введите новое описание фильма: ")
        val input = readlnOrNull()
        if(input != null)
            movies[movieId].description = input
        else
            println("\nНельзя вводить пустую строку!")
    }
    fun editSessionData(sessionId: Int) {
        print("Введите новое время начала сеанса(в формате yyyy-MM-dd-HH-mm): ")
        val inputStart = readlnOrNull()
        print("\nВведите новое время конца сеанса(в формате yyyy-MM-dd-HH-mm): ")
        val inputEnd = readlnOrNull()
        if(inputStart != null && inputEnd != null) {
            val dateStart = LocalDateTime.parse(inputStart)
            val dateEnd = LocalDateTime.parse(inputEnd)
            sessions[sessionId].start = dateStart
            sessions[sessionId].end = dateEnd
        } else
            println("\nНельзя вводить пустую строку!")
    }
    fun sellTicket(customer: Customer, sessionId: Int) {
        displaySeatsStatus(sessionId)
        print("Выберите свободное место: ")
        val seat= readln().toIntOrNull()
        if(seat != null) {
            if(seat < cinemaHall.totalSeats && seat >= 0) {
                if(sessions[sessionId].seats[seat] == true) {
                    tickets.add(Ticket(customer, sessionId, uniqueID, seat))
                    sessions[sessionId].seats[seat] = false
                    println("Вы успешно приобрели билет с номером ${uniqueID++}")
                } else
                    println("Ошибка! Это место уже занято!")
            } else
                println("Ошибка! Места с таким номером не существует!")
        } else
            println("\nНельзя вводить пустую строку!")
    }
}