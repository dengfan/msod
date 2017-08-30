var status = -1;

function start(mode, type, selection) {
	qm.sendNext("Please go find Jane in El Nath for more information.");
	qm.startQuest();
	qm.forceCompleteQuest();
	qm.dispose();
}

function end(mode, type, selection) {
	qm.startQuest();
	qm.forceCompleteQuest();
	qm.dispose();
}
