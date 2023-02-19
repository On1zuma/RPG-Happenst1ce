package org.entity;

import org.main.GamePanel;
import javax.imageio.ImageIO;
import java.io.IOException;

public class NPC_Datolana3 extends Entity{

    public NPC_Datolana3(GamePanel gp){
        super(gp);

        direction = "stopD";
        type =1;
        speed = gp.player.speed/2;
        getImage();
        setDialogue();
        
    }

    public void getImage(){
        try{
            int z =8;
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

            dialogues[0] = "What do you want ?";
            dialogues[1] = "Let me think";
            dialogues[2] = "...";
    }

    public void speak(){
        super.speak();
    }

    public void setAction(){
        if(direction != "stopD"){
            actionLockCounter++;
            if(actionLockCounter == 30){
                orientation = 2;
                direction = "stopD";
                actionLockCounter=0;
            }
        }        
    }   
}
