package net.hegedus.binocular.renderer;
import java.awt.Color;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import org.lwjgl.opengl.GL11;

import net.hegedus.binocular.Binocular;
import net.hegedus.binocular.items.BinocularItem;
import net.hegedus.binocular.util.DrawUtils;
import net.hegedus.binocular.util.TeleportMessage;
import net.hegedus.binocular.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.math.RayTraceResult;

public class GuiOverlayScreen extends GuiScreen implements GuiList.IGuiList {
  public Minecraft field_146297_k;
  private BinocularItem tavcso;
  protected GuiSlotList list;
  private ArrayList<String> nameList;
  private ArrayList<String> coordList;
  private int selectedIndex;
  private GuiButton btAdd;
  private GuiButton btSet;
  private GuiButton btDel;
  private GuiButton btGo;
  private GuiTextField txtName;
  private GuiTextField txtCoord;
  private int iBgWidth;
  private int iBgHeight;
  private int iBgX;
  private int iBgY;
  private int iScrollPadding;
  private int iScrollX;
  private int iScrollY;
  private int iScrollW;
  private int iScrollH;
  private int iScrollBarW;
  private int iScrollBarX;
  private int iScrollBarY;
  
  public GuiOverlayScreen(Minecraft mc) {
    this.nameList = new ArrayList();
    this.coordList = new ArrayList();
    this.selectedIndex = 0;









    
    this.iBgWidth = 240;
    this.iBgHeight = 155;
    this.iBgX = 0;
    this.iBgY = 0;
    
    this.iScrollPadding = 5;
    this.iScrollX = 0;
    this.iScrollY = 0;
    this.iScrollW = 0;
    this.iScrollH = 0;
    this.iScrollBarW = 10;
    this.iScrollBarX = 0;
    this.iScrollBarY = 0;
    this.iScrollButtonOffset = 0;
    this.iScrollButtonPrev = 0;
    this.bScrollButtonPressed = false;
    this.iScrollListOffset = 0;
    
    this.iElementHeight = 14;
    this.iElementPadding = 5;
    
    this.iNameX = 0;
    this.iNameY = 0;
    this.iCoordX = 0;
    this.iCoordY = 0;
    
    this.iTxtWidth = this.iBgWidth - 10;
    this.iTxtHeight = 12;
    this.iBtWidth = 45;
    this.iBtHeight = 20;
    
    this.iBtCoordTop = 0;
    this.iBtCoord1Left = 0;
    this.iBtCoord2Left = 0;
    this.iBtCoordWidth = 12;
    this.iBtCoordHeight = this.iTxtHeight + 3;
    this.iTxtCoordWidth = this.iTxtWidth - 2 * (this.iBtCoordWidth + 5);
    this.iBtCoord1Bg = 0;
    this.iBtCoord2Bg = 0;
    
    this.iBtX = 0;
    this.iBtY = 0;





    
    this.field_146297_k = mc;
    DrawUtils.setResolution(new ScaledResolution(mc));
    DrawUtils.setFontRenderer(this.field_146297_k.fontRenderer);
  }
  private int iScrollButtonOffset; private int iScrollButtonPrev; private boolean bScrollButtonPressed; private int iScrollListOffset; private int iElementHeight; private int iElementPadding; private int iNameX; private int iNameY; private int iCoordX; private int iCoordY; private int iTxtWidth; private int iTxtHeight; private int iBtWidth; private int iBtHeight; private int iBtCoordTop; private int iBtCoord1Left; private int iBtCoord2Left; private int iBtCoordWidth; private int iBtCoordHeight; private int iTxtCoordWidth; private int iBtCoord1Bg; private int iBtCoord2Bg; private int iBtX; private int iBtY; private GuiTextField activeTextField;
  private String getPos(int iType) {
    DecimalFormatSymbols decimalSymbol = new DecimalFormatSymbols(Locale.getDefault());
    decimalSymbol.setDecimalSeparator('.');
    NumberFormat fmt = NumberFormat.getInstance(Locale.US);
    fmt.setGroupingUsed(false);
    if (fmt instanceof DecimalFormat) {
      ((DecimalFormat)fmt).applyPattern("0.0000");
    }
    String sPos = "";
    if (iType == 1) {
      double px = this.field_146297_k.player.getPosition().getX();
      double py = this.field_146297_k.player.getPosition().getY();
      double pz = this.field_146297_k.player.getPosition().getZ();


      
      sPos = fmt.format(px) + " " + fmt.format(py) + " " + fmt.format(pz);







    
    }
    else {







      
      RayTraceResult mop = Utils.getMouseOverExtended(this.field_146297_k, 1000.0F);
      
      if (mop != null) {
        sPos = fmt.format(mop.hitVec.x) + " " + fmt.format(mop.hitVec.y) + " " + fmt.format(mop.hitVec.z);
      }
    } 
    return sPos;
  }

  
  public void func_73866_w_() {
    initCalc();
    
    this.txtName = new GuiTextField(1, this.field_146297_k.fontRenderer, this.iNameX, this.iNameY, this.iTxtWidth, this.iTxtHeight);
    this.txtName.setFocused(false);
    this.txtName.setMaxStringLength(40);
    this.txtName.setText("name");
    this.txtName.setEnableBackgroundDrawing(true);
    
    this.txtCoord = new GuiTextField(1, this.field_146297_k.fontRenderer, this.iCoordX, this.iCoordY, this.iTxtCoordWidth, this.iTxtHeight);
    this.txtCoord.setFocused(false);
    this.txtCoord.setMaxStringLength(40);
    this.txtCoord.setText(getPos(1));
    this.txtCoord.setEnableBackgroundDrawing(true);
    
    this.btAdd = new GuiButton(1, this.iBtX, this.iBtY, this.iBtWidth, this.iBtHeight, "Add"); this.iBtY += this.btAdd.height + 5;
    this.btSet = new GuiButton(2, this.iBtX, this.iBtY, this.iBtWidth, this.iBtHeight, "Set"); this.iBtY += this.btAdd.height + 5;
    this.btDel = new GuiButton(3, this.iBtX, this.iBtY, this.iBtWidth, this.iBtHeight, "Remove"); this.iBtY += this.btAdd.height + 5;
    this.btGo = new GuiButton(4, this.iBtX, this.iBtY, this.iBtWidth, this.iBtHeight, "Go");

    
    this.buttonList.clear();
    this.buttonList.add(this.btAdd);
    this.buttonList.add(this.btSet);
    this.buttonList.add(this.btDel);
    this.buttonList.add(this.btGo);









    
    loadConfig();
  }

  
  private void loadConfig() {
    String[] names = Binocular.config.get("WayPoints", "namesList", new String[0]).getStringList();
    String[] coords = Binocular.config.get("WayPoints", "coordsList", new String[0]).getStringList();
    
    this.nameList = new ArrayList(Arrays.asList(names));
    this.coordList = new ArrayList(Arrays.asList(coords));
    
    System.out.println("---------> CONFIG LOAD: " + Integer.toString(names.length));
  }
  
