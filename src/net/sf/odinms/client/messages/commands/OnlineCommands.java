/*
	This file is part of the OdinMS Maple Story Server
    Copyright (C) 2008 Patrick Huy <patrick.huy@frz.cc>
                       Matthias Butz <matze@odinms.de>
                       Jan Christian Meyer <vimes@odinms.de>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License version 3
    as published by the Free Software Foundation. You may not use, modify
    or distribute this program under any other version of the
    GNU Affero General Public License.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package net.sf.odinms.client.messages.commands;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Map;

import net.sf.odinms.net.channel.ChannelServer;
import net.sf.odinms.client.MapleClient;
import net.sf.odinms.client.MapleCharacter;
import net.sf.odinms.client.MapleCharacterUtil;
import net.sf.odinms.client.messages.Command;
import net.sf.odinms.client.messages.CommandDefinition;
import net.sf.odinms.client.messages.MessageCallback;
import net.sf.odinms.server.maps.MapleMap;

public class OnlineCommands implements Command {

    @Override
    public void execute(MapleClient c, MessageCallback mc, String[] splitted) throws Exception, RemoteException {
        if (splitted[0].toLowerCase().equals("!ol")) {
            mc.dropMessage("----------- 在线角色 -----------");
            for (ChannelServer cs : ChannelServer.getAllInstances()) {
                mc.dropMessage("[频道 " + cs.getChannel() + "]");
                for (MapleCharacter chr : cs.getPlayerStorage().getAllCharacters()) {
                    MapleMap mm = chr.getMap();
                    int id = chr.getId();
                    String name = chr.getName();
                    String mapName = mm.getMapName();
                    int mapId = mm.getId();
                    int lv = chr.getLevel();
                    String info = String.format("%s (%s) lv.%s @%s(%s)", name, id, lv, mapName, mapId);
                    mc.dropMessage(info);
                }
            }
        } else if (splitted[0].equalsIgnoreCase("!gmsonline")) {
            try {
                mc.dropMessage("在线管理员: " + c.getChannelServer().getWorldInterface().listGMs());
            } catch (RemoteException re) {
            }
        } else if (splitted[0].equalsIgnoreCase("!connected")) {
            try {
                Map<Integer, Integer> connected = c.getChannelServer().getWorldInterface().getConnected();
                StringBuilder conStr = new StringBuilder("连接数量: ");
                boolean first = true;
                for (int i : connected.keySet()) {
                    if (!first) {
                        conStr.append(", ");
                    } else {
                        first = false;
                    }
                    if (i == 0) {
                        conStr.append("总计: ");
                        conStr.append(connected.get(i));
                    } else {
                        conStr.append("频道 ");
                        conStr.append(i);
                        conStr.append(": ");
                        conStr.append(connected.get(i));
                    }
                }
                mc.dropMessage(conStr.toString());
            } catch (RemoteException e) {
                c.getChannelServer().reconnectWorld();
            }
        }
    }

    @Override
    public CommandDefinition[] getDefinition() {
        return new CommandDefinition[]{
                    new CommandDefinition("ol", "", "List all of the users on the server, organized by channel.", 50),
                    new CommandDefinition("channel", "", "List all characters online on a channel.", 50),
                    new CommandDefinition("gmsonline", "", "Shows the name of every GM that is online", 50),
                    new CommandDefinition("connected", "", "Shows how many players are connected on each channel", 50)
                };
    }
}