importPackage(Packages.net.sf.odinms.server);
importPackage(Packages.net.sf.odinms.net.channel);
importPackage(Packages.net.sf.odinms.tools);


function enter(pi) {
	pi.showInstruction("����#e#b[����ߵ�NPC]#k#n����������ͼ", 200, 5);
	return false;
}
function sendMessage(pi,message) {
	pi.getPlayer().getClient().getSession().write(MaplePacketCreator.serverNotice(1, message));
}

