package ru.xewe.xonagic.common.ability;

import net.minecraft.entity.player.EntityPlayer;

public abstract class Ability {
    public int coolDown;
    protected int repeat;
    protected EntityPlayer player;

    public void execute(EntityPlayer player) {
        this.player = player;
        this.repeat = getInfo().repeat();
        AbilityManager.getAbilityManager(player).activateAbility(this);
    }

    protected void onExit() {
        AbilityManager.getAbilityManager(player).deactivateAbility(this);
        this.repeat = 0;
        if (!player.isCreative()) {
            this.coolDown += getInfo().coolDown();
            AbilityManager.getAbilityManager(player).imposeCoolDown(this);
        }

    }

    public abstract boolean onUpdate();

    public boolean onUpdateDefault() {
        if (repeat > 0) {
            boolean result = onUpdate();
            repeat--;
            return result;
        } else {
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

    public AbilityInfo getInfo(){
        return getClass().getAnnotation(AbilityInfo.class);
    }

//    public String getDisplayName() {
//        return displayName;
//    }
//
//    public TextFormatting getColor() {
//        return color;
//    }
}
