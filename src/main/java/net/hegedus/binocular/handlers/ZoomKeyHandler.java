package net.hegedus.binocular.handlers;

import net.hegedus.binocular.items.BinocularItem;
import net.hegedus.binocular.proxy.ClientProxy;
import net.hegedus.binocular.renderer.GuiOverlayScreen;
import net.hegedus.binocular.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;



public class ZoomKeyHandler
{
  private Minecraft mc;
  private BinocularItem tavcso;
  private boolean bZoomIn = false;
  private boolean bZoomOut = false;
  private boolean bZoomReset = false;
  private boolean bNightV = false;
  private boolean bZoomLock = false;
  private boolean bWayPoints = false;
  private boolean bQuickInfo = false;
  private float zoomStep = 0.01F;
  private float zoomMin = 0.015F;
  private float zoomMax = 1.5F;
  private float zoomReset = 0.25F;
  
  public static final int ZOOM_IN = 0;
  
  public static final int ZOOM_OUT = 1;
  
  public static final int ZOOM_RESET = 2;
  public static final int NIGHT_VISION = 3;
  public static final int ZOOM_LOCK = 4;
  public static final int WAY_POINTS = 5;
  public static final int QUICK_INFO = 6;
  
  public void init() {
    this.mc = Minecraft.getMinecraft();
    this.tavcso = Utils.getBinocularItem(this.mc);
  }
  
  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void onKeyInput(InputEvent.KeyInputEvent event) {
    init();
    if (this.mc.inGameHasFocus && this.tavcso != null) {
      Utils.initConfig();
      
      KeyBinding[] keyBindings = ClientProxy.keyBindings;
      
      this.bZoomIn = keyBindings[0].isPressed();
      this.bZoomOut = keyBindings[1].isPressed();
      this.bZoomReset = keyBindings[2].isPressed();
      this.bNightV = keyBindings[3].isPressed();
      this.bZoomLock = keyBindings[4].isPressed();
      this.bWayPoints = keyBindings[5].isPressed();
      this.bQuickInfo = keyBindings[6].isPressed();
      
      boolean bIsUsing = this.tavcso.isUsing();

      
      if (this.bZoomIn || this.bZoomOut || this.bZoomReset || this.bNightV) {
        if (bIsUsing) {
          if (this.bZoomIn)
          {
            setZoomFactor(-this.zoomStep);
          }
          if (this.bZoomOut)
          {
            setZoomFactor(this.zoomStep);
          }
          if (this.bZoomReset) {
            setZoomFactor(this.zoomReset);
          }
          if (this.bNightV) {
            this.tavcso.nightVision = !this.tavcso.nightVision;
            
            if (this.tavcso.nightVision) {
              System.out.println("NightVision ON");




              
              this.mc.player.addPotionEffect(new PotionEffect(Potion.getPotionById(16), 5, 1));
            } else {
              System.out.println("NightVision OFF");



              
              this.mc.player.removePotionEffect(Potion.getPotionById(16));
            } 
          } 
        } 
      } else if (this.bWayPoints && this.tavcso.zoomLocked) {
        showWayPoints();
      }
      else if (this.bQuickInfo) {
        this.tavcso.quickInfo = !this.tavcso.quickInfo;
      } 
      
      if (this.bZoomLock)
      {
        setZoomLock();
      }
    } 
  }

  
  private void showWayPoints() { this.mc.displayGuiScreen(new GuiOverlayScreen(this.mc)); }

  
  private void setZoomLock() {
    this.tavcso.zoomLocked = !this.tavcso.zoomLocked;
    if (!this.tavcso.zoomLocked) {
      this.tavcso.resetUse();
    } else {
      this.tavcso.setUse();
    } 
  }

















  
  public void setZoomFactor(float correction) {
    float zoomFactor = this.tavcso.zoomFactor;
    
    if (correction == this.zoomReset) {
      zoomFactor = this.zoomReset;
    } else {
      zoomFactor += correction;
      if (zoomFactor < this.zoomMin) {
        zoomFactor = this.zoomMin;
      } else if (zoomFactor > this.zoomMax) {
        zoomFactor = this.zoomMax;
      } 
    } 
    this.tavcso.zoomFactor = zoomFactor;
  }
}
