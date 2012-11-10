package net.minecraft.src;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;


public class ItemAncientsword extends ItemSword{
	public ItemAncientsword(int i, EnumToolMaterial enumtoolmaterial){
		super (i,enumtoolmaterial);
		maxStackSize=1;
	}
	public ItemAncientsword(int i){
		this(i,EnumToolMaterial.IRON);
	}
    public String getTextureFile()
    {
       return "/skull/Fos_items.png";
    }
	public int getDamageVsEntity(Entity entity)
    {
		if (entity!=null){
			
			if (entity.worldObj.difficultySetting > 0){
				if (entity instanceof EntityPig||entity instanceof EntityPigZombie){
					EntityPlayer possiblePlayer=SearchUser(entity);
					if (possiblePlayer!=null && checkHelmet(possiblePlayer)){
						if (!entity.worldObj.isRemote){
							EntityFriendlyPigZombie entitypigzombie = new EntityFriendlyPigZombie(entity.worldObj);
							entitypigzombie.LeaderName=possiblePlayer.username;
							entitypigzombie.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
							entity.setDead();					
							entity.worldObj.spawnEntityInWorld(entitypigzombie);
							entitypigzombie.Mouth.SendSpeech(EnumPigmenSpeaks.LifeFor, entitypigzombie.LeaderName);
						}
					}
				}
			}
		}
		entity.worldObj.addWeatherEffect(new EntityMLighting(entity.worldObj,entity.posX,entity.posY,entity.posZ));
		return (4 + EnumToolMaterial.IRON.getDamageVsEntity() * 2);
    }
	private EntityPlayer SearchUser(Entity centerRef){
		EntityPlayer tmp=(EntityPlayer)centerRef.worldObj.findNearestEntityWithinAABB(EntityPlayer.class, centerRef.boundingBox.expand(6.0, 6.0, 6.0), centerRef);
		if (tmp==null) return null;
		return tmp;
	}

	private boolean checkHelmet(EntityPlayer testee){
		ItemStack itemstack = testee.inventory.armorInventory[3];
		if (itemstack==null){
			return false;
		}else{
			if (itemstack.itemID!=mod_Fossil.Ancienthelmet.shiftedIndex){
				return false;
			}
			return true;
		}
	}
}