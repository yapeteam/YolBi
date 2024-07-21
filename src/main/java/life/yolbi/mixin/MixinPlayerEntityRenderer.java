package life.yolbi.mixin;

import life.yolbi.module.imp.render.SwardBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author yuxiangll
 * @since 2024/7/20 下午9:42
 * IntelliJ IDEA
 */



@Mixin(PlayerEntityRenderer.class)
public abstract class MixinPlayerEntityRenderer {
    @Inject(at = @At(value = "RETURN"), method = "getArmPose", cancellable = true)
    @Environment(EnvType.CLIENT)
    private static void swordblocking$getArmPose(AbstractClientPlayerEntity player, Hand hand, CallbackInfoReturnable<BipedEntityModel.ArmPose> cir) {
        if (!SwardBlock.INSTANCE.getEnable()) return;

        ItemStack handStack = player.getStackInHand(hand);
        ItemStack offStack = player.getStackInHand(hand.equals(Hand.MAIN_HAND) ? Hand.OFF_HAND : Hand.MAIN_HAND);
        if (!SwardBlock.INSTANCE.getAlwaysHideShield() && (handStack.getItem() instanceof ShieldItem) && !SwardBlock.INSTANCE.canWeaponBlock(player))
            return;

        if (offStack.getItem() instanceof ShieldItem && SwardBlock.INSTANCE.isWeaponBlocking(player)) {
            cir.setReturnValue(BipedEntityModel.ArmPose.BLOCK);
        } else if (handStack.getItem() instanceof ShieldItem && SwardBlock.INSTANCE.getHideShield() && (cir.getReturnValue() == BipedEntityModel.ArmPose.ITEM || cir.getReturnValue() == BipedEntityModel.ArmPose.BLOCK)) {
            cir.setReturnValue(BipedEntityModel.ArmPose.EMPTY);
        }
    }
}
