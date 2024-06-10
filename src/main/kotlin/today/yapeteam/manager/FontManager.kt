package today.yapeteam.manager

import today.yapeteam.YolBi4
import today.yapeteam.font.FontAdapter
import today.yapeteam.font.RendererFontAdapter
import java.awt.Font
import java.util.*

/**
 * @author yuxiangll
 * @since 2024/6/7 下午9:58
 * IntelliJ IDEA
 */
class FontManager {

    lateinit var pingFang22: FontAdapter
    lateinit var pingFang16: FontAdapter


    private fun getPingFang(size: Int): FontAdapter {
        return createFont("assets/yolbi4/fonts/PingFang_Normal.ttf", size)
    }

    fun createFont(url: String, size: Int): FontAdapter {
        return try {
             RendererFontAdapter(
                Font.createFont(
                    Font.TRUETYPE_FONT,
                    Objects.requireNonNull(this::class.java.classLoader.getResourceAsStream(url))).deriveFont(Font.PLAIN, size*2F), size*2F)
            }catch (e: Exception){
                YolBi4.logger.error("字体加载错误")
                throw e
            }
    }



    fun initialize(){
        pingFang22 = getPingFang(22)
        pingFang16 = getPingFang(16)

    }

}