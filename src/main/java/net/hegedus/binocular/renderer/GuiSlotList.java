package net.hegedus.binocular.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;
















public class GuiSlotList extends GuiSlot
{
  protected Minecraft field_148161_k;
  protected int field_148149_f;
  private String[] strings;
  private GuiOverlayScreen gui;
  
  public GuiSlotList(GuiOverlayScreen gui) {
    super(gui.field_146297_k, 300, 200, 32, 200, 24);
    this.field_148149_f = 24;
    this.field_148161_k = gui.field_146297_k;
    this.gui = gui;
    
    this.strings = new String[] { "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven" };
  }


  
  protected int func_148127_b() { return this.strings.length; }


  
  protected void func_148144_a(int index, boolean twice, int p_148144_3_, int p_148144_4_) {
    System.out.println("Clicked: " + Integer.toString(index));
    System.out.println(twice);
    
    if (twice)
    {
      this.field_148161_k.displayGuiScreen(this.gui);
    }
  }


  
  protected boolean func_148131_a(int par1) { return false; }


  
  protected int func_148138_e() { return func_148127_b() * 24; }



  
  protected void func_148123_a() { this.gui.drawDefaultBackground(); }
  
  protected void func_192637_a(int p_192637_1_, int p_192637_2_, int p_192637_3_, int p_192637_4_, int p_192637_5_, int p_192637_6_, float p_192637_7_) {}



@Override
protected int getSize() {
	// TODO Auto-generated method stub
	return 0;
}



@Override
protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
	// TODO Auto-generated method stub
	
}



@Override
protected boolean isSelected(int slotIndex) {
	// TODO Auto-generated method stub
	return false;
}



@Override
protected void drawBackground() {
	// TODO Auto-generated method stub
	
}



@Override
protected void drawSlot(int slotIndex, int xPos, int yPos, int heightIn, int mouseXIn, int mouseYIn,
		float partialTicks) {
	// TODO Auto-generated method stub
	
}
}
