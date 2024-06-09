package today.yapeteam.utils

import net.minecraft.client.MinecraftClient

/**
 * @author yuxiangll
 * @since 2024/6/7 下午8:42
 * IntelliJ IDEA
 */
interface IMinecraftClient {
    val mc: MinecraftClient
        get() = MinecraftClient.getInstance()
}