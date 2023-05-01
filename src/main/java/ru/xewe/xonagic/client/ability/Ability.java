package ru.xewe.xonagic.client.ability;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextFormatting;
import ru.xewe.xonagic.client.gui.TextGui;

import java.util.ArrayList;
import java.util.List;

public abstract class Ability {
    protected int coolDown;
    protected String displayName;
    protected TextFormatting color;
    protected EntityPlayer player;

    public void execute(EntityPlayer player) {
        this.player = player;
        this.activateAbility();
        // Client side
        if (player.world.isRemote) {
            AbilityInfo info = getClass().getAnnotation(AbilityInfo.class);
            this.displayName = info.displayName();
            this.color = info.color();
            this.setCoolDown(info.coolDown());
        }else{
            UpdateAbilities.listPlayer.add((EntityPlayerMP) player);
        }
    }

    protected void onExit(EntityPlayer player) {
        this.player = player;
        this.deactivateAbility();
        if (player.world.isRemote) {
            if (!player.isCreative()) {
                this.imposeCoolDown();
                TextGui.coolDownText += new StringBuilder()
                        .append(color.toString())
                        .append(TextFormatting.STRIKETHROUGH)
                        .append(displayName).append(" ")
                        .append(((this.coolDown / 40) + 1))
                        .append(TextFormatting.RESET)
                        .append("  ");
            }
        }
    }

    public abstract boolean onUpdate(EntityPlayer player);

    public boolean allowedExecute() {
        return !(this.isActive() || this.isCoolDown());
    }

    protected boolean isActive() {
        return UpdateAbilities.activateAbilities.contains(getInstance());
    }

    protected void activateAbility() {
        if (player.world.isRemote)
            UpdateAbilities.activateAbilities.add(getInstance());
        else
            try {
                if (UpdateAbilities.activateAbilitiesServer.get(player.getUniqueID()) == null) {
                    List<Ability> abilities = new ArrayList<>();
                    abilities.add(getClass().newInstance());
                    UpdateAbilities.activateAbilitiesServer.put(player.getUniqueID(), abilities);
                } else {
                    UpdateAbilities.activateAbilitiesServer.get(player.getUniqueID()).add(getClass().newInstance());
                }
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
    }

    protected void deactivateAbility() {
        if (player.world.isRemote)
            UpdateAbilities.activateAbilities.remove(getInstance());
        else {
            AbilityInfo info = getClass().getAnnotation(AbilityInfo.class);
            List<Ability> abilities = UpdateAbilities.activateAbilitiesServer.get(player.getUniqueID());
            for (Ability ability : abilities) {
                if (ability.toString().contains(info.name())) {
                    UpdateAbilities.activateAbilitiesServer.get(player.getUniqueID()).remove(ability);
                    break;
                }
            }
        }
    }

    protected boolean isCoolDown() {
        return UpdateAbilities.coolDownAbilities.contains(getInstance());
    }

    protected void imposeCoolDown() {
        UpdateAbilities.coolDownAbilities.add(getInstance());
    }

    protected void cancelCoolDown() {
        UpdateAbilities.coolDownAbilities.remove(getInstance());
    }

    protected void setCoolDown(int coolDown) {
        this.coolDown = (coolDown * 40) - 1;
    }

    public abstract Ability getInstance();
}
