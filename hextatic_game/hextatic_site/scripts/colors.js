var hextaticColors = [
	"#ab2828", // red
	"#ff6e22", // orange
	"#ffd000", // yellow
	"#00de56", // green
	"#00a6ff", // blue
	"#6a00ff", // indigo
	"#f401ed", // violet
];

function gameColors(color, i) {

	var colors = {
		hex: hextaticColors[i],
		blocked: "#000000",
		empty: "#ffffff",
		done: "#e8e8e8"
	}
	
	return colors[color];
	
}

function hexColors(value, i) {

	if(value === 2) return gameColors("blocked", i);
	else if(value === 3) return gameColors("hex", i);
	else return gameColors("empty", i);

}

function hexBorderColors(value, i) {

	if(value === 9) return gameColors("hex", i);
	else return gameColors("blocked", i);

}


