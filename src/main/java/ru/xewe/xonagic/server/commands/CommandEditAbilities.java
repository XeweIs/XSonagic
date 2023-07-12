package ru.xewe.xonagic.server.commands;

import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import ru.xewe.xonagic.common.data.AbilitiesData;
import ru.xewe.xonagic.common.data.ElementData;
import ru.xewe.xonagic.common.enums.AbilitiesEnum;
import ru.xewe.xonagic.common.enums.ElementEnum;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class CommandEditAbilities extends CommandBase {

    @Nonnull
    @Override
    public String getName() {
        return "editAbilities";
    }

    @Nonnull
    @Override
    public String getUsage(@Nonnull ICommandSender sender) {
        return "command.editAbilities.usage";
    }
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) throws CommandException {
        if (args.length < 2) {
            throw new WrongUsageException(getUsage(sender));
        }

        final EntityPlayerMP player = getPlayer(server, sender, args[0]);
        String option = args[1];
        String ability = "";

        if (option.equals("add") || option.equals("remove")){
            if (args.length != 3) throw new WrongUsageException(getUsage(sender));

            try {
                ability = AbilitiesEnum.valueOfCaseLess(args[2]).getInfo().name();

                if(!AbilitiesEnum.valueOfCaseLess(args[2]).getInfo().element().equals(ElementData.getElement(player)))
                    throw new SyntaxErrorException("command.editAbilities.mismatchAbility", player.getName(), args[2]);

            } catch (IllegalArgumentException exception) {
                throw new SyntaxErrorException("command.editAbilities.unknownAbility", args[2]);
            }
        }

        switch (option) {
            case "add":
                if (!AbilitiesData.add(player, ability)) {
                    throw new SyntaxErrorException("command.editAbilities.fail.add", player.getName(), ability);
                }
                break;
            case "remove":
                if (!AbilitiesData.remove(player, ability)) {
                    throw new SyntaxErrorException("command.editAbilities.fail.remove", player.getName(), ability);
                }
                break;
            case "reset":
                AbilitiesData.reset(player);
                break;
            default:
                throw new WrongUsageException(getUsage(sender));
        }

        if (sender.getEntityWorld().getGameRules().getBoolean("sendCommandFeedback")) {
            player.sendMessage(new TextComponentTranslation("command.editAbilities.change." + option, ability));
        }

        notifyCommandListener(sender, this, 1, "command.editAbilities.success."+option, player.getName(), ability);
    }

    @Nonnull
    @Override
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] arguments,
                                          BlockPos pos) {

        String[] options = {"add", "remove", "reset"};

        switch (arguments.length) {
            case 1:
                return getListOfStringsMatchingLastWord(arguments, server.getOnlinePlayerNames());
            case 2:
                return getListOfStringsMatchingLastWord(arguments, options);
            case 3:
                if (arguments[1].equals("reset")) {
                    break;
                }

                EntityPlayerMP player;
                List<String> abilities = new ArrayList<>();
                try {
                    player = getPlayer(server, sender, arguments[0]);
                } catch (CommandException e) {
                    throw new RuntimeException(e);
                }

                switch (arguments[1]) {
                    case "add":
                        ElementEnum element = ElementData.getElement(player);
                        List<String> arrayAbilities = AbilitiesEnum.getAllWithElementToString(element);
                        arrayAbilities.removeAll(AbilitiesData.get(player));
                        abilities = arrayAbilities;
                        break;
                    case "remove":
                        abilities = AbilitiesData.get(player);
                        break;
                }
                return getListOfStringsMatchingLastWord(arguments, abilities);
        }
        return super.getTabCompletions(server, sender, arguments, pos);
    }
}
