var MAX_STAGES = 7;
var MAX_MAPS = 25;
var PLAY = "PLAY";
var SELECT = "SELECT";
var GAME = "GAME";

var previousGameState = null;
var gameState = null;

var playSound = new GameAudio("sounds/play.mp3");
var stateChangeSound = new GameAudio("sounds/state_change.mp3");
var hexagonMoveSound = new GameAudio("sounds/hexagon_move.mp3");
var refreshSound = new GameAudio("sounds/refresh.mp3");
var winSound = new GameAudio("sounds/win.mp3");
var lossSound = new GameAudio("sounds/loss.mp3");

var previousSound = null;

var soundState = null;

jQuery(function () {

	if (isLocalStorageAvailable()) {

		setInitialGameState();

		loadStart();

	} else {

		jQuery("#mainbox").hide();
		jQuery("#errorbox-storage").show();

	}

});

function isLocalStorageAvailable() {
	var test = "test";
	try {
		localStorage.setItem(test, test);
		localStorage.removeItem(test);
		return true;
	} catch (e) {
		return false;
	}
}

function setInitialGameState() {

	var sound = localStorage.getItem("sound");
	if (sound === null) {
		sound = "1";
		localStorage.setItem("sound", sound);
	}

	soundState = (sound === "1");

	if (sound !== "1") {
		jQuery(".sound").toggle();
	}

	var currentstage = localStorage.getItem("currentstage");
	if (currentstage === null) {
		currentstage = 0;
		localStorage.setItem("currentstage", currentstage);
	}

	d3.selectAll(".colorful").style("fill", hextaticColors[currentstage]);

}

function setInitialStageState(stageid) {

	if (localStorage.getItem("stage" + stageid) === null) {
		localStorage.setItem("stage" + stageid,
			JSON.stringify([
				3, 2, 2, 2, 2,
				2, 2, 2, 2, 2,
				2, 2, 2, 2, 2,
				2, 2, 2, 2, 2,
				2, 2, 2, 2, 2
			])
		);
	}

}

