package com.app.monster;

import javax.imageio.ImageIO;
import java.io.IOException;

import com.app.entity.Entity;
import com.app.main.GamePanel;
import java.util.Random;

public class MON_RivalM extends Entity {
    String gender;

    public MON_RivalM(GamePanel gp) {
        super(gp);        
        name = "Rival Monster";
        direction = "stopD";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 50;
        life = maxLife;

        type =2;
        getImage();
    }

    public void getImage(){

        try{
            gender = "Orc";
            up0 = ImageIO.read(getClass().getResourceAsStream("/res/skins/monster/"+ gender +"/tile012.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile013.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile014.png"));
            up3 = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile015.png"));
    
            right0 = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile008.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile009.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile010.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile011.png"));
    
            left0 = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile004.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile005.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile006.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile007.png"));
    
            down0 = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile000.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile001.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile002.png"));
            down3 = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile003.png"));

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void setAction(){
        
        int distanceRow = (int)gp.player.worldY/gp.tileSize - (int)worldY/gp.tileSize;
        int distanceCol = (int)gp.player.worldX/gp.tileSize - (int)worldX/gp.tileSize;

        if(distanceCol >= -6 && distanceCol <= 6 && distanceRow >= -6 && distanceRow <= 6){
            onPath = true;
        }
        if(distanceRow < -6 || distanceRow > 6){
            onPath = false;
        }
        if(distanceCol < -6 || distanceCol > 6){
            onPath = false;
        }
        if(onPath == true){
            // int goaLCol =47;//36
            // int golRow= 20;//23
            int  goaLCol = ((int)gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
            int  golRow = ((int)gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;
            searchPath(goaLCol,golRow);
            
        }else if(onPath == false){
            actionLockCounter++;

            if(actionLockCounter == 60){
                // i= i+1;
                // System.out.println("MOTHER MOVE X:" + worldX/gp.tileSize + " Y " + worldY/gp.tileSize);

            Random random = new Random();
            int i = random.nextInt(10)+1; // pick up a number from 1 to 100
            if(i == 1){
                direction = "up";
            }
            else if(i == 2){
                direction = "left";
            }
            else if(i == 3){
                direction = "down";
            }
            else if(i == 4){
                direction = "right";
            } 
            //tentative pour voir si je peux faire bouger le perso comme je veux
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
}
