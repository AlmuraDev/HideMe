/*
 * This file is part of HideMe.
 *
 * © 2013 InspireNXE <http://www.inspirenxe.org/>
 * HideMe is licensed under the Spout License Version 1.
 *
 * HideMe is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the Spout License Version 1.
 *
 * HideMe is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the Spout License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://spout.in/licensev1> for the full license, including
 * the MIT license.
 */
package org.inspirenxe.hideme;

import java.io.File;

import org.spout.api.Spout;
import org.spout.api.exception.ConfigurationException;
import org.spout.api.util.config.ConfigurationHolder;
import org.spout.api.util.config.ConfigurationHolderConfiguration;
import org.spout.api.util.config.yaml.YamlConfiguration;

public class HideMeConfiguration extends ConfigurationHolderConfiguration {
	public static final ConfigurationHolder HIDDEN_PLAYER_JOIN_MESSAGE = new ConfigurationHolder("You are currently hidden.", "messages", "hidden_player_join");
	public static final ConfigurationHolder HIDDEN_CHANGE_WORLD_MESSAGE = new ConfigurationHolder("You are currently hidden.", "messages", "hidden_change_world");

	public HideMeConfiguration(File dataFolder) {
		super(new YamlConfiguration(new File(dataFolder, "config.yml")));
	}

	@Override
	public void load() {
		try {
			super.load();
			super.save();
		} catch (ConfigurationException e) {
			Spout.getLogger().severe("Unable to load HideMe's configuration file: " + e);
		}
	}

	@Override
	public void save() {
		try {
			super.save();
		} catch (ConfigurationException e) {
			Spout.getLogger().severe("Unable to save HideMe's configuration file: " + e);
		}
	}
}
