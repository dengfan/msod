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
package net.sf.odinms.scripting.npc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import javax.script.Invocable;
import javax.script.ScriptEngine;

import net.sf.odinms.client.MapleClient;
import net.sf.odinms.net.world.MaplePartyCharacter;
import net.sf.odinms.scripting.AbstractScriptManager;
import net.sf.odinms.tools.FileoutputUtil;

/**
 *
 * @author Matze
 */
public class NPCScriptManager extends AbstractScriptManager {

    private Map<MapleClient, NPCConversationManager> cms = new HashMap<>();
    private Map<MapleClient, Invocable> scripts = new HashMap<>();
    private static NPCScriptManager instance = new NPCScriptManager();

    public synchronized static NPCScriptManager getInstance() {
        return instance;
    }

    public void start(MapleClient c, int npc) {
        start(c, npc, 0);
    }

    public void start(MapleClient c, int npc, int wh) {
        final String whStr = wh == 0 ? "" : "_" + wh;
        final String npcIdName = npc + whStr;
        final String path = String.format("npc/%s.js", npcIdName);

        // ��ֹ�����ظ����� 
        if (!c.canCallNpc(npcIdName)) {
            c.getPlayer().dropMessage(5, ">_< ���Ĳ������죬�����㡣");
            return;
        } else {
            c.setLastCallNpcTime(System.currentTimeMillis(), npcIdName);
        }

        if (c.getPlayer().isGM()) {
            c.getPlayer().dropMessage("[ϵͳ��ʾ] �ѽ�����NPC " + npcIdName + " �ĶԻ�");
        }

        final Lock lock = c.getNPCLock();
        lock.lock();

        try {
            if (cms.containsKey(c)) {
                //c.getPlayer().dropMessage(5, "[ϵͳ��ʾ] ��ɫ���ܽ������״̬�������� @k ���������״̬��");
                dispose(c);
                return;
            } else {
                Invocable iv = getInvocable(path, c);
                NPCConversationManager cm = new NPCConversationManager(c, npc, wh);

                if ((iv == null) || (getInstance() == null)) {
                    cm.sendOk("�Բ���\r\n��Ū������ð�յ��Ĺ�����\r\n���ܰ��������Ա��������\r\n�ҵ�Ա������� " + npcIdName);
                    cm.dispose();
                    return;
                }

                cms.put(c, cm);
                engine.put("cm", cm);
                scripts.put(c, iv);

                try {
                    iv.invokeFunction("start");
                } catch (NoSuchMethodException nsme) {
                    System.out.printf("[��ʾ] �ű�<%s>��start������ֱ��ִ��action������", path);
                    iv.invokeFunction("action", (byte) 1, (byte) 0, 0);
                }
            }
        } catch (Exception e) {
            log.error("�ű�ִ�г����ű��ļ���" + path + "\n�쳣���飺" + e);
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "�ű�ִ�г����ű��ļ���" + path + "\n�쳣���飺" + e);
            dispose(c);
        } finally {
            lock.unlock();
        }
    }

    public void start(String filename, MapleClient c, int npc, List<MaplePartyCharacter> chars) {
        start(filename, c, npc, 0, chars);
    }

    public void start(String filename, MapleClient c, int npc, int wh, List<MaplePartyCharacter> chars) { // CPQ start
        System.out.println("[��ʾ] ִ��CPQ start������");
        String path = "npc/" + filename + ".js";

        try {
            NPCConversationManager cm = new NPCConversationManager(c, npc, wh, chars, 0);
            cm.dispose();
            if (cms.containsKey(c)) {
                return;
            }
            cms.put(c, cm);

            Invocable iv = getInvocable(path, c);
            NPCScriptManager npcsm = NPCScriptManager.getInstance();
            if (iv == null || NPCScriptManager.getInstance() == null || npcsm == null) {
                cm.dispose();
                return;
            }
            engine.put("cm", cm);
            scripts.put(c, iv);
            iv.invokeFunction("start");
        } catch (Exception e) {
            log.error("�ű�ִ�г����ű��ļ���" + path + "\n�쳣���飺" + e);
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "�ű�ִ�г����ű��ļ���" + path + "\n�쳣���飺" + e);
            dispose(c);
        }
    }

    // mode = 0 ��ʾֱ�ӹرնԻ���
    // mode = 1 ��ʾ��ʼִ����ҵĶԻ������ѡ��
    public void action(MapleClient c, byte mode, byte type, int selection) {
        if (c.getCM() == null || mode == 0) { // ֱ�ӹرնԻ����ESC���رնԻ���
            dispose(c);
            return;
        }

        final Lock lock = c.getNPCLock();
        lock.lock();
        try {
            // ��ʾ�������ִ�жԻ���ѡ�ִ����Ϻ��ɽű�����������ע���Ի����� cm.dispose();
            Invocable iv = scripts.get(c);
            if (iv != null) {
                try {
                    iv.invokeFunction("action", mode, type, selection);
                } catch (Exception e) {
                    log.error("Error executing NPC script " + c.getCM().getNpc(), e);
                    dispose(c);
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public void dispose(NPCConversationManager cm) {
        MapleClient c = cm.getC();
        cms.remove(c);
        scripts.remove(c);

        int npc = cm.getNpc();
        int wh = cm.getWh();
        final String whStr = wh == 0 ? "" : "_" + wh;
        final String npcIdName = npc + whStr;
        resetContext(String.format("npc/%s.js", npcIdName), c);
        
        c.setLastCallNpcTime(System.currentTimeMillis(), npcIdName);
    }

    public void dispose(MapleClient c) {
        NPCConversationManager npccm = cms.get(c);
        if (npccm != null) {
            dispose(npccm);
        }
    }

    public NPCConversationManager getCM(MapleClient c) {
        return cms.get(c);
    }
}
