package net.minecraft.src;
//Exported java file
//Keep in mind that you still need to fill in some blanks
// - ZeuX

public class ModelFailuresaurus extends ModelBase
{
	public 	ModelFailuresaurus()
	{
		noumenon1 = new ModelRenderer(this,0, 1);
		noumenon1.addBox(0F, 0F, 0F, 10, 1, 14, 0F);
		noumenon1.setRotationPoint(-5F, 23F, -7F);
		
		noumenon1.rotateAngleX = 0F;
		noumenon1.rotateAngleY = 0F;
		noumenon1.rotateAngleZ = 0F;
		noumenon1.mirror = false;
		
		noumenon2 = new ModelRenderer(this,2, 3);
		noumenon2.addBox(0F, 0F, 0F, 14, 3, 10, 0F);
		noumenon2.setRotationPoint(-7F, 21F, -5F);
		
		noumenon2.rotateAngleX = 0F;
		noumenon2.rotateAngleY = 0F;
		noumenon2.rotateAngleZ = 0F;
		noumenon2.mirror = false;
		
		noumenon3 = new ModelRenderer(this,18, 6);
		noumenon3.addBox(0F, 0F, 0F, 10, 4, 5, 0F);
		noumenon3.setRotationPoint(-5F, 17F, -2F);
		
		noumenon3.rotateAngleX = 0F;
		noumenon3.rotateAngleY = 0F;
		noumenon3.rotateAngleZ = 0F;
		noumenon3.mirror = false;
		
		hand1 = new ModelRenderer(this,0, 16);
		hand1.addBox(0F, 0F, 0F, 8, 6, 10, 0F);
		hand1.setRotationPoint(-4F, 12F, -8F);
		
		hand1.rotateAngleX = 0F;
		hand1.rotateAngleY = 0F;
		hand1.rotateAngleZ = 0F;
		hand1.mirror = false;
		
		
	}

	public void render(Entity entity,float f, float f1, float f2, float f3, float f4, float f5)
	{
		setRotationAngles(f, f1, f2, f3, f4, f5,entity);
		noumenon1.render(f5);
		noumenon2.render(f5);
		noumenon3.render(f5);
		hand1.render(f5);
		
	}


	//fields
	public ModelRenderer noumenon1;
	public ModelRenderer noumenon2;
	public ModelRenderer noumenon3;
	public ModelRenderer hand1;
	
}
