package today.yapeteam.ui.clickgui

import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import thunder.hack.utility.render.MSAAFramebuffer

/**
 * @author yuxiangll
 * @since 2024/6/10 下午3:30
 * IntelliJ IDEA
 */
object ClickUI : Screen(Text.of("ClickGui")) {

    private val windowsList: MutableList<AbstractWindow> = mutableListOf()
    private val combatWindow: AbstractWindow = AbstractWindow("Combat",5F,10F,60F,200F)
    private val musicWindow: AbstractWindow = AbstractWindow("Music",70F,10F,60F,200F)
    private val renderWindow: AbstractWindow = AbstractWindow("Render",135F,10F,60F,200F)
    private val movementWindow: AbstractWindow = AbstractWindow("Movement",200F,10F,60F,200F)
    private val playerWindow: AbstractWindow = AbstractWindow("Player",265F,10F,60F,200F)
    private val clientWindow: AbstractWindow = AbstractWindow("Client",330F,10F,60F,200F)
    override fun init() {
        windowsList.clear()
        windowsList.add(combatWindow)
        windowsList.add(musicWindow)
        windowsList.add(renderWindow)
        windowsList.add(movementWindow)
        windowsList.add(playerWindow)
        windowsList.add(clientWindow)

    }

    override fun shouldPause(): Boolean {
        return false
    }

    override fun close() {
        super.close()
    }

    override fun resize(client: MinecraftClient?, width: Int, height: Int) {
        windowsList.clear()
        windowsList.add(combatWindow)
        windowsList.add(musicWindow)
        windowsList.add(renderWindow)
        windowsList.add(movementWindow)
        windowsList.add(playerWindow)
        windowsList.add(clientWindow)
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {

        return super.mouseClicked(mouseX, mouseY, button)
    }

    override fun mouseReleased(mouseX: Double, mouseY: Double, button: Int): Boolean {

        return super.mouseReleased(mouseX, mouseY, button)
    }


    override fun mouseDragged(mouseX: Double, mouseY: Double, button: Int, deltaX: Double, deltaY: Double): Boolean {

        windowsList.forEach {
            if (it.hoevered){
                it.x += deltaX.toFloat()
                it.y += deltaY.toFloat()
            }
        }


        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY)
    }


    override fun render(context: DrawContext?, mouseX: Int, mouseY: Int, delta: Float) {
        if (context == null) return

        windowsList.forEach {
            it.isHoever(mouseX.toFloat(),mouseY.toFloat())
            it.render(context, mouseX, mouseY, delta)
            it.animationX.run(it.x.toDouble())
            it.animationY.run(it.y.toDouble())
        }

//        windowsList.forEach {
//            it.render(context, mouseX, mouseY, delta)
//        }
        //super.render(context, mouseX, mouseY, delta)
        //println("INit clickgui")

    }





}