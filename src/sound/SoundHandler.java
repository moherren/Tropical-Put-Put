package sound;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundHandler {
	
	
	/**
	 * "Wellness Center" tutorial music
	 */
	public static String SONG_THREE = "sounds/WellnessCenter.wav";

	/**
	 * "Loading" menu music
	 */
	public static String SONG_ONE = "sounds/loading.wav";
	
	/**
	 * "BeautyPlus" game music
	 */
	public static String SONG_TWO = "sounds/BeautyPlus.wav";
	
	/**
	 * the sound of the ball getting putted
	 */
	public static String PuttingSound = "sounds/BeautyPlus.wav";
	
	public static Clip music=null;
	
	public static String currentSong="";
	
	static FloatControl gainControl;
	
	public static void play(String ref) {
		try {
			//read audio data from whatever source (file/classloader/etc.)
			InputStream audioSrc = SoundHandler.class.getResourceAsStream(ref);
			//add buffer for mark/reset support
			InputStream bufferedIn = new BufferedInputStream(audioSrc);
			AudioInputStream audio = AudioSystem.getAudioInputStream(bufferedIn);		
			
			Clip clip = AudioSystem.getClip();

			clip.open(audio);

			clip.start();

		} catch (Exception e) {
			System.out.println("check " + ref + "\n");
			e.printStackTrace();
		}
	}

	public static void play(String ref, float deltaGain) {
		try {
			//read audio data from whatever source (file/classloader/etc.)
			InputStream audioSrc = SoundHandler.class.getResourceAsStream(ref);
			//add buffer for mark/reset support
			InputStream bufferedIn = new BufferedInputStream(audioSrc);
			AudioInputStream audio = AudioSystem.getAudioInputStream(bufferedIn);		
			
			Clip clip = AudioSystem.getClip();

			clip.open(audio);

			FloatControl gainControl = (FloatControl) clip
					.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(deltaGain); // Reduce volume by deltaGain

			clip.start();

		} catch (Exception e) {
			System.out.println("check " + ref + "\n");
			e.printStackTrace();
		}
	}

	
	
	public static void play(String ref, float deltaGain, float deltaPitch) {
		try {
			//read audio data from whatever source (file/classloader/etc.)
			InputStream audioSrc = SoundHandler.class.getResourceAsStream(ref);
			//add buffer for mark/reset support
			InputStream bufferedIn = new BufferedInputStream(audioSrc);
			AudioInputStream audio = AudioSystem.getAudioInputStream(bufferedIn);		
			
			Clip clip = AudioSystem.getClip();

			clip.open(audio);

			FloatControl gainControl = (FloatControl) clip
					.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(deltaGain); // Reduce volume by deltaGain

			FloatControl pitchControl = (FloatControl) clip
					.getControl(FloatControl.Type.SAMPLE_RATE);
			pitchControl.setValue(deltaPitch);

			clip.start();

		} catch (Exception e) {
			System.out.println("check " + ref + "\n");
			e.printStackTrace();
		}
	}
	
	public static void playMusic(String ref,float deltaGain){
		
		try {
			if(!(music==null))
			music.stop();
			
			//read audio data from whatever source (file/classloader/etc.)
			InputStream audioSrc = SoundHandler.class.getResourceAsStream(ref);
			//add buffer for mark/reset support
			InputStream bufferedIn = new BufferedInputStream(audioSrc);
			AudioInputStream audio = AudioSystem.getAudioInputStream(bufferedIn);			
			
			currentSong=ref;
			
			music=AudioSystem.getClip();
			
			music.open(audio);
			
			gainControl = (FloatControl) music
					.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(deltaGain);
			
			music.loop(9999);
			
			music.start();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setMusicVolume(double gain){
		gainControl.setValue((float) (Math.log(gain) / Math.log(10.0) * 20.0));
	}
	
	private static AudioInputStream convertToPCM(AudioInputStream audioInputStream)
    {
        AudioFormat m_format = audioInputStream.getFormat();

        if ((m_format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) &&
            (m_format.getEncoding() != AudioFormat.Encoding.PCM_UNSIGNED))
        {
            AudioFormat targetFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                m_format.getSampleRate(), 16,
                m_format.getChannels(), m_format.getChannels() * 2,
                m_format.getSampleRate(), m_format.isBigEndian());
            audioInputStream = AudioSystem.getAudioInputStream(targetFormat, audioInputStream);
    }

    return audioInputStream;
}
}