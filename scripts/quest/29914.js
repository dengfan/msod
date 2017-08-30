var status = -1;

function start(mode, type, selection) {
    if (qm.getPlayer().getLevel() >= 200 && ((qm.getPlayer().getJob() / 100) | 0) == 5) {
        qm.startQuest();
    }
    qm.dispose();
}

function end(mode, type, selection) {
    if (qm.canHold(1142013, 1) && !qm.haveItem(1142013, 1) && qm.getPlayer().getLevel() >= 200 && ((qm.getPlayer().getJob() / 100) | 0) == 5) {
        qm.gainItem(1142013, 1);
        qm.startQuest();
        qm.forceCompleteQuest();
    }
    qm.dispose();
}
