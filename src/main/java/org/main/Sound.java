package org.main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.*;

public class Sound {

    Clip clip;
    URL soundURL[] = new URL[40];
    FloatControl fc;
    int volumeScale = 3;
    float volume;
    public Sound(){

        soundURL[0] = getClass().getResource("/sound/music/Outside1.wav");

        soundURL[1] = getClass().getResource("/sound/music/Inside1.wav");
        soundURL[2] = getClass().getResource("/sound/effect/Zone_transition.wav");

        soundURL[3] = getClass().getResource("/sound/effect/Get_item1.wav");
        soundURL[4] = getClass().getResource("/sound/effect/Get_item1.wav");
        soundURL[5] = getClass().getResource("/sound/music/mainMenu.wav");
        
        soundURL[6] = getClass().getResource("/sound/effect/logoSound.wav");
        soundURL[7] = getClass().getResource("/sound/music/musicCreationMenu.wav");
        soundURL[8] = getClass().getResource("/sound/music/cave_theme.wav");

        soundURL[9] = getClass().getResource("/sound/music/musicLetsgo.wav");
        soundURL[10] = getClass().getResource("/sound/music/musicChill.wav");
        soundURL[11] = getClass().getResource("/sound/music/concentrationMusic.wav");

        soundURL[12] = getClass().getResource("/sound/effect/clavierEffect.wav");
        
        soundURL[13] = getClass().getResource("/sound/effect/attack1.wav");
        soundURL[14] = getClass().getResource("/sound/effect/attack2.wav");
        soundURL[15] = getClass().getResource("/sound/effect/attack3.wav");
        soundURL[16] = getClass().getResource("/sound/effect/Hit1.wav");

        soundURL[17] = getClass().getResource("/sound/music/Battle.wav");

        soundURL[18] = getClass().getResource("/sound/effect/clickMenu.wav");
        soundURL[19] = getClass().getResource("/sound/effect/gameOver.wav");
        soundURL[20] = getClass().getResource("/sound/effect/Lvlup.wav");

        soundURL[21] = getClass().getResource("/sound/music/inventoryMusic.wav");
        soundURL[22] = getClass().getResource("/sound/music/TrainingArea.wav");

        soundURL[23] = getClass().getResource("/sound/effect/electricSoundEffect.wav");
        soundURL[24] = getClass().getResource("/sound/effect/enterEffect.wav");
    }

    public void setFile(int i){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
        } catch (Exception e) {
        }
    }

    public void play(){
        // FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        // gainControl.setValue(-5.0f);
        clip.start();
    }

    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        clip.stop();
    }

    public void checkVolume(){
        switch(volumeScale){
            case 0: volume = -80f; break;
            case 1: volume = -20f; break;
            case 2: volume = -12f; break;
            case 3: volume = -5f; break;
            case 4: volume = 1f; break;
            case 5: volume = 6f; break;
        }
        fc.setValue(volume);
    }
}
