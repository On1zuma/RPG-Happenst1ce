//KEY HANDLEE
package org.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JDialog;

public class KeyHandler implements KeyListener{

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    public int soundMenu = 0;

    GamePanel gp;
    public KeyHandler(GamePanel gp ){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {        
    }

    //when we press a key
    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        ///////////////////////////////////////////////////////////PLAY STATE
        if(gp.gameState == gp.playState || gp.gameState == gp.fightState){
            //if dialogue before we don't update position of npc
            gp.speakWith =0;

            if(code == KeyEvent.VK_Z){
                upPressed = true;
            }
            else if(code == KeyEvent.VK_S){
                downPressed = true;
            }
            else if(code == KeyEvent.VK_Q){
                leftPressed = true;
            }
            else if(code == KeyEvent.VK_D){
                rightPressed = true;
            }
            else if(code == KeyEvent.VK_E){
                enterPressed = true;
            }
            else if(code == KeyEvent.VK_R){
                if(gp.gameState != gp.fightState && gp.currentState > 1){
                    gp.aSetter.setNPC();
                    gp.player.worldX = gp.posAfterTpX;
                    gp.player.worldY = gp.posAfterTpY;
                }
            }
            else if(code == KeyEvent.VK_M){
                if(gp.gameState == gp.playState){
                    gp.gameState = gp.pauseState;
                    gp.playMusic(21);
                    gp.ui.commandStep =4;
                    gp.ui.mapAnimation =1;
                    gp.ui.mapCounter =0;
                }
            }
            else if(code == KeyEvent.VK_ESCAPE){
                if(gp.gameState == gp.gameStateSaver){
                    gp.checkIfMusicIsSet();
                    gp.gameState = gp.pauseState;
                    gp.ui.commandNum =0;
                    gp.ui.commandStep =0;
                }
            }
            else if(code == KeyEvent.VK_U && gp.EventEdge ==0){
                downPressed = false;
                upPressed = false;
                leftPressed = false;
                rightPressed = false;
                if(gp.gameState == gp.gameStateSaver && gp.gameState == gp.playState){
                    gp.gameState = gp.photoState;
                    gp.ui.photoInstruction =0;
                }
            }
            else if(code == KeyEvent.VK_U && gp.EventEdge ==1){
                gp.ui.alterteMessage =1;
            }
        }

        ///////////////////////////////////////////////////////////PHOTO STATE 
        else if(gp.gameState == gp.photoState){
            // zoomInOut

            if(code == KeyEvent.VK_UP || code == KeyEvent.VK_Z
            ){
                gp.ui.photoInstruction =1;
                gp.zoomInOut(2);
                gp.zoomLimit(2);
            }
            if(code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S){
                gp.ui.photoInstruction =1;
                gp.zoomInOut(-2);
                gp.zoomLimit(-2);
            }
            else if(code == KeyEvent.VK_ESCAPE || code == KeyEvent.VK_U){
                if(gp.gameState == gp.photoState){
                    // gp.aSetter.setNPC();
                    // gp.setupGame();
                    gp.aEntity.direction ="down";
                    gp.pass = 0;
                    gp.gameState = gp.playState;
                    gp.zoomLimit = 0;
                    gp.player.worldX = gp.savePlayerPosX;
                    gp.player.worldY = gp.savePlayerPosY;
                    gp.ui.photoInstruction =0;
                }
            }
        }

        ///////////////////////////////////////////////////////////CINEMATIC STATE 
        else if(gp.gameState == gp.cinematicState){
            if(code == KeyEvent.VK_Z){
                if(gp.currentState == 3 || gp.currentState == 6){
                    upPressed = true;
                }
                else if(gp.currentState !=12){
                        upPressed = false;
                }
            }
            if(gp.currentState ==12 && gp.ui.currentChatStep == 6){
                if(code == KeyEvent.VK_Q){
                    gp.ui.commandNum--;
                    if(gp.ui.commandNum<0){
                        gp.ui.commandNum=0;
                    }
                }
                else if(code == KeyEvent.VK_D){
                    gp.ui.commandNum++;
                    if(gp.ui.commandNum>2){
                        gp.ui.commandNum=2;
                    }
                }
                else if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_E){
                    if(gp.currentState ==12){
                        if(gp.ui.commandNum==0){
                            gp.tribeValue=1;
                            gp.gameState = gp.dialogueState;
                            gp.ui.currentChatStep = 7;
                            gp.ui.commandNum=0;
                        }
                        if(gp.ui.commandNum==1){
                            gp.tribeValue=2;
                            gp.gameState = gp.dialogueState;
                            gp.ui.currentChatStep = 7;
                            gp.ui.commandNum=0;
                        }
    
                        if(gp.ui.commandNum==2){
                            gp.tribeValue=3;
                            gp.gameState = gp.dialogueState;
                            gp.ui.currentChatStep = 7;
                            gp.ui.commandNum=0;
                        }
                    }
                }
            }

            if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_E){
                if(gp.BattleStep ==1){
                    gp.gameState = gp.fightState;
                    gp.BattleStep=2;
                }
                if(gp.BattleStep ==3){
                    gp.keyH.downPressed = true;
                    gp.currentState++;
                    gp.gameState = gp.cinematicState;
                    gp.checkIfMusicIsSet();
                    gp.BattleStep=0;
                }
            }       
        }

        ///////////////////////////////////////////////////////////TITLE STATE
        else if(gp.gameState == gp.titleState){
            //LOGO
            if( gp.ui.commandStep==2){
                    //while no key pressed we put the logo
                    if(System.in==null){
                        Scanner scanner = new Scanner(System.in);
                        scanner.nextInt();
                        scanner.close();
                        
                    }else{
                        gp.ui.commandStep=0;
                        gp.playMusic(5);
                    }
            }
            else if(gp.ui.commandStep!=2){
                //MENU SELECTOR
                    if(code == KeyEvent.VK_Z){
                        //MENU
                            if( gp.ui.commandStep ==0 ){
                            gp.ui.commandNum--;
                            soundMenu =0;
                            if(gp.ui.commandNum<0){
                                soundMenu =1;
                                gp.ui.commandNum=0;
                            }
                            else if(soundMenu ==0){
                                gp.playSE(18);
                            }
                        }
                        //credit
                        else if( gp.ui.commandStep ==3 ){
                            gp.ui.pageCreditNumber--;
                            if(gp.ui.pageCreditNumber<0){
                                gp.ui.pageCreditNumber=0;
                            }           
                        }

                        //settings
                        if( gp.ui.commandStep ==4 ){
                            gp.ui.commandNum--;
                            soundMenu =0;
                            if(gp.ui.commandNum<0){
                                gp.ui.commandNum=0;
                                soundMenu =1;
                            }
                            else if(soundMenu ==0){
                                gp.playSE(18);
                            }
                        }
                    }
                    else if(code == KeyEvent.VK_S){
                        //MENU
                        if( gp.ui.commandStep ==0 ){
                            gp.ui.commandNum++;
                            soundMenu =0;
                            if(gp.ui.commandNum>4){
                                gp.ui.commandNum=4;
                                soundMenu =1;
                            }
                            else if(soundMenu ==0){
                                gp.playSE(18);
                            }           
                        }
                        //credit
                        else if( gp.ui.commandStep ==3 ){
                            gp.ui.pageCreditNumber++;
                            if(gp.ui.pageCreditNumber>2){
                                gp.ui.pageCreditNumber=2;
                            }           
                        }

                        if( gp.ui.commandStep ==4 ){
                            gp.ui.commandNum++;
                            soundMenu =0;
                            if(gp.ui.commandNum>4){
                                gp.ui.commandNum=4;
                                soundMenu =1;
                            }
                            else if(soundMenu ==0){
                                gp.playSE(18);
                            }
                        }
                    }

                //gender SELECTOR Q
                else if(code == KeyEvent.VK_Q){
                //new adventure
                    if(gp.ui.commandStep == 1){
                        if(gp.ui.commandNum == 0 && gp.ui.currentChatStep ==10){
                            gp.genderValue= 1;

                            if(gp.genderValue<1){
                                gp.genderValue=1;
                            }
                        }
                    }
                    //settings
                    if(gp.ui.commandStep == 4){
                        //sound2 effect
                        if(gp.ui.commandNum ==2){
                            //SOUND --
                            gp.sound2.volumeScale--;
                            if(gp.sound2.volumeScale <0){
                                gp.sound2.volumeScale =0;
                            }
                            gp.sound2.checkVolume();
                            gp.playSE(18);

                        }
                        //music sound
                        else if(gp.ui.commandNum ==3){
                            //SOUND --
                            gp.sound.volumeScale--;
                            if(gp.sound.volumeScale <0){
                                gp.sound.volumeScale =0;
                            }
                            gp.sound.checkVolume();
                            gp.playSE2(18);

                        }
                    }
                }
                //gender SELECTOR D
                else if(code == KeyEvent.VK_D){
                    //new adventure
                    if(gp.ui.commandStep == 1){
                        if(gp.ui.commandNum == 0 && gp.ui.currentChatStep ==10){
                            gp.genderValue= 2;
                            if(gp.genderValue>2){
                                gp.genderValue=2;
                            }
                        } 
                    }
                    //settings
                    if(gp.ui.commandStep == 4){
                        //sound effect
                        if(gp.ui.commandNum ==2){
                            //SOUND ++
                            gp.sound2.volumeScale++;
                            if(gp.sound2.volumeScale >5){
                                gp.sound2.volumeScale =5;
                            }
                            gp.sound2.checkVolume();
                            gp.playSE(18);
                        }
                        //music sound
                        else if(gp.ui.commandNum ==3){
                            //SOUND ++
                            gp.sound.volumeScale++;
                            if(gp.sound.volumeScale >5){
                                gp.sound.volumeScale =5;
                            }
                            gp.sound.checkVolume();
                            gp.playSE2(18);
                        }
                    }
                }

                if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_E ){
                    //enter sound effect
                    if(gp.ui.commandStep != 4 && gp.ui.currentChatStep <1){
                        gp.playSE(24);
                    }
                    //inside settings
                    if(gp.ui.commandStep == 4){
                        //full screen
                        if(gp.ui.commandNum ==0){   
                            if(gp.setFullScreenSet ==0){
                                gp.setFullScreenSet =1;
                            }
                            else if(gp.setFullScreenSet ==1){
                                gp.setFullScreenSet =0;
                            }
                        }
                        if(gp.ui.commandNum ==1){   
                            if(gp.KeyHelper ==0){
                                gp.KeyHelper =1;
                            }
                            else if(gp.KeyHelper ==1){
                                gp.KeyHelper =0;
                            }
                        }

                        if(gp.ui.commandNum ==4){   
                            //key handle                 
                            gp.ui.commandStep =5;
                        }
                    }

                    // CREATION BUTTON
                    else if(gp.ui.commandStep == 1){
                        // STEP INSIDE CREATION
                        if(gp.ui.currentChatStep !=10 && gp.ui.currentChatStep !=12){

                            gp.ui.currentChatStep++;
                            gp.nameStep =0;

                        }else if(gp.ui.currentChatStep ==10){
                            if(gp.genderValue != 0){
                            gp.ui.currentChatStep++;
                            }

                        } else if(gp.ui.currentChatStep == 12){
                            // && gp.playerName.contains("") == false   isDigit
                            // 
                            final JDialog dialog = new JDialog();
                            dialog.setAlwaysOnTop(true);

                            if(gp.playerName != null && gp.playerName.contains(" ") == false && gp.playerName.length() >= 2  && gp.playerName.length() <= 10 && gp.playerName.matches("[+-]?\\d*(\\.\\d+)?") == false){
                            gp.ui.currentChatStep++;
                            }
                            else if(gp.playerName == null || gp.playerName.contains(" ")){
                                JOptionPane.showMessageDialog(dialog, "Your name does not match, don't use spaces","Invalid",JOptionPane.INFORMATION_MESSAGE);
                                gp.playerName="";
                                gp.nameStep =0;
                            }
                            else if(gp.playerName.matches("[+-]?\\d*(\\.\\d+)?") == true){
                                JOptionPane.showMessageDialog(dialog, "You cannot use special characters or numbers","Invalid",JOptionPane.INFORMATION_MESSAGE);
                                gp.playerName="";
                                gp.nameStep =0;
                            }
                            else if(gp.playerName.length() > 10){
                                JOptionPane.showMessageDialog(dialog, "Your name is too long  >1 <10 ","Invalid",JOptionPane.INFORMATION_MESSAGE);
                                gp.playerName="";
                                gp.nameStep =0;
                            }
                            else if(gp.playerName.length() < 2){
                                JOptionPane.showMessageDialog(dialog, "Your name is too short >1 <10","Invalid", JOptionPane.INFORMATION_MESSAGE);
                                gp.playerName="";
                                gp.nameStep =0;
                            }
                        }
                        if(gp.ui.currentChatStep > 16){ //END OF THE CREATION
                            if(gp.sound.soundURL[1] != null){
                                gp.playMusic(1);
                            }
                            //DATA RESET //player
                            gp.tribeValue =0;//0
                            gp.ui.commandNum = 0;
                            gp.ui.commandStep =10;
                            gp.currentMap =0;
                            gp.player.worldX = gp.tileSize * 23;
                            gp.player.worldY =gp.tileSize * 41;
                            gp.gameState = gp.playState;
                            gp.ui.currentChatStep=1;
                            gp.maxXP = 100;
                            gp.playerPV = gp.maxPV;
                            gp.level = 1;
                            gp.playerXP = 1;
                            gp.playerXPPrec = gp.playerXP;
                            gp.ui.movInstruction =0; //Key instructions
                            gp.ui.ValuYesNo =-1;
                            //initialisation
                            gp.currentState = 0;
                            gp.npcINI =0;
                            gp.monsterINI =0;
                            gp.objectINI =0;
                            gp.genderIni =-1;
                            gp.tribeIni =-1;
                        }
                    }

                    else if(gp.ui.commandStep==0){
                        //new adventure
                        if(gp.ui.commandNum==0){
                            gp.ui.currentChatStep=1;
                            gp.ui.commandStep=1;
                        }
                    // LOAD BUTTON
                        if(gp.ui.commandNum == 1){
                            if(gp.debug==1){
                                gp.currentMap =gp.debugMap;//0
                                gp.stopMusic();
                                gp.player.worldX = gp.tileSize * gp.debugPosX;
                                gp.player.worldY =gp.tileSize * gp.debugPosY;  //41 
                                gp.currentState =gp.debugCurrentState;
                                gp.tribeValue = gp.debugTribV;//0
                                gp.playerName = "debug";
                                gp.level = gp.lvlDebug;
                                gp.ui.currentChatStep=0;   
                                gp.gameState = gp.playState;//playstate
                            }
                            else if(gp.debug ==0){
                                //LOAD SAVE
                                if (gp.saveSet ==1){
                                    gp.ui.currentChatStep = 0;
                                    gp.dataSave.loadSave();
                                    gp.npcINI =0;
                                    gp.monsterINI =0;
                                    gp.objectINI =0;
                                    gp.genderIni =-1;
                                    gp.tribeIni =-1;
                                    //music part
                                    gp.currentMap = gp.saveMap;
                                    gp.player.worldX=gp.savePlayerPosX;
                                    gp.player.worldY=gp.savePlayerPosY;
        
                                    gp.gameState = gp.gameStateSaver;
                                    gp.playMusic(gp.currentMusicSaver);
                                    }
                            }
                        }
                        // EXIT BUTTON
                        else if(gp.ui.commandNum == 4){
                            System.exit(0);
                        }

                        // CREDIT BUTTON
                        else if(gp.ui.commandNum == 3){
                            gp.checkIfMusicIsSet();
                            gp.ui.commandStep = 3;
                        }

                        // OPTION SETTING
                        else if(gp.ui.commandNum == 2){
                            gp.checkIfMusicIsSet();
                            gp.ui.commandStep = 4;
                            gp.ui.commandNum = 0;
                        }
                    }
                }
                else if(code == KeyEvent.VK_ESCAPE){
                    if(gp.ui.commandStep > 0 && gp.ui.commandStep != 5){
                        gp.ui.currentChatStep =0;
                        gp.ui.stopper=0;
                        gp.ui.pageCreditNumber=0;
                        gp.ui.commandNum =0;
                        gp.playMusic(5);

                        gp.ui.commandStep = 0;
                        gp.ui.ValuYesNo =-1;
                    }
                    else if(gp.ui.commandStep == 5){
                        gp.ui.commandStep = 4;
                        gp.ui.commandNum =0;
                    }
                }
            }
        }

        ///////////////////////////////////////////////////////////PAUSE STATE
        else if(gp.gameState == gp.pauseState){
            if(code == KeyEvent.VK_Z){
                //MENU
                    if( gp.ui.commandStep ==0 ){
                    gp.ui.commandNum--;
                    soundMenu =0;
                    if(gp.ui.commandNum<0){
                        gp.ui.commandNum=0;
                        soundMenu =1;
                    }
                    else if(soundMenu ==0){
                        gp.playSE(18);
                    }
                }
                //credit
                else if( gp.ui.commandStep ==3 ){
                    gp.ui.pageCreditNumber--;
                    if(gp.ui.pageCreditNumber<0){
                        gp.ui.pageCreditNumber=0;
                    }           
                }

                //settings
                if( gp.ui.commandStep ==1 ){
                    gp.ui.commandNum--;
                    soundMenu =0;
                    if(gp.ui.commandNum<0){
                        gp.ui.commandNum=0;
                        soundMenu =1;
                    }
                    else if(soundMenu ==0){
                        gp.playSE(18);
                    }
                }
            }
            else if(code == KeyEvent.VK_S){
                //MENU
                if( gp.ui.commandStep ==0 ){
                    gp.ui.commandNum++;
                    soundMenu =0;
                    if(gp.ui.commandNum>3){
                        gp.ui.commandNum=3;
                        soundMenu =1;
                    }     
                    else if(soundMenu ==0){
                        gp.playSE(18);
                    }      
                }
                //credit
                else if( gp.ui.commandStep ==3 ){
                    gp.ui.pageCreditNumber++;
                    if(gp.ui.pageCreditNumber>2){
                        gp.ui.pageCreditNumber=2;
                    }           
                }
                //settings
                if( gp.ui.commandStep ==1 ){
                    gp.ui.commandNum++;
                    soundMenu =0;
                    if(gp.ui.commandNum>4){
                        gp.ui.commandNum=4;
                        soundMenu =1;
                    }     
                    else if(soundMenu ==0){
                        gp.playSE(18);
                    }      
                }
            }
            else if(code == KeyEvent.VK_Q){
                //settings
                    //settings
                    if(gp.ui.commandStep == 1){
                        //sound2 effect
                        if(gp.ui.commandNum ==2){
                            //SOUND --
                            gp.sound2.volumeScale--;
                            if(gp.sound2.volumeScale <0){
                                gp.sound2.volumeScale =0;
                            }
                            gp.sound2.checkVolume();
                            gp.playSE(18);

                        }
                        //music sound
                        else if(gp.ui.commandNum ==3){
                            //SOUND --
                            gp.sound.volumeScale--;
                            if(gp.sound.volumeScale <0){
                                gp.sound.volumeScale =0;
                            }
                            gp.sound.checkVolume();
                            gp.playSE2(18);

                        }
                    }
            }
            else if(code == KeyEvent.VK_D){
                //settings
                if(gp.ui.commandStep == 1){
                    //sound effect
                    if(gp.ui.commandNum ==2){
                        //SOUND ++
                        gp.sound2.volumeScale++;
                        if(gp.sound2.volumeScale >5){
                            gp.sound2.volumeScale =5;
                        }
                        gp.sound2.checkVolume();
                        gp.playSE(18);
                    }
                    //music sound
                    else if(gp.ui.commandNum ==3){
                        //SOUND ++
                        gp.sound.volumeScale++;
                        if(gp.sound.volumeScale >5){
                            gp.sound.volumeScale =5;
                        }
                        gp.sound.checkVolume();
                        gp.playSE2(18);
                    }
                }
            }
            else if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_E){
                gp.playSE(24);
                // MAIN MENU
                if(gp.ui.commandStep ==0){
                    if(gp.ui.commandNum == 0){
                        gp.ui.currentDialogue = " ";
                        gp.ui.commandStep = 0;

                        //save if main menu
                        gp.dataSave.saveData();
                        gp.config.saveConfig();

                        gp.gameState = gp.titleState;
                        gp.playMusic(5);

                        gp.ui.commandStep = 0;
                    } 
                    if(gp.ui.commandNum == 1){
                        gp.ui.commandStep = 1;
                        gp.ui.commandNum =0;
                    } 
                    
                    // EXIT BUTTON
                    if(gp.ui.commandNum == 2){
                        //save on exit
                        gp.dataSave.saveData();
                        gp.config.saveConfig();

                        System.exit(0);
                    } 
                    // CREDIT BUTTON
                    if(gp.ui.commandNum == 3){
                        gp.ui.commandStep = 3;      
                    } 
                }

                //inside settings
                else if(gp.ui.commandStep == 1){
                    //full screen
                    if(gp.ui.commandNum ==0){   
                        if(gp.setFullScreenSet ==0){
                            gp.setFullScreenSet =1;
                        }
                        else if(gp.setFullScreenSet ==1){
                            gp.setFullScreenSet =0;
                        }
                    }
                    if(gp.ui.commandNum ==1){   
                        if(gp.KeyHelper ==0){
                            gp.KeyHelper =1;
                        }
                        else if(gp.KeyHelper ==1){
                            gp.KeyHelper =0;
                        }
                    }

                    if(gp.ui.commandNum ==4){   
                        //key handle                 
                        gp.ui.commandStep =5;
                    }
                }
                
            }
            else if(code == KeyEvent.VK_ESCAPE){
                if(gp.ui.commandStep == 0 || gp.ui.commandStep == 4){
                    if(gp.gameState != gp.gameStateSaver){
                        gp.ui.commandNum = 0;
                        gp.ui.mapAnimation =0;
                        gp.playMusic(gp.currentMusicSaver);
                        gp.gameState = gp.gameStateSaver;
                    }
                }
                else if(gp.ui.commandStep == 3 || gp.ui.commandStep == 1 ){
                    gp.ui.commandStep = 0;
                    gp.ui.pageCreditNumber=0;
                    gp.ui.commandNum = 0;
                }
                else if(gp.ui.commandStep == 5){
                    gp.ui.commandStep = 1;
                    gp.ui.commandNum = 0;
                }
            }
        }
        
        ///////////////////////////////////////////////////////////GAME OVER STATE
        else if(gp.gameState == gp.gameOverState){
            
            if(code == KeyEvent.VK_Z){
                gp.ui.commandNum--;
                soundMenu =0;
                if(gp.ui.commandNum<0){
                    gp.ui.commandNum=0;
                    soundMenu =1;
                }
                else if(soundMenu ==0){
                    gp.playSE(18);
                }  
            }

            else if(code == KeyEvent.VK_S){
                    gp.ui.commandNum++;
                    soundMenu =0;
                    if(gp.ui.commandNum>1){
                        gp.ui.commandNum=1;
                        soundMenu =1;
                    }
                    else if(soundMenu ==0){
                        gp.playSE(18);
                    }           
            }
            else if(code == KeyEvent.VK_ENTER){
                gp.playSE(24);
                if(gp.ui.commandNum == 0){
                    gp.ui.commandNum = 0;
                    gp.gameState = gp.playState;
                    gp.playerPV = gp.maxPV;
                    //BACK TO HOME
                    gp.currentMap =0;
                    gp.player.worldX = gp.tileSize * 23;
                    gp.player.worldY =gp.tileSize * 41;
                    gp.player.direction ="down";
                    gp.ui.currentChatStep=0;   
                    gp.playMusic(1);
                }
                if(gp.ui.commandNum == 1){
                    System.exit(0);                
                }
            }
        }

        ///////////////////////////////////////////////////////////DIALOGUE STATE
        else if(gp.gameState == gp.dialogueState){
            if(code == KeyEvent.VK_E){
                if(gp.EventCHat == 1){
                    gp.ui.currentChatStep++;
                    gp.ui.drawDialogueScreen();
                }else if(gp.EventCHat == 0){
                    gp.aEntity.setDialogue();
                    gp.aEntity.speak();
                    gp.aEntity.update();
                } 
            }
            // if(code == KeyEvent.VK_ESCAPE){
            //     gp.ui.currentChatStep=0;
            //     gp.gameState = gp.playState;
            // }
        }
    }

    ///////////////////////////////////////////////////////////when we release the key
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_Z){
            upPressed = false;
        }
        else if(code == KeyEvent.VK_S){
            downPressed = false;
        }
        else if(code == KeyEvent.VK_E){
            enterPressed = false;
        }
        else if(code == KeyEvent.VK_Q){
            leftPressed = false;
        }
        else if(code == KeyEvent.VK_D){
            rightPressed = false;
        }
    }
    
}