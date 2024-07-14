package life.yolbi.mixin;

import com.mojang.authlib.GameProfile;
import life.yolbi.YolBi4;
import life.yolbi.events.EventPlayerMove;
import life.yolbi.events.EventPostPlayerUpdate;
import life.yolbi.events.EventPrePlayerUpdate;
import life.yolbi.util.IMinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.MovementType;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author yuxiangll
 * @since 2024/7/5 下午8:18
 * IntelliJ IDEA
 */
@Mixin(ClientPlayerEntity.class)
public abstract class MixinClientPlayerEntity extends AbstractClientPlayerEntity implements IMinecraftClient {


    @Unique
    private boolean updateLock = false;

    @Shadow
    protected abstract void sendMovementPackets();



    public MixinClientPlayerEntity(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void onPlayerTick(CallbackInfo ci){
        if (mc.player == null) return;
        YolBi4.INSTANCE.getEventBus().post(new EventPrePlayerUpdate());
    }

    @Inject(method = "move", at = @At("HEAD"), cancellable = true)
    public void move$Inject$HEAD(MovementType type, Vec3d movement, CallbackInfo p_Info) {
        EventPlayerMove eventPlayerMove = new EventPlayerMove(type, movement.x, movement.y, movement.z);
        YolBi4.INSTANCE.getEventBus().post(eventPlayerMove);
        if (eventPlayerMove.isCancelled())
        {
            super.move(type, new Vec3d(eventPlayerMove.getX(), eventPlayerMove.getY(), eventPlayerMove.getZ()));
            p_Info.cancel();
        }
    }


    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;sendMovementPackets()V", ordinal = 0, shift = At.Shift.AFTER), cancellable = true)
    private void PostUpdateHook(CallbackInfo info) {
        if(mc.player == null || mc.world == null) return;
        if (updateLock) {
            return;
        }
        EventPostPlayerUpdate playerUpdateEvent = new EventPostPlayerUpdate();
        YolBi4.INSTANCE.getEventBus().post(playerUpdateEvent);
        if (playerUpdateEvent.isCancelled()) {
            info.cancel();
            if (playerUpdateEvent.getIterations() > 0) {
                for (int i = 0; i < playerUpdateEvent.getIterations(); i++) {
                    updateLock = true;
                    tick();
                    updateLock = false;
                    sendMovementPackets();
                }
            }
        }
    }

}
