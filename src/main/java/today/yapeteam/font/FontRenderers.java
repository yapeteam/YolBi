package today.yapeteam.font;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FontRenderers {
    private static final List<RendererFontAdapter> fontRenderers = new ArrayList<>();
    private static FontAdapter normal;


    public static FontAdapter getRenderer() {
        return normal;
    }

    public static void setRenderer(FontAdapter normal) {
        FontRenderers.normal = normal;
    }


    public static FontAdapter createFont(String url, int size) {
        int v = size * 2;
        FontAdapter fontAdapter;
        try {
            fontAdapter = new RendererFontAdapter(Font.createFont(
                    Font.TRUETYPE_FONT,
                    Objects.requireNonNull(FontRenderers.class.getClassLoader().getResourceAsStream(url))
            ).deriveFont(Font.PLAIN, v), v);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    return fontAdapter;
    }




}
