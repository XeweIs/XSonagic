package ru.xewe.xonagic.common.packets;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import ru.xewe.xonagic.proxy.ClientProxy;

public abstract class AbstractPacket<REQ extends IMessage>  implements IMessage, IMessageHandler<REQ, REQ>
{
    @Override
    public REQ onMessage(final REQ msg, final MessageContext ctx)
    {
        if(ctx.side == Side.SERVER)
        {
            EntityPlayerMP player = ctx.getServerHandler().player;
            handleServer(msg, player);
        }
        else
        {
            EntityPlayer player = ClientProxy.getEntityPlayer();

            handleClient(msg, player);
        }
        return null;
    }

    public abstract void handleClient(final REQ msg, final EntityPlayer player);
    public abstract void handleServer(final REQ msg, final EntityPlayerMP player);
}


