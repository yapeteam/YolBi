package life.yolbi.managers

import life.yolbi.YolBi4
import life.yolbi.module.Module
import life.yolbi.module.imp.combat.*
import life.yolbi.module.imp.render.*

/**
 * @author yuxiangll
 * @since 2024/7/6 上午9:42
 * IntelliJ IDEA
 */

@Suppress("MemberVisibilityCanBePrivate")
object ModuleManager {


    val modules = mutableListOf<Module>()



    fun initialize(){
        modules.add(AutoClicker)
        modules.add(ClickGui)
        modules.add(TESTTT)
        YolBi4.EventBus.subscribe(this)
    }






}