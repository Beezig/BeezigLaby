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

package eu.the5zig.mod.gui.ingame;

import java.util.HashMap;

/**
 * A class that represents a sidebar scoreboard.
 */
public class Scoreboard {

	private String title;
	private HashMap<String, Integer> lines;

	public Scoreboard(String title, HashMap<String, Integer> lines) {
		this.title = title;
		this.lines = lines;
	}

	/**
	 * @return the title of the scoreboard.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return a map, containing all scores together with their name.
	 */
	public HashMap<String, Integer> getLines() {
		return lines;
	}
}
