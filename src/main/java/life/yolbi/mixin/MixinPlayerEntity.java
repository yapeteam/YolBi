package life.yolbi.mixin;

import life.yolbi.YolBi4;
import life.yolbi.events.EventPlayerJump;
import life.yolbi.events.EventPlayerTravel;
import life.yolbi.events.EventPostAttack;
import life.yolbi.util.IMinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author yuxiangll
 * @since 2024/7/5 下午8:33
 * IntelliJ IDEA
 */
@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity extends LivingEntity implements IMinecraftClient {

    protected MixinPlayerEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }


    @Inject(method = {"travel"}, at = {@At("HEAD")}, cancellable = true)
    private void travel(Vec3d movement, CallbackInfo info) {
        EventPlayerTravel eventPlayerTravel = new EventPlayerTravel(movement.getX(), movement.getY(), movement.getZ());
        YolBi4.INSTANCE.getEventBus().post(eventPlayerTravel);
        if (eventPlayerTravel.isCancelled()) info.cancel();
    }

    @Inject(method = {"jump"}, at = {@At("HEAD")})
    private void jump(CallbackInfo callback) {
        if (mc.player == null) return;
        YolBi4.INSTANCE.getEventBus().post(new EventPlayerJump());
    }



    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    private void attackAHook2(Entity target, CallbackInfo ci) {
        final EventPostAttack event = new EventPostAttack(target);
        YolBi4.INSTANCE.getEventBus().post(event);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }






}
