package com.app.entity;

import com.app.main.GamePanel;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;

public class NPC_Blocker extends Entity{

    public NPC_Blocker(GamePanel gp){
        super(gp);

        direction = "stopL";
        type =1;
        speed = gp.player.speed/2;
        getImage();
        setDialogue();
        
    }

    public void getImage(){
        try{
            int z =3;
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

            // dialogues[0] = "The professor is looking for you";
            // dialogues[1] = "Join it ASAP !";
            // dialogues[2] = "A good day";            // 
            dialogues[0] = "Greetings";
            dialogues[1] = "...";
    }

    public void speak(){
        super.speak();
    }

    public void setAction(){
        actionLockCounter++;

        if(actionLockCounter == 100){
            i= i+1;

        Random random = new Random();
        int i = random.nextInt(10)+1; // pick up a number from 1 to 100
        if(i == 1){
            direction = "stopL";
        }
        else if(i == 2){
            direction = "stopL";
        }
        else if(i == 3){
            direction = "stopL";
        }
        else if(i == 4){
            direction = "stopL";
        } 
        //tentative pour voir si je peux faire bouger le perso comme je veux
        else if(i > 4){
            direction = "stopL";
        }
        actionLockCounter =0;
        }
    }    
}

