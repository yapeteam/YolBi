package today.yapeteam.mixin.accesors;

import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

/**
 * @author yuxiangll
 * @since 2024/6/8 上午5:57
 * IntelliJ IDEA
 */
@Mixin(ShaderProgram.class)
public interface IShaderProgram {
    @Accessor("loadedUniforms")
    Map<String, GlUniform> getUniformsHook();
}
