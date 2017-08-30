/*
	This file is part of the OdinMS Maple Story Server
    Copyright (C) 2008 Patrick Huy <patrick.huy@frz.cc>
		       Matthias Butz <matze@odinms.de>
		       Jan Christian Meyer <vimes@odinms.de>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation version 3 as published by
    the Free Software Foundation. You may not use, modify or distribute
    this program under any other version of the GNU Affero General Public
    License.

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
import net.sf.odinms.net.channel.ChannelServer;
import net.sf.odinms.tools.MaplePacketCreator;
import net.sf.odinms.tools.data.input.SeekableLittleEndianAccessor;

/**
 *
 * @author
 */
public final class AcceptFamilyHandler extends AbstractMaplePacketHandler {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AcceptFamilyHandler.class);

    public final void handlePacket(SeekableLittleEndianAccessor slea, MapleClient c) {
        log.info("111111111111111111");
        System.out.println(slea.toString());
        int inviterId = slea.readInt();
//        String inviterName = slea.readMapleAsciiString();
        MapleCharacter inviter = ChannelServer.getCharacterFromAllServers(inviterId);
        if (inviter != null) {
            inviter.getClient().getSession().write(MaplePacketCreator.sendFamilyJoinResponse(true, c.getPlayer().getName()));
        }
        c.getSession().write(MaplePacketCreator.sendFamilyMessage());
    }
}