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
		sendMessage(pi,"[��ʾ]�����ܺ��İ�ʨ�������ֻ�ڵڶ�Ƶ���ٻ���");
		return false;
	}
	if (!pi.haveItem(5252004)) {
		sendMessage(pi,"[��ʾ]��û����������boss��ͼ�볡ȯ,�����̳�����ȥ����");
		return false;
	}
	if (pi.getPlayer().getBossLog('blxgod')>=2) {
		sendMessage(pi,"[��ʾ]ÿ�������λ����⹫԰ֻ����ս2��.");
		return false;
	}
	
	if (zakumMap.mobCount()>=1 && zakumMap.playerCount()>=1) {
		sendMessage(pi,"[��ʾ]�����ܺ��İ�ʨ������ﻹû������ս�����ڽ�����.");
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
		pi.getPlayer().getClient().getChannelServer().getWorldInterface().broadcastMessage(pi.getPlayer().getName(),MaplePacketCreator.serverNotice(0, "��� "+pi.getPlayer().getName() + " ʹ����[���������볡��]�����˱����ܵ�ͼ��").getBytes());
		return true;

}
function sendMessage(pi,message) {
	pi.getPlayer().getClient().getSession().write(MaplePacketCreator.serverNotice(5, message));
}