  private void saveConfig() {
    String[] names = (String[])this.nameList.toArray(new String[this.nameList.size()]);
    String[] coords = (String[])this.coordList.toArray(new String[this.coordList.size()]);
    
    Binocular.config.get("WayPoints", "namesList", new String[0]).set(names);
    Binocular.config.get("WayPoints", "coordsList", new String[0]).set(coords);
    Binocular.config.save();
    
    System.out.println("---------> CONFIG SAVE: " + Integer.toString(names.length));
  }

  
  public void initCalc() {
    this.iBgX = (this.width - this.iBgWidth) / 2;
    this.iBgY = (this.height - this.iBgHeight) / 2;

    
    this.iNameX = this.iBgX + 5;
    this.iNameY = this.iBgY + 5;
    
    this.iCoordX = this.iBgX + 5;
    this.iCoordY = this.iNameY + this.iTxtHeight + 5;


    
    this.iBtX = this.iBgX + this.iBgWidth - this.iBtWidth - 5;
    this.iBtY = this.iBgY + 2 * (this.iTxtHeight + 5) + 10;






    
    this.iScrollX = this.iBgX + this.iScrollPadding;
    this.iScrollY = this.iCoordY + this.iTxtHeight + this.iScrollPadding;
    
    this.iScrollW = this.iBgWidth - this.iBtWidth - this.iScrollBarW - this.iScrollPadding - 2 * this.iScrollPadding;
    this.iScrollH = this.iBgHeight - 2 * this.iTxtHeight + 10 - 2 * this.iScrollPadding;
    
    this.iScrollBarX = this.iScrollX + this.iScrollW + 2;
    this.iScrollBarY = this.iScrollY;
    
    this.iBtCoordTop = this.iCoordY - 1;
    this.iBtCoord1Left = this.iCoordX + this.iTxtCoordWidth + 5;
    this.iBtCoord2Left = this.iBtCoord1Left + this.iBtCoordWidth + 5;
  }













  
  private int listHeightAbsolute() { return this.iElementPadding + this.nameList.size() * (this.iElementHeight + this.iElementPadding); }






  
  private float getScrollButtonSize() {
    float fButtonHeight = 0.0F;

    
    int iListHeight = listHeightAbsolute();

    
    if (iListHeight > this.iScrollH) {
      
      float fRatio = iListHeight / this.iScrollH;




      
      fButtonHeight = this.iScrollH / fRatio;
      if (fRatio < 1.0F) {
        fButtonHeight = 0.0F;
      }
    } 
    
    return fButtonHeight;
  }
  
