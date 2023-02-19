package org.entity;

import org.main.GamePanel;
import javax.imageio.ImageIO;
import java.io.IOException;

public class NPC_FirstCity2 extends Entity{

    public NPC_FirstCity2(GamePanel gp){
        super(gp);

        direction = "stopR";
        type =1;
        speed = gp.player.speed/2;
        getImage();
        setDialogue();
        
    }

    public void getImage(){
        try{
            int z =2;
            down0 = ImageIO.read(getClass().getResourceAsStream("/skins/random"+ z +"/tile000.png"));
            right0 = ImageIO.read(getClass().getResourceAsStream("/skins/random"+ z +"/tile008.png"));
            left0 = ImageIO.read(getClass().getResourceAsStream("/skins/random"+ z +"/tile004.png"));
            up0 = ImageIO.read(getClass().getResourceAsStream("/skins/random"+ z +"/tile012.png"));

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    //MESSAGE
    public void setDialogue(){

            dialogues[0] = "The professor is a genius in his field";
            dialogues[1] = "He helped me a lot";
            dialogues[2] = "...";
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
        
        if(direction != "stopR"){
            actionLockCounter++;
            if(actionLockCounter == 30){
                orientation = 4;
                direction = "stopR";
                actionLockCounter=0;
            }
        }
    }    
}
