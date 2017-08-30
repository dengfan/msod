var status = -1;

function start(mode, type, selection) {
    qm.sendNext("Find Phantom Seeds.");
    qm.startQuest();
    qm.dispose();
}
function end(mode, type, selection) {
    qm.dispose();
}
