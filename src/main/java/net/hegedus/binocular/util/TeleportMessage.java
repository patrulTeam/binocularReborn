//if error maybe here, so get the backup and redeobfuscate

package net.hegedus.binocular.util;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class TeleportMessage
  implements IMessage {
  private String text;
  private double x;
  private double y;
  private double z;
  
  public TeleportMessage() {}
  
  public TeleportMessage(String text) { this.text = text; }

  
  public TeleportMessage(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }


  
  public void fromBytes(ByteBuf buf) {
    try {
      this.x = buf.readDouble();
      this.y = buf.readDouble();
      this.z = buf.readDouble();
    
    }
    catch (Exception e) {
      System.out.println("[fromBytes] " + e.getMessage());
    } 
  }


  
  public void toBytes(ByteBuf buf) {
    try {
      buf.writeDouble(this.x);
      buf.writeDouble(this.y);
      buf.writeDouble(this.z);
    
    }
    catch (Exception e) {
      System.out.println("[toBytes] " + e.getMessage());
    } 
  }


  
  public static class Handler
    extends Object
    implements IMessageHandler<TeleportMessage, IMessage>
  {
    public IMessage onMessage(TeleportMessage message, MessageContext ctx) {
      try {
        System.out.println(String.format("Received from %s", new Object[] { (ctx.getServerHandler()).player.getDisplayName() }));







        
        (ctx.getServerHandler()).player.setPositionAndUpdate(message.x, message.y, message.z);
      }
      catch (Exception e) {
        System.out.println("[onMessage] " + e.getMessage());
      } 
      
      return null;
    }
  }
}
