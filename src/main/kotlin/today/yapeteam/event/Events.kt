package today.yapeteam.event

import net.minecraft.client.gui.DrawContext
import net.minecraft.client.util.Window

/**
 * @author yuxiangll
 * @since 2024/6/7 上午7:43
 * IntelliJ IDEA
 */

data class EventRender2D(val context: DrawContext, val partialTicks: Float) : Event()

data class EventKeyPress(val key: Int, val scanCode: Int) : Event()

data class EventKeyHold(val key: Int, val scanCode: Int) : Event()

data class EventKeyRelease(val key: Int, val scanCode: Int) : Event()

data class EventWindowsResize(val windows:Window) : Event()

class EventKeyboardInput() : Event()

