//CherryMS LoveMXD
//��ͬ���ֹת��
importPackage(Packages.net.sf.odinms.server.maps);
importPackage(Packages.net.sf.odinms.net.channel);
importPackage(Packages.net.sf.odinms.tools);

function enter(pi) {
	var papuMap = ChannelServer.getInstance(pi.getPlayer().getClient().getChannel()).getMapFactory().getMap(220080001);
	if (papuMap.playerCount()==0 && papuMap.mobCount()>0) {
		papuMap.killAllMonsters(false);
	}
	else { // someone is inside
		var mapobjects = papuMap.playerCount();
		if (mapobjects>0 && papuMap.mobCount()>0) {
			sendMessage(pi,"����������ս.");
			return false;
		}
		if (pi.getPlayer().getBossLog('Populatus0')>=5) {
			sendMessage(pi,"ÿ��ֻ����ս5��");
			return false;
		}
	}
	papuMap.resetReactors();
	
	pi.getPlayer().setBossLog('Populatus0');
	pi.warp(220080001);
	return true;
	//return pi.warp(220080001, "sp","st00");
}

function sendMessage(pi,message) {
	pi.getPlayer().getClient().getSession().write(MaplePacketCreator.serverNotice(5, message));
}
