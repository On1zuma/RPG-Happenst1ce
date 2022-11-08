package com.app.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.app.main.KeyHandler;
import com.app.main.GamePanel;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.AlphaComposite;

public class Player extends Entity {

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;   

    String gender;

    public Player(GamePanel gp, KeyHandler keyH){

        super(gp);
        this.keyH = keyH;

        //screen position
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        //on crée un cube solide autour du perso
        solidArea = new Rectangle();
        solidArea.x = (gp.tileSize/4) - gp.tileSize/8;
        solidArea.y = gp.tileSize;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = gp.tileSize/2 + gp.tileSize/4 ;
        solidArea.height = gp.tileSize/2 ;

        attackArea.width =36;
        attackArea.height =36;

        setDefaultValues();
        getImage();
    }

    public void setDefaultValues(){
        speed = (double)gp.tileSize * gp.maxWorldCol/800;
        worldX = gp.tileSize/1.391304347826087 * 23;
        worldY =gp.tileSize/1.391304347826087 * 41;

        direction = "down";
    }
    
    public void update(){
        
        //ATTACK SETUP ANIMATION
        if(attacking == true){
            attacking();
        }

        //WALK SETUP ANIMATION
        else if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true){

            if(keyH.upPressed == true){
                direction = "up";
                orientation = 1;
            }
            else if(keyH.downPressed == true){
                direction = "down";
                orientation = 2;
            }
            else if(keyH.leftPressed == true){
                direction = "left";
                orientation = 3;
            }
            else if(keyH.rightPressed == true){
                direction = "right";
                orientation = 4;
            }       
                 
            //CHECK TILE COLLISION
            collision = false;
            gp.cChecker.checkTile(this);

            //CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            //CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            //CHECK MONSTER COLLISION
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            interactMONSTER(monsterIndex);

            //CHECK EVENT HANDLER
            gp.eHandler.checkEvent();

            //IF COLLISION IS FALSE PLAYER CAN'T MOVE
            if(collision == false && keyH.enterPressed == false){
                switch(direction){
                case "up":
                       // equal: playerY = playerSpeed - playerY; 
                        worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
                }
            }
            gp.keyH.enterPressed = false;

            spriteCounter ++;
            if(spriteCounter > 9){
    
                spriteNum++;
                if(spriteNum == 5){
                    spriteNum=1;
                }
                spriteCounter = 0;
            }
        }else{
            if(orientation == 1){
                direction = "up";
                spriteNum = 1;
            }
            if(orientation == 2){
                direction = "down";
                spriteNum = 1;
            }
            if(orientation == 3){
                direction = "left";
                spriteNum = 1;
            }
            if(orientation == 4){
                direction = "right";
                spriteNum = 1;
            }
        }

