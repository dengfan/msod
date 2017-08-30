importPackage(Packages.net.sf.odinms.server);
importPackage(Packages.net.sf.odinms.net.channel);
importPackage(Packages.net.sf.odinms.tools);


function enter(pi) {
	pi.showInstruction("请点击#e#b[←左边的NPC]#k#n进入扎昆地图", 200, 5);
	return false;
}
function sendMessage(pi,message) {
	pi.getPlayer().getClient().getSession().write(MaplePacketCreator.serverNotice(1, message));
}

