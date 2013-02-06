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

import org.spout.api.Server;
import org.spout.api.Spout;
import org.spout.api.chat.ChatArguments;
import org.spout.api.chat.style.ChatStyle;
import org.spout.api.command.CommandRegistrationsFactory;
import org.spout.api.command.annotated.AnnotatedCommandRegistrationFactory;
import org.spout.api.command.annotated.SimpleAnnotatedCommandExecutorFactory;
import org.spout.api.command.annotated.SimpleInjector;
import org.spout.api.event.Listener;
import org.spout.api.map.DefaultedKey;
import org.spout.api.map.DefaultedKeyImpl;
import org.spout.api.plugin.CommonPlugin;
import org.spout.api.plugin.PluginLogger;

public class HideMePlugin extends CommonPlugin {
	public static final DefaultedKey<Boolean> IS_VISIBLE = new DefaultedKeyImpl<Boolean>("hideme_is_visible", true);
	private HideMeConfiguration config;

	@Override
	public void onEnable() {
		if (Spout.getEngine() instanceof Server) {
			final CommandRegistrationsFactory<Class<?>> commandRegFactory = new AnnotatedCommandRegistrationFactory(new SimpleInjector(this), new SimpleAnnotatedCommandExecutorFactory());
			getEngine().getRootCommand().addSubCommands(this, HideMeCommands.class, commandRegFactory);
			getEngine().getEventManager().registerEvents(new HideMeListener(this), this);
		}
		getLogger().info(getDescription().getVersion() + " enabled.");
	}

	@Override
	public void onDisable() {
		getLogger().info(getDescription().getVersion() + " disabled.");
	}

	@Override
	public void onLoad() {
		((PluginLogger) getLogger()).setTag(new ChatArguments("[", ChatStyle.DARK_GREEN, "HideMe", ChatStyle.RESET, "] "));
		config = new HideMeConfiguration(getDataFolder());
		config.load();
	}

	public ChatArguments getPrefix() {
		return ((PluginLogger) getLogger()).getTag();
	}
}
