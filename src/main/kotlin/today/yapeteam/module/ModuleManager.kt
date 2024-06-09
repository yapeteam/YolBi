package today.yapeteam.module

import meteordevelopment.orbit.EventHandler
import today.yapeteam.YolBi4
import today.yapeteam.event.EventKeyPress
import today.yapeteam.event.EventKeyRelease
import today.yapeteam.font.FontRenderers
import today.yapeteam.module.imp.render.HUD
import today.yapeteam.module.imp.render.RenderTest


/**
 * @author yuxiangll
 * @since 2024/6/7 上午8:54
 * IntelliJ IDEA
 */
class ModuleManager {
    val modules = mutableListOf<Module>()



    fun initialize(){
        YolBi4.EVENT_BUG.subscribe(this)
        modules.add(RenderTest)
        modules.add(HUD)

        //modules.sortByDescending { YolBi4.fontManager.getPingFang22().getStringWidth("${it.name}+${it.prefix}") }
    }

    @EventHandler
    fun onKeyPress(event: EventKeyPress){
        if (event.key == 82){
            HUD.toggle()
        }
        //println(event.key)
    }

    @EventHandler
    fun onKeyRelease(event: EventKeyRelease){

    }

}



fun main(){



}