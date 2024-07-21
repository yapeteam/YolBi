package life.yolbi.module.imp.render

import life.yolbi.module.Category
import life.yolbi.module.Module
import net.minecraft.component.DataComponentTypes
import net.minecraft.entity.LivingEntity
import net.minecraft.item.AxeItem
import net.minecraft.item.MaceItem
import net.minecraft.item.ShieldItem
import net.minecraft.item.SwordItem
import java.util.*

/**
 * @author yuxiangll
 * @since 2024/7/20 下午9:45
 * IntelliJ IDEA
 */
object SwardBlock : Module(
    "SwardBlock",
    Category.RENDER,
) {

    var hideShield: Boolean = true
    var alwaysHideShield: Boolean = false
    var hideOffhandSlot: Boolean = false

    fun isWeaponBlocking(entity: LivingEntity): Boolean {
        return entity.isUsingItem && (canWeaponBlock(entity) || isBlockingOnViaVersion(entity))
    }

    fun canWeaponBlock(entity: LivingEntity): Boolean {
        if (enable && (entity.offHandStack.item is ShieldItem || entity.mainHandStack.item is ShieldItem)) {
            val weaponItem =
                if (entity.offHandStack.item is ShieldItem) entity.mainHandStack.item else entity.offHandStack.item
            return weaponItem is SwordItem || weaponItem is AxeItem || weaponItem is MaceItem
        }
        return false
    }

    fun isBlockingOnViaVersion(entity: LivingEntity): Boolean {
        val item = if (entity.mainHandStack.item is SwordItem) entity.mainHandStack.item else entity.offHandStack.item
        return item is SwordItem && item.getComponents() != null && item.getComponents()
            .contains(DataComponentTypes.FOOD) && Objects.requireNonNull(
            item.getComponents().get(DataComponentTypes.FOOD)
        )!!.eatSeconds() == 3600f
    }
    


}