package ru.xewe.xonagic.common.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public abstract class AbstractPacket<REQ extends IMessage> implements IMessage, IMessageHandler<REQ, REQ>
{
    @Override
    public REQ onMessage(final REQ message, final MessageContext ctx)
    {
        if(ctx.side == Side.SERVER)
        {
            EntityPlayerMP player = ctx.getServerHandler().player;
            handleServer(message, player);
        }
        else
        {
            EntityPlayerSP player = Minecraft.getMinecraft().player;
            handleClient(message, player);
        }
        return null;
    }

    public abstract void handleClient(final REQ message, final EntityPlayerSP player);
    public abstract void handleServer(final REQ message, final EntityPlayerMP player);
}


