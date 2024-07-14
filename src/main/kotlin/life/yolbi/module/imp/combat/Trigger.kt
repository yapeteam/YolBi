package life.yolbi.module.imp.combat

import life.yolbi.events.EventPostPlayerUpdate
import life.yolbi.events.EventPrePlayerUpdate
import life.yolbi.mixin.accessors.IMinecraftClient
import life.yolbi.module.Category
import life.yolbi.module.Module
import meteordevelopment.orbit.EventHandler
import net.minecraft.util.Hand
import net.minecraft.util.hit.EntityHitResult

/**
 * @author yuxiangll
 * @since 2024/7/14 下午5:14
 * IntelliJ IDEA
 */
object Trigger : Module("Trigger",Category.COMBAT) {



    @EventHandler
    fun onAttack(event: EventPostPlayerUpdate){


        if ( mc.crosshairTarget == null) return
        if (mc.player?.getAttackCooldownProgress(0.5f)!! < 1f) return
        val ent = mc.crosshairTarget
        when (ent){
            is EntityHitResult -> {
                mc.interactionManager?.attackEntity(mc.player,ent.entity)
                mc.player?.swingHand(Hand.MAIN_HAND)
            }
        }



    }


}