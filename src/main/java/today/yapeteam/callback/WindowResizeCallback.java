package today.yapeteam.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;

/**
 * @author yuxiangll
 * @since 2024/6/8 上午6:21
 * IntelliJ IDEA
 */
public interface WindowResizeCallback {

    Event<WindowResizeCallback> EVENT = EventFactory.createArrayBacked(WindowResizeCallback.class, callbacks -> (client, window) -> {
        for (var callback : callbacks) {
            callback.onResized(client, window);
        }
    });

    void onResized(MinecraftClient client, Window window);

}
