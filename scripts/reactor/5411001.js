/* 
    �����ܽű� 
    ������ð�� о�������޸�    
    ��ͬ���ڽ�ֹת�� 

*/  
importPackage(Packages.net.sf.cherry.server.maps); 

function act(){
if (rm.getPlayer().getMap().getMonsterById(9420520) == null  && rm.getPlayer().getMap().getMonsterById(9420521) == null && rm.getPlayer().getMap().getMonsterById(9420522) == null ) {
	rm.getReactor().getMap().addMapTimer(2 * 60 * 60,541020700);   
        rm.mapMessage("���������ѱ��ٻ�");
        rm.spawnMonster(9420520, -178, -212);
        rm.createMapMonitor(1,540000000,"sp");
	}else{
	 rm.mapMessage("������������ս��.....�����ظ��ٻ�!!");
	}
}
