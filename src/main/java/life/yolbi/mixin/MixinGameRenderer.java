package life.yolbi.mixin;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author yuxiangll
 * @since 2024/7/5 下午7:45
 * IntelliJ IDEA
 */

@Mixin(GameRenderer.class)
@SuppressWarnings("SpellCheckingInspection")
public abstract class MixinGameRenderer {

    @Inject(at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/GameRenderer;renderHand:Z", opcode = Opcodes.GETFIELD, ordinal = 0), method = "renderWorld")
    void renderWorld(RenderTickCounter tickCounter, CallbackInfo ci) {
        //todo all undone
    }


}
