package thunder.hack.gui.font;

import org.jetbrains.annotations.NotNull;
import today.yapeteam.YolBi;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class FontRenderers {

    public static FontRenderer minecraft;
    public static FontRenderer pingFang;

    
    public static @NotNull FontRenderer create(float size, String name) throws IOException, FontFormatException {
        return new FontRenderer(Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(YolBi.class.getClassLoader().getResourceAsStream("assets/yolbi/fonts/" + name + ".ttf"))).deriveFont(Font.PLAIN, size / 2f), size / 2f);
    }
}
