package life.yolbi.module

import java.awt.Color

/**
 * @author yuxiangll
 * @since 2024/7/6 上午9:37
 * IntelliJ IDEA
 */


sealed class Settings(val name: String, val description: String = "", val value: Any, val visible: ()->Boolean = { true })

class BindSetting(name: String, description: String = "", value: Bind, visible: () -> Boolean = { true }) : Settings(name,description,value,visible)

class BooleanSetting(name: String, description: String = "", value: Boolean, visible: () -> Boolean = { true }) : Settings(name,description,value,visible)

class ColorSetting(name: String, description: String = "", value: Color, visible: () -> Boolean = { true }) : Settings(name,description,value,visible)

class NumberSetting(name: String, description: String = "",range: ClosedRange<*>, value: Number, visible: () -> Boolean = { true }) : Settings(name,description,value,visible)

class SingleModeSetting(name: String,description: String = "", )

data class Bind(val key: Int, val type: BindType = BindType.PreClick)

enum class BindType {
    PreClick,  // 0 按下切换
    PostClick, // 1 松开切换
    Hold,      // 2 长按开启，松开关闭
}