  public void keyTyped(char c, int i) {
    try {
      super.keyTyped(c, i);
    } catch (IOException e) {
      System.out.println(e.getMessage());
    } 
    
    if (this.activeTextField != null)
      this.activeTextField.textboxKeyTyped(c, i); 
  }
  
  public void mouseClicked(int i, int j, int k) {
    try {
      super.mouseClicked(i, j, k);
    }
    catch (IOException e) {
      System.out.println(e.getMessage());
    } 
    
    boolean itemClicked = false;
    
    this.bScrollButtonPressed = false;
    this.iScrollButtonPrev = 0;
    float fButtonSize = getScrollButtonSize();
    
    if (i >= this.txtName.x && i <= this.txtName.x + this.txtName.width && j >= this.txtName.y && j <= this.txtName.y + this.txtName.height) {
      this.activeTextField = this.txtName;
    }
    else if (i >= this.txtCoord.x && i <= this.txtCoord.x + this.txtCoord.width && j >= this.txtCoord.y && j <= this.txtCoord.y + this.txtCoord.height) {
      this.activeTextField = this.txtCoord;
    }
    else if (i >= this.iScrollX && i <= this.iScrollX + this.iScrollW && j >= this.iScrollY && j <= this.iScrollY + this.iScrollH) {
      itemClicked = true;
    }
    else if (i >= this.iScrollBarX && i <= this.iScrollBarX + this.iScrollBarW && j >= this.iScrollBarY + this.iScrollButtonOffset && j <= this.iScrollBarY + this.iScrollButtonOffset + (int)fButtonSize) {
      this.bScrollButtonPressed = true;
      this.iScrollButtonPrev = j;
      System.out.println("clicked: " + Integer.toString(j));
    } else if (i >= this.iBtCoord1Left && i <= this.iBtCoord1Left + this.iBtCoordWidth && j >= this.iBtCoordTop && j <= this.iBtCoordTop + this.iBtCoordHeight) {
      
      this.txtCoord.setText(getPos(1));
    }
    else if (i >= this.iBtCoord2Left && i <= this.iBtCoord2Left + this.iBtCoordWidth && j >= this.iBtCoordTop && j <= this.iBtCoordTop + this.iBtCoordHeight) {
      
      this.txtCoord.setText(getPos(2));
    } 
    
    if (this.activeTextField != null) {
      this.activeTextField.mouseClicked(i, j, k);
    }
    if (this.activeTextField == this.txtName)
      this.txtCoord.setFocused(false); 
    if (this.activeTextField == this.txtCoord) {
      this.txtName.setFocused(false);
    }
    this.selectedIndex = getActiveItem(i, j);
    if (this.selectedIndex > 0 && itemClicked) {
      this.txtName.setText((String)this.nameList.get(this.selectedIndex - 1));
      this.txtCoord.setText((String)this.coordList.get(this.selectedIndex - 1));
    } 
  }





  
  public void mouseClickMove(int mouseX, int mouseY, int button, long timeSince) {
    super.mouseClickMove(mouseX, mouseY, button, timeSince);
    
    if (this.bScrollButtonPressed) {
      
      int iOffs = mouseY - this.iScrollButtonPrev;

      
      if (iOffs != 0) {
        this.iScrollButtonPrev = mouseY;








        
        int iListOuterSize = 0;
        int iScrollOuterSize = 0;
        
        int iListOffset = 0;

        
        if (iOffs > 0) {
          
          iListOuterSize = listHeightAbsolute() - this.iScrollH - this.iScrollListOffset;

          
          iScrollOuterSize = this.iScrollH - (int)getScrollButtonSize() + this.iScrollButtonOffset;

          
          iListOffset = (iScrollOuterSize > 0) ? (int)(Math.ceil((iListOuterSize / iScrollOuterSize)) * iOffs) : 0;







          
          float fButtonSize = getScrollButtonSize();
          if ((int)fButtonSize + this.iScrollButtonOffset + iOffs < this.iScrollH) {
            this.iScrollButtonOffset += iOffs;
          } else {
            this.iScrollButtonOffset = this.iScrollH - (int)fButtonSize;
          } 
          
          if (listHeightAbsolute() - this.iScrollListOffset + iListOffset > this.iScrollH) {
            this.iScrollListOffset += iListOffset;
          } else {
            this.iScrollListOffset = listHeightAbsolute() - this.iScrollH;
          }
        
        }
        else {
          
          iListOuterSize = this.iScrollListOffset;

          
          iScrollOuterSize = this.iScrollButtonOffset;

          
          iListOffset = (iScrollOuterSize > 0) ? (int)(Math.ceil((iListOuterSize / iScrollOuterSize)) * iOffs) : 0;







          
          if (this.iScrollButtonOffset + iOffs >= 0) {
            this.iScrollButtonOffset += iOffs;
          } else {
            this.iScrollButtonOffset = 0;
          } 
          
          if (this.iScrollListOffset + iListOffset >= 0) {
            this.iScrollListOffset += iListOffset;
          } else {
            this.iScrollListOffset = 0;
          } 
        } 
      } 
    } 
  }

  
  public void drawScreen(int x, int y, float f) {
    drawDefaultBackground();






    
    drawRect(this.iBgX, this.iBgY, this.iBgX + this.iBgWidth, this.iBgY + this.iBgHeight, (new Color(50, 170, 170, 70)).getRGB());


    
    this.txtName.drawTextBox();
    this.txtCoord.drawTextBox();

    
    renderList(x, y);
    renderScrollBar();
    
    this.iBtCoord1Bg = (new Color(86, 86, 86, 255)).getRGB();
    this.iBtCoord2Bg = (new Color(86, 86, 86, 255)).getRGB();
    if (x >= this.iBtCoord1Left && x <= this.iBtCoord1Left + this.iBtCoordWidth && y >= this.iBtCoordTop && y <= this.iBtCoordTop + this.iBtCoordHeight) {
      this.iBtCoord1Bg = (new Color(122, 132, 88, 255)).getRGB();
    }
    else if (x >= this.iBtCoord2Left && x <= this.iBtCoord2Left + this.iBtCoordWidth && y >= this.iBtCoordTop && y <= this.iBtCoordTop + this.iBtCoordHeight) {
      this.iBtCoord2Bg = (new Color(122, 132, 88, 255)).getRGB();
    } 
    renderButton(this.iBtCoord1Left, this.iBtCoordTop, this.iBtCoordWidth, this.iBtCoordHeight, this.iBtCoord1Bg, "<");
    renderButton(this.iBtCoord2Left, this.iBtCoordTop, this.iBtCoordWidth, this.iBtCoordHeight, this.iBtCoord2Bg, "<<");


    
    super.drawScreen(x, y, f);
  }
  
