package life.yolbi.mixin;

import life.yolbi.YolBi4;
import life.yolbi.events.EventAttackBlock;
import life.yolbi.events.EventBreakBlock;
import life.yolbi.util.IMinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author yuxiangll
 * @since 2024/7/8 下午3:12
 * IntelliJ IDEA
 */
@Mixin(ClientPlayerInteractionManager.class)
abstract public class MixinClientPlayerInteractionManager implements IMinecraftClient {


    @Inject(method = "attackBlock", at = @At("HEAD"), cancellable = true)
    private void attackBlockHook(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if(mc.player == null || mc.world == null) return;

        EventAttackBlock event = new EventAttackBlock(pos, direction);
        YolBi4.INSTANCE.getEventBus().post(event);
        if (event.isCancelled())
            cir.setReturnValue(false);
    }



    @Inject(method = "breakBlock", at = @At("HEAD"), cancellable = true)
    public void breakBlockHook(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if(mc.player == null || mc.world == null) return;
        EventBreakBlock event = new EventBreakBlock(pos);
        YolBi4.INSTANCE.getEventBus().post(event);
        if (event.isCancelled())
            cir.setReturnValue(false);
    }


}
