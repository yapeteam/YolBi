package life.yolbi.util.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import life.yolbi.font.Texture;
import life.yolbi.util.IMinecraftClient;
import life.yolbi.util.math.ColorUtility;
import life.yolbi.util.math.MathUtility;
import life.yolbi.util.render.shaders.ArcShader;
import life.yolbi.util.render.shaders.BlurProgram;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.RandomStringUtils;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL40C;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Stack;


public class Render2DEngine implements IMinecraftClient {

    private static BlurProgram BLUR;

    public static HashMap<Integer, BlurredShadow> shadowCache = new HashMap<>();
    public static HashMap<Integer, BlurredShadow> shadowCache1 = new HashMap<>();
    final static Stack<Rectangle> clipStack = new Stack<>();

    public static void initShaders() {

        BLUR = new BlurProgram();
    }

    public static void drawRoundedBlur(MatrixStack matrices, float x, float y, float width, float height, float radius, Color c1, float blurStrenth, float blurOpacity) {
        BufferBuilder bb = preShaderDraw(matrices, x - 10, y - 10, width + 20, height + 20);
        BLUR.setParameters(x, y, width, height, radius, c1, blurStrenth, blurOpacity);
        BLUR.use();
        BufferRenderer.drawWithGlobalProgram(bb.end());
        endRender();
    }




    public static void addWindow(MatrixStack stack, Rectangle r1) {
        Matrix4f matrix = stack.peek().getPositionMatrix();
        Vector4f coord = new Vector4f(r1.x, r1.y, 0, 1);
        Vector4f end = new Vector4f(r1.x1, r1.y1, 0, 1);
        coord.mulTranspose(matrix);
        end.mulTranspose(matrix);
        float x = coord.x();
        float y = coord.y();
        float endX = end.x();
        float endY = end.y();
        Rectangle r = new Rectangle(x, y, endX, endY);
        if (clipStack.empty()) {
            clipStack.push(r);
            beginScissor(r.x, r.y, r.x1, r.y1);
        } else {
            Rectangle lastClip = clipStack.peek();
            float lsx = lastClip.x;
            float lsy = lastClip.y;
            float lstx = lastClip.x1;
            float lsty = lastClip.y1;
            float nsx = MathHelper.clamp(r.x, lsx, lstx);
            float nsy = MathHelper.clamp(r.y, lsy, lsty);
            float nstx = MathHelper.clamp(r.x1, nsx, lstx);
            float nsty = MathHelper.clamp(r.y1, nsy, lsty);
            clipStack.push(new Rectangle(nsx, nsy, nstx, nsty));
            beginScissor(nsx, nsy, nstx, nsty);
        }
    }

    public static void popWindow() {
        clipStack.pop();
        if (clipStack.empty()) {
            endScissor();
        } else {
            Rectangle r = clipStack.peek();
            beginScissor(r.x, r.y, r.x1, r.y1);
        }
    }

    public static void beginScissor(double x, double y, double endX, double endY) {
        double width = endX - x;
        double height = endY - y;
        width = Math.max(0, width);
        height = Math.max(0, height);
        int ay = (int) ((mc.getWindow().getScaledHeight() - (y + height)));
        RenderSystem.enableScissor((int) (x), ay, (int) (width), (int) (height));
    }

    public static void endScissor() {
        RenderSystem.disableScissor();
    }

    public static void addWindow(MatrixStack stack, float x, float y, float x1, float y1, double animation_factor) {
        float h = y + y1;
        float h2 = (float) (h * (1d - MathUtility.clamp(animation_factor, 0, 1.0025f)));

        float x3 = x;
        float y3 = y + h2;
        float x4 = x1;
        float y4 = y1 - h2;

        if (x4 < x3) x4 = x3;
        if (y4 < y3) y4 = y3;
        addWindow(stack, new Rectangle(x3, y3, x4, y4));
    }

    public static void horizontalGradient(MatrixStack matrices, float x1, float y1, float x2, float y2, Color startColor, Color endColor) {
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        setupRender();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        buffer.vertex(matrix, x1, y1, 0.0F).color(startColor.getRGB());
        buffer.vertex(matrix, x1, y2, 0.0F).color(startColor.getRGB());
        buffer.vertex(matrix, x2, y2, 0.0F).color(endColor.getRGB());
        buffer.vertex(matrix, x2, y1, 0.0F).color(endColor.getRGB());
        BufferRenderer.drawWithGlobalProgram(buffer.end());
        endRender();
    }

