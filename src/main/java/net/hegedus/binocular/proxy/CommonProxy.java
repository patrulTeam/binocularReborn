package net.hegedus.binocular.proxy;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;


public class CommonProxy
{
  private static final Map<String, NBTTagCompound> extendedEntityData = new HashMap();


  
  public void registerRenderThings() {}


  
  public void registerKeyBinding() {}


  
  public void registerTileEntitySpecialRenderer() {}


  
  public void onZoom(ItemStack stack, EntityPlayer player) {}

  
  public void resetSavedFOV() {}

  
  public boolean isZooming() { return false; }





  
  public static void storeEntityData(String name, NBTTagCompound compound) {
    extendedEntityData.put(name, compound);
    System.out.println("-----> CommonProxy.storeEntityData...");
  }




  
  public static NBTTagCompound getEntityData(String name) { return (NBTTagCompound)extendedEntityData.remove(name); }
}
