package life.yolbi.module.imp.world

import life.yolbi.events.EventPrePlayerUpdate
import life.yolbi.mixin.accessors.IMinecraftClient
import life.yolbi.module.Category
import life.yolbi.module.Module
import meteordevelopment.orbit.EventHandler

/**
 * @author yuxiangll
 * @since 2024/7/14 下午5:53
 * IntelliJ IDEA
 */
object FastPlace : Module("FastPlace",Category.WORLD) {


    @EventHandler
    fun onPlace(event: EventPrePlayerUpdate) {
        (mc as IMinecraftClient ).useCooldown = 0
    }


}