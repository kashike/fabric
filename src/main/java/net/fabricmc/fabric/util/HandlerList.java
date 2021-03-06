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

package net.fabricmc.fabric.util;

public class HandlerList<T> implements HandlerRegistry<T> {
	private static final Object[] EMPTY = new Object[0];
	private Object[] array;

	@SuppressWarnings("unchecked")
	public HandlerList() {
		this.array = EMPTY;
	}

	@Override
	public void register(T handler) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == handler) {
				throw new RuntimeException("Handler " + handler + " already registered!");
			}
		}

		//noinspection unchecked
		T[] newArray = (T[]) new Object[array.length + 1];
		System.arraycopy(array, 0, newArray, 0, array.length);
		newArray[array.length] = handler;
		array = newArray;
	}

	public Object[] getBackingArray() {
		return array;
	}
}
