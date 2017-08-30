var status = -1;

function start(mode, type, selection) {
    qm.sendNext("Find Elder Ashes.");
    qm.startQuest();
    qm.dispose();
}
function end(mode, type, selection) {
    qm.dispose();
}
