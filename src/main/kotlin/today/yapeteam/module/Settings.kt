package today.yapeteam.module

import java.awt.Color


/**
 * @author yuxiangll
 * @since 2024/6/9 下午4:22
 * IntelliJ IDEA
 */




abstract class Settings<T>(val name: String, val description: String = "", val value: T, val visible: ()->Boolean = { true })

class BindSetting(name: String, description: String = "", value: Bind, visible: () -> Boolean) : Settings<Bind>(name,description,value,visible)

class BooleanSetting(name: String, description: String = "", value: Boolean, visible: () -> Boolean) : Settings<Boolean>(name,description,value,visible)

class ColorSetting(name: String, description: String = "", value: Color, visible: () -> Boolean) : Settings<Color>(name,description,value,visible)

class NumberSetting(name: String, description: String = "", value: Number, visible: () -> Boolean) : Settings<Number>(name,description,value,visible)

class SingleModeSetting(name: String,description: String = "", )

data class Bind(val key: Int, val type: BindType)

enum class BindType {
    PreClick,  // 0
    PostClick, // 1
    Hold,      // 2
}