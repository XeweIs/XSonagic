package ru.xewe.xonagic.common.ability.air;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServer;
import ru.xewe.xonagic.common.ability.Ability;
import ru.xewe.xonagic.common.ability.AbilityInfo;
import ru.xewe.xonagic.common.collision.Collision;
import ru.xewe.xonagic.common.enums.ElementEnum;
import ru.xewe.xonagic.common.enums.TypeCast;
import ru.xewe.xonagic.common.packets.SPacketPlayerMotion;
import ru.xewe.xonagic.common.registry.NetworkHandler;

import java.util.List;
import java.util.stream.Collectors;

@AbilityInfo(
        name = "AirSuction",
        displayName = "Air Suction",
        element = ElementEnum.Air,
        coolDown = 5,
        repeat = 32,
        activations = {TypeCast.RightBlock, TypeCast.RightEmpty, TypeCast.RightEntity},
        color = TextFormatting.WHITE,
        combo = "XV"
)
public class AirSuction extends Ability {
    Vec3d startPlayerPosition;
    Vec3d startPlayerLook;
    float startPlayerEyeHeight;
    RayTraceResult startPlayerRayTrace;

    @Override
    public void execute(EntityPlayer player) {
        Vec3d vec3d = player.getPositionEyes(1);
        Vec3d vec3d1 = player.getLook(1);
        Vec3d vec3d2 = vec3d.addVector(vec3d1.x * 16, vec3d1.y * 16, vec3d1.z * 16);

        startPlayerPosition = player.getPositionVector();
        startPlayerEyeHeight = player.getEyeHeight();
        startPlayerLook = player.getLookVec();
        startPlayerRayTrace = player.world.rayTraceBlocks(vec3d, vec3d2, false, false, true);
        if(startPlayerRayTrace != null) {
            for (short i = 0; i < startPlayerPosition.distanceTo(startPlayerRayTrace.hitVec); i++) {
                Vec3d pos = startPlayerPosition.add(startPlayerLook.scale(i)).addVector(0f, player.getEyeHeight(), 0f);
                AxisAlignedBB aabbHit = new AxisAlignedBB(pos.x, pos.y, pos.z, pos.x, pos.y, pos.z).grow(0.5f);
                // Чтобы вычислить ближайшую сущность по траектории и переопределить стартовую позицию как найденную
                List<Entity> entities = player.world.getEntitiesWithinAABB(Entity.class, aabbHit)
                        .stream().filter(a -> a != player).collect(Collectors.toList());
                if (!entities.isEmpty()) {
                    startPlayerRayTrace.hitVec = pos;
                    break;
                }
            }
        }

        super.execute(player);
    }

    @Override
    public boolean onUpdate() {

        Vec3d direction = startPlayerPosition.addVector(0, startPlayerEyeHeight, 0).subtract(startPlayerRayTrace.hitVec).normalize();
        Vec3d pos = startPlayerRayTrace.hitVec.add(direction.scale((32 - repeat) / 2f));
        AxisAlignedBB aabb = new AxisAlignedBB(pos.x, pos.y, pos.z, pos.x, pos.y, pos.z).grow(0.5f);
        if ((32 - repeat) / 2f < startPlayerPosition.distanceTo(startPlayerRayTrace.hitVec)) {
            if (!player.world.isRemote) {
                ((WorldServer) player.world).spawnParticle(EnumParticleTypes.SPELL, pos.x, pos.y, pos.z,
                        2, 0, 0, 0, 0f);

                if (repeat % 4 == 0) {
                    player.world.playSound(null, pos.x, pos.y, pos.z, SoundEvents.ENTITY_CREEPER_DEATH, SoundCategory.PLAYERS, 1, 1f);
                }
            }

            //Нахожу сущности по пути чтобы их притягивать
            for (Entity target : Collision.onCollideEntity(aabb, player.world, player)) {
                Vec3d vec = startPlayerLook.scale(-0.9f);
                target.motionX = vec.x;
                target.motionY = vec.y;
                target.motionZ = vec.z;

                //Отправка этого пакета обязательна, ибо motion для игроков необходимо делать со стороны клиента
                //Проверка !player.world.isRemote здесь не нужна, потому что тип EntityPlayerMP получаем только на стороне сервера
                if (target instanceof EntityPlayerMP) {
                    NetworkHandler.NETWORK.sendTo(new SPacketPlayerMotion(startPlayerLook.scale(-0.9f)), (EntityPlayerMP) target);
                }
            }
        } else {
            return true;
        }

        return false;
    }

    @Override
    protected void onExit() {
        if(!player.isCreative()) {
            this.coolDown = (int) startPlayerPosition.distanceTo(startPlayerRayTrace.hitVec);
        }
        super.onExit();
    }
}
