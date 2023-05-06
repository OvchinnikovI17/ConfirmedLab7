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

fun main() {

    startKoin {
        modules(serverModule)
    }

    val serverModule = ServerModuleGet().returnServerModule()
    System.setProperty("log4j.configurationFile", "classpath:log4j2.xml")
    System.setProperty("DataOfCollection.server", "D:\\HOTFIXLABSIX\\untitled\\server\\src\\main\\resources\\DataOfCollection.txt")
    val logger: Logger = LogManager.getLogger(ServerModuleGet::class.java)
    logger.info("Запуск сервера")
    val threadPoolMain = Executors.newFixedThreadPool(10)

    while (true){
        serverModule.serverReceiver()
        serverModule.serverSender()
    }

}

class ServerModuleGet : KoinComponent{
    val serverModule: ServerModule by inject()
    fun returnServerModule():ServerModule{
        return serverModule
    }
}

// Facade pattern
//Блокирующая очередь, TP1(обработка) -> очередь(блокирующая) -> TP2(выполнение команды) -> очередь(блокирующая) -> ТP3(отправка)
//JDBC bdconfig