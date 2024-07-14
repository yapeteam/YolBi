package life.yolbi.util.client

/**
 * @author yuxiangll
 * @since 2024/7/8 下午2:31
 * IntelliJ IDEA
 */
class TimerUtil {

    private var time: Long = 0


    init {
        reset()
    }


    fun reset(){
        time = System.nanoTime()
    }


    fun passedS(s: Double): Boolean {
        return getMs(System.nanoTime() - time) >= (s * 1000.0).toLong()
    }

    fun passedMs(ms: Long): Boolean {
        return getMs(System.nanoTime() - time) >= ms
    }

    fun every(ms: Long): Boolean {
        val passed = getMs(System.nanoTime() - time) >= ms
        if (passed) reset()
        return passed
    }

    fun setMs(ms: Long) {
        this.time = System.nanoTime() - ms * 1000000L
    }

    fun getPassedTimeMs(): Long {
        return getMs(System.nanoTime() - time)
    }

    private fun getMs(time: Long): Long {
        return time / 1000000L
    }





}