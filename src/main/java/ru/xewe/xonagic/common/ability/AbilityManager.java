package ru.xewe.xonagic.common.ability;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class AbilityManager {
    public List<Ability> coolDownAbilities = new ArrayList<>();
    public List<Ability> activateAbilities = new ArrayList<>();
    public List<Ability> abilities = new ArrayList<>();
    public static AbilityManager abilityManagerSP = new AbilityManager();
    public static HashMap<UUID, AbilityManager> abilityManagerMP = new HashMap<>();

    @SideOnly(Side.CLIENT)
    public void onUpdate() {
        if (Minecraft.getMinecraft().isGamePaused()) return;
        EntityPlayer player = Minecraft.getMinecraft().player;
        //CoolDown for Client
        for (int i = 0; i < coolDownAbilities.size(); i++) {
            Ability ability = coolDownAbilities.get(i);

            if (ability.coolDown <= 0) {
                cancelCoolDown(ability);
            } else {
                ability.coolDown--;
            }

        }

        //Update for Client
        for (int i = 0; i < activateAbilities.size(); i++) {
            Ability ability = activateAbilities.get(i);

            if (player.isDead) {
                ability.onExit();
                break;
            }

            if (ability.onUpdateDefault()) {
                ability.onExit();
            }
        }
    }

    public void onUpdateServer() {
        //Update for Server
        for (int a = 0; a < activateAbilities.size(); a++) {
            Ability ability = activateAbilities.get(a);

            if (ability.player.isDead) {
                ability.onExit();
                break;
            }

            if (ability.onUpdateDefault()) {
                ability.onExit();
            }
        }

        //CoolDown for Server
        for (int a = 0; a < coolDownAbilities.size(); a++){
            Ability ability = coolDownAbilities.get(a);

            if (ability.coolDown <= 0) {
                cancelCoolDown(ability);
            } else {
                ability.coolDown--;
            }
        }
    }

    public void activateAbility(Ability ability) {
        activateAbilities.add(ability);
    }

    public void deactivateAbility(Ability ability) {
        activateAbilities.remove(ability);
    }

    public void imposeCoolDown(Ability ability) {
        ability.coolDown *= 20;  //20 тиков - секунда
        coolDownAbilities.add(ability);
    }

    public void cancelCoolDown(Ability ability) {
        coolDownAbilities.remove(ability);
    }

    //Special
    public static AbilityManager getAbilityManager(EntityPlayer player){
        if(player.world.isRemote) {
            return getAbilityManagerSP();
        }else{
            return getAbilityManagerMP(player.getUniqueID());
        }
    }

    @SideOnly(Side.CLIENT)
    public static AbilityManager getAbilityManagerSP(){
        return abilityManagerSP;
    }

    public static AbilityManager getAbilityManagerMP(UUID uuidPlayer){
        return abilityManagerMP.get(uuidPlayer);
    }
}