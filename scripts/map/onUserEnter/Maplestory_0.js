//CherryMS LoveMXD
var messages = Array("��ͼ����1���޸�·��Script/map/onUserEnter/Maplestory_0��", "��ͼ����2���޸�·��Script/map/onUserEnter/Maplestory_0��", "��ͼ����2���޸�·��Script/map/onUserEnter/Maplestory_0��");

function start(ms) {
		ms.getPlayer().startMapEffect(messages[Math.floor(Math.random()*messages.length)], 5120025);
	
}
