function act(){
	rm.mapMessage(5, "吱吱吱，随着一声振动，暴力熊被召唤了出来！！。");
	rm.changeMusic("BgmMY/KualaLumpur");
	rm.getReactor().getMap().addMapTimer(1 * 60 * 60,910000000);
	rm.spawnMonster(9420541, -515, 640);
}