function loadGame(stageid, mapid) {

	previousGameState = gameState;
	gameState = GAME;

	var timeoutRefreshLoadGameEvent;

	var stage = hexstages["stage" + stageid];
	var map = stage["map" + mapid];

	var sizex = jQuery(window).width();
	var sizey = jQuery(window).height();
	sizey = sizey - 150;
	var size = sizex > sizey ? sizey : sizex;
	if (size > 450) {
		size = 450;
	}

	var hexGrid = new HexGrid(map, size);

	var radius = hexGrid.hexRadius * 8 / 10;

	var hexbin = d3.hexbin().radius(hexGrid.hexRadius);

	d3.select("#hexgrid").remove();

	//Create SVG element
	var svg = d3.select("#hexgridbox").append("svg")
		.attr("width", hexGrid.width)
		.attr("height", hexGrid.height)
		.attr("id", "hexgrid")
		.append("g")
		.attr("transform", "translate(" +
			(hexGrid.hexWidth) +
			"," + (hexGrid.hexRadius) +
			")");

	// fixes left margin of the hexgrid
	jQuery("#hexgrid").css({ marginLeft: "-=" + (hexGrid.hexWidth / 2) + "px" });

	//Start drawing the hexagons
	svg.append("g")
		.selectAll(".hexagon")
		.data(hexbin(hexGrid.points))
		.enter().append("path")
		.property("hex", function (d, i) {
			return hexGrid.hexes[hexGrid.itox(i)][hexGrid.itoy(i)];
		})
		.attr("class", function (d) {
			return "hexagon hexagon" + this.hex.val;
		})
		.attr("id", function (d) {
			return this.hex.id();
		})
		.attr("d", function (d) {
			return !hexGrid.isHiddenHex(this.hex) ? "m" + d.x + "," + d.y + hexbin.hexagon(radius) : null;
		})
		.attr("stroke", function (d) {
			return hexBorderColors(this.hex.val, stageid);
		})
		.attr("stroke-width", function (d) {
			return this.hex.val === 9 ? "2px" : "1px";
		})
		.style("fill", function () {
			return hexColors(this.hex.val, stageid);
		})
		.on("click", function (d) {
			if (hexGrid.isBlockHex(this.hex) || hexGrid.isRedHex(this.hex)) {
				return;
			} else {
				this.hex.val = 2;
				jQuery(this).removeClass("hexagon9 hexagon1").addClass("hexagon2");
				d3.select(this).attr("stroke", hexBorderColors(this.hex.val, stageid)).style("fill", gameColors("blocked", stageid));
				d3.select(this).attr("stroke-width", "1px");
				var nexthex = hexGrid.moveRedHex();
				if (nexthex === null) {
					setTimeout(function () {
						previousSound = winSound.play(soundState, previousSound);
					}, 200);
					var stage = JSON.parse(localStorage.getItem("stage" + stageid));
					stage[mapid] = 1;
					var lastMap = false;
					if (mapid == MAX_MAPS - 1) {
						lastMap = true;
						localStorage.setItem("stage" + stageid, JSON.stringify(stage));
					} else if (stage[++mapid] === 2) {
						stage[mapid] = 3;
						localStorage.setItem("stage" + stageid, JSON.stringify(stage));
					}
					d3.selectAll(".hexagon").on("click", null);
					jQuery(".hexagon2").addClass("pulsating");
					jQuery("#gamebox .refresh").hide();
					if (!lastMap) {
						jQuery("#gamebox .forward").fadeIn(200);
					}
					timeoutRefreshLoadGameEvent = setTimeout(function () {
						previousSound = stateChangeSound.play(soundState, previousSound);
						if (mapid == MAX_MAPS - 1) {
							jQuery("#gamebox").hide();
							jQuery("#game-back").hide();
							loadStageSelect();
						} else {
							loadGame(stageid, mapid);
						}
					}, 5000);
				} else {
					hexGrid.redhex.val = 1;
					d3.select(".hexagon#" + hexGrid.redhex.id()).style("fill", gameColors("empty", stageid));
					jQuery(".hexagon#" + hexGrid.redhex.id()).removeClass("hexagon3").addClass("hexagon1");

					hexGrid.redhex = nexthex;
					d3.select(".hexagon#" + hexGrid.redhex.id()).style("fill", gameColors("hex", stageid));
					jQuery(".hexagon#" + hexGrid.redhex.id()).removeClass("hexagon9 hexagon1").addClass("hexagon3");
					if (hexGrid.isEndHex(hexGrid.redhex)) {
						d3.selectAll(".hexagon").on("click", null);
						setTimeout(function () {
							previousSound = lossSound.play(soundState, previousSound);
						}, 200);
						jQuery(".hexagon#" + hexGrid.redhex.id()).addClass("pulsating");
						timeoutRefreshLoadGameEvent = setTimeout(function () {
							jQuery("#gamebox .refresh").click();
						}, 5000);
					} else {
						hexGrid.redhex.val = 3;
					}
				}
				previousSound = hexagonMoveSound.play(soundState, previousSound);
			}
		});

	jQuery("#map-number").html(hexstagesCodenames[stageid] + "-" + mapid).css("color", hextaticColors[stageid]);
	jQuery(".refresh").css("fill", hextaticColors[stageid]);
	jQuery(".forward").css("fill", hextaticColors[stageid]);
	jQuery(".back").css("fill", hextaticColors[stageid]);
	jQuery(".sound").css("fill", hextaticColors[stageid]);

	jQuery("#gamebox .refresh").show();
	jQuery("#gamebox .forward").hide();

	jQuery("#game-back").off("click").on("click", function () {
		clearTimeout(timeoutRefreshLoadGameEvent);
		previousSound = playSound.play(soundState, previousSound);
		jQuery("#gamebox").hide();
		jQuery("#game-back").hide();
		loadStageSelect();
	});

	jQuery("#gamebox .refresh").off("click").on("click", function () {
		clearTimeout(timeoutRefreshLoadGameEvent);
		previousSound = refreshSound.play(soundState, previousSound);
		jQuery(this).off("click");
		d3.select(".hexagon").on("click", null);
		d3.select("#gamebox .refresh").transition().duration(400).attrTween("transform", function () {
			return d3.interpolateString("rotate(0)", "rotate(360)");
		});
		timeoutRefreshLoadGameEvent = setTimeout(function () {
			loadGame(stageid, mapid);
		}, 400);
	});

	jQuery("#gamebox .forward").off("click").on("click", function () {
		clearTimeout(timeoutRefreshLoadGameEvent);
		previousSound = stateChangeSound.play(soundState, previousSound);
		loadGame(stageid, mapid);
	});

	jQuery("#gamebox").fadeIn(500);
	jQuery("#game-back").show();

}

