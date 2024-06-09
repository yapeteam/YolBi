package today.yapeteam.manager

import today.yapeteam.YolBi4
import today.yapeteam.font.FontAdapter
import today.yapeteam.font.FontRenderers

/**
 * @author yuxiangll
 * @since 2024/6/7 下午9:58
 * IntelliJ IDEA
 */
class FontManager {

    lateinit var pingFang22: FontAdapter
    lateinit var pingFang16: FontAdapter


    private fun getPingFang(size: Int): FontAdapter {
        return FontRenderers.createFont("assets/yolbi4/fonts/PingFang_Normal.ttf", size)
    }
    fun initialize(){
        pingFang22 = getPingFang(22)
        pingFang16 = getPingFang(16)

    }

}