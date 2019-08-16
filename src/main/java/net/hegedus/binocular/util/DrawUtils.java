package net.hegedus.binocular.util;

import java.awt.Color;
import java.awt.Font;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;






public class DrawUtils
{
  private static float LINE_WIDTH = 1.0F;
  private static Font FONT = new Font("SansSerif", 0, 10);

  
  private static FontRenderer font;

  
  private static ScaledResolution resolution;


  
  public static void setFont(Font f) { FONT = f; }






  
  public static Font getFont() { return FONT; }


  
  public static void setResolution(ScaledResolution sr) { resolution = sr; }

  
  public static void setFontRenderer(FontRenderer fr) { font = fr; }










  
  public static void drawText(String text, int x, int y, int color) { font.drawString(text, x, y, color); }


  
  public static void scaledText(String text, float sizePercent, int x, int y, int color) {
    if (resolution.getScaleFactor() == 1) {
      sizePercent = 1.0F;
    }
    float sizeFactor = 1.0F / sizePercent;
    
    int new_x = (int)(x * sizeFactor);
    int new_y = (int)(y * sizeFactor);
    
    GL11.glPushMatrix();
    GL11.glScalef(sizePercent, sizePercent, sizePercent);
    font.drawString(text, x, y, color);
    
    GL11.glPopMatrix();
  }










  
  public static void drawRect(int x, int y, int width, int height, Color color) {
    GL11.glPushMatrix();
    GL11.glColor4f(getR(color), getG(color), getB(color), getA(color));
    GL11.glLineWidth(LINE_WIDTH);
    GL11.glBegin(2);
    GL11.glVertex2i(x, y);
    GL11.glVertex2i(x + width, y);
    GL11.glVertex2i(x + width, y + height);
    GL11.glVertex2i(x, y + height);
    GL11.glEnd();
    GL11.glPopMatrix();
  }
  
  public static void fillRect(int x, int y, int width, int height, Color color) {
    GL11.glPushMatrix();
    GL11.glColor4f(getR(color), getG(color), getB(color), getA(color));
    GL11.glBegin(7);
    GL11.glVertex2i(x, y);
    GL11.glVertex2i(x + width, y);
    GL11.glVertex2i(x + width, y + height);
    GL11.glVertex2i(x, y + height);
    GL11.glEnd();
    GL11.glPopMatrix();
  }








  
  public static void startClip(int x, int y, int width, int height) {
    GL11.glEnable(3089);
    GL11.glScissor(x, y, width, height);
  }




  
  public static void endClip() { GL11.glDisable(3089); }


  
  public static float getR(Color color) { return color.getRed() / 255.0F; }


  
  public static float getG(Color color) { return color.getGreen() / 255.0F; }


  
  public static float getB(Color color) { return color.getBlue() / 255.0F; }


  
  public static float getA(Color color) { return color.getAlpha() / 255.0F; }
}
