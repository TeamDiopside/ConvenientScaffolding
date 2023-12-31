package nl.teamdiopside.convenientscaffolding.mixin;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ScaffoldingBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import nl.teamdiopside.convenientscaffolding.Config;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScaffoldingBlock.class)
public abstract class ScaffoldingBlockMixin extends Block implements SimpleWaterloggedBlock {

    @Mutable
    @Shadow @Final public static int STABILITY_MAX_DISTANCE;

    public ScaffoldingBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void modifyMaxDistance(Properties properties, CallbackInfo ci) {
        STABILITY_MAX_DISTANCE = Config.INSTANCE.maxScaffoldingDistance;
    }

    @ModifyConstant(method = "tick", constant = @Constant(intValue = 7))
    private int tick(int constant) {
        return Config.INSTANCE.maxScaffoldingDistance;
    }

    @ModifyConstant(method = {"getDistance", "canSurvive"}, constant = @Constant(intValue = 7))
    private static int getDistance(int constant) {
        return Config.INSTANCE.maxScaffoldingDistance;
    }
}
