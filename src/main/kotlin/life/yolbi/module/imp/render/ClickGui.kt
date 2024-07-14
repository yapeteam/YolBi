package life.yolbi.module.imp.render

import life.yolbi.gui.clickgui.ClickScreen
import life.yolbi.module.Bind
import life.yolbi.module.Category
import life.yolbi.module.Module
import net.minecraft.text.Text
import org.lwjgl.glfw.GLFW

/**
 * @author yuxiangll
 * @since 2024/7/8 下午12:13
 * IntelliJ IDEA
 */
object ClickGui : Module(
    "ClickGui",
    Category.RENDER,
    Bind(GLFW.GLFW_KEY_RIGHT_SHIFT)
) {

    var openned = false

    override fun onEnable() {
        if (!openned){
            openned = true
            //mc.setScreen(ClickScreen)
            //mc.player?.sendMessage(Text.of("开启clickgui"))
            mc.setScreen(ClickScreen)
        }

    }

    override fun onDisable() {
        openned = false
    }


}