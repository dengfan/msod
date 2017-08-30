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

import net.sf.odinms.client.MapleCharacter;
import net.sf.odinms.client.MapleClient;
import net.sf.odinms.client.MapleStat;
import net.sf.odinms.client.messages.Command;
import net.sf.odinms.client.messages.CommandDefinition;
import net.sf.odinms.client.messages.IllegalCommandSyntaxException;
import net.sf.odinms.client.messages.MessageCallback;
import net.sf.odinms.server.maps.MapleMap;
import net.sf.odinms.net.channel.ChannelServer;
import net.sf.odinms.scripting.npc.NPCScriptManager;
import net.sf.odinms.server.MapleAchievements;
import net.sf.odinms.tools.MaplePacketCreator;
import net.sf.odinms.server.MaplePortal;
import net.sf.odinms.server.maps.SavedLocationType;

public class PlayerCommands implements Command {

    private long lasttime, nowtime;

    @Override
    public void execute(MapleClient c, MessageCallback mc, String[] splitted) throws Exception, IllegalCommandSyntaxException {
        MapleCharacter player = c.getPlayer();
        if (splitted[0].equals("@b")) {
            if ((c.getPlayer().getMapId() < 910000000) || (c.getPlayer().getMapId() > 910000022)) {
                c.getSession().write(MaplePacketCreator.enableActions());
                MapleMap to;
                MaplePortal pto;
                to = ChannelServer.getInstance(c.getChannel()).getMapFactory().getMap(910000000);
                c.getPlayer().saveLocation(SavedLocationType.FREE_MARKET);
                pto = to.getPortal("out00");
                c.getPlayer().changeMap(to, pto);
            } else {
                mc.dropMessage("你已经在自由市场了。");
                c.getSession().write(MaplePacketCreator.enableActions());
            }

        } else if (splitted[0].equalsIgnoreCase("@help")) {
            mc.dropMessage("指令列表：");
            mc.dropMessage("---------------------------");
            mc.dropMessage("@k   -- 解除不正常状态（如不能跟NPC对话，不能进地图等等）");
            mc.dropMessage("@b   -- 回到自由市场");
            mc.dropMessage("---------------------------");

//        } else if (splitted[0].equalsIgnoreCase("@s")) {
//            if (System.currentTimeMillis() - c.getPlayer().getLastSave() < (1000 * 60 * 15)) {
//                mc.dropMessage("请等待15分钟后再次保存.");
//            } else {
//                c.getPlayer().setLastSave(System.currentTimeMillis());
//                mc.dropMessage("恭喜，存档成功.");
//            }
//        } else if (splitted[0].equalsIgnoreCase("@achievements")) {
//            mc.dropMessage("Your finished achievements:");
//            for (Integer i : c.getPlayer().getFinishedAchievements()) {
//                mc.dropMessage(MapleAchievements.getInstance().getById(i).getName() + " - " + MapleAchievements.getInstance().getById(i).getReward() + " NX.");
//            }
        } else if (splitted[0].equals("@k")) {
            //NPCScriptManager.getInstance().dispose(c);
            if (c.getCM() != null) {
                c.getCM().dispose();
            }
            mc.dropMessage("你现在可以和NPC对话了。");
        }
    }

    @Override
    public CommandDefinition[] getDefinition() {
        return new CommandDefinition[]{
            new CommandDefinition("help", "", "显示玩家指令列表之指令", 0),
            new CommandDefinition("k", "", "解除不正常状态（如不能跟NPC对话）之指令", 0),
            new CommandDefinition("b", "", "回到自由市场之指令", 0)
        };
    }
}
