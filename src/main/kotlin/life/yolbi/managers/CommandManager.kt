package life.yolbi.managers

import life.yolbi.command.Command
import life.yolbi.command.HelpCommand
import life.yolbi.command.ToggleCommand

/**
 * @author yuxiangll
 * @since 2024/7/7 下午7:35
 * IntelliJ IDEA
 */
object CommandManager {
    val commands = mutableListOf<Command>()


    fun initialize(){
        commands.add(HelpCommand)
        commands.add(ToggleCommand)


        commands.forEach { it.initializeCommand() }
    }




}