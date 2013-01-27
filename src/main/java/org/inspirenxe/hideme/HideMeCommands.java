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
import org.spout.api.command.CommandContext;
import org.spout.api.command.CommandSource;
import org.spout.api.command.annotated.Command;
import org.spout.api.entity.Player;
import org.spout.api.exception.CommandException;

public class HideMeCommands {
	private final HideMePlugin plugin;

	public HideMeCommands(HideMePlugin plugin) {
		this.plugin = plugin;
	}

	@Command(aliases = {"hide"}, usage = "<player>", desc = "Allows either yourself or another player to be hidden.", min = 0, max = 1)
	public void hide(CommandContext args, CommandSource source) throws CommandException {
		if (args.length() == 0 && source.hasPermission("hideme.hide.me")) {
			if (source instanceof Player) {
				Player player = (Player) source;

				for (Player onlinePlayers : ((Server) Spout.getEngine()).getOnlinePlayers()) {
					if (onlinePlayers.equals(player)) {
						continue;
					}
					onlinePlayers.setVisible(player, false);
					player.getData().put(HideMePlugin.IS_VISIBLE, false);
				}
				player.sendMessage(plugin.getPrefix(), "You are now hidden.");
			}
		} else if (args.length() == 1 && source.hasPermission("hideme.hide.other")) {
			Player target = args.getPlayer(0, true);

			if (target == null) {
				source.sendMessage(plugin.getPrefix(), "The player '", args.getString(0), "' does not exist or is offline.");
				return;
			}

			for (Player onlinePlayers : ((Server) Spout.getEngine()).getOnlinePlayers()) {
				if (onlinePlayers.equals(target)) {
					continue;
				}
				onlinePlayers.setVisible(target, false);
				onlinePlayers.getData().put(HideMePlugin.IS_VISIBLE, false);
			}

			if (source != target) {
				if (source.hasPermission("hideme.message")) {
					source.sendMessage(plugin.getPrefix(), target.getName(), " is now hidden.");
				}
				if (target.hasPermission("hideme.message")) {
					target.sendMessage(plugin.getPrefix(), source.getName(), " has made you hidden.");
				}
			} else if (source == target) {
				if (target.hasPermission("hideme.message")) {
					target.sendMessage(plugin.getPrefix(), "You are now hidden.");
				}
			}

			if (source instanceof Server) {
				if (target.hasPermission("hideme.message")) {
					target.sendMessage(plugin.getPrefix(), "The Server has made you hidden.");
				}
			}
		} else {
			source.sendMessage(plugin.getPrefix(), "You do not have the permissions to perform that command!");
		}
	}

	@Command(aliases = {"show"}, usage = "<player>", desc = "Allows either yourself or another player to be seen.", min = 0, max = 1)
	public void show(CommandContext args, CommandSource source) throws CommandException {
		if (args.length() == 0 && source.hasPermission("hideme.show.me")) {
			if (source instanceof Player) {
				Player player = (Player) source;

				for (Player onlinePlayers : ((Server) Spout.getEngine()).getOnlinePlayers()) {
					if (onlinePlayers.equals(player)) {
						continue;
					}
					onlinePlayers.setVisible(player, true);
				}
				player.getData().put(HideMePlugin.IS_VISIBLE, true);
				if (player.hasPermission("hideme.message")) {
					player.sendMessage(plugin.getPrefix(), "You are now visible.");
				}
			}
		} else if (args.length() == 1 && source.hasPermission("hideme.show.other")) {
			Player target = args.getPlayer(0, true);

			if (target == null) {
				source.sendMessage(plugin.getPrefix(), "The player '", args.getString(0), "' does not exist or is offline.");
				return;
			}

			for (Player onlinePlayers : ((Server) Spout.getEngine()).getOnlinePlayers()) {
				if (onlinePlayers.equals(target)) {
					continue;
				}
				onlinePlayers.setVisible(target, true);
				onlinePlayers.getData().put(HideMePlugin.IS_VISIBLE, true);
			}

			if (source != target) {
				if (source.hasPermission("hideme.message")) {
					source.sendMessage(plugin.getPrefix(), target.getName(), " is now visible.");
				}
				if (target.hasPermission("hideme.message")) {
					target.sendMessage(plugin.getPrefix(), source.getName(), " has made you visible.");
				}
			} else if (source == target) {
				if (target.hasPermission("hideme.message")) {
					target.sendMessage(plugin.getPrefix(), "You are now visible");
				}
			}

			if (source instanceof Server) {
				if (target.hasPermission("hideme.message")) {
					target.sendMessage(plugin.getPrefix(), "The Server has made you visible.");
				}
			}
		} else {
			source.sendMessage(plugin.getPrefix(), "You do not have the permissions to perform that command!");
		}
	}
}
