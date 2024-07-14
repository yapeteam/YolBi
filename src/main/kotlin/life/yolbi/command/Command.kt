package life.yolbi.command

import life.yolbi.util.IMinecraft
import net.minecraft.text.Text

/**
 * @author yuxiangll
 * @since 2024/7/7 下午7:18
 * IntelliJ IDEA
 */
abstract class Command(val name: String, val description: String): IMinecraft{

    val send: (String)-> Unit = {
        mc.player?.sendMessage(Text.of(it))
    }


    abstract fun initializeCommand()


}