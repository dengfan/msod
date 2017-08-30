package net.sf.odinms.scripting;

import java.io.*;
import java.io.File;
import java.util.stream.Collectors;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import net.sf.odinms.client.MapleClient;
import net.sf.odinms.tools.FileoutputUtil;
import net.sf.odinms.tools.StringUtil;

/**
 *
 * @author Matze
 */
public abstract class AbstractScriptManager {

    protected ScriptEngine engine;
    private ScriptEngineManager sem;
    protected static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AbstractScriptManager.class);

    protected AbstractScriptManager() {
        sem = new ScriptEngineManager();
    }

    protected Invocable getInvocable(String path, MapleClient c) {
        InputStream in = null;
        try {
            path = "scripts/" + path;
            engine = null;
            
            if (c != null) {
                engine = c.getScriptEngine(path);
            }
            
            if (engine == null) {
                File scriptFile = new File(path);
                if (!scriptFile.exists()) {
                    return null;
                }
                
                engine = sem.getEngineByName("nashorn");

                if (c != null) {
                    c.setScriptEngine(path, engine);
                }

                in = new FileInputStream(scriptFile);
                BufferedReader bf = new BufferedReader(new InputStreamReader(in, EncodingDetect.getJavaEncode(scriptFile)));
                String lines = "load('nashorn:mozilla_compat.js');" + bf.lines().collect(Collectors.joining(System.lineSeparator()));
                engine.eval(lines);
            }
            
            return (Invocable) engine;
        } catch (FileNotFoundException | UnsupportedEncodingException | ScriptException e) {
            log.error("脚本执行出错，脚本文件：" + path + "\n异常详情：" + e);
            FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "脚本执行出错，脚本文件：" + path + "\n异常详情：" + e);
            return null;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ignore) {
            }
        }
    }

    protected void resetContext(String path, MapleClient c) {
        path = "scripts/" + path;
        c.removeScriptEngine(path);
    }
}