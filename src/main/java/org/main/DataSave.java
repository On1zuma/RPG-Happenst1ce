package org.main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataSave {
    GamePanel gp;
    
    public DataSave(GamePanel gp){
         this.gp = gp;

    }

    public void saveData(){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/save.txt"));
            // if(gp.gameStateSaver == gp.playState || gp.gameStateSaver == gp.fightState){
                gp.saveSet =1;
                // save game STATE 
                bw.write(String.valueOf(gp.gameStateSaver));

                //save MAP
                bw.newLine();
                bw.write(String.valueOf(gp.currentMap));

                // save state quest
                bw.newLine();
                bw.write(String.valueOf(gp.currentState));

                //save player gender
                bw.newLine();
                bw.write(String.valueOf(gp.genderValue));

                //save player tribeValue
                bw.newLine();
                bw.write(String.valueOf(gp.tribeValue));

                //player lvl
                bw.newLine();
                bw.write(String.valueOf(gp.level));

                //max PV
                bw.newLine();
                bw.write(String.valueOf(gp.maxPV));

                //PLAYER PV
                bw.newLine();
                bw.write(String.valueOf(gp.playerPV));

                //max XP TO lvl up
                bw.newLine();
                bw.write(String.valueOf(gp.maxXP));

                //current player xp
                bw.newLine();
                bw.write(String.valueOf(gp.playerXP));

                //xp has to been setup as the player xp
                bw.newLine();
                bw.write(String.valueOf(gp.playerXPPrec));

                //Player name
                bw.newLine();
                bw.write(String.valueOf(gp.playerName));

                //Player pos y
                bw.newLine();
                bw.write(String.valueOf(gp.player.worldY));

                //Player pos x
                bw.newLine();
                bw.write(String.valueOf(gp.player.worldX));

                //Last music played
                bw.newLine();
                bw.write(String.valueOf(gp.currentMusicSaver));

                //BattleStep
                bw.newLine();
                bw.write(String.valueOf(gp.BattleStep));

                //save POS IF FIGH OR PLAY STATE
                bw.newLine();
                bw.write(String.valueOf(gp.savePlayerPosX));
                bw.newLine();
                bw.write(String.valueOf(gp.savePlayerPosY));
                bw.newLine();
                bw.write(String.valueOf(gp.saveMap));
                bw.newLine();
                bw.write(String.valueOf(gp.BefforFightX));
                bw.newLine();
                bw.write(String.valueOf(gp.BefforFightY));
                bw.newLine();
                bw.write(String.valueOf(gp.BefforFightMap));
                bw.newLine();
                bw.write(String.valueOf(gp.tpAfterfight));

                //Reset position data
                bw.newLine();
                bw.write(String.valueOf(gp.posAfterTpX));
                bw.newLine();
                bw.write(String.valueOf(gp.posAfterTpY));

                bw.close();
            // }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSave(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/save.txt"));
            String s = br.readLine();


            //Game state
            gp.gameStateSaver = Integer.parseInt(s);

            //Map where the player was
            s = br.readLine();
            gp.currentMap = Integer.parseInt(s);

            //last state reach by the player
            s = br.readLine();
            gp.currentState = Integer.parseInt(s);

            //gender of the player
            s = br.readLine();
            gp.genderValue = Integer.parseInt(s);

            //tribe of the player
            s = br.readLine();
            gp.tribeValue = Integer.parseInt(s);

            //level of the player
            s = br.readLine();
            gp.level = Integer.parseInt(s);

            //max pv of the player
            s = br.readLine();
            gp.maxPV = Integer.parseInt(s);

            //max pv of the player
            s = br.readLine();
            gp.playerPV = Integer.parseInt(s);
            
            //max xp of the player
            s = br.readLine();
            gp.maxXP = Double.parseDouble(s);            

            //max xp of the player
            s = br.readLine();
            gp.playerXP = Double.parseDouble(s); 

            //playerXPPrec of the player
            s = br.readLine();
            gp.playerXPPrec = Double.parseDouble(s); 

            //playerName
            s = br.readLine();
            gp.playerName = String.valueOf(s);

            //player worldY
            s = br.readLine();
            gp.player.worldY = Double.parseDouble(s); 

            //player worldX
            s = br.readLine();
            gp.player.worldX = Double.parseDouble(s); 

            //currentMusicSaver
            s = br.readLine();
            gp.currentMusicSaver = Integer.parseInt(s); 

            //BattleStep
            s = br.readLine();
            gp.BattleStep = Integer.parseInt(s); 

            //POS SAVE 
            s = br.readLine();
            gp.savePlayerPosX = Double.parseDouble(s); 
            s = br.readLine();
            gp.savePlayerPosY = Double.parseDouble(s); 
            s = br.readLine();
            gp.saveMap = Integer.parseInt(s); 

            // save data beffore fight state
            s = br.readLine();
            gp.BefforFightX = Double.parseDouble(s); 
            s = br.readLine();
            gp.BefforFightY = Double.parseDouble(s); 
            s = br.readLine();
            gp.BefforFightMap = Integer.parseInt(s); 
            s = br.readLine();
            gp.tpAfterfight = Integer.parseInt(s); 

            s = br.readLine();
            gp.posAfterTpX = Double.parseDouble(s); 
            s = br.readLine();
            gp.posAfterTpY = Double.parseDouble(s);  

            br.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
