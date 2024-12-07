package com.name.disarmtripwire;

import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.event.level.BlockEvent;
import org.slf4j.Logger;

import static net.minecraft.world.level.block.TripWireBlock.ATTACHED;
import static net.minecraft.world.level.block.TripWireBlock.DISARMED;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Disarmtripwire.MODID)
public class Disarmtripwire {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "disarmtripwire";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID)
    public static class ServerModEvents {
        @SubscribeEvent
        public static void onBlockBreak(BlockEvent.BreakEvent event) {
            if (event.getState().is(Blocks.TRIPWIRE) && event.getState().getValue(ATTACHED) && !event.getPlayer().getMainHandItem().isEmpty() && event.getPlayer().getMainHandItem().canPerformAction(ItemAbilities.SHEARS_DISARM)) {
                event.getLevel().setBlock(event.getPos(), event.getState().setValue(DISARMED, true), Block.UPDATE_INVISIBLE);
                event.setCanceled(true);
            }
        }
    }
}
