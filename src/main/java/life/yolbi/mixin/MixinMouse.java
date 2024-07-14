package life.yolbi.mixin;

import life.yolbi.YolBi4;
import life.yolbi.events.EventMouseButton;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author yuxiangll
 * @since 2024/7/5 下午8:32
 * IntelliJ IDEA
 */
@Mixin(Mouse.class)
public abstract class MixinMouse {

    @Inject(method = {"onMouseButton"}, at = {@At("HEAD")})
    private void onMouseButton(long window, int button, int action, int mods, CallbackInfo callback) {
        YolBi4.INSTANCE.getEventBus().post(new EventMouseButton(button, action));
    }

    @Inject(method = {"onMouseScroll"}, at = {@At("HEAD")})
    private void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo info) {
    }


}
