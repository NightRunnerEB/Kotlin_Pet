import java.io.Console
import kotlin.system.exitProcess
import kotlinx.serialization.Serializable
fun main() {
    val cinemaHall = CinemaHall(49)
    val cinemaManager = CinemaManager(cinemaHall)
    while (true) {
//        System.out.print("\033[H\033[2J");
//        System.out.flush();
//        Runtime.getRuntime().exec("cls");
        print(
            "\nФиксировать продажу билета - 1\n" +
                    "Вернуть проданный билет - 2\n" +
                    "Отобразить свободные и проданные места на сеанс - 3\n" +
                    "Редактировать информацию о фильме - 4\n" +
                    "Изменить расписание сеанса - 5\n" +
                    "Завершить работу программы - 6\n"
        )
        print("Введите номер желаемой команды: ")
        when (readln().toInt()) {
            1 -> {
                print("Введите номер сеанса: ")
                val sessionId = readln().toInt()
                print("Введите ваше имя: ")
                val name = readln()
                if (sessionId < cinemaManager.sessions.size && sessionId >= 0)
                    cinemaManager.sellTicket(name, sessionId)
                else
                    println("\nВведены некорректные данные!")
            }

            2 -> {
                print("Введите номер билета: ")
                val ticketId = readln().toInt()
                cinemaManager.refundTicket(ticketId)
            }

            3 -> {
                print("Введите номер сеанса: ")
                val sessionId = readln().toInt()
                if (sessionId < cinemaManager.sessions.size && sessionId >= 0)
                    cinemaManager.displaySeatsStatus(sessionId)
                else
                    println("\nВведены некорректные данные!")
            }

            4 -> {
                print("Введите номер фильма: ")
                val movieId = readln().toInt()
                if (movieId < cinemaManager.movies.size && movieId >= 0)
                    cinemaManager.editMovieData(movieId)
                else
                    println("\nВведены некорректные данные!")
            }

            5 -> {
                print("Введите номер сеанса: ")
                val sessionId = readln().toInt()
                if (sessionId < cinemaManager.sessions.size && sessionId >= 0)
                    cinemaManager.editSessionData(sessionId)
                else
                    println("\nВведены некорректные данные!")
            }

            6 -> {
                println("Программа завершена...")
                return
            }

            else -> println("Функционал с таким номером отсутствует!")
        }
    }
}