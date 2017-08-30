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

package net.sf.odinms.client;

public enum MapleJob {

    BEGINNER(0),
    WARRIOR(100),
    FIGHTER(110),
    CRUSADER(111),
    HERO(112),
    PAGE(120),
    WHITEKNIGHT(121),
    PALADIN(122),
    SPEARMAN(130),
    DRAGONKNIGHT(131),
    DARKKNIGHT(132),
    MAGICIAN(200),
    FP_WIZARD(210),
    FP_MAGE(211),
    FP_ARCHMAGE(212),
    IL_WIZARD(220),
    IL_MAGE(221),
    IL_ARCHMAGE(222),
    CLERIC(230),
    PRIEST(231),
    BISHOP(232),
    BOWMAN(300),
    HUNTER(310),
    RANGER(311),
    BOWMASTER(312),
    CROSSBOWMAN(320),
    SNIPER(321),
    CROSSBOWMASTER(322),
    THIEF(400),
    ASSASSIN(410),
    HERMIT(411),
    NIGHTLORD(412),
    BANDIT(420),
    CHIEFBANDIT(421),
    SHADOWER(422),
    PIRATE(500),
    BRAWLER(510),
    MARAUDER(511),
    BUCCANEER(512),
    GUNSLINGER(520),
    OUTLAW(521),
    CORSAIR(522),
    GM(900),
    KNIGHT(1000),
    GHOST_KNIGHT(1100),
    GHOST_KNIGHT_2(1110),
    GHOST_KNIGHT_3(1111),
    FIRE_KNIGHT(1200),
    FIRE_KNIGHT_2(1210),
    FIRE_KNIGHT_3(1211),
    WIND_KNIGHT(1300),
    WIND_KNIGHT_2(1310),
    WIND_KNIGHT_3(1311),
    NIGHT_KNIGHT(1400),
    NIGHT_KNIGHT_2(1410),
    NIGHT_KNIGHT_3(1411),
    THIEF_KNIGHT(1500),
    THIEF_KNIGHT_2(1510),
    THIEF_KNIGHT_3(1511),
    Ares(2000),
    Ares_1(2100),
    Ares_2(2110),
    Ares_3(2111),
    Ares_4(2112);
    final int jobid;

    private MapleJob(int id) {
        jobid = id;
    }

    public int getId() {
        return jobid;
    }

    public static MapleJob getById(int id) {
        for (MapleJob l : MapleJob.values()) {
            if (l.getId() == id) {
                return l;
            }
        }
        return null;
    }

    public static MapleJob getBy5ByteEncoding(int encoded) {
        switch (encoded) {
            case 2:
                return WARRIOR;
            case 4:
                return MAGICIAN;
            case 8:
                return BOWMAN;
            case 16:
                return THIEF;
            case 32:
                return PIRATE;
            default:
                return BEGINNER;
        }
    }

    public boolean isA(MapleJob basejob) {
        return getId() >= basejob.getId() && getId() / 100 == basejob.getId() / 100;
    }

    public String getJobNameAsString() {
        MapleJob job = this;
        if (job == BEGINNER) {
            return "����";
        } else if (job == THIEF) {
            return "����";
        } else if (job == WARRIOR) {
            return "սʿ";
        } else if (job == MAGICIAN) {
            return "ħ��ʦ";
        } else if (job == BOWMAN) {
            return "������";
        } else if (job == PIRATE) {
            return "����";
        } else if (job == BANDIT) {
            return "����";
        } else if (job == ASSASSIN) {
            return "�̿�";
        } else if (job == SPEARMAN) {
            return "ǹսʿ";
        } else if (job == PAGE) {
            return "׼��ʿ";
        } else if (job == FIGHTER) {
            return "����";
        } else if (job == CLERIC) {
            return "��ʦ";
        } else if (job == IL_WIZARD) {
            return "���׷�ʦ";
        } else if (job == FP_WIZARD) {
            return "�𶾷�ʦ";
        } else if (job == HUNTER) {
            return "����";
        } else if (job == CROSSBOWMAN) {
            return "����";
        } else if (job == GUNSLINGER) {
            return "Gunslinger";
        } else if (job == BRAWLER) {
            return "Brawler";
        } else if (job == CHIEFBANDIT) {
            return "���п�";
        } else if (job == HERMIT) {
            return "��Ӱ��";
        } else if (job == DRAGONKNIGHT) {
            return "����ʿ";
        } else if (job == WHITEKNIGHT) {
            return "��ʿ";
        } else if (job == CRUSADER) {
            return "��ʿ";
        } else if (job == PALADIN) {
            return "ʥ��ʿ";
        } else if (job == PRIEST) {
            return "����";
        } else if (job == IL_MAGE) {
            return "����/��ʦ";
        } else if (job == FP_MAGE) {
            return "��/��ʦ";
        } else if (job == RANGER) {
            return "����";
        } else if (job == SNIPER) {
            return "����";
        } else if (job == MARAUDER) {
            return "Marauder";
        } else if (job == OUTLAW) {
            return "Outlaw";
        } else if (job == SHADOWER) {
            return "����";
        } else if (job == NIGHTLORD) {
            return "��ʿ";
        } else if (job == DARKKNIGHT) {
            return "Dark Knight";
        } else if (job == HERO) {
            return "Ӣ��";
        } else if (job == PALADIN) {
            return "ʥ��ʿ";
        } else if (job == IL_ARCHMAGE) {
            return "ħ��ʦ/����";
        } else if (job == FP_ARCHMAGE) {
            return "ħ��ʦ/��";
        } else if (job == BOWMASTER) {
            return "������";
        } else if (job == CROSSBOWMASTER) {
            return "����";
        } else if (job == BUCCANEER) {
            return "Buccaneer";
        } else if (job == CORSAIR) {
            return "Corsair";
        } else {
            return "����Ա";
        }
    }
}