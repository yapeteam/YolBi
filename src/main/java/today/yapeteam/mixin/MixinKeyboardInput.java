package today.yapeteam.mixin;

import net.minecraft.client.input.KeyboardInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import today.yapeteam.YolBi4;
import today.yapeteam.event.EventKeyboardInput;

/**
 * @author yuxiangll
 * @since 2024/6/7 下午1:54
 * IntelliJ IDEA
 */
@Mixin(KeyboardInput.class)
abstract public class MixinKeyboardInput {

    @Inject(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/client/input/KeyboardInput;sneaking:Z", shift = At.Shift.BEFORE), cancellable = true)
    private void onSneak(boolean slowDown, float slowDownFactor, CallbackInfo ci) {
        EventKeyboardInput eventKeyboardInput = new EventKeyboardInput();
        YolBi4.INSTANCE.getEVENT_BUG().post(eventKeyboardInput);
        if (eventKeyboardInput.getCancelled()){
            ci.cancel();
        }
    }


}
