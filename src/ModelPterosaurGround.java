package net.minecraft.src;
//Exported java file
//Keep in mind that you still need to fill in some blanks
// - ZeuX

public class ModelPterosaurGround extends ModelDinosaurs
{
	public 	ModelPterosaurGround()
	{
		Body = new ModelRenderer(this,0, 0);
		Body.addBox(-2F, -3F, -2F, 4, 7, 4, 0F);
		Body.setRotationPoint(0F, 18F, 2F);
		Body.rotateAngleX = 0.587636F;
		Body.rotateAngleY = 0F;
		Body.rotateAngleZ = 0F;
		Body.mirror = false;
		Tail = new ModelRenderer(this,0, 11);
		Tail.addBox(-2F, 0F, -2F, 4, 3, 2, 0F);
		Tail.setRotationPoint(0F, 20F, 5F);
		Tail.rotateAngleX = 0.2260139F;
		Tail.rotateAngleY = 0F;
		Tail.rotateAngleZ = 0F;
		Tail.mirror = false;
		right_leg = new ModelRenderer(this,40, 4);
		right_leg.addBox(-1F, 0F, 0F, 1, 3, 1, 0F);
		right_leg.setRotationPoint(-1F, 22F, 2F);
		right_leg.rotateAngleX = -0.2712166F;
		right_leg.rotateAngleY = 0F;
		right_leg.rotateAngleZ = 0F;
		right_leg.mirror = false;
		Left_leg = new ModelRenderer(this,40, 0);
		Left_leg.addBox(0F, 0F, 0F, 1, 3, 1, 0F);
		Left_leg.setRotationPoint(1F, 22F, 2F);
		Left_leg.rotateAngleX = -0.2712166F;
		Left_leg.rotateAngleY = 0F;
		Left_leg.rotateAngleZ = 0F;
		Left_leg.mirror = false;
		Neck_1 = new ModelRenderer(this,8, 16);
		Neck_1.addBox(-1F, -2F, -1F, 2, 4, 2, 0F);
		Neck_1.setRotationPoint(0F, 15F, 0F);
		Neck_1.rotateAngleX = 1.130069F;
		Neck_1.rotateAngleY = 0F;
		Neck_1.rotateAngleZ = 0F;
		Neck_1.mirror = false;
		Neck_2 = new ModelRenderer(this,0, 16);
		Neck_2.addBox(-1F, -2F, -1F, 2, 4, 2, 0F);
		Neck_2.setRotationPoint(0F, 14F, -3F);
		Neck_2.rotateAngleX = 1.446489F;
		Neck_2.rotateAngleY = 0F;
		Neck_2.rotateAngleZ = 0F;
		Neck_2.mirror = false;
		Head = new ModelRenderer(this,0, 23);
		Head.addBox(-2F, -5F, -1F, 4, 5, 4, 0F);
		Head.setRotationPoint(0F, 14F, -4F);
		Head.rotateAngleX = 1.571F;
		Head.rotateAngleY = 0F;
		Head.rotateAngleZ = 0F;
		Head.mirror = false;
		lower_mouth = new ModelRenderer(this,44, 9);
		lower_mouth.addBox(-1F, -1F, -12F, 2, 1, 8, 0F);
		lower_mouth.setRotationPoint(0F, 14F, -4F);
		lower_mouth.rotateAngleX = 0F;
		lower_mouth.rotateAngleY = 0F;
		lower_mouth.rotateAngleZ = 0F;
		lower_mouth.mirror = false;
		upper_mouth = new ModelRenderer(this,44, 0);
		upper_mouth.addBox(-1F, -2F, -12F, 2, 1, 8, 0F);
		upper_mouth.setRotationPoint(0F, 14F, -4F);
		upper_mouth.rotateAngleX = 0F;
		upper_mouth.rotateAngleY = 0F;
		upper_mouth.rotateAngleZ = 0F;
		upper_mouth.mirror = false;
		crown = new ModelRenderer(this,16, 22);
		crown.addBox(-1F, -5F, -1F, 2, 4, 6, 0F);
		crown.setRotationPoint(0F, 14F, -4F);
		crown.rotateAngleX = 0.698F;
		crown.rotateAngleY = 0F;
		crown.rotateAngleZ = 0F;
		crown.mirror = false;
		Right_wing_1 = new ModelRenderer(this,16, 7);
		Right_wing_1.addBox(0F, 0F, -1F, 8, 6, 1, 0F);
		Right_wing_1.setRotationPoint(-2F, 16F, 1F);
		Right_wing_1.rotateAngleX = -0.349F;
		Right_wing_1.rotateAngleY = 2.269F;
		Right_wing_1.rotateAngleZ = -0.524F;
		Right_wing_1.mirror = false;
		Right_wing_2 = new ModelRenderer(this,46, 18);
		Right_wing_2.addBox(-1F, -1F, -1F, 8, 4, 1, 0F);
		Right_wing_2.setRotationPoint(-6.9F, 20F, -4F);
		Right_wing_2.rotateAngleX = 2.541F;
		Right_wing_2.rotateAngleY = -0.419F;
		Right_wing_2.rotateAngleZ =-3.002F;
		Right_wing_2.mirror = false;
		Left_wing_1 = new ModelRenderer(this,16, 0);
		Left_wing_1.addBox(0F, 0F, 0F, 8, 6, 1, 0F);
		Left_wing_1.setRotationPoint(2F, 16F, 1F);
		Left_wing_1.rotateAngleX = 0.349F;
		Left_wing_1.rotateAngleY = 0.873F;
		Left_wing_1.rotateAngleZ = 0.542F;
		Left_wing_1.mirror = false;
		Left_wing_2 = new ModelRenderer(this,46, 23);
		Left_wing_2.addBox(-1F, -1F, 0F, 8,4, 1, 0F);
		Left_wing_2.setRotationPoint(6.9F, 20F, -4F);
		Left_wing_2.rotateAngleX = 0.583F;
		Left_wing_2.rotateAngleY = -0.419F;
		Left_wing_2.rotateAngleZ = -0.140F;
		Left_wing_2.mirror = false;
		
	}

