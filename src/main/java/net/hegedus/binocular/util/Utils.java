//if error maybe here, so get the backup and redeobfuscate
package net.hegedus.binocular.util;

import java.io.File;
import java.util.List;
import net.hegedus.binocular.Binocular;
import net.hegedus.binocular.items.BinocularItem;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.config.Configuration;





public class Utils
{
  public static BinocularItem getBinocularItem(Minecraft mc) {
    BinocularItem tavcso = null;
    
    try {
      if (mc.player.isHandActive() && mc.player.getActiveItemStack().getItem() == Binocular.itemBinocular) {
        tavcso = (BinocularItem)mc.player.getActiveItemStack().getItem();
      } else {
        tavcso = getBinocularFromInventory(mc);
      } 
    } catch (Exception exception) {}

    
    return tavcso;
  }

  
  private static BinocularItem getBinocularFromInventory(Minecraft mc) {
    try {
      InventoryPlayer inventory = mc.player.inventory;
      for (int i = 0; i < inventory.getSizeInventory(); i++) {
        ItemStack curStack = inventory.getStackInSlot(i);
        if (curStack != null && 
          curStack.getItem() instanceof BinocularItem) {
          return (BinocularItem)curStack.getItem();
        }
      } 
    } catch (Exception exception) {}
    
    return null;
  }




  
  public static void initConfig() {
    if (!Binocular.isConfigLoaded.booleanValue()) {
      System.out.println("LOAD CONFIG...");
      
      try {
        String sWorldName = "";
        if (Minecraft.getMinecraft().isSingleplayer()) {
          sWorldName = DimensionManager.getCurrentSaveRootDirectory().getName();
        }






        
        System.out.println("WORLD NAME: " + sWorldName);
        
        String sConfigFile = Binocular.configPath.replace(".cfg", "_" + sWorldName + ".cfg");
        
        Binocular.config = new Configuration(new File(sConfigFile));
        Binocular.config.load();
        
        Binocular.proxy.registerKeyBinding();
        
        Binocular.config.save();
        
        Binocular.isConfigLoaded = Boolean.valueOf(true);
      }
      catch (Exception e) {
        System.out.println("Hiba: " + e.getMessage());
      } 
    } 
  }



  
  public static RayTraceResult getMouseOverExtended(Minecraft mc, float dist) {
    Entity theRenderViewEntity = mc.getRenderViewEntity();
    AxisAlignedBB theViewBoundingBox = new AxisAlignedBB(theRenderViewEntity.posX - 0.5D, theRenderViewEntity.posY - 0.0D, theRenderViewEntity.posZ - 0.5D, theRenderViewEntity.posX + 0.5D, theRenderViewEntity.posY + 1.5D, theRenderViewEntity.posZ + 0.5D);






    
    RayTraceResult returnMOP = null;
    if (mc.world != null) {
      double var2 = dist;
      returnMOP = theRenderViewEntity.rayTrace(var2, 0.0F);
      double calcdist = var2;
      Vec3d pos = theRenderViewEntity.getPositionEyes(0.0F);
      var2 = calcdist;
      if (returnMOP != null) {
        calcdist = returnMOP.hitVec.distanceTo(pos);
      }
      
      Vec3d lookvec = theRenderViewEntity.getLook(0.0F);
      Vec3d var8 = pos.addVector(lookvec.x * var2, lookvec.y * var2, lookvec.z * var2);
      Entity pointedEntity = null;
      float var9 = 1.0F;
      
      List<Entity> list = mc.world.getEntitiesWithinAABBExcludingEntity(theRenderViewEntity, theViewBoundingBox








          
          .expand(lookvec.x * var2, lookvec.y * var2, lookvec.z * var2)

          
          .expand(var9, var9, var9));
      
      double d = calcdist;
      
      for (Entity entity : list) {
        if (entity.canBeCollidedWith()) {
          float bordersize = entity.getCollisionBorderSize();
          AxisAlignedBB aabb = new AxisAlignedBB(entity.posX - (entity.width / 2.0F), entity.posY, entity.posZ - (entity.width / 2.0F), entity.posX + (entity.width / 2.0F), entity.posY + entity.height, entity.posZ + (entity.width / 2.0F));





          
          aabb.expand(bordersize, bordersize, bordersize);
          RayTraceResult mop0 = aabb.calculateIntercept(pos, var8);





          
          if (aabb.contains(pos)) {
            if (0.0D < d || d == 0.0D) {
              pointedEntity = entity;
              d = 0.0D;
            }  continue;
          }  if (mop0 != null) {
            double d1 = pos.distanceTo(mop0.hitVec);
            
            if (d1 < d || d == 0.0D) {
              pointedEntity = entity;
              d = d1;
            } 
          } 
        } 
      } 
      
      if (pointedEntity != null && (d < calcdist || returnMOP == null)) {
        returnMOP = new RayTraceResult(pointedEntity);
      }
    } 
    return returnMOP;
  }
}
