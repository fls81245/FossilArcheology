package net.minecraft.src;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class FossilBlockRenderHandler implements ISimpleBlockRenderingHandler {
	public FossilBlockRenderHandler(){
		super();
		mod_Fossil.blockRendererID=this.getRenderId();
	}
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getRenderId() {
		return 633;
	}

}
