package life.yolbi.gui.clickgui.imp

import life.yolbi.gui.clickgui.AbstractButton
import life.yolbi.managers.FontManager
import life.yolbi.managers.ModuleManager
import life.yolbi.module.Category
import life.yolbi.module.Module
import life.yolbi.util.animation.Animation
import life.yolbi.util.animation.Easing
import life.yolbi.util.render.MSAAFramebuffer
import life.yolbi.util.render.Render2DEngine
import net.minecraft.client.gui.DrawContext
import java.awt.Color

/**
 * @author yuxiangll
 * @since 2024/7/8 下午1:17
 * IntelliJ IDEA
 */
class Windows(
    private val name: Category,
    private var xPos: Float,
    private var yPos: Float,
    private val width: Float,
    private var height: Float,
): AbstractButton(name,xPos,yPos, width, height) {
    val animation: Animation = Animation(Easing.EASE_OUT_CIRC,500)
    val modules = ArrayList<Module>()
    init {
        modules.addAll(
            ModuleManager.modules.filter { it.category == name }
        )
        this.height = 20F + modules.size*18
    }


    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        if (isHowever(mouseX.toDouble(), mouseY.toDouble())){
            animation.run(100.0)
        }else{
            animation.run(0.0)
        }
        Render2DEngine.drawRound(context.matrices,xPos,yPos,width,height,5F,Color(170,170,170,animation.value.toInt()))
        Render2DEngine.drawRoundedBlur(context.matrices,xPos,yPos,width,height,5F, Color(10,10,10),10F,0.7F)

        FontManager.pingFang22.drawCenteredString(context.matrices,name.readableName,(xPos+width/2).toDouble(),(yPos+4).toDouble(),Color(250,250,250))
        Render2DEngine.drawRect(context.matrices,xPos+2,yPos+16,width-4,1F,Color(250,250,250,40))
        //Render2DEngine.drawLine(xPos+2,yPos+16,xPos+width-4,yPos+16,Color(250,250,250,10).rgb)

        modules.forEachIndexed { index, module ->
            if (mouseX.toFloat() in xPos+2..xPos+width-4 && mouseY.toFloat() in yPos+20+index*18..yPos+20+index*18+15) {
                Render2DEngine.drawRoundedBlur(context.matrices,xPos+2,yPos+20+index*18,width-4,15F,3F, Color(170,170,170),10F,0.7F)
            }else{
                Render2DEngine.drawRoundedBlur(context.matrices,xPos+2,yPos+20+index*18,width-4,15F,3F, Color(100,100,100),10F,0.7F)
            }


            //Render2DEngine.drawRoundedBlur(context.matrices,xPos+2,yPos+20+index*18,width-4,15F,3F, Color(100,100,100),10F,0.7F)
            FontManager.pingFang16.drawString(context.matrices,module.name,(xPos+4).toDouble(),(yPos+20+5+index*18).toDouble(),Color(200,200,200))
        }


    }

    override fun mouseDragged(mouseX: Double, mouseY: Double, button: Int, deltaX: Double, deltaY: Double) {

        xPos += deltaX.toFloat()
        yPos += deltaY.toFloat()

        //println("xPos: $xPos, yPos: $yPos, mouseX: $mouseX, mouseY: $mouseY")
    }

    override fun isHowever(mouseX: Double, mouseY: Double) : Boolean{
        return mouseX in xPos..xPos + width && mouseY in yPos..yPos + height
    }

}