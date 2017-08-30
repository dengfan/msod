package net.sf.odinms.scripting.npc;

import java.util.List;
import net.sf.odinms.client.MapleCharacter;
import net.sf.odinms.net.world.MaplePartyCharacter;

/**
@author Matze
 */
public interface NPCScript {

    public void start();

    public void start(MapleCharacter chr);

    public void start(List<MaplePartyCharacter> chrs);

    public void action(byte mode, byte type, int selection);
}
