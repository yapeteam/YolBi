package today.yapeteam.module

import today.yapeteam.YolBi4
import today.yapeteam.utils.IMinecraftClient

/**
 * @author yuxiangll
 * @since 2024/6/7 上午8:08
 * IntelliJ IDEA
 */
abstract class Module(val name: String, val category: Category) : IMinecraftClient{

    private var enable: Boolean = false
    var prefix: String = ""
    private val settings: MutableList<Settings<Any>> = mutableListOf()

    open fun onEnable() {

    }

    open fun onDisable(){

    }

    fun addSetting(setting: Settings<Any>) {
        settings.add(setting)
    }

    fun addSetting(setting: MutableList<Settings<Any>>) {
        settings.addAll(setting)
    }

    fun toggle(){
        if(enable)
            disable()
        else
            enable()

    }

    private fun enable(){
        enable = true
        YolBi4.EVENT_BUG.subscribe(this)
    }

    private fun disable(){
        enable = false
        YolBi4.EVENT_BUG.unsubscribe(this)
    }


}

enum class Category(val n: String) {
    COMBAT("Combat"),
    MISC("Misc"),
    RENDER("Render"),
    MOVEMENT("Movement"),
    PLAYER("Player"),
    CLIENT("Client")
}

