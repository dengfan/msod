package net.sf.odinms.manager;

import net.sf.odinms.client.MapleCharacter;
import net.sf.odinms.client.MapleClient;
import net.sf.odinms.net.RecvPacketOpcode;
import net.sf.odinms.net.SendPacketOpcode;
import net.sf.odinms.net.channel.ChannelServer;
import net.sf.odinms.server.MapleInventoryManipulator;
import net.sf.odinms.server.MapleItemInformationProvider;
import net.sf.odinms.tools.MaplePacketCreator;

import java.util.HashMap;
import java.util.Map;

public class CommandProcessor {
    public static String processCommand(String command) {
        int success = 0;
        StringBuilder message = new StringBuilder();

        try {
            String[] commands = command.split(" ");
            if ("保存".equalsIgnoreCase(commands[0])) {
                int count = 0;
                for (ChannelServer chl : ChannelServer.getAllInstances()) {
                    for (MapleCharacter chr : chl.getPlayerStorage().getAllCharacters()) {
                        chr.saveToDB(true);
                        count++;
                    }
                }
                message.append("共保存" + count + "个玩家数据。\r\n");
                success = 2;
            } else if ("add".equals(commands[0])) {
                success = addComandProcessor(commands[1], Integer.parseInt(commands[2]), Integer.parseInt(commands[3]));
            } else if ("在线人数".equals(commands[0])) {
                for (Map.Entry entry : selectAllCharacterId().entrySet()) {
                    message.append("用户名：").append(entry.getValue()).append(" ID：").append(entry.getKey()).append("\r\n");
                }
                success = 2;
            } else if ("item".equals(commands[0])) {
                success = addItem(Integer.parseInt(commands[1]), Integer.parseInt(commands[2]), Integer.parseInt(commands[3]));
            } else if ("显示包头".equals(commands[0])) {
                //RecvPacketOpcode.showPacketHead = true;
                //SendPacketOpcode.showPacketHead = true;
                success = 1;
            } else if ("不显示包头".equals(commands[0])) {
                //RecvPacketOpcode.showPacketHead = false;
                //SendPacketOpcode.showPacketHead = false;
                success = 1;
            } else if ("clients".equalsIgnoreCase(commands[0])) {
                message.append("Client:\n");
                for (ChannelServer c : ChannelServer.getAllInstances()) {
                    for (MapleCharacter character : c.getPlayerStorage().getAllCharacters()) {
                        message.append(character.getName() + ":" + character.getClient().toString() + "\n");
                    }
                }
                success = 2;
            } 
        } catch (Exception e) {
            System.out.println("命令格式错误！");
            e.printStackTrace();
            success = 0;
        }
        if (success == 1) {
            message.append("命令执行成功。");
        } else if (success == 0) {
            message.append("命令执行失败。");
        }
        return message.toString();
    }



    /**
     * do add command
     *
     * @param type
     * @param id       character id
     * @param quentity
     * @return
     */
    private static int addComandProcessor(String type, int quentity, int id) {
        try {
            if ("meso".equals(type)) {
                getMapleCharacterById(id).gainMeso(quentity, true);
            } else if ("cash".equals(type)) {
                getMapleCharacterById(id).modifyCSPoints(0, quentity);
            } else if ("exp".equals(type)) {
                getMapleCharacterById(id).gainExp(quentity, true, true);
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    /**
     * send item to player
     *
     * @param itemId
     * @param quantity
     * @param id
     * @return
     */
    private static int addItem(int itemId, int quantity, int charId) {
        try {
            MapleClient mc = getMapleCharacterById(charId).getClient();

            if (quantity >= 0) {
                MapleInventoryManipulator.addById(mc, itemId, (short) quantity, "获得道具 " + itemId);
            } else {
                MapleInventoryManipulator.removeById(mc, MapleItemInformationProvider.getInstance().getInventoryType(itemId), itemId, -quantity, true, false);
            }
            mc.getSession().write(MaplePacketCreator.getShowItemGain(itemId, (short) quantity, true));
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * get character from All user storage by id
     *
     * @param id
     * @return
     */
    private static MapleCharacter getMapleCharacterById(int id) {
        for (ChannelServer c : ChannelServer.getAllInstances()) {
            for (MapleCharacter character : c.getPlayerStorage().getAllCharacters()) {
                if (character.getId() == id) {
                    return character;
                }
            }
        }
        return null;
    }


    /**
     * get all character's id and name
     *
     * @return
     */
    private static Map<Integer, String> selectAllCharacterId() {
        Map<Integer, String> ids = new HashMap<>();
        for (ChannelServer c : ChannelServer.getAllInstances()) {
            for (MapleCharacter character : c.getPlayerStorage().getAllCharacters()) {
                ids.put(character.getId(), character.getName());
            }
        }
        return ids;
    }
}
