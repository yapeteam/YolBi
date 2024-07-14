package life.yolbi.managers


import life.yolbi.YolBi4
import life.yolbi.surveillance.EventSurveillance
import life.yolbi.surveillance.ModuleSurveillance


/**
 * @author yuxiangll
 * @since 2024/7/6 上午9:53
 * IntelliJ IDEA
 */

object SurveillanceManager {
    private val surveillance = mutableListOf<Any>()

    fun initialize(){
        surveillance.add(ModuleSurveillance)
        surveillance.add(EventSurveillance)

        surveillance.forEach {
            YolBi4.EventBus.subscribe(it)
        }

    }

}