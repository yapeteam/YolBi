package life.yolbi.util.client

/**
 * @author yuxiangll
 * @since 2024/7/8 下午2:31
 * IntelliJ IDEA
 */
class TimerUtil {

    private var time: Long = 0


    init {
        time = System.nanoTime()
    }

    val reset = {
        time = System.nanoTime()
    }


    val passed: (Number)-> Boolean = {
        when (it){
            is Long -> System.nanoTime() ms time >= it
            is Double -> System.nanoTime() ms time>= it* 1000
            else -> false
        }
    }


    fun every(ms: Long): Boolean {
        val passed = System.nanoTime() ms time >= ms
        if (passed) reset()
        return passed
    }

    fun setMs(ms: Long) {
        this.time = System.nanoTime() - ms * 1000000L
    }


    val getPassedTimeMs = {
        System.nanoTime() ms time
    }




    private infix fun Long.ms(ms: Long): Long {
        return  (this - ms) / 1000000L
    }




}