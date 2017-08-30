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
package net.sf.odinms.net.channel.handler;

import net.sf.odinms.client.MapleCharacter;
import net.sf.odinms.client.MapleClient;
import net.sf.odinms.net.AbstractMaplePacketHandler;
import net.sf.odinms.scripting.quest.QuestScriptManager;
import net.sf.odinms.server.quest.MapleQuest;
import net.sf.odinms.tools.MaplePacketCreator;
import net.sf.odinms.tools.data.input.SeekableLittleEndianAccessor;

/**
 *
 * @author Matze
 */
public class QuestActionHandler extends AbstractMaplePacketHandler {

    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(QuestActionHandler.class);

    public QuestActionHandler() {
    }

    public void handlePacket(SeekableLittleEndianAccessor slea, MapleClient c) {
        byte action = slea.readByte();
        short quest = slea.readShort();
        MapleCharacter player = c.getPlayer();
        if (action == 1) { // ��������
            int npc = slea.readInt();
            try {
                MapleQuest.getInstance(quest).start(player, npc);
                c.getSession().write(MaplePacketCreator.showSpecialEffect(10));
                c.getPlayer().getMap().broadcastMessage(c.getPlayer(), MaplePacketCreator.showSpecialEffect(10), false);
            } catch (Exception e) {
                log.error("Error starting quest. QuestID: " + quest, e);
            }
        } else if (action == 2) { // �������
            int npc = slea.readInt();
            slea.readInt(); // dont know *o*
            try {
                if (slea.available() >= 4) {
                    int selection = slea.readInt();
                    MapleQuest.getInstance(quest).complete(player, npc, selection, false);
                    c.getSession().write(MaplePacketCreator.showSpecialEffect(9));
                    c.getPlayer().getMap().broadcastMessage(c.getPlayer(), MaplePacketCreator.showSpecialEffect(9), false);
                } else {
                    MapleQuest.getInstance(quest).complete(player, npc);
                    c.getSession().write(MaplePacketCreator.showSpecialEffect(9));
                    c.getPlayer().getMap().broadcastMessage(c.getPlayer(), MaplePacketCreator.showSpecialEffect(9), false);
                }
            } catch (Exception e) {
                log.error("Error completing quest. QuestID: " + quest, e);
            }
        } else if (action == 3) { // ��������
            MapleQuest.getInstance(quest).forfeit(player);
        } else if (action == 4) { //�ű���ʼ����
            int npc = slea.readInt();
            slea.readInt(); // dont know *o*
            QuestScriptManager.getInstance().start(c, npc, quest);
        } else if (action == 5) { //�ű���������
            int npc = slea.readInt();
            slea.readInt(); // dont know *o*
            QuestScriptManager.getInstance().end(c, npc, quest);
            c.getSession().write(MaplePacketCreator.showSpecialEffect(9));
            c.getPlayer().getMap().broadcastMessage(c.getPlayer(), MaplePacketCreator.showSpecialEffect(9), false);
        }
    }
}
