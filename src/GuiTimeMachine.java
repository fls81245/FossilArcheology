package net.minecraft.src;

import java.util.Iterator;

import org.lwjgl.opengl.GL11;

public class GuiTimeMachine extends GuiContainer
{
    private TileEntityTimeMachine timeMachineInstance;
	final int SQR_WIDTH=34;
	final int SQR_HEIGHT=13;
	final int SQR_POSX=131;
    public GuiTimeMachine(InventoryPlayer par1InventoryPlayer, TileEntityTimeMachine par2TileEntityFurnace)
    {
        super(new ContainerTimeMachine(par1InventoryPlayer, par2TileEntityFurnace));
        this.timeMachineInstance = par2TileEntityFurnace;
    }
    public void initGui()
    {
    	super.initGui();
        this.controlList.clear();

        //this.controlList.add(new GuiSenceSqr(1, this.guiLeft+SQR_POSX, this.guiTop+18,  SQR_WIDTH, SQR_HEIGHT, "Start" ));
        //this.controlList.add(new GuiSenceSqr(2,guiLeft+SQR_POSX,this.guiTop+57, SQR_WIDTH, SQR_HEIGHT, "Memory"));
        GuiButton var2;

    }
    /**
     * Draw the foreground layer for the GuiContainer (everythin in front of the items)
     */
    protected void drawGuiContainerForegroundLayer()
    {
    	final int COLOR_DATA_DISPLAY=0xFF0000;
    	
    	String secondWord=String.valueOf(this.timeMachineInstance.getChargeLevel()/10)+"%";
    	int secondWordPosX=(SQR_WIDTH-this.fontRenderer.getStringWidth(secondWord))/2;
        //this.fontRenderer.drawString(StatCollector.translateToLocal("container.furnace"), 60, 6, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
        this.fontRenderer.drawString(secondWord, 131+secondWordPosX, 40, COLOR_DATA_DISPLAY);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
    	final int GUI_CLOCK_SIZE=75;
        int var4 = this.mc.renderEngine.getTexture("/TimeMachine/TMGui.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(var4);
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
        int var7;
        float tmp;
        tmp=((float)(this.timeMachineInstance.MAX_CHARGED-this.timeMachineInstance.getChargeLevel())/(float)this.timeMachineInstance.MAX_CHARGED);
        var7 = (int) (tmp*(float)GUI_CLOCK_SIZE);
        this.drawTexturedModalRect(var5 + 51, var6 + 6 , 176, 1, 75, var7);

    }
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        switch (par1GuiButton.id)
        {
            case 1:
            	if (this.timeMachineInstance.isCharged() && !this.timeMachineInstance.isRestoring) this.timeMachineInstance.startWork();
            case 2:
            	if (!this.timeMachineInstance.isRestoring)this.timeMachineInstance.startMemory();
        }
    }
    public void updateScreen()
    {
        super.updateScreen();
        ((GuiButton)this.controlList.get(0)).enabled=this.timeMachineInstance.isCharged() && !this.timeMachineInstance.isRestoring;
        ((GuiButton)this.controlList.get(1)).enabled=!this.timeMachineInstance.isRestoring;
    }
}
