package net.sf.odinms.tools;

import java.io.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.sf.odinms.client.MapleCharacter;

public class FileoutputUtil {

    private static final SimpleDateFormat sdfT = new SimpleDateFormat("yyyy��MM��dd��HHʱmm��ss��");
    // Logging output file
    public static final String fixdam_mg = "log\\ħ���˺�����.log",
            fixdam_ph = "log\\�����˺�����.log",
            MobVac_log = "log\\����.log",
            hack_log = "log\\�������.log",
            ban_log = "log\\���.log",
            Acc_Stuck = "log\\���˺�.log",
            Login_Error = "log\\��¼����.log",
            //Timer_Log = "log\\Log_Timer_Except.log",
            //MapTimer_Log = "log\\Log_MapTimer_Except.log",
            IP_Log = "log\\�˺�IP.log",
            //GMCommand_Log = "Log_GMCommand.log",
            Zakum_Log = "log\\����.log",
            Horntail_Log = "log\\��������.log",
            Pinkbean_Log = "log\\Ʒ����.log",
            ScriptEx_Log = "log\\�ű��쳣.log",
            PacketEx_Log = "log\\����쳣.log" // I cba looking for every error, adding this back in.
            + "";
    // End
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat sdf_ = new SimpleDateFormat("yyyy-MM-dd");

    public static void logToFile_chr(MapleCharacter chr, final String file, final String msg) {
        logToFile(file, "\r\n" + FileoutputUtil.CurrentReadable_Time() + " �˺ţ�" + chr.getClient().getAccountName() + " ��ɫ��" + chr.getName() + " (" + chr.getId() + ") �ȼ���" + chr.getLevel() + " ��ͼ��" + chr.getMapId() + " " + msg, false);
    }

    public static void logToFile(final String file, final String msg) {
        logToFile(file, msg, false);
    }

    public static void logToFile(final String file, final String msg, boolean notExists) {
        FileOutputStream out = null;
        try {
            File outputFile = new File(file);
            if (outputFile.exists() && outputFile.isFile() && outputFile.length() >= 10 * 1024 * 1000) {
                outputFile.renameTo(new File(file.substring(0, file.length() - 4) + "_" + sdfT.format(Calendar.getInstance().getTime()) + file.substring(file.length() - 4, file.length())));
                outputFile = new File(file);
            }
            if (outputFile.getParentFile() != null) {
                outputFile.getParentFile().mkdirs();
            }
            out = new FileOutputStream(file, true);
            if (!out.toString().contains(msg) || !notExists) {
                OutputStreamWriter osw = new OutputStreamWriter(out, "UTF-8");
                osw.write(msg);
                osw.flush();
            }
        } catch (IOException ess) {
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ignore) {
            }
        }
    }

    public static void packetLog(String file, String msg) {
        boolean notExists = false;
        FileOutputStream out = null;
        try {
            File outputFile = new File(file);
            if (outputFile.exists() && outputFile.isFile() && outputFile.length() >= 1024 * 1000) {
                outputFile.renameTo(new File(file.substring(0, file.length() - 4) + "_" + sdfT.format(Calendar.getInstance().getTime()) + file.substring(file.length() - 4, file.length())));
                outputFile = new File(file);
            }
            if (outputFile.getParentFile() != null) {
                outputFile.getParentFile().mkdirs();
            }
            out = new FileOutputStream(file, true);
            if (!out.toString().contains(msg) || !notExists) {
                OutputStreamWriter osw = new OutputStreamWriter(out, "UTF-8");
                osw.write(msg);
                osw.flush();
            }
        } catch (IOException ess) {
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ignore) {
            }
        }
    }

    public static void log(final String file, final String msg) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file, true);
            out.write(("\r\n------------------------ " + CurrentReadable_Time() + " ------------------------\r\n").getBytes());
            out.write(msg.getBytes());
        } catch (IOException ess) {
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ignore) {
            }
        }
    }

    public static void outputFileError(final String file, final Throwable t) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file, true);
            out.write(("\r\n------------------------ " + CurrentReadable_Time() + " ------------------------\r\n").getBytes());
            out.write(getString(t).getBytes());
        } catch (IOException ess) {
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ignore) {
            }
        }
    }

    public static String CurrentReadable_Date() {
        return sdf_.format(Calendar.getInstance().getTime());
    }

    public static String CurrentReadable_Time() {
        return sdf.format(Calendar.getInstance().getTime());
    }

    public static String getString(final Throwable e) {
        String retValue = null;
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            retValue = sw.toString();
        } finally {
            try {
                if (pw != null) {
                    pw.close();
                }
                if (sw != null) {
                    sw.close();
                }
            } catch (IOException ignore) {
            }
        }
        return retValue;
    }

    public static String NowTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
