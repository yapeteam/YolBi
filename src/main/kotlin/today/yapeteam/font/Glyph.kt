package today.yapeteam.font

import net.minecraft.client.texture.NativeImage
import net.minecraft.client.texture.NativeImageBackedTexture
import org.lwjgl.BufferUtils
import today.yapeteam.YolBi4.mc
import java.awt.*
import java.awt.font.FontRenderContext
import java.awt.geom.AffineTransform
import java.awt.geom.Rectangle2D
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.util.*
import javax.imageio.ImageIO
import kotlin.math.ceil

/**
 * @author yuxiangll
 * @since 2024/6/10 上午11:47
 * IntelliJ IDEA
 */
class Glyph(val c: Char, val font: Font) {

    val imageTex: Texture = Texture("font/glyphs/" + c.code + "-" + font.getName().lowercase(Locale.getDefault()).hashCode() + Math.floor(Math.random() * 0xFFFF))
    val offsetX: Int = 5
    val offsetY: Int = 5
    var dimensions: Rectangle2D? = null

    init {
        generateTexture()
    }

    fun generateTexture() {
        val affineTransform = AffineTransform()
        val fontRenderContext = FontRenderContext(affineTransform, true, true)
        val dim: Rectangle2D = font.getStringBounds(c.toString(), fontRenderContext)
        this.dimensions = dim
        val bufferedImage = BufferedImage(
            ceil(dim.width).toInt() + 10, ceil(dim.height)
                .toInt() + 10, BufferedImage.TYPE_INT_ARGB
        )
        val g = bufferedImage.createGraphics()

        g.font = font
        // Set Color to Transparent
        g.color = Color(255, 255, 255, 0)
        // Set the image background to transparent
        g.fillRect(0, 0, bufferedImage.width, bufferedImage.height)

        g.color = Color.white

        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON)
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF)
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
        val fontMetrics = g.fontMetrics
        g.drawString(c.toString(), offsetX, offsetY + fontMetrics.ascent)

        registerBufferedImageTexture(imageTex, bufferedImage)
    }

    fun registerBufferedImageTexture(i: Texture?, bi: BufferedImage?) {
        try {
            val baos = ByteArrayOutputStream()
            ImageIO.write(bi, "png", baos)
            val bytes = baos.toByteArray()
            registerTexture(i, bytes)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
    fun registerTexture(i: Texture?, content: ByteArray) {
        try {
            val data = BufferUtils.createByteBuffer(content.size).put(content)
            data.flip()
            val tex = NativeImageBackedTexture(NativeImage.read(data))
            mc.execute { mc.textureManager.registerTexture(i, tex) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}