  public void renderList(int x, int y) {
    DrawUtils.setFontRenderer(this.field_146297_k.fontRenderer);
    
    ScaledResolution sr = new ScaledResolution(this.field_146297_k);
    int scale = sr.getScaleFactor(), scissorX = this.iScrollX, scissorY = this.iScrollY, scissorWidth = this.iScrollW, scissorHeight = this.iScrollH;
    DrawUtils.startClip(this.iScrollX, this.field_146297_k.displayHeight - (scissorY + scissorHeight) * scale, (scissorWidth + scissorX) * scale, scissorHeight * scale);
    
    int iY = 0;
    for (int i = 0; i < this.nameList.size(); i++) {
      Object o = this.nameList.get(i);

      
      iY = getItemY(i) - this.iScrollListOffset;
      
      DrawUtils.drawText(o.toString(), this.iScrollX + this.iElementPadding, iY, Color.white.getRGB());
    } 

    
    if (this.selectedIndex > 0) {
      GL11.glEnable(3042);
      iY = getItemY(this.selectedIndex - 1) - this.iElementPadding;
      
      drawRect(this.iScrollX, iY - this.iScrollListOffset, this.iScrollX + this.iScrollW, iY + this.iElementHeight + 3 - this.iScrollListOffset, (new Color(255, 255, 255, 70)).getRGB());
    } 
    
    DrawUtils.endClip();
  }
  
