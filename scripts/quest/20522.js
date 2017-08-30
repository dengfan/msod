var status = -1;

function start(mode, type, selection) {
	qm.sendNext("謝謝你。");
	qm.startQuest();
	qm.forceCompleteQuest();
	qm.dispose();
}

function end(mode, type, selection) {
	qm.startQuest();
	qm.forceCompleteQuest();
	qm.dispose();
}
