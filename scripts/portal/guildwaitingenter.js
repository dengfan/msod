//���崫��
//CherryMS LoveMXD

function enter(pi) {
        if (pi.getPlayer().getEventInstance() == null) {
                pi.warp(101030104);
                return true;
        }
        else {
                if (pi.getPlayer().getEventInstance().getProperty("canEnter").equals("false")) {
                        pi.warp(990000100);
                        return true;
                }
                else { //cannot proceed while allies can still enter
                        pi.playerMessage("������Ż�û�򿪡�");
                        return false;
                }
        }
}