  public void renderScrollBar() {
    drawRect(this.iScrollBarX, this.iScrollBarY, this.iScrollBarX + this.iScrollBarW, this.iScrollBarY + this.iScrollH, (new Color(255, 255, 255, 30)).getRGB());

    
    float fButtonSize = getScrollButtonSize();
    drawRect(this.iScrollBarX, this.iScrollBarY + this.iScrollButtonOffset, this.iScrollBarX + this.iScrollBarW, this.iScrollBarY + this.iScrollButtonOffset + (int)fButtonSize, (new Color(255, 255, 255, 80)).getRGB());
  }






  
  public void renderButton(int iLeft, int iTop, int iWidth, int iHeight, int iBg, String text) {
    drawRect(iLeft, iTop, iLeft + iWidth - 1, iTop + 1, (new Color(255, 255, 255, 255)).getRGB());
    drawRect(iLeft, iTop, iLeft + 1, iTop + iHeight, (new Color(255, 255, 255, 255)).getRGB());
    drawRect(iLeft + iWidth - 1, iTop, iLeft + iWidth, iTop + iHeight, (new Color(0, 0, 0, 255)).getRGB());
    drawRect(iLeft, iTop + iHeight - 1, iLeft + iWidth, iTop + iHeight, (new Color(0, 0, 0, 255)).getRGB());
    drawRect(iLeft + 1, iTop + 1, iLeft + iWidth - 1, iTop + iHeight - 1, iBg);
    
    int _left = iLeft + (iWidth - this.field_146297_k.fontRenderer.getStringWidth(text)) / 2 + text.length();
    int _top = iTop + 6;
    
    DrawUtils.scaledText(text, 0.5F, _left * 2, _top * 2, Color.white.getRGB());
  }



  
  protected void func_146284_a(GuiButton btn) {
    if (btn.id == this.btAdd.id) {
      
      this.nameList.add(this.txtName.getText());
      this.coordList.add(this.txtCoord.getText());



      
      this.txtName.setText("");
      this.txtCoord.setText("");
      saveConfig();
    }
    else if (btn.id == this.btSet.id) {
      if (this.selectedIndex > 0)
      {
        this.nameList.set(this.selectedIndex - 1, this.txtName.getText());
        this.coordList.set(this.selectedIndex - 1, this.txtCoord.getText());



        
        saveConfig();
      }
    
    } else if (btn.id == this.btDel.id) {
      if (this.selectedIndex > 0)
      {
        
        float fButtonSizePrev = getScrollButtonSize();
        
        this.nameList.remove(this.selectedIndex - 1);
        this.coordList.remove(this.selectedIndex - 1);
        
        this.selectedIndex = -1;


        
        float fButtonSize = getScrollButtonSize();
        
        int iOffs = (int)(fButtonSizePrev - fButtonSize);
        if (this.iScrollButtonOffset + iOffs > 0) {
          this.iScrollButtonOffset += iOffs;
        } else {
          this.iScrollButtonOffset = 0;
        } 



        
        if (this.iScrollListOffset - this.iElementHeight + this.iElementPadding > 0) {
          this.iScrollListOffset -= this.iElementHeight + this.iElementPadding;
        } else {
          this.iScrollListOffset = 0;
        } 




        
        saveConfig();
      }
    
    } else if (btn.id == this.btGo.id) {

      
      String[] coord = this.txtCoord.getText().split(" ");
      if (coord.length == 3) {
        try {
          this.tavcso = Utils.getBinocularItem(this.field_146297_k);
          this.tavcso.zoomLocked = false;
          this.tavcso.resetUse();







          
          this.field_146297_k.player.closeScreen();





          
          Binocular.network.sendToServer(new TeleportMessage(Double.parseDouble(coord[0]), Double.parseDouble(coord[1]), Double.parseDouble(coord[2])));





        
        }
        catch (Exception e) {
          System.out.println("TP hiba: " + e.getMessage());



          
          this.field_146297_k.player.sendChatMessage("TP error: " + e.getMessage());
        } 
      }
    } 
  }











  
  public void selectItem(int index) {}










  
  public int getItemY(int index) { return this.iScrollY + this.iElementPadding + index * (this.iElementHeight + this.iElementPadding); }


  
  public int getActiveItem(int x, int y) {
    int ret = 0;
    
    if (x >= this.iScrollX && x <= this.iScrollX + this.iScrollW && y >= this.iScrollY && y <= this.iScrollY + this.iScrollH)
    {
      for (int i = 0; i < this.nameList.size(); i++) {
        if (y >= getItemY(i) - this.iScrollListOffset && y <= getItemY(i + 1) - this.iScrollListOffset) {
          ret = i + 1;
        }
      } 
    }
    
    if (ret == 0) {
      ret = this.selectedIndex;
    }
    return ret;
  }



  
  public boolean func_73868_f() { return false; }
  
  public void onElementDoubleClick(GuiList guilist, Object element, int pos) {}
}
