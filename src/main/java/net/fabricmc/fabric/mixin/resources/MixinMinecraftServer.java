/*
 * Copyright (c) 2016, 2017, 2018 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.fabricmc.fabric.mixin.resources;

import net.fabricmc.fabric.resources.impl.ModDataPackSupplier;
import net.minecraft.class_3283;
import net.minecraft.class_3288;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {
    @Shadow
    private class_3283<class_3288> field_4595;

    @Inject(method = "method_3800", at = @At(value = "INVOKE", target = "Lnet/minecraft/class_3283;method_14443(Lnet/minecraft/class_3285;)V", ordinal = 1))
    public void method_3800(File file, LevelProperties properties, CallbackInfo info) {
	    field_4595.method_14443(new ModDataPackSupplier());
    }
}
