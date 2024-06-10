package today.yapeteam.module.imp.render

import meteordevelopment.orbit.EventHandler
import today.yapeteam.YolBi4
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

object HUD : Module("HUD",Category.RENDER, Bind(82, BindType.Hold)) {

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
        val font = YolBi4.fontManager.pingFang16
        YolBi4.moduleManager.modules.forEachIndexed { index, module ->
            font.drawString(event.context.matrices, module.name, 2F, test+10F + index * 10, -1)
            if (module.prefix.isNotEmpty()) font.drawString(event.context.matrices, "[${module.prefix}]", 2F + font.getStringWidth(module.name) + 2, 10F + index * 10, Color(100,100,100).rgb)
        }
        RenderHelper.setMatrixStack(event.context.matrices)
        RenderHelper.setContext(event.context)

    }

}