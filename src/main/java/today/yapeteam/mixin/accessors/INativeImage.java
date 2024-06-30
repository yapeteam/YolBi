package today.yapeteam.mixin.accessors;

import net.minecraft.client.texture.NativeImage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author yuxiangll
 * @since 2024/6/30 上午8:38
 * IntelliJ IDEA
 */

@Mixin(NativeImage.class)
public interface INativeImage {
    @Accessor("pointer")
    long getPointer();
}
