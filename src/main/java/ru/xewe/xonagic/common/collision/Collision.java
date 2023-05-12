package ru.xewe.xonagic.common.collision;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

public class Collision {
    public static boolean onCollide(World world, AxisAlignedBB collider, AxisAlignedBB... collidingBoxes) {
        return onCollideAABB(collider, collidingBoxes) || onCollideBlock(collider, world);
    }

    public static boolean onCollideAABB(AxisAlignedBB collider, AxisAlignedBB... collidingBoxes){
        for (AxisAlignedBB bound : collidingBoxes) {
            if (collider.intersects(bound)) {
                return true;
            }
        }
        return false;
    }

    public static boolean onCollideBlock(AxisAlignedBB collider, World world){
        return world.checkBlockCollision(collider);
    }

    public static List<Entity> onCollideEntity(AxisAlignedBB collider, World world, Entity exception){
        return world.getEntitiesWithinAABB(Entity.class, collider, a -> a != exception);
    }
}
