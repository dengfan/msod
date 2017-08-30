importPackage(Packages.net.sf.odinms.client);
importPackage(Packages.net.sf.odinms.world);
importPackage(Packages.net.sf.odinms.server.life);
var ca = java.util.Calendar.getInstance();
var mapId=910000000;
var checkstart=0;
var channels=2;

var setupTask;

function init() {
    scheduleNew();
}

function scheduleNew() {
    var cal = java.util.Calendar.getInstance();
    cal.set(java.util.Calendar.SECOND, 5);
    var nextTime = cal.getTimeInMillis();
    while (nextTime <= java.lang.System.currentTimeMillis()) {
        nextTime += 1000 * 50 * 1; // Every 1 minute
    }
    setupTask = em.scheduleAtTimestamp("start", nextTime);
}

function cancelSchedule() {
    setupTask.cancel(true);
}


function start() {
    scheduleNew();
	if(em.getMin()==18){
		startInstance(); //���﹥��
	}
}

function startInstance() {//���﹥��
	var mobCount = net.sf.odinms.net.channel.ChannelServer.getInstance(channels).getMapFactory().getMap(mapId).mobCount();
	if(mobCount==0){
		var map = em.getChannelServer().getMapFactory().getMap(mapId); 
		var Rand = Math.floor(Math.random() * 952)+479;
		var overrideStats = new net.sf.odinms.server.life.MapleMonsterStats(); 			
			var mob = net.sf.odinms.server.life.MapleLifeFactory.getMonster(9600076);
			overrideStats.setHp(1500000000); 
			overrideStats.setExp(20000000); 
			overrideStats.setMp(2100000000); 
			mob.setOverrideStats(overrideStats); 
			mob.setHp(1500000000); 
			if(em.getChannelServer().getChannel()==channels){
				map.spawnMonsterOnGroundBelow(mob, new java.awt.Point(-1192, 34));
			}
		scheduleNew2();
    }
    em.getChannelServer().broadcastPacket(Packages.net.sf.odinms.tools.MaplePacketCreator.serverNotice(0,"[ʮ���] 2���г�ͻȻ����һͷ���ޣ���Ӣ�۾����𱦵�K����!"));
	
}           

function scheduleNew2() {//��������
    var cal = java.util.Calendar.getInstance();
    cal.set(java.util.Calendar.SECOND, 5);
    var nextTime = cal.getTimeInMillis();
    while (nextTime <= java.lang.System.currentTimeMillis()) {
        nextTime += 1000 * 2; // Every 1 minute
    }
    setupTask = em.scheduleAtTimestamp("started", nextTime);
}

function started() {
	checkstart=checkstart+1;
	var mobCount = net.sf.odinms.net.channel.ChannelServer.getInstance(channels).getMapFactory().getMap(mapId).mobCount();
    if(mobCount==0){
		if(checkstart>20){
			em.getChannelServer().broadcastPacket(Packages.net.sf.odinms.tools.MaplePacketCreator.serverNotice(0,"[����] �������ĵ���ʧ��2���г�!"));
			checkstart=0;
		}else{
			scheduleNew2();
			em.getChannelServer().broadcastPacket(Packages.net.sf.odinms.tools.MaplePacketCreator.serverNotice(0,"[ʮ���] 2���г�ͻȻ����һͷ���ޣ���Ӣ�۾����𱦵�K����!"));
			var map = em.getChannelServer().getMapFactory().getMap(mapId); 
		var overrideStats = new net.sf.odinms.server.life.MapleMonsterStats(); 			
			var mob = net.sf.odinms.server.life.MapleLifeFactory.getMonster(9600076);
			overrideStats.setHp(1500000000); 
			overrideStats.setExp(20000000); 
			overrideStats.setMp(200000000); 
			mob.setOverrideStats(overrideStats); 
			mob.setHp(1500000000); 
			if(em.getChannelServer().getChannel()==channels){
				map.spawnMonsterOnGroundBelow(mob, new java.awt.Point(-1192, 34));
			}
		}
		
    }else{
		checkstart=0;
    }
}
