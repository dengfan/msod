function enter(pi) {
	if (!pi.isQuestActive(21000)) {
		pi.playerMessage("��������˺����ȵ��������ͨ����");
		pi.unblockPortal();
	}
	pi.aranTemporarySkills();
	pi.blockPortal();
	pi.warp(914000200, 1);
	return true;
}