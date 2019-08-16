package net.hegedus.binocular;

import com.google.gson.JsonObject;
import net.hegedus.binocular.handlers.OverlayGuiHandler;
import net.hegedus.binocular.items.BinocularItem;
import net.hegedus.binocular.proxy.CommonProxy;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = "binocular", version = "1.2", name = "Binocular", acceptedMinecraftVersions = "[1.12.2]")
public class Binocular
{
  public static final String modID = "binocular";
  public static final String version = "1.2";
  public static final String modName = "BinocularReborn";
  public static String configPath;
  public static Configuration config;
  public static Boolean isConfigLoaded = Boolean.valueOf(false);
  
  public static BinocularItem itemBinocular;
  
  public static SimpleNetworkWrapper network;
  
  @Instance("binocular")
  public static Binocular instance;
  
  @SidedProxy(clientSide = "net.hegedus.binocular.proxy.ClientProxy", serverSide = "net.hegedus.binocular.proxy.CommonProxy")
  public static CommonProxy proxy;
  
  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    configPath = event.getSuggestedConfigurationFile().getPath();
    
    itemBinocular = new BinocularItem("binocular", "binocular");
    
    CraftingHelper.register(new ResourceLocation("binocular:binocular.json"), new IRecipeFactory()
        {
          public IRecipe parse(JsonContext context, JsonObject json)
          {
            return CraftingHelper.getRecipe(json, context);
          }
        });
    network = NetworkRegistry.INSTANCE.newSimpleChannel("BinocularTP");
    network.registerMessage(net.hegedus.binocular.util.TeleportMessage.Handler.class, net.hegedus.binocular.util.TeleportMessage.class, 0, Side.SERVER);
  }

  
  @EventHandler
  public void init(FMLInitializationEvent event) {
    NetworkRegistry.INSTANCE.registerGuiHandler(this, new OverlayGuiHandler());
    proxy.registerRenderThings();
  }

  
  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {}

  
  @EventHandler
  public void load(FMLInitializationEvent event) { isConfigLoaded = Boolean.valueOf(false); }
}