package today.yapeteam.mixin;

import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import today.yapeteam.YolBi4;
import today.yapeteam.event.EventKeyHold;
import today.yapeteam.event.EventKeyPress;
import today.yapeteam.event.EventKeyRelease;

/**
 * @author yuxiangll
 * @since 2024/6/7 下午2:01
 * IntelliJ IDEA
 */
@Mixin(Keyboard.class)
abstract public class MixinKeyboard {
    @Final
    @Shadow
    private MinecraftClient client;


    @Inject(method = "onKey", at = @At(value = "HEAD"), cancellable = true)
    private void onKey(long window, int key, int scanCode, int action, int modifiers, CallbackInfo ci) {
        if (window != this.client.getWindow().getHandle()) {
            ci.cancel();
            return;
        }

        switch (action) {
            case 0 -> {
                EventKeyRelease eventKeyRelease = new EventKeyRelease(key,scanCode);
                YolBi4.INSTANCE.getEVENT_BUG().post(eventKeyRelease);
                if (eventKeyRelease.getCancelled()) ci.cancel();

            }
            case 1 ->{
                EventKeyPress eventKeyPress = new EventKeyPress(key,scanCode);
                YolBi4.INSTANCE.getEVENT_BUG().post(eventKeyPress);
                if (eventKeyPress.getCancelled()) ci.cancel();
            }
            case 2 ->{
                EventKeyHold eventKeyHold = new EventKeyHold(key,scanCode);
                YolBi4.INSTANCE.getEVENT_BUG().post(eventKeyHold);
                if (eventKeyHold.getCancelled()) ci.cancel();
            }
        }

    }

}
