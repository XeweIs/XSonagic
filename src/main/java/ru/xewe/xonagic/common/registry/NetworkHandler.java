package ru.xewe.xonagic.common.registry;

import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import ru.xewe.xonagic.XeweXonagic;
import ru.xewe.xonagic.common.packets.CPacketCast;
import ru.xewe.xonagic.common.packets.SPacketPlayerMotion;

public final class NetworkHandler {
    private static short id = 0;

    public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(XeweXonagic.MODID + "-Packet");

    public static void init() {
        register(CPacketCast.class);
        register(SPacketPlayerMotion.class);
    }

    public static void sendToAllAround(IMessage packet, World world, double x, double y, double z, double distance) {
        NETWORK.sendToAllAround(packet, new NetworkRegistry.TargetPoint(world.provider.getDimension(), x, y, z, distance));
    }

    private static void register(Class packet) {
        NETWORK.registerMessage(packet, packet, id, Side.CLIENT);
        NETWORK.registerMessage(packet, packet, id, Side.SERVER);
        id++;
    }
}

