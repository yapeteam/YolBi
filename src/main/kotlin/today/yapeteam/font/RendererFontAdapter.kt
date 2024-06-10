package today.yapeteam.font

import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.render.*
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Util
import org.joml.Matrix4f
import org.lwjgl.opengl.GL11
import today.yapeteam.font.AlphaOverride.compute
import java.awt.Font
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.math.floor
import kotlin.math.pow

/**
 * @author yuxiangll
 * @since 2024/6/10 上午8:04
 * IntelliJ IDEA
 */
class RendererFontAdapter(val font: Font, val size: Float) : FontAdapter {

    val fontRenderer: FontRenderer = FontRenderer(font)

    override fun drawString(matrices: MatrixStack?, text: String?, x: Float, y: Float, color: Int) {
        var color1 = color
        if ((color1 and -0x4000000) == 0) {
            color1 = color1 or -0x1000000
        }
        val a = (color1 shr 24 and 255).toFloat() / 255.0f
        val r = (color1 shr 16 and 255).toFloat() / 255.0f
        val g = (color1 shr 8 and 255).toFloat() / 255.0f
        val b = (color1 and 255).toFloat() / 255.0f
        drawString(matrices, text, x, y, r, g, b, a)
    }

    override fun drawString(matrices: MatrixStack?, text: String?, x: Double, y: Double, color: Int) {
        drawString(matrices, text, x.toFloat(), y.toFloat(), color)
    }

    override fun drawString(
        matrices: MatrixStack?,
        text: String?,
        x: Float,
        y: Float,
        r: Float,
        g: Float,
        b: Float,
        a: Float
    ) {
        if (text == null) throw Exception()
        if (matrices == null) throw Exception()

        val v: Float = compute.invoke((a*255).toInt()) / 255;
        fontRenderer.drawString(matrices, text, x, y, r, g, b, v)
    }

    override fun drawCenteredString(matrices: MatrixStack?, text: String?, x: Double, y: Double, color: Int) {
        var color1 = color
        if ((color1 and -0x4000000) == 0) {
            color1 = color1 or -0x1000000
        }
        val a = (color1 shr 24 and 255).toFloat() / 255.0f
        val r = (color1 shr 16 and 255).toFloat() / 255.0f
        val g = (color1 shr 8 and 255).toFloat() / 255.0f
        val b = (color1 and 255).toFloat() / 255.0f
        drawCenteredString(matrices, text, x, y, r, g, b, a)    }

    override fun drawCenteredString(
        matrices: MatrixStack?,
        text: String?,
        x: Double,
        y: Double,
        r: Float,
        g: Float,
        b: Float,
        a: Float
    ) {
        if (text == null) throw Exception()
        if (matrices == null) throw Exception()

        val v: Float = compute.invoke((a*255).toInt()) / 255;
        fontRenderer.drawCenteredString(matrices, text, x.toFloat(), y.toFloat(), r, g, b, v)

    }

    override fun getStringWidth(text: String?): Float {
        if (text == null) throw Exception()

        return fontRenderer.getStringWidth(text)
    }

    override fun trimStringToWidth(string: String?, width: Double): String {
        if (string.isNullOrEmpty()) return ""

        return string.fold(StringBuilder()) { str, char ->
            if (getStringWidth(str.toString()+char) >= width) return str.toString()
            str.append(char)
            return str.toString()
        }.toString()


    }

    override fun trimStringToWidth(`in`: String?, width: Double, reverse: Boolean): String? {
        return trimStringToWidth(`in`, width)
    }
}

class FontRenderer(private val f: Font){
    private val colorMap: Map<Char, Int> = Util.make<Map<Char, Int>> {
        val ci: MutableMap<Char, Int> = HashMap()
        ci['0'] = 0x000000
        ci['1'] = 0x0000AA
        ci['2'] = 0x00AA00
        ci['3'] = 0x00AAAA
        ci['4'] = 0xAA0000
        ci['5'] = 0xAA00AA
        ci['6'] = 0xFFAA00
        ci['7'] = 0xAAAAAA
        ci['8'] = 0x555555
        ci['9'] = 0x5555FF
        ci['A'] = 0x55FF55
        ci['B'] = 0x55FFFF
        ci['C'] = 0xFF5555
        ci['D'] = 0xFF55FF
        ci['E'] = 0xFFFF55
        ci['F'] = 0xFFFFFF
        ci
    }
    val glyphMap: MutableMap<Char, Glyph> = ConcurrentHashMap()
    var cachedHeight: Float = 0f

    init {
        init()
        cachedHeight = glyphMap.values
            .stream()
            .max(Comparator.comparingDouble { value: Glyph -> value.dimensions!!.height })
            .orElseThrow().dimensions!!.height.toFloat() * 0.25f
    }

