var status = -1;

function start(mode, type, selection) {
    if (qm.getPlayer().getLevel() >= 200 && ((qm.getPlayer().getJob() / 100) | 0) == 1) {
        qm.startQuest();
    }
    qm.dispose();
}

function end(mode, type, selection) {
    if (qm.canHold(1142009, 1) && !qm.haveItem(1142009, 1) && qm.getPlayer().getLevel() >= 200 && ((qm.getPlayer().getJob() / 100) | 0) == 1) {
        qm.gainItem(1142009, 1);
        qm.startQuest();
        qm.forceCompleteQuest();
    }
    qm.dispose();
}
