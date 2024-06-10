package today.yapeteam.module.imp.render

import today.yapeteam.module.Bind
import today.yapeteam.module.Category
import today.yapeteam.module.Module
import today.yapeteam.ui.clickgui.ClickUI


/**
 * @author yuxiangll
 * @since 2024/6/10 下午2:54
 * IntelliJ IDEA
 */
object ClickGUI : Module("ClickGui", Category.RENDER, Bind(344)) {


    override fun onEnable() {
        this.toggle(false)
        mc.setScreen(ClickUI)
    }


}