///UI
package com.app.main;

import java.awt.Color;

import javax.imageio.ImageIO;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.io.InputStream;
import java.awt.image.BufferedImage;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class UI {
    
    public BufferedImage backgroundImage;
    GamePanel gp;
    Graphics2D g2;
    Font maruMonica;
    // arial_80b;
    public String currentDialogue = "";
    //TO KNOW WICH SELECTED ON MAIN MENU
    public int commandNum = 0;
    //INITIATE SCREEN LOGO
    public int commandStep = 2;
    public int creationPerso =0;
    public int currentChatStep = 0;
    public int pageCreditNumber =0;

    public int photoInstruction = 0;
    public int movInstruction = 0;
    public int alterteMessage =0;
    public int alerteCounter =0;
    public int ValuYesNo =-1;
    public int mapAnimation = 0; //1 map animation unable else 0 nothing happen
    public int mapCounter = 0;
    int mapAnimationCounter = 0;

    int stopper=0;
    public UI(GamePanel gp){
        this.gp = gp;
        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/res/tiles/backgroundImage/Pvh.gif"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            InputStream is = getClass().getResourceAsStream("/res/fonts/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            
        } catch (Exception e) {}
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.setColor(Color.white);

        //TITLE STATE
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }
        //PLAY STATE
        if(gp.gameState == gp.playState || gp.gameState == gp.fightState){
            ATH();
            Quest();
            
            if(gp.KeyHelper ==1){
                drawInstruction();
            }
            if(alterteMessage ==1){
                String texte;
                texte = "Camera mode is not available here";
                alerteMessage(texte);
            }
        }
        //PAUSE STATE
        if(gp.gameState == gp.pauseState){
            drawPauseScreen();
        }
        //PHOTO STATE
        if(gp.gameState == gp.photoState){
            if(gp.KeyHelper ==1){
                drawInstruction();
            }
        }
        //DIALOGUE STATE
        if(gp.gameState == gp.dialogueState){
            drawDialogueScreen();
        }
        //CINEMATIC STATE
        if(gp.gameState == gp.cinematicState){
            if(gp.currentState == 12 && gp.ui.currentChatStep == 6){
                if(gp.currentState==12){
                    drawTribeSelector();
                }
            }
            if(gp.BattleStep ==1 || gp.BattleStep ==3){
                BattleInterface();
            }
        }
        //GAME OVER STATE
        if(gp.gameState == gp.gameOverState){
            gameOverScreen();
        }
    }
    
    public void alerteMessage(String text){
        //alerte message
        int x = getXforCenteredText(text);
        int y= gp.screenHeight - gp.tileSize*3;
        setFontBold(g2, 36F);
        Color c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.drawString(text, x, y);
        alerteCounter++;
        if(alerteCounter >100){
            alterteMessage =0;
            alerteCounter=0;
        }
    }

    public void gameOverScreen(){
        //background
        Color c = new Color(0, 0, 0, 200);
        g2.setColor(c);
        g2.fillRect(0 , 0, gp.screenWidth, gp.screenHeight);

        setFontBold(g2, 96F);
        int x;
        int y;
        //title
        String text = "YOU DIED";
        x = getXforCenteredText(text);
        y = gp.tileSize * 6;
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        setFontPlain(g2, 40F);
        text = "Respawn";
        x = getXforCenteredText(text);
        y += gp.tileSize*3;
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "Disconect";
        x = getXforCenteredText(text);
        y += gp.tileSize +25;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">", x-gp.tileSize, y);
        }

        setFontPlain(g2, 30F);
        text = "Press enter";
        x = 30;
        y = gp.screenHeight-gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">", x-gp.tileSize, y);
        }

    }

    public void Quest(){
        int x;
        int y;
        String text = "";
        x= 27;
        y = gp.tileSize*3 +7;

        Color c = new Color(255, 255, 255);
        g2.setColor(c);
        setFontPlain(g2, 19F);

        if(gp.currentState == 5 || gp.currentState == 6 || gp.currentState == 24 || gp.currentState == 20 || gp.currentState ==40){
            text = "Quest:";
            g2.drawString(text, x, y);
        
            x= gp.tileSize;
            y = gp.tileSize*3 +40;

            setFontPlain(g2, 28F);

            if(gp.currentState == 5){
                text = "Find Karlos' bag 0/1 ";
                g2.drawString(text, x, y);
            }
            else if(gp.currentState == 6){
                text = "Find Karlos' bag 1/1 ";
                g2.drawString(text, x, y);
                y = gp.tileSize*3 +70;
                text = "Bring the bag to the laboratory";
                g2.drawString(text, x, y);
            }
            else if(gp.currentState == 20 && gp.currentMap == 6){
                text = "Find your enemy and kill it ! ";
                g2.drawString(text, x, y);

                text = "Press 'E' to attack";
                g2.drawString(text, x, y+45);
            }
            else if(gp.currentState == 24 && gp.currentMap != 0 && gp.currentMap != 7 && gp.level ==1){
                text = "Go to your room to practice";
                g2.drawString(text, x, y);
            }
            //Later put a pnj who introduces the training area AND allows us to leave the area if we talk to him and on the pc have the choice between training and save pos
            else if(gp.currentState == 24 && gp.currentMap == 0 && gp.level ==1){
                text = "Sit on your chair and click on 'E' ";
                g2.drawString(text, x, y);
            }
            else if(gp.currentState == 24 && gp.currentMap == 7 && gp.level ==1){
                text = "Train to pass level 2";
                g2.drawString(text, x, y);
            }
            else if(gp.currentState == 24 && gp.currentMap == 7 && gp.level >=2){
                text = "good job !";
                g2.drawString(text, x, y);
                text = "return to the cross to disconect from the virtual world";
                g2.drawString(text, x, y+45);
            }
            else if(gp.currentState == 24 && gp.currentMap != 7 && gp.level >=2){
                text = "You can now leave Calmeria";
                g2.drawString(text, x, y);
            }
            else if(gp.currentState == 40 && gp.clueCounter ==0){
                text = "Try to find clues 0/3";
                g2.drawString(text, x, y);
            }
            else if(gp.currentState == 40 && gp.clueCounter ==1){
                text = "Try to find clues 1/3";
                g2.drawString(text, x, y);
            }
            else if(gp.currentState == 40 && gp.clueCounter ==2){
                text = "Try to find clues 2/3";
                g2.drawString(text, x, y);
            }
            else if(gp.currentState == 40 && gp.clueCounter >=3){
                text = "Try to find clues 3/3";
                g2.drawString(text, x, y);
                text = "Bring the clues to the professor";
                g2.drawString(text, x, y+45);
            }
        }
    }
    
    public void drawInstruction(){
        int x;
        int y;
        String text = "";
        x= gp.tileSize;

        Color c = new Color(255, 255, 255);
        g2.setColor(c);

        if(photoInstruction!=1 && gp.gameState == gp.photoState){
            setFontBold(g2, 28F);
            text = "Zoom IN";
            y = gp.tileSize;
            g2.drawString(text, x, y);
            
            setFontPlain(g2, 28F);
            text = "press: Z";
            y += gp.tileSize/2;
            g2.drawString(text, x, y);
    
            setFontBold(g2, 28F);
            text = "Zoom OUT";
            y += gp.tileSize*1;
            g2.drawString(text, x, y);
    
            setFontPlain(g2, 28F);
            text = "press: S";
            y += gp.tileSize/2;
            g2.drawString(text, x, y);
        }

        if(movInstruction !=1 && gp.gameState == gp.playState){
            setFontBold(g2, 28F);
            text = "UP";
            y = gp.tileSize*4;
            g2.drawString(text, x, y);
            
            setFontPlain(g2, 28F);
            text = "press: Z";
            y += gp.tileSize/2;
            g2.drawString(text, x, y);
    //
            setFontBold(g2, 28F);
            text = "DOWN";
            y += gp.tileSize*1;
            g2.drawString(text, x, y);
    
            setFontPlain(g2, 28F);
            text = "press: S";
            y += gp.tileSize/2;
            g2.drawString(text, x, y);
    //
            setFontBold(g2, 28F);
            text = "LEFT";
            y += gp.tileSize*1;
            g2.drawString(text, x, y);

            setFontPlain(g2, 28F);
            text = "press: Q";
            y += gp.tileSize/2;
            g2.drawString(text, x, y);
    //
            setFontBold(g2, 28F);
            text = "RIGHT";
            y += gp.tileSize*1;
            g2.drawString(text, x, y);

            setFontPlain(g2, 28F);
            text = "press: D";
            y += gp.tileSize/2;
            g2.drawString(text, x, y);
    //
            setFontBold(g2, 28F);
            text = "MENU & BACK";
            y += gp.tileSize*1;
            g2.drawString(text, x, y);

            setFontPlain(g2, 28F);
            text = "press: ESC";
            y += gp.tileSize/2;
            g2.drawString(text, x, y);
    //
            setFontBold(g2, 28F);
            text = "MAP";
            y += gp.tileSize*1;
            g2.drawString(text, x, y);

            setFontPlain(g2, 28F);
            text = "press: M";
            y += gp.tileSize/2;
            g2.drawString(text, x, y);
    //
            setFontBold(g2, 28F);
            text = "PHOTO mode";
            y += gp.tileSize*1;
            g2.drawString(text, x, y);

            setFontPlain(g2, 28F);
            text = "press: U";
            y += gp.tileSize/2;
            g2.drawString(text, x, y);
        }
    }

    public void BattleInterface(){
        String text = ""; 
        Color c = new Color(0, 0, 0);
        g2.setColor(c);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        
        c = new Color(255, 255, 255);
        setFontBold(g2, 50F);
        g2.setColor(c);
        if(gp.BattleStep ==1){
            text = "Fight !"; 
        }
        if(gp.BattleStep ==3){
            text = "Victory"; 
        }            
        int x= getXforCenteredText(text);
        g2.drawString(text, x, gp.screenHeight/2 -gp.tileSize);
    }

    public void ATH(){
        int x,y;
        String text = ""; 

         //Ath skin
         //background
         Color c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.fillRect(gp.tileSize/2 , gp.tileSize/2, gp.tileSize*7, gp.tileSize*2);


        c = new Color(255, 255, 255, 150);
        g2.setColor(c);
        g2.fillRect(gp.tileSize*7 , gp.tileSize/2, gp.tileSize, gp.tileSize*2);

        c = new Color(255, 255, 255, 100);
        g2.setColor(c);
        g2.fillRect(gp.tileSize*7 +gp.tileSize , gp.tileSize/2, gp.tileSize/3, gp.tileSize*2);

        c = new Color(255, 255, 255, 50);
        g2.setColor(c);
        g2.fillRect(gp.tileSize*7 +gp.tileSize + gp.tileSize/3, gp.tileSize/2, gp.tileSize/4, gp.tileSize*2);
        
        g2.setColor(new Color(32,32,32));
        g2.fillRoundRect(gp.tileSize/2 -5 , gp.tileSize/2 -5 , gp.tileSize*2 +10, gp.tileSize*2 +10, 25, 25);
        g2.fillRect(gp.tileSize/2 -5 , gp.tileSize/2 -5 , gp.tileSize*2 +10, gp.tileSize*2 +10);

         g2.setColor(new Color(200,200,200));
        // g2.setStroke(new BasicStroke(3));
         g2.fillRoundRect(gp.tileSize/2 , gp.tileSize/2  , gp.tileSize*2 , gp.tileSize*2,5,5);

        //player name
        c = new Color(0, 0, 0);
        g2.setColor(c);

        setFontPlain(g2, 20F);
        text = gp.playerName; //player name
        x= gp.tileSize*3;
        y = gp.tileSize;
        g2.drawString(text, x, y);
        
        // pv
        setFontPlain(g2, 10F);
        text = " "+gp.playerPV+"/"+gp.maxPV;
        g2.drawString(text, x, y+gp.tileSize/2 +8);
        //hp bar
        double oneScalePV = (double)gp.tileSize/gp.maxPV;
        double hpBarValue = oneScalePV * gp.playerPV;

        g2.setColor(new Color(83,83,83));
        g2.fillRect(x-1, y+9, gp.tileSize*4 +2, gp.tileSize/4 +2);
        
        g2.setColor(new Color(218,0,0));
        g2.fillRect(x , y +10 , (int)hpBarValue*4, gp.tileSize/4 );
        
        //xp bar
        g2.setColor(new Color(255,255,255));
        setFontPlain(g2, 14F);
        text = "XP: "+(int)gp.playerXP+"/"+(int)gp.maxXP;
        g2.drawString(text, 5, gp.screenHeight-gp.tileSize/2 +5);
        //hp bar
        double oneScaleXP = (double)gp.screenWidth/gp.maxXP;
        double xpBarValue = oneScaleXP * gp.playerXP;
        //back xp bar
        g2.setColor(new Color(83,83,83));
        g2.fillRect(0, gp.screenHeight-gp.tileSize/6, gp.screenWidth, gp.tileSize/6 +2);   
        //front xp bar
        g2.setColor(new Color(112,0,205));
        g2.fillRect(0 , gp.screenHeight-gp.tileSize/6 +1 , (int)xpBarValue, gp.tileSize/6 );

         //player tribes and lvl
        if(gp.tribeValue == 0){
            g2.drawImage(gp.player.none, gp.tileSize/2     ,gp.tileSize/2-gp.tileSize/4   ,   gp.tileSize*2     , gp.tileSize*2 , null);   

            c = new Color(0, 0, 0);
            g2.setColor(c);
    
            setFontPlain(g2, 20F);
            text = "LvL. ?";
            x= gp.tileSize*5;
            y = gp.tileSize;
            g2.drawString(text, x, y);
        }
        if(gp.tribeValue != 0){
            if(gp.tribeValue == 1){
                g2.drawImage(gp.player.down0Orc, gp.tileSize/2     ,gp.tileSize/2-gp.tileSize/4    ,   gp.tileSize*2     , gp.tileSize*2 , null);
                
                c = new Color(0, 0, 0);
                g2.setColor(c);
        
                setFontPlain(g2, 20F);
                text = "LvL." + gp.level;//gp.respawnCount
                x= gp.tileSize*5;
                y = gp.tileSize;
                g2.drawString(text, x, y);
            }
            if(gp.tribeValue == 2){
                g2.drawImage(gp.player.down0Panda, gp.tileSize/2     ,gp.tileSize/2-gp.tileSize/4    ,   gp.tileSize*2     , gp.tileSize*2 , null);   
    
                c = new Color(0, 0, 0);
                g2.setColor(c);
    
                setFontPlain(g2, 20F);
                text = "LvL." + gp.level;
                x= gp.tileSize*5;
                y = gp.tileSize;
                g2.drawString(text, x, y);
            }
            if(gp.tribeValue == 3){
                g2.drawImage(gp.player.down0Avian, gp.tileSize/2     ,gp.tileSize/2-gp.tileSize/4    ,   gp.tileSize*2     , gp.tileSize*2 , null);   
    
                c = new Color(0, 0, 0);
                g2.setColor(c);
        
                setFontPlain(g2,20F);
                text = "LvL." + gp.level;
                x= gp.tileSize*5;
                y = gp.tileSize;
                g2.drawString(text, x, y);
            }
        }
    }

    public void drawTitleScreen(){
        int x,y;
        String text = "";
        gp.currentMap = 2;
        gp.player.worldX = gp.tileSize * 23;
        gp.player.worldY =gp.tileSize * 41;
        //command step: 2 logo, 1 perso creat, 0, main menu, credit 3, 4 settings, 5 key handler
        //Logo
        if(commandStep==2){
            g2.setColor(new Color(255,255,255));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            g2.drawImage(gp.player.logoStudio, gp.screenWidth/2 -gp.tileSize*8, gp.screenHeight/2-gp.tileSize*8, gp.tileSize*16,gp.tileSize*16, null);   
        }

        else if(commandStep==1){
            // Charac creation
            g2.setColor(new Color(0,0,0));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            if(gp.saveSet ==1 && ValuYesNo == -1){
                final JDialog dialog = new JDialog();
                dialog.setAlwaysOnTop(true);    
                ValuYesNo = JOptionPane.showConfirmDialog(dialog,"Warning, you are about to overwrite your previous save." ,null, JOptionPane.YES_NO_OPTION);
                
                //if press yes and was a save then we play music of creation
                if(ValuYesNo ==0){
                    if(gp.musicSetter == 1 && stopper ==0){
                        gp.stopMusic();
                            stopper=1;
                    }
                    if(gp.musicSetter==0){
                        gp.playMusic(8);
                    }
                }
                //if no, we go back
                if(ValuYesNo ==1){
                    ValuYesNo =-1;
                    commandStep =0;
                }
            }

            if(currentChatStep<5){
                ESC();
            }

            drawSubWindow(gp.tileSize*2, gp.tileSize*2, gp.screenWidth - (gp.tileSize*4), gp.tileSize*4);
            drawDialogueScreen();



            g2.setColor(new Color(0,0,0));
            x = gp.tileSize*2;
            y=gp.tileSize*2;

            x += gp.tileSize;
            y+= gp.tileSize;
            if(currentChatStep==1){
                
                text = "...";
                //play music directly if no save
                if(gp.musicSetter == 1 && stopper ==0 && gp.saveSet ==0){
                    gp.stopMusic();
                    stopper=1;
                }
                if(gp.musicSetter==0 && gp.saveSet ==0){
                    gp.playMusic(8);
                }

            }
            if(currentChatStep==2){
                text = "Hmmm...";
            }

            if(currentChatStep==3){
                text = "Where is it...";
            }
            if(currentChatStep==4){
                text = "Ah !";

            }
            if(currentChatStep==5){
                text = "Hello ! \n Sorry to keep you waiting !";
                g2.drawImage(gp.player.oldman, gp.screenWidth/2+gp.tileSize*3, gp.screenHeight/2-gp.tileSize*7, gp.tileSize*7,gp.tileSize*7, null);   
                if(gp.musicSetter == 1 && stopper ==1){
                    gp.stopMusic();
                    stopper=0;
                }
                if(gp.musicSetter==0){
                    gp.playMusic(7);
                }
            }
            if(currentChatStep==6){
                text = "Welcome to the world of Happenst1ce !";
                g2.drawImage(gp.player.oldman, gp.screenWidth/2+gp.tileSize*3, gp.screenHeight/2-gp.tileSize*7, gp.tileSize*7,gp.tileSize*7, null);   

            }
            if(currentChatStep==7){
                text = "I am Professor Karlos and I am happy today to welcome \n you in my world ! ";
                g2.drawImage(gp.player.oldman, gp.screenWidth/2+gp.tileSize*3, gp.screenHeight/2-gp.tileSize*7, gp.tileSize*7,gp.tileSize*7, null);   

            }
            if(currentChatStep==8){
                text = "In this world the virtual reality is more and more highlighted. \n VR allows us to travel, fight, \n train our virtual charactere and much more...";
                g2.drawImage(gp.player.oldman, gp.screenWidth/2+gp.tileSize*3, gp.screenHeight/2-gp.tileSize*7, gp.tileSize*7,gp.tileSize*7, null);   

            }
            if(currentChatStep==9){
                text = "Before going any further I will need some information.";
                g2.drawImage(gp.player.oldman, gp.screenWidth/2+gp.tileSize*3, gp.screenHeight/2-gp.tileSize*7, gp.tileSize*7,gp.tileSize*7, null);   

            }
            if(currentChatStep==10){
                text = "First I let you choose your gender";
                if(gp.genderValue==1){
                    g2.drawImage(gp.player.left0boy, x, y , gp.tileSize*2, gp.tileSize*2, null);
                    g2.drawImage(gp.player.down0boy, x+gp.tileSize, y , gp.tileSize*2, gp.tileSize*2, null);   
                    g2.drawImage(gp.player.cubeChooser, gp.screenWidth/2 -(int)g2.getFontMetrics().getStringBounds(text, g2).getWidth()/2 -3*gp.tileSize, gp.screenHeight/2-2*gp.tileSize, gp.tileSize*7, gp.tileSize*7, null);   
                    gp.genderValue=1;
                }       

                if(gp.genderValue==2){
                    g2.drawImage(gp.player.left0girl, x, y , gp.tileSize*2, gp.tileSize*2, null);
                    g2.drawImage(gp.player.down0girl, x+gp.tileSize, y , gp.tileSize*2, gp.tileSize*2, null);   
                    g2.drawImage(gp.player.cubeChooser, gp.screenWidth/2 -(int)g2.getFontMetrics().getStringBounds(text, g2).getWidth()/2 +5*gp.tileSize, gp.screenHeight/2-2*gp.tileSize, gp.tileSize*7,gp.tileSize*7, null);   
                    gp.genderValue=2;
                }
                g2.drawImage(gp.player.down0boy, gp.screenWidth/2 -(int)g2.getFontMetrics().getStringBounds(text, g2).getWidth()/2 -3*gp.tileSize, gp.screenHeight/2-2*gp.tileSize, gp.tileSize*7, gp.tileSize*7, null);   
                g2.drawImage(gp.player.down0girl, gp.screenWidth/2 -(int)g2.getFontMetrics().getStringBounds(text, g2).getWidth()/2 +5*gp.tileSize, gp.screenHeight/2-2*gp.tileSize, gp.tileSize*7,gp.tileSize*7, null);   
            }
            if(currentChatStep==11){
                text = "Great. Then what's your name ?";
                g2.drawImage(gp.player.oldman, gp.screenWidth/2+gp.tileSize*3, gp.screenHeight/2-gp.tileSize*7, gp.tileSize*7,gp.tileSize*7, null);   

            }
            if(currentChatStep==12){
                text = "Great. Then what's your name ?";
                g2.drawImage(gp.player.oldman, gp.screenWidth/2+gp.tileSize*3, gp.screenHeight/2-gp.tileSize*7, gp.tileSize*7,gp.tileSize*7, null);   

                //player NAME
                if(gp.nameStep == 0){
                    gp.nameStep =1;
                    final JDialog dialog = new JDialog();
                    dialog.setAlwaysOnTop(true);    
                    gp.playerName = JOptionPane.showInputDialog(dialog,"enter your name");
                }
            }
            if(currentChatStep==13){
                // text = gp.playerName+ ", Stunning !";
                text = "Nice to meet you "+ gp.playerName+" !";
                g2.drawImage(gp.player.oldman, gp.screenWidth/2+gp.tileSize*3, gp.screenHeight/2-gp.tileSize*7, gp.tileSize*7,gp.tileSize*7, null);   

            }
            if(currentChatStep==14){
                text = "Proving your worth in this world is the key to success.";
                g2.drawImage(gp.player.oldman, gp.screenWidth/2+gp.tileSize*3, gp.screenHeight/2-gp.tileSize*7, gp.tileSize*7,gp.tileSize*7, null);   

            }
            if(currentChatStep==15){
                text = "I am waiting for you at my laboratory.";
                g2.drawImage(gp.player.oldman, gp.screenWidth/2+gp.tileSize*3, gp.screenHeight/2-gp.tileSize*7, gp.tileSize*7,gp.tileSize*7, null);   

            }
            if(currentChatStep==16){
                text = "Good luck !";
                g2.drawImage(gp.player.oldman, gp.screenWidth/2+gp.tileSize*3, gp.screenHeight/2-gp.tileSize*7, gp.tileSize*7,gp.tileSize*7, null);   
            }
            for(String line: text.split(" \n ")){
                g2.drawString(line, x, y);
                y+= 40;
            }
        }

        //CREDIT
        else if(commandStep==3){
            credits();
        }

        //key
        else if(commandStep==5){
            keys();
        }

        //OPTION
        else if(commandStep==4){
            settings();
        }
        //MAIN MENU
        else if(commandStep==0){
            currentChatStep=0;
            //TITLE NAME
            setFontBold(g2, 96F);

            text = "Happenst1ce";
            x = getXforCenteredText(text);
            y = gp.tileSize * 5;

            //SHADOW COLOR
            g2.setColor(Color.gray);
            g2.drawString(text, x+5, y+5);
            //TEXT COLOR
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            //MAIN CHARAC
            x = gp.screenWidth/2+gp.tileSize*4;
            y +=gp.screenHeight/2 - gp.screenHeight/4 -gp.tileSize;
            g2.drawImage(gp.player.left0b, x, y , gp.tileSize*2, gp.tileSize*2, null);

            x = gp.screenWidth/2+gp.tileSize*4;
            y +=gp.screenHeight/2 - gp.screenHeight/4 -gp.tileSize;
            g2.drawImage(gp.player.girlswin, x -gp.tileSize*11, y-gp.tileSize , gp.tileSize*2, gp.tileSize*2, null);

            //MENU
            setFontBold(g2, 38F);

            text = "NEW GAME";
            x= getXforCenteredText(text);
            y = gp.tileSize*8+ gp.tileSize/2 + gp.tileSize/4 -gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">", x-gp.tileSize, y);
            }
            
            if(gp.saveSet==1){
                g2.setColor(Color.white);
            }
            if(gp.saveSet==0){
                g2.setColor(Color.gray);
            }
            text = "LOAD GAME";
            x= getXforCenteredText(text);
            y += gp.tileSize*1+gp.tileSize/2;
            g2.drawString(text, x, y);

            g2.setColor(Color.white);
            if(commandNum == 1){
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "SETTINGS";
            x= getXforCenteredText(text);
            y += gp.tileSize*1 +gp.tileSize/2;
            g2.drawString(text, x, y);
            if(commandNum == 2){
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "CREDITS";
            x= getXforCenteredText(text);
            y += gp.tileSize*1 +gp.tileSize/2;
            g2.drawString(text, x, y);
            if(commandNum == 3){
            g2.drawString(">", x-gp.tileSize, y);
            }

            text = "EXIT";
            x= getXforCenteredText(text);
            y += gp.tileSize*1 +gp.tileSize/2;
            g2.drawString(text, x, y);
            if(commandNum == 4){
                g2.drawString(">", x-gp.tileSize, y);
            }

            basicKey();
        }
    }

    public void settings(){
        gp.config.saveConfig();

        //background
        Color c = new Color(0, 0, 0);
        g2.setColor(c);
        g2.fillRect(0 , 0, gp.screenWidth, gp.screenHeight);

        setFontBold(g2, 66F);
        String text = "";
        int x;
        int y;
        int o;
        //SHADOW COLOR
        g2.setColor(Color.gray);
        text = "SETTINGS";
        x = getXforCenteredText(text);
        y = gp.tileSize * 4;
        o = x+gp.tileSize*4+gp.tileSize/2;

        g2.drawString(text, x+5, y+5);
        //TEXT COLOR
        g2.setColor(Color.white);
        text = "SETTINGS";
        g2.drawString(text, x, y);

        //settings
        //full screen 
        setFontPlain(g2, 25F);
        g2.setColor(Color.white);
        x = getXforCenteredText(text)-gp.tileSize*2;
        y += gp.tileSize * 2;
        text = "Full screen";
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">", x-gp.tileSize, y);
        }
        //if full screen is not set no cube
        if(gp.setFullScreenSet ==0){
            g2.setColor(Color.white);
            g2.fillRect(o , y-20, gp.tileSize/2, gp.tileSize/2);

            g2.setColor(Color.black);
            g2.fillRect(o+3 , y-20+3, gp.tileSize/2-6, gp.tileSize/2-6);
        }
        //if full screen is set cube
        if(gp.setFullScreenSet == 1){
            g2.setColor(Color.white);
            g2.fillRect(o , y-20, gp.tileSize/2, gp.tileSize/2);

            g2.setColor(Color.black);
            g2.fillRect(o+3 , y-20+3, gp.tileSize/2-6, gp.tileSize/2-6);

            g2.setColor(Color.white);
            g2.fillRect(o+5 , y-20+5, gp.tileSize/2-10, gp.tileSize/2-10);
        }

        //disable HELP
        g2.setColor(Color.white);
        y += gp.tileSize*1+20;
        text = "Help messages";
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">", x-gp.tileSize, y);
        }
        //key helper disabled
        if(gp.KeyHelper ==0){
            g2.setColor(Color.white);
            g2.fillRect(o , y-20, gp.tileSize/2, gp.tileSize/2);

            g2.setColor(Color.black);
            g2.fillRect(o+3 , y-20+3, gp.tileSize/2-6, gp.tileSize/2-6);
        }
        //key helper unabled
        if(gp.KeyHelper == 1){
            g2.setColor(Color.white);
            g2.fillRect(o , y-20, gp.tileSize/2, gp.tileSize/2);

            g2.setColor(Color.black);
            g2.fillRect(o+3 , y-20+3, gp.tileSize/2-6, gp.tileSize/2-6);

            g2.setColor(Color.white);
            g2.fillRect(o+5 , y-20+5, gp.tileSize/2-10, gp.tileSize/2-10);
        }

        //sound  effect
        g2.setColor(Color.white);
        y += gp.tileSize*1+20;
        text = "Sound effect";
        g2.drawString(text, x, y);
        if(commandNum == 2){
            g2.drawString(">", x-gp.tileSize, y+40);
        }
        g2.setColor(Color.white);
        g2.fillRect(x , y+20, gp.tileSize*5, gp.tileSize/2);

        g2.setColor(Color.black);
        g2.fillRect(x+3 , y+20+3, gp.tileSize*5-6, gp.tileSize/2-6);

        //EFFECT VOL ++ --
        double witdhS = gp.tileSize*5-10;
        double volumeMult = witdhS/120;
        double volumeWitdh = 24*gp.sound2.volumeScale*volumeMult ;

        g2.setColor(Color.white);
        g2.fillRect(x+5 , y+20+5, (int)volumeWitdh, gp.tileSize/2-10);


        //music volume
        y += gp.tileSize*2;
        text = "Music volume";
        g2.drawString(text, x, y);
        if(commandNum == 3){
            g2.drawString(">", x-gp.tileSize, y+40);
        }
        g2.setColor(Color.white);
        // g2.fillRoundRect(gp.tileSize/2 -5 , gp.tileSize/2 -5 , gp.tileSize*2 +10, gp.tileSize*2 +10, 25, 25);
        g2.fillRect(x , y+20, gp.tileSize*5, gp.tileSize/2);

        g2.setColor(Color.black);
        g2.fillRect(x+3 , y+20+3, gp.tileSize*5-6, gp.tileSize/2-6);

        //EFFECT VOL ++ --
        witdhS = gp.tileSize*5-10;
        volumeMult = witdhS/120;
        volumeWitdh = 24*gp.sound.volumeScale*volumeMult ;

        g2.setColor(Color.white);
        g2.fillRect(x+5 , y+20+5, (int)volumeWitdh, gp.tileSize/2-10);

        //key
        text = "Control";
        y += gp.tileSize*2;
        g2.drawString(text, getXforCenteredText(text)-15, y);
        if(commandNum == 4){
            g2.drawString(">", x-gp.tileSize, y);
        }

        //key
        if(gp.setFullScreenSet != gp.fullScreenWarningMessage){
            text = "Some changes need a restart to be effective";
            y += gp.tileSize*2;
            g2.drawString(text, getXforCenteredText(text), y);
        }
        ESC();
    }

    public void keys(){
        //background
        Color c = new Color(0, 0, 0);
        g2.setColor(c);
        g2.fillRect(0 , 0, gp.screenWidth, gp.screenHeight);

        setFontBold(g2, 66F);
        String text = "";
        int x;
        int y;
        //SHADOW COLOR
        g2.setColor(Color.gray);
        text = "Control";
        x = getXforCenteredText(text);
        y = gp.tileSize * 4;
        g2.drawString(text, x+5, y+5);
        //TEXT COLOR
        g2.setColor(Color.white);
        text = "Control";
        g2.drawString(text, x, y);

        //settings
        //move 
        setFontPlain(g2, 25F);
        g2.setColor(Color.white);
        x = getXforCenteredText(text)-gp.tileSize*2;
        y += gp.tileSize * 2;
        text = "Move";
        g2.drawString(text, x, y);
        setFontBold(g2, 25F);
        text = "WASD";
        g2.drawString(text, x+gp.tileSize*4+gp.tileSize/2, y);

        //confirm/attack 
        setFontPlain(g2, 25F);
        y += gp.tileSize*1+20;
        text = "Confirm/attack";
        g2.drawString(text, x, y);
        setFontBold(g2, 25F);
        text = "ENTER/E";
        g2.drawString(text, x+gp.tileSize*4+gp.tileSize/2, y);

        //escape
        setFontPlain(g2, 25F);
        y += gp.tileSize*1+20;
        text = "Back";
        g2.drawString(text, x, y);
        setFontBold(g2, 25F);
        text = "ESC";
        g2.drawString(text, x+gp.tileSize*4+gp.tileSize/2, y);

        //map
        setFontPlain(g2, 25F);
        y += gp.tileSize*1+20;
        text = "Map";
        g2.drawString(text, x, y);
        setFontBold(g2, 25F);
        text = "M";
        g2.drawString(text, x+gp.tileSize*4+gp.tileSize/2, y);

        //screen shot
        y += gp.tileSize*1+20;
        setFontPlain(g2, 25F);
        text = "Screenshot mode";
        g2.drawString(text, x, y);
        setFontBold(g2, 25F);
        text = "U";
        g2.drawString(text, x+gp.tileSize*4+gp.tileSize/2, y);

        //reset npc
        y += gp.tileSize*1+20;
        setFontPlain(g2, 25F);
        text = "Reset NPCs";
        g2.drawString(text, x, y);
        setFontBold(g2, 25F);
        text = "R";
        g2.drawString(text, x+gp.tileSize*4+gp.tileSize/2, y);

        ESC();
    }
    
    public void map(){
        //background
        if(mapAnimation ==1){
            mapCounter++;
            if(mapCounter <=200){
                mapAnimationCounter++;
            }
            else if(mapCounter >400 && mapCounter <= 600){
                mapAnimationCounter--;
            }
            else if(mapCounter >800){
                mapCounter=0;
            }
        }
        int animspeed =6;
        g2.drawImage(gp.player.blurMap,0 - mapAnimationCounter/2/animspeed, 0 -mapAnimationCounter/2/animspeed,gp.screenWidth+mapAnimationCounter/animspeed,gp.screenHeight+mapAnimationCounter/animspeed , null); 


        //map Background
        int x =gp.screenHeight-gp.tileSize; 
        int y ;
        final int gpTile = gp.tileSize;
        Color c = new Color(0, 0, 0, 150);
        g2.setColor(c); 
        g2.fillRoundRect(gp.screenWidth/2 -x/2 +5, gpTile/2+5, x  ,x, 6, 6); //        g2.fillRoundRect(gp.screenWidth/2 -x/2 , gpTile/2, x  ,x , 35,35); 
        c = new Color(0, 0, 0);
        g2.setColor(c); 
        g2.fillRoundRect(gp.screenWidth/2 -x/2 , gpTile/2, x  ,x , 5,5); 
        setFontBold(g2, 56F);

        //map
        x =gp.screenHeight-gp.tileSize-4;
        g2.drawImage(gp.player.map, gp.screenWidth/2 -x/2 +0 , gpTile/2 +2, x  ,x , null);   
        
        y = gpTile*2;

        if(gp.currentMap < 7 && gp.currentMap != 4){
            g2.setColor(Color.gray);
            String text = "Calmeria village";
            g2.drawString(text, gpTile, y+5);
            //TEXT COLOR
            g2.setColor(Color.white);
            text = "Calmeria village";
            g2.drawString(text, gpTile, y);
            g2.drawImage(gp.player.face0,gp.screenWidth/2 - gpTile*5, gp.screenHeight/2 + gpTile*5 + gpTile/2, gpTile, gpTile , null); 
        }
        else if(gp.currentMap == 4){
            g2.setColor(Color.gray);
            String text = "Road 1";
            g2.drawString(text, gpTile+5, y+5);
            //TEXT COLOR
            g2.setColor(Color.white);
            text = "Road 1";
            g2.drawString(text, gpTile, y);
            g2.drawImage(gp.player.face0,gp.screenWidth/2 - gpTile*5, gp.screenHeight/2 + gpTile*4 +gpTile/3 + gpTile/2, gpTile, gpTile , null); 
        }
        else if(gp.currentMap == 8){
            g2.setColor(Color.gray);
            String text = "Datolana";
            g2.drawString(text, gpTile+5, y+5);
            //TEXT COLOR
            g2.setColor(Color.white);
            text = "Datolana";
            g2.drawString(text, gpTile, y);
            g2.drawImage(gp.player.face0,gp.screenWidth/2 - gpTile*5, gp.screenHeight/2 + gpTile*3 +gpTile/3 + gpTile/2, gpTile, gpTile , null); 
        }
    }

    public void credits(){
        //background
        Color c = new Color(0, 0, 0);
        g2.setColor(c);
        g2.fillRect(0 , 0, gp.screenWidth, gp.screenHeight);

        //title
        setFontBold(g2, 96F);
        String text = "";
        int x;
        int y;
        //SHADOW COLOR
        g2.setColor(Color.gray);
        text = "CREDITS";
        x = getXforCenteredText(text);
        y = gp.tileSize * 4;
        g2.drawString(text, x+5, y+5);
        //TEXT COLOR
        g2.setColor(Color.white);
        text = "CREDITS";
        g2.drawString(text, x, y);
        
            //page 1
        if(pageCreditNumber == 0){
            x = gp.tileSize*2;
            setFontPlain(g2, 26F);
            text = "This project is a culmination that allowed me to learn JAVA and put forward my knowledge.";
            y+= gp.tileSize*2;
            g2.drawString(text, x, y);

            text = "Make a 2D adventure RPG was like a dream for me so I make one :D .";
            y+= 25;
            g2.drawString(text, x, y);

            text = "The whole project was realized in JAVA by Wassim MESFIOUI.";
            y+= 25;
            g2.drawString(text, x, y);

            text = "Through this project, I have been able to implement my various computer skills, ";
            y+= 50;
            g2.drawString(text, x, y);

            text = "whether for the adaptation of the tiles with the LibreSprite software, the adaptation";
            y+= 25;
            g2.drawString(text, x, y);

            text = "of the musics and the sounds with Audacity, or the creation of maps with Tiled.";
            y+= 25;
            g2.drawString(text, x, y);

            text = "I was also able to put my creative skills into play, whether for the creation of a story";
            y+= 50;
            g2.drawString(text, x, y);

            text = "but also for the drawing of the different characters and their animations.";
            y+= 25;
            g2.drawString(text, x, y);

            setFontPlain(g2, 16F);
            text = "all therme 'I', 'ME', concerns Wassim MESFIOUI.";
            y+= 60;
            g2.drawString(text, x, y);
        }
        if(pageCreditNumber == 1){
            x = gp.tileSize*2;
            setFontPlain(g2, 26F);
            text = "I would like to thank :";
            y+= gp.tileSize*2;
            g2.drawString(text, x, y);

            text = "Magiscarf";
            y+= 50;
            g2.drawString(text, x, y);

            text = "Original creator of the exterior tiles";
            y+= 25;
            g2.drawString(text, x, y);

            text = "Demise-Hawk ";
            y+= 50;
            g2.drawString(text, x, y);

            text = "Original creator of the interior tiles";
            y+= 25;
            g2.drawString(text, x, y);

            text = "xDeviruchi";
            y+= 50;
            g2.drawString(text, x, y);

            text = "Original creator of 8bit fight music";
            y+= 25;
            g2.drawString(text, x, y);

            text = "SVL";
            y+= 50;
            g2.drawString(text, x, y);

            text = "Original creator of the RPG musical atmosphere";
            y+= 25;
            g2.drawString(text, x, y);
        }

        if(pageCreditNumber == 2){
            x = gp.tileSize*2;
            setFontPlain(g2, 26F);
            text = "This project will remain at the stage of an Alpha for the moment.";
            y+= gp.tileSize*2;
            g2.drawString(text, x, y);

            text = "At the current stage, we can already have an idea of the possible functionalities.";
            y+= 25;
            g2.drawString(text, x, y);

        }

        //page selector
        setFontPlain(g2, 26F);
        x=gp.tileSize/2;
        y= gp.screenHeight-gp.tileSize*2;
        if(pageCreditNumber != 0){
            text = "↑";
            g2.drawString(text, x, y);
        }

        text = pageCreditNumber+1 + " /3";
        x=gp.tileSize/2 -10;
        y+= 25;
        g2.drawString(text, x, y);

        if(pageCreditNumber != 2){
        text = "↓";
        x=gp.tileSize/2;
        y+= 25;
        g2.drawString(text, x, y);
        }
    }

    public void ESC(){
        String text = "";
        setFontBold(g2, 18F);
        g2.setColor(Color.white);
        text = "go back: ESC";
        g2.drawString(text, gp.tileSize/4, gp.screenHeight-gp.tileSize/3);
    }

    public void basicKey(){
        //KEY
        int x;
        int y;
        String text = "";
        setFontBold(g2, 28F);
        text = "↑";
        x= getXforCenteredText(text)-gp.tileSize*9;
        y = gp.tileSize;
        g2.drawString(text, x, y);
        
        setFontPlain(g2, 28F);
        text = "press: Z";
        x= getXforCenteredText(text)-gp.tileSize*9;
        y += gp.tileSize/2;
        g2.drawString(text, x, y);

        setFontBold(g2, 28F);
        text = "↓";
        x= getXforCenteredText(text)-gp.tileSize*9;
        y += gp.tileSize*1;
        g2.drawString(text, x, y);

        setFontPlain(g2, 28F);
        text = "press: S";
        x= getXforCenteredText(text)-gp.tileSize*9;
        y += gp.tileSize/2;
        g2.drawString(text, x, y);

        setFontBold(g2, 28F);
        text = "→";
        x= getXforCenteredText(text)-gp.tileSize*9;
        y += gp.tileSize*1;
        g2.drawString(text, x, y);

        setFontPlain(g2, 28F);
        text = "press: D";
        x= getXforCenteredText(text)-gp.tileSize*9;
        y += gp.tileSize/2;
        g2.drawString(text, x, y);

        setFontBold(g2, 28F);
        text = "←";
        x= getXforCenteredText(text)-gp.tileSize*9;
        y += gp.tileSize*1;
        g2.drawString(text, x, y);

        setFontPlain(g2, 28F);
        text = "press: Q";
        x= getXforCenteredText(text)-gp.tileSize*9;
        y += gp.tileSize/2;
        g2.drawString(text, x, y);

        setFontBold(g2, 28F);
        text = "select";
        x= getXforCenteredText(text)-gp.tileSize*9;
        y += gp.tileSize*1;
        g2.drawString(text, x, y);

        setFontPlain(g2, 28F);
        text = "press: ENTER or E";
        x= getXforCenteredText(text)-gp.tileSize*9;
        y += gp.tileSize/2;
        g2.drawString(text, x, y);

        setFontBold(g2, 28F);
        text = "back";
        x= getXforCenteredText(text)-gp.tileSize*9;
        y += gp.tileSize*1;
        g2.drawString(text, x, y);

        setFontPlain(g2, 28F);
        text = "press: ESC";
        x= getXforCenteredText(text)-gp.tileSize*9;
        y += gp.tileSize/2;
        g2.drawString(text, x, y);
    }
    
    //bold font
    public void setFontBold(Graphics2D g2 ,float i){
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,i));
    }

    //plain font
    public void setFontPlain(Graphics2D g2, float i){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,i));
    }

    //pause screen
    public void drawPauseScreen(){
         gp.dataSave.saveData();
        // gp.config.saveConfig();

        int x,y;
        String text = "";    
        //main menu    
        if(commandStep ==0){
            g2.setColor(new Color(0,0,0, 200));
            g2.fillRect(0 , 0, gp.screenWidth, gp.screenHeight);
            x = gp.tileSize*2;
            y=gp.tileSize*2;

            g2.setColor(new Color(255,255,255));
            setFontBold(g2, 96F);
            text = "PAUSE";
            x = getXforCenteredText(text);
            y = gp.tileSize * 5;
            g2.drawString(text, x, y);

            setFontBold(g2, 28F);

            text = "MAIN MENU & SAVE";
            x= getXforCenteredText(text);
            y += gp.tileSize*2;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "SETTINGS";
            x= getXforCenteredText(text);
            y += gp.tileSize*1+gp.tileSize/2;
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "EXIT & SAVE";
            x= getXforCenteredText(text);
            y += gp.tileSize*1 +gp.tileSize/2;
            g2.drawString(text, x, y);
            if(commandNum == 2){
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "CREDITS";
            x= getXforCenteredText(text);
            y += gp.tileSize*1 +gp.tileSize/2;
            g2.drawString(text, x, y);
            if(commandNum == 3){
            g2.drawString(">", x-gp.tileSize, y);
            }  
        }
        //settings
        else if(commandStep==1){
            settings();
        }
        //credits
        else if(commandStep==3){
            credits();
        }
        //credits
        else if(commandStep==4){
            map();
        }
        //key
        else if(commandStep==5){
            keys();
        }
    }

    public void drawTribeSelector(){
        int x,y;
        String text = ""; 

        g2.setColor(new Color(0,0,0));
        g2.fillRect(0 , 0, gp.screenWidth, gp.screenHeight);
        drawDialogueScreen();
        x = gp.tileSize*3;

        int ts = gp.tileSize;
        y = gp.screenHeight/2 -2*ts;

        if(commandNum ==0){
            text = "Orcs, a fantastic, powerful and feared creatures";

            g2.drawImage(gp.player.cubeChooser,        ts*3    ,                      y,     ts*6     ,ts*6 , null);    
        }
        else if(commandNum ==1){
            text = "Pandas, wise and respected creatures but sometimes a little lazy";

            g2.drawImage(gp.player.cubeChooser,      gp.screenWidth/2 - ts*3,       y,     ts*6     ,ts*6 , null);     
        }        
        else if(commandNum ==2){
            text = "Anivians, known for their cunning, their agility and their crazy \n mania to ravage everything";
            
            g2.drawImage(gp.player.cubeChooser,      (gp.screenWidth) - ts*9,       y,     ts*6     ,ts*6 , null);    
        }
        y=gp.tileSize*3;
        for(String line: text.split(" \n ")){
            g2.drawString(line, x, y);
            y+= 40;
        }
        y = gp.screenHeight/2 -2*ts;
        g2.drawImage(gp.player.down0Orc,        ts*3    ,                           y,     ts*6     ,ts*6 , null);   
        g2.drawImage(gp.player.down0Panda,      gp.screenWidth/2 - ts*3,            y,     ts*6     ,ts*6 , null);   
        g2.drawImage(gp.player.down0Avian,      (gp.screenWidth) - ts*9,            y,     ts*6     ,ts*6 , null);   
       

    }

    //DIALOGUE
    public void drawDialogueScreen(){
        //WINDOW
        int x = gp.tileSize*2;
        int y=gp.tileSize*2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height= gp.tileSize*4;
        drawSubWindow(x, y, width, height);

        //text color
        Color c = new Color(66, 66, 66);
        g2.setColor(c);
        setFontPlain(g2, 32F);

        x+= gp.tileSize;
        y+= gp.tileSize;
        
        if(currentDialogue!= null){
            for(String line: currentDialogue.split(" \n ")){
                g2.drawString(line, x, y);
                y+= 40;
            }
        }
    }
    
    //DIALOGUE BACKGROUND
    public void drawSubWindow(int x, int y, int width, int height){

        Color c = new Color(255, 251, 240);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(144, 104, 80);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }
    
    public int getXforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 -length/2;
        return x;
    }
}