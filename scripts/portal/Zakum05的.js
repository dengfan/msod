importPackage(Packages.net.sf.odinms.server.maps);
importPackage(Packages.net.sf.odinms.net.channel);
importPackage(Packages.net.sf.odinms.tools);

/*
    ���Žű�
    CherryMS LoveMXD
    ��ͬ���ڽ�ֹת��
*/

function enter(pi) {
	var nextMap = 211042400;
//	if (pi.getQuestStatus(100200) != net.sf.odinms.client.MapleQuestStatus.Status.COMPLETED) {
//		// do nothing; send message to player
//		pi.getPlayer().getClient().getSession().write(MaplePacketCreator.serverNotice(6, "�㻹û��׼�������ǿ��Ĵ����!"));
//		return false;
//	}
//	else
        if (!pi.haveItem(4001017)) {
		// ���û�л�����۾���
		pi.getPlayer().getClient().getSession().write(MaplePacketCreator.serverNotice(6,"�������û�л������,�����㲻�ܽ��롣"));
		return false;
	}
	else{
		pi.warp(nextMap,"west00");
		return true;
	}
}
