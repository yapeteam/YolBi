package life.yolbi

import life.yolbi.managers.CommandManager
import life.yolbi.managers.FontManager
import life.yolbi.managers.ModuleManager
import life.yolbi.managers.SurveillanceManager
import life.yolbi.util.render.Render2DEngine
import meteordevelopment.orbit.EventBus
import net.fabricmc.api.ModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.invoke.MethodHandles
import java.lang.reflect.Method

@Suppress("MemberVisibilityCanBePrivate")
object YolBi4 : ModInitializer {
	val logger: Logger = LoggerFactory.getLogger("YolBi4")

	val NAME = "YolBi"
	val VERSION = "4.0.0"

	val EventBus = EventBus()

	override fun onInitialize() {

		logger.info("Welcome to $NAME $VERSION ")

		EventBus.registerLambdaFactory( "life.yolbi") { lookupInMethod: Method, klass: Class<*> ->
			lookupInMethod(null, klass, MethodHandles.lookup()) as MethodHandles.Lookup
		}


		logger.info("initializing managers")

		ModuleManager.initialize()
		SurveillanceManager.initialize()
		CommandManager.initialize()

		Render2DEngine.initShaders()


	}



}