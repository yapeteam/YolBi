package today.yapeteam.module.imp.render

import meteordevelopment.orbit.EventHandler
import today.yapeteam.event.EventRender2D
import today.yapeteam.module.Category
import today.yapeteam.module.Module

/**
 * @author yuxiangll
 * @since 2024/6/7 上午7:50
 * IntelliJ IDEA
 */

object RenderTest : Module("渲染测试", Category.RENDER){

    @EventHandler
    fun eventHandler(event: EventRender2D) {

//        MSAAFramebuffer.use(true){
//            RenderHelper.setMatrixStack(event.context.matrices)
//            RenderHelper.setContext(event.context)
//            RenderUtil.renderRoundedRect(10F,10F,100F,100F,Color(147,124,246),10F)
//        }


    }

    override fun onEnable() {
        TODO("Not yet implemented")
    }

    override fun onDisable() {
        TODO("Not yet implemented")
    }


}