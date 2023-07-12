package ru.xewe.xonagic.common.data;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.datasync.DataParameter;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import ru.xewe.xonagic.XeweXonagic;
import ru.xewe.xonagic.common.ability.AbilityManager;
import ru.xewe.xonagic.common.ability.AbilityManagerEvents;
import ru.xewe.xonagic.common.enums.AbilitiesEnum;
import ru.xewe.xonagic.common.packets.SPacketSynchAbilityManager;
import ru.xewe.xonagic.common.registry.NetworkHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = XeweXonagic.MODID)
public class AbilitiesData {
    static DataParameter<String> dataParameter = PropertiesRegistry.ABILITIES;
    static String key = XeweXonagic.MODID + ":abilities";

    //Получаем хранящуюся строку со способностями без обработки split'ом
    public static String getRaw(EntityPlayer player) {
        return player.getDataManager().get(dataParameter);
    }

    //Получаем список названий способностей участника
    public static List<String> get(EntityPlayer player) {
        if(getRaw(player).isEmpty()) return new ArrayList<>(); //Пиздец, ну и костыль с этим ебучим new Array - если убрать, то get будет возвращать даже пустую строку с 1 элементом.
        return Arrays.asList(getRaw(player).split("\\|"));
    }

    //Задаем текущее значение способностей без обработки
    static void setRaw(EntityPlayer player, String value) {
        player.getDataManager().set(dataParameter, value);
        saveDataToNBT(player);
    }

    //Задаем текущее значение способностей с обработкой, то есть входными данными списка с именами способностей
//    public static void set(EntityPlayer player, String... abilitiesName) {
//        setRaw(player, String.join("|", abilitiesName));
//    }

    //Задаем текущее значение способностей с обработкой, то есть входными данными списка со способностями
//    public static void set(EntityPlayer player, Ability... abilities) {
//        List<String> abilitiesName = Arrays.stream(abilities).map(ability -> ability.getClass().getSimpleName()).collect(Collectors.toList());
//        setRaw(player, String.join("|", abilitiesName));
//    }

    //Добавление способностей
//    public static void add(EntityPlayer player, Ability... abilities) {
//        List<String> abilitiesName = Arrays.stream(abilities).map(ability -> ability.getClass().getSimpleName()).collect(Collectors.toList());
//        abilitiesName.addAll(Arrays.asList(get(player)));
//        setRaw(player, String.join("|", abilitiesName));
//    }

    public static boolean add(EntityPlayer player, String... abilitiesName){
        boolean success = true;

        List<String> abilitiesNameList = new ArrayList<>(Arrays.asList(abilitiesName));

        for(int a = 0; a < abilitiesNameList.size(); a++){
            String abilityName = abilitiesNameList.get(a);

            if(get(player).contains(abilityName)){
                abilitiesNameList.remove(abilityName);
                success = false;
            }else if(!player.world.isRemote){ //Синхронизация способностей с AbilityManager
                AbilityManager.getAbilityManagerMP(player.getUniqueID()).abilities.add(AbilitiesEnum.valueOfCaseLess(abilityName).getInstance());
                NetworkHandler.NETWORK.sendTo(new SPacketSynchAbilityManager(SPacketSynchAbilityManager.Option.add, abilityName), (EntityPlayerMP) player);
            }
        }

        abilitiesNameList.addAll(get(player));
        setRaw(player, String.join("|", abilitiesNameList));

        return success;
    }

    //Удаление способностей
//    public static void remove(EntityPlayer player, Ability... abilities){
//        List<String> abilitiesName = new ArrayList<>(Arrays.asList(get(player)));
//        List<String> toRemoveAbilitiesName = Arrays.stream(abilities).map(ability -> ability.getClass().getSimpleName()).collect(Collectors.toList());
//        abilitiesName.removeAll(toRemoveAbilitiesName);
//        setRaw(player, String.join("|", abilitiesName));
//    }

    public static boolean remove(EntityPlayer player, String... abilitiesName){
        for(String abilityName : abilitiesName){
            if(!get(player).contains(abilityName)) return false;

            //Синхронизация способностей с AbilityManager
            if(!player.world.isRemote){
                AbilityManager.getAbilityManagerMP(player.getUniqueID()).abilities
                        .removeIf(ability -> ability.getInfo().name().equals(abilityName));
                NetworkHandler.NETWORK.sendTo(new SPacketSynchAbilityManager(SPacketSynchAbilityManager.Option.remove, abilityName), (EntityPlayerMP) player);
            }
        }

        List<String> abilities = new ArrayList<>(get(player));
        List<String> toRemoveAbilitiesName = new ArrayList<>(Arrays.asList(abilitiesName));
        abilities.removeAll(toRemoveAbilitiesName);
        setRaw(player, String.join("|", abilities));
        return true;
    }

    //Сброс значений
    public static void reset(EntityPlayer player) {
        //Синхронизация способностей с AbilityManager
        if(!player.world.isRemote) {
            AbilityManager.getAbilityManagerMP(player.getUniqueID()).abilities.clear();
            NetworkHandler.NETWORK.sendTo(new SPacketSynchAbilityManager(SPacketSynchAbilityManager.Option.reset, ""), (EntityPlayerMP) player);
        }

        setRaw(player, "");
    }

    //Сохранение данных в NBT
    public static void saveDataToNBT(EntityPlayer player) {
        player.getEntityData().setString(key, getRaw(player));
    }

    //Загрузка данных из NBT
    static String loadDataFromNBT(EntityPlayer player) {
        return player.getEntityData().hasKey(key)
                ? player.getEntityData().getString(key)
                : "";
    }

    //Установка значений игрока при заходе в мир
    @SubscribeEvent
    static void onPlayerLogIn(PlayerEvent.PlayerLoggedInEvent event) {
        String data = loadDataFromNBT(event.player);
        setRaw(event.player, data);

        //Установка значений уже для AbilityManager
        AbilityManagerEvents.onPlayerLogIn(event);
    }

    //Сохранение значений игрока при выходе из мира
    @SubscribeEvent
    static void onPlayerLogOut(PlayerEvent.PlayerLoggedOutEvent event) {
        saveDataToNBT(event.player);
    }

    //Восстановление значений при смерти игрока
    @SubscribeEvent
    static void playerClone(net.minecraftforge.event.entity.player.PlayerEvent.Clone event) {
        EntityPlayer player = event.getEntityPlayer();
        EntityPlayer oldPlayer = event.getOriginal();

        setRaw(player, getRaw(oldPlayer));
    }
}
