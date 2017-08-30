var status = -1;

//member of resistance.

function end(mode, type, selection) {
    qm.forceStartQuest(23103);
    qm.forceStartQuest(23128, "1");
    qm.startQuest();
    qm.forceCompleteQuest();
    qm.dispose();
}
