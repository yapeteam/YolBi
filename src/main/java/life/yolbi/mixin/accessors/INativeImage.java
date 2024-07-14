package life.yolbi.mixin.accessors;

import net.minecraft.client.texture.NativeImage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author yuxiangll
 * @since 2024/7/8 上午9:55
 * IntelliJ IDEA
 */

@Mixin(NativeImage.class)
public interface INativeImage {
    @Accessor("pointer")
    long getPointer();


}
