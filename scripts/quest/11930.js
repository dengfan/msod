/**
 *	[���]�����ǹ������Ǿͻᵷ������
 */

var status = -1;

function start(mode, type, selection) {
    qm.startQuest();;
    qm.dispose();
}

function end(mode, type, selection) {
    qm.forceCompleteQuest();
    qm.dispose();
}
