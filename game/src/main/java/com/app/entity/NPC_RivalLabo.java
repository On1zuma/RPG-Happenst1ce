package com.app.entity;

import com.app.main.GamePanel;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;

public class NPC_RivalLabo extends Entity{
    int set=1;
    public NPC_RivalLabo(GamePanel gp){
        super(gp);

        direction = "stopD";
        type =1;
        speed = gp.player.speed;
        getImage();
        setDialogue();
        
    }

    public void getImage(){
        try{
            down0 = ImageIO.read(getClass().getResourceAsStream("/res/skins/rival/tile000.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/skins/rival/tile001.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/skins/rival/tile002.png"));
            down3 = ImageIO.read(getClass().getResourceAsStream("/res/skins/rival/tile003.png"));

            right0 = ImageIO.read(getClass().getResourceAsStream("/res/skins/rival/tile008.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/skins/rival/tile009.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/skins/rival/tile010.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/res/skins/rival/tile011.png"));

            left0 = ImageIO.read(getClass().getResourceAsStream("/res/skins/rival/tile004.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/skins/rival/tile005.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/skins/rival/tile006.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/res/skins/rival/tile007.png"));
            
            up0 = ImageIO.read(getClass().getResourceAsStream("/res/skins/rival/tile012.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/skins/rival/tile013.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/skins/rival/tile014.png"));
            up3 = ImageIO.read(getClass().getResourceAsStream("/res/skins/rival/tile015.png"));

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    //MESSAGE
    public void setDialogue(){

             dialogues[0] = "I can finally go on an adventure! \n I've been waiting for this for so long";
             dialogues[1] = "The lands of Happenst1ce are vast and full of challenges";
             dialogues[2] = "Karlos is one of the founding fathers of virtual reality";
             dialogues[3] = "...";
    }

    public void speak(){
        super.speak();
    }

    public void setAction(){

        if(gp.currentState == 18){
            gp.playSE(2);
            direction = "stopD";
            gp.aSetter.setNPC();
            gp.currentState = 19;
            actionLockCounter=0;
        }
        else if(gp.currentState == 19){
            direction = "stopD";
            gp.keyH.downPressed = true;
            gp.eHandler.checkEvent();
        }
        else if(gp.currentState == 20){
            direction = "stopD";
            actionLockCounter=0;
        }
        else if(gp.currentState == 23){
            if(set==1){
                actionLockCounter=0;
                set=0;
            }
            direction = "up";
            actionLockCounter++;

            if(actionLockCounter >= 50){
                gp.playSE(2);
                gp.currentState = 24;
                gp.gameState = gp.playState;
                actionLockCounter=0;
                set=1;
            }
        }
        else if(gp.currentState != 18 || gp.currentState != 19 || gp.currentState != 23 || gp.currentState != 20 || gp.currentState != 21 || gp.currentState != 22){
            if(set==1){
                actionLockCounter=0;
                set=0;
            }
            actionLockCounter++;

            if(actionLockCounter >= 60){
                // i= i+1;
                // System.out.println("MOTHER MOVE X:" + worldX/gp.tileSize + " Y " + worldY/gp.tileSize);
    
            Random random = new Random();
            int i = random.nextInt(10)+1; // pick up a number from 1 to 100
            if(i == 1){
                direction = "stopD";
            }
            else if(i == 2){
                direction = "stopD";
            }
            else if(i == 3){
                direction = "stopL";
            }
            else if(i == 4){
                direction = "stopL";
            } 
            //tentative pour voir si je peux faire bouger le perso comme je veux
            else if(i == 5){
                direction = "stopD";
            }
            else if(i == 6){
                direction = "stopD";
            }
            else if(i == 7){
                direction = "stopL";
            }
            else if(i == 8){
                direction = "stopR";
            }
            else if(i > 8){
                direction = "stopD";
            }
            actionLockCounter =0;
            }
        }

    }      
}
