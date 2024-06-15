package today.yapeteam.module.imp.render

import com.sun.jna.platform.KeyboardUtils
import today.yapeteam.module.Bind
import today.yapeteam.module.Category
import today.yapeteam.module.Module
import today.yapeteam.ui.clickgui.ClickUI
import java.awt.event.KeyEvent


/**
 * @author yuxiangll
 * @since 2024/6/10 下午2:54
 * IntelliJ IDEA
 */
object ClickGUI : Module("ClickGui", Category.RENDER, Bind(82)) {


    override fun onEnable() {
        mc.setScreen(ClickUI)
        this.toggle(false)
    }


}