//���ݵĴ�����
//�޸�

function enter(pi) {
	if(pi.getPlayer().getMapId() == 222020100) {
		if(pi.getPlayer().getClient().getChannelServer().getEventSM().getEventManager("elevator").getProperty("isDown").equals("true")) {
			pi.warp(222020110, "sp");
			return true;
		} else {
			pi.playerMessage("���ڻ����ܽ�ȥ");
			return false;
		}
	} else if(pi.getPlayer().getMapId() == 222020200) {
		if(pi.getPlayer().getClient().getChannelServer().getEventSM().getEventManager("elevator").getProperty("isUp").equals("true")) {
			pi.warp(222020210, "sp");
			return true;
		} else {
			pi.playerMessage("���ڻ����ܽ�ȥ");
			return false;
		}
	} else {
		pi.playerMessage("���ִ����뵽��̳�͹���Ա��ϵ");
		return false;
	}
}
