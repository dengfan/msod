function enter(pi) {
	if (!pi.isQuestFinished(21012)) {
		pi.playerMessage("������������󣬲��ܽ�����һ����ͼ��");
		return false;
	}
	pi.warp(140090400, 1);
	return true;
}