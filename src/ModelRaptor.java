package net.minecraft.src;
//Exported java file
//Keep in mind that you still need to fill in some blanks
// - ZeuX

public class ModelRaptor extends ModelBase
{

public ModelRaptor()
{
noumenon2 = new ModelRenderer(this,0, 0);
noumenon2.addBox(-3, -3, -6, 8, 6, 7, 0F);
noumenon2.setRotationPoint(-1, 13, 3);

noumenon2.rotateAngleX = 0F;
noumenon2.rotateAngleY = 0F;
noumenon2.rotateAngleZ = 0F;
noumenon2.mirror = false;

noumenon3 = new ModelRenderer(this,3, 1);
noumenon3.addBox(-2, 0, 0, 4, 4, 6, 0F);
noumenon3.setRotationPoint(0, 10, 4);

noumenon3.rotateAngleX = -0.5235988F;
noumenon3.rotateAngleY = 0F;
noumenon3.rotateAngleZ = 0F;
noumenon3.mirror = false;

noumenon4 = new ModelRenderer(this,20, 11);
noumenon4.addBox(-1, 0, 6, 2, 2, 12, 0F);
noumenon4.setRotationPoint(0, 10, 4);

noumenon4.rotateAngleX = -0.6981317F;
noumenon4.rotateAngleY = 0F;
noumenon4.rotateAngleZ = 0F;
noumenon4.mirror = false;

thigh_L2 = new ModelRenderer(this,48, 1);
thigh_L2.addBox(0, -1, -2, 3, 5, 5, 0F);
thigh_L2.setRotationPoint(4, 14, 0);

thigh_L2.rotateAngleX = 0F;
thigh_L2.rotateAngleY = 0F;
thigh_L2.rotateAngleZ = 0F;
thigh_L2.mirror = false;

thigh_L1 = new ModelRenderer(this,43, 0);
thigh_L1.addBox(0, -1, -1, 2, 3, 3, 0F);
thigh_L1.setRotationPoint(3, 12, -6);

thigh_L1.rotateAngleX = 0F;
thigh_L1.rotateAngleY = 0F;
thigh_L1.rotateAngleZ = 0F;
thigh_L1.mirror = false;

noumenon1 = new ModelRenderer(this,3, 2);
noumenon1.addBox(-3, -6, -5, 6, 6, 5, 0F);
noumenon1.setRotationPoint(0, 16, -4);

noumenon1.rotateAngleX = -0.5235988F;
noumenon1.rotateAngleY = 0F;
noumenon1.rotateAngleZ = 0F;
noumenon1.mirror = false;

head1 = new ModelRenderer(this,3, 1);
head1.addBox(-2, 0, -6, 4, 4, 6, 0F);
head1.setRotationPoint(0, 10, -5);

head1.rotateAngleX = -2.094395F;
head1.rotateAngleY = 0F;
head1.rotateAngleZ = 0F;
head1.mirror = false;

head2 = new ModelRenderer(this,0, 17);
head2.addBox(-3, -7, -8, 6, 7, 8, 0F);
head2.setRotationPoint(0, 5, -3);

head2.rotateAngleX = 0.08726646F;
head2.rotateAngleY = 0F;
head2.rotateAngleZ = 0F;
head2.mirror = false;

head3_up = new ModelRenderer(this,44, 22);
head3_up.addBox(-2, -4, -6, 4, 4, 6, 0F);
head3_up.setRotationPoint(0, 5, -11);

head3_up.rotateAngleX = 0.08726646F;
head3_up.rotateAngleY = 0F;
head3_up.rotateAngleZ = 0F;
head3_up.mirror = false;

head3_down = new ModelRenderer(this,23, 0);
head3_down.addBox(-2, 0, -5, 4, 1, 6, 0F);
head3_down.setRotationPoint(0, 5, -10);

head3_down.rotateAngleX = 0F;
head3_down.rotateAngleY = 0F;
head3_down.rotateAngleZ = 0F;
head3_down.mirror = false;

leg_L1 = new ModelRenderer(this,20, 18);
leg_L1.addBox(0, 2, -4, 2, 2, 4, 0F);
leg_L1.setRotationPoint(3, 12, -6);

leg_L1.rotateAngleX = 0.994461F;
leg_L1.rotateAngleY = 0F;
leg_L1.rotateAngleZ = 0F;
leg_L1.mirror = false;

leg_L2 = new ModelRenderer(this,14, 8);
leg_L2.addBox(0, 4, -7, 2, 2, 7, 0F);
leg_L2.setRotationPoint(4, 14, 0);

leg_L2.rotateAngleX = 0.9948377F;
leg_L2.rotateAngleY = 0F;
leg_L2.rotateAngleZ = 0F;
leg_L2.mirror = false;

left_nail_1 = new ModelRenderer(this,32, 7);
left_nail_1.addBox(0, 5, 3, 1, 1, 3, 0F);
left_nail_1.setRotationPoint(4, 14, 0);

left_nail_1.rotateAngleX = -0.8726646F;
left_nail_1.rotateAngleY = 0F;
left_nail_1.rotateAngleZ = 0F;
left_nail_1.mirror = false;

left_nail_2 = new ModelRenderer(this,32, 7);
left_nail_2.addBox(0, -5, 5, 1, 1, 1, 0F);
left_nail_2.setRotationPoint(4, 14, 0);

left_nail_2.rotateAngleX = -2.6529F;
left_nail_2.rotateAngleY = 0F;
left_nail_2.rotateAngleZ = 0F;
left_nail_2.mirror = false;

horn_2_L = new ModelRenderer(this,30, 26);
horn_2_L.addBox(0, 8, -3, 3, 2, 4, 0F);
horn_2_L.setRotationPoint(4, 14, 1);

horn_2_L.rotateAngleX = 0F;
horn_2_L.rotateAngleY = 0F;
horn_2_L.rotateAngleZ = 0F;
horn_2_L.mirror = false;

leg_R1 = new ModelRenderer(this,20, 18);
leg_R1.addBox(-2, 2, -4, 2, 2, 4, 0F);
leg_R1.setRotationPoint(-3, 12, -6);

leg_R1.rotateAngleX = 0.994461F;
leg_R1.rotateAngleY = 0F;
leg_R1.rotateAngleZ = 0F;
leg_R1.mirror = false;

thigh_R2 = new ModelRenderer(this,48, 12);
thigh_R2.addBox(-3, -1, -2, 3, 5, 5, 0F);
thigh_R2.setRotationPoint(-4, 14, 0);

thigh_R2.rotateAngleX = 0F;
thigh_R2.rotateAngleY = 0F;
thigh_R2.rotateAngleZ = 0F;
thigh_R2.mirror = false;

thigh_R1 = new ModelRenderer(this,43, 11);
thigh_R1.addBox(-2, -1, -1, 2, 3, 3, 0F);
thigh_R1.setRotationPoint(-3, 12, -6);

thigh_R1.rotateAngleX = 0F;
thigh_R1.rotateAngleY = 0F;
thigh_R1.rotateAngleZ = 0F;
thigh_R1.mirror = false;

leg_R2 = new ModelRenderer(this,14, 8);
leg_R2.addBox(-2, 4, -7, 2, 2, 7, 0F);
leg_R2.setRotationPoint(-4, 14, 0);

leg_R2.rotateAngleX = 0.9948377F;
leg_R2.rotateAngleY = 0F;
leg_R2.rotateAngleZ = 0F;
leg_R2.mirror = false;

right_nail_2 = new ModelRenderer(this,32, 7);
right_nail_2.addBox(-1, -5, 5, 1, 1, 1, 0F);
right_nail_2.setRotationPoint(-4, 14, 0);

right_nail_2.rotateAngleX = -2.617994F;
right_nail_2.rotateAngleY = 0F;
right_nail_2.rotateAngleZ = 0F;
right_nail_2.mirror = false;

right_nail_1 = new ModelRenderer(this,32, 7);
right_nail_1.addBox(-1, 5, 3, 1, 1, 3, 0F);
right_nail_1.setRotationPoint(-4, 14, 0);

right_nail_1.rotateAngleX = -0.8726646F;
right_nail_1.rotateAngleY = 0F;
right_nail_1.rotateAngleZ = 0F;
right_nail_1.mirror = false;

horn_1_R = new ModelRenderer(this,30, 26);
horn_1_R.addBox(-3, 8, -3, 3, 2, 4, 0F);
horn_1_R.setRotationPoint(-4, 14, 1);

horn_1_R.rotateAngleX = 0F;
horn_1_R.rotateAngleY = 0F;
horn_1_R.rotateAngleZ = 0F;
horn_1_R.mirror = false;

}
public void render(Entity entity,float f, float f1, float f2, float f3, float f4, float f5){
	render(entity,f,f1,f2,f3,f4,f5,false);
}
public void render(Entity entity,float f, float f1, float f2, float f3, float f4, float f5,boolean HeadTailFree)
{
//super.render(float f, float f1, float f2, float f3, float f4, float f5)
setRotationAngles(f, f1, f2, f3, f4, f5,HeadTailFree);
noumenon2.render(f5);
noumenon3.render(f5);
noumenon4.render(f5);
thigh_L2.render(f5);
thigh_L1.render(f5);
noumenon1.render(f5);
head1.render(f5);
head2.render(f5);
head3_up.render(f5);
head3_down.render(f5);
leg_L1.render(f5);
leg_L2.render(f5);
left_nail_1.render(f5);
left_nail_2.render(f5);
horn_2_L.render(f5);
leg_R1.render(f5);
thigh_R2.render(f5);
thigh_R1.render(f5);
leg_R2.render(f5);
right_nail_2.render(f5);
right_nail_1.render(f5);
horn_1_R.render(f5);

}
public void HuntingPose(){
	noumenon2.setRotationPoint(-1.0F, 10.0F, 3.0F);
	noumenon2.rotateAngleX=-0.558F;
	
	noumenon3.setRotationPoint(0.0F, 7.0F, 2.0F);
	noumenon3.rotateAngleX=0.555F;
	
	noumenon4.setRotationPoint(0.0F, 11.0F, 4.0F);
	noumenon4.rotateAngleX=1.161F;
	
	thigh_L2.setRotationPoint(4.0F, 9.0F, 0.0F);
	thigh_L2.rotateAngleX=-0.611F;
	
	thigh_L1.setRotationPoint(3.0F, 7.0F, -6.0F);
	thigh_L1.rotateAngleX=0.0F;
	
	noumenon1.setRotationPoint(0.0F, 9.5F, -2.0F);
	noumenon1.rotateAngleX=-0.43F;
	
	head1.setRotationPoint(0.0F, 2.0F, -3.0F);
	head1.rotateAngleX=-0.273F;
	
	head2.setRotationPoint(0.0F, 2.0F, -6.0F);
	head2.rotateAngleX=0.873F;
	
	head3_up.setRotationPoint(0.0F, 7.0F, -12.0F);
	head3_up.rotateAngleX=0.873F;
	
	head3_down.setRotationPoint(0.0F, 7.0F, -12.0F);
	head3_down.rotateAngleX=1.589F;
	
	leg_L1.setRotationPoint(3.0F, 7.0F, -6.0F);
	leg_L1.rotateAngleX=0.994F;
	
	leg_L2.setRotationPoint(4.0F, 9.0F, 0.0F);
	leg_L2.rotateAngleX=0.177F;
	
	left_nail_1.setRotationPoint(4.0F, 9.0F, 0.0F);
	left_nail_1.rotateAngleX=-1.616F;
	
	left_nail_2.setRotationPoint(4.0F, 9.0F, 0.0F);
	left_nail_2.rotateAngleX=2.961F;
	
	horn_2_L.setRotationPoint(4.0F, 9.0F, 1.0F);
	horn_2_L.rotateAngleX=-0.706F;
	
	leg_R1.setRotationPoint(-3.0F, 7.0F, -6.0F);
	leg_R1.rotateAngleX=0.994F;
	
	thigh_R2.setRotationPoint(-4.0F, 9.0F, 0.0F);
	thigh_R2.rotateAngleX=0.186F;
	
	thigh_R1.setRotationPoint(-3.0F, 7.0F,-6.0F);
	thigh_R1.rotateAngleX=0.0F;
	
	leg_R2.setRotationPoint(-4.0F, 11.0F, -2.0F);
	leg_R2.rotateAngleX=1.515F;
	
	right_nail_2.setRotationPoint(-4.0F, 9.0F, 0.0F);
	right_nail_2.rotateAngleX=-2.358F;
	
	right_nail_1.setRotationPoint(-4.0F, 9.0F, 0.0F);
	right_nail_1.rotateAngleX=-0.630F;
	
	horn_1_R.setRotationPoint(-4.0F, 9.0F, 0.0F);
	horn_1_R.rotateAngleX=0.335F;
};
public void ReturnPose(){
	noumenon2.setRotationPoint(-1.0F, 13.0F, 3.0F);
	noumenon2.rotateAngleX=0.0F;
	noumenon3.setRotationPoint(0.0F, 10.0F,4.0F);
	noumenon3.rotateAngleX=-0.524F;
	noumenon4.setRotationPoint(0.0F, 10.0F, 4.0F);
	noumenon4.rotateAngleX=-0.698F;
	thigh_L2.setRotationPoint(4.0F, 14.0F, 0.0F);
	thigh_L2.rotateAngleX=0.0F;
	thigh_L1.setRotationPoint(3.0F, 12.0F, -6.0F);
	thigh_L1.rotateAngleX=0.0F;
	noumenon1.setRotationPoint(0.0F, 16.0F, -4.0F);
	noumenon1.rotateAngleX=-0.524F;
	head1.setRotationPoint(0.0F, 10.0F, -5.0F);
	head1.rotateAngleX=-2.094F;
	head2.setRotationPoint(0.0F, 5.0F, -3.0F);
	head2.rotateAngleX=0.087F;
	head3_up.setRotationPoint(0.0F, 1.0F, -11.0F);
	head3_up.rotateAngleX=0.087F;
	head3_down.setRotationPoint(0.0F, 5.0F, -10.0F);
	head3_down.rotateAngleX=0.436F;
	leg_L1.setRotationPoint(3.0F, 12.0F, -6.0F);
	leg_L1.rotateAngleX=0.994F;
	leg_L2.setRotationPoint(4.0F,14.0F, 0.0F);
	leg_L2.rotateAngleX=0.995F;
	left_nail_1.setRotationPoint(4.0F, 14.0F, 0.0F);
	left_nail_1.rotateAngleX=-0.873F;
	left_nail_2.setRotationPoint(4.0F, 14.0F, 0.0F);
	left_nail_2.rotateAngleX=-2.653F;
	horn_2_L.setRotationPoint(4.0F, 14.0F, 1.0F);
	horn_2_L.rotateAngleX=0.0F;
	leg_R1.setRotationPoint(-3.0F, 12.0F, -6.0F);
	leg_R1.rotateAngleX=0.994F;
	thigh_R2.setRotationPoint(-4.0F, 14.0F, 0.0F);
	thigh_R2.rotateAngleX=0.0F;
	thigh_R1.setRotationPoint(-3.0F, 12.0F,-6.0F);
	thigh_R1.rotateAngleX=0.0F;
	leg_R2.setRotationPoint(-4.0F, 14.0F, 0.0F);
	leg_R2.rotateAngleX=0.995F;
	right_nail_2.setRotationPoint(-4.0F, 14.0F, 0.0F);
	right_nail_2.rotateAngleX=-2.618F;
	right_nail_1.setRotationPoint(-4.0F, 14.0F, 0.0F);
	right_nail_1.rotateAngleX=-0.873F;
	horn_1_R.setRotationPoint(-4.0F, 14.0F, 1.0F);
	horn_1_R.rotateAngleX=0.0F;
}
public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5,boolean HeadTailFree)
    {
		if (!HeadTailFree){
			//right leg
			thigh_R2.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
			leg_R2.rotateAngleX=MathHelper.cos(f * 0.6662F) * 1.4F * f1+0.9948377F;
			horn_1_R.rotateAngleX=MathHelper.cos(f * 0.6662F) * 1.4F * f1;
			right_nail_1.rotateAngleX=MathHelper.cos(f * 0.6662F) * 1.4F * f1-0.8726646F;
			right_nail_2.rotateAngleX=MathHelper.cos(f * 0.6662F) * 1.4F * f1-2.617994F;
			//left leg
			thigh_L2.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
			leg_L2.rotateAngleX =MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1+0.9948377F;
			horn_2_L.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
			left_nail_1.rotateAngleX =MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1-0.8726646F;
			left_nail_2.rotateAngleX =MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1-2.617994F;
			//right arm
			thigh_R1.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
			leg_R1.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1+0.994461F;
			//left arm
			thigh_L1.rotateAngleX=MathHelper.cos(f * 0.6662F) * 1.4F * f1;
			leg_L1.rotateAngleX=MathHelper.cos(f * 0.6662F) * 1.4F * f1+0.994461F;
			//tail
			if (Math.abs(thigh_L2.rotateAngleX)>=0.174532F){
				TailUpper();
			}else{
				TailLower();
			}
			//Head
			/*head2.rotateAngleY = -f3 / 57.29578F;
			head3_up.rotateAngleY = -f3 / 57.29578F;
			head3_down.rotateAngleY = -f3 / 57.29578F;*/
			if (Math.abs(thigh_L2.rotateAngleX)>=0.174532F){
			//moving
				HeadLower();
			}else{
			//standing
				HeadUpper();
	
			}
		}
    }
