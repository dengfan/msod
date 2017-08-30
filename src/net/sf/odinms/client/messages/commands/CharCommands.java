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

import java.util.Arrays;
import java.util.List;

import net.sf.odinms.client.Equip;
import net.sf.odinms.client.IItem;
import net.sf.odinms.client.ISkill;
import net.sf.odinms.client.Item;
import net.sf.odinms.client.MapleCharacter;
import net.sf.odinms.client.MapleClient;
import net.sf.odinms.client.MapleInventoryType;
import net.sf.odinms.client.MapleJob;
import net.sf.odinms.client.MaplePet;
import net.sf.odinms.client.MapleStat;
import net.sf.odinms.client.SkillFactory;
import net.sf.odinms.client.messages.Command;
import net.sf.odinms.client.messages.CommandDefinition;
import static net.sf.odinms.client.messages.CommandProcessor.getOptionalIntArg;
import net.sf.odinms.client.messages.IllegalCommandSyntaxException;
import net.sf.odinms.client.messages.MessageCallback;
import net.sf.odinms.net.MaplePacket;
import net.sf.odinms.net.channel.ChannelServer;
import net.sf.odinms.server.MapleInventoryManipulator;
import net.sf.odinms.server.MapleItemInformationProvider;
import net.sf.odinms.server.TimerManager;
import net.sf.odinms.server.constants.ServerConstants;
import net.sf.odinms.server.life.MapleLifeFactory;
import net.sf.odinms.server.life.MapleNPC;
import net.sf.odinms.server.maps.MapleMapObject;
import net.sf.odinms.server.maps.MapleMapObjectType;
import net.sf.odinms.tools.MaplePacketCreator;

public class CharCommands implements Command {

