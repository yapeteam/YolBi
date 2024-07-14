package life.yolbi.gui.clickgui

import life.yolbi.gui.clickgui.imp.Windows
import life.yolbi.module.Category
import life.yolbi.module.imp.render.ClickGui
import life.yolbi.util.animation.Animation
import life.yolbi.util.animation.Easing
import life.yolbi.util.client.TimerUtil
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import org.lwjgl.glfw.GLFW

/**
 * @author yuxiangll
 * @since 2024/7/8 下午12:48
 * IntelliJ IDEA
 */
object ClickScreen : Screen(Text.of("ClickGUI")) {

    private val windows = ArrayList<AbstractButton>()
    private var topWindow: AbstractButton

    private val ODAnimation = Animation(Easing.EASE_OUT_CUBIC,300)
    private var desValue = 1.0
    private val timer = TimerUtil()


    init {
        windows.add(Windows(Category.COMBAT, 2F,2F,80F,450F))
        windows.add(Windows(Category.PLAYER,85F+2,2F,80F,450F))
        windows.add(Windows(Category.RENDER,85*2F+2,2F,80F,450F))
        windows.add(Windows(Category.MOVEMENT,85*3F+2,2F,80F,450F))
        windows.add(Windows(Category.EXPLOIT,85*4F+2,2F,80F,450F))
        windows.add(Windows(Category.WORLD,85*5F+2,2F,80F,450F))
        windows.add(Windows(Category.FUN,85*6F+2,2F,80F,450F))
        windows.add(Windows(Category.MISC,85*7F+2,2F,80F,450F))
        windows.add(Windows(Category.CLIENT,85*8F+2,2F,80F,450F))
        //CLIENT
        topWindow = windows.first()
    }

    override fun init() {
        desValue = 1.0
    }


    private fun onClose() {
        super.close()
        ClickGui.toggle(false)
        ClickGui.openned = false
        super.close()
    }

    override fun close() {

    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE){
            desValue = 0.0
            timer.reset()
        }

        return true
    }


    override fun render(context: DrawContext?, mouseX: Int, mouseY: Int, delta: Float) {
        if (context == null) return

        context.matrices.push()

        ODAnimation.run(desValue)
        context.matrices.scale(1F, ODAnimation.value.toFloat(), 1F)


        windows.forEach {
            it.render(context, mouseX, mouseY, delta)
        }


        context.matrices.pop()


        if (desValue == 0.0 && timer.passed(200L)){
            onClose()
        }

    }


    override fun mouseDragged(mouseX: Double, mouseY: Double, button: Int, deltaX: Double, deltaY: Double): Boolean {
        if (topWindow.isHowever(mouseX, mouseY)) {
            topWindow.mouseDragged(mouseX,mouseY,button,deltaX,deltaY)
        }




        return true
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        run breaking@{
            windows.forEach {
                if (it.isHowever(mouseX,mouseY) && it != topWindow){
                    topWindow = it
                    return@breaking
                }
            }
        }
        windows.last topped topWindow


        windows.forEach{ it.mouseClicked(mouseX.toInt(), mouseY.toInt(),button) }


        return true
    }


    private infix fun AbstractButton.topped(other: AbstractButton) {
        if (this != other){
            windows.remove(other)
            windows.addLast(other)
        }
    }

}