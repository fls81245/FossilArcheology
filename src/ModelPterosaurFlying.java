package net.minecraft.src;
//Exported java file
//Keep in mind that you still need to fill in some blanks
// - ZeuX

public class ModelPterosaurFlying extends ModelPterosaurGround
{
	public ModelPterosaurFlying()
	{
		Body = new ModelRenderer(this,0, 0);
		Body.addBox(-2F, -3.5F, -2F, 4, 7, 4, 0F);
		Body.setRotationPoint(0F, 23F, 0F);
		Body.rotateAngleX = 1.570796F;
		Body.rotateAngleY = 0F;
		Body.rotateAngleZ = 0F;
		Body.mirror = false;
		Neck_1 = new ModelRenderer(this,8, 16);
		Neck_1.addBox(-1F, -7F, -1F, 2, 4, 2, 0F);
		Neck_1.setRotationPoint(0F, 23F, 0F);
		Neck_1.rotateAngleX = 1.570796F;
		Neck_1.rotateAngleY = 0F;
		Neck_1.rotateAngleZ = 0F;
		Neck_1.mirror = false;
		New_Shape1 = new ModelRenderer(this,0, 16);
		New_Shape1.addBox(-1F, -11F, -1F, 2, 4, 2, 0F);
		New_Shape1.setRotationPoint(0F, 23F, 0F);
		New_Shape1.rotateAngleX = 1.570796F;
		New_Shape1.rotateAngleY = 0F;
		New_Shape1.rotateAngleZ = 0F;
		New_Shape1.mirror = false;
		Left_wing_1 = new ModelRenderer(this,16, 0);
		Left_wing_1.addBox(2F, -3F, 0F, 8, 6, 1, 0F);
		Left_wing_1.setRotationPoint(0F, 23F, 0F);
		Left_wing_1.rotateAngleX = -1.570796F;
		Left_wing_1.rotateAngleY = 3.141593F;
		Left_wing_1.rotateAngleZ = 2.792527F;
		Left_wing_1.mirror = false;
		Right_wing_1 = new ModelRenderer(this,16, 7);
		Right_wing_1.addBox(2F, -3F, -1F, 8, 6, 1, 0F);
		Right_wing_1.setRotationPoint(0F, 23F, 0F);
		Right_wing_1.rotateAngleX = 1.570796F;
		Right_wing_1.rotateAngleY = 0F;
		Right_wing_1.rotateAngleZ = -2.792527F;
		Right_wing_1.mirror = false;
		Left_wing_2 = new ModelRenderer(this,46, 23);
		Left_wing_2.addBox(9F, -3F, 3F, 8, 4, 1, 0F);
		Left_wing_2.setRotationPoint(0F, 23F, 0F);
		Left_wing_2.rotateAngleX = 1.570796F;
		Left_wing_2.rotateAngleY = 0F;
		Left_wing_2.rotateAngleZ = 0F;
		Left_wing_2.mirror = false;
		Right_wing_2 = new ModelRenderer(this,46, 18);
		Right_wing_2.addBox(9F, -3F, -4F, 8, 4, 1, 0F);
		Right_wing_2.setRotationPoint(0F, 23F, 0F);
		Right_wing_2.rotateAngleX = -1.570796F;
		Right_wing_2.rotateAngleY = 3.141593F;
		Right_wing_2.rotateAngleZ = 0F;
		Right_wing_2.mirror = false;
		Tail = new ModelRenderer(this,0, 11);
		Tail.addBox(-2F, 2F, 0F, 4, 3, 2, 0F);
		Tail.setRotationPoint(0F, 23F, 0F);
		Tail.rotateAngleX = 1.570796F;
		Tail.rotateAngleY = 0F;
		Tail.rotateAngleZ = 0F;
		Tail.mirror = false;
		crown = new ModelRenderer(this,16, 22);
		crown.addBox(-1F, -10F, -9F, 2, 4, 6, 0F);
		crown.setRotationPoint(0F, 23F, 0F);
		crown.rotateAngleX = 0.4859298F;
		crown.rotateAngleY = 0F;
		crown.rotateAngleZ = 0F;
		crown.mirror = false;
		Head = new ModelRenderer(this,0, 23);
		Head.addBox(-2F, -13F, -1F, 4, 5, 4, 0F);
		Head.setRotationPoint(0F, 23F, 0F);
		Head.rotateAngleX = 1.570796F;
		Head.rotateAngleY = 0F;
		Head.rotateAngleZ = 0F;
		Head.mirror = false;
		upper_mouth = new ModelRenderer(this,44, 0);
		upper_mouth.addBox(-1F, -1F, -21F, 2, 1, 8, 0F);
		upper_mouth.setRotationPoint(0F, 23F, 0F);
		upper_mouth.rotateAngleX = 0F;
		upper_mouth.rotateAngleY = 0F;
		upper_mouth.rotateAngleZ = 0F;
		upper_mouth.mirror = false;
		lower_mouth = new ModelRenderer(this,44, 9);
		lower_mouth.addBox(-1F, -2F, -20F, 2, 1, 8, 0F);
		lower_mouth.setRotationPoint(0F, 23F, 0F);
		lower_mouth.rotateAngleX = 0.1356083F;
		lower_mouth.rotateAngleY = 0F;
		lower_mouth.rotateAngleZ = 0F;
		lower_mouth.mirror = false;
		Left_leg = new ModelRenderer(this,40, 0);
		Left_leg.addBox(1F, 3F, -2F, 1, 3, 1, 0F);
		Left_leg.setRotationPoint(0F, 23F, 0F);
		Left_leg.rotateAngleX = 1.570796F;
		Left_leg.rotateAngleY = 0F;
		Left_leg.rotateAngleZ = 0F;
		Left_leg.mirror = false;
		right_leg = new ModelRenderer(this,40, 4);
		right_leg.addBox(-2F, 3F, -2F, 1, 3, 1, 0F);
		right_leg.setRotationPoint(0F, 23F, 0F);
		right_leg.rotateAngleX = 1.570796F;
		right_leg.rotateAngleY = 0F;
		right_leg.rotateAngleZ = 0F;
		right_leg.mirror = false;
	}
	
