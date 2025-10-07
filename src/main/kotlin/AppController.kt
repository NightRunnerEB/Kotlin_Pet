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
                print("Неверный ввод! Попробуйте ещё раз: ")
                choice = readln().toIntOrNull()
            }
            when (choice) {
                1 -> {
                    while (true) {
                        print("Введите номер сеанса: ")
                        val sessionId = readlnOrNull()
                        print("Введите ваше имя: ")
                        val name = readlnOrNull()
                        print("Введите номер карты: ")
                        val card = readlnOrNull()
                        if (name!= null && card != null && sessionId != null && sessionId.toInt() < cinemaManager.sessions.size && sessionId.toInt() >= 0) {
                            cinemaManager.sellTicket(Customer(name, card.toLong()), sessionId.toInt())
                            break
                        }
                        else {
                            println("\nВведены некорректные данные!")
                            continue
                        }
                    }
                }

                2 -> {
                    while (true) {
                        print("Введите номер билета: ")
                        val ticketId = readlnOrNull()
                        if (ticketId!= null) {
                            cinemaManager.refundTicket(ticketId.toInt())
                            break
                        }
                        else {
                            println("\nВведены некорректные данные!")
                            continue
                        }
                    }
                }

                3 -> {
                    while (true) {
                        print("Введите номер сеанса: ")
                        val sessionId = readlnOrNull()
                        if (sessionId != null && sessionId.toInt() < cinemaManager.sessions.size && sessionId.toInt() >= 0) {
                            cinemaManager.displaySeatsStatus(sessionId.toInt())
                            break
                        }
                        else {
                            println("\nВведены некорректные данные!")
                            continue
                        }
                    }
                }

                4 -> {
                    while (true) {
                        print("Введите номер фильма: ")
                        val movieId = readlnOrNull()
                        if (movieId != null && movieId.toInt() < cinemaManager.movies.size && movieId.toInt() >= 0) {
                            cinemaManager.editMovieData(movieId.toInt())
                            break
                        }
                        else {
                            println("\nВведены некорректные данные!")
                            continue
                        }
                    }
                }

                5 -> {
                    while (true) {
                        print("Введите номер сеанса: ")
                        val sessionId = readlnOrNull()
                        if (sessionId != null && sessionId.toInt() < cinemaManager.sessions.size && sessionId.toInt() >= 0) {
                            cinemaManager.editSessionData(sessionId.toInt())
                            break
                        }
                        else {
                            println("\nВведены некорректные данные!")
                            continue
                        }
                    }
                }

                6 -> {
                    return
                }

                else -> println("Функционал с таким номером отсутствует!")
            }
        }
    }

    fun finishApp(manager: CinemaManager){
        filesController.saveChanges(manager.movies, FilePaths.MOVIES_FILE.path)
        filesController.saveChanges(manager.sessions, FilePaths.SESSIONS_FILE.path)
        filesController.saveChanges(manager.tickets, FilePaths.TICKETS_FILE.path)
        filesController.saveChanges(manager.cinemaHall, FilePaths.CINEMAHALL_FILE.path)
        println("Программа завершена...")
    }
}
