import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileWriter

class AppController {
    private val filesController = FilesController()
    var movies: MutableList<Movie> = mutableListOf()
    var sessions: MutableList<Session> = mutableListOf()
    var tickets: MutableList<Ticket> = mutableListOf()
    var cinemaHall: CinemaHall = CinemaHall(0)
    fun startApp(): CinemaManager{
        movies = filesController.getData<MutableList<Movie>>(FilePaths.MOVIES_FILE.path)
        sessions = filesController.getData<MutableList<Session>>(FilePaths.SESSIONS_FILE.path)
        tickets = filesController.getData<MutableList<Ticket>>(FilePaths.TICKETS_FILE.path)
        cinemaHall = filesController.getData<CinemaHall>(FilePaths.CINEMAHALL_FILE.path)

        return CinemaManager(cinemaHall, movies, sessions, tickets)
    }

    fun appProcess(cinemaManager: CinemaManager){
        while(true){
            Runtime.getRuntime().exec("clear")
            print(
                "\nФиксировать продажу билета - 1\n" +
                        "Вернуть проданный билет - 2\n" +
                        "Отобразить свободные и проданные места на сеанс - 3\n" +
                        "Редактировать информацию о фильме - 4\n" +
                        "Изменить расписание сеанса - 5\n" +
                        "Завершить работу программы - 6\n"
            )
            print("Введите номер желаемой команды: ")
            var choice = readln().toIntOrNull()
            while(choice == null || choice < 1 || choice > 6){
                print("Wrong input! Try again:")
                choice = readln().toIntOrNull()
            }
            when (choice) {
                1 -> {
                    while (true) {
                        print("Введите номер сеанса: ")
                        val sessionId = readln().toInt()
                        print("Введите ваше имя: ")
                        val name = readlnOrNull()
                        print("Введите номер карты: ")
                        val card = readlnOrNull()
                        if (sessionId < cinemaManager.sessions.size && sessionId >= 0 && name!= null && card != null) {
                            cinemaManager.sellTicket(Customer(name, card.toLong()), sessionId)
                            break
                        }
                        else {
                            println("\nВведены некорректные данные!")
                            continue
                        }
                    }
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
                    return
                }

                else -> println("Функционал с таким номером отсутствует!")
            }
        }
    }

    fun finishApp(manager: CinemaManager){
        filesController.saveChanges(manager.movies, FilePaths.MOVIES_FILE.toString())
        filesController.saveChanges(manager.sessions, FilePaths.SESSIONS_FILE.toString())
        filesController.saveChanges(manager.tickets, FilePaths.TICKETS_FILE.toString())
        filesController.saveChanges(manager.cinemaHall, FilePaths.CINEMAHALL_FILE.toString())
        println("Программа завершена...")
    }
}