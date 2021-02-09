/* 
 * Offset coordinates system used is even-r
 * as shown in http://www.redblobgames.com/grids/hexagons/
 */

var offsetDirections = [
	[ new Hex(0, 1), new Hex(-1, +1), new Hex(-1, 0),
		new Hex(0, -1), new Hex(1, 0), new Hex(+1, +1) ],
	[ new Hex(0, 1), new Hex(-1, 0), new Hex(-1, -1),
		new Hex(0, -1), new Hex(1, -1), new Hex(1, 0) ]
];

function Hex(x, y, val) {

	this.x = x;
	this.y = y;
	this.val = val;

}

Hex.prototype.id = function() {

	return "x"+this.x+"y"+this.y;

};

function HexGrid(map, size) {

	// Almost always the same
	this.rows = map.length;
	this.columns = map.length;

	//The maximum radius the hexagons can have to still fit the screen
	this.hexRadius = size / ((this.columns + 0.5) * Math.sqrt(3));

	this.hexWidth = this.hexRadius * Math.sqrt(3);
	this.hexHeight = this.hexRadius * 2;

	this.width = this.hexWidth * (this.columns + 0.5);
	this.height = this.hexHeight * this.rows - Math.floor(this.rows / 2) * this.hexRadius;

	// Hexes array
	this.hexes = [];

	// Red hex
	this.redhex = null;

	// End hexes array
	this.endhexes = [];

	// Points array
	this.points = [];

	var i = 0;
	var j = 0;
	var	a = 0;
	var b = 0;
	
	for (i = 0; i < this.rows; i++) {
		b = (3 * i) * this.hexRadius / 2;
		this.hexes[i] = [];
		for (j = 0; j < this.columns; j++) {
			a = this.hexWidth * (j - 0.5 * (i % 2));
			// Set the hexes
			this.hexes[i][j] = new Hex(i, j, map[i][j]); 
			// Set the red Hex
			if(map[i][j] === 3) this.redhex = this.hexes[i][j];
			// Set the end hexes
			if(map[i][j] === 9)	this.endhexes.push(this.hexes[i][j]);
			// Set the points
			this.points.push([a, b]);
		}
	}

}

HexGrid.prototype.itox = function(i) {

	return Math.floor(i / this.rows);

};

HexGrid.prototype.itoy = function(i) {

	return i % this.columns;

};

HexGrid.prototype.neighbour = function(hex, direction) {

	var parity = hex.x & 1;
	var dir = offsetDirections[parity][direction];

	return this.hexes[hex.x + dir.x][hex.y + dir.y];

};

HexGrid.prototype.isEndHex = function(hex) {

	return hex.val === 9;

};

HexGrid.prototype.isRedHex = function(hex) {

	return hex.val === 3;

};

HexGrid.prototype.isBlockHex = function(hex) {

	return hex.val === 2;

};

HexGrid.prototype.isEmptyHex = function(hex) {

	return hex.val === 1;

};

HexGrid.prototype.isHiddenHex = function(hex) {

	return hex.val === 0;

};

HexGrid.prototype.guideContainsEndHex = function(guide) {

	var i = 0;

	for(i = 0; i < this.endhexes.length; i++) {
		if(guide.has(this.endhexes[i])) return true;
	}

	return false;

};

HexGrid.prototype.getPaths = function(guide) {

	var i = 0;
	var path;
	var paths = [];

	for (i = 0; i < this.endhexes.length; i++) {
		path = this.pathFinder(guide, this.endhexes[i]);
		if(path !== null) paths.push(path);
	}

	return paths;

};

HexGrid.prototype.getBestPaths = function(paths) {

	var i = 0;
	var bestPaths = [];
	var shortest = paths[i].length;

	for(i = 0; i < paths.length; i++) {
		if(paths[i].length === shortest) {
			bestPaths.push(paths[i]);
		} else if(paths[i].length < shortest) {
			bestPaths = [];
			bestPaths.push(paths[i]);
			shortest = paths[i].length;
		}
	}

	return bestPaths;

};

HexGrid.prototype.getBestNeighbour = function(bestPaths) {

	var i = 0;
	var l = 0;
	var neighbourValues = [];
	var neighbourhex = null;
	var maxValue = 0;
	var nexthex = null;
	var path = null;
	
	for(i = 0; i < 6;i++) {
		neighbourValues[i] = 0;
		neighbourhex = this.neighbour(this.redhex, i);
		for(l = 0; l < bestPaths.length; l++) {
			path = bestPaths[l];
			if(neighbourhex === path[path.length - 1]) {
				neighbourValues[i]++;
			}
		}
	}

	for(i = 0; i < 6; i++) {
		if(maxValue < neighbourValues[i]) {
			maxValue = neighbourValues[i];
			nexthex = this.neighbour(this.redhex, i);
		}
	}

	return nexthex;

};

HexGrid.prototype.moveRedHex = function() {
	
	var guide = this.bfs();
	
	if(!this.guideContainsEndHex(guide)) {
		return null;
	}

	var paths = this.getPaths(guide);
	
	var bestPaths = this.getBestPaths(paths);
	
	return this.getBestNeighbour(bestPaths);
	
};

HexGrid.prototype.bfs = function() {
	
	var frontier = new Queue();
	frontier.enqueue(this.redhex);
	var cameFrom = new WeakMap();
	cameFrom.set(this.redhex, null);
	
	var i = 0;
	var current = null;
	var neighbour = null;
	var N = Math.floor(Math.random() * 100) + 1;

	while(frontier.size() !== 0) {
		current = frontier.dequeue();
		if(!this.isEndHex(current)) {
			for(i = N;i < N + 6;i++) {
				neighbour = this.neighbour(current, i % 6);
				if(!cameFrom.has(neighbour) && !this.isBlockHex(neighbour)) {
					frontier.enqueue(neighbour);
					cameFrom.set(neighbour, current);
				}
			}
		}
	}

	return cameFrom;
	
};

HexGrid.prototype.pathFinder = function(guide, endhex) {

	var start = this.redhex;
	var current = endhex;
	var path = [];

	while(current !== start) {
		path.push(current);
		current = guide.get(current);
		if(current === undefined) return null;
	}
	
	return path;
	
};