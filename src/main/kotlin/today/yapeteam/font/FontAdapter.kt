package today.yapeteam.font

import net.minecraft.client.util.math.MatrixStack

/**
 * @author yuxiangll
 * @since 2024/6/10 上午7:39
 * IntelliJ IDEA
 */

interface FontAdapter {
    fun drawString(matrices: MatrixStack?, text: String?, x: Float, y: Float, color: Int)

    fun drawString(matrices: MatrixStack?, text: String?, x: Double, y: Double, color: Int)
    fun drawString(
        matrices: MatrixStack?,
        text: String?,
        x: Float,
        y: Float,
        r: Float,
        g: Float,
        b: Float,
        a: Float
    )

    fun drawCenteredString(matrices: MatrixStack?, text: String?, x: Double, y: Double, color: Int)

    fun drawCenteredString(
        matrices: MatrixStack?,
        text: String?,
        x: Double,
        y: Double,
        r: Float,
        g: Float,
        b: Float,
        a: Float
    )

    fun getStringWidth(text: String?): Float

    fun trimStringToWidth(`in`: String?, width: Double): String?

    fun trimStringToWidth(`in`: String?, width: Double, reverse: Boolean): String?

}