package net.hegedus.binocular.items;

import net.hegedus.binocular.Binocular;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class BinocularItem extends Item {
  private boolean isUsing;
  
  /**
 * @param unlocalizedName
 * @param registryName
 */
public BinocularItem(String unlocalizedName, String registryName) {
    this.isUsing = false;
    
    this.nightVision = false;
    this.zoomLocked = false;
    this.quickInfo = false;
    
    this.zoomFactor = 0.25F;





    
    setCreativeTab(CreativeTabs.TOOLS);
    
    setUnlocalizedName(unlocalizedName);
    setRegistryName(new ResourceLocation("binocular", registryName));





    
    ForgeRegistries.ITEMS.register(this);
  }


  
  public boolean nightVision;

  
  public boolean zoomLocked;
  
  public boolean quickInfo;
  
  public float zoomFactor;

  
  public boolean isUsing() { return this.isUsing; }


  
  public void setUse() { this.isUsing = true; }

  
  public void resetUse() {
    this.isUsing = false;
    
    if (this.nightVision) {
      this.nightVision = false;



      
      (Minecraft.getMinecraft()).player.removePotionEffect(Potion.getPotionById(16));
    } 
  }

























  
  public ActionResult<ItemStack> func_77659_a(World worldIn, EntityPlayer entityPlayer, EnumHand hand) {
    ItemStack itemStack = entityPlayer.getHeldItem(hand);
    
    System.out.println("\n onItemRightClick(" + itemStack.getUnlocalizedName() + "; WorldIsRemote=" + worldIn.isRemote + "; EntityPlayer=" + entityPlayer.getName() + "; EnumHand=" + hand.name() + ")\n");








    
    entityPlayer.setActiveHand(hand);
    ReflectionHelper.setPrivateValue(EntityLivingBase.class, entityPlayer, itemStack, new String[] { "activeItemStack", "field_184627_bm" });
    ReflectionHelper.setPrivateValue(EntityLivingBase.class, entityPlayer, Integer.valueOf(100), new String[] { "activeItemStackUseCount", "field_184628_bn" });
    
    this.isUsing = true;
    entityPlayer.openGui(Binocular.instance, 0, entityPlayer.world, (int)entityPlayer.posX, (int)entityPlayer.posY, (int)entityPlayer.posZ);
    
    return new ActionResult(EnumActionResult.PASS, itemStack);
  }




  
  public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
    try {
      System.out.println("onPlayerStoppedUsing");
      super.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
      
      if (!this.zoomLocked) {
        resetUse();
      }
    } catch (Exception e) {
      System.out.println("onPlayerStoppedUsing: " + e.getMessage());
    } 
  }
}
