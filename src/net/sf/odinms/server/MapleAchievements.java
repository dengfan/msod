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

package net.sf.odinms.server;

import java.util.ArrayList;
import java.util.List;
import net.sf.odinms.tools.Pair;

/**
 *
 * @author Patrick/PurpleMadness
 */
public class MapleAchievements {

    private List<Pair<Integer, MapleAchievement>> achievements = new ArrayList<Pair<Integer, MapleAchievement>>();
    private static MapleAchievements instance = null;

    protected MapleAchievements() {
        achievements.add(new Pair<Integer, MapleAchievement>(3, new MapleAchievement("等级到达30级", 30, true, false)));
        achievements.add(new Pair<Integer, MapleAchievement>(4, new MapleAchievement("等级到达70级", 50, true, false)));
        achievements.add(new Pair<Integer, MapleAchievement>(5, new MapleAchievement("等级到达120级", 100, true, false)));
        achievements.add(new Pair<Integer, MapleAchievement>(7, new MapleAchievement("首次成功创建家族", 80, true, false)));
        achievements.add(new Pair<Integer, MapleAchievement>(9, new MapleAchievement("首次人气达到50点", 100, true, false)));
        achievements.add(new Pair<Integer, MapleAchievement>(12, new MapleAchievement("首次成功砸卷", 50, true, false)));
        achievements.add(new Pair<Integer, MapleAchievement>(22, new MapleAchievement("到达200级", 200, true, false)));
        achievements.add(new Pair<Integer, MapleAchievement>(24, new MapleAchievement("首次击败扎昆", 200, true, false)));
        achievements.add(new Pair<Integer, MapleAchievement>(26, new MapleAchievement("首次击败暗黑龙王", 200, true, false)));
        achievements.add(new Pair<Integer, MapleAchievement>(28, new MapleAchievement("首次击败品克缤", 200, true, false)));
    }

    public static MapleAchievements getInstance() {
        if (instance == null) {
            instance = new MapleAchievements();
        }
        return instance;
    }

    public MapleAchievement getById(int id) {
        for (Pair<Integer, MapleAchievement> achievement : this.achievements) {
            if (achievement.getLeft() == id) {
                return achievement.getRight();
            }
        }
        return null;
    }

    public Integer getByMapleAchievement(MapleAchievement ma) {
        for (Pair<Integer, MapleAchievement> achievement : this.achievements) {
            if (achievement.getRight() == ma) {
                return achievement.getLeft();
            }
        }
        return null;
    }
}