function enter(pi) {
	var map = pi.getPlayer().getMap();
	var reactor = map.getReactorByName("gate02");
	var state = reactor.getState();
	if (state >= 4) {
		pi.warp(670010600, 6);
		return true;
	} else {
		pi.getClient().getSession().write(Packages.net.sf.odinms.tools.MaplePacketCreator.serverNotice(5, "���ѹر�"));
		return false;
	}
}
