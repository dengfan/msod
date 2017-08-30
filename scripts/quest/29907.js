var status = -1;

function start(mode, type, selection) {
    if (qm.getPlayer().getJob() > 1000 && qm.getPlayer().getJob() % 100 > 0 && qm.getPlayer().getJob() < 2000) {
        qm.startQuest();
    }
    qm.dispose();
}

function end(mode, type, selection) {
    if (qm.canHold(1142067, 1) && !qm.haveItem(1142067, 1) && qm.getPlayer().getJob() > 1000 && qm.getPlayer().getJob() % 100 > 0 && qm.getPlayer().getJob() < 2000) {
        qm.gainItem(1142067, 1);
        qm.startQuest();
        qm.forceCompleteQuest();
    }
    qm.dispose();
}
