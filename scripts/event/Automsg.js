var setupTask;

function init() {
	scheduleNew();
}

function scheduleNew() {
	setupTask = em.schedule("start", 1000 * 60 * 3);
}

function cancelSchedule() {
	setupTask.cancel(true);
}

function start() {
	scheduleNew();
	var msgArr = ["����в���ĵط���������������и��õĽ���Ҳ�������һ���������ǵķ�Ҷ���磡ΨһQQȺ��55580907",
	"��Ҷ�ɶһ�����ȯ���ƽ��Ҷ�ɶһ���ȯ��",
	"������������������ť���ɴ򿪶๦�ܲ˵�����ȥ���԰ɣ�",
	"��ƽ�����Ļ�������Ϸ�ĸ���������ʹ���κ����׹��ߣ���ϧ����ʺţ�"];
	em.getChannelServer().broadcastPacket(Packages.net.sf.odinms.tools.MaplePacketCreator.serverNotice(6, msgArr[Math.floor(Math.random() * msgArr.length)]));
}
