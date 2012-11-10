// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;


// Referenced classes of package net.minecraft.src:
//            EntityAnimal, DataWatcher, NBTTagCompound, World, 
//            EntityPlayer, Item, EntityPigZombie, AchievementList, 
//            EntityLightningBolt

public class EntityPregnantPig extends EntityPig implements IViviparous,IEntityAdditionalSpawnData
{
	public int EmbyoProgress=0;
	public final int EmbyoGrowTime=3000;
	public EnumEmbyos Embyos=null;
    public EntityPregnantPig(World world)
    {
        super(world);
    }
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setInteger("EmbyoProgress", EmbyoProgress);
        nbttagcompound.setByte("Inside", (byte)this.Embyos.ordinal());
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        EmbyoProgress=nbttagcompound.getInteger("EmbyoProgress");
        if (nbttagcompound.hasKey("Inside")){
        	this.Embyos=EnumEmbyos.values()[nbttagcompound.getByte("Inside")];
        }
    }
    public void SetEmbyo(EnumEmbyos target){
    	this.Embyos=target;
    }
    public boolean interact(EntityPlayer entityplayer)
    {
    	ItemStack itemstack = entityplayer.inventory.getCurrentItem();
    	if (FMLCommonHandler.instance().getSide().isClient()){
     if(itemstack != null && itemstack.itemID == mod_Fossil.DinoPedia.shiftedIndex)
        {
            this.showPedia(entityplayer);
            return true;
        }
    	}
    	return false;
    }
    public void onLivingUpdate()
    {
    	EntityAnimal entityanimal2=new EntityPig(worldObj);
    	if (this.Embyos==null){
    		this.setDead();
            worldObj.spawnEntityInWorld(entityanimal2);
    	}
    	this.EmbyoProgress++;
    	if (this.EmbyoProgress==this.EmbyoGrowTime){
    		EntityAnimal entityanimal1;
    		switch (this.Embyos){
    		case Pig:
    			entityanimal1=new EntityPig(worldObj);

    			break;
    		case Sheep:
    			entityanimal1=new EntitySheep(worldObj);

    			break;
    		case Cow:
    			entityanimal1=new EntityCow(worldObj);

    			break;
    		case SaberCat:
    			entityanimal1=new EntitySaberCat(worldObj);
    			break;
    		case Mammoth:
    			entityanimal1=new EntityMammoth(worldObj).Imprinting(posX,posY,posZ);
    			break;
    		default:
    			entityanimal1=new EntityPig(worldObj);

    		}
    		entityanimal1.setGrowingAge(-24000);
            entityanimal1.setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
            entityanimal2.setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
            for(int i = 0; i < 7; i++)
            {
                double d = rand.nextGaussian() * 0.02D;
                double d1 = rand.nextGaussian() * 0.02D;
                double d2 = rand.nextGaussian() * 0.02D;
                worldObj.spawnParticle("heart", (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + 0.5D + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d1, d2);
            }
            this.setDead();
            if (!worldObj.isRemote){
                worldObj.spawnEntityInWorld(entityanimal1);
                worldObj.spawnEntityInWorld(entityanimal2);
            }
    	}else super.onLivingUpdate();
    }
    public void showPedia(EntityPlayer checker) {
    	//if (worldObj.isRemote) return;
    	String TempStatus = "";
    	UpdatePediaText();
		int Progress = (int) Math.floor(((float)this.EmbyoProgress / 3000) * 100);

			mod_Fossil.ShowMessage(new StringBuilder().append(InsideText).append(mod_Fossil.GetEmbyoName(Embyos)).toString(),checker);
			mod_Fossil.ShowMessage(new StringBuilder().append(GrowingText).append(Progress).append("%").toString(),checker);
		
	}
    public EntityAnimal spawnBabyAnimal(EntityAnimal entityanimal)
    {
        return null;
    }
    public void UpdatePediaText(){
    	final String PEDIA="PediaText.vivi.";
 		InsideText=mod_Fossil.GetLangTextByKey(PEDIA+"inside");
 		GrowingText=mod_Fossil.GetLangTextByKey(PEDIA+"growing");
    }
	@Override
	public void writeSpawnData(ByteArrayDataOutput data) {
		data.writeInt(this.Embyos.ordinal());
		
	}
	@Override
	public void readSpawnData(ByteArrayDataInput data) {
		this.Embyos=EnumEmbyos.values()[data.readInt()];
		
	}
    public String InsideText="Embyo inside:";
    public String GrowingText="Growing progress:";
}
