package net.hegedus.binocular.proxy;

import net.hegedus.binocular.Binocular;
import net.hegedus.binocular.handlers.GenericEventsHandler;
import net.hegedus.binocular.handlers.ZoomKeyHandler;
import net.hegedus.binocular.handlers.ZoomTickHandler;
import net.hegedus.binocular.renderer.GuiOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;





public class ClientProxy
  extends CommonProxy
{
  public static KeyBinding[] keyBindings;
  private static final String KEY_CAT = "key.categories.binocular";
  private static final String[] keys = { "zoomIn", "zoomOut", "zoomReset", "nightVision", "zoomLock", "wayPoints", "quickInfo" };






  
  private static final int[] keyValues = { 33, 47, 19, 49, 38, 25, 23 };







  
  public void registerRenderThings() {
    registerItemModel(Binocular.itemBinocular);
    
    FMLCommonHandler.instance().bus().register(new ZoomTickHandler(Minecraft.getMinecraft()));
    FMLCommonHandler.instance().bus().register(new ZoomKeyHandler());
    
    MinecraftForge.EVENT_BUS.register(new GuiOverlay(Minecraft.getMinecraft()));
    MinecraftForge.EVENT_BUS.register(new GenericEventsHandler());
  }


  
  public void registerRenderers() {}

  
  public static void registerItemModel(Item item) { Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName().toString(), "inventory")); }


  
  public void registerKeyBinding() {
    keyBindings = new KeyBinding[keys.length];
    for (int i = 0; i < keys.length; i++) {
      keyBindings[i] = new KeyBinding("key." + keys[i], Binocular.config.get("keys", keys[i], keyValues[i], "").getInt(), "key.categories.binocular");
      
      ClientRegistry.registerKeyBinding(keyBindings[i]);
    } 
  }
}
