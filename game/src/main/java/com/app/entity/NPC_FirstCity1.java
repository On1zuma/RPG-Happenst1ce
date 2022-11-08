package com.app.entity;

import com.app.main.GamePanel;
import javax.imageio.ImageIO;
import java.io.IOException;

public class NPC_FirstCity1 extends Entity{

    public NPC_FirstCity1(GamePanel gp){
        super(gp);

        direction = "stopL";
        type =1;
        speed = gp.player.speed/2;
        getImage();
        setDialogue();
    }

    public void getImage(){
        try{
            int z =4;
            down0 = ImageIO.read(getClass().getResourceAsStream("/res/skins/random"+ z +"/tile000.png"));
            right0 = ImageIO.read(getClass().getResourceAsStream("/res/skins/random"+ z +"/tile008.png"));
            left0 = ImageIO.read(getClass().getResourceAsStream("/res/skins/random"+ z +"/tile004.png"));
            up0 = ImageIO.read(getClass().getResourceAsStream("/res/skins/random"+ z +"/tile012.png"));

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    //MESSAGE
    public void setDialogue(){

            dialogues[0] = "Hi young adventurer";
            dialogues[1] = "Do you know the legend?";
            dialogues[2] = "A legend says that rare artifacts appear \n to those who deserve them.";
            dialogues[3] = "In view of their rarity, they must be very powerful";
            dialogues[4] = "Who knows...";
            dialogues[5] = "...";
    }

    public void speak(){
        super.speak();
    }

    public void setAction(){
        solidArea.x = gp.tileSize;
        solidArea.y = gp.tileSize -20 ;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = gp.tileSize ;
        solidArea.height = gp.tileSize*2;
        
        if(direction != "stopL"){
            actionLockCounter++;
            if(actionLockCounter == 30){
                orientation = 3;
                direction = "stopL";
                actionLockCounter=0;
            }
        }
    }    
}
