package net.hegedus.binocular.handlers;
import net.hegedus.binocular.Binocular;
import net.hegedus.binocular.items.BinocularItem;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ZoomTickHandler {
  private Minecraft mc;
  
  public ZoomTickHandler(Minecraft mc) {
    this.configLoading = Boolean.valueOf(false);






















































































    
    this.fovModifierHand = 0.0F;
    this.mc = mc;
  } private Boolean configLoading; public float fovModifierHand;
  public void setZoom(EntityPlayer player) {
    this.fovModifierHand = Binocular.itemBinocular.zoomFactor;
    
    ObfuscationReflectionHelper.setPrivateValue(net.minecraft.client.renderer.EntityRenderer.class, this.mc.entityRenderer, Float.valueOf(this.fovModifierHand), new String[] { "fovModifierHand", "field_78507_R" });
  }
  @SubscribeEvent
  public void onItemPickup(PlayerEvent.ItemPickupEvent event) { System.out.println("You just picked up: " + event.pickedUp.toString()); }
  
  public void resetZoom() { this.fovModifierHand = 0.0F; }
  
  @SubscribeEvent
  public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
    boolean bShowOverlay = Binocular.itemBinocular.isUsing();
    if (!bShowOverlay)
      bShowOverlay = getZoomLock(); 
    if (bShowOverlay) {
      setZoom(this.mc.player);
    } else {
      resetZoom();
    } 
  }
  
  private boolean getZoomLock() {
    boolean bRet = false;
    if (this.mc.player != null) {
      InventoryPlayer inventory = this.mc.player.inventory;
      for (int i = 0; i < inventory.getSizeInventory(); i++) {
        ItemStack curStack = inventory.getStackInSlot(i);
        if (curStack != null && curStack.getItem() instanceof BinocularItem)
          bRet = ((BinocularItem)curStack.getItem()).zoomLocked; 
      } 
    } 
    return bRet;
  }
}
