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

package net.fabricmc.fabric.block;

import net.fabricmc.fabric.tools.ToolManager;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.Tag;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.world.loot.LootTables;

import java.util.function.Function;

/**
 * Fabric's version of Block.Settings. Adds additional methods and hooks
 * not found in the original class.
 *
 * To use it, simply replace Block.Settings.create() with
 * FabricBlockSettings.create() and add .build() at the end to return the
 * vanilla Block.Settings instance beneath.
 */
public class FabricBlockSettings {
	public interface Delegate {
		void fabric_setMapColor(MaterialColor color);
		void fabric_setCollidable(boolean value);
		void fabric_setSoundGroup(BlockSoundGroup group);
		void fabric_setLuminance(int value);
		void fabric_setHardness(float value);
		void fabric_setResistance(float value);
		void fabric_setRandomTicks(boolean value);
		void fabric_setFriction(float value);
		void fabric_setDropTable(Identifier id);
	}

	protected final Block.Settings delegate;
	private final FabricBlockSettings.Delegate castDelegate;

	protected FabricBlockSettings(Material material) {
		delegate = Block.Settings.create(material);
		castDelegate = (FabricBlockSettings.Delegate) delegate;
	}

	protected FabricBlockSettings(Block base) {
		delegate = Block.Settings.copy(base);
		castDelegate = (FabricBlockSettings.Delegate) delegate;
	}

	public static FabricBlockSettings create(Material material) {
		return new FabricBlockSettings(material);
	}

	public static FabricBlockSettings copy(Block base) {
		return new FabricBlockSettings(base);
	}

	/* FABRIC HELPERS */

	public FabricBlockSettings setBreakByHand(boolean value) {
		ToolManager.entry(delegate).setBreakByHand(value);
		return this;
	}

	public FabricBlockSettings setBreakByTool(Tag<Item> tag) {
		return setBreakByTool(tag, 0);
	}

	public FabricBlockSettings setBreakByTool(Tag<Item> tag, int miningLevel) {
		ToolManager.entry(delegate).putBreakByTool(tag, miningLevel);
		return this;
	}

	/* DELEGATE WRAPPERS */

	public FabricBlockSettings setMaterialColor(MaterialColor color) {
		castDelegate.fabric_setMapColor(color);
		return this;
	}

	public FabricBlockSettings setMaterialColor(DyeColor color) {
		castDelegate.fabric_setMapColor(color.getMaterialColor());
		return this;
	}

	/**
	 * @deprecated Use {@link #setMaterialColor(MaterialColor) setMaterialColor} instead.
	 */
	@Deprecated
	public FabricBlockSettings setMapColor(MaterialColor color) {
		return setMaterialColor(color);
	}

	/**
	 * @deprecated Use {@link #setMaterialColor(DyeColor) setMaterialColor} instead.
	 */
	@Deprecated
	public FabricBlockSettings setMapColor(DyeColor color) {
		return setMaterialColor(color);
	}

	public FabricBlockSettings setCollidable(boolean value) {
		castDelegate.fabric_setCollidable(value);
		return this;
	}

	public FabricBlockSettings setSoundGroup(BlockSoundGroup group) {
		castDelegate.fabric_setSoundGroup(group);
		return this;
	}

	public FabricBlockSettings acceptRandomTicks() {
		castDelegate.fabric_setRandomTicks(true);
		return this;
	}

	public FabricBlockSettings setLuminance(int value) {
		castDelegate.fabric_setLuminance(value);
		return this;
	}

	public FabricBlockSettings setHardness(float value) {
		castDelegate.fabric_setHardness(value);
		castDelegate.fabric_setResistance(value);
		return this;
	}

	public FabricBlockSettings setResistance(float value) {
		castDelegate.fabric_setResistance(value);
		return this;
	}

	public FabricBlockSettings setStrength(float hardness, float resistance) {
		castDelegate.fabric_setHardness(hardness);
		castDelegate.fabric_setResistance(resistance);
		return this;
	}

	public FabricBlockSettings noDropTable() {
		castDelegate.fabric_setDropTable(LootTables.EMPTY);
		return this;
	}

	public FabricBlockSettings copyDropTable(Block block) {
		castDelegate.fabric_setDropTable(block.getDropTableId());
		return this;
	}

	public FabricBlockSettings setDropTable(Identifier id) {
		castDelegate.fabric_setDropTable(id);
		return this;
	}

	public FabricBlockSettings setFrictionCoefficient(float value) {
		castDelegate.fabric_setFriction(value);
		return this;
	}

	/* BUILDING LOGIC */

	public Block.Settings build() {
		return delegate;
	}

	public <T> T build(Function<Block.Settings, T> function) {
		return function.apply(delegate);
	}
}
