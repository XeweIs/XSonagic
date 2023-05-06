package ru.xewe.xonagic.common.ability;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import ru.xewe.xonagic.client.gui.TextGui;

public abstract class Ability {
    protected int coolDown;
    protected int repeat;
    protected String displayName;
    protected TextFormatting color;
    protected EntityPlayer player;

    public void execute(EntityPlayer player) {
        AbilityInfo info = getClass().getAnnotation(AbilityInfo.class);
        this.player = player;
        this.repeat = info.repeat();
        AbilityManager.activateAbility(this);
        // Client side
        if (player.world.isRemote) {
            this.displayName = info.displayName();
            this.color = info.color();
        }
    }

    protected void onExit(EntityPlayer player) {
        this.player = player;
        AbilityManager.deactivateAbility(this);
        if (player.world.isRemote) {
            if (!player.isCreative()) {
                AbilityManager.imposeCoolDown(this);
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

    protected abstract boolean onUpdate(EntityPlayer player);

    public boolean onUpdateDefault(EntityPlayer player) {
        if(repeat > 0) {
            boolean result = onUpdate(player);
            repeat--;
            return result;
        }else {
            return true;
        }
    }

    public boolean allowedExecute() {
        return !(this.isActive() || this.isCoolDown());
    }

    protected boolean isActive() {
        return repeat > 0;
    }

    protected boolean isCoolDown() {
        return coolDown > 0;
    }

//    public void activateAbility(Ability ability) {
//        if (player.world.isRemote)
//            AbilitiesManager.activateAbilities.add(getInstance());
//        else
//            try {
//                if (AbilitiesManager.activateAbilitiesServer.get(player.getUniqueID()) == null) {
//                    List<Ability> abilities = new ArrayList<>();
//                    abilities.add(getClass().newInstance());
//                    AbilitiesManager.activateAbilitiesServer.put(player.getUniqueID(), abilities);
//                } else {
//                    AbilitiesManager.activateAbilitiesServer.get(player.getUniqueID()).add(this);
//                }
//            } catch (InstantiationException | IllegalAccessException e) {
//                throw new RuntimeException(e);
//            }
//    }

//    protected void deactivateAbility() {
//        if (player.world.isRemote)
//            AbilitiesManager.activateAbilities.remove(getInstance());
//        else {
//            List<Ability> abilities = AbilitiesManager.activateAbilitiesServer.get(player.getUniqueID());
//            for (Ability ability : abilities) {
//                if (ability.getClass() == this.getClass()) {
//                    AbilitiesManager.activateAbilitiesServer.get(player.getUniqueID()).remove(ability);
//                    break;
//                }
//            }
//        }
//    }

//    protected void imposeCoolDown() {
//        AbilityInfo info = getClass().getAnnotation(AbilityInfo.class);
//        this.setCoolDown(info.coolDown());
//        AbilitiesManager.coolDownAbilities.add(getInstance());
//    }
//
//    protected void cancelCoolDown() {
//        AbilitiesManager.coolDownAbilities.remove(getInstance());
//    }

    protected void setCoolDown(int coolDown) {
        this.coolDown = (coolDown * 40) - 1;
    }

    public abstract Ability getInstance();
}
