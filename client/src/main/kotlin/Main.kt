
import di.koinModule
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import usersView.AnswerToUser
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.io.File

fun main() {

    startKoin {
        modules(koinModule)
    }
    //Stas

    val listNo = File("listNo.txt")
    val listLong = File("listLong.txt")
    val listObject = File("listObject.txt")
    val listObjectPlus = File("listObjectPlus.txt")
    val writeToConsole: AnswerToUser = AnswerToUser()
    val tokenizator = KoinStarter().returnTokenizator()
    val clientModule = KoinStarter().returnClientModule()
    System.setProperty("log4j.configurationFile", "classpath:log4j2.xml")
    val logger: Logger = LogManager.getLogger(KoinStarter::class.java)

    writeToConsole.writeToConsoleLn("Для получения списка команд введите: help")
    clientModule.start()
    logger.info("Начало программы")
    tokenizator.downloadLists()
    tokenizator.tokenizator("update_command", mutableListOf<String>())

    while (true){
        writeToConsole.writeToConsole("> ")
        val getCommandFromUser: List<String> = ((readln().lowercase()) + " 1").split(" ")
        val command = getCommandFromUser[0]
        val argument = mutableListOf<String>()
        for (i in 1..getCommandFromUser.size - 1) {
            argument.add(getCommandFromUser[i])
        }
        logger.info("Запуск команды: $command")
        tokenizator.tokenizator(command, argument)
    }
}

class KoinStarter: KoinComponent{
    val tokenizator: Tokenizator by inject()
    val clientModule: ClientModule by inject()
    fun returnTokenizator(): Tokenizator{
        return tokenizator
    }

    fun returnClientModule(): ClientModule{
        return clientModule
    }
}
