package life.yolbi.mixin;


import life.yolbi.YolBi4;
import life.yolbi.events.EventClientTick;
import life.yolbi.events.EventPreAttack;
import life.yolbi.managers.FontManager;
import life.yolbi.util.IMinecraftClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author yuxiangll
 * @since 2024/7/5 下午8:31
 * IntelliJ IDEA
 */
@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient implements IMinecraftClient {


    @Inject(method = "tick", at = @At("HEAD"))
    public void onTick(CallbackInfo ci){
        //if (mc.player == null) return;
        YolBi4.INSTANCE.getEventBus().post(new EventClientTick());
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    void postWindowInit(RunArgs args, CallbackInfo ci) {

    }

    @Inject(method = "onFinishedLoading", at = @At("TAIL"))
    private void loadingFinished(CallbackInfo ci) {
        FontManager.initialize();
    }

    @Inject(method = "tick", at = @At("HEAD"))
    void preTickHook(CallbackInfo ci) {
        YolBi4.INSTANCE.getEventBus().post(new EventClientTick());
    }


    @Inject(method = "doAttack", at = @At("HEAD"), cancellable = true)
    private void doAttackHook(CallbackInfoReturnable<Boolean> cir) {
        final EventPreAttack event = new EventPreAttack();
        YolBi4.INSTANCE.getEventBus().post(event);
        if (event.isCancelled()) {
            cir.setReturnValue(false);
        }
    }




}
