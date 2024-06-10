package today.yapeteam.module

import meteordevelopment.orbit.EventHandler
import today.yapeteam.YolBi4
import today.yapeteam.event.EventKeyHold
import today.yapeteam.event.EventKeyPress
import today.yapeteam.event.EventKeyRelease
import today.yapeteam.module.imp.render.ClickGUI
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
        modules.add(ClickGUI)
        //modules.sortByDescending { YolBi4.fontManager.getPingFang22().getStringWidth("${it.name}+${it.prefix}") }
    }

    @EventHandler
    fun onKeyPress(event: EventKeyPress){
        modules.forEach {
            if (it.bind.value.key == event.key){
                if (it.bind.value.type == BindType.PreClick){
                    it.toggle()
                }else if (it.bind.value.type == BindType.Hold && !it.enable){
                    it.toggle(true)
                }

            }
        }

    }

    @EventHandler
    fun onKeyRelease(event: EventKeyRelease){
        modules.forEach {
            if (it.bind.value.key == event.key){
                if (it.bind.value.type == BindType.PostClick){
                    it.toggle()
                }else if (it.bind.value.type == BindType.Hold && it.enable){
                    it.toggle(false)
                }
            }
        }
    }



}



fun main(){



}