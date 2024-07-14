package life.yolbi.mixin;

import life.yolbi.YolBi4;
import life.yolbi.events.EventKeyPress;
import life.yolbi.events.EventKeyRelease;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author yuxiangll
 * @since 2024/7/5 下午7:48
 * IntelliJ IDEA
 */
@Mixin(Keyboard.class)
public abstract class MixinKeyBoard {

    @Inject(method = "onKey", at = @At("HEAD"))
    public void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo callback) {
        switch (action) {
            case 0 -> YolBi4.INSTANCE.getEventBus().post(new EventKeyRelease(key,scancode));
            case 1 -> YolBi4.INSTANCE.getEventBus().post(new EventKeyPress(key,scancode));
        }
    }


}
