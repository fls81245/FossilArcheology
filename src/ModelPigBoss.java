package net.minecraft.src;
//Exported java file
//Keep in mind that you still need to fill in some blanks
// - ZeuX

public class ModelPigBoss extends ModelBiped
{
	public 	ModelPigBoss()
	{
        isSneak = false;
		bipedBody = new ModelRenderer(this,40, 18);
		bipedBody.addBox(-4F, -4F, -2F, 8, 10, 4, 0F);
		bipedBody.setRotationPoint(4F, 7F, 4F);
		
		bipedBody.rotateAngleX = 0F;
		bipedBody.rotateAngleY = 0F;
		bipedBody.rotateAngleZ = 0F;
		bipedBody.mirror = false;
		
		bipedRightArm = new ModelRenderer(this,16, 0);
		bipedRightArm.addBox(-4F, -1F, -2F, 4, 10, 4, 0F);
		bipedRightArm.setRotationPoint(0F, 5F, 6F);
		
		bipedRightArm.rotateAngleX = 0F;
		bipedRightArm.rotateAngleY = 0F;
		bipedRightArm.rotateAngleZ = 0F;
		bipedRightArm.mirror = false;
		
		bipedLeftArm = new ModelRenderer(this,0, 0);
		bipedLeftArm.addBox(0F, -1F, -2F, 4, 10, 4, 0F);
		bipedLeftArm.setRotationPoint(8F, 4F, 4F);
		
		bipedLeftArm.rotateAngleX = 0F;
		bipedLeftArm.rotateAngleY = 0F;
		bipedLeftArm.rotateAngleZ = 0F;
		bipedLeftArm.mirror = false;
		
		bipedRightLeg = new ModelRenderer(this,47, 0);
		bipedRightLeg.addBox(-2F, 0F, -2F, 4, 10, 4, 0F);
		bipedRightLeg.setRotationPoint(2F, 13F, 4F);
		
		bipedRightLeg.rotateAngleX = 0F;
		bipedRightLeg.rotateAngleY = 0F;
		bipedRightLeg.rotateAngleZ = 0F;
		bipedRightLeg.mirror = false;
		
		bipedLeftLeg = new ModelRenderer(this,47, 0);
		bipedLeftLeg.addBox(-2F, 0F, -2F, 4, 10, 4, 0F);
		bipedLeftLeg.setRotationPoint(6F, 13F, 4F);
		
		bipedLeftLeg.rotateAngleX = 0F;
		bipedLeftLeg.rotateAngleY = 3.141593F;
		bipedLeftLeg.rotateAngleZ = 0F;
		bipedLeftLeg.mirror = false;
		
		bipedHead = new ModelRenderer(this,0, 16);
		bipedHead.addBox(-4F, -8F, -4F, 8, 8, 8, 0F);
		bipedHead.setRotationPoint(4F, 3F, 4F);
		
		bipedHead.rotateAngleX = 0F;
		bipedHead.rotateAngleY = 0F;
		bipedHead.rotateAngleZ = 0F;
		bipedHead.mirror = false;
		
		HornLeft = new ModelRenderer(this,0, 17);
		HornLeft.addBox(2F, -14F, 3F, 2, 6, 1, 0F);
		HornLeft.setRotationPoint(4F, 3F, 4F);
		
		HornLeft.rotateAngleX = 0F;
		HornLeft.rotateAngleY = 0F;
		HornLeft.rotateAngleZ = 0F;
		HornLeft.mirror = false;
		
		HornRight = new ModelRenderer(this,0, 17);
		HornRight.addBox(-4F, -14F, 3F, 2, 6, 1, 0F);
		HornRight.setRotationPoint(4F, 3F, 4F);
		
		HornRight.rotateAngleX = 0F;
		HornRight.rotateAngleY = 0F;
		HornRight.rotateAngleZ = 0F;
		HornRight.mirror = false;
		
		bipedHeadwear = new ModelRenderer(this,24, 2);
		bipedHeadwear.addBox(-2F, -10F, -8F, 4, 3, 12, 0F);
		bipedHeadwear.setRotationPoint(4F, 3F, 4F);
		
		bipedHeadwear.rotateAngleX = 0F;
		bipedHeadwear.rotateAngleY = 0F;
		bipedHeadwear.rotateAngleZ = 0F;
		bipedHeadwear.mirror = false;
		
		LeftTooth = new ModelRenderer(this,0, 0);
		LeftTooth.addBox(2F, -4F, -6F, 1, 1, 1, 0F);
		LeftTooth.setRotationPoint(4F, 3F, 4F);
		
		LeftTooth.rotateAngleX = 0F;
		LeftTooth.rotateAngleY = 0F;
		LeftTooth.rotateAngleZ = 0F;
		LeftTooth.mirror = false;
		
		RightTooth = new ModelRenderer(this,0, 0);
		RightTooth.addBox(-3F, -4F, -6F, 1, 1, 1, 0F);
		RightTooth.setRotationPoint(4F, 3F, 4F);
		
		RightTooth.rotateAngleX = 0F;
		RightTooth.rotateAngleY = 0F;
		RightTooth.rotateAngleZ = 0F;
		RightTooth.mirror = false;
		
		Mouth = new ModelRenderer(this,26, 17);
		Mouth.addBox(-3F, -3F, -6F, 6, 3, 2, 0F);
		Mouth.setRotationPoint(4F, 3F, 4F);
		
		Mouth.rotateAngleX = 0F;
		Mouth.rotateAngleY = 0F;
		Mouth.rotateAngleZ = 0F;
		Mouth.mirror = false;
		
		
	}

