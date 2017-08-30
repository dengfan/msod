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

package net.sf.odinms.server.maps;

import java.awt.Point;

import net.sf.odinms.client.MapleCharacter;
import net.sf.odinms.client.MapleClient;
import net.sf.odinms.client.SkillFactory;
import net.sf.odinms.tools.MaplePacketCreator;

/**
 *
 * @author Jan
 */
public class MapleSummon extends AbstractAnimatedMapleMapObject {

    private MapleCharacter owner;
    private int skillLevel;
    private int skill;
    private MapleMap map;
    private int hp;
    private SummonMovementType movementType;

    public MapleSummon(MapleCharacter owner, int skill, Point pos, SummonMovementType movementType) {
        super();
        this.owner = owner;
        this.skill = skill;
        this.map = owner.getMap();
        this.skillLevel = owner.getSkillLevel(SkillFactory.getSkill(skill));
        if (skillLevel == 0) {
            throw new RuntimeException("Trying to create a summon for a char without the skill");
        }
        this.movementType = movementType;
        setPosition(pos);
    }

    public void sendSpawnData(MapleClient client) {
        client.getSession().write(MaplePacketCreator.spawnSpecialMapObject(this, skillLevel, false));
    }

    public void sendDestroyData(MapleClient client) {
        client.getSession().write(MaplePacketCreator.removeSpecialMapObject(this, true));
    }

    public MapleCharacter getOwner() {
        return this.owner;
    }

    public final void updateMap(final MapleMap map) {
        this.map = map;
    }
    
    public int getSkill() {
        return this.skill;
    }

    public int getHP() {
        return this.hp;
    }

    public void addHP(int delta) {
        this.hp += delta;
    }

    public SummonMovementType getMovementType() {
        return movementType;
    }

    public boolean isPuppet() {
        //return (skill == 3111002 || skill == 3211002 || skill == 5211001 || skill == 13111004);
        return (skill == 3111002 || skill == 3211002 || skill == 5211001|| skill == 13111004);
    }

    public boolean isSummon() {
       // return (skill == 2311006 || skill == 2321003 || skill == 2121005 || skill == 2221005 || skill == 5211002);
        switch (skill) {
            case 12111004:
            case 1321007: //beholder
            case 2311006:
            case 2321003:
            case 2121005:
            case 2221005:
            case 5211001: // Pirate octopus summon
            case 5211002:
            case 5220002: // wrath of the octopi
            case 13111004:
            case 11001004:
            case 12001004:
            case 13001004:
            case 14001005:
            case 15001004:
                return true;
        }
        return false;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public final int getSummonType() {
        if (isPuppet()) {
            return 0;
        }
        switch (skill) {
            case 1321007:
                return 2;
            case 35111001: //satellite.
            case 35111009:
            case 35111010:
                return 3;
            case 35121009: //bots n. tots
                return 4;
        }
        return 1;
    }

    @Override
    public MapleMapObjectType getType() {
        return MapleMapObjectType.SUMMON;
    }
}
