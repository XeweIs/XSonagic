package ru.xewe.xonagic.common.registry;

import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import ru.xewe.xonagic.XeweXonagic;
import ru.xewe.xonagic.common.packets.*;

public final class NetworkHandler {
    private static short id = 0;

    public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(XeweXonagic.MODID + "-Packet");

    public static void init() {
        register(SPacketPlayerMotion.class,  Side.CLIENT);
        register(SPacketAbilityManagerReset.class, Side.CLIENT);
        register(SPacketSynchAbilityManager.class,  Side.CLIENT);
        register(SPacketCastAbility.class, Side.CLIENT);

        register(CPacketCastAbility.class,  Side.SERVER);
    }

    public static void sendToAllAround(IMessage packet, World world, double x, double y, double z, double distance) {
        NETWORK.sendToAllAround(packet, new NetworkRegistry.TargetPoint(world.provider.getDimension(), x, y, z, distance));
    }

    private static void register(Class packet, Side side) {
//        Side side = null;
//        if(packet.getSimpleName().charAt(0) == 'C'){
//            side = Side.SERVER;
//        } else if (packet.getSimpleName().charAt(0) == 'S') {
//            side = Side.CLIENT;
//        }
//        try {
//            NETWORK.registerMessage(packet.newInstance(), packet, id, side);
//        } catch (InstantiationException | IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
        NETWORK.registerMessage(packet, packet, id, side);
        id++;
    }
}

