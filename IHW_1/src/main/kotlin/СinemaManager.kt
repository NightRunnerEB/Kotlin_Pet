import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class CinemaManager(private val cinemaHall: CinemaHall) {

    var movies: MutableList<Movie> = mutableListOf<Movie>(
        Movie("Interstellar", "There is no need to say much about this film - just a masterpiece", 170),
        Movie(
            "The Imitation Game",
            "A film about a mathematician who invented one of the first computers and deciphered the Enigma code",
            115
        ),
        Movie("Лучшие в аду", "Патриотично и ничего больше", 109)
    )

    var sessions: MutableList<Session> = mutableListOf(
        Session(movies[0], LocalDateTime.of(2023, 12, 25, 10, 30), LocalDateTime.of(2023, 12, 25, 13, 20), Array(cinemaHall.totalSeats) { true }),
        Session(movies[1], LocalDateTime.of(2023, 12, 25, 13, 40), LocalDateTime.of(2023, 12, 25, 15, 35), Array(cinemaHall.totalSeats) { true }),
        Session(movies[2], LocalDateTime.of(2023, 12, 25, 15, 55), LocalDateTime.of(2023, 12, 25, 17, 20), Array(cinemaHall.totalSeats) { true }),
        )
    private var soldTickets: MutableList<Ticket> = mutableListOf()
    private var uniqueID: Int = 1

    fun refundTicket(id: Int) {
        println("Билет с номером $id, возвращен!")
        val find: Ticket? = soldTickets.find { ticket: Ticket -> ticket.uniqueID == id }
        if(find != null) {
            sessions[find.sessionId].seats[find.seatNumber] = true
            soldTickets.removeIf{x -> x.uniqueID == id}
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
        val dtf = DateTimeFormatter.ofPattern("yyyy-dd-MM-d-HH-mm", Locale.ENGLISH)
        print("Введите новое время начала сеанса(в формате yyyy-dd-MM-d-HH-mm): ")
        val inputStart = readlnOrNull()
        print("\nВведите новое время конца сеанса(в формате yyyy-dd-MM-d-HH-mm): ")
        val inputEnd = readlnOrNull()
        if(inputStart != null && inputEnd != null) {
            val dateStart = LocalDateTime.parse(inputStart, dtf)
            val dateEnd = LocalDateTime.parse(inputEnd, dtf)
            sessions[sessionId].start = dateStart
            sessions[sessionId].end = dateEnd
        } else
            println("\nНельзя вводить пустую строку!")
    }
    fun sellTicket(name: String, sessionId: Int) {
        displaySeatsStatus(sessionId)
        print("Выберите свободное место: ")
        val seat: Int = readln().toInt()
        if(seat < cinemaHall.totalSeats && seat >= 0) {
            if(sessions[sessionId].seats[seat] == true) {
                soldTickets.add(Ticket(name, sessionId, uniqueID, seat))
                sessions[sessionId].seats[seat] = false
                println("Вы успешно приобрели билет с номером ${uniqueID++}")
            } else
                println("Ошибка! Это место уже занято!")
        } else
            println("Ошибка! Места с таким номером не существует!")
    }
}