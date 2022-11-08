package com.app.entity;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import com.app.main.GamePanel;
import java.awt.AlphaComposite;
import java.awt.Color;

public class Entity {
    public GamePanel gp;

    //STATE
    public double worldX, worldY;
    //invicible periode
    public boolean invincible = false;
    boolean attacking = false;
    int soundAttack =0;
    int soundAttackStopper =0;
    boolean hpBarOn = false;
    public boolean alive = true;
    public boolean dying = false;
    public boolean knockBack = false;

    //COUNTER
    int hpBarCounter = 0;
    public int invincibleCounter =0;
    public int spriteCounter = 0;
    int dyingCounter = 0;
    int knockBackCounter= 0;

    //CHARAC ATTRIBUTES
    public int type; // 0 PLAYER 1 NPC 2 MONSTER
    public String name ="";
    public int life = 100;
    public int maxLife = 100;
    public double speed;
    public double defaultSpeed;
    public String direction = "down";
    public int spriteNum = 1;
    public int orientation = 2; 
    // public String gender;

    //BUFFEREDIMAGE
    public BufferedImage down0,down1,down2,down3, left0,left1,left2,left3, right0,right1,right2,right3, up0,up1,up2,up3, face0,
    // others
    anim0,anim1,anim2,anim3, left0boy,left0girl,down0girl,down0boy,cubeChooser,oldman,girlswin ,logoStudio, blurMap, map,
    //Girl
    down0g,down1g,down2g,down3g, left0g,left1g,left2g,left3g, right0g,right1g,right2g,right3g, up0g,up1g,up2g,up3g, face0g,
    //Boy
    down0b,down1b,down2b,down3b, left0b,left1b,left2b,left3b, right0b,right1b,right2b,right3b, up0b,up1b,up2b,up3b, face0b,
    //Tribes
    down0Panda,down0Orc,down0Avian, none,
    //Panda
    down0p,down1p,down2p,down3p, left0p,left1p,left2p,left3p, right0p,right1p,right2p,right3p, up0p,up1p,up2p,up3p,
    //Orc
    down0o,down1o,down2o,down3o, left0o,left1o,left2o,left3o, right0o,right1o,right2o,right3o, up0o,up1o,up2o,up3o,
    //Avian
    down0a,down1a,down2a,down3a, left0a,left1a,left2a,left3a, right0a,right1a,right2a,right3a, up0a,up1a,up2a,up3a;
    public BufferedImage attackUp1,attackUp2 ,attackDown1,attackDown2, attackLeft1,attackLeft2, attackRight1,attackRight2,
    //Panda
    attackUp1p,attackUp2p ,attackDown1p,attackDown2p, attackLeft1p,attackLeft2p, attackRight1p,attackRight2p,
    //Orc
    attackUp1o,attackUp2o ,attackDown1o,attackDown2o, attackLeft1o,attackLeft2o, attackRight1o,attackRight2o,
    //Avian
    attackUp1a,attackUp2a ,attackDown1a,attackDown2a, attackLeft1a,attackLeft2a, attackRight1a,attackRight2a;

