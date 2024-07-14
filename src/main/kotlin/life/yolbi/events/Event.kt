package life.yolbi.events

/**
 * @author yuxiangll
 * @since 2024/7/5 下午7:51
 * IntelliJ IDEA
 */

@Suppress("MemberVisibilityCanBePrivate")
open class Event(){
    var cancelled: Boolean = false

    fun cancel(){
        cancelled = true
    }

    fun isCancelled(): Boolean{
        return cancelled
    }

}
