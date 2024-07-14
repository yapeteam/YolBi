package life.yolbi.module

import life.yolbi.YolBi4
import life.yolbi.surveillance.PacketSurveillance
import life.yolbi.util.IMinecraft
import net.minecraft.network.packet.Packet

/**
 * @author yuxiangll
 * @since 2024/7/6 上午9:26
 * IntelliJ IDEA
 */


@Suppress("MemberVisibilityCanBePrivate")
abstract class Module(
    val name: String,
    val category: Category,
    key: Bind = Bind(0,BindType.PreClick),
) : IMinecraft {

    var enable: Boolean = false
    var prefix: String = ""
    var bind: BindSetting = BindSetting(name,"Binding",key)

    private val settings: MutableList<Settings> = mutableListOf()

    open fun onEnable(){

    }

    open fun onDisable(){

    }

    fun addSetting(setting: Settings) {
        settings.add(setting)
    }

    fun addSetting(setting: MutableList<Settings>){
        settings.addAll(setting)
    }

    fun toggle(){
        if(enable)
            disable()
        else
            enable()

    }
    fun toggle(enable: Boolean) {
        if(enable)
            enable()
        else
            disable()

    }

    private fun enable(){
        enable = true
        YolBi4.EventBus.subscribe(this)
        onEnable()
    }

    private fun disable(){
        enable = false
        YolBi4.EventBus.unsubscribe(this)
        onDisable()

    }


    protected fun sendPacketSilent(packet: Packet<*>) {
        if (mc.networkHandler == null) return
        PacketSurveillance.silentPackets.add(packet)
        mc.networkHandler!!.sendPacket(packet)
        //ThunderHack.core.silentPackets.add(packet)
        //thunder.hack.modules.Module.mc.getNetworkHandler().sendPacket(packet)
    }




}