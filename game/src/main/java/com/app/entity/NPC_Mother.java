package com.app.entity;

import com.app.main.GamePanel;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;

public class NPC_Mother extends Entity{
    public NPC_Mother(GamePanel gp){
        super(gp);

        direction = "down";
        type =1;
        speed = gp.player.speed/2;
        getImage();
        setDialogue();
    }
    
    public void getImage(){
        try{
            up0 = ImageIO.read(getClass().getResourceAsStream("/res/skins/mother/up0.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/skins/mother/up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/skins/mother/up2.png"));
            up3 = ImageIO.read(getClass().getResourceAsStream("/res/skins/mother/up3.png"));

            right0 = ImageIO.read(getClass().getResourceAsStream("/res/skins/mother/right0.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/skins/mother/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/skins/mother/right2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/res/skins/mother/right3.png"));

            left0 = ImageIO.read(getClass().getResourceAsStream("/res/skins/mother/left0.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/skins/mother/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/skins/mother/left2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/res/skins/mother/left3.png"));
            
            down0 = ImageIO.read(getClass().getResourceAsStream("/res/skins/mother/down0.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/skins/mother/down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/skins/mother/down2.png"));
            down3 = ImageIO.read(getClass().getResourceAsStream("/res/skins/mother/down3.png"));

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public void setDialogue(){
        dialogues[0] = "HEY, "+gp.playerName+" !";
        dialogues[1] = "A great day to train";
        dialogues[2] = "I love Marakasa city ";
        dialogues[3] = "Have you seen the flight of pelicans,\n it was wonderful! ";
        dialogues[4] = " ... "; 
    }

    public void setAction(){
        actionLockCounter++;
        if(actionLockCounter == 80){
        Random random = new Random();
        int i = random.nextInt(10)+1; // pick up a number from 1 to 100
        if(i == 1 && worldY/gp.tileSize>=2){
            direction = "up";
        }
        else if(i == 2 && worldY/gp.tileSize<=63){
            direction = "down";
        }
        else if(i == 3 && worldX/gp.tileSize>=2){
            direction = "left";
        }
        else if(i == 4 && worldX/gp.tileSize<=63){
            direction = "right";
        } 
        else if(i == 5){
            direction = "stopD";
        }
        else if(i == 6){
            direction = "stopU";
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
    
    public void speak(){
        super.speak();
    }
}