	public void render(Entity entity,float f, float f1, float f2, float f3, float f4, float f5)
	{
		setRotationAngles(f, f1, f2, f3, f4, f5,((EntityDinosaurce)entity).isModelized());
		Body.render(f5);
		Tail.render(f5);
		right_leg.render(f5);
		Left_leg.render(f5);
		Neck_1.render(f5);
		Neck_2.render(f5);
		Head.render(f5);
		lower_mouth.render(f5);
		upper_mouth.render(f5);
		crown.render(f5);
		Right_wing_1.render(f5);
		Right_wing_2.render(f5);
		//mod_Fossil.ShowMessage(new StringBuilder().append("X:").append(Right_wing_2.rotationPointX).toString());
		//mod_Fossil.ShowMessage(new StringBuilder().append("Y:").append(Right_wing_2.rotationPointY).toString());
		//mod_Fossil.ShowMessage(new StringBuilder().append("Z:").append(Right_wing_2.rotationPointZ).toString());
		Left_wing_1.render(f5);
		Left_wing_2.render(f5);
		
	}


	public void OpenMouth(int Steps){
		if (lower_mouth.rotateAngleX<0.109F) lower_mouth.rotateAngleX+=(0.109F/Steps);
		else lower_mouth.rotateAngleX=0.109F;
		//mod_Fossil.ShowMessage(new StringBuilder().append(head3_down.rotateAngleX).toString());
	}
	public void CloseMouth(int Steps){
		if (lower_mouth.rotateAngleX>0) lower_mouth.rotateAngleX-=(0.109F/Steps);
		else lower_mouth.rotateAngleX=0;
		//mod_Fossil.ShowMessage(new StringBuilder().append(head3_down.rotateAngleX).toString());
	}
	//fields
	public ModelRenderer Body;
	public ModelRenderer Tail;
	public ModelRenderer right_leg;
	public ModelRenderer Left_leg;
	public ModelRenderer Neck_1;
	public ModelRenderer Neck_2;
	public ModelRenderer Head;
	public ModelRenderer lower_mouth;
	public ModelRenderer upper_mouth;
	public ModelRenderer crown;
	public ModelRenderer Right_wing_1;
	public ModelRenderer Right_wing_2;
	public ModelRenderer Left_wing_1;
	public ModelRenderer Left_wing_2;
	@Override
	protected void setRotationAngles(float f, float f1, float f2, float f3,
			float f4, float f5, boolean modelized) {
		if (modelized) return;
		crown.rotateAngleY=upper_mouth.rotateAngleY=lower_mouth.rotateAngleY=Head.rotateAngleY = -f3 / 57.29578F;
		right_leg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1-0.271F;
		Left_leg.rotateAngleX = MathHelper.cos(f * 0.6662F+ 3.141593F) * 1.4F * f1-0.271F;
		
	}
	
}
