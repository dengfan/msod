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
package net.sf.odinms.net.mina;

import net.sf.odinms.client.MapleClient;
import net.sf.odinms.net.MaplePacket;
import net.sf.odinms.net.SendPacketOpcode;
import net.sf.odinms.server.constants.ServerConstants;
import net.sf.odinms.tools.FileoutputUtil;
import net.sf.odinms.tools.HexTool;
import net.sf.odinms.tools.MapleCustomEncryption;
import net.sf.odinms.tools.data.input.ByteArrayByteStream;
import net.sf.odinms.tools.data.input.GenericLittleEndianAccessor;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class MaplePacketEncoder implements ProtocolEncoder {

    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MaplePacketEncoder.class);

    public synchronized void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        MapleClient client = (MapleClient) session.getAttribute(MapleClient.CLIENT_KEY);
        if (client != null) {
            synchronized (client.getSendCrypto()) {
                final byte[] input = ((MaplePacket) message).getBytes();

                if (ServerConstants.WRITEPACKET) {
                    int packetLen = input.length;
                    int pHeader = readFirstShort(input);
                    String pHeaderStr = Integer.toHexString(pHeader).toUpperCase();
                    String op = lookupRecv(pHeader);
                    boolean show = true;
                    switch (op) {
                        case "WARP_TO_MAP":
                        case "PING":
                        case "NPC_ACTION":
                        case "UPDATE_STATS":
                        case "MOVE_PLAYER":
                        case "SPAWN_NPC":
                        case "SPAWN_NPC_REQUEST_CONTROLLER":
                        case "REMOVE_NPC":
                        case "MOVE_LIFE":
                        case "MOVE_MONSTER":
                        case "MOVE_MONSTER_RESPONSE":
                        case "SPAWN_MONSTER":
                        case "SPAWN_MONSTER_CONTROL":
                        case "ANDROID_MOVE":
                            show = false;
                    }
                    String Recv = "服务端发送 " + op + " [" + pHeaderStr + "] (" + packetLen + ")\r\n";
                    if (packetLen <= 50000) {
                        String RecvTo = Recv + HexTool.toString(input) + "\r\n" + HexTool.toStringFromAscii(input);
                        if (show) {
                            FileoutputUtil.packetLog("log\\服务端封包.log", RecvTo);
                            log.info(RecvTo);
                        }
                    } else {
                        log.info(HexTool.toString(new byte[]{input[0], input[1]}) + " ...");
                    }
                }

                byte[] unencrypted = new byte[input.length];
                System.arraycopy(input, 0, unencrypted, 0, input.length);
                byte[] ret = new byte[unencrypted.length + 4];
                byte[] header = client.getSendCrypto().getPacketHeader(unencrypted.length);
                MapleCustomEncryption.encryptData(unencrypted);
                client.getSendCrypto().crypt(unencrypted);
                System.arraycopy(header, 0, ret, 0, 4);
                System.arraycopy(unencrypted, 0, ret, 4, unencrypted.length);
                IoBuffer out_buffer = IoBuffer.wrap(ret);
                out.write(out_buffer);
            }
        } else {
            out.write(IoBuffer.wrap(((MaplePacket) message).getBytes()));
        }
    }

    public void dispose(IoSession session) throws Exception {
    }

    private String lookupRecv(int val) {
        for (SendPacketOpcode op : SendPacketOpcode.values()) {
            if (op.getValue() == val) {
                return op.name();
            }
        }
        return "UNKNOWN";
    }

    private int readFirstShort(byte[] arr) {
        return new GenericLittleEndianAccessor(new ByteArrayByteStream(arr)).readShort();
    }
}
