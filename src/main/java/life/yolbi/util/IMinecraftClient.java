package life.yolbi.util;

import net.minecraft.client.MinecraftClient;

/**
 * @author yuxiangll
 * @since 2024/7/5 下午8:21
 * IntelliJ IDEA
 */
public interface IMinecraftClient {
    MinecraftClient mc = MinecraftClient.getInstance();

//    default boolean fullNullCheck() {
//        return mc.player == null || mc.world == null ;
//    }

}
