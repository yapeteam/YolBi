package life.yolbi.mixin;

import life.yolbi.YolBi4;
import life.yolbi.events.EventPostPacketReceive;
import life.yolbi.events.EventPostPacketSend;
import life.yolbi.events.EventPrePacketReceive;
import life.yolbi.events.EventPrePacketSend;
import life.yolbi.surveillance.PacketSurveillance;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author yuxiangll
 * @since 2024/7/5 下午7:50
 * IntelliJ IDEA
 */

@SuppressWarnings({"unchecked","SpellCheckingInspection"})
@Mixin(ClientConnection.class)
public abstract class MixinClientConnection {

    //@Unique
    //private static final MinecraftClient mc = MinecraftClient.getInstance();

    private static boolean fullNullCheck() {
        return MinecraftClient.getInstance().player == null || MinecraftClient.getInstance().world == null;
    }


    @Inject(method = "handlePacket", at = @At("HEAD"), cancellable = true)
    private static <T extends PacketListener> void onHandlePacket(Packet<T> packet, PacketListener listener, CallbackInfo info) {
        if(fullNullCheck()) return;
        EventPrePacketReceive event = new EventPrePacketReceive(packet);

        YolBi4.INSTANCE.getEventBus().post(event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }

    @Inject(method = "handlePacket", at = @At("TAIL"), cancellable = true)
    private static <T extends PacketListener> void onHandlePacketPost(Packet<T> packet, PacketListener listener, CallbackInfo info) {
        if(fullNullCheck()) return;
        EventPostPacketReceive event = new EventPostPacketReceive(packet);
        YolBi4.INSTANCE.getEventBus().post(event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }

    @Inject(method = "send(Lnet/minecraft/network/packet/Packet;)V", at = @At("HEAD"),cancellable = true)
    private void onSendPacketPre(Packet<?> packet, CallbackInfo info) {
        if(fullNullCheck()) return;
        if(PacketSurveillance.INSTANCE.getSilentPackets().contains(packet)) {
            PacketSurveillance.INSTANCE.getSilentPackets().remove(packet);
            return;
        }
        EventPrePacketSend event = new EventPrePacketSend(packet);
        YolBi4.INSTANCE.getEventBus().post(event);
        if (event.isCancelled()) info.cancel();
    }

    @Inject(method = "send(Lnet/minecraft/network/packet/Packet;)V", at = @At("RETURN"),cancellable = true)
    private void onSendPacketPost(Packet<?> packet, CallbackInfo info) {
        if(fullNullCheck()) return;
        EventPostPacketSend event = new EventPostPacketSend(packet);
        YolBi4.INSTANCE.getEventBus().post(event);
        if (event.isCancelled()) info.cancel();
    }

}
