function start() {
    status = -1;

    action(1, 0, 0);
}
function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    }
    else {
        if (status >= 0 && mode == 0) {

            cm.sendOk("��л��Ĺ��٣�");
            cm.dispose();
            return;
        }
        if (mode == 1) {
            status++;
        }
        else {
            status--;
        }
        if (status == 0) {
            var tex2 = "";
            var text = "";
            for (i = 0; i < 10; i++) {
                text += "";
            }
			//��ʾ��ƷIDͼƬ�õĴ�����  #v����д��ID#
            text += "#e#d��ȷ��Ҫ����500��ð�ձ�����#v3994091#��.#l\r\n\r\n"//3
            text += "#L1##r�ǵ���Ҫ����#l\r\n\r\n"//3
            cm.sendSimple(text);
        } else if (selection == 1) {
			if(cm.getMeso() > 5000000){
				cm.gainItem(3994091, 1);
				cm.gainMeso(-5000000);
            cm.sendOk("����ɹ���");
			cm.worldMessage(6,"��ң�["+cm.getName()+"]����ĩ���й�����[̩������Ʒ]���ڴ����հɣ�");
            cm.dispose();
			}else{
            cm.sendOk("���Ľ�Ҳ��㣡");
            cm.dispose();
			}
		}
    }
}

