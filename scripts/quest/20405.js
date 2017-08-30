var status = -1;

function start(mode, type, selection) {
	qm.sendNext("Go back to Erev to report about the situation.");
	qm.startQuest();
	qm.forceCompleteQuest();
	qm.dispose();
}

function end(mode, type, selection) {
	qm.startQuest();
	qm.forceCompleteQuest();
	qm.dispose();
}
