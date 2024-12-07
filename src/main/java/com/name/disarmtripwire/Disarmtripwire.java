package com.name.disarmtripwire;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TripWireBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

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
            if (event.getState().is(Blocks.TRIPWIRE) && !event.getPlayer().getMainHandItem().isEmpty() && event.getPlayer().getMainHandItem().canPerformAction(ToolActions.SHEARS_DISARM)) {
                event.getLevel().setBlock(event.getPos(), event.getState().setValue(DISARMED, true), Block.UPDATE_INVISIBLE);
                event.setCanceled(true);
            }
        }
    }
}
