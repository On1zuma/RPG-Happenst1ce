package com.app.main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {
    GamePanel gp;
    
    public Config(GamePanel gp){
         this.gp = gp;

    }

    public void saveConfig(){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));
            // gp.saveSet=0;
            //SCREEN SIZE
            if(gp.setFullScreenSet == 1){
                bw.write("1");
            }
            if(gp.setFullScreenSet == 0){
                bw.write("0");
            }
            //Effect
            bw.newLine();
            bw.write(String.valueOf(gp.sound2.volumeScale));

            //Music
            bw.newLine();
            bw.write(String.valueOf(gp.sound.volumeScale));
            bw.newLine();

            //if help is enabled or not
            bw.write(String.valueOf(gp.KeyHelper));
            bw.newLine();

            //we look is a save had been made or not
            bw.write(String.valueOf(gp.saveSet));
            bw.newLine();

            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));
            String s = br.readLine();

            //Full screen
            if(s.equals("1")){
                gp.setFullScreenSet = 1;
            }
            if(s.equals("0")){
                gp.setFullScreenSet = 0;
            }

            //Effect
            s = br.readLine();
            gp.sound2.volumeScale = Integer.parseInt(s);

            //Music
            s = br.readLine();
            gp.sound.volumeScale = Integer.parseInt(s);

            //keyHelper
            s = br.readLine();
            gp.KeyHelper = Integer.parseInt(s);

            //save set 1 or not 0
            s = br.readLine();
            gp.saveSet = Integer.parseInt(s);
            
            br.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
