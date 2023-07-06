package ru.xewe.xonagic.server.commands;

import net.minecraft.client.resources.I18n;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import ru.xewe.xonagic.common.data.AbilitiesData;
import ru.xewe.xonagic.common.data.ElementData;

import javax.annotation.Nonnull;
import java.util.List;

public class CommandGetInfo extends CommandBase {

    @Nonnull
    @Override
    public String getName() {
        return "getInfo";
    }

    @Nonnull
    @Override
    public String getUsage(@Nonnull ICommandSender sender) {
        return I18n.format("command.getInfo.usage");
    }

    public int getRequiredPermissionLevel() {
        return 1;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1) {
            throw new WrongUsageException(getUsage(sender));
        }

        final EntityPlayerMP player = args.length == 2 ? getPlayer(server, sender, args[1]) : getCommandSenderAsPlayer(sender);
        switch (args[0].toLowerCase()){
            case "element":
                if(sender == player) {
                    player.sendMessage(new TextComponentTranslation("command.getInfo.element.self",
                            ElementData.getElement(player).getProcessed()));
                }else{
                    player.sendMessage(new TextComponentTranslation("command.getInfo.element.other", player.getName(),
                            ElementData.getElement(player).getProcessed()));
                }
                break;
            case "abilities":
                if(sender == player) {
                    sender.sendMessage(new TextComponentTranslation("command.getInfo.abilities.self",
                            String.join(", ", AbilitiesData.get(player))));
                }else{
                    sender.sendMessage(new TextComponentTranslation("command.getInfo.abilities.other", player.getName(),
                            String.join(", ", AbilitiesData.get(player))));
                }
                break;
            default:
                throw new WrongUsageException(getUsage(sender));
        }
    }

    @Nonnull
    @Override
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] arguments,
                                          BlockPos pos) {

        String[] options = {"element", "abilities"};

        switch (arguments.length) {

            case 1:
                return getListOfStringsMatchingLastWord(arguments, options);
            case 2:
                return getListOfStringsMatchingLastWord(arguments, server.getOnlinePlayerNames());
        }
        return super.getTabCompletions(server, sender, arguments, pos);
    }
}