function loadStageSelect() {

	previousGameState = gameState;
	gameState = SELECT;

	var stageid = parseInt(localStorage.getItem("currentstage"));
	setInitialStageState(stageid);
	var stage = JSON.parse(localStorage.getItem("stage" + stageid));

	jQuery(".rectbox").each(function () {
		jQuery(this).removeClass();
		if (stage[this.id] !== 2) {
			if (stage[this.id] === 1) {
				jQuery(this).addClass("rectbox done");
			} else if (stage[this.id] === 3) {
				jQuery(this).addClass("rectbox tobe-done");
			}
			jQuery(this).off("click").on("click", function () {
				previousSound = playSound.play(soundState, previousSound);
				jQuery("#selectbox").hide();
				jQuery("#select-back").hide();
				loadGame(stageid, this.id);
			});
		} else {
			jQuery(this).addClass("rectbox not-done");
		}
	});

	if (stageid === 0) {
		jQuery("#changebox-first").show();
	} else if (stageid === MAX_STAGES - 1) {
		jQuery("#changebox-last").show();
	} else {
		jQuery("#changebox-in-between").show();
	}

	d3.select("#stage-number").html(hexstagesCodenames[stageid]).transition().duration(500).style("color", hextaticColors[stageid]);
	d3.selectAll(".done").transition().duration(500).style("background-color", gameColors("done", stageid));
	d3.selectAll(".tobe-done").transition().duration(500).style("background-color", hextaticColors[stageid]);
	d3.selectAll(".not-done").transition().duration(500).style("background-color", gameColors("blocked", stageid));
	d3.selectAll(".forward").transition().duration(500).style("fill", hextaticColors[stageid]);
	d3.selectAll(".back").transition().duration(500).style("fill", hextaticColors[stageid]);
	d3.selectAll(".colorful").transition().duration(500).style("fill", hextaticColors[stageid]);

	jQuery("#select-back").off("click").on("click", function () {
		previousSound = playSound.play(soundState, previousSound);
		jQuery("#selectbox").hide();
		jQuery(".changebox").hide();
		jQuery("#select-back").hide();
		loadStart();
	});

	jQuery(".changebox .forward").off("click").on("click", function () {
		previousSound = stateChangeSound.play(soundState, previousSound);
		localStorage.setItem("currentstage", stageid + 1);
		jQuery(".changebox").hide();
		loadStageSelect();
	});

	jQuery(".changebox .back").off("click").on("click", function () {
		previousSound = stateChangeSound.play(soundState, previousSound);
		localStorage.setItem("currentstage", stageid - 1);
		jQuery(".changebox").hide();
		loadStageSelect();
	});

	jQuery("#selectbox").fadeIn(500);
	if (previousGameState === GAME) {
		jQuery("#select-back").show();
	} else {
		jQuery("#select-back").fadeIn(500);
	}

}

function loadPrivacy() {

	d3.selectAll(".colorful").transition().duration(0).style("fill", gameColors("blocked", 0));

	jQuery("#privacy-back").off("click").on("click", function () {
		previousSound = playSound.play(soundState, previousSound);
		hextaticAnimationStop = true;
		jQuery("#privacybox").hide();
		jQuery("#privacy-back").hide();
		loadStart();
	});

	jQuery("#privacybox").show();
	jQuery("#privacy-back").show();

}

function loadStart() {

	previousGameState = gameState;
	gameState = PLAY;

	var hextaticAnimationStop = false;
	function hextaticAnimation(i) {
		if (hextaticAnimationStop === true) {
			return;
		}
		if (i === MAX_STAGES) {
			i = 0;
		}
		d3.selectAll(".colorful")
			.transition()
			.duration(1000)
			.style("fill", hextaticColors[i])
			.on("end", function () {
				hextaticAnimation(i + 1);
			});
	}
	hextaticAnimation(parseInt(localStorage.getItem("currentstage")));

	jQuery("#playbutton-inner").off("click").on("click", function () {
		previousSound = playSound.play(soundState, previousSound);
		hextaticAnimationStop = true;
		jQuery("#playbox").hide();
		jQuery(".privacytext-link").hide();
		loadStageSelect();
	});

	jQuery(".sound").off("click").on("click", function () {
		if (soundState) {
			localStorage.setItem("sound", "0");
			soundState = false;
		} else {
			previousSound = stateChangeSound.play(!soundState, previousSound);
			localStorage.setItem("sound", "1");
			soundState = true;
		}
		jQuery(".sound").toggle();
	});

	jQuery(".privacytext-link").off("click").on("click", function () {
		previousSound = stateChangeSound.play(soundState, previousSound);
		hextaticAnimationStop = true;
		jQuery("#playbox").hide();
		jQuery(".privacytext-link").hide();
		loadPrivacy();
	});

	jQuery("#playbox").fadeIn(500);
	jQuery(".privacytext-link").fadeIn(500);

}

