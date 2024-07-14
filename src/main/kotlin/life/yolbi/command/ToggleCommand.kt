package life.yolbi.command

import com.mojang.brigadier.arguments.BoolArgumentType
import life.yolbi.managers.ModuleManager
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback

/**
 * @author yuxiangll
 * @since 2024/7/7 下午7:17
 * IntelliJ IDEA
 */
object ToggleCommand: Command("toggle","toggle your module") {

    override fun initializeCommand() {
        ModuleManager.modules.forEach { module->
            ClientCommandRegistrationCallback.EVENT.register(ClientCommandRegistrationCallback{ dispatcher, _ ->
                println(module.name)
                dispatcher.register(literal("yolbi")
                    .then(literal("toggle")
                        .then(literal(module.name)
                            .executes{
                                module.toggle()
                                1
                            }
                            .then(argument("state", BoolArgumentType.bool())
                                .executes{
                                    val state = BoolArgumentType.getBool(it,"state")
                                    module.toggle(state)
                                    1
                                })
                        )
                    )
                )
            })
        }
    }
}