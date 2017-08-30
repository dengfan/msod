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
package net.sf.odinms.scripting.portal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import net.sf.odinms.client.MapleClient;
import net.sf.odinms.scripting.EncodingDetect;
import net.sf.odinms.server.MaplePortal;
import net.sf.odinms.tools.FileoutputUtil;

public class PortalScriptManager {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PortalScriptManager.class);
    private static final PortalScriptManager instance = new PortalScriptManager();
    private Map<String, PortalScript> scripts = new HashMap<String, PortalScript>();
    private final static ScriptEngineFactory sef = new ScriptEngineManager().getEngineByName("nashorn").getFactory();

    public final static PortalScriptManager getInstance() {
        return instance;
    }

    private final PortalScript getPortalScript(String scriptName, MapleClient c) {
        if (scripts.containsKey(scriptName)) {
            return scripts.get(scriptName);
        }
        
        if (c.getPlayer().isGM()) {
            c.getPlayer().dropMessage("[系统提示] 已建立与传送门 " + scriptName + " 的对话");
        }
        
        String path = "scripts/portal/" + scriptName + ".js";
        File scriptFile = new File(path);
        if (!scriptFile.exists()) {
            scripts.put(scriptName, null);
            return null;
        }

        InputStream in = null;
        ScriptEngine portal = sef.getScriptEngine();
        try {
            in = new FileInputStream(scriptFile);
            BufferedReader bf = new BufferedReader(new InputStreamReader(in, EncodingDetect.getJavaEncode(scriptFile)));
              
            String lines = "load('nashorn:mozilla_compat.js');" + bf.lines().collect(Collectors.joining(System.lineSeparator()));
            CompiledScript compiled = ((Compilable) portal).compile(lines);
            compiled.eval();
        } catch (FileNotFoundException | UnsupportedEncodingException | ScriptException e) {
            log.error("脚本执行出错，脚本文件：" + path + "\n异常详情：" + e);
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "脚本执行出错，脚本文件：" + path + "\n异常详情：" + e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ignore) {
            }
        }

        PortalScript script = ((Invocable) portal).getInterface(PortalScript.class);
        scripts.put(scriptName, script);
        return script;
    }

    // rhino is thread safe so this should be fine without synchronisation
    public boolean executePortalScript(MaplePortal portal, MapleClient c) {
        PortalScript script = getPortalScript(portal.getScriptName(), c);

        if (script != null && !c.getPlayer().getBlockedPortals().contains(portal.getScriptName())) {
            return script.enter(new PortalPlayerInteraction(c, portal));
        } else {
            return false;
        }
    }

    public void clearScripts() {
        scripts.clear();
    }
}
