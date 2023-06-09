package ru.xewe.xonagic.server.commands;

import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import ru.xewe.xonagic.common.data.AbilitiesData;
import ru.xewe.xonagic.common.data.ElementData;
import ru.xewe.xonagic.common.enums.ElementEnum;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class CommandElementChoose extends CommandBase {

    @Nonnull
    @Override
    public String getName() {
        return "elementChoose";
    }

    @Nonnull
    @Override
    public String getUsage(@Nonnull ICommandSender sender) {
        return "command.elementChoose.usage";
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1) {
            throw new WrongUsageException(getUsage(sender));
        }

        final EntityPlayerMP player = args.length == 2 ? getPlayer(server, sender, args[1]) : getCommandSenderAsPlayer(sender);
        ElementEnum element;
        try {
            element = ElementEnum.valueOfCaseLess(args[0]);
        } catch (IllegalArgumentException exception) {
            throw new SyntaxErrorException("command.elementChoose.unknownElement", args[0]);
        }

        if (sender.getEntityWorld().getGameRules().getBoolean("sendCommandFeedback")) {
            player.sendMessage(new TextComponentTranslation("command.elementChoose.change", element.getProcessed()));
        }

        notifyCommandListener(sender, this, 1, "command.elementChoose.success", player.getName(), element.getProcessed());

        if(!element.equals(ElementData.getElement(player))){
            ElementData.setElement(player, element);
            AbilitiesData.reset(player);
        }
    }

    @Nonnull
    @Override
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] arguments,
                                          BlockPos pos) {
        String[] elements = Arrays.stream(ElementEnum.values()).map(ElementEnum::toString).toArray(String[]::new);

        switch (arguments.length) {

            case 1:
                return getListOfStringsMatchingLastWord(arguments, elements);
            case 2:
                return getListOfStringsMatchingLastWord(arguments, server.getOnlinePlayerNames());
        }
        return super.getTabCompletions(server, sender, arguments, pos);
    }
}
