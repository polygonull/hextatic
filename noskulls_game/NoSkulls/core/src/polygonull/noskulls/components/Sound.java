package polygonull.noskulls.components;

import com.badlogic.gdx.Gdx;

public class Sound {

    private com.badlogic.gdx.audio.Sound sound;

    public Sound(String soundPath) {
        sound = Gdx.audio.newSound(Gdx.files.internal(soundPath));
    }

    public void play(boolean soundActive) {
        if(soundActive) {
            sound.play();
        }
    }

}
