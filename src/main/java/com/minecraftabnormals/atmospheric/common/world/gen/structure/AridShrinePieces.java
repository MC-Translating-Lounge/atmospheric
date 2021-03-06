package com.minecraftabnormals.atmospheric.common.world.gen.structure;

import com.minecraftabnormals.atmospheric.core.Atmospheric;
import com.minecraftabnormals.atmospheric.core.registry.AtmosphericBlocks;
import com.minecraftabnormals.atmospheric.core.registry.AtmosphericStructures;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AridShrinePieces {
	private static final BlockPos STRUCTURE_OFFSET = new BlockPos(0, -3, 0);
	private static final ResourceLocation STRUCTURE = new ResourceLocation(Atmospheric.MOD_ID, "arid_shrine/arid_shrine");

	public static void func_204760_a(TemplateManager p_204760_0_, BlockPos p_204760_1_, Rotation p_204760_2_, List<StructurePiece> p_204760_3_, Random random) {
		p_204760_3_.add(new AridShrinePieces.Piece(p_204760_0_, p_204760_1_, p_204760_2_));
	}

	public static class Piece extends TemplateStructurePiece {
		private final Rotation rotation;

		public Piece(TemplateManager p_i48904_1_, BlockPos p_i48904_3_, Rotation p_i48904_4_) {
			super(AtmosphericStructures.Pieces.ARID_SHRINE_PIECE, 0);
			this.templatePosition = p_i48904_3_;
			this.rotation = p_i48904_4_;
			this.func_204754_a(p_i48904_1_);
		}

		public Piece(TemplateManager p_i50445_1_, CompoundNBT p_i50445_2_) {
			super(AtmosphericStructures.Pieces.ARID_SHRINE_PIECE, p_i50445_2_);
			this.rotation = Rotation.valueOf(p_i50445_2_.getString("Rot"));
			this.func_204754_a(p_i50445_1_);
		}

		@Override
		protected void readAdditional(CompoundNBT tagCompound) {
			super.readAdditional(tagCompound);
			tagCompound.putString("Rot", this.rotation.name());
		}

		private void func_204754_a(TemplateManager manager) {
			Template template = manager.getTemplateDefaulted(STRUCTURE);
			PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE).setCenterOffset(AridShrinePieces.STRUCTURE_OFFSET).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
			this.setup(template, this.templatePosition, placementsettings);
		}

		@Override
		protected void handleDataMarker(String function, BlockPos pos, IServerWorld worldIn, Random rand, MutableBoundingBox sbb) {
			if ("decor".equals(function)) {
				ArrayList<BlockState> stateList = new ArrayList<>();
				stateList.add(AtmosphericBlocks.ALOE_BUNDLE.get().getDefaultState().with(RotatedPillarBlock.AXIS, Axis.X));
				stateList.add(AtmosphericBlocks.ALOE_BUNDLE.get().getDefaultState().with(RotatedPillarBlock.AXIS, Axis.Y));
				stateList.add(AtmosphericBlocks.ALOE_BUNDLE.get().getDefaultState().with(RotatedPillarBlock.AXIS, Axis.Z));
				stateList.add(AtmosphericBlocks.ALOE_GEL_BLOCK.get().getDefaultState());
				stateList.add(AtmosphericBlocks.POTTED_BARREL_CACTUS.get().getDefaultState());
				stateList.add(AtmosphericBlocks.POTTED_GILIA.get().getDefaultState());
				stateList.add(AtmosphericBlocks.POTTED_YUCCA_FLOWER.get().getDefaultState());
				stateList.add(AtmosphericBlocks.POTTED_YUCCA_SAPLING.get().getDefaultState());
				stateList.add(AtmosphericBlocks.ROASTED_YUCCA_BUNDLE.get().getDefaultState());
				stateList.add(AtmosphericBlocks.YUCCA_BUNDLE.get().getDefaultState());
				stateList.add(AtmosphericBlocks.YUCCA_GATEAU.get().getDefaultState().with(HorizontalBlock.HORIZONTAL_FACING, Direction.NORTH));
				stateList.add(AtmosphericBlocks.YUCCA_GATEAU.get().getDefaultState().with(HorizontalBlock.HORIZONTAL_FACING, Direction.EAST));
				stateList.add(AtmosphericBlocks.YUCCA_GATEAU.get().getDefaultState().with(HorizontalBlock.HORIZONTAL_FACING, Direction.SOUTH));
				stateList.add(AtmosphericBlocks.YUCCA_GATEAU.get().getDefaultState().with(HorizontalBlock.HORIZONTAL_FACING, Direction.WEST));

				worldIn.setBlockState(pos, stateList.get(rand.nextInt(stateList.size())), 2);
			}
		}

		@Override
		public boolean func_230383_a_(ISeedReader world, StructureManager manager, ChunkGenerator generator, Random random, MutableBoundingBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
			int i = 256;
			BlockPos blockpos = this.template.getSize();
			int k = blockpos.getX() * blockpos.getZ();
			if (k != 0) {
				BlockPos blockpos1 = this.templatePosition.add(blockpos.getX() - 1, 0, blockpos.getZ() - 1);

				for (BlockPos blockpos2 : BlockPos.getAllInBoxMutable(this.templatePosition, blockpos1)) {
					int l = world.getHeight(Heightmap.Type.WORLD_SURFACE_WG, blockpos2.getX(), blockpos2.getZ());
					i = Math.min(i, l);
				}
			}

			int i1 = i - 1;
			this.templatePosition = new BlockPos(this.templatePosition.getX(), i1, this.templatePosition.getZ());
			return super.func_230383_a_(world, manager, generator, random, boundingBox, chunkPos, blockPos);
		}
	}
} 