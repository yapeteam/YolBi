package life.yolbi.util

import net.minecraft.client.MinecraftClient

/**
 * @author yuxiangll
 * @since 2024/7/5 下午7:29
 * IntelliJ IDEA
 */
interface IMinecraft {
    val mc: MinecraftClient
        get() = MinecraftClient.getInstance()

}