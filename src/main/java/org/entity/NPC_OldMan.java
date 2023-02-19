package org.entity;

import org.main.GamePanel;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;

public class NPC_OldMan extends Entity{
    int soundPassOldMan =0;

    public NPC_OldMan(GamePanel gp){
        super(gp);
        direction = "stopD";
        type =1;
        speed = gp.player.speed/2;
        getImage();
        setDialogue();
        
    }

    public void getImage(){
        try{
            up0 = ImageIO.read(getClass().getResourceAsStream("/skins/searcher/up0.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/skins/searcher/up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/skins/searcher/up2.png"));
            up3 = ImageIO.read(getClass().getResourceAsStream("/skins/searcher/up3.png"));

            right0 = ImageIO.read(getClass().getResourceAsStream("/skins/searcher/right0.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/skins/searcher/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/skins/searcher/right2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/skins/searcher/right3.png"));

            left0 = ImageIO.read(getClass().getResourceAsStream("/skins/searcher/left0.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/skins/searcher/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/skins/searcher/left2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/skins/searcher/left3.png"));
            
            down0 = ImageIO.read(getClass().getResourceAsStream("/skins/searcher/down0.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/skins/searcher/down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/skins/searcher/down2.png"));
            down3 = ImageIO.read(getClass().getResourceAsStream("/skins/searcher/down3.png"));

            anim0 = ImageIO.read(getClass().getResourceAsStream("/skins/searcher/chut.png"));
            anim1 = ImageIO.read(getClass().getResourceAsStream("/skins/searcher/down0.png"));
            anim2 = ImageIO.read(getClass().getResourceAsStream("/skins/searcher/down0.png"));
            anim3 = ImageIO.read(getClass().getResourceAsStream("/skins/searcher/down0.png"));


        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    //MESSAGE
    public void setDialogue(){

            dialogues[0] = "Explore, discover, accomplish, nothing better \n for such a great adventure! ";
            dialogues[1] = "The world is moving at breakneck speed, don't you think?";
            dialogues[2] = "VR is just a step in our progress";
            dialogues[3] = "... ?";
    }

    public void speak(){
        super.speak();
    }

    public void setAction(){
        
        // Animation labo first floor, speaking about avatar
        if(gp.currentState == 6){direction = "stopD";}
        else if(gp.currentState == 8){
            direction = "stopL";
            actionLockCounter++;
            if(actionLockCounter == 100){
                actionLockCounter=0;
                direction = "stopD";
                gp.currentState = 9;
                gp.gameState = gp.dialogueState;
            }
        }
        else if(gp.currentState == 10){
            direction = "up";
            actionLockCounter++;
            if(actionLockCounter == 200){
                gp.currentState = 11;
                actionLockCounter=0;
            }
        }
        else if(gp.currentState == 11){
            gp.playSE(2);
            gp.aSetter.setNPC();
            gp.gameState = gp.playState;
            gp.checkIfMusicIsSet();              
            gp.playMusic(1);
        }

        // Animation labo second floor, avatar creation
        else if(gp.currentState == 12){
            direction = "stopR";
        }
        else if(gp.currentState == 13){
            solidArea.y = gp.tileSize;
            direction = "up";
            actionLockCounter++;
            if(actionLockCounter == 100){
                solidArea.y = gp.tileSize;
                gp.currentState = 14;
                actionLockCounter=0;
                gp.playSE(12);
            }
        }
        else if(gp.currentState == 14){
            solidArea.y = gp.tileSize;
            direction = "stopU";

            actionLockCounter++;
            if(actionLockCounter == 150){
                solidArea.y = gp.tileSize;
                direction = "stopU";
                gp.currentState = 15;
                actionLockCounter=0;
            }
        }
        else if(gp.currentState == 15){
            solidArea.y = gp.tileSize;
            direction = "down";
            actionLockCounter++;
            if(actionLockCounter == 60){
                solidArea.y = gp.tileSize/2;
                direction = "stopR";
                gp.currentState = 16;
                actionLockCounter=0;
            }
        }
        else if(gp.currentState == 16){
             direction = "stopR";
             gp.eHandler.checkEvent();
             gp.gameState = gp.dialogueState;

        }
        else{
            actionLockCounter++;
            if(actionLockCounter == 100){
                i= i+1;
    
            Random random = new Random();
            int i = random.nextInt(10)+1; // pick up a number from 1 to 100
            if(i == 1){
                direction = "animation";
            }
            else if(i == 2){
                direction = "stopD";
            }
            else if(i == 3){
                direction = "stopR";
            }
            else if(i == 4){
                direction = "stopL";
            } 
            //tentative pour voir si je peux faire bouger le perso comme je veux
            else if(i > 4){
                direction = "stopD";
            }
            actionLockCounter =0;
            }
        }
    }    
}
