//RIVAL;

package com.app.entity;

import com.app.main.GamePanel;
import javax.imageio.ImageIO;
import java.io.IOException;

public class NPC_RivalForest extends Entity{
    int musicPass =0;
    int lockset =0;

    public NPC_RivalForest(GamePanel gp){
        super(gp);

        direction = "up";
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

             dialogues[0] = "What is happening";
             dialogues[1] = "I have a bad feeling";
             dialogues[2] = "...";
    }

    public void speak(){
        super.speak();
    }

    public void setAction(){
        if(gp.currentState == 31){
            speed = gp.player.speed*3;
            solidArea.x = (gp.tileSize/4) - gp.tileSize/10+ gp.tileSize/4;
            solidArea.y = gp.tileSize/2 -gp.tileSize/7 -gp.tileSize/3;
    
            solidArea.width = gp.tileSize/4;
            solidArea.height = gp.tileSize +gp.tileSize;
            solidAreaDefaultX = solidArea.x;
            solidAreaDefaultY = solidArea.y -gp.tileSize/2;
            direction ="up";
            actionLockCounter++;
            if(actionLockCounter == 80){
                // gp.playSE(6);
                direction = "stopU";
                gp.eHandler.checkEvent();                
                gp.currentState =32;
                gp.keyH.downPressed = true;
            }
        }
        if(gp.currentState >=33){
            gp.player.speed = (double)gp.tileSize * gp.maxWorldCol/850;
            solidArea.x = 0;
            solidArea.y = gp.tileSize/2;
            solidArea.width = gp.tileSize;
            solidArea.height = gp.tileSize +gp.tileSize/2;
            solidAreaDefaultX = solidArea.x;
            solidAreaDefaultY = solidArea.y -gp.tileSize/2;
        }
    }    
}
