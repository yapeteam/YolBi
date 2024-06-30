package today.yapeteam.module

import today.yapeteam.YolBi
import today.yapeteam.utils.IMinecraftClient

/**
 * @author yuxiangll
 * @since 2024/6/7 上午8:08
 * IntelliJ IDEA
 */
abstract class Module(val name: String, val category: Category, val key: Bind = Bind(0,BindType.PreClick)) : IMinecraftClient{

    var enable: Boolean = false
    var prefix: String = ""
    var bind: BindSetting = BindSetting(name,"Binding",key)

    private val settings: MutableList<Settings> = mutableListOf()

     open fun onEnable(){

     }

    open fun onDisable(){

    }

    fun addSetting(setting: Settings) {
        settings.add(setting)
    }

    fun addSetting(setting: MutableList<Settings>){
        settings.addAll(setting)
    }

    fun toggle(){
        if(enable)
            disable()
        else
            enable()

    }
    fun toggle(enable: Boolean) {
        if(enable)
            enable()
        else
            disable()

    }

    private fun enable(){
        enable = true
        YolBi.EVENT_BUG.subscribe(this)
        onEnable()
    }

    private fun disable(){
        enable = false
        YolBi.EVENT_BUG.unsubscribe(this)
        onDisable()

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

