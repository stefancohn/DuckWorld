package audio;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {
    
    private Clip getClip(String name) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File(name);
		AudioInputStream audio = AudioSystem.getAudioInputStream(file);
		Clip c = AudioSystem.getClip();
		c.open(audio);
		return c;
	}
}
