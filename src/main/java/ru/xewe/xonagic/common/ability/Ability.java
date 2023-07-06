package ru.xewe.xonagic.common.ability;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;

public abstract class Ability {
    public int coolDown;
    protected int repeat;
    public String displayName;
    public TextFormatting color;
    protected EntityPlayer player;

    public void execute(EntityPlayer player) {
        AbilityInfo info = getClass().getAnnotation(AbilityInfo.class);
        this.player = player;
        this.repeat = info.repeat();
        AbilityManager.getAbilityManager(player).activateAbility(this);
        if (player.world.isRemote) {
            this.displayName = info.displayName();
            this.color = info.color();
        }
    }

    protected void onExit() {
        AbilityManager.getAbilityManager(player).deactivateAbility(this);
        this.repeat = 0;
        if (!player.isCreative()) {
            this.coolDown = 0;
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
}
