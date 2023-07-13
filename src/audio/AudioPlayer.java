package audio;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {
    public Clip[] songs; //array of all songs
    public Clip[] effects; //array of all sound effects 

    //song selection
    public static int MENU_SONG = 0;
	public static int PLAYING_SONG = 1;
	public static int HIGHSCORE_SONG = 2;

    //effects selection
	public static int ATTACK = 0;
	public static int JUMP = 1;
	public static int DEATH = 2;

    int currentSongID; //so we can use the various methods
    
    float volume = 0.8f;

    public AudioPlayer() {
        loadSong(); //load da songs
        loadEffects(); //load effects
        playSong(MENU_SONG);
    }

    private void loadSong() { //load song into clip array
        String[] names = { "src/res/Sounds/introMusic.wav", "src/res/Sounds/fluffingADuck.wav", "src/res/Sounds/highscoreMusic.wav"};
		songs = new Clip[names.length];

		for (int i = 0; i < songs.length; i++) {
			songs[i] = getClip(names[i]); //here is where we put them into array
        }
    }

    private void loadEffects() { //load effects into clip array 
        String[] names = { "src/res/Sounds/quack.wav", "src/res/Sounds/jumpEffect.wav", "src/res/Sounds/duckDeath.wav"};
        effects = new Clip[names.length];

        for (int i = 0; i < effects.length; i++) {
            effects[i] = getClip(names[i]);
        }

        updateEffectsVolume();
    }
    
    //put an audio in a clip 
    public Clip getClip(String name) { 
        try { 
            File file = new File(name); 
            AudioInputStream audio = AudioSystem.getAudioInputStream(file);
            Clip c = AudioSystem.getClip();
            c.open(audio);
            return c;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
            return null;
        }
	}

    private void updateSongVolume() {
        FloatControl gainControl = (FloatControl) songs[currentSongID].getControl(FloatControl.Type.MASTER_GAIN); //retrieve the "volume" control of current clip playing
		float range = gainControl.getMaximum() - gainControl.getMinimum(); //retreive the amount of possible volume (min will be negative, making the range higher than the max)
		float gain = (range * volume) + gainControl.getMinimum(); //this is where volume comes in play. a value of 1 is maxed volume because of the negative minimum 
		gainControl.setValue(gain); //set the floatcontrol to our new value 
	}
    private void updateEffectsVolume() {
		for (Clip c : effects) {
			FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
			float range = gainControl.getMaximum() - gainControl.getMinimum();
			float gain = (range * volume) + gainControl.getMinimum();
			gainControl.setValue(gain);
		}
    }

    public void playSong(int song) {
        stopSong(); //stop song if there is one playing to prevent over lap

        currentSongID = song; //update song ID
        updateSongVolume(); //make sure the volume is active
        songs[currentSongID].setMicrosecondPosition(10); //start at beginning 
		songs[currentSongID].loop(Clip.LOOP_CONTINUOUSLY); //create a  loop
    }
    public void playEffect(int effect) { //since it's an effect, don't have to take into account other audio
        stopEffect(effect);
		effects[effect].setMicrosecondPosition(0);
		effects[effect].start();
	}

    public void stopSong() { //stop the song
		if (songs[currentSongID].isActive()) {
			songs[currentSongID].stop();
        }
	}
    public void stopEffect(int effect) { //stop effect
		if (effects[effect].isActive()) {
			effects[effect].stop();
        }
	}

    public void setVolume(float volume) { //change volume variable, update volumes for sound effects
		this.volume = volume;
		updateSongVolume();
		updateEffectsVolume();
	}
}
