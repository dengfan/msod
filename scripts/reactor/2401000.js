function act() {
	//if (rm.getPlayer().getClient().getChannelServer().getMapFactory().getMap(240060100).getCharacters().size() <= 0) {
		var map = rm.getPlayer().getClient().getChannelServer().getMapFactory().getMap(240060200);	
		rm.changeMusic("Bgm14/HonTale");
		rm.spawnMonster(8810026, 71, 260);
		rm.mapMessage(5, "伴随着洞穴强烈的震动,洞穴深处,黑暗龙王出现了.!");
	    var Reactors = map.getReactorById(2401000);
	    map.broadcastMessage(Packages.net.sf.odinms.tools.MaplePacketCreator.destroyReactor(Reactors));	
		rm.getReactor().getMap().addMapTimer(6 * 60 * 60, 240000000);	
		//rm.createMapMonitor(2,240050400,"sp","8810010,8810011,8810012,8810013,8810014,8810015,8810016,8810017",0,8810018);
    	//rm.createMapMonitor(2,240050400,"sp","8810002,8810004,8810005,8810006,8810007,8810008,8810009",1,8810003);
		//rm.createMapMonitor(rm.getPlayer().getMap().getId(), false, 0, "", 0, -1);
		//rm.getReactor().setState(6);
	//}
	//else {
	//	rm.mapMessage(5, "伴随着洞穴强烈的震动,洞穴深处,黑暗龙王出现了.!");
	//	rm.getReactor().setState(0);
	//}
}
