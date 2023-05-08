import com.google.gson.Gson
import di.serverModule
import moduleWithResults.ResultModule
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import workCommandsList.*
import java.net.DatagramPacket
import java.net.DatagramSocket
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.concurrent.Executors
import kotlin.concurrent.thread

fun main() {

    startKoin {
        modules(serverModule)
    }

    val serverModule = ServerModuleGet().returnServerModule()
    val dataBaseManager = ServerModuleGet().returnDatabase()
    dataBaseManager.connectionDB
    System.setProperty("log4j.configurationFile", "classpath:log4j2.xml")
    System.setProperty("DataOfCollection.server", "D:\\HOTFIXLABSIX\\untitled\\server\\src\\main\\resources\\DataOfCollection.txt")
    val logger: Logger = LogManager.getLogger(ServerModuleGet::class.java)
    logger.info("Запуск сервера")
    val threadPoolMain = Executors.newFixedThreadPool(10)

    thread { serverModule.serverReceiver() }
    thread { serverModule.commandExecutor() }
    thread { serverModule.serverSender() }


}

class ServerModuleGet : KoinComponent{
    val serverModule: ServerModule by inject()
    val dbModule: DataBaseManager by inject()
    fun returnServerModule():ServerModule{
        return serverModule
    }

    fun returnDatabase(): DataBaseManager{
        return  dbModule
    }
}

// Facade pattern
//Блокирующая очередь, TP1(обработка) -> очередь(блокирующая) -> TP2(выполнение команды) -> очередь(блокирующая) -> ТP3(отправка)
//JDBC bdconfig