package org.entity;

import org.main.GamePanel;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;

public class NPC_Datolana2 extends Entity{

    public NPC_Datolana2(GamePanel gp){
        super(gp);

        direction = "stopL";
        type =1;
        speed = gp.player.speed/2;
        getImage();
        setDialogue();
        
    }

    public void getImage(){
        try{
            int z =7;
            down0 = ImageIO.read(getClass().getResourceAsStream("/skins/random"+ z +"/tile000.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/skins/random"+ z +"/tile001.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/skins/random"+ z +"/tile002.png"));
            down3 = ImageIO.read(getClass().getResourceAsStream("/skins/random"+ z +"/tile003.png"));

            right0 = ImageIO.read(getClass().getResourceAsStream("/skins/random"+ z +"/tile008.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/skins/random"+ z +"/tile009.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/skins/random"+ z +"/tile010.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/skins/random"+ z +"/tile011.png"));

            left0 = ImageIO.read(getClass().getResourceAsStream("/skins/random"+ z +"/tile004.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/skins/random"+ z +"/tile005.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/skins/random"+ z +"/tile006.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/skins/random"+ z +"/tile007.png"));
            
            up0 = ImageIO.read(getClass().getResourceAsStream("/skins/random"+ z +"/tile012.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/skins/random"+ z +"/tile013.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/skins/random"+ z +"/tile014.png"));
            up3 = ImageIO.read(getClass().getResourceAsStream("/skins/random"+ z +"/tile015.png"));

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    //MESSAGE
    public void setDialogue(){

            dialogues[0] = "Datacenter runs 24 hours a day in Datolana";
            dialogues[1] = "That's impressive";
            dialogues[2] = "...";
    }

    public void speak(){
        super.speak();
    }

    public void setAction(){
        actionLockCounter++;
        if(actionLockCounter == 90){
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
}
