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

import net.sf.odinms.client.MapleClient;
import net.sf.odinms.client.MapleCharacter;
import net.sf.odinms.net.AbstractMaplePacketHandler;
import net.sf.odinms.scripting.npc.NPCScriptManager;
import net.sf.odinms.tools.data.input.SeekableLittleEndianAccessor;
import net.sf.odinms.tools.MaplePacketCreator;

public class RingActionHandler extends AbstractMaplePacketHandler {

    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RingActionHandler.class);

    @Override
    public void handlePacket(SeekableLittleEndianAccessor slea, MapleClient c) {
        byte mode = slea.readByte();
        switch (mode) {
            case 0: //Send
                String partnerName = slea.readMapleAsciiString();
                if (partnerName.equalsIgnoreCase(c.getPlayer().getName())) {
                    c.getSession().write(MaplePacketCreator.serverNotice(1, "���������Լ�������"));
                    return;
                }
                MapleCharacter partner = c.getChannelServer().getPlayerStorage().getCharacterByName(partnerName);
                if (partner == null) {
                    c.getSession().write(MaplePacketCreator.serverNotice(1, partnerName + " �ڴ�Ƶ��û���ҵ�"));
                    return;
                } else if (partner.getGender() == c.getPlayer().getGender()) {
                    c.getSession().write(MaplePacketCreator.serverNotice(1, "�Ա����"));
                    return;
                } else {
                    NPCScriptManager.getInstance().start(partner.getClient(), 9201002);
                }
                break;
            case 1: //Cancel send
                c.getSession().write(MaplePacketCreator.serverNotice(1, "�����ѳɹ�ȡ��"));
                break;
            case 3: //Drop Ring
                try {
                    //MapleCharacterUtil.divorceEngagement(c.getPlayer());
                    c.getSession().write(MaplePacketCreator.serverNotice(1, "ȡ��?"));
                } catch (Exception exc) {
                    log.error("Error divorcing engagement", exc);
                }
                break;
        }
    }
}