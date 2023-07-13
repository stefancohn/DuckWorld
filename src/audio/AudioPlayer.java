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

    public Clip song; //array of all songs
    public Clip[] effects; //array of all sound effects 
    
    float volume = 1f;

    public AudioPlayer() {
        loadSong();
        playSong();
    }

    private void loadSong() { //load song into array
        song = getClip("src/res/Sounds/fluffingADuck.wav");
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
		FloatControl gainControl = (FloatControl) song.getControl(FloatControl.Type.MASTER_GAIN);
		float range = gainControl.getMaximum() - gainControl.getMinimum();
		float gain = (range * volume) + gainControl.getMinimum();
		gainControl.setValue(gain);
	}

    public void playSong() {
        updateSongVolume();
        song.setMicrosecondPosition(0);
		song.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stopSong() {
		if (song.isActive())
			song.stop();
	}
}
