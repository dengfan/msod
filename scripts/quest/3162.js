var status = -1;

function start(mode, type, selection) {
    qm.sendNext("Royal Guard Ani comes out every hour, but right now he's not feeling like fighting.");
    qm.startQuest();
    qm.dispose();
}
function end(mode, type, selection) {
    qm.dispose();
}
