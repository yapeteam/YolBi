package today.yapeteam.manager

import thunder.hack.gui.font.FontRenderer
import thunder.hack.gui.font.FontRenderers

/**
 * @author yuxiangll
 * @since 2024/6/7 下午9:58
 * IntelliJ IDEA
 */
class FontManager {

    lateinit var pingFang22: FontRenderer
    lateinit var pingFang16: FontRenderer



    fun initialize(){
        pingFang22 = FontRenderers.create(22F,"PingFang_Normal")
        pingFang16 = FontRenderers.create(16F,"PingFang_Normal")

    }

}