    private fun init() {
        val chars = "ABCabc 123+-".toCharArray() // basic abc + specials, more gets generated on the fly
        for (aChar in chars) {
            val glyph = Glyph(aChar, f)
            glyphMap[aChar] = glyph
        }
    }

    fun roundToDecimal(n: Double, point: Int): Double {
        if (point == 0) {
            return floor(n)
        }
        val factor: Double = 10.0.pow(point.toDouble())
        return Math.round(n * factor) / factor
    }

    fun drawString(matrices: MatrixStack, s: String, x: Float, y: Float, r: Float, g: Float, b: Float, a: Float) {
        val roundedX = roundToDecimal(x.toDouble(), 1).toFloat()
        val roundedY = roundToDecimal(y.toDouble(), 1).toFloat()
        var r1 = r
        var g1 = g
        var b1 = b
        matrices.push()
        matrices.translate(roundedX, roundedY, 0f)
        matrices.scale(0.25f, 0.25f, 1f)

        RenderSystem.enableBlend()
        RenderSystem.defaultBlendFunc()
        RenderSystem.disableCull()
        GlStateManager._texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR)

        RenderSystem.setShader { GameRenderer.getPositionTexColorProgram() }
        val bufferBuilder = Tessellator.getInstance().buffer
        var isInSelector = false
        for (c in s.toCharArray()) {
            if (isInSelector) {
                val upper = c.toString().uppercase(Locale.getDefault())[0]
                val color = colorMap.getOrDefault(upper, 0xFFFFFF)
                r1 = (color shr 16 and 255).toFloat() / 255.0f
                g1 = (color shr 8 and 255).toFloat() / 255.0f
                b1 = (color and 255).toFloat() / 255.0f
                isInSelector = false
                continue
            }
            if (c == '§') {
                isInSelector = true
                continue
            }

            val matrix = matrices.peek().positionMatrix
            val prevWidth = drawChar(bufferBuilder, matrix, c, r1, g1, b1, a)
            matrices.translate(prevWidth, 0.0, 0.0)
        }
        matrices.pop()
        RenderSystem.enableCull()
    }

    fun stripControlCodes(`in`: String): String {
        val s = `in`.toCharArray()
        val out = java.lang.StringBuilder()
        var i = 0
        while (i < s.size) {
            val current = s[i]
            if (current == '§') {
                i++
                i++
                continue
            }
            out.append(current)
            i++
        }
        return out.toString()
    }

    fun getStringWidth(text: String): Float {
        var wid = 0f
        for (c in stripControlCodes(text).toCharArray()) {
            val g = glyphMap.computeIfAbsent(
                c
            ) { character: Char? ->
                Glyph(
                    character!!, f!!
                )
            }
            wid += g.dimensions!!.width.toFloat()
        }
        return wid * 0.25f
    }

    fun drawCenteredString(
        matrices: MatrixStack,
        s: String,
        x: Float,
        y: Float,
        r: Float,
        g: Float,
        b: Float,
        a: Float
    ) {
        drawString(matrices, s, x - getStringWidth(s) / 2f, y, r, g, b, a)
    }


    private fun drawChar(
        bufferBuilder: BufferBuilder,
        matrix: Matrix4f,
        c: Char,
        r: Float,
        g: Float,
        b: Float,
        a: Float
    ): Double {
        val v = compute.invoke(a.toInt())
        val glyph = glyphMap.computeIfAbsent(
            c
        ) { character: Char? ->
            Glyph(
                character!!, f!!
            )
        }
        RenderSystem.setShaderTexture(0, glyph.imageTex)

        val height = glyph.dimensions!!.height.toFloat()
        val width = glyph.dimensions!!.width.toFloat()

        val inOffsetX = glyph.offsetX / (width + 10)
        val inOffsetY = glyph.offsetY / (height + 10)

        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR)
        bufferBuilder.vertex(matrix, 0f, height, 0f).texture(0 + inOffsetX, 1 - inOffsetY).color(r, g, b, v).next()
        bufferBuilder.vertex(matrix, width, height, 0f).texture(1 - inOffsetX, 1 - inOffsetY).color(r, g, b, v).next()
        bufferBuilder.vertex(matrix, width, 0f, 0f).texture(1 - inOffsetX, 0 + inOffsetY).color(r, g, b, v).next()
        bufferBuilder.vertex(matrix, 0f, 0f, 0f).texture(0 + inOffsetX, 0 + inOffsetY).color(r, g, b, v).next()
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end())

        return width.toDouble()
    }
}

