//RIVAL;

package org.entity;

import org.main.GamePanel;
import javax.imageio.ImageIO;
import java.io.IOException;

public class NPC_Rival extends Entity{
    int musicPass =0;
    int lockset =0;

    public NPC_Rival(GamePanel gp){
        super(gp);

        direction = "up";
        type =1;
        speed = gp.player.speed - gp.player.speed/4;
        getImage();
        setDialogue();
        
    }

    public void getImage(){
        try{
            down0 = ImageIO.read(getClass().getResourceAsStream("/skins/rival/tile000.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/skins/rival/tile001.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/skins/rival/tile002.png"));
            down3 = ImageIO.read(getClass().getResourceAsStream("/skins/rival/tile003.png"));

            right0 = ImageIO.read(getClass().getResourceAsStream("/skins/rival/tile008.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/skins/rival/tile009.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/skins/rival/tile010.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/skins/rival/tile011.png"));

            left0 = ImageIO.read(getClass().getResourceAsStream("/skins/rival/tile004.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/skins/rival/tile005.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/skins/rival/tile006.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/skins/rival/tile007.png"));
            
            up0 = ImageIO.read(getClass().getResourceAsStream("/skins/rival/tile012.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/skins/rival/tile013.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/skins/rival/tile014.png"));
            up3 = ImageIO.read(getClass().getResourceAsStream("/skins/rival/tile015.png"));

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    //MESSAGE
    public void setDialogue(){

             dialogues[0] = "DO YOU ONLY WAKE UP NOW!?";
             dialogues[1] = "Hurry it's finally D-Day";
             dialogues[2] = "We will finally receive the necessary \n equipment to travel! \n see you at the lab";
             dialogues[3] = "...";
    }

    public void speak(){
        super.speak();
    }

    public void setAction(){
        actionLockCounter++;
        if(musicPass==0){
            gp.playSE(2);
            musicPass=1;
        }
        if(actionLockCounter == 60 && gp.currentState == 0){

        direction = "stopL";
        gp.currentState = 1; 
        actionLockCounter =0;
        }
        if(gp.currentState == 2){
            if(lockset ==0){
                actionLockCounter=0;
                lockset=1;
            }
            else if(lockset ==1){
                actionLockCounter++;
                if(actionLockCounter <= 200){
                    direction = "down";
                }
                if(actionLockCounter > 200){
                    gp.gameState = gp.playState;
                    direction = "stopD";
                    gp.currentState = 3; 
                    gp.playSE(2);
                    actionLockCounter =0;
                }
            }
        }
    }    
}
