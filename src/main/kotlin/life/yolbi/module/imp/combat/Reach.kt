package life.yolbi.module.imp.combat

import life.yolbi.module.Category
import life.yolbi.module.Module
import life.yolbi.module.NumberSetting

/**
 * @author yuxiangll
 * @since 2024/7/14 下午4:35
 * IntelliJ IDEA
 */

object Reach : Module("Reach", Category.COMBAT) {
    val blockRange = NumberSetting("BlockRange","Modify your range",1.0..100.0,50.0)
    val attackRange = NumberSetting("AttackRange","Modify your range",1.0..100.0,50.0)


    init {
        this.addSetting(blockRange)
        this.addSetting(attackRange)
    }
}