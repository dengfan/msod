//CherryMS LoveMXD
var messages = Array("��ͼ����1���޸�·��Script/map/onUserEnter/Cabin_Orbis.js��", "��ͼ����2���޸�·��Script/map/onUserEnter/Cabin_Orbis.js��", "��ͼ����3���޸�·��Script/map/onUserEnter/Cabin_Orbis.js��");

function start(ms) {
		ms.getPlayer().startMapEffect(messages[Math.floor(Math.random()*messages.length)], 5120025);
	
}
