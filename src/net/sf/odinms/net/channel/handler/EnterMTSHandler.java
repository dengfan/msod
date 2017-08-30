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

import java.rmi.RemoteException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.*;

import net.sf.odinms.client.Equip;
import net.sf.odinms.client.IItem;
import net.sf.odinms.client.Item;
import net.sf.odinms.client.MapleClient;
import net.sf.odinms.client.messages.ServernoticeMapleClientMessageCallback;
import net.sf.odinms.database.DatabaseConnection;
import net.sf.odinms.net.AbstractMaplePacketHandler;
import net.sf.odinms.net.channel.ChannelServer;
import net.sf.odinms.net.world.remote.WorldChannelInterface;
import net.sf.odinms.server.MTSItemInfo;
import net.sf.odinms.tools.MaplePacketCreator;
import net.sf.odinms.tools.data.input.SeekableLittleEndianAccessor;
import net.sf.odinms.net.channel.ChannelServer;
import net.sf.odinms.server.MaplePortal;
import net.sf.odinms.server.maps.MapleMap;
import net.sf.odinms.server.maps.SavedLocationType;
import net.sf.odinms.scripting.npc.NPCScriptManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnterMTSHandler extends AbstractMaplePacketHandler {
      @Override
      public void handlePacket(SeekableLittleEndianAccessor slea, MapleClient c) {
//            if ((c.getPlayer().getMapId() < 910000000) || (c.getPlayer().getMapId() > 910000022)){
//              new ServernoticeMapleClientMessageCallback(5, c).dropMessage("王下七武海之熊用果实能力把你拍到到自由市场！");
//              c.getSession().write(MaplePacketCreator.enableActions());
//                MapleMap to;
//                MaplePortal pto;
//                              to = ChannelServer.getInstance(c.getChannel()).getMapFactory().getMap(910000000);
//                              c.getPlayer().saveLocation(SavedLocationType.FREE_MARKET);
//                              pto = to.getPortal("out00"); // or st00?
//                                c.getPlayer().changeMap(to, pto);
//            } else {
//                              new ServernoticeMapleClientMessageCallback(5, c).dropMessage("你已经是在自由市场了，你还想干什么！");
//              c.getSession().write(MaplePacketCreator.enableActions());
//                          }
          NPCScriptManager.getInstance().start(c, 9900004);
          c.getSession().write(MaplePacketCreator.enableActions());
          }
      }
            
            