package today.yapeteam

import meteordevelopment.orbit.EventBus
import net.fabricmc.api.ModInitializer
import net.minecraft.client.MinecraftClient
import org.slf4j.LoggerFactory
import today.yapeteam.manager.FontManager
import today.yapeteam.module.ModuleManager
import java.lang.invoke.MethodHandles
import java.lang.reflect.Method

object YolBi4 : ModInitializer {

	val logger = LoggerFactory.getLogger("yolbi4")
	//private val version = "4.0.0"
	val EVENT_BUG = EventBus();
	val moduleManager = ModuleManager()

	val fontManager = FontManager()
	val mc: MinecraftClient = MinecraftClient.getInstance()

	override fun onInitialize() {

		EVENT_BUG.registerLambdaFactory(/* packagePrefix = */ "today.yapeteam"
		) { lookupInMethod: Method, klass: Class<*>? ->
			lookupInMethod.invoke(
				null,
				klass,
				MethodHandles.lookup()
			) as MethodHandles.Lookup
		}
		moduleManager.initialize()
		logger.info("Welcome to YolBi4!")
	}
}