/*
 *    Copyright 2016 5zig
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package eu.the5zig.mod.server;

import java.util.List;

/**
 * Represents the result of a matched pattern.
 */
public class IPatternResult {

	private final List<String> result;

	public IPatternResult(final List<String> result) {
		this.result = result;
	}

	public int size() {
		return this.result.size();
	}

	public String get(final int index) {
		if (index < 0 || index >= this.size()) {
			return "";
		}
		return this.result.get(index);
	}

	@Override
	public String toString() {
		return this.result.toString();
	}

}
