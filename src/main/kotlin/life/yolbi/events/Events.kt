package life.yolbi.events

import net.minecraft.client.gui.DrawContext
import net.minecraft.client.render.RenderTickCounter
import net.minecraft.entity.Entity
import net.minecraft.entity.MovementType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.network.listener.PacketListener
import net.minecraft.network.packet.Packet
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction

/**
 * @author yuxiangll
 * @since 2024/7/5 下午7:58
 * IntelliJ IDEA
 */

// client
class EventClientTick: Event()

class EventMouseButton(val button: Int, val action: Int): Event()

class EventKeyPress(val key: Int, val scanCode: Int): Event()

class EventKeyRelease(val key: Int, val scanCode: Int): Event()


// network
class EventPrePacketSend(val packet: Packet<*>): Event()

class EventPostPacketSend(val packet: Packet<*>): Event()

class EventPrePacketReceive(val packet: Packet<*>): Event()

class EventPostPacketReceive(val packet: Packet<*>): Event()

// player
class EventPreAttack(): Event()

class EventPostAttack(val entity: Entity): Event()

class EventDeath(val player: PlayerEntity): Event()

class EventPlayerJump(): Event()

class EventPlayerMove(val moveType: MovementType,val x: Double, val y: Double, val z: Double): Event()


class EventPlayerTravel(val strafe: Double, val vertical: Double, val forward: Double): Event()

class EventAttackBlock(val blockPos: BlockPos, val enumFacing: Direction): Event()

class EventBreakBlock(val blockPos: BlockPos): Event()

class EventPrePlayerUpdate(): Event()

class EventPostPlayerUpdate(): Event(){
    val iterations: Int = 0
}

class EventTotemPop(val entity: PlayerEntity): Event()

//render
class EventRenderEntityName(): Event()

class EventRenderGameOverlay(val context: DrawContext, val renderTickCounter: RenderTickCounter): Event()



