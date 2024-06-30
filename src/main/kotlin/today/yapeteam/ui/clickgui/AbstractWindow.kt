package today.yapeteam.ui.clickgui

import net.minecraft.client.gui.DrawContext
import thunder.hack.utility.render.Render2DEngine
import today.yapeteam.YolBi
import today.yapeteam.utils.animation.Animation
import today.yapeteam.utils.animation.Easing
import java.awt.Color

/**
 * @author yuxiangll
 * @since 2024/6/15 下午7:06
 * IntelliJ IDEA
 */

class AbstractWindow(val name: String, var x: Float = 0f, var y: Float = 0f, var width: Float = 0f, var height: Float = 0f) {
    var hoevered: Boolean = false
    //var draggable: Boolean = false
    var dragging: Boolean = false
    val animationX: Animation = Animation(Easing.EASE_OUT_QUINT,300)
    val animationY: Animation = Animation(Easing.EASE_OUT_QUINT,300)



    fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        if (hoevered) {
//            Render2DEngine.drawGradientBlurredShadow1(
//                context.matrices,
//                animationX.value.toFloat(),
//                animationY.value.toFloat(),
//                width,
//                height,10,
//                Color(255,255,255,100),
//                Color(255,255,255,100),
//                Color(255,255,255,100),
//                Color(255,255,255,100)
//            )
            Render2DEngine.drawRoundedBlur(
                context.matrices,
                animationX.value.toFloat(),
                animationY.value.toFloat(),
                width,
                height,10F,Color(55,58,115,255),
                5f,0.55f
            )
        }else{
            Render2DEngine.drawRoundedBlur(
                context.matrices,
                animationX.value.toFloat(),
                animationY.value.toFloat(),
                width,
                height,10F,Color(55,58,115,255),
                5f,0.2f
            )
        }
        YolBi.fontManager.pingFang22.drawCenteredString(
            context.matrices,
            name,
            (animationX.value.toFloat()+width/2).toDouble(),
            (animationY.value.toFloat()+5).toDouble(),
            Color(255,255,255).rgb
        )

    }

    fun isHoever(mouseX: Float, mouseY: Float) {
        hoevered = mouseX in x..(x+width) && mouseY in y..(y+height)
    }


}