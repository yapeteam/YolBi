package today.yapeteam.ui.clickgui

import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import thunder.hack.utility.render.Render2DEngine
import today.yapeteam.YolBi4
import java.awt.Color

/**
 * @author yuxiangll
 * @since 2024/6/10 下午3:30
 * IntelliJ IDEA
 */
object ClickUI : Screen(Text.of("ClickGui")) {

    override fun init() {
        println("INit clickgui")

    }

    override fun shouldPause(): Boolean {
        return super.shouldPause()
    }


    override fun close() {
        super.close()
    }

    override fun render(context: DrawContext?, mouseX: Int, mouseY: Int, delta: Float) {
        //super.render(context, mouseX, mouseY, delta)

    }

}