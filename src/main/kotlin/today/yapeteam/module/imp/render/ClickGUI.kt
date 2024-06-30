package today.yapeteam.module.imp.render

import org.lwjgl.glfw.GLFW
import today.yapeteam.module.*
import today.yapeteam.ui.clickgui.ClickUI
import java.awt.event.KeyEvent


/**
 * @author yuxiangll
 * @since 2024/6/10 下午2:54
 * IntelliJ IDEA
 */
object ClickGUI : Module("ClickGui", Category.RENDER, Bind(GLFW.GLFW_KEY_RIGHT_SHIFT,BindType.PreClick)) {

    private val isPause: BooleanSetting = BooleanSetting("Pause", "is pause on ClickGUI opened", false)


    init {
        this.addSetting(isPause)
    }
    override fun onEnable() {
        this.toggle(false)
        mc.setScreen(ClickUI)
    }


}