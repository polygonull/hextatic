function GameAudio(soundName) {
    this.sound = new Audio(soundName);
}

GameAudio.prototype.play = function(soundState, previousSound) {
    if(soundState) {
        if(previousSound !== null) {
            previousSound.sound.pause();
            previousSound.sound.currentTime = 0;
        }
        this.sound.play();
        return this;
    }
    return null;
};

