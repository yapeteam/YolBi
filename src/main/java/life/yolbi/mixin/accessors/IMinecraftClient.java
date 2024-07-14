package life.yolbi.mixin.accessors;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 * @author yuxiangll
 * @since 2024/7/14 下午5:55
 * IntelliJ IDEA
 */
@Mixin(MinecraftClient.class)
public interface IMinecraftClient {

    @Accessor("itemUseCooldown")
    int getUseCooldown();

    @Accessor("itemUseCooldown")
    void setUseCooldown(int val);

    @Invoker("doItemUse")
    void idoItemUse();

    @Invoker("doAttack")
    boolean idoAttack();

}
