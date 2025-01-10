package com.name.disarmtripwire;

import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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
    @Mod.EventBusSubscriber(modid = MODID)
    public static class ServerModEvents {

        @SubscribeEvent
        public static void onBlockBreak(BlockEvent.BreakEvent event) {
            if (event.getState().is(Blocks.TRIPWIRE) && event.getState().getValue(ATTACHED) && !event.getPlayer().getMainHandItem().isEmpty() && event.getPlayer().getMainHandItem().canPerformAction(ToolActions.SHEARS_DISARM)) {
                event.getLevel().setBlock(event.getPos(), event.getState().setValue(DISARMED, true), Block.UPDATE_INVISIBLE);
                event.setCanceled(true);
            }
        }
    }
}
