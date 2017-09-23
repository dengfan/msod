/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.odinms.server;

import net.sf.odinms.net.channel.ChannelServer;
import net.sf.odinms.net.login.LoginServer;
import net.sf.odinms.net.world.WorldServer;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.odinms.client.MapleCharacter;
import net.sf.odinms.database.DatabaseConnection;

/**
 *
 * @author Fan
 */
public class Start {

    public static void main(String[] args) {
        long currentTime = System.currentTimeMillis();

        //停用RMI
        //System.setProperty("javax.net.ssl.keyStore","scripts/Keys/filename.keystore");
        //System.setProperty("javax.net.ssl.keyStorePassword","passwd");
        //System.setProperty("javax.net.ssl.trustStore","scripts/Keys/filename.keystore");
        //System.setProperty("javax.net.ssl.trustStorePassword","passwd");
        //String key = System.getProperty("javax.net.ssl.keyStore");
        //System.out.println(key);
        try {
            WorldServer.start();
            System.out.println(":::::::::: World server launched ::::::::::");

            LoginServer.start();
            System.out.println(":::::::::: Login server launched ::::::::::");

            ChannelServer.start();
            System.out.println(":::::::::: Channel server launched ::::::::::");

            //GUI.main(null);
            //System.out.println("4. GUI manager launched :::::");
            
            System.out.println("=============================================");
            System.out.println(" All server launched successfully! Time: " + (System.currentTimeMillis() - currentTime) / 1000.0 + "s");
            System.out.println("=============================================");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (InstanceAlreadyExistsException e) {
            e.printStackTrace();
        } catch (MBeanRegistrationException e) {
            e.printStackTrace();
        } catch (NotCompliantMBeanException e) {
            e.printStackTrace();
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        定时任务(30);
    }

    public static void 定时任务(final int time) {
        TimerManager.getInstance().register(new Runnable() {
            @Override
            public void run() {
                Calendar calendar = Calendar.getInstance();

                // 定时查询，防止数据库连接过期
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = null;
                try {
                    PreparedStatement psu = con.prepareStatement("SELECT COUNT(id) FROM accounts WHERE loggedin = 2");
                    psu.execute();
                    psu.close();
                } catch (SQLException ex) {
                    System.err.println("查询在线账号出错：" + ex.getMessage());
                }

                // 凌晨3点清空疲劳值
                if (calendar.get(Calendar.HOUR_OF_DAY) == 3) {
                    try {
                        PreparedStatement psu = con.prepareStatement("UPDATE accounts SET df_tired_point = 0");
                        psu.executeUpdate();
                        psu.close();
                    } catch (SQLException ex) {
                        System.err.println("清空疲劳值出错：" + ex.getMessage());
                    }
                }

                // 定时积累在线玩家的疲劳值
                for (ChannelServer chl : ChannelServer.getAllInstances()) {
                    for (MapleCharacter chr : chl.getPlayerStorage().getAllCharacters()) {
                        if (chr == null) {
                            continue;
                        }

                        try {
                            chr.定时累积疲劳值(time);
                        } catch (SQLException ex) {
                            System.out.println("自动记录状态出错：" + ex.getMessage());
                        }
                    }
                }
            }
        }, 60000 * time);
    }
}
