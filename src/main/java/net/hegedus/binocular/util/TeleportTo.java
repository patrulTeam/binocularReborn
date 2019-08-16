//if error maybe here, so get the backup and redeobfuscate
package net.hegedus.binocular.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class TeleportTo
  extends Teleporter
{
  private final WorldServer worldServerInstance;
  private double x;
  private double y;
  private double z;
  
  public TeleportTo(WorldServer world, double x, double y, double z) {
    super(world);
    this.worldServerInstance = world;
    this.x = x;
    this.y = y;
    this.z = z;
  }












  
  public void func_180266_a(Entity pEntity, float rotationYaw) {
    this.worldServerInstance.getBlockState(new BlockPos((int)this.x, (int)this.y, (int)this.z));
    
    pEntity.setPosition(this.x, this.y, this.z);
  }
  
  public static void teleport(EntityPlayer player, int dim, double x, double y, double z) {
    try {
      MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
      
      WorldServer mServer = server.worlds[0];
      
      Side side = FMLCommonHandler.instance().getEffectiveSide();
      if (side == Side.SERVER && 
        player instanceof EntityPlayerMP) {
        
        EntityPlayerMP playerMP = (EntityPlayerMP)player;
        if (player.getRidingEntity() == null && player instanceof EntityPlayer) {
          FMLCommonHandler.instance().getMinecraftServerInstance();






          
          playerMP.getServer().getPlayerList().transferPlayerToDimension(playerMP, dim, new TeleportTo(playerMP.getServer().getWorld(dim), x, y, z));//if errors getServer should be changed to mcServer; 
        }
      
      } 
    } catch (Exception exception) {}
  }
}
