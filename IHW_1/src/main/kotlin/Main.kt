
fun main(args: Array<String>) {
    val appController = AppController()
    val manager = appController.startApp()
    appController.appProcess(manager)
    appController.finishApp(manager)
}