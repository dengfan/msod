//CherryMS LoveMXD
var messages = Array("地图公告1【欢迎来到天子冒险岛！】", "地图公告2【欢迎来到天子冒险岛！】", "地图公告3【欢迎来到天子冒险岛！】");

function start(ms) {
		ms.getPlayer().startMapEffect(messages[Math.floor(Math.random()*messages.length)], 5120025);
	
}
