package life.yolbi.surveillance


import life.yolbi.managers.ModuleManager
import life.yolbi.events.EventKeyPress
import life.yolbi.events.EventKeyRelease
import life.yolbi.module.Bind
import life.yolbi.module.BindType
import meteordevelopment.orbit.EventHandler

/**
 * @author yuxiangll
 * @since 2024/7/6 上午9:53
 * IntelliJ IDEA
 */


object ModuleSurveillance {
    private val modules = ModuleManager.modules

    @EventHandler
    fun onKeyPress(event: EventKeyPress){

        modules.forEach {
            if ((it.bind.value as Bind).key == event.key){
                if ((it.bind.value as Bind).type == BindType.PreClick){
                    it.toggle()
                }else if ((it.bind.value as Bind).type == BindType.Hold && !it.enable){
                    it.toggle(true)
                }

            }
        }

    }

    @EventHandler
    fun onKeyRelease(event: EventKeyRelease){
        modules.forEach {
            if ((it.bind.value as Bind).key == event.key){
                if ((it.bind.value as Bind).type == BindType.PostClick){
                    it.toggle()
                }else if ((it.bind.value as Bind).type == BindType.Hold && it.enable){
                    it.toggle(false)
                }
            }
        }
    }




}