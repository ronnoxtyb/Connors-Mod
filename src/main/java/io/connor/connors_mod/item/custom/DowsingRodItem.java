package io.connor.connors_mod.item.custom;

import io.connor.connors_mod.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

public class DowsingRodItem extends Item {
    public DowsingRodItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(context.getWorld().isClient()){
            BlockPos positionClicked = context.getBlockPos();
            PlayerEntity player = context.getPlayer();
            boolean foundBlock = false;

            for (int i = 0; i <= positionClicked.getY(); i++){
                Block blockBelow = context.getWorld().getBlockState(positionClicked.down(i)).getBlock();

                if(isVauluableBlock(blockBelow)){
                    outputValuableCoordinates(positionClicked, player, blockBelow);
                    foundBlock = true;
                    break;
                }
            }

            if(!foundBlock){
                player.sendMessage(new TranslatableText("item.connors_mod.dowsing_rod.no_valuables"), false);
            }

        }
        context.getStack().damage(1, context.getPlayer(), (player) -> player.sendToolBreakStatus(player.getActiveHand()));

        return super.useOnBlock(context);
    }

    private void outputValuableCoordinates(BlockPos blockPos, PlayerEntity player, Block blockBelow) {
        player.sendMessage(new LiteralText("Found " + blockBelow.asItem().getName().getString() + " at (" +
                blockPos.getX() + "," + blockPos.getY() + "," + blockPos.getZ() + ")"), false);
    }

    private boolean isVauluableBlock(Block block){
        return block == Blocks.COAL_ORE || block == Blocks.COPPER_ORE || block ==
                Blocks.IRON_ORE || block == Blocks.GOLD_ORE || block == Blocks.DIAMOND_ORE || block == ModBlocks.BRASS_ORE; }
}
