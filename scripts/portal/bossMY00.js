/*
    baolixiong Entrance
*/
importPackage(Packages.net.sf.odinms.server.maps);
importPackage(Packages.net.sf.odinms.net.channel);
importPackage(Packages.net.sf.odinms.tools);


function enter(pi) {
	var nextMap = 551030200;
	var zakumMap = ChannelServer.getInstance(pi.getPlayer().getClient().getChannel()).getMapFactory().getMap(nextMap);

	if (pi.getPlayer().getClient().getChannel() !=2) {
		sendMessage(pi,"[提示]暴力熊和心巴狮王大怪物只在第二频道召唤。");
		return false;
	}
	if (!pi.haveItem(5252004)) {
		sendMessage(pi,"[提示]你没有马来西亚boss地图入场券,请在商城里面去购买！");
		return false;
	}
	if (pi.getPlayer().getBossLog('blxgod')>=2) {
		sendMessage(pi,"[提示]每天拯救梦幻主题公园只能挑战2次.");
		return false;
	}
	
	if (zakumMap.mobCount()>=1 && zakumMap.playerCount()>=1) {
		sendMessage(pi,"[提示]暴力熊和心巴狮王大怪物还没有消灭，战斗正在进行中.");
		return false;
	}
	if (zakumMap.mobCount()>=1){
		var mapobjects = zakumMap.getMapObjects();
		var iter = mapobjects.iterator();
		var o=0;
		while (iter.hasNext()) {
			o = iter.next();
			if (o.getType() == MapleMapObjectType.MONSTER){
				zakumMap.removeMapObject(o);
			}
			if (o.getType() == MapleMapObjectType.ITEM){
				zakumMap.removeMapObject(o);
			}
		}
	}
		zakumMap.resetReactors();
		pi.getPlayer().setBossLog('blxgod');
		pi.gainItem(5252004,-1);	
		pi.warp(551030200,0);
		pi.getPlayer().getClient().getChannelServer().getWorldInterface().broadcastMessage(pi.getPlayer().getName(),MaplePacketCreator.serverNotice(0, "玩家 "+pi.getPlayer().getName() + " 使用了[马来西亚入场卷]进入了暴力熊地图！").getBytes());
		return true;

}
function sendMessage(pi,message) {
	pi.getPlayer().getClient().getSession().write(MaplePacketCreator.serverNotice(5, message));
}