    @SuppressWarnings("static-access")
    @Override
    public void execute(MapleClient c, MessageCallback mc, String[] splitted) throws Exception, IllegalCommandSyntaxException {
        MapleCharacter player = c.getPlayer();
        if (splitted[0].equals("!maxstats")) {
            player.setMaxHp(30000);
            player.setMaxMp(30000);
            player.setStr(Short.MAX_VALUE);
            player.setDex(Short.MAX_VALUE);
            player.setInt(Short.MAX_VALUE);
            player.setLuk(Short.MAX_VALUE);
            player.updateSingleStat(MapleStat.MAXHP, 30000);
            player.updateSingleStat(MapleStat.MAXMP, 30000);
            player.updateSingleStat(MapleStat.STR, Short.MAX_VALUE);
            player.updateSingleStat(MapleStat.DEX, Short.MAX_VALUE);
            player.updateSingleStat(MapleStat.INT, Short.MAX_VALUE);
            player.updateSingleStat(MapleStat.LUK, Short.MAX_VALUE);
        } else if (splitted[0].equals("!minstats")) {
            player.setMaxHp(50);
            player.setMaxMp(5);
            player.setStr(4);
            player.setDex(4);
            player.setInt(4);
            player.setLuk(4);
            player.updateSingleStat(MapleStat.MAXHP, 50);
            player.updateSingleStat(MapleStat.MAXMP, 5);
            player.updateSingleStat(MapleStat.STR, 4);
            player.updateSingleStat(MapleStat.DEX, 4);
            player.updateSingleStat(MapleStat.INT, 4);
            player.updateSingleStat(MapleStat.LUK, 4);
        } else if (splitted[0].equals("!maxskills")) {
            c.getPlayer().maxAllSkills();
        } else if (splitted[0].equals("!maxhp")) {
            int stat = Integer.parseInt(splitted[1]);
            player.setMaxHp(stat);
            player.updateSingleStat(MapleStat.MAXHP, stat);
        } else if (splitted[0].equals("!maxmp")) {
            int stat = Integer.parseInt(splitted[1]);
            player.setMaxMp(stat);
            player.updateSingleStat(MapleStat.MAXMP, stat);
        } else if (splitted[0].equals("!hp")) {
            int stat = Integer.parseInt(splitted[1]);
            player.setHp(stat);
            player.updateSingleStat(MapleStat.HP, stat);
        } else if (splitted[0].equals("!mp")) {
            int stat = Integer.parseInt(splitted[1]);
            player.setMp(stat);
            player.updateSingleStat(MapleStat.MP, stat);
        } else if (splitted[0].equals("!str")) {
            int stat = Integer.parseInt(splitted[1]);
            player.setStr(stat);
            player.updateSingleStat(MapleStat.STR, stat);
        } else if (splitted[0].equals("!dex")) {
            int stat = Integer.parseInt(splitted[1]);
            player.setDex(stat);
            player.updateSingleStat(MapleStat.DEX, stat);
        } else if (splitted[0].equals("!int")) {
            int stat = Integer.parseInt(splitted[1]);
            player.setInt(stat);
            player.updateSingleStat(MapleStat.INT, stat);
        } else if (splitted[0].equals("!luk")) {
            int stat = Integer.parseInt(splitted[1]);
            player.setLuk(stat);
            player.updateSingleStat(MapleStat.LUK, stat);
        } else if (splitted[0].equals("!skill")) {
            ISkill skill = SkillFactory.getSkill(Integer.parseInt(splitted[1]));
            int level = getOptionalIntArg(splitted, 2, 1);
            int masterlevel = getOptionalIntArg(splitted, 3, 1);
            if (level > skill.getMaxLevel()) {
                level = skill.getMaxLevel();
            }
            if (masterlevel > skill.getMaxLevel() && skill.isFourthJob()) {
                masterlevel = skill.getMaxLevel();
            } else {
                masterlevel = 0;
            }
            player.changeSkillLevel(skill, level, masterlevel);
        } else if (splitted[0].equals("!god")) {
            boolean choice = true;
            int set = Integer.parseInt(splitted[1]);
            if (set == 1) {
                choice = true;
            } else if (set == 2) {
                choice = false;
            }
            player.setInvincible(choice);
        } else if (splitted[0].equals("!sp")) {
            int sp = Integer.parseInt(splitted[1]);
            if (sp + player.getRemainingSp() > Short.MAX_VALUE) {
                sp = Short.MAX_VALUE;
            }
            player.setRemainingSp(sp);
            player.updateSingleStat(MapleStat.AVAILABLESP, player.getRemainingSp());
        } else if (splitted[0].equals("!ap")) {
            int ap = Integer.parseInt(splitted[1]);
            if (ap + player.getRemainingAp() > Short.MAX_VALUE) {
                ap = Short.MAX_VALUE;
            }
            player.setRemainingAp(ap);
            player.updateSingleStat(MapleStat.AVAILABLEAP, player.getRemainingAp());
        } else if (splitted[0].equals("!job")) {
            int jobId = Integer.parseInt(splitted[1]);
            if (MapleJob.getById(jobId) != null) {
                player.changeJob(MapleJob.getById(jobId));
            }
        } else if (splitted[0].equals("!whereami")) {
            int currentMap = player.getMapId();
            mc.dropMessage("当前所在地图 " + currentMap);
        } else if (splitted[0].equals("!1")) {
            c.getPlayer().showDojoClock();
            c.getSession().write(MaplePacketCreator.enableActions());
        } else if (splitted[0].equals("!2")) {
            c.getSession().write(MaplePacketCreator.environmentChange("Dojang/start", 4));
            c.getSession().write(MaplePacketCreator.enableActions());
        } else if (splitted[0].equals("!3")) {
            c.getSession().write(MaplePacketCreator.environmentChange("dojang/start/stage", 3));
            c.getSession().write(MaplePacketCreator.enableActions());
        } else if (splitted[0].equals("!4")) {
            c.getSession().write(MaplePacketCreator.getEnergy(c.getPlayer().getDojoEnergy()));
            c.getSession().write(MaplePacketCreator.enableActions());
        } else if (splitted[0].equals("!5")) {
            c.getSession().write(MaplePacketCreator.sendBlockedMessage(5));
            c.getSession().write(MaplePacketCreator.enableActions());
        } else if (splitted[0].equals("!6")) {
            c.getSession().write(MaplePacketCreator.enableActions());
        } else if (splitted[0].equals("!关门")) {
            player.getMap().clearDrops(player, true);
        } else if (splitted[0].equals("!8")) {
            c.getSession().write(MaplePacketCreator.Combo_Effect(8));
            c.getSession().write(MaplePacketCreator.enableActions());
        } else if (splitted[0].equals("!meso")) {
            if (Integer.MAX_VALUE - (player.getMeso() + Integer.parseInt(splitted[1])) >= 0) {
                player.gainMeso(Integer.parseInt(splitted[1]), true);
            } else {
                player.gainMeso(Integer.MAX_VALUE - player.getMeso(), true);
            }
        } else if (splitted[0].equals("!levelup")) {
            if (player.getLevel() < 200) {
                player.levelUp();
                //player.setExp(0);
            } else {
                mc.dropMessage("你已满级了");
            }
        } else if (splitted[0].equals("!nitem")) {
            MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            short quantity = (short) getOptionalIntArg(splitted, 2, 1);
            if (Integer.parseInt(splitted[1]) >= 5000000 && Integer.parseInt(splitted[1]) <= 5000100) {
                if (quantity > 1) {
                    quantity = 1;
                }
                int petId = MaplePet.createPet(Integer.parseInt(splitted[1]));
                MapleInventoryManipulator.addById(c, Integer.parseInt(splitted[1]), quantity, player.getName() + "used !nitem with quantity " + quantity, player.getName(), petId);
                return;
            } else if (ii.isRechargable(Integer.parseInt(splitted[1]))) {
                quantity = ii.getSlotMax(c, Integer.parseInt(splitted[1]));
                MapleInventoryManipulator.addById(c, Integer.parseInt(splitted[1]), quantity, "Rechargable item created.", player.getName(), -1);
                return;
            }
            MapleInventoryManipulator.addById(c, Integer.parseInt(splitted[1]), quantity, player.getName() + "used !nitem with quantity " + quantity, player.getName(), -1);
        } else if (splitted[0].equals("!item")) {
            MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            short quantity = (short) getOptionalIntArg(splitted, 2, 1);
            if (Integer.parseInt(splitted[1]) >= 5000000 && Integer.parseInt(splitted[1]) <= 5000100) {
                if (quantity > 1) {
                    quantity = 1;
                }
                int petId = MaplePet.createPet(Integer.parseInt(splitted[1]));
                return;
            } else if (ii.isRechargable(Integer.parseInt(splitted[1]))) {
                quantity = ii.getSlotMax(c, Integer.parseInt(splitted[1]));
                return;
            }
            MapleInventoryManipulator.addById(c, Integer.parseInt(splitted[1]), quantity, player.getName() + "used !item with quantity " + quantity);
        } else if (splitted[0].equals("!alert")) {
            ChannelServer cserv = c.getChannelServer();
            cserv.getPlayerStorage().getCharacterByName(splitted[1]).gainWarning(true, 1);
        } else if (splitted[0].equals("!drop")) {
            MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            int itemId = Integer.parseInt(splitted[1]);
            short quantity = (short) getOptionalIntArg(splitted, 2, 1);
            IItem toDrop;
            if (ii.getInventoryType(itemId) == MapleInventoryType.EQUIP) {
                toDrop = ii.randomizeStats((Equip) ii.getEquipById(itemId));
            } else {
                toDrop = new Item(itemId, (byte) 0, quantity);
            }
            toDrop.log("Created by " + player.getName() + " using !drop. Quantity: " + quantity, false);
            toDrop.setOwner(player.getName());
            player.getMap().spawnItemDrop(player, player, toDrop, player.getPosition(), true, true);
        } else if (splitted[0].equals("!nonamedrop")) {
            MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            int itemId = Integer.parseInt(splitted[1]);
            short quantity = (short) getOptionalIntArg(splitted, 2, 1);
            IItem toDrop;
            if (ii.getInventoryType(itemId) == MapleInventoryType.EQUIP) {
                toDrop = ii.randomizeStats((Equip) ii.getEquipById(itemId));
            } else {
                toDrop = new Item(itemId, (byte) 0, quantity);
            }
            player.getMap().spawnItemDrop(player, player, toDrop, player.getPosition(), true, true);
        } else if (splitted[0].equals("!level")) {
            int quantity = Integer.parseInt(splitted[1]);
            c.getPlayer().setLevel(quantity);
            c.getPlayer().levelUp();
            int newexp = c.getPlayer().getExp();
            if (newexp < 0) {
                c.getPlayer().gainExp(-newexp, false, false);
            }
        } else if (splitted[0].equals("!maxlevel")) {
            while (player.getLevel() < 200) {
                player.levelUp();
            }
            player.gainExp(-player.getExp(), false, false);
        } else if (splitted[0].equals("!ring")) {
            /*
            int itemId = Integer.parseInt(splitted[1]);
            String partnerName = splitted[2];
            String message = splitted[3];
            int ret = MapleRing.createRing(itemId, player, player.getClient().getChannelServer().getPlayerStorage().getCharacterByName(partnerName), message);
            if (ret == -1) {
            mc.dropMessage("Error - Make sure the person you are attempting to create a ring with is online.");
            }
             */
        } else if (splitted[0].equals("!ariantpq")) {
            if (splitted.length < 2) {
                player.getMap().AriantPQStart();
            } else {
                c.getSession().write(MaplePacketCreator.updateAriantPQRanking(splitted[1], 5, false));
            }
        } else if (splitted[0].equals("!dh")) {
            c.getPlayer().startCygnusIntro();
        } else if (splitted[0].equals("!dh3")) {
            c.getPlayer().startCygnusIntro_3();
        } else if (splitted[0].equals("!playernpc")) {
            c.getPlayer().getPlayerNPC().createPNE();
            c.getPlayer().getPlayerNPC().createPlayerNPC(c.getPlayer(), c.getPlayer().getMapId());
        } else if (splitted[0].equals("!position")) {
            mc.dropMessage("Your current co-ordinates are: " + c.getPlayer().getPosition().x + " x and " + c.getPlayer().getPosition().y + " y.");
        } else if (splitted[0].equals("!clearinvent")) {
            if (splitted.length < 2) {
                mc.dropMessage("Please specify which tab to clear. If you want to clear all, use '!clearinvent all'.");
            } else {
                String type = splitted[1];
                boolean pass = false;
                if (type.equals("equip") || type.equals("all")) {
                    if (!pass) {
                        pass = true;
                    }
                    for (int i = 0; i < 101; i++) {
                        IItem tempItem = c.getPlayer().getInventory(MapleInventoryType.EQUIP).getItem((byte) i);
                        if (tempItem == null) {
                            continue;
                        }
                        MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.EQUIP, (byte) i, tempItem.getQuantity(), false, true);
                    }
                }
                if (type.equals("use") || type.equals("all")) {
                    if (!pass) {
                        pass = true;
                    }
                    for (int i = 0; i < 101; i++) {
                        IItem tempItem = c.getPlayer().getInventory(MapleInventoryType.USE).getItem((byte) i);
                        if (tempItem == null) {
                            continue;
                        }
                        MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.USE, (byte) i, tempItem.getQuantity(), false, true);
                    }
                }
                if (type.equals("etc") || type.equals("all")) {
                    if (!pass) {
                        pass = true;
                    }
                    for (int i = 0; i < 101; i++) {
                        IItem tempItem = c.getPlayer().getInventory(MapleInventoryType.ETC).getItem((byte) i);
                        if (tempItem == null) {
                            continue;
                        }
                        MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.ETC, (byte) i, tempItem.getQuantity(), false, true);
                    }
                }
                if (type.equals("setup") || type.equals("all")) {
                    if (!pass) {
                        pass = true;
                    }
                    for (int i = 0; i < 101; i++) {
                        IItem tempItem = c.getPlayer().getInventory(MapleInventoryType.SETUP).getItem((byte) i);
                        if (tempItem == null) {
                            continue;
                        }
                        MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.SETUP, (byte) i, tempItem.getQuantity(), false, true);
                    }
                }
                if (type.equals("cash") || type.equals("all")) {
                    if (!pass) {
                        pass = true;
                    }
                    for (int i = 0; i < 101; i++) {
                        IItem tempItem = c.getPlayer().getInventory(MapleInventoryType.CASH).getItem((byte) i);
                        if (tempItem == null || tempItem.getUniqueId() != 0) {
                            continue;
                        }
                        MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.CASH, (byte) i, tempItem.getQuantity(), false, true);
                    }
                }
                if (!pass) {
                    mc.dropMessage("!clearinvent " + type + " does not exist!");
                } else {
                    mc.dropMessage("Your inventory has been cleared!");
                }
            }
        } else if (splitted[0].equals("!godmode")) {
            player.setGodmode(!player.hasGodmode());
            mc.dropMessage("You are now " + (player.hasGodmode() ? "" : "not ") + "in godmode.");
        } else if (splitted[0].equals("!eventlevel")) {
            int minlevel = Integer.parseInt(splitted[1]);
            int maxlevel = Integer.parseInt(splitted[2]);
            int map = Integer.parseInt(splitted[3]);
            int minutes = getOptionalIntArg(splitted, 4, 5);
            if (splitted.length < 4) {
                mc.dropMessage("Syntax Error: !eventlevel <minlevel> <maxlevel> <mapid> <minutes>");
                return;
            }
            c.getChannelServer().startEvent(minlevel, maxlevel, map);
            final MapleNPC npc = MapleLifeFactory.getNPC(9201093);
            npc.setPosition(c.getPlayer().getPosition());
            npc.setCy(c.getPlayer().getPosition().y);
            npc.setRx0(c.getPlayer().getPosition().x + 50);
            npc.setRx1(c.getPlayer().getPosition().x - 50);
            npc.setFh(c.getPlayer().getMap().getFootholds().findBelow(c.getPlayer().getPosition()).getId());
            npc.setCustom(true);
            c.getPlayer().getMap().addMapObject(npc);
            c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.spawnNPC(npc));
            MaplePacket msgpacket = MaplePacketCreator.serverNotice(6, "The NPC " + npc.getName() + " will be in " + c.getPlayer().getMap().getMapName() + " for " + minutes + " minutes(s). Please talk to it to be warped to the Event (Must be in between level " + minlevel + " and " + maxlevel + ")");
            ChannelServer.getInstance(c.getChannel()).getWorldInterface().broadcastMessage(c.getPlayer().getName(), msgpacket.getBytes());
            final MapleCharacter playerr = c.getPlayer();
            TimerManager.getInstance().schedule(new Runnable() {

                @Override
                public void run() {
                    List<MapleMapObject> npcs = playerr.getMap().getMapObjectsInRange(playerr.getPosition(), Double.POSITIVE_INFINITY, Arrays.asList(MapleMapObjectType.NPC));
                    for (MapleMapObject npcmo : npcs) {
                        MapleNPC fnpc = (MapleNPC) npcmo;
                        if (fnpc.isCustom() && fnpc.getId() == npc.getId()) {
                            playerr.getMap().removeMapObject(fnpc.getObjectId());
                        }
                    }
                }
            }, minutes * 60 * 1000);
        } else if (splitted[0].equals("!dbg")) {
            ServerConstants.WRITEPACKET = !ServerConstants.WRITEPACKET;
        } else if (splitted[0].equals("!showeffect")) {
            String vars = splitted[1];
            if (vars.equals("adv")) {
                String path = "Effect/Direction3.img/goAdventure/Scene0";
                int infoId = -1;
                c.getSession().write(MaplePacketCreator.showWZEffectS(path, infoId));
                c.getPlayer().getMap().broadcastMessage(c.getPlayer(), MaplePacketCreator.showWZEffectS(path, infoId), false);
            } else {
                int effectId = Integer.parseInt(splitted[1]);
                c.getSession().write(MaplePacketCreator.showSpecialEffect(effectId));
                c.getPlayer().getMap().broadcastMessage(c.getPlayer(), MaplePacketCreator.showSpecialEffect(effectId), false);
            }
        }
    }

    @Override
    public CommandDefinition[] getDefinition() {
        return new CommandDefinition[]{
            new CommandDefinition("1", "", "", 3),
            new CommandDefinition("2", "", "", 3),
            new CommandDefinition("3", "", "", 3),
            new CommandDefinition("4", "", "", 3),
            new CommandDefinition("5", "", "", 3),
            new CommandDefinition("6", "", "", 3),
            new CommandDefinition("关门", "", "", 3),
            new CommandDefinition("8", "", "", 3),
            new CommandDefinition("maxstats", "", "", 50),
            new CommandDefinition("minstats", "", "", 50),
            new CommandDefinition("maxskills", "", "", 50),
            new CommandDefinition("maxhp", "", "", 50),
            new CommandDefinition("maxmp", "", "", 50),
            new CommandDefinition("hp", "", "", 50),
            new CommandDefinition("mp", "", "", 50),
            new CommandDefinition("str", "", "", 50),
            new CommandDefinition("dex", "", "", 50),
            new CommandDefinition("int", "", "", 50),
            new CommandDefinition("luk", "", "", 50),
            new CommandDefinition("skill", "", "", 4),
            new CommandDefinition("sp", "", "", 50),
            new CommandDefinition("ap", "", "", 50),
            new CommandDefinition("godmode", "", "", 50),
            new CommandDefinition("job", "", "", 50),
            new CommandDefinition("gob", "", "", 50),
            new CommandDefinition("whereami", "", "", 1),
            new CommandDefinition("alert", "", "", 1),
            new CommandDefinition("shop", "", "", 50),
            new CommandDefinition("meso", "", "", 50),
            new CommandDefinition("levelup", "", "", 50),
            new CommandDefinition("nitem", "", "", 50),
            new CommandDefinition("item", "", "", 50),
            new CommandDefinition("drop", "", "", 50),
            new CommandDefinition("nonamedrop", "", "", 50),
            new CommandDefinition("level", "", "", 50),
            new CommandDefinition("maxlevel", "", "", 50),
            new CommandDefinition("ring", "", "", 50),
            new CommandDefinition("ariantpq", "", "", 50),
            new CommandDefinition("dh", "", "", 50),
            new CommandDefinition("dh3", "", "", 50),
            new CommandDefinition("scoreboard", "", "", 50),
            new CommandDefinition("playernpc", "", "", 50),
            new CommandDefinition("charinfo", "charname", "Shows info about the character with the given name", 1),
            new CommandDefinition("selfinfo", "charname", "Shows info about your own character", 50),
            new CommandDefinition("position", "", "Shows your character's coordinates", 50),
            new CommandDefinition("clearinvent", "<all, equip, use, etc, setup, cash>", "Clears the desired inventory", 50),
            new CommandDefinition("eventlevel", "<minlevel> <maxlevel> <mapid> <minutes>", "Spawns NPC to warp to an event", 50),
            new CommandDefinition("dbg", "", "", 50),
            new CommandDefinition("showeffect", "", "", 50)
        };
    }
}