	public void render(Entity entity,float f, float f1, float f2, float f3, float f4, float f5)
	{
		//super.render(f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5,entity);
		bipedBody.render(f5);
		bipedRightArm.render(f5);
		bipedLeftArm.render(f5);
		bipedRightLeg.render(f5);
		bipedLeftLeg.render(f5);
		bipedHead.render(f5);
		HornLeft.render(f5);
		HornRight.render(f5);
		bipedHeadwear.render(f5);
		LeftTooth.render(f5);
		RightTooth.render(f5);
		Mouth.render(f5);
		
	}

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity par7Entity)
	{
		//super.setRotationAngles(f, f1, f2, f3, f4, f5);
		
		bipedHead.rotateAngleY = f3 / 57.29578F;
        bipedHead.rotateAngleX = f4 / 57.29578F;
        bipedHeadwear.rotateAngleY = bipedHead.rotateAngleY;
        bipedHeadwear.rotateAngleX = bipedHead.rotateAngleX;
        bipedRightArm.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 2.0F * f1 * 0.5F;
        bipedLeftArm.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
        bipedRightArm.rotateAngleZ = 0.0F;
        bipedLeftArm.rotateAngleZ = 0.0F;
        bipedRightLeg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        bipedLeftLeg.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
        bipedRightLeg.rotateAngleY = 0.0F;
        bipedLeftLeg.rotateAngleY = 0.0F;
        if(heldItemLeft!=0)
        {
            bipedLeftArm.rotateAngleX = bipedLeftArm.rotateAngleX * 0.5F - 0.3141593F;
        }
        if(heldItemRight!=0)
        {
            bipedRightArm.rotateAngleX = bipedRightArm.rotateAngleX * 0.5F - 0.3141593F;
        }
        bipedRightArm.rotateAngleY = 0.0F;
        bipedLeftArm.rotateAngleY = 0.0F;
        if(onGround > -9990F)
        {
            float f6 = onGround;
            bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(f6) * 3.141593F * 2.0F) * 0.2F;
            bipedRightArm.rotateAngleY += bipedBody.rotateAngleY;
            bipedLeftArm.rotateAngleY += bipedBody.rotateAngleY;
            bipedLeftArm.rotateAngleX += bipedBody.rotateAngleY;
            f6 = 1.0F - onGround;
            f6 *= f6;
            f6 *= f6;
            f6 = 1.0F - f6;
            float f7 = MathHelper.sin(f6 * 3.141593F);
            float f8 = MathHelper.sin(onGround * 3.141593F) * -(bipedHead.rotateAngleX - 0.7F) * 0.75F;
            bipedRightArm.rotateAngleX -= (double)f7 * 1.2D + (double)f8;
            bipedRightArm.rotateAngleY += bipedBody.rotateAngleY * 2.0F;
            bipedRightArm.rotateAngleZ = MathHelper.sin(onGround * 3.141593F) * -0.4F;
        }
        bipedRightArm.rotateAngleZ += MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
        bipedLeftArm.rotateAngleZ -= MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
        bipedRightArm.rotateAngleX += MathHelper.sin(f2 * 0.067F) * 0.05F;
        bipedLeftArm.rotateAngleX -= MathHelper.sin(f2 * 0.067F) * 0.05F;
		
		this.HornLeft.rotateAngleX=this.HornRight.rotateAngleX=this.bipedHeadwear.rotateAngleX;
		this.HornLeft.rotateAngleY=this.HornRight.rotateAngleY=this.bipedHeadwear.rotateAngleY;
		this.Mouth.rotateAngleX=this.bipedHead.rotateAngleX;
		this.Mouth.rotateAngleY=this.bipedHead.rotateAngleY;
		this.LeftTooth.rotateAngleX=this.RightTooth.rotateAngleX=this.bipedHead.rotateAngleX;
		this.LeftTooth.rotateAngleY=this.RightTooth.rotateAngleY=this.bipedHead.rotateAngleY;
		if (RangedAttack) {
			this.bipedRightArm.rotateAngleX=-3.141593F/2+this.bipedHead.rotateAngleX;
			this.bipedRightArm.rotateAngleY=this.bipedHead.rotateAngleY;
		}
	}

	//fields
	/*public ModelRenderer bipedBody;
	public ModelRenderer bipedRightArm;
	public ModelRenderer bipedLeftArm;
	public ModelRenderer bipedRightLeg;
	public ModelRenderer bipedLeftLeg;
	public ModelRenderer bipedHead;
	public ModelRenderer bipedHeadwear;*/
	public ModelRenderer HornLeft;
	public ModelRenderer HornRight;
	public ModelRenderer LeftTooth;
	public ModelRenderer RightTooth;
	public ModelRenderer Mouth;
	public boolean RangedAttack;
	
}
