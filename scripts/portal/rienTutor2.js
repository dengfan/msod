function enter(pi) {
	if (!pi.isQuestFinished(21011)) {
		pi.playerMessage("������������󣬲��ܽ�����һ����ͼ��");
		return false;
	}
	pi.warp(140090300, 1);
	return true;
}