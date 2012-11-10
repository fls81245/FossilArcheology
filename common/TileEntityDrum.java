// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;
import java.util.*;


// Referenced classes of package net.minecraft.src:
//            TileEntity, NBTTagCompound, World, Material

public class TileEntityDrum extends TileEntity
{
	final String DRUM="Drum.";
	final String MSG="Msg.";
	final String ORDER="Order.";
	final String HEAD="Head";
	final String MIDDLE="Middle";
	final String TAIL="Tail";
	final String TREXMSG=MSG+"TRex.";
	final String DINO="Dino.";
	public EnumOrderType Order=EnumOrderType.Stay;
    public TileEntityDrum()
    {
        note = 0;
        previousRedstoneState = false;
    }

    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        //nbttagcompound.setByte("note", note);
		nbttagcompound.setByte("Order", (byte)mod_Fossil.EnumToInt(Order));
    }

    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        /*note = nbttagcompound.getByte("note");
        if(note < 0)
        {
            note = 0;
        }
        if(note > 24)
        {
            note = 24;
        }*/
		Order=EnumOrderType.values()[nbttagcompound.getByte("Order")];
    }


    public void triggerNote(World par1World, int par2, int par3, int par4)
    {
        if (par1World.getBlockMaterial(par2, par3 + 1, par4) == Material.air)
        {
            Material var5 = par1World.getBlockMaterial(par2, par3 - 1, par4);
            byte var6 = 0;

            if (var5 == Material.rock)
            {
                var6 = 1;
            }

            if (var5 == Material.sand)
            {
                var6 = 2;
            }

            if (var5 == Material.glass)
            {
                var6 = 3;
            }

            if (var5 == Material.wood)
            {
                var6 = 4;
            }

            par1World.addBlockEvent(par2, par3, par4,mod_Fossil.Dump.blockID, var6, this.note);
        }
    }
    private String GetOrderString(){
    	return mod_Fossil.GetLangTextByKey(ORDER+Order.toString());
    }
	public void TriggerOrder(EntityPlayer checker){
		Order=Order.Next();
		worldObj.playSoundEffect(xCoord,yCoord,zCoord,"drum_single",8.0F, (float)Math.pow(2D, (double)(Order.ToInt()-1)));
		String msgHead=mod_Fossil.GetLangTextByKey(DRUM+ORDER+HEAD);
		String OrderName=GetOrderString();
		mod_Fossil.ShowMessage(new StringBuilder().append(msgHead).append(OrderName).toString(),checker);
        this.onInventoryChanged();
		
	}
	public boolean SendOrder(int UsingID,EntityPlayer player){
		String Type="";
		String OrderString="";
		final String MSGHEAD=mod_Fossil.GetLangTextByKey(DRUM+MSG+HEAD);
		final String MSGMIDDLE=mod_Fossil.GetLangTextByKey(DRUM+MSG+MIDDLE);
		final String MSGTAIL=mod_Fossil.GetLangTextByKey(DRUM+MSG+TAIL);
		worldObj.playSoundEffect(xCoord,yCoord,zCoord,"drum_triple",8.0F, (float)Math.pow(2D, (double)(Order.ToInt()-1)));
		if (UsingID!=Item.stick.shiftedIndex && UsingID!=Item.bone.shiftedIndex && UsingID!=mod_Fossil.SkullStick.shiftedIndex && UsingID!=Item.arrow.shiftedIndex) return false;
		if(UsingID == Item.stick.shiftedIndex){
			OrderTri();
			Type=EntityDinosaurce.GetNameByEnum(EnumDinoType.Triceratops, true);
		}
		if(UsingID == Item.bone.shiftedIndex){
			OrderRaptor();
			Type=EntityDinosaurce.GetNameByEnum(EnumDinoType.Raptor, true);
		}
		if(UsingID == Item.arrow.shiftedIndex){
			OrderPTS();
			Type=EntityDinosaurce.GetNameByEnum(EnumDinoType.Pterosaur, true);
		}
		if(UsingID == mod_Fossil.SkullStick.shiftedIndex){
			OrderTRex(player);
		}

			OrderString=GetOrderString();
		if (UsingID != mod_Fossil.SkullStick.shiftedIndex){

				mod_Fossil.ShowMessage(new StringBuilder().append(MSGHEAD).append(Type).append(MSGMIDDLE).append(OrderString).append(MSGTAIL).toString(),player);

			return true;
		}else{
			String TmpMsg=mod_Fossil.GetLangTextByKey(DRUM+TREXMSG+String.valueOf(Order.ToInt()+1));
			mod_Fossil.ShowMessage(TmpMsg,player);
			return true;
		}
	}
	private void OrderRaptor(){
		List list = worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityRaptor.class, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(xCoord, yCoord, zCoord, xCoord + 1.0D, yCoord + 1.0D, zCoord + 1.0D).expand(30D, 4D, 30D));
		Iterator iterator = list.iterator();
        do
        {
            if(!iterator.hasNext())
            {
                 break;
            }
            Entity entity1 = (Entity)iterator.next();
            EntityDinosaurce entityDino = (EntityDinosaurce)entity1;
            if (entityDino.isTamed())entityDino.SetOrder(Order);
        } while(true);
	}
	private void OrderPTS(){
		List list = worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityPterosaur.class, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(xCoord, yCoord, zCoord, xCoord + 1.0D, yCoord + 1.0D, zCoord + 1.0D).expand(30D, 4D, 30D));
		Iterator iterator = list.iterator();
        do
        {
            if(!iterator.hasNext())
            {
                 break;
            }
            Entity entity1 = (Entity)iterator.next();
            EntityDinosaurce entityDino = (EntityDinosaurce)entity1;
            if (entityDino.isTamed())entityDino.SetOrder(Order);
        } while(true);
	}
	private void OrderTri(){
		List list = worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityTriceratops.class, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(xCoord, yCoord, zCoord, xCoord + 1.0D, yCoord + 1.0D, zCoord + 1.0D).expand(30D, 4D, 30D));
		Iterator iterator = list.iterator();
        do
        {
            if(!iterator.hasNext())
            {
                 break;
            }
            Entity entity1 = (Entity)iterator.next();
            EntityDinosaurce entityDino = (EntityDinosaurce)entity1;
			if (entityDino.isTamed())entityDino.SetOrder(Order);
        } while(true);
	}
	private void OrderTRex(EntityPlayer player){
		List list = worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityTRex.class, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(xCoord, yCoord, zCoord, xCoord + 1.0D, yCoord + 1.0D, zCoord + 1.0D).expand(50D, 4D, 50D));
		Iterator iterator = list.iterator();
        do
        {
            if(!iterator.hasNext())
            {
                 break;
            }
            Entity entity1 = (Entity)iterator.next();
            EntityTRex entityTRex = (EntityTRex)entity1;
			if (entityTRex.getDinoAge()>=3 && !entityTRex.isTamed()){
				entityTRex.setSelfAngry(true);
				entityTRex.entityToAttack=player;
			}
        } while(true);
	}
    public byte note;

    public boolean previousRedstoneState;
}
