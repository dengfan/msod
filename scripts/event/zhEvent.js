//��ʱ�ٻ�����
//CherryMS LoveMXD


importPackage(Packages.net.sf.odinms.client);

function init() {
	scheduleNew();
}

function scheduleNew() {
	em.schedule("start", 1*30*1000); //ÿ5���ӳ���һ��
}

function start() {
	scheduleNew();
	var hotSand = em.getChannelServer().getMapFactory().getMap(910000000);
	var kingClang = net.sf.odinms.server.life.MapleLifeFactory.getMonster(9600034);
	//var current1 = em.getChannelServer().getMapFactory().getMap(910000000).spawnMob(9600034);
	//var current2 = em.getChannelServer().getMapFactory().getMap(910000000).spawnMob(9600034);
	//if (current1 == 0 && current2 == 0) {
		var random = Math.floor(Math.random()*7);
		var posX;
		var posY;
		switch (random) { //һ�������꣬���ѡ��һ��������
			case 1:
				posX = 603;
				posY = 4;
				break;
			case 2:
				posX = 1000;
				posY = 4;
				break;
			case 3:
				posX = 1000;
				posY = 4;
				break;
			case 4:
				posX = 1000;
				posY = 173;
				break;
			case 5:
				posX = 1000;
				posY = 4;
				break;
			default:
				posX = 1000;
				posY = 4;
		}
		hotSand.spawnMonsterOnGroundBelow(kingClang, new java.awt.Point(posX, posY));
		hotSand.broadcastMessage(Packages.net.sf.odinms.tools.MaplePacketCreator.serverNotice(6, "[��������] �޾�з�����ɳ̲�ϳ����ˡ�"));
	//}
	//scheduleNew();
}
