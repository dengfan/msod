function enter(pi) {
	pi.spawnTutorialSummon();
	pi.tutorialSpeechBubble("��ӭ������֮��ð�յ����磡���Ǹ��������Ŀ⣡�����Ϊ10����������ʿ֮ǰ�������ʲô��֪���ġ����Թ������ҡ��������ʲô���ʣ���˫���ң�");
	pi.updateCygnusIntroState("helper=clear");
	pi.blockPortal();
	return true;
}