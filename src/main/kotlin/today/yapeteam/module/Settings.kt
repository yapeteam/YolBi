package today.yapeteam.module

import java.awt.Color


/**
 * @author yuxiangll
 * @since 2024/6/9 下午4:22
 * IntelliJ IDEA
 */




sealed class Settings(val name: String, val description: String = "", val value: Any, val visible: ()->Boolean = { true })

class BindSetting(name: String, description: String = "", value: Bind, visible: () -> Boolean = { true }) : Settings(name,description,value,visible)

class BooleanSetting(name: String, description: String = "", value: Boolean, visible: () -> Boolean = { true }) : Settings(name,description,value,visible)

class ColorSetting(name: String, description: String = "", value: Color, visible: () -> Boolean = { true }) : Settings(name,description,value,visible)

class NumberSetting(name: String, description: String = "", value: Number, visible: () -> Boolean = { true }) : Settings(name,description,value,visible)

class SingleModeSetting(name: String,description: String = "", )

data class Bind(val key: Int, val type: BindType){
    constructor(key: Int) : this(key,BindType.PreClick)
}

enum class BindType {
    PreClick,  // 0
    PostClick, // 1
    Hold,      // 2
}