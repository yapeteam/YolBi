package life.yolbi.mixin;

import life.yolbi.YolBi4;
import life.yolbi.events.EventRenderGameOverlay;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author yuxiangll
 * @since 2024/7/5 下午8:28
 * IntelliJ IDEA
 */
@Mixin(InGameHud.class)
public abstract class MixinIngameHud {

    @Inject(at = @At(value = "RETURN"), method = "render", cancellable = true)
    public void render(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        EventRenderGameOverlay eventRenderGameOverlay = new EventRenderGameOverlay(context, tickCounter);
        YolBi4.INSTANCE.getEventBus().post(eventRenderGameOverlay);
        if (eventRenderGameOverlay.isCancelled()) ci.cancel();
    }

}
