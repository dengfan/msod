var status = -1;
//TEMPORARY QUEST NOW SKIPPING
//this quest is AFTER SHEDDING 1
function start(mode, type, selection) {
	qm.gainItem(4032502,1);
	qm.startQuest();
	qm.dispose();
}

function end(mode, type, selection) {
	qm.forceCompleteQuest();
	qm.dispose();
}
