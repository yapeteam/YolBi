package today.yapeteam.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import today.yapeteam.YolBi;
import today.yapeteam.event.EventWindowsResize;


/**
 * @author yuxiangll
 * @since 2024/6/7 下午1:50
 * IntelliJ IDEA
 */

@Mixin(MinecraftClient.class)
abstract public class MixinMinecraftClient {

    @Shadow
    @Final
    private Window window;

    @Inject(method = "onResolutionChanged", at = @At("TAIL"))
    private void captureResize(CallbackInfo ci) {
        EventWindowsResize eventWindowsResize = new EventWindowsResize(window);
        YolBi.INSTANCE.getEVENT_BUG().post(eventWindowsResize);
    }

    @Inject(method = "onFinishedLoading", at = @At("TAIL"))
    private void loadingFinished(CallbackInfo ci) {
        YolBi.INSTANCE.getFontManager().initialize();
    }

}
