package today.yapeteam.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import today.yapeteam.YolBi4;
import today.yapeteam.event.EventRender2D;

/**
 * @author yuxiangll
 * @since 2024/6/6 下午10:52
 * IntelliJ IDEA
 */
@Mixin(InGameHud.class)
public abstract class MixinIngameHud {
    @Inject(at = @At(value = "HEAD"), method = "render", cancellable = true)
    public void render(DrawContext context, float tickDelta, CallbackInfo callback) {

        EventRender2D eventRender2D = new EventRender2D(context,tickDelta);
        YolBi4.INSTANCE.getEVENT_BUG().post(eventRender2D);
        if (eventRender2D.getCancelled()) callback.cancel();

        //context.drawText(MinecraftClient.getInstance().textRenderer, "ABCDSACACSC",100,100,-1,true);
    }
}
