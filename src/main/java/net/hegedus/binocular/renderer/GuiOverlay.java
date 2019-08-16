package net.hegedus.binocular.renderer;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.hegedus.binocular.Binocular;
import net.hegedus.binocular.items.BinocularItem;
import net.hegedus.binocular.util.DrawUtils;
import net.hegedus.binocular.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;














@SideOnly(Side.CLIENT)
public class GuiOverlay
  extends Gui
{
  private Minecraft mc;
  private FontRenderer font;
  private double ovWidth;
  private double ovHeight;
  private double ovChangeRatio;
  private static final List<RenderGameOverlayEvent.ElementType> BLOCKED_ELEMENT_TYPES = Arrays.asList(new RenderGameOverlayEvent.ElementType[] { RenderGameOverlayEvent.ElementType.ARMOR, RenderGameOverlayEvent.ElementType.EXPERIENCE, RenderGameOverlayEvent.ElementType.FOOD, RenderGameOverlayEvent.ElementType.HEALTH, RenderGameOverlayEvent.ElementType.HEALTHMOUNT, RenderGameOverlayEvent.ElementType.AIR });








  
  public GuiOverlay(Minecraft mc) {
    this.mc = mc;
    this.font = this.mc.fontRenderer;
    this.mc.entityRenderer.setupOverlayRendering();
    DrawUtils.setFontRenderer(this.font);
  }
  
  @SubscribeEvent
  public void renderScreen(RenderGameOverlayEvent.Post event) {
    if (event.isCancelable() || event.getType() != RenderGameOverlayEvent.ElementType.TEXT) {
      return;
    }
    
    try {
      Utils.initConfig();
      
      boolean bShowOverlay = Binocular.itemBinocular.isUsing();
      if (!bShowOverlay)
      {


        
        if (this.mc.player.isHandActive() && this.mc.player.getActiveItemStack().getItem() == Binocular.itemBinocular) {
          bShowOverlay = true;
          System.out.println("[renderScreen] Showing Overlay");
        } 
      }








      
      if (!bShowOverlay) {
        bShowOverlay = getZoomLock();
      }
      
      if (bShowOverlay) {
        init();
        overlay();
        infos(false);
        compass();
      } else {
        
        Binocular.itemBinocular.resetUse();
        
        if (Binocular.itemBinocular.quickInfo) {
          infos(true);
        }
      } 
    } catch (Exception e) {
      System.out.println("[renderScreen] " + e.getMessage());
    } 
  }
  
  private String compass() {
    String direction = "";

    
    try {
      int yaw = (int)this.mc.player.rotationYaw;
      if (yaw < 0)
        yaw += 360; 
      yaw %= 360;
      int facing = yaw / 45;

















      
      String irany = "";
      switch (facing) {
        case 7:
          irany = I18n.translateToLocal("direction.se");
          break;
        case 0:
          irany = I18n.translateToLocal("direction.s");
          break;
        case 1:
          irany = I18n.translateToLocal("direction.sw");
          break;
        case 2:
          irany = I18n.translateToLocal("direction.w");
          break;
        case 3:
          irany = I18n.translateToLocal("direction.nw");
          break;
        case 4:
          irany = I18n.translateToLocal("direction.n");
          break;
        case 5:
          irany = I18n.translateToLocal("direction.ne");
          break;
        case 6:
          irany = I18n.translateToLocal("direction.e");
          break;
      } 

      
      direction = irany;
    }
    catch (Exception e) {
      System.out.println("compass: " + e.getMessage());
    } 
    
    return direction;
  }
  
  private boolean getZoomLock() {
    boolean bRet = false;
    
    try {
      InventoryPlayer inventory = this.mc.player.inventory;
      for (int i = 0; i < inventory.getSizeInventory(); i++) {
        ItemStack curStack = inventory.getStackInSlot(i);
        if (curStack != null && 
          curStack.getItem() instanceof BinocularItem) {
          bRet = ((BinocularItem)curStack.getItem()).zoomLocked;
        }
      }
    
    } catch (Exception exception) {}
    
    return bRet;
  }
  
  protected String getTime() {
    String time = "";







    
    try {
      MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
      if (server.worlds.length > 0) {
        time = StringUtils.ticksToElapsedTime((int)server.worlds[0].getWorldTime());
      }
    } catch (Exception exception) {}
    
    return time;
  }
  
  public static String formatTime(Long time) {
    int hours24 = (int)(time.longValue() / 1000L + 6L) % 24;
    int hours = hours24 % 12;
    int minutes = (int)((float)time.longValue() / 16.666666F % 60.0F);
    
    return String.format("%02d:%02d %s", new Object[] { Integer.valueOf((hours < 1) ? 12 : hours), Integer.valueOf(minutes), (hours24 < 12) ? "AM" : "PM" });
  }









  
  private void text(String text, int x, int y, int color) { DrawUtils.drawText(text, x, y, color); }

  
  private void scaledText(String text, float sizePercent, int x, int y, int color) {
    ScaledResolution scaledresolution = new ScaledResolution(this.mc);
    if (scaledresolution.getScaleFactor() == 1) {
      sizePercent = 1.0F;
    }
    float sizeFactor = 1.0F / sizePercent;
    
    int new_x = (int)(x * sizeFactor);
    int new_y = (int)(y * sizeFactor);
    
    GL11.glPushMatrix();
    GL11.glScalef(sizePercent, sizePercent, sizePercent);
    text(text, new_x, new_y, color);
    GL11.glPopMatrix();
  }
  
  private void init() {
    ScaledResolution scaledresolution = new ScaledResolution(this.mc);
    int scaledWidth = scaledresolution.getScaledWidth();
    int scaledHeight = scaledresolution.getScaledHeight();



    
    double ovRatioX = 1.6D;
    double ovRatioY = 4.26D;

    
    this.ovWidth = this.mc.displayWidth / ovRatioX;

    
    this.ovChangeRatio = 640.0D / this.ovWidth;

    
    this.ovHeight = 240.0D / this.ovChangeRatio;

    
    if (scaledHeight > scaledWidth) {
      this.ovHeight = this.mc.displayHeight / ovRatioY;
      this.ovChangeRatio = 240.0D / this.ovHeight;
      this.ovWidth = 640.0D / this.ovChangeRatio;
    } 
  }


  
  private void infos(boolean quickInfo) {
    this.mc.entityRenderer.setupOverlayRendering();
    ScaledResolution scaledresolution = new ScaledResolution(this.mc);
    int scaledWidth = scaledresolution.getScaledWidth();
    int scaledHeight = scaledresolution.getScaledHeight();
    int scaleFactor = scaledresolution.getScaleFactor();
    
    int centerX = scaledWidth / 2;
    int centerY = scaledHeight / 2;
    
    DecimalFormat dfPos = new DecimalFormat("0.0000");
    DecimalFormat dfDist = new DecimalFormat("0.##");
    DecimalFormat df2 = new DecimalFormat("0.00");



    
    double dist = 0.0D;




    
    int zoomOfsX = 88;
    int zoomOfsY = 94;
    
    int distOfsX = 37;
    int distOfsY = 94;
    
    int timeOfsX = 298;
    int timeOfsY = 105;
    
    int locOfsX = 15;
    int locOfsY = 15;
    
    int icoOfsX = 120;
    int icoOfsY = 40;
    
    int entOfsX = 20;
    int entOfsY = 30;
    
    int destOfsX = 75;
    int destOfsY = 40;
    
    int destCOfsY = 20;


    
    if (scaleFactor > 2) {
      zoomOfsX -= 2;
      distOfsX += 3;
      icoOfsX = 180;
      icoOfsY = 60;
      destOfsX = 120;
      destOfsY = 55;
      destCOfsY = 25;
    } 
    if (scaleFactor == 4) {
      icoOfsX = 240;
      icoOfsY = 70;
      destOfsX = 150;
      destOfsY = 60;
      destCOfsY = 30;
    } 
    
    double zoomPosX = zoomOfsX / this.ovChangeRatio / scaleFactor;
    double zoomPosY = zoomOfsY / this.ovChangeRatio / scaleFactor;
    
    double distPosX = distOfsX / this.ovChangeRatio / scaleFactor;
    double distPosY = distOfsY / this.ovChangeRatio / scaleFactor;
    
    double timePosX = timeOfsX / this.ovChangeRatio / scaleFactor;
    double timePosY = timeOfsY / this.ovChangeRatio / scaleFactor;
    
    double locPosX = centerX - (this.ovWidth / 2.0D - locOfsX) / scaleFactor;
    double locPosY = centerY + (this.ovHeight / 2.0D + locOfsY) / scaleFactor;

    
    double icoPosX = (centerX - icoOfsX / scaleFactor);
    double icoPosY = centerY - (this.ovHeight / 2.0D + icoOfsY) / scaleFactor;
    
    double destPosX = (centerX - destOfsX / scaleFactor);
    double destPosY = centerY - (this.ovHeight / 2.0D + destOfsY) / scaleFactor;
    
    double destCPosY = centerY - (this.ovHeight / 2.0D + destOfsY - destCOfsY) / scaleFactor;
    
    double compassPosX = (int)(centerX - timePosX);
    double compassPosY = destCPosY;











    
    double px = Double.valueOf((this.mc.getRenderViewEntity()).posX).doubleValue();
    double py = Double.valueOf((this.mc.getRenderViewEntity().getEntityBoundingBox()).minY).doubleValue();
    double pz = Double.valueOf((this.mc.getRenderViewEntity()).posZ).doubleValue();
    
    String sPlayerPos = String.format("XYZ: %.3f / %.5f / %.3f", new Object[] {

          
          Double.valueOf(px), 
          Double.valueOf(py), 
          Double.valueOf(pz)
        });









    
    String sCurrentTime = "";
    try {
      Long time = Long.valueOf(this.mc.world.provider.getWorldTime());
      sCurrentTime = formatTime(time);
    }
    catch (Exception exception) {}




    
    String sCompass = compass();




    
    String sElementName = "";
    String sElementPos = "";
    String sElementInfo = "";






    
    try {
      RayTraceResult mp = getMouseOverExtended(this.mc, 1000.0F);

      
      if (mp != null) {
        BlockPos pos = mp.getBlockPos();
        
        if (mp.typeOfHit == RayTraceResult.Type.ENTITY) {
          pos = new BlockPos(mp.hitVec.x, mp.hitVec.y, mp.hitVec.z);
          
          EntityLiving entity = GetEntityLookAt(this.mc, 1000);
          if (entity != null) {
            sElementPos = String.format("x:%s, y:%s, z:%s", new Object[] { dfPos.format(entity.posX), dfPos.format(entity.posY), dfPos.format(entity.posZ) });
            sElementInfo = " �7(�a" + Integer.toString((int)entity.getMaxHealth()) + "�7" + "/" + "�a" + Integer.toString((int)entity.getHealth()) + "�7" + ")" + "�f";
            
            sElementName = entity.toString();
            if (sElementName.indexOf("['") > -1) {
              sElementName = sElementName.substring(sElementName.indexOf("['") + 2);
              if (sElementName.indexOf("'") > -1) {
                sElementName = sElementName.substring(0, sElementName.indexOf("'"));
              }
            } 
            
            if (!quickInfo) {
              GL11.glPushAttrib(1048575);
              GL11.glPushMatrix();
              RenderLiving renderLiving = (RenderLiving)Minecraft.getMinecraft().getRenderManager().getEntityClassRenderObject(entity.getClass());
              
              GL11.glTranslatef(((int)icoPosX + entOfsX), ((int)icoPosY + entOfsY), 0.0F);
              GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
              GL11.glScaled(25.0D, 25.0D, 25.0D);
              GL11.glScalef(0.5F, 0.5F, 0.5F);
              renderLiving.doRender(entity, 1.0D, 1.0D, 1.0D, 0.0F, 0.0F);
              GL11.glPopMatrix();
              GL11.glPopAttrib();
            } 

            
            int xp_width = (int)(entity.getHealth() / entity.getMaxHealth()) * 50;


            
            sElementName = sElementName + sElementInfo;
          } else {
            System.out.println("[entity not found]");
          
          }
        
        }
        else {
          
          IBlockState state = this.mc.player.world.getBlockState(pos);
          Block block = state.getBlock();







          
          sElementName = block.getLocalizedName();
          if (sElementName.endsWith(".name")) {
            sElementName = block.getUnlocalizedName().substring(block.getUnlocalizedName().indexOf(".") + 1);
          }

          
          if (!quickInfo && 
            !sElementName.equals("air")) {
            
            try {
              ItemStack itemStack = new ItemStack(block);
              
              GlStateManager.pushAttrib();
              GlStateManager.pushMatrix();

              
              this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);


              
              if (itemStack.getItem() != null) {
                IBakedModel bakedModel = this.mc.getRenderItem().getItemModelMesher().getItemModel(itemStack);

                
                GlStateManager.translate(icoPosX + 8.0D, icoPosY + 8.0D, this.zLevel);
                GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
                GlStateManager.scale(16.0F, 16.0F, 1.0F);
                
                Minecraft.getMinecraft().getRenderItem().renderItem(new ItemStack(block), bakedModel);
              } else {
                
                IBlockState blockState = this.mc.world.getBlockState(pos);
                IBakedModel bakedModel = this.mc.getBlockRendererDispatcher().getModelForState(blockState);
                
                Tessellator tessellator = Tessellator.getInstance();
                GlStateManager.translate(icoPosX + 16.0D, icoPosY + 16.0D, this.zLevel);
                GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
                GlStateManager.scale(16.0F, 16.0F, 1.0F);
                
                tessellator.getBuffer().begin(7, DefaultVertexFormats.ITEM);
                
                Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(this.mc.player.world, bakedModel, blockState, new BlockPos(0, 0, 0), tessellator



                    
                    .getBuffer(), false, 0L);
                
                tessellator.draw();
              } 

              
              GlStateManager.popAttrib();
              GlStateManager.popMatrix();
            }
            catch (Exception ei) {
              System.out.println("[renderIcon] [" + sElementName + "] err: " + ei.getMessage());
            } 
          }
        } 


        
        sElementPos = String.format("x:%d y:%d z:%d", new Object[] { Integer.valueOf(pos.getX()), Integer.valueOf(pos.getY()), Integer.valueOf(pos.getZ()) });

        
        double dx = pos.getX() - px;
        double dy = pos.getY() - py;
        double dz = pos.getZ() - pz;
        dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
      
      }

    
    }
    catch (Exception exception) {}





    
    String sDistance = ((dist < 5.0D) ? "< 5" : dfDist.format(dist)) + "m";




    
    float zoomFactor = Binocular.itemBinocular.zoomFactor;
    zoomFactor = 100.0F - zoomFactor * 100.0F;

    
    if (quickInfo) {
      String sInfo = sCurrentTime;
      
      if (sCompass != "") {
        sInfo = sInfo + ", " + sCompass;
      }
      if (sElementName != "") {
        sInfo = sInfo + ", �e" + sElementName + "�f";
      }
      if (sElementPos != "") {
        sInfo = sInfo + "�7 [�f" + sElementPos + "�7" + "]" + "�f";
      }
      if (sDistance != "") {
        sInfo = sInfo + " " + sDistance;
      }
      scaledText(sInfo, 0.75F, 5, 5, Color.white.getRGB());
    }
    else {
      
      scaledText(I18n.translateToLocal("zoom.in") + ": F", 0.75F, 10, 10, Color.white.getRGB());
      scaledText(I18n.translateToLocal("zoom.out") + ": V", 0.75F, 10, 20, Color.white.getRGB());
      scaledText(I18n.translateToLocal("zoom.reset") + ": R", 0.75F, 10, 30, Color.white.getRGB());
      scaledText(I18n.translateToLocal("zoom.lock") + ": L", 0.75F, 10, 40, Color.white.getRGB());
      scaledText(I18n.translateToLocal("zoom.nightvision") + ": N", 0.75F, 10, 50, Color.white.getRGB());
      scaledText(I18n.translateToLocal("zoom.waypoints") + ": P", 0.75F, 10, 60, Color.white.getRGB());
      scaledText(I18n.translateToLocal("zoom.quickInfo") + ": I", 0.75F, 10, 70, Color.white.getRGB());

      
      scaledText(sPlayerPos, 0.75F, (int)locPosX, (int)locPosY, Color.white.getRGB());

      
      scaledText(sCurrentTime, 0.75F, (int)(centerX - timePosX), (int)(centerY - timePosY), Color.white.getRGB());

      
      scaledText(sCompass, 0.75F, (int)compassPosX, (int)compassPosY, Color.white.getRGB());

      
      scaledText(sDistance, 0.75F, (int)(centerX + distPosX), (int)(centerY + distPosY), Color.green.getRGB());

      
      scaledText(dfDist.format(zoomFactor), 0.75F, (int)(centerX - zoomPosX), (int)(centerY + zoomPosY), Color.green.getRGB());

      
      scaledText(sElementName, 0.75F, (int)destPosX, (int)destPosY, Color.yellow.getRGB());
      scaledText(sElementPos, 0.75F, (int)destPosX, (int)destCPosY, Color.white.getRGB());
    } 
  }















































































































  
  public static void pre(double x, double y, double z) {
    GlStateManager.pushMatrix();
    
    RenderHelper.disableStandardItemLighting();
    GlStateManager.enableBlend();
    GlStateManager.blendFunc(770, 771);
    
    if (Minecraft.isAmbientOcclusionEnabled()) {
      GL11.glShadeModel(7425);
    } else {
      GL11.glShadeModel(7424);
    } 
    GlStateManager.translate(x, y, z);
  }
  
  public static void post() {
    GlStateManager.disableBlend();
    RenderHelper.enableStandardItemLighting();
    GlStateManager.popMatrix();
  }
  
  private void test() {
    DecimalFormat dfPos = new DecimalFormat("0.0000");

    
    RayTraceResult mop = getMouseOverExtended(this.mc, 100.0F);
    if (mop != null) {
      BlockPos pos = new BlockPos(mop.hitVec.x, mop.hitVec.y - 1.0D, mop.hitVec.z);
      IBlockState state = this.mc.player.world.getBlockState(pos);
      Block block = state.getBlock();
      
      String sElementPos = "x: " + dfPos.format(pos.getX()) + ", y: " + dfPos.format(pos.getY()) + ", z: " + dfPos.format(pos.getZ());
      String sElementName = block.getLocalizedName();
      if (sElementName.endsWith(".name")) {
        sElementName = block.getUnlocalizedName().substring(block.getUnlocalizedName().indexOf(".") + 1);
      }
      System.out.println("[block] " + sElementName + " " + sElementPos);
    } 
  }



  
  public static List GetNearEntityWithPlayer(EntityLivingBase player, int range, boolean needToBeSeen) {
    double px = player.posX;
    double py = player.posY;
    double pz = player.posZ;
    
    BlockPos posMin = new BlockPos(px - range, py - range, pz - range);
    BlockPos posMax = new BlockPos(px + range, py + range, pz + range);









    
    AxisAlignedBB boundingBox = new AxisAlignedBB(posMin, posMax);
    List l = player.world.getEntitiesWithinAABB(EntityLivingBase.class, boundingBox);
    
    List result = new ArrayList();
    for (int i = 0; i < l.size(); i++) {
      EntityLivingBase x = (EntityLivingBase)l.get(i);
      if (x != null && 
        x.getDistance(player) <= range && (!needToBeSeen || x.canEntityBeSeen(player))) {
        result.add(x);
      }
    } 
    
    return result;
  }
  
  public EntityLiving GetEntityLookAt(Minecraft mc, int max_dis) {
    RayTraceResult mop = getMouseOverExtended(mc, max_dis);
    if (mop != null && 
      mop.entityHit != null && mop.entityHit.hurtResistantTime == 0 && 
      mop.entityHit != mc.player) {
      return (EntityLiving)mop.entityHit;
    }

    
    return null;
  }


  
  public RayTraceResult getMouseOverExtended(Minecraft mc, float dist) {
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
  
  public void overlay() {
    this.mc.entityRenderer.setupOverlayRendering();
    ScaledResolution scaledresolution = new ScaledResolution(this.mc);
    int scaledWidth = scaledresolution.getScaledWidth();
    int scaledHeight = scaledresolution.getScaledHeight();
    int scaleFactor = scaledresolution.getScaleFactor();
    
    int centerX = scaledWidth / 2;
    int centerY = scaledHeight / 2;
    
    GL11.glEnable(3042);
    GL11.glDisable(2929);
    GL11.glDepthMask(false);
    GL11.glBlendFunc(770, 771);
    
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GL11.glDisable(3008);
    
    ResourceLocation texture = new ResourceLocation("binocular", "textures/items/binocularblur.png");
    
    this.mc.getTextureManager().bindTexture(texture);

    
    boolean isHorizontal = true;

    
    double aspect_ratio = 1.0D;

    
    int w = scaledWidth;
    int h = scaledWidth;
    int ofs_x = 0;
    int ofs_y = 0;
    
    if (h < scaledHeight) {
      
      h = scaledHeight;
      w = (int)(scaledHeight * aspect_ratio);
      isHorizontal = false;
    } 

    
    if (w > scaledWidth)
      ofs_x = (w - scaledWidth) / 2; 
    if (h > scaledHeight) {
      ofs_y = (h - scaledHeight) / 2;
    }
    int tl_x = 0 - ofs_x;
    int tl_y = 0 - ofs_y;
    
    int bl_x = 0 - ofs_x;
    int bl_y = h - ofs_y;
    
    int br_x = w - ofs_x;
    int br_y = h - ofs_y;
    
    int tr_x = w - ofs_x;
    int tr_y = 0 - ofs_y;
    
    Tessellator t = Tessellator.getInstance();





    
    BufferBuilder vertexbuffer = t.getBuffer();
    vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
    vertexbuffer.pos(tl_x, tl_y, 0.0D).tex(0.0D, 0.0D).endVertex();
    vertexbuffer.pos(bl_x, bl_y, 0.0D).tex(0.0D, 1.0D).endVertex();
    vertexbuffer.pos(br_x, br_y, 0.0D).tex(1.0D, 1.0D).endVertex();
    vertexbuffer.pos(tr_x, tr_y, 0.0D).tex(1.0D, 0.0D).endVertex();
    t.draw();



































































































    
    GL11.glDepthMask(true);
    GL11.glEnable(2929);
    GL11.glEnable(3008);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GL11.glDisable(3042);
  }
}
