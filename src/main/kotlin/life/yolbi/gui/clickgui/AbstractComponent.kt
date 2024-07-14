package life.yolbi.gui.clickgui

import net.minecraft.client.gui.DrawContext

/**
 * @author yuxiangll
 * @since 2024/7/8 下午12:49
 * IntelliJ IDEA
 */

// setting
abstract class AbstractComponent {

    abstract fun render(context: DrawContext?, mouseX: Int, mouseY: Int, delta: Float)

    abstract fun mouseClicked(mouseX: Int, mouseY: Int, button: Int)

    abstract fun mouseReleased(mouseX: Int, mouseY: Int, button: Int)


}