package net.minecraft.src;

public enum EnumDinoType {
	Triceratops(EntityTriceratops.class,true),
	Raptor(EntityRaptor.class,false),
	TRex(EntityTRex.class,false),
	Pterosaur(EntityPterosaur.class,true),
	Nautilus(EntityNautilus.class,false),
	Plesiosaur(EntityPlesiosaur.class,true),
	Mosasaurus(EntityMosasaurus.class,false),
	Stegosaurus(EntityStegosaurus.class,false),
	dilphosaur(Entitydil.class,false),
	Brachiosaurus(EntityBrachiosaurus.class,true);
	private final Class dinoClass;
	private final boolean modelable; 

	private EnumDinoType(Class dinoClassVar,boolean modelableVar) {
		this.dinoClass = dinoClassVar;
		this.modelable=modelableVar;
	}
	public Class getDinoClass() {
		return dinoClass;
	}
	public boolean isModelable() {
		return modelable;
	}
}
