function enter(pi) {
	if (pi.getAranIntroState("cmd=o")) {
		pi.blockPortal();
		return false;
	}
	pi.playerMessage("����������ͨ��������͹�������ʵ���������");
	pi.updateAranIntroState("cmd=o;normal=o;arr0=o;arr1=o;arr2=o;mo1=o;chain=o;mo2=o;mo3=o;mo4=o");
	pi.blockPortal();
	pi.showWZEffect("Effect/OnUserEff.img/guideEffect/aranTutorial/tutorialGuide3", 1);
	return true;
}