    public static void verticalGradient(MatrixStack matrices, float left, float top, float right, float bottom, Color startColor, Color endColor) {
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        setupRender();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        buffer.vertex(matrix, left, top, 0.0F).color(startColor.getRGB());
        buffer.vertex(matrix, left, bottom, 0.0F).color(endColor.getRGB());
        buffer.vertex(matrix, right, bottom, 0.0F).color(endColor.getRGB());
        buffer.vertex(matrix, right, top, 0.0F).color(startColor.getRGB());
        BufferRenderer.drawWithGlobalProgram(buffer.end());
        endRender();
    }

    public static void drawRect(MatrixStack matrices, float x, float y, float width, float height, Color c) {
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        setupRender();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        buffer.vertex(matrix, x, y + height, 0.0F).color(c.getRGB());
        buffer.vertex(matrix, x + width, y + height, 0.0F).color(c.getRGB());
        buffer.vertex(matrix, x + width, y, 0.0F).color(c.getRGB());
        buffer.vertex(matrix, x, y, 0.0F).color(c.getRGB());
        BufferRenderer.drawWithGlobalProgram(buffer.end());
        endRender();
    }

    public static void drawRectWithOutline(MatrixStack matrices, float x, float y, float width, float height, Color c, Color c2) {
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        setupRender();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        buffer.vertex(matrix, x, y + height, 0.0F).color(c.getRGB());
        buffer.vertex(matrix, x + width, y + height, 0.0F).color(c.getRGB());
        buffer.vertex(matrix, x + width, y, 0.0F).color(c.getRGB());
        buffer.vertex(matrix, x, y, 0.0F).color(c.getRGB());
        BufferRenderer.drawWithGlobalProgram(buffer.end());

        buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION_COLOR);
        buffer.vertex(matrix, x, y + height, 0.0F).color(c2.getRGB());
        buffer.vertex(matrix, x + width, y + height, 0.0F).color(c2.getRGB());
        buffer.vertex(matrix, x + width, y, 0.0F).color(c2.getRGB());
        buffer.vertex(matrix, x, y, 0.0F).color(c2.getRGB());
        buffer.vertex(matrix, x, y + height, 0.0F).color(c2.getRGB());
        BufferRenderer.drawWithGlobalProgram(buffer.end());
        endRender();
    }

    public static void drawRectDumbWay(MatrixStack matrices, float x, float y, float x1, float y1, Color c1) {
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        setupRender();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        buffer.vertex(matrix, x, y1, 0.0F).color(c1.getRGB());
        buffer.vertex(matrix, x1, y1, 0.0F).color(c1.getRGB());
        buffer.vertex(matrix, x1, y, 0.0F).color(c1.getRGB());
        buffer.vertex(matrix, x, y, 0.0F).color(c1.getRGB());
        BufferRenderer.drawWithGlobalProgram(buffer.end());
        endRender();
    }

    public static void setRectPoints(BufferBuilder bufferBuilder, Matrix4f matrix, float x, float y, float x1, float y1, Color c1, Color c2, Color c3, Color c4) {
        bufferBuilder.vertex(matrix, x, y1, 0.0F).color(c1.getRGB());
        bufferBuilder.vertex(matrix, x1, y1, 0.0F).color(c2.getRGB());
        bufferBuilder.vertex(matrix, x1, y, 0.0F).color(c3.getRGB());
        bufferBuilder.vertex(matrix, x, y, 0.0F).color(c4.getRGB());
    }


    public static void drawBlurredShadow(MatrixStack matrices, float x, float y, float width, float height, int blurRadius, Color color) {
        width = width + blurRadius * 2;
        height = height + blurRadius * 2;
        x = x - blurRadius;
        y = y - blurRadius;

        int identifier = (int) (width * height + width * blurRadius);
        if (shadowCache.containsKey(identifier)) {
            shadowCache.get(identifier).bind();
        } else {
            BufferedImage original = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB);
            Graphics g = original.getGraphics();
            g.setColor(new Color(-1));
            g.fillRect(blurRadius, blurRadius, (int) (width - blurRadius * 2), (int) (height - blurRadius * 2));
            g.dispose();
            GaussianFilter op = new GaussianFilter(blurRadius);
            BufferedImage blurred = op.filter(original, null);
            shadowCache.put(identifier, new BlurredShadow(blurred));
            return;
        }

        setupRender();
        RenderSystem.setShaderColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        renderTexture(matrices, x, y, width, height, 0, 0, width, height, width, height);
        endRender();
    }

    public static void drawGradientBlurredShadow(MatrixStack matrices, float x, float y, float width, float height, int blurRadius, Color color1, Color color2, Color color3, Color color4) {
        width = width + blurRadius * 2;
        height = height + blurRadius * 2;
        x = x - blurRadius;
        y = y - blurRadius;

        int identifier = (int) (width * height + width * blurRadius);
        if (shadowCache.containsKey(identifier)) {
            shadowCache.get(identifier).bind();
        } else {
            BufferedImage original = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB);
            Graphics g = original.getGraphics();
            g.setColor(new Color(-1));
            g.fillRect(blurRadius, blurRadius, (int) (width - blurRadius * 2), (int) (height - blurRadius * 2));
            g.dispose();
            GaussianFilter op = new GaussianFilter(blurRadius);
            BufferedImage blurred = op.filter(original, null);
            shadowCache.put(identifier, new BlurredShadow(blurred));
            return;
        }

        setupRender();
        renderGradientTexture(matrices, x, y, width, height, 0, 0, width, height, width, height, color1, color2, color3, color4);
        endRender();
    }

    public static void drawGradientBlurredShadow1(MatrixStack matrices, float x, float y, float width, float height, int blurRadius, Color color1, Color color2, Color color3, Color color4) {
        width = width + blurRadius * 2;
        height = height + blurRadius * 2;
        x = x - blurRadius;
        y = y - blurRadius;

        int identifier = (int) (width * height + width * blurRadius);
        if (shadowCache1.containsKey(identifier)) {
            shadowCache1.get(identifier).bind();
        } else {
            BufferedImage original = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB);
            Graphics g = original.getGraphics();
            g.setColor(new Color(-1));
            g.fillRect(blurRadius, blurRadius, (int) (width - blurRadius * 2), (int) (height - blurRadius * 2));
            g.dispose();
            BufferedImage blurred = new GaussianFilter(blurRadius).filter(original, null);

            BufferedImage black = new BufferedImage((int) width + blurRadius * 2, (int) height + blurRadius * 2, BufferedImage.TYPE_INT_ARGB);
            Graphics g2 = black.getGraphics();
            g2.setColor(new Color(0x000000));
            g2.fillRect(0, 0, (int) width + blurRadius * 2, (int) height + blurRadius * 2);
            g2.dispose();

            BufferedImage combined = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB);
            Graphics g1 = combined.getGraphics();
            g1.drawImage(black, -blurRadius, -blurRadius, null);
            g1.drawImage(blurred, 0, 0, null);
            g1.dispose();

            shadowCache1.put(identifier, new BlurredShadow(combined));
            return;
        }

        setupRender();
        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE);
        renderGradientTexture(matrices, x, y, width, height, 0, 0, width, height, width, height, color1, color2, color3, color4);
        endRender();
    }


    public static void renderTexture(MatrixStack matrices, double x0, double y0, double width, double height, float u, float v, double regionWidth, double regionHeight, double textureWidth, double textureHeight) {
        double x1 = x0 + width;
        double y1 = y0 + height;
        double z = 0;
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        buffer.vertex(matrix, (float) x0, (float) y1, (float) z).texture((u) / (float) textureWidth, (v + (float) regionHeight) / (float) textureHeight);
        buffer.vertex(matrix, (float) x1, (float) y1, (float) z).texture((u + (float) regionWidth) / (float) textureWidth, (v + (float) regionHeight) / (float) textureHeight);
        buffer.vertex(matrix, (float) x1, (float) y0, (float) z).texture((u + (float) regionWidth) / (float) textureWidth, (v) / (float) textureHeight);
        buffer.vertex(matrix, (float) x0, (float) y0, (float) z).texture((u) / (float) textureWidth, (v + 0.0F) / (float) textureHeight);
        BufferRenderer.drawWithGlobalProgram(buffer.end());
    }

    public static void renderGradientTexture(MatrixStack matrices, double x0, double y0, double width, double height, float u, float v, double regionWidth, double regionHeight, double textureWidth, double textureHeight,
                                             Color c1, Color c2, Color c3, Color c4) {
        RenderSystem.setShader(GameRenderer::getPositionTexColorProgram);
        BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        renderGradientTextureInternal(buffer, matrices, x0, y0, width,height, u, v, regionWidth, regionHeight, textureWidth, textureHeight, c1, c2, c3, c4);
        BufferRenderer.drawWithGlobalProgram(buffer.end());
    }

    public static void renderGradientTextureInternal(BufferBuilder buff, MatrixStack matrices, double x0, double y0, double width, double height, float u, float v, double regionWidth, double regionHeight, double textureWidth, double textureHeight,
                                             Color c1, Color c2, Color c3, Color c4) {
        double x1 = x0 + width;
        double y1 = y0 + height;
        double z = 0;
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        buff.vertex(matrix, (float) x0, (float) y1, (float) z).texture((u) / (float) textureWidth, (v + (float) regionHeight) / (float) textureHeight).color(c1.getRGB());
        buff.vertex(matrix, (float) x1, (float) y1, (float) z).texture((u + (float) regionWidth) / (float) textureWidth, (v + (float) regionHeight) / (float) textureHeight).color(c2.getRGB());
        buff.vertex(matrix, (float) x1, (float) y0, (float) z).texture((u + (float) regionWidth) / (float) textureWidth, (v) / (float) textureHeight).color(c3.getRGB());
        buff.vertex(matrix, (float) x0, (float) y0, (float) z).texture((u) / (float) textureWidth, (v + 0.0F) / (float) textureHeight).color(c4.getRGB());
    }

    public static void renderRoundedGradientRect(MatrixStack matrices, Color color1, Color color2, Color color3, Color color4, float x, float y, float width, float height, float Radius) {
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        RenderSystem.colorMask(false, false, false, true);
        RenderSystem.clearColor(0.0F, 0.0F, 0.0F, 0.0F);
        RenderSystem.clear(GL40C.GL_COLOR_BUFFER_BIT, false);
        RenderSystem.colorMask(true, true, true, true);

        Render2DEngine.drawRound(matrices, x, y, width, height, Radius, color1);
        setupRender();
        RenderSystem.blendFunc(GL40C.GL_DST_ALPHA, GL40C.GL_ONE_MINUS_DST_ALPHA);
        BufferBuilder bufferBuilder = Tessellator.getInstance().begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix, x, y + height, 0.0F).color(color1.getRGB());
        bufferBuilder.vertex(matrix, x + width, y + height, 0.0F).color(color2.getRGB());
        bufferBuilder.vertex(matrix, x + width, y, 0.0F).color(color3.getRGB());
        bufferBuilder.vertex(matrix, x, y, 0.0F).color(color4.getRGB());
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
        endRender();
    }

    public static void drawRound(MatrixStack matrices, float x, float y, float width, float height, float radius, Color color) {
        renderRoundedQuad(matrices, color, x, y, width + x, height + y, radius, 4);
    }

    public static void renderRoundedQuad(MatrixStack matrices, Color c, double fromX, double fromY, double toX, double toY, double radius, double samples) {
        setupRender();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        renderRoundedQuadInternal(matrices.peek().getPositionMatrix(), c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha() / 255f, fromX, fromY, toX, toY, radius, samples);
        endRender();
    }

    public static void renderRoundedQuad2(MatrixStack matrices, Color c, Color c2, Color c3, Color c4, double fromX, double fromY, double toX, double toY, double radius) {
        setupRender();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        renderRoundedQuadInternal2(matrices.peek().getPositionMatrix(), c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha() / 255f, c2.getRed() / 255f, c2.getGreen() / 255f, c2.getBlue() / 255f, c2.getAlpha() / 255f, c3.getRed() / 255f, c3.getGreen() / 255f, c3.getBlue() / 255f, c3.getAlpha() / 255f, c4.getRed() / 255f, c4.getGreen() / 255f, c4.getBlue() / 255f, c4.getAlpha() / 255f, fromX, fromY, toX, toY, radius);
        endRender();
    }

    public static void renderRoundedQuadInternal(Matrix4f matrix, float cr, float cg, float cb, float ca, double fromX, double fromY, double toX, double toY, double radius, double samples) {
        BufferBuilder bufferBuilder = Tessellator.getInstance().begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
        double[][] map = new double[][]{new double[]{toX - radius, toY - radius, radius}, new double[]{toX - radius, fromY + radius, radius}, new double[]{fromX + radius, fromY + radius, radius}, new double[]{fromX + radius, toY - radius, radius}};
        for (int i = 0; i < 4; i++) {
            double[] current = map[i];
            double rad = current[2];
            for (double r = i * 90d; r < (360 / 4d + i * 90d); r += (90 / samples)) {
                float rad1 = (float) Math.toRadians(r);
                float sin = (float) (Math.sin(rad1) * rad);
                float cos = (float) (Math.cos(rad1) * rad);
                bufferBuilder.vertex(matrix, (float) current[0] + sin, (float) current[1] + cos, 0.0F).color(cr, cg, cb, ca);
            }
            float rad1 = (float) Math.toRadians((360 / 4d + i * 90d));
            float sin = (float) (Math.sin(rad1) * rad);
            float cos = (float) (Math.cos(rad1) * rad);
            bufferBuilder.vertex(matrix, (float) current[0] + sin, (float) current[1] + cos, 0.0F).color(cr, cg, cb, ca);
        }
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
    }

    public static void renderRoundedQuadInternal2(Matrix4f matrix, float cr, float cg, float cb, float ca, float cr1, float cg1, float cb1, float ca1, float cr2, float cg2, float cb2, float ca2, float cr3, float cg3, float cb3, float ca3, double fromX, double fromY, double toX, double toY, double radC1) {
        BufferBuilder bufferBuilder = Tessellator.getInstance().begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);

        double[][] map = new double[][]{new double[]{toX - radC1, toY - radC1, radC1}, new double[]{toX - radC1, fromY + radC1, radC1}, new double[]{fromX + radC1, fromY + radC1, radC1}, new double[]{fromX + radC1, toY - radC1, radC1}};

        for (int i = 0; i < 4; i++) {
            double[] current = map[i];
            double rad = current[2];
            for (double r = i * 90; r < (90 + i * 90); r += 10) {
                float rad1 = (float) Math.toRadians(r);
                float sin = (float) (Math.sin(rad1) * rad);
                float cos = (float) (Math.cos(rad1) * rad);
                switch (i) {
                    case 0 ->
                            bufferBuilder.vertex(matrix, (float) current[0] + sin, (float) current[1] + cos, 0.0F).color(cr1, cg1, cb1, ca1);
                    case 1 ->
                            bufferBuilder.vertex(matrix, (float) current[0] + sin, (float) current[1] + cos, 0.0F).color(cr, cg, cb, ca);
                    case 2 ->
                            bufferBuilder.vertex(matrix, (float) current[0] + sin, (float) current[1] + cos, 0.0F).color(cr2, cg2, cb2, ca2);
                    default ->
                            bufferBuilder.vertex(matrix, (float) current[0] + sin, (float) current[1] + cos, 0.0F).color(cr3, cg3, cb3, ca3);
                }
            }
        }
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
    }


    public static void draw2DGradientRect(MatrixStack matrices, float left, float top, float right, float bottom, Color leftBottomColor, Color leftTopColor, Color rightBottomColor, Color rightTopColor) {
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        setupRender();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        BufferBuilder bufferBuilder = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix, right, top, 0.0F).color(rightTopColor.getRGB());
        bufferBuilder.vertex(matrix, left, top, 0.0F).color(leftTopColor.getRGB());
        bufferBuilder.vertex(matrix, left, bottom, 0.0F).color(leftBottomColor.getRGB());
        bufferBuilder.vertex(matrix, right, bottom, 0.0F).color(rightBottomColor.getRGB());
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
        endRender();
    }

    private static void setupRender() {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
    }



    public static void drawDefaultArrow(MatrixStack matrices, float x, float y, float size, float tracerWidth, float downHeight, boolean down, boolean glow, int color) {
        if (glow)
            Render2DEngine.drawBlurredShadow(matrices, x - size * tracerWidth, y, (x + size * tracerWidth) - (x - size * tracerWidth), size, 10, ColorUtility.injectAlpha(new Color(color), 140));

        matrices.push();
        setupRender();
        Matrix4f matrix = matrices.peek().getPositionMatrix();

        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        BufferBuilder bufferBuilder = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix, x, y, 0.0F).color(color);
        bufferBuilder.vertex(matrix, (x - size * tracerWidth), (y + size), 0.0F).color(color);
        bufferBuilder.vertex(matrix, x, (y + size - downHeight), 0.0F).color(color);
        bufferBuilder.vertex(matrix, x, y, 0.0F).color(color);
        color = ColorUtility.darker(new Color(color), 0.8f).getRGB();
        bufferBuilder.vertex(matrix, x, y, 0.0F).color(color);
        bufferBuilder.vertex(matrix, x, (y + size - downHeight), 0.0F).color(color);
        bufferBuilder.vertex(matrix, (x + size * tracerWidth), (y + size), 0.0F).color(color);
        bufferBuilder.vertex(matrix, x, y, 0.0F).color(color);

        if (down) {
            color = ColorUtility.darker(new Color(color), 0.6f).getRGB();
            bufferBuilder.vertex(matrix, (x - size * tracerWidth), (y + size), 0.0F).color(color);
            bufferBuilder.vertex(matrix, (x + size * tracerWidth), (y + size), 0.0F).color(color);
            bufferBuilder.vertex(matrix, x, (y + size - downHeight), 0.0F).color(color);
            bufferBuilder.vertex(matrix, (x - size * tracerWidth), (y + size), 0.0F).color(color);
        }

        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
        endRender();
        matrices.pop();
    }

    private static void endRender() {
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
    }

    public static void drawGradientRound(MatrixStack ms, float v, float v1, float i, float i1, float v2, Color darker, Color darker1, Color darker2, Color darker3) {
        renderRoundedQuad2(ms, darker, darker1, darker2, darker3, v, v1, v + i, v1 + i1, v2);
    }

    private static BufferBuilder preShaderDraw(MatrixStack matrices, float x, float y, float width, float height) {
        setupRender();
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);
        setRectanglePoints(buffer, matrix, x, y, x + width, y + height);
        return buffer;
    }

    private static void setRectanglePoints(BufferBuilder buffer, Matrix4f matrix, float x, float y, float x1, float y1) {
        buffer.vertex(matrix, x, y, 0);
        buffer.vertex(matrix, x, y1, 0);
        buffer.vertex(matrix, x1, y1, 0);
        buffer.vertex(matrix, x1, y, 0);
    }

    public static void drawLine(float x, float y, float x1, float y1, int color) {
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        BufferBuilder bufferBuilder = Tessellator.getInstance().begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(x, y, 0f).color(color);
        bufferBuilder.vertex(x1, y1, 0f).color(color);
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
    }


    public static void registerBufferedImageTexture(Texture i, BufferedImage bi) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "png", baos);
            byte[] bytes = baos.toByteArray();
            registerTexture(i, bytes);
        } catch (Exception ignored) {
        }
    }
    public static void registerTexture(Texture i, byte[] content) {
        try {
            ByteBuffer data = BufferUtils.createByteBuffer(content.length).put(content);
            data.flip();
            NativeImageBackedTexture tex = new NativeImageBackedTexture(NativeImage.read(data));
            mc.execute(() -> mc.getTextureManager().registerTexture(i.getId(), tex));
        } catch (Exception ignored) {
        }
    }

    private static class BlurredShadow {
        Texture id;

        public BlurredShadow(BufferedImage bufferedImage) {
            this.id = new Texture("texture/remote/" + RandomStringUtils.randomAlphanumeric(16));
            registerBufferedImageTexture(id, bufferedImage);
        }

        public void bind() {
            RenderSystem.setShaderTexture(0, id.getId());
        }
    }

    private record Rectangle(float x, float y, float x1, float y1) {
        public boolean contains(double x, double y) {
            return x >= this.x && x <= x1 && y >= this.y && y <= y1;
        }
    }
}