    //rectangle for NPC
    public Rectangle solidArea = new Rectangle();
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);

    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collision = false;    
    public int actionLockCounter = 0;
    public int preventLastMove = 0;
    String dialogues[] = new String[20];
    int dialoguesIndex = 0;
    int i =1;

    //PathFinding
    public boolean onPath = true; //pathfinding activation

    public Entity(GamePanel gp){
        
        this.gp = gp;
    }

    public void setAction(){}
    public void setDialogue(){}

    public void speak(){
        if(dialogues[dialoguesIndex] == null){
            gp.gameState = gp.gameStateSaver;
            dialoguesIndex =0;
        }
        
        switch(gp.player.direction){
            case"up":
                orientation = 2;
                direction = "stopD";
                break;

            case"down":
                orientation = 1;
                direction = "stopU";
                break;
            case"right":
                orientation = 3;
                direction = "stopL";
                break;
            case"left":
                orientation = 4;
                direction = "stopR";
                break;
        }

        gp.ui.currentDialogue = dialogues[dialoguesIndex];
        dialoguesIndex=dialoguesIndex+1;

        if(dialogues[dialoguesIndex] == null){
            gp.gameState = gp.gameStateSaver;
            dialoguesIndex =0;
        }
    }

    public void update(){
        
        solidArea.x = 0;
        solidArea.y = gp.tileSize/2;
        solidArea.width = gp.tileSize;
        solidArea.height = gp.tileSize +gp.tileSize/2;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y -gp.tileSize/2;
        
        checkCollision();
        
        if(knockBack == true){
            checkCollision();
            if(collision == true){
                    knockBackCounter =0;
                    knockBack = false;
                    speed = defaultSpeed;
            }
            
            else if(collision == false){
                switch(gp.player.direction){
                    case "up":
                    if(preventLastMove != 1){worldY -= speed; }
                    else{ direction = "stopU"; preventLastMove = 0; } orientation =1;
                break;
                case "down": 
                        if(preventLastMove != 2){worldY += speed;}
                        else{ direction = "stopD"; preventLastMove = 0; } orientation =2; 
                break;
                case "left": 
                        if(preventLastMove != 3){worldX -= speed;}
                        else{ direction = "stopL"; preventLastMove = 0; } orientation =3; 
                break;
                case "right": 
                        if(preventLastMove != 4){worldX += speed;}
                        else{ direction = "stopR"; preventLastMove = 0; } orientation =4; 
                break; 
                case "stopD": direction = "stopD"; break;
                case "animation": direction = "animation"; break;
                }
            }
            knockBackCounter++;
            //distance of knockBack
            if(knockBackCounter == 5){
                knockBackCounter =0;
                knockBack = false;
                speed = defaultSpeed;
            }
        }
        else{
            if(gp.gameState != gp.photoState){
                setAction();
            }
            if(gp.gameState == gp.photoState){
                direction = "stopD";
            }
            checkCollision();

            //IF COLLISION IS FALSE PLAYER CAN'T MOVE
            if(collision == false){
                switch(direction){
                    case "up":
                            // equal: playerY = playerSpeed - playerY; 
                            if(preventLastMove != 1){worldY -= speed; }
                            else{ direction = "stopU"; preventLastMove = 0; } orientation =1;
                    break;
                    case "down": 
                            if(preventLastMove != 2){worldY += speed;}
                            else{ direction = "stopD"; preventLastMove = 0; } orientation =2; 
                    break;
                    case "left": 
                            if(preventLastMove != 3){worldX -= speed;}
                            else{ direction = "stopL"; preventLastMove = 0; } orientation =3; 
                    break;
                    case "right": 
                            if(preventLastMove != 4){worldX += speed;}
                            else{ direction = "stopR"; preventLastMove = 0; } orientation =4; 
                    break; 
                    case "stopD": direction = "stopD"; break;
                    case "animation": direction = "animation"; break;
                }
            }
            else
            { 
                if(orientation == 1){
                    direction = "stopU"; spriteNum = 1; preventLastMove =1;
                }
                if(orientation == 2){
                    direction = "stopD"; spriteNum = 1; preventLastMove = 2;
                }
                if(orientation == 3){
                    direction = "stopL"; spriteNum = 1; preventLastMove =3;
                }
                if(orientation == 4){
                    direction = "stopR"; spriteNum = 1; preventLastMove = 4;
                }
            }
        }

        spriteCounter ++;
        if(spriteCounter > 9){
            spriteNum++;
            if(spriteNum == 5){
                spriteNum=1;
            }
            spriteCounter = 0;
        }

        //allow MONSTER to be invincible
        if(invincible == true){
            invincibleCounter++;
            if(invincibleCounter > 40){
                invincible = false;
                invincibleCounter=0;
            }
        }
    }

    public void checkCollision(){
        collision = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        //we check if collision or not with player
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        //if monster tuch player then player lose pv
        if(this.type == 2 && contactPlayer == true){
            if(gp.player.invincible == false){
                //damage
                if(gp.currentState< 24 && gp.playerPV > 10){
                    gp.playerPV-=5;
                }
                else if(gp.currentState >= 24){
                    gp.playerPV-= (5+gp.maxXP/20);
                }

                gp.playSE(16);
                gp.player.invincible = true;
            }
        }
    }

    public void draw(Graphics2D g2){

        int rightOffset = gp.screenWidth - gp.player.screenX;
        int bottomOffset = gp.screenHeight - gp.player.screenY;
    
        if(worldX + gp.tileSize + gp.tileSize + gp.tileSize> gp.player.worldX - gp.player.screenX && 
        worldX - gp.tileSize - gp.tileSize - gp.tileSize< gp.player.worldX + gp.player.screenX && 
        worldY + gp.tileSize + gp.tileSize + gp.tileSize> gp.player.worldY - gp.player.screenY &&
        worldY - gp.tileSize - gp.tileSize - gp.tileSize< gp.player.worldY + gp.player.screenY 
          ){
            drawingEntity(g2);
        }else if(gp.player.screenX > gp.player.worldX ||
        gp.player.screenY > gp.player.worldY ||
        rightOffset > gp.worldWidth - gp.player.worldX||
        bottomOffset > gp.worldHeight - gp.player.worldY){
            drawingEntity(g2);
        }
    }

    public void drawingEntity(Graphics2D g2){
        
        BufferedImage image = null;
        double screenX = worldX - gp.player.worldX + gp.player.screenX;
        double screenY = worldY - gp.player.worldY + gp.player.screenY;

        //Screen if border map
        double x =screenX;
        double y=screenY;
        if(gp.gameState != gp.photoState){
            if(screenX > worldX){
                x = (int)worldX;
            }
            if(screenY > worldY){
                y = (int)worldY;
            }
            //right
            double rightOffset = gp.screenWidth - screenX;
            if(rightOffset > gp.worldWidth - worldX){
                x = gp.screenWidth - (gp.worldWidth - (int)worldX);
            }
            double bottomOffset = gp.screenHeight - screenY;
            if(bottomOffset > gp.worldHeight - worldY){
                y = gp.screenHeight - (gp.worldHeight - (int)worldY);
            }
        }

        switch(direction){
            case "up":
            if(spriteNum == 1){image = up0;}
            if(spriteNum == 2){image = up1;}
            if(spriteNum == 3){image = up2;}
            if(spriteNum == 4){image = up3;} break;

            case "down":
            if(spriteNum == 1){image = down0;}
            if(spriteNum == 2){image = down1;}
            if(spriteNum == 3){image = down2;}
            if(spriteNum == 4){image = down3;} break;

            case "left":
            if(spriteNum == 1){image = left0;}
            if(spriteNum == 2){image = left1;}
            if(spriteNum == 3){image = left2;}
            if(spriteNum == 4){image = left3;} break;

            case "right":
            if(spriteNum == 1){image = right0;}
            if(spriteNum == 2){image = right1;}
            if(spriteNum == 3){image = right2;}
            if(spriteNum == 4){image = right3;} break;
            //Animation
            case "animation":
            if(spriteNum == 1){image = anim0;}
            if(spriteNum == 2){image = anim1;}
            if(spriteNum == 3){image = anim2;}
            if(spriteNum == 4){image = anim3;}
            if(spriteNum == 5){image = anim3;} break;

            //stop left right up down
            case "stopD": image = down0; break;
            case "stopU": image = up0; break;
            case "stopL": image = left0; break;
            case "stopR": image = right0; break;
        }
        
        //Monster hp bar
        if(type == 2 && hpBarOn == true){
            double oneScale = (double)gp.tileSize/maxLife;
            double hpBarValue = oneScale * life;

            g2.setColor(new Color(83,83,83));
            g2.fillRect((int)x-1, (int)y - gp.tileSize/2 -1, gp.tileSize +2, gp.tileSize/4 +2);

            g2.setColor(new Color(255,0,30));
            g2.fillRect((int)x, (int)y - gp.tileSize/2, (int)hpBarValue, gp.tileSize/4);

            hpBarCounter++;
            if(hpBarCounter > 600){
                hpBarCounter = 0;
                hpBarOn = false;
            }
        }

        if(invincible == true){
            hpBarOn = true;
            hpBarCounter = 0;
            changeAlpha(g2,0.4f);
        }
        if(dying == true){
            dyingAnimation(g2);
        }

        int mult = 2;
        //we make the character grow and we also adapt his position
        g2.drawImage(image, (int)x-gp.tileSize/mult, (int)y-gp.tileSize/mult, gp.tileSize*mult, gp.tileSize*mult, null);
        changeAlpha(g2,1f);

        if(gp.debug==1){
            //rectangle
            g2.setColor(Color.red);
            g2.drawRect((int)x + solidArea.x , (int)y + solidArea.y , solidArea.width, solidArea.height);
        }
    }

    public void dyingAnimation(Graphics2D g2){
        dyingCounter++;
        int i =5;
        if(dyingCounter <=i){changeAlpha(g2,0f);}
        if(dyingCounter > i && dyingCounter<=i*2 ){changeAlpha(g2,1f);}
        if(dyingCounter > i*2 && dyingCounter<=i*3 ){changeAlpha(g2,0f);}
        if(dyingCounter > i*3 && dyingCounter<=i*4 ){changeAlpha(g2,1f);}
        if(dyingCounter > i*4 && dyingCounter<=i*5 ){changeAlpha(g2,0f);}
        if(dyingCounter > i*5 && dyingCounter<=i*6 ){changeAlpha(g2,1f);}
        if(dyingCounter > i*6 && dyingCounter<=i*7 ){changeAlpha(g2,0f);}
        if(dyingCounter > i*7 && dyingCounter<=i*8 ){changeAlpha(g2,1f);}
        if(dyingCounter >40 ){
            dying = false;
            alive = false;
            // dyingCounter=0;
        }
    }

    //change opacity
    public void changeAlpha(Graphics2D g2, float alphaValue){
    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    //pathfinding
    public void searchPath(int goalCol, int goalRow){
        double startCol = (worldX +solidArea.x)/gp.tileSize;
        double startRow = (worldY +solidArea.y)/gp.tileSize;

        gp.pFinder.setNodes((int)startCol, (int)startRow, goalCol, goalRow, this);
        if(gp.pFinder.search() == true){

            //Next worldX & worldY
            double nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            double nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

            //Entity's solidArea position
            double enLeftX =(int)worldX +solidArea.x;
            double enRightX =(int)worldX +solidArea.x + solidArea.width;
            double enTopY = (int)worldY +solidArea.y;
            double enBottomY =(int)worldY +solidArea.y + solidArea.height;
            if(enTopY >= nextY && enBottomY < nextY +solidArea.height+1){
                //left or right
                if(enLeftX > nextX){
                    direction = "left";
                    // System.out.println("C");
                }
                if(enLeftX < nextX){
                    direction = "right";
                    // System.out.println("D");
                }
            }
            else if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX +solidArea.height+1){
                direction = "up";
                // System.out.println("A" + collision);
            }
            else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX +solidArea.height+1){
                direction = "down";
                // System.out.println("B");
            }
             else if(enTopY> nextY && enLeftX > nextX){
                 //up or left
                 direction = "up";
                //  System.out.println("1");
                 checkCollision();
                 if(collision == true){
                    //  System.out.println("2");
                     direction = "left";
                 }             
             }else if(enTopY> nextY && enLeftX < nextX){
                //  System.out.println("3");
                 //up or right
                 direction ="up";
                 checkCollision();
                 if(collision == true){
                    //  System.out.println("4");
                     direction = "right";
                 }   
             }else if(enTopY< nextY && enLeftX > nextX){
                 //down or left
                 direction = "down";
                 checkCollision();                   
                //  System.out.println("5");
                 if(collision == true){
                     direction = "left";
                    //  System.out.println("6");

                 }
             }else if(enTopY< nextY && enLeftX < nextX){
                 //down or right
                 direction = "down";
                //  System.out.println("7");

                 checkCollision();
                 if(collision == true){
                    //  System.out.println("8");
                     direction = "right";
                 }
             }
            // checkCollision();
            int nextCol = gp.pFinder.pathList.get(0).col;
            int nextRow = gp.pFinder.pathList.get(0).row;

            if(nextCol == goalCol && nextRow == goalRow){
                onPath = false;
            }
        }
    }
}
