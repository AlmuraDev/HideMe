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
import org.spout.api.entity.Player;
import org.spout.api.event.EventHandler;
import org.spout.api.event.Listener;
import org.spout.api.event.Order;
import org.spout.api.event.entity.EntityChangeWorldEvent;
import org.spout.api.event.player.PlayerJoinEvent;

public class HideMeListener implements Listener {
	private final HideMePlugin plugin;

	public HideMeListener(HideMePlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler(order = Order.LATEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (event.getPlayer().getData().get(HideMePlugin.IS_VISIBLE) == false) {
			Player player = event.getPlayer();

			for (Player onlinePlayers : ((Server) Spout.getEngine()).getOnlinePlayers()) {
				if (onlinePlayers.equals(player)) {
					continue;
				}
				onlinePlayers.setVisible(player, false);
			}
			System.out.println(player.hasPermission("hideme.message"));
			if (player.hasPermission("hideme.message")) {
				player.sendMessage(plugin.getPrefix(), HideMeConfiguration.HIDDEN_PLAYER_JOIN_MESSAGE.getString());
			}
		}
	}

	@EventHandler
	public void onWorldChange(EntityChangeWorldEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (player.getData().get(HideMePlugin.IS_VISIBLE) == false) {
				for (Player onlinePlayers : ((Server) Spout.getEngine()).getOnlinePlayers()) {
					if (onlinePlayers.equals(player)) {
						continue;
					}
					onlinePlayers.setVisible(player, false);
				}
				if (player.hasPermission("hideme.message")) {
					player.sendMessage(plugin.getPrefix(), HideMeConfiguration.HIDDEN_CHANGE_WORLD_MESSAGE.getString());
				}
			}
		}
	}
}
