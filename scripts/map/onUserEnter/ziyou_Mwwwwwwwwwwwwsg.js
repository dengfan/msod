//CherryMS LoveMXD
var messages = Array("��ͼ����1����ӭ��������ð�յ�����", "��ͼ����2����ӭ��������ð�յ�����", "��ͼ����3����ӭ��������ð�յ�����");

function start(ms) {
		ms.getPlayer().startMapEffect(messages[Math.floor(Math.random()*messages.length)], 5120025);
	
}
