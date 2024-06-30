package today.yapeteam.module.imp.render

import meteordevelopment.orbit.EventHandler
import org.lwjgl.glfw.GLFW
import thunder.hack.utility.render.MSAAFramebuffer
import thunder.hack.utility.render.Render2DEngine
import today.yapeteam.YolBi
import today.yapeteam.event.EventRender2D
import today.yapeteam.module.Bind
import today.yapeteam.module.BindType
import today.yapeteam.module.Category
import today.yapeteam.module.Module
import today.yapeteam.utils.render.RenderHelper
import java.awt.Color

/**
 * @author yuxiangll
 * @since 2024/6/9 下午2:37
 * IntelliJ IDEA
 */

object HUD : Module("HUD",Category.RENDER,Bind(GLFW.GLFW_KEY_R)) {

    var test: Int = 0
    init {
        this.prefix = "测试前缀"
    }

    override fun onEnable() {
        test+=10
    }
    override fun onDisable() {
        test-=10
    }

    @EventHandler
    fun onRender(event: EventRender2D){
        val font = YolBi.fontManager.pingFang16
        YolBi.moduleManager.modules.forEachIndexed { index, module ->
            //font.drawString()
            font.drawString(event.context.matrices, module.name, 2.0, (test+10 + index * 10).toDouble(),Color(255, 255, 255))
            if (module.prefix.isNotEmpty()) font.drawString(event.context.matrices, "[${module.prefix}]", (2F + font.getStringWidth(module.name) + 2).toDouble(), (10F + index * 10).toDouble(), Color(100,100,100))
        }
        RenderHelper.setMatrixStack(event.context.matrices)
        RenderHelper.setContext(event.context)
        val context = event.context


        Render2DEngine.drawRoundedBlur(context.matrices,300F,100F,100F,100F,1F,Color(255,255,255),
            50F,0.3F)
        //Render2DEngine.drawBlurredShadow(context.matrices,300F,100F,100F,100F, 20,Color(255,255,255,1))
        Render2DEngine.drawGradientBlurredShadow1(
            context.matrices,
            50F,
            100F,
            100F,
            100F,
            50,
            Color(100,100,200),
            Color(163,155,253),
            Color(200,200,200),
            Color(200,100,100)
        )





    }

}