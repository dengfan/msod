function enter(pi) {
	if (!pi.isQuestFinished(21010)) {
		pi.playerMessage("������������󣬲��ܽ�����һ����ͼ��");
		return false;
	}
	pi.warp(140090200, 1);
	return true;
}