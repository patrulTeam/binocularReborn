package net.hegedus.binocular.handlers;

import net.hegedus.binocular.util.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

































































































public class GenericEventsHandler
{
  @SubscribeEvent
  public void onEntityConstructing(EntityEvent.EntityConstructing event) {
    if (event.getEntity() instanceof EntityPlayer) {
      Side side = FMLCommonHandler.instance().getEffectiveSide();
      if (side == Side.CLIENT && 
        (EntityPlayer)event.getEntity() instanceof net.minecraft.client.entity.EntityPlayerSP) {






        
        System.out.println("-----> onEntityConstructing");

        
        Utils.initConfig();
      } 
    } 
  }
}