        //allow us to be invincible for 1 second when hit by enemy
        if(invincible == true){
            invincibleCounter++;
            if(invincibleCounter >60){
                invincible = false;
                invincibleCounter=0;
            }
        }
    }

    public void attacking(){
        spriteCounter++;
        if(spriteCounter <=5){
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <=25){
            spriteNum = 2;
            if(soundAttackStopper == 0){
                if(soundAttack==0){
                    gp.playSE(13);
                    soundAttack=1;
                }
                else if(soundAttack==1){
                    gp.playSE(14);
                    soundAttack=2;
                }
                else if(soundAttack==2){
                    gp.playSE(15);
                    soundAttack=0;
                }
                soundAttackStopper =1;
            }
            //save the current position of the SolidRect player
            double currentWorldX = worldX;
            double currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;
            //Adjust on attack
            switch(direction){
                case"up":worldY -=attackArea.height; break;
                case"down":worldY +=attackArea.height; break;
                case"left":worldX -=attackArea.width; break;
                case"right":worldX +=attackArea.width; break;
            }
            //change the solideArea to the sword size
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            //check monster collision
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            // IF THE MONSTER IS HIT BUY OUR SWORD THEN he take some damage
            damageMonster(monsterIndex);
            // restore the original data
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.height = solidAreaHeight;
            solidArea.width = solidAreaWidth;
        }
        if(spriteCounter > 25){
            spriteNum = 1;
            spriteCounter =0;
            attacking = false;
            soundAttackStopper =0;
        }
    }

    //function to see if a player hit an item
    public void pickUpObject(int i){
        if(gp.currentState < 24){
            if(i != 999 && i == 0){
                gp.currentState =12;
                gp.playSE(4); 
            }
            if(i != 999 && i == 5){
                gp.currentState =6;
                gp.playSE(4); 
            } 
        }else if(gp.currentState >=24){
            if(i != 999){
                gp.playSE(4); 
                gp.obj[gp.currentState][gp.currentMap][i] = null;
                gp.clueCounter ++;
            }
        }    
    }

    public void interactNPC(int i){
        if(i != 999){
            if(gp.keyH.enterPressed == true){
            gp.gameState = gp.dialogueState;
            gp.npc[gp.currentMap][i].speak();
            }
        }
        else{
            if(gp.keyH.enterPressed == true && gp.gameState == gp.fightState){
                attacking = true;
            }
        }
    }
    
    public void interactMONSTER(int i){
        if(i != 999){
            if(invincible == false){
                //damage
                if(gp.currentState< 24 && gp.playerPV > 10){
                    gp.playerPV-=5;
                }
                else if(gp.currentState >= 24){
                    gp.playerPV-= (5+gp.maxXP/20);
                }

                gp.playSE(16);
                invincible = true;
            }
        }
    }

    public void damageMonster(int i){
        if(i != 999){
            //set the monster invincible when hit one time
            if(gp.monster[gp.currentMap][i].invincible == false){
                knockBack(gp.monster[gp.currentMap][i]);
                gp.monster[gp.currentMap][i].life -= 10+gp.level;
                if(gp.debug ==1){
                    System.out.println("Hit!");
                }
                if(soundAttackStopper == 1){
                    gp.playSE(16);
                }
                gp.monster[gp.currentMap][i].invincible = true;
            }
            //IF PV <0 then we kill the monster
            if(gp.monster[gp.currentMap][i].life <=0){
                gp.monster[gp.currentMap][i].dying = true;
            }
        }
        else{
            if(gp.debug ==1){
                System.out.println("NO Hit!");
            }
        }
    }

    public void knockBack(Entity entity){
        entity.direction = direction;
        entity.speed += 10;
        entity.knockBack = true;
    }

    public void draw(Graphics2D g2){
        BufferedImage image = null;
        
        switch(direction){
            case "up":
            if(attacking == false){
                if(spriteNum == 1){image = up0;}
                if(spriteNum == 2){image = up1;}
                if(spriteNum == 3){image = up2;}
                if(spriteNum == 4){image = up3;}
            }//attack ANIMATION
            if(attacking == true){
                if(spriteNum == 1){image = attackUp1;}
                if(spriteNum == 2){image = attackUp2;}
            }
            break;

            case "down":
            if(attacking == false){
                if(spriteNum == 1){image = down0;}
                if(spriteNum == 2){image = down1;}
                if(spriteNum == 3){image = down2;}
                if(spriteNum == 4){image = down3;}
            }
            if(attacking == true){
                if(spriteNum == 1){image = attackDown1;}
                if(spriteNum == 2){image = attackDown2;}
            }
            break;

            case "left":
            if(attacking == false){
                if(spriteNum == 1){image = left0;}
                if(spriteNum == 2){image = left1;}
                if(spriteNum == 3){image = left2;}
                if(spriteNum == 4){image = left3;}
            }
            if(attacking == true){
                if(spriteNum == 1){image = attackLeft1;}
                if(spriteNum == 2){image = attackLeft2;}
            }
            break;

            case "right":
            if(attacking == false){
                if(spriteNum == 1){image = right0;}
                if(spriteNum == 2){image = right1;}
                if(spriteNum == 3){image = right2;}
                if(spriteNum == 4){image = right3;}
            }
            if(attacking == true){
                if(spriteNum == 1){image = attackRight1;}
                if(spriteNum == 2){image = attackRight2;}
            }
            break;
        }

        if(invincible == true){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }

        int mult = 2;
        int x =screenX;
        int y=screenY;
        
        if(gp.gameState != gp.photoState){

            if(screenX > worldX){
                x = (int)worldX;
                gp.EventEdge =1;
            }
            if(screenY > worldY){
                y = (int)worldY;
                gp.EventEdge =1;
            }
            //right
            int rightOffset = gp.screenWidth - screenX;
            if(rightOffset > gp.worldWidth - worldX){
                x = gp.screenWidth - (gp.worldWidth - (int)worldX);
                gp.EventEdge =1;
            }
            int bottomOffset = gp.screenHeight - screenY;
            if(bottomOffset > gp.worldHeight - worldY){
                y = gp.screenHeight - (gp.worldHeight - (int)worldY);
                gp.EventEdge =1;
            }

            //if no edge detected
            else if(screenX < worldX && screenY < worldY && rightOffset < gp.worldWidth - worldX && bottomOffset < gp.worldHeight - worldY){
                gp.EventEdge =0;
            }
        }
        //we make the character grow and we also adapt his position
        g2.drawImage(image, x-gp.tileSize/mult, y-gp.tileSize/mult, gp.tileSize*mult, gp.tileSize*mult, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        if(gp.debug==1){
            g2.setColor(Color.red);
            g2.drawRect(x + solidArea.x , y + solidArea.y , solidArea.width, solidArea.height);
        }
    }

    public void getImage(){
        try{
            //exp
            otherImage();

            //PLAYER SKIN
            gender = "boy";
            up0b = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/"+ gender +"/up0.png"));
            up1b = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/"+ gender +"/up1.png"));
            up2b = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/"+ gender +"/up2.png"));
            up3b = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/"+ gender +"/up3.png"));

            right0b = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/"+ gender +"/right0.png"));
            right1b = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/"+ gender +"/right1.png"));
            right2b = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/"+ gender +"/right2.png"));
            right3b = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/"+ gender +"/right3.png"));

            left0b = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/"+ gender +"/left0.png"));
            left1b = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/"+ gender +"/left1.png"));
            left2b = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/"+ gender +"/left2.png"));
            left3b = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/"+ gender +"/left3.png"));
            
            down0b = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/"+ gender +"/down0.png"));
            down1b = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/"+ gender +"/down1.png"));
            down2b = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/"+ gender +"/down2.png"));
            down3b = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/"+ gender +"/down3.png"));

            gender = "girl";
            up0g = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/"+ gender +"/up0.png"));
            up1g = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/"+ gender +"/up1.png"));
            up2g = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/"+ gender +"/up2.png"));
            up3g = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/"+ gender +"/up3.png"));

            right0g = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/"+ gender +"/right0.png"));
            right1g = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/"+ gender +"/right1.png"));
            right2g= ImageIO.read(getClass().getResourceAsStream("/res/skins/player/"+ gender +"/right2.png"));
            right3g = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/"+ gender +"/right3.png"));

            left0g = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/"+ gender +"/left0.png"));
            left1g = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/"+ gender +"/left1.png"));
            left2g = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/"+ gender +"/left2.png"));
            left3g = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/"+ gender +"/left3.png"));
            
            down0g = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/"+ gender +"/down0.png"));
            down1g = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/"+ gender +"/down1.png"));
            down2g = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/"+ gender +"/down2.png"));
            down3g = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/"+ gender +"/down3.png"));

            gender = "Avian";
            attackDown1a = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/"+gender+"Fight/tile001.png"));
            attackDown2a = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/"+gender+"Fight/tile002.png"));

            attackLeft1a = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/"+gender+"Fight/tile003.png"));
            attackLeft2a = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/"+gender+"Fight/tile004.png"));

            attackRight1a = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/"+gender+"Fight/tile005.png"));
            attackRight2a = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/"+gender+"Fight/tile006.png"));

            attackUp1a = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/"+gender+"Fight/tile007.png"));
            attackUp2a = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/"+gender+"Fight/tile008.png"));

            up0a = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile012.png"));
            up1a = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile013.png"));
            up2a = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile014.png"));
            up3a = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile015.png"));

            right0a = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile008.png"));
            right1a = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile009.png"));
            right2a= ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile010.png"));
            right3a = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile011.png"));

            left0a = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile004.png"));
            left1a = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile005.png"));
            left2a = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile006.png"));
            left3a = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile007.png"));
            
            down0a = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile000.png"));
            down1a = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile001.png"));
            down2a = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile002.png"));
            down3a = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile003.png"));
            
            gender = "Orc";
            attackDown1o = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/"+gender+"Fight/tile001.png"));
            attackDown2o = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/"+gender+"Fight/tile002.png"));

            attackLeft1o = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/"+gender+"Fight/tile003.png"));
            attackLeft2o = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/"+gender+"Fight/tile004.png"));

            attackRight1o = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/"+gender+"Fight/tile005.png"));
            attackRight2o = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/"+gender+"Fight/tile006.png"));

            attackUp1o = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/"+gender+"Fight/tile007.png"));
            attackUp2o = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/"+gender+"Fight/tile008.png"));

            up0o = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile012.png"));
            up1o = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile013.png"));
            up2o = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile014.png"));
            up3o = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile015.png"));

            right0o = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile008.png"));
            right1o = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile009.png"));
            right2o= ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile010.png"));
            right3o = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile011.png"));

            left0o = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile004.png"));
            left1o = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile005.png"));
            left2o = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile006.png"));
            left3o = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile007.png"));
            
            down0o = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile000.png"));
            down1o = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile001.png"));
            down2o = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile002.png"));
            down3o = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile003.png"));

            gender = "Panda";
            attackDown1p = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/"+gender+"Fight/tile001.png"));
            attackDown2p = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/"+gender+"Fight/tile002.png"));

            attackLeft1p = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/"+gender+"Fight/tile003.png"));
            attackLeft2p = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/"+gender+"Fight/tile004.png"));

            attackRight1p = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/"+gender+"Fight/tile005.png"));
            attackRight2p = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/"+gender+"Fight/tile006.png"));

            attackUp1p = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/"+gender+"Fight/tile007.png"));
            attackUp2p = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/"+gender+"Fight/tile008.png"));

            up0p = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile012.png"));
            up1p = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile013.png"));
            up2p = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile014.png"));
            up3p = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile015.png"));

            right0p = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile008.png"));
            right1p = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile009.png"));
            right2p= ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile010.png"));
            right3p = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile011.png"));

            left0p = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile004.png"));
            left1p = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile005.png"));
            left2p = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile006.png"));
            left3p = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile007.png"));
            
            down0p = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile000.png"));
            down1p = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile001.png"));
            down2p = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile002.png"));
            down3p = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/"+ gender +"/tile003.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void otherImage(){
        try{
            logoStudio = ImageIO.read(getClass().getResourceAsStream("/res/logo/for1tess.png"));
            girlswin = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/girl/girlswimer.png"));
            cubeChooser = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/cubeChooser.png"));
            oldman = ImageIO.read(getClass().getResourceAsStream("/res/skins/searcher/down0.png"));

            left0girl = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/girl/left0.png"));
            down0girl = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/girl/down0.png"));

            left0boy = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/boy/left0.png"));
            down0boy = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/boy/down0.png"));

            down0Panda = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/Panda/tile000.png"));
            down0Orc = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/Orc/tile000.png"));
            down0Avian = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/Avian/tile000.png"));
            none = ImageIO.read(getClass().getResourceAsStream("/res/skins/tribes/none.png"));

            blurMap = ImageIO.read(getClass().getResourceAsStream("/res/WorldMap/BlurMap.png"));
            map = ImageIO.read(getClass().getResourceAsStream("/res/WorldMap/WorldMap.png"));

            face0g = ImageIO.read(getClass().getResourceAsStream("/res/skins/player/girl/girlFace.png"));
            face0b= ImageIO.read(getClass().getResourceAsStream("/res/skins/player/boy/boyFace.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
