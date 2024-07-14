package life.yolbi.gui.clickgui

import life.yolbi.module.Category
import net.minecraft.client.gui.DrawContext

/**
 * @author yuxiangll
 * @since 2024/7/8 下午12:57
 * IntelliJ IDEA
 */

//module
abstract class AbstractButton(
    name: Category,
    private var xPos: Float,
    private var yPos: Float,
    private val width: Float,
    private var height: Float
) {

    abstract fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float)

    abstract fun mouseDragged(mouseX: Double, mouseY: Double, button: Int, deltaX: Double, deltaY: Double)

    abstract fun isHowever(mouseX: Double, mouseY: Double) : Boolean

    fun mouseClicked(mouseX: Int, mouseY: Int, button: Int){}

    fun mouseReleased(mouseX: Int, mouseY: Int, button: Int){}

//mouseX: Double, mouseY: Double

}