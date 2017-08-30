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

import net.sf.odinms.client.MapleClient;
import net.sf.odinms.client.messages.CommandDefinition;
import net.sf.odinms.client.messages.Command;
import net.sf.odinms.client.messages.IllegalCommandSyntaxException;
import net.sf.odinms.client.messages.MessageCallback;
import net.sf.odinms.net.channel.ChannelServer;
import net.sf.odinms.server.TimerManager;
import net.sf.odinms.tools.MaplePacketCreator;

public class RateCommands implements Command {

    @Override
    public void execute(MapleClient c, MessageCallback mc, String[] splitted) throws Exception, IllegalCommandSyntaxException {
        if (splitted[0].equals("!rate")) {
            if (splitted.length > 2) {
                int arg = Integer.parseInt(splitted[2]);
                int seconds = Integer.parseInt(splitted[3]);
                int mins = Integer.parseInt(splitted[4]);
                int hours = Integer.parseInt(splitted[5]);
                int time = seconds + (mins * 60) + (hours * 60 * 60);
                boolean bOk = true;
                for (final ChannelServer cservs : ChannelServer.getAllInstances()) {
                    if (splitted[1].equals("exp")) {
                        if (arg <= 50) {
                            cservs.setExpRate(arg);
                            cservs.broadcastPacket(MaplePacketCreator.serverNotice(6, "�����Ѿ��ɹ��޸�Ϊ " + arg + "����ף�����Ϸ���ģ�"));
                        } else {
                            mc.dropMessage("�����ѱ�ϵͳ���ơ�");
                        }
                    } else if (splitted[1].equals("drop")) {
                        if (arg <= 5) {
                            cservs.setDropRate(arg);
                            cservs.broadcastPacket(MaplePacketCreator.serverNotice(6, "�����Ѿ��ɹ��޸�Ϊ " + arg + "����ף�����Ϸ���ģ�"));
                        } else {
                            mc.dropMessage("�����ѱ�ϵͳ���ơ�");
                        }
                    } else if (splitted[1].equals("meso")) {
                        if (arg <= 5) {
                            cservs.setMesoRate(arg);
                            cservs.broadcastPacket(MaplePacketCreator.serverNotice(6, "����Ѿ��ɹ��޸�Ϊ " + arg + "����ף�����Ϸ���ģ�"));
                        } else {
                            mc.dropMessage("�����ѱ�ϵͳ���ơ�");
                        }
                    } else if (splitted[1].equals("bossdrop")) {
                        if (arg <= 5) {
                            cservs.setBossDropRate(arg);
                            cservs.broadcastPacket(MaplePacketCreator.serverNotice(6, "BOSS�����Ѿ��ɹ��޸�Ϊ " + arg + "����ף�����Ϸ���ģ�"));
                        } else {
                            mc.dropMessage("�����ѱ�ϵͳ���ơ�");
                        }
                    } else if (splitted[1].equals("petexp")) {
                        if (arg <= 5) {
                            cservs.setPetExpRate(arg);
                            cservs.broadcastPacket(MaplePacketCreator.serverNotice(6, "���ﾭ���Ѿ��ɸ��޸�Ϊ " + arg + "����ף�����Ϸ���ģ�"));
                        } else {
                            mc.dropMessage("�����ѱ�ϵͳ���ơ�");
                        }
                    } else {
                        bOk = false;
                    }
                    final String rate = splitted[1];
                    TimerManager.getInstance().schedule(new Runnable() {

                        @Override
                        public void run() {
                            if (rate.equals("exp")) {
                                cservs.setExpRate(30);
                            } else if (rate.equals("drop")) {
                                cservs.setDropRate(1);
                            } else if (rate.equals("meso")) {
                                cservs.setMesoRate(5);
                            } else if (rate.equals("bossdrop")) {
                                cservs.setBossDropRate(1);
                            } else if (rate.equals("petexp")) {
                                cservs.setPetExpRate(2);
                            }
                            cservs.broadcastPacket(MaplePacketCreator.serverNotice(6, " ϵͳ˫����Ѿ�������ϵͳ�ѳɹ��Զ��л�Ϊ������Ϸģʽ��"));
                        }
                    }, time * 1000);
                }
                if (bOk == false) {
                    mc.dropMessage("ʹ�÷���: !rate <exp|drop|meso|boss|pet> <��> <��> <��> <ʱ>");
                }
            } else {
                mc.dropMessage("ʹ�÷���: !rate <exp|drop|meso|boss|pet> <��> <��> <��> <ʱ>");
            }
        } else if (splitted[0].equals("!rates")) {
            ChannelServer cserv = c.getChannelServer();
            mc.dropMessage("Ŀǰ���������鱬����Ϣ:");
            mc.dropMessage("����: " + cserv.getExpRate() + "�� | ����: " + cserv.getPetExpRate() + "��");
            mc.dropMessage("����: " + cserv.getDropRate() + "�� | BOSS����: " + cserv.getBossDropRate() + "��");
            mc.dropMessage("���: " + cserv.getMesoRate() + "��");
        }
    }

    @Override
    public CommandDefinition[] getDefinition() {
        return new CommandDefinition[]{
                    new CommandDefinition("rate", "<exp|drop|meso|bossdrop|petexp> <amount> <seconds> <minutes> <hours>", "Changes the specified rate", 50),
                    new CommandDefinition("rates", "", "Shows each rate", 50)
                };
    }
}