package com.setadokalo.stairdoors.mixin;


import com.setadokalo.stairdoors.StairDoorsMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.DoorHinge;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DoorBlock.class)
public class DoorsMixin {
	@Inject(cancellable = true, method = "canPlaceAt(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;)Z", at = @At("HEAD"))
	private void canPlaceAtSemiBlock(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		BlockPos blockPos = pos.down();
		BlockState blockState = world.getBlockState(blockPos);
		if (blockState.getBlock() instanceof StairsBlock) {
			DoorBlock bs = (DoorBlock) state.getBlock();
			if (state.get(DoorBlock.FACING) == blockState.get(StairsBlock.FACING).rotateYClockwise().rotateYClockwise())
				cir.setReturnValue(true);
			else if (state.get(DoorBlock.FACING) == blockState.get(StairsBlock.FACING).rotateYClockwise() && state.get(DoorBlock.HINGE) == DoorHinge.LEFT)
				cir.setReturnValue(true);
			else if (state.get(DoorBlock.FACING) == blockState.get(StairsBlock.FACING).rotateYCounterclockwise() && state.get(DoorBlock.HINGE) == DoorHinge.RIGHT)
				cir.setReturnValue(true);
		} else if (blockState.getBlock() instanceof SlabBlock) {
            cir.setReturnValue(true);
        }
	}
}
