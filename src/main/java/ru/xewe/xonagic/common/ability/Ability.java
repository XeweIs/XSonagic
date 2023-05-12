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
        AbilityManager.activateAbility(this);
        if (player.world.isRemote) {
            this.displayName = info.displayName();
            this.color = info.color();
        }
    }

    protected void onExit(EntityPlayer player) {
        this.player = player;
        AbilityManager.deactivateAbility(this);
        this.repeat = 0;
        if (player.world.isRemote) {
            if (!player.isCreative()) {
                AbilityManager.imposeCoolDown(this);
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

    protected void setCoolDown(int coolDown) {
        this.coolDown = (coolDown * 40);
    }
}
