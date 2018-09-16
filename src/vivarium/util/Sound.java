/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vivarium.util;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author bright
 */
public class Sound {
    
    private Clip _c;
    
    public Sound(URL son) {
        try {
            AudioInputStream a = AudioSystem.getAudioInputStream(son);
            this._c = AudioSystem.getClip();
            this._c.open(a);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void play() {
        this._c.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    public void stop() {
        this._c.stop();
    }
    
    public boolean isPlaying() {
        return this._c.isRunning();
    }
    
    public static void playTempSound(URL son) {
        Sound s = new Sound(son);
        s.play();
    }
    
}
