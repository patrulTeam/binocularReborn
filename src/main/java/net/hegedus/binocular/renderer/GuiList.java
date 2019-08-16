package net.hegedus.binocular.renderer;

import java.awt.Color;
import java.util.ArrayList;
import net.hegedus.binocular.util.DrawUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;

public class GuiList {
  private ArrayList<? extends Object> data;
  private int elementHeight;
  private Minecraft mc;
  private FontRenderer font;
  private int xCoord;
  private int yCoord;
  private int width;
  private int height;
  private float scrollPos;
  private int startIndex;
  
  public GuiList(Minecraft mc, int x, int y, int w, int h, int elementHeight, ArrayList<? extends Object> data, IGuiList handler) {
    this.scrollPos = 0.0F;


    
    this.mc = mc;
    this.font = this.mc.fontRenderer;
    
    this.xCoord = x;
    this.yCoord = y;
    this.width = w;
    this.height = h;
    
    this.elementHeight = elementHeight;
    this.data = data;
  }












  
  public void render(int x, int y) {
    DrawUtils.setFontRenderer(this.font);
    
    DrawUtils.drawRect(this.xCoord, this.yCoord, this.width, this.height, Color.yellow);

    
    ScaledResolution sr = new ScaledResolution(this.mc);
    int scale = sr.getScaleFactor(), scissorX = this.xCoord, scissorY = this.yCoord, scissorWidth = this.width, scissorHeight = this.height;
    DrawUtils.startClip(this.xCoord, this.mc.displayHeight - (scissorY + scissorHeight) * scale, (scissorWidth + scissorX) * scale, scissorHeight * scale);

    
    int padding = 5;
    int offset = 0;
    
    for (int i = 0; i < this.data.size(); i++) {
      Object o = this.data.get(i);
      
      int iY = this.yCoord + padding + i * (this.elementHeight + padding);
      
      DrawUtils.drawRect(this.xCoord, iY - padding, this.width, this.elementHeight, Color.red);
      DrawUtils.drawText(o.toString(), this.xCoord + padding, iY, Color.white.getRGB());
    } 
    
    DrawUtils.endClip();
  }
  
  public static interface IGuiList {
    void onElementDoubleClick(GuiList param1GuiList, Object param1Object, int param1Int);
  }
}