public boolean SwingHead(float targetAngle,int step){
	float amount=targetAngle/step;
	int StepCount=0;
	boolean direction=(targetAngle>=0);	/*true for right*/
	if (direction){
		if (head2.rotateAngleZ<targetAngle) head2.rotateAngleZ+=amount;
		else {
			head2.rotateAngleZ=targetAngle;
			StepCount++;
		}
		
		if (head3_up.rotateAngleZ<targetAngle) head3_up.rotateAngleZ+=amount;
		else {
			head3_up.rotateAngleZ=targetAngle;
			StepCount++;
		}
		
		if (head3_down.rotateAngleZ<targetAngle) head3_down.rotateAngleZ+=amount;
		else {
			head3_down.rotateAngleZ=targetAngle;
			StepCount++;
		}
	}else{
		if (head2.rotateAngleZ>targetAngle) head2.rotateAngleZ+=amount;
		else {
			head2.rotateAngleZ=targetAngle;
			StepCount++;
		}
		
		if (head3_up.rotateAngleZ>targetAngle) head3_up.rotateAngleZ+=amount;
		else {
			head3_up.rotateAngleZ=targetAngle;
			StepCount++;
		}
		
		if (head3_down.rotateAngleZ>targetAngle) head3_down.rotateAngleZ+=amount;
		else {
			head3_down.rotateAngleZ=targetAngle;
			StepCount++;
		}
	}
	return (StepCount==3);
}
public void SwingHeadBack(){
	head2.rotateAngleZ=0;
	head3_up.rotateAngleZ=0;
	head3_down.rotateAngleZ=0;
}
	public void TailUpper(){
		if (noumenon3.rotateAngleX<0)noumenon3.rotateAngleX+=(0.5235988F/10);
		else noumenon3.rotateAngleX=0F;
		if (noumenon4.rotateAngleX<0)noumenon4.rotateAngleX+=(0.6981317F/10);
		else noumenon4.rotateAngleX=0F;
	}
	public void TailLower(){
		if (noumenon3.rotateAngleX>-0.5235988F)noumenon3.rotateAngleX-=(0.5235988F/10);
		else noumenon3.rotateAngleX=-0.5235988F;
		if (noumenon4.rotateAngleX>-0.6981317F)noumenon4.rotateAngleX-=(0.6981317F/10);
		else noumenon4.rotateAngleX=-0.6981317F;
	}
	public void HeadLower(){
		if (noumenon1.rotationPointZ<-3) noumenon1.rotationPointZ+=0.1;
		else noumenon1.rotationPointZ=-3;
		
		if (noumenon1.rotateAngleX<0)noumenon1.rotateAngleX+=(0.5235988F/10);
		else noumenon1.rotateAngleX=0F;
		
		if (head1.rotationPointZ>-8) head1.rotationPointZ-=(3/10);
		else head1.rotationPointZ=-8;
		
		if (head1.rotateAngleX<0)head1.rotateAngleX+=(2.094395F/10);
		else head1.rotateAngleX=0F;
		
		if (head2.rotationPointY<15) head2.rotationPointY+=1;
		else head2.rotationPointY=15;
		
		if (head2.rotationPointZ>-12) head2.rotationPointZ-=(9/10);
		else head2.rotationPointZ=-12;
		
		if (head3_up.rotationPointY<15) head3_up.rotationPointY+=1;
		else head3_up.rotationPointY=15;
		
		if (head3_up.rotationPointZ>-20) head3_up.rotationPointZ-=(9/10);
		else head3_up.rotationPointZ=-20;
		
		if (head3_down.rotationPointY<15) head3_down.rotationPointY+=1;
		else head3_down.rotationPointY=15;
		
		if (head3_down.rotationPointZ>-19) head3_down.rotationPointZ-=(9/10);
		else head3_down.rotationPointZ=-19;

	}
	public void HeadUpper(){
		if (noumenon1.rotationPointZ>-4) noumenon1.rotationPointZ-=0.1;
		else noumenon1.rotationPointZ=-4;
		if (noumenon1.rotateAngleX>-0.5235988F)noumenon1.rotateAngleX-=(0.5235988F/10);
		else noumenon1.rotateAngleX=-0.5235988F;
		if (head1.rotationPointZ<-5) head1.rotationPointZ+=(3/10);
		else head1.rotationPointZ=-5;
		if (head1.rotateAngleX>-2.094395F)head1.rotateAngleX-=(2.094395F/10);
		else head1.rotateAngleX=-2.094395F;
		if (head2.rotationPointY>5) head2.rotationPointY-=1;
		else head2.rotationPointY=5;
		if (head2.rotationPointZ<-3) head2.rotationPointZ+=(9/10);
		else head2.rotationPointZ=-3;
		if (head3_up.rotationPointY>5) head3_up.rotationPointY-=1;
		else head3_up.rotationPointY=5;
		if (head3_up.rotationPointZ<-11) head3_up.rotationPointZ+=(9/10);
		else head3_up.rotationPointZ=-11;
		if (head3_down.rotationPointY>5) head3_down.rotationPointY-=1;
		else head3_down.rotationPointY=5;
		if (head3_down.rotationPointZ<-10) head3_down.rotationPointZ+=(9/10);
		else head3_down.rotationPointZ=-10;

	}
	public void OpenMouth(int Steps){
		if (head3_down.rotateAngleX<0.4363323F) head3_down.rotateAngleX+=(0.4363323F/Steps);
		else head3_down.rotateAngleX=0.4363323F;
		//mod_Fossil.ShowMessage(new StringBuilder().append(head3_down.rotateAngleX).toString());
	}
	public void CloseMouth(int Steps){
		if (head3_down.rotateAngleX>0) head3_down.rotateAngleX-=(0.4363323F/Steps);
		else head3_down.rotateAngleX=0;
		//mod_Fossil.ShowMessage(new StringBuilder().append(head3_down.rotateAngleX).toString());
	}
	

//fields
public ModelRenderer noumenon2;
public ModelRenderer noumenon3;
public ModelRenderer noumenon4;
public ModelRenderer thigh_L2;
public ModelRenderer thigh_L1;
public ModelRenderer noumenon1;
public ModelRenderer head1;
public ModelRenderer head2;
public ModelRenderer head3_up;
public ModelRenderer head3_down;
public ModelRenderer leg_L1;
public ModelRenderer leg_L2;
public ModelRenderer left_nail_1;
public ModelRenderer left_nail_2;
public ModelRenderer horn_2_L;
public ModelRenderer leg_R1;
public ModelRenderer thigh_R2;
public ModelRenderer thigh_R1;
public ModelRenderer leg_R2;
public ModelRenderer right_nail_2;
public ModelRenderer right_nail_1;
public ModelRenderer horn_1_R;

}