	public void render(Entity entity,float f, float f1, float f2, float f3, float f4, float f5)
	{
		//super.render(f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5,((EntityDinosaurce)entity).isModelized());
		Body.render(f5);
		Neck_1.render(f5);
		New_Shape1.render(f5);
		Left_wing_1.render(f5);
		Right_wing_1.render(f5);
		Left_wing_2.render(f5);
		Right_wing_2.render(f5);
		Tail.render(f5);
		crown.render(f5);
		Head.render(f5);
		upper_mouth.render(f5);
		lower_mouth.render(f5);
		Left_leg.render(f5);
		right_leg.render(f5);
	}
	

	protected void setRotationAngles(float f, float f1, float f2, float f3,
			float f4, float f5, boolean modelized) {
		if (modelized) return;
		Left_wing_1.rotateAngleX=-1.570796F+this.AirPitch;
		Left_wing_2.rotateAngleX=1.570796F+this.AirPitch;
		Right_wing_1.rotateAngleX=1.570796F-this.AirPitch;
		Right_wing_2.rotateAngleX=-1.570796F-this.AirPitch;
		this.Body.rotateAngleX=1.570796F+this.AirPitch;
		this.Neck_1.rotateAngleX=1.570796F+this.AirPitch;
		this.New_Shape1.rotateAngleX=1.570796F+this.AirPitch;
		this.Tail.rotateAngleX=1.570796F+this.AirPitch;
		this.crown.rotateAngleX=0.4859298F+this.AirPitch;
		this.Head.rotateAngleX=1.570796F+this.AirPitch;
		this.upper_mouth.rotateAngleX=this.AirPitch;
		this.lower_mouth.rotateAngleX=0.1356083F+this.AirPitch;
		this.Left_leg.rotateAngleX=this.right_leg.rotateAngleX=1.570796F+this.AirPitch;
		//Roll
		this.Body.rotateAngleZ=this.AirRoll;
		this.Neck_1.rotateAngleZ=this.AirRoll;
		this.New_Shape1.rotateAngleZ=this.AirRoll;
		this.Left_wing_1.rotateAngleZ=2.792527F+this.AirRoll;
		this.Left_wing_2.rotateAngleZ=this.AirRoll;
		this.Right_wing_1.rotateAngleZ=-2.792527F+this.AirRoll;
		this.Right_wing_2.rotateAngleZ=this.AirRoll;
		this.Tail.rotateAngleZ=this.AirRoll;
		this.crown.rotateAngleZ=this.AirRoll;
		this.Head.rotateAngleZ=this.AirRoll;
		this.upper_mouth.rotateAngleZ=this.AirRoll;
		this.lower_mouth.rotateAngleZ=this.AirRoll;
		this.Left_leg.rotateAngleZ=this.right_leg.rotateAngleZ=this.AirRoll;
		
	}
	//fields
	public ModelRenderer Body;
	public ModelRenderer Neck_1;
	public ModelRenderer New_Shape1;
	public ModelRenderer Left_wing_1;
	public ModelRenderer Right_wing_1;
	public ModelRenderer Left_wing_2;
	public ModelRenderer Right_wing_2;
	public ModelRenderer Tail;
	public ModelRenderer crown;
	public ModelRenderer Head;
	public ModelRenderer upper_mouth;
	public ModelRenderer lower_mouth;
	public ModelRenderer Left_leg;
	public ModelRenderer right_leg;
	public float LeftWingRandomFlap=0.0F;
	public float RightWingRandomFlap=0.0F;
	public float AirRoll=0.0F;
	public float AirPitch=0.0F;
}
