package com.app.entity;

import com.app.main.GamePanel;
import javax.imageio.ImageIO;
import java.io.IOException;

public class NPC_OldManForest extends Entity{
    int soundPassOldMan =0;

    public NPC_OldManForest(GamePanel gp){
        super(gp);
        direction = "stopU";
        type =1;
        speed = gp.player.speed;
        if(gp.currentState>= 33){
            getImage();
        }
        setDialogue();
        
    }

    public void getImage(){
        try{
            up0 = ImageIO.read(getClass().getResourceAsStream("/res/skins/searcher/up0.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/skins/searcher/up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/skins/searcher/up2.png"));
            up3 = ImageIO.read(getClass().getResourceAsStream("/res/skins/searcher/up3.png"));

            right0 = ImageIO.read(getClass().getResourceAsStream("/res/skins/searcher/right0.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/skins/searcher/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/skins/searcher/right2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/res/skins/searcher/right3.png"));

            left0 = ImageIO.read(getClass().getResourceAsStream("/res/skins/searcher/left0.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/skins/searcher/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/skins/searcher/left2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/res/skins/searcher/left3.png"));
            
            down0 = ImageIO.read(getClass().getResourceAsStream("/res/skins/searcher/down0.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/skins/searcher/down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/skins/searcher/down2.png"));
            down3 = ImageIO.read(getClass().getResourceAsStream("/res/skins/searcher/down3.png"));

            anim0 = ImageIO.read(getClass().getResourceAsStream("/res/skins/searcher/chut.png"));
            anim1 = ImageIO.read(getClass().getResourceAsStream("/res/skins/searcher/down0.png"));
            anim2 = ImageIO.read(getClass().getResourceAsStream("/res/skins/searcher/down0.png"));
            anim3 = ImageIO.read(getClass().getResourceAsStream("/res/skins/searcher/down0.png"));


        }catch(IOException e){
            e.printStackTrace();
        }
    }
    //MESSAGE
    public void setDialogue(){
            dialogues[0] = "The Zeno ecosystem is in danger";
            dialogues[1] = "Without the security core, Zeno users are at risk of serious problems.";
            dialogues[3] = "... ?";
    }

    public void speak(){
        super.speak();
    }

