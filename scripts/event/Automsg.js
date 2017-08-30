var setupTask;

function init() {
	scheduleNew();
}

function scheduleNew() {
	setupTask = em.schedule("start", 1000 * 60 * 3);
}

function cancelSchedule() {
	setupTask.cancel(true);
}

function start() {
	scheduleNew();
	var msgArr = ["如果有不足的地方请提出来，或者有更好的建议也提出来，一起完美我们的枫叶世界！唯一QQ群：55580907",
	"枫叶可兑换抵用券，黄金枫叶可兑换点券。",
	"点击右下区域的拍卖按钮，可打开多功能菜单，快去试试吧！",
	"公平公正的环境是游戏的根基，切勿使用任何作弊工具，珍惜你的帐号！"];
	em.getChannelServer().broadcastPacket(Packages.net.sf.odinms.tools.MaplePacketCreator.serverNotice(6, msgArr[Math.floor(Math.random() * msgArr.length)]));
}
