package today.yapeteam.event

/**
 * @author yuxiangll
 * @since 2024/6/7 上午7:36
 * IntelliJ IDEA
 */
open class Event {
    var cancelled: Boolean = false

    fun cancel() {
        cancelled = true
    }

}