package net.hegedus.binocular.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;



public class OverlayGuiHandler
  implements IGuiHandler
{
  public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    System.out.println("ServerGui: " + Integer.toString(ID));
    
    return null;
  }



  
  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    System.out.println("ClientGui: " + Integer.toString(ID));


    
    return null;
  }
}