    public void setAction(){
        if(gp.currentState <34){
            solidArea.x = (gp.tileSize/4) - gp.tileSize/10+ gp.tileSize/4;
            solidArea.y = gp.tileSize/2 -gp.tileSize/7 -gp.tileSize/3;

            solidArea.width = gp.tileSize/4;
            solidArea.height = gp.tileSize +gp.tileSize;
            solidAreaDefaultX = solidArea.x;
            solidAreaDefaultY = solidArea.y;

            if(gp.currentState ==25 && onPath ==true){
                actionLockCounter++;
                if(actionLockCounter>=110){
                    gp.playSE(2);
                    getImage();
                    onPath =false;
                    actionLockCounter=0;
                }
            }
            else if(gp.currentState ==25 && onPath ==false){
                direction = "up";
                actionLockCounter++;
                if(actionLockCounter >= 50){
                    gp.player.direction = "down";
                    gp.player.orientation = 2;
                    gp.currentState =26;
                    actionLockCounter=0;
                }
            }
            else if(gp.currentState ==26){
                direction = "up";
                actionLockCounter++;
                if(actionLockCounter == 30){
                    gp.currentState =27;
                    actionLockCounter=0;
                }
            }
            else if(gp.currentState ==27){
                direction = "up";
                gp.player.speed =1;
                actionLockCounter++;
                if(actionLockCounter == 40){
                    gp.keyH.leftPressed = false;
                    gp.keyH.rightPressed = false;
                    if(gp.player.orientation == 3 && gp.currentState == 27){
                        gp.player.orientation = 4;
                        gp.player.direction = "right";
                        gp.currentState =28;
                        actionLockCounter=0;
                    }
                    if(gp.player.orientation == 4 && gp.currentState == 27){
                        gp.player.orientation = 3;
                        gp.player.direction = "left";
                        gp.currentState =28;
                        actionLockCounter=0;
                    }
                }
                else if(gp.player.worldX/gp.tileSize>= 54){
                    gp.keyH.leftPressed = true;
                    gp.player.orientation = 3;
                }
                else if(gp.player.worldX/gp.tileSize< 54){
                    gp.keyH.rightPressed = true;
                    gp.player.orientation = 4;
                }

            }
            else if(gp.currentState ==28){
                actionLockCounter++;
                direction= "up";
                if(actionLockCounter== 30){
                    if(gp.player.worldX/gp.tileSize <54){
                        direction= "right";
                    }
                    else if(gp.player.worldX/gp.tileSize >=54){
                        direction= "left";
                    }
                    gp.player.orientation = 1;
                    gp.player.direction = "up";
                    actionLockCounter=0;
                    gp.currentState =29;
                }
            }
            else if(gp.currentState ==29){
                if(gp.player.worldX/gp.tileSize <54){
                    actionLockCounter++;
                    if(actionLockCounter>= 5){
                        direction= "up";
                        actionLockCounter=0;
                        gp.currentState =30;
                    }
                }
                else if(gp.player.worldX/gp.tileSize >=54){
                    actionLockCounter++;
                    if(actionLockCounter>= 30){
                        direction= "up";
                        actionLockCounter=0;
                        gp.currentState =30;
                    }
                }
            }        
            else if(gp.currentState ==30){
                actionLockCounter++;
                if(actionLockCounter>= 170){
                    gp.playSE(2);
                    gp.player.orientation = 2;
                    gp.player.direction = "down";
                    actionLockCounter=0;
                    gp.currentState =31;
                    gp.aSetter.setNPC();
                }

            }
        }
        
        else if(gp.currentState >=34 && gp.currentState< 40){
            solidArea.x = (gp.tileSize/4) - gp.tileSize/10+ gp.tileSize/4;
            solidArea.y = gp.tileSize;

            solidArea.width = gp.tileSize/4;
            solidArea.height = gp.tileSize;
            solidAreaDefaultX = solidArea.x;
            solidAreaDefaultY = solidArea.y;

            if(gp.currentState ==34){
                direction = "stopU";
                gp.currentState = 35;
            }
            else if(gp.currentState ==35){
                gp.keyH.downPressed = false;
                gp.keyH.leftPressed = false;
                gp.keyH.rightPressed = false; 

                if(gp.keyH.upPressed == false){
                    gp.keyH.upPressed = true;
                }
                if(gp.player.worldY/gp.tileSize<= 20){
                    gp.gameState = gp.cinematicState;  
                    gp.keyH.upPressed = false;
                    gp.keyH.downPressed = false;
                    gp.keyH.leftPressed = false;
                    gp.keyH.rightPressed = false;
                    gp.currentState =36;
                }
            }
            else if(gp.currentState == 36){
                gp.keyH.leftPressed = true;
                if(gp.player.worldX/gp.tileSize<= 31){
                    gp.keyH.leftPressed = false;
                    gp.currentState =37;
                    gp.player.orientation = 1;
                    gp.player.direction = "up"; 
                }
            }
            else if(gp.currentState ==37){
                actionLockCounter++;
                if(actionLockCounter >=35){
                    actionLockCounter=0;
                    gp.keyH.upPressed = true;
                    direction = "stopD";
                    gp.currentState=38;
                }
            }
            else if(gp.currentState ==39){
                actionLockCounter++;
                direction = "up";
                if(actionLockCounter >=45){
                    actionLockCounter=0;
                    gp.gameState = gp.playState;
                    gp.aSetter.setNPC();
                    gp.currentState =40;
                    gp.aSetter.setObject();
                    gp.clueCounter =0;
                    gp.playSE(2);
                }
            }
        }
        
        else if(gp.currentState >=40){
            actionLockCounter++;
            if(actionLockCounter ==10 && gp.currentState ==40 && gp.clueCounter !=3){
                direction = "stopU";
                orientation =1; 
                actionLockCounter=0;
            }
            if(gp.clueCounter ==3){
                orientation =2; 
                direction = "stopD";
            }
        }
    }    
}
