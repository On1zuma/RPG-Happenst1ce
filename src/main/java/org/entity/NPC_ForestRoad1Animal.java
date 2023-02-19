package org.entity;

import org.main.GamePanel;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;

public class NPC_ForestRoad1Animal extends Entity{

    public NPC_ForestRoad1Animal(GamePanel gp){
        super(gp);

        direction = "stopD";
        type =1;
        speed = gp.player.speed;
        getImage();
        setDialogue();
        
    }

    public void getImage(){
        try{
            int z =1;
            down0 = ImageIO.read(getClass().getResourceAsStream("/skins/animals/random"+ z +"/tile000.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/skins/animals/random"+ z +"/tile001.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/skins/animals/random"+ z +"/tile002.png"));
            down3 = ImageIO.read(getClass().getResourceAsStream("/skins/animals/random"+ z +"/tile000.png"));

            left0 = ImageIO.read(getClass().getResourceAsStream("/skins/animals/random"+ z +"/tile003.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/skins/animals/random"+ z +"/tile004.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/skins/animals/random"+ z +"/tile005.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/skins/animals/random"+ z +"/tile003.png"));

            right0 = ImageIO.read(getClass().getResourceAsStream("/skins/animals/random"+ z +"/tile006.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/skins/animals/random"+ z +"/tile007.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/skins/animals/random"+ z +"/tile008.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/skins/animals/random"+ z +"/tile006.png"));
   
            up0 = ImageIO.read(getClass().getResourceAsStream("/skins/animals/random"+ z +"/tile009.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/skins/animals/random"+ z +"/tile010.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/skins/animals/random"+ z +"/tile011.png"));
            up3 = ImageIO.read(getClass().getResourceAsStream("/skins/animals/random"+ z +"/tile009.png"));

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    //MESSAGE
    public void setDialogue(){

            dialogues[0] = "skouik skouik";
            dialogues[1] = "...";
    }

    public void speak(){
        super.speak();
    }

    public void setAction(){
        actionLockCounter++;
        if(actionLockCounter == 60){
        Random random = new Random();
        int i = random.nextInt(20)+1; // pick up a number from 1 to 100
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
}
