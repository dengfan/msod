/*
	����: ���ܶ��ٴζ�����ȡ��
	����: �Ҵ���������ʻԱ����λ��2102����ҵ����#p9120033#����������ַ�#o9400295#��������˵������ʹ�õ��������Ŀǰ������#o9400295#����Χ���������Ϳ���ǿ��#o9400295#ûʱ���ˣ����������ش�#o9400295#�Ļ�����
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        qm.sendNext("...What is it? Ah, I see that he's coming really close!");
        qm.dispose();
        return;
    }
    if (status == 0) {
        qm.sendAcceptDecline("Watch out, because he seems... much more powerful than before. Do not underestimate him!");
    } else if (status == 1) {
        qm.startQuest();
        qm.dispose();
    }
}

function end(mode, type, selection) {
    qm.forceCompleteQuest();
    qm.dispose();
}
