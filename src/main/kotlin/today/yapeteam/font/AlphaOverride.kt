package today.yapeteam.font

import java.util.Stack

/**
 * @author yuxiangll
 * @since 2024/6/10 上午7:21
 * IntelliJ IDEA
 */

object AlphaOverride {
    val alphaMultipliers: Stack<Float> = Stack()
    val compute: (Int) -> Float = {
        alphaMultipliers.reduce{a, b -> a + b} * it
    }

}