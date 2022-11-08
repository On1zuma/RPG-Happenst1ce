package com.app.main;

public class EventHandler {

    GamePanel gp;
    EventRect eventRect[][];

    public int mapSetter = 0;
    //if we change between interior and exteriors
    public String changeMusicIfTypeChange;

    public EventHandler(GamePanel gp){
        this.gp = gp;
        // QuestSetter questSetter = new QuestSetter(gp);
        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
        int col = 0;
        int row = 0;
        while(col < gp.maxWorldCol && row < gp.maxWorldRow){
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 0;
            eventRect[col][row].y = 10;
            eventRect[col][row].width = gp.tileSize;
            eventRect[col][row].height = gp.tileSize;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        } 
    }

    public void checkEvent(){

        if(gp.tpAfterfight ==1){
            int map;
            double col;
            double row;
            gp.currentState += 1;
            map= gp.BefforFightMap;
            col = gp.BefforFightX/gp.tileSize;
            row = gp.BefforFightY/gp.tileSize;
            gp.keyH.downPressed = false;
            gp.playerPV = gp.maxPV;
            teleport(map, col, row, "down");
            gp.tpAfterfight=0;
        }
        //colum / row //globaly we are looking first to wich map we are and after if we go to an exit we teleport the player OR others
        //MAIN BOARD FIRST CITY
        else if(gp.currentMap ==2){
        //labo door
            if(hit(35,15, "up")== true){
                if(gp.currentState != 17 && gp.currentState != 18 && gp.currentState != 19){
                    if(gp.currentState <33 || gp.currentState>= 41){
                        teleport(3, 29.5, 41.5, "up");
                    }
                }
                if(gp.currentState >=33 && gp.currentState <41){
                    stopMap(gp.dialogueState, gp.ui.currentChatStep, 4);
                }
            }
            //house door
            else if(hit(48,15, "up")== true){teleport(1, 30.5, 46.6, "up");}
            //to the forest
            else if(hit(52,0, "up")== true 
            || hit(53,0, "up")== true 
            || hit(54,0, "up")== true 
            || hit(55,0, "up")== true 
            || hit(56,0, "up")== true
            || hit(57,0, "up")== true
            || hit(58,0, "up")== true){
            if(gp.level >= 2){ 
                teleport(4, 55, 62.5, "up");
            }
                else if(gp.level < 2){
                    stopMapFirstCity(gp.dialogueState, gp.ui.currentChatStep);
                    if(gp.gameState == gp.playState){
                        double coll = gp.player.worldX/gp.tileSize;
                        double roww = 3;
                        teleport(2, coll, roww, "down");
                    }
                }
            }

            //event after avatar creation FIRST fight
            else if(hit(37,16, "any") == true 
            || hit(36,17, "any") == true 
            || hit(35,17, "any") == true 
            || hit(34,17, "any") == true 
            || hit(33,16, "any") == true)
            {   
            if(gp.currentState == 17){
                gp.keyH.rightPressed = false;
                gp.keyH.leftPressed = false;
                gp.keyH.upPressed = false;
                gp.keyH.downPressed = false;
                gp.gameState = gp.cinematicState;
                gp.currentState = 18;
                gp.aSetter.setNPC();
            }
            if(gp.currentState == 19){
                gp.keyH.upPressed = true;
                startFirstFightWithRival(gp.dialogueState, gp.ui.currentChatStep);
                }
                if(gp.BattleStep == 1){
                    teleportBattle(6, 30, 30, "down");
                }
                if(gp.currentState == 22){
                    gp.keyH.downPressed = false;
                    endFirstFightWithRival(gp.dialogueState, gp.ui.currentChatStep);
                }
            }
        }

        //MAIN ROOM
        else if(gp.currentMap == 0){
            //door
            if(hit(27,46, "down")== true || 
            hit(28,46, "down")== true ){
                if(gp.currentState > 1 ){
                    teleport(1, 32.5, 39, "down");
                }
            }
            //computer
            else if(hit(24,40, "up")== true){
                if(gp.currentState >=24 && gp.keyH.enterPressed == true ){
                    gp.gameState = gp.fightState;
                    gp.keyH.enterPressed = false;
                    teleport(7, 51, 48, "down");
                    gp.playMusic(22);
                }
            }
            //currentstate 0 first quest
            else if(hit(27,44, "any")== true
                || hit(28,44, "any")== true 
                || hit(26,45, "any")== true
                || hit(26,43, "any")== true
                && gp.currentState == 1){
                if(gp.currentState==1){
                    dialogueFirstRoom(gp.dialogueState, gp.ui.currentChatStep);
                    }
            }
        }
            
        //HOUSE FIRST FLOOR
        else if(gp.currentMap == 1){
            //door
            if(hit(30,47, "down")== true){teleport(2, 48, 15, "down");}
            else if(hit(31,47, "down")== true){teleport(2, 48, 15, "down");}
            else if(hit(29,47, "down")== true){teleport(2, 48, 15, "down");}
            //stairs
            else if(hit(32,40, "up")== true){teleport(0, 27.5, 45.5, "up");}
            else if(hit(33,40, "up")== true){teleport(0, 27.5, 45.5, "up");}
        }

        //TRAINING AREA
        else if(gp.currentMap == 7){
            //tp river up and down
            if(hit(51,31, "any")== true){teleport(7, 51, 15, "up");}
            else if(hit(51,17, "any")== true){teleport(7, 51, 33, "down");}

            //tp back to room
            else if(hit(51,50, "any")== true){
                if(gp.currentState >=24 && gp.level >=2){ //&& gp.keyH.enterPressed == true
                    gp.playerPV = gp.maxPV;
                    gp.keyH.enterPressed = false;
                    gp.gameState = gp.playState;
                    teleport(0, 24, 40, "down");
                    gp.playMusic(1);
                }
            }
        }

        //MAP LABO SECOND FLOOR
        else if(gp.currentMap == 5){
            // Door 
            if(hit(28,40, "down")== true || hit(29,40, "down")== true 
            || hit(30,40, "down")== true
            || hit(31,40, "down")== true)
            {
                teleport(3, 29.5, 30, "down");
            }

            //POSTION UP FROM PROF
            else if(hit(26,30, "left")== true 
            || hit(26,31, "left")== true 
            || hit(26,32, "left")== true
            || hit(26,33, "left")== true
            ||hit(26,34, "down")== true
            )
            {
                if(gp.currentState == 12){
                    gp.gameState =gp.cinematicState;
                    gp.keyH.downPressed = true;
                }
            }
            //POSTION DOWN FROM PROF
            else if(hit(26,37, "left")== true
            || hit(26,38, "left")== true
            || hit(26,36, "up")== true)
                {
                    if(gp.currentState == 12){
                        gp.gameState =gp.cinematicState;
                        gp.keyH.upPressed = true;
                    }
                }

            else if(hit(26,35, "any")== true
            || hit(27,35, "any")== true
            || hit(25,35, "any")== true
            ){
                if(gp.currentState ==12){
                    gp.player.direction = "left";
                    profSecondFloorLabop1(gp.dialogueState, gp.ui.currentChatStep); 
                }
                if(gp.currentState ==16){
                    gp.player.direction = "left";
                    profSecondFloorLabop2(gp.dialogueState, gp.ui.currentChatStep); 
                }                    
            }                 
        }

        //MAP LABO FIRST FLOOR
        else if(gp.currentMap == 3){
            //cinematic
            if(gp.currentState == 3 || gp.currentState == 6){
                gp.keyH.leftPressed = false;
                gp.keyH.rightPressed = false;
            }

            //stairs
            if(hit(28,32, "up")== true || hit(29,32, "up")== true || hit(30,32, "up")== true)
            {
                if(gp.currentState<12){
                stopMapLabo(gp.dialogueState, gp.ui.currentChatStep);
                // stopMapLabo
                }
            }
            
            else if(hit(28,30, "up")== true || hit(29,30, "up")== true || hit(30,30, "up")== true)
            {
                if(gp.currentState>=12){
                teleport(5, 29.5, 39.5, "up");
                }
            }

            //OUT door
            else if(hit(29,42, "down") == true || hit(30,42, "down") == true|| hit(28,42, "down")== true){
                if(gp.currentState ==11 || gp.currentState ==12){
                    stopMapLaboOut(gp.dialogueState, gp.ui.currentChatStep);
                }
                else{teleport(2, 35, 15, "down");}
            }

            //entry to house cinematic with prof for 2 quest
            else if(hit(29,42, "any") == true || hit(30,42, "any") == true|| hit(28,42, "any")== true){
                if(gp.currentState == 3 || gp.currentState == 6){
                    gp.gameState = gp.cinematicState;
                }
                else if(gp.currentState == 6){
                    gp.gameState = gp.cinematicState;
                }
            }

            //dialogues with the professor
            else if(hit(29,36, "any") == true || hit(30,36, "any") == true|| hit(28,36, "any")== true){
                if(gp.currentState <5){
                    if(gp.currentState == 3){
                        profIntro1(gp.dialogueState, gp.ui.currentChatStep);
                
                    }
                }
                else if(gp.currentState==6){
                    profIntro2p1(gp.dialogueState, gp.ui.currentChatStep);
                }
                else if(gp.currentState==9){
                    profIntro2p2(gp.dialogueState, gp.ui.currentChatStep);
                }
            }
        }
        
        //FOREST
        else if(gp.currentMap == 4){
            //back to the first main board
            if(hit(50,63, "down")== true
            ||hit(51,63, "down")== true
            ||hit(52,63, "down")== true
            ||hit(53,63, "down")== true
            ||hit(54,63, "down")== true
            ||hit(55,63, "down")== true
            ||hit(56,63, "down")== true
            ||hit(57,63, "down")== true
            ||hit(58,63, "down")== true
            ||hit(59,63, "down")== true
            ||hit(60,63, "down")== true
            ||hit(61,63, "down")== true
            ||hit(62,63, "down")== true
            ){  
                if(gp.currentState != 33){
                    teleport(2, 55, 0, "down");
                }
                else if(gp.currentState == 33){
                    int wichWay =1;
                    stopMap(gp.dialogueState, gp.ui.currentChatStep, wichWay);
                }
            }

            //Go to the top map
            else if(hit(16,0, "up")== true
            ||hit(17,0, "up")== true
            ||hit(18,0, "up")== true
            ||hit(19,0, "up")== true
            ||hit(20,0, "up")== true
            ||hit(21,0, "up")== true
            ||hit(22,0, "up")== true
            ||hit(23,0, "up")== true
            ){
                teleport(8, 20, 62, "up");
            }
            
            //Go to the right map
            else if(hit(63,14, "right")== true
            ||hit(63,15, "right")== true
            ||hit(63,16, "right")== true
            ||hit(63,17, "right")== true
            ||hit(63,18, "right")== true
            ||hit(63,19, "right")== true
            ||hit(63,20, "right")== true
            ){
                if(gp.currentState > 41){
                    //OTHER MAP
                }
                else if(gp.currentState >= 33){
                    int wichWay =3;
                    stopMap(gp.dialogueState, gp.ui.currentChatStep, wichWay);
                }
            }
            //Go to the left map
            else if(hit(0,21, "left")== true
            ||hit(0,22, "left")== true
            ||hit(0,23, "left")== true
            ||hit(0,24, "left")== true
            ||hit(0,25, "left")== true
            ||hit(0,26, "left")== true
            ||hit(0,27, "left")== true
            ||hit(0,28, "left")== true
            ||hit(0,20, "left")== true
            ){
                if(gp.currentState > 41){
                    //OTHER MAP
                }
                else if(gp.currentState >= 33){
                    int wichWay =2;
                    stopMap(gp.dialogueState, gp.ui.currentChatStep, wichWay);
                }
            }

            //Forest quest with explosion 1  
            else if(hit(50,57, "any")== true
            ||hit(51,57, "any")== true
            ||hit(52,57, "any")== true
            ||hit(53,57, "any")== true
            ||hit(54,57, "any")== true
            ||hit(55,57, "any")== true
            ||hit(56,57, "any")== true
            ||hit(57,57, "any")== true
            ||hit(58,57, "any")== true
            ){
                if(gp.currentState ==24 ){
                    gp.playSE(23);
                    gp.gameState = gp.cinematicState;
                    
                    gp.currentState =25;
                    gp.aSetter.setNPC();
                    gp.aEntity.onPath =true;

                    gp.keyH.upPressed = false;
                    gp.keyH.downPressed = false;
                    gp.keyH.leftPressed = false;
                    gp.keyH.rightPressed = false;
                    gp.playMusic(11);
                }
            }  
            //Forest quest with explosion 2  
            else if(hit(50,58, "any")== true
            ||hit(51,58, "any")== true
            ||hit(52,58, "any")== true
            ||hit(53,58, "any")== true
            ||hit(54,58, "any")== true
            ||hit(55,58, "any")== true
            ||hit(56,58, "any")== true
            ||hit(57,58, "any")== true
            ||hit(58,58, "any")== true
            ){
                if(gp.currentState ==32 ){
                    explosionInTheForest(gp.dialogueState, gp.ui.currentChatStep);
                    gp.keyH.upPressed = false;
                    gp.keyH.downPressed = false;
                    gp.keyH.leftPressed = false;
                    gp.keyH.rightPressed = false;
                }
            }
        } 
   
        //DATA CENTER MAP
        else if(gp.currentMap == 8){
            //quest with old man
            if(hit(31,32, "up")== true
            ||hit(32,32, "up")== true
            ||hit(33,32, "up")== true
            ||hit(34,32, "up")== true
            ||hit(35,32, "up")== true
            ||hit(36,32, "up")== true
            ){
                if(gp.currentState == 33){
                    gp.currentState = 34;
                    gp.gameState = gp.playState;  
                    gp.keyH.downPressed = false;
                    gp.keyH.leftPressed = false;
                    gp.keyH.rightPressed = false;
                    gp.keyH.upPressed = true;
                }
            }
            else if(hit(31,19, "up")== true){
                if(gp.currentState ==38){
                    InFrontOfDataCenter(gp.dialogueState, gp.ui.currentChatStep);
                }
            }
            //go to the forest
            else if(hit(20,63, "down")== true
            ||hit(21,63, "down")== true
            ||hit(19,63, "down")== true
            ||hit(22,63, "down")== true
            ){
                teleport(4, gp.player.worldX/gp.tileSize, 2, "down");
            }

            //go inside datacenter
            else if(hit(31,16, "up")== true){
                teleport(9, 29.5, 29, "up");
            }

            //Go inside house 1
            else if(hit(13,24, "up")== true){
                teleport(10, 24.5, 29, "up");
            }
            //Go inside house 2
            else if(hit(57,12, "up")== true){
                teleport(11, 24.5, 29, "up");
            }
        }
        //small house 1 datalona
        else if(gp.currentMap ==10){
            if(hit(24,30, "down")== true
            ||hit(23,30, "down")== true
            ||hit(25,30, "down")== true
            ){
                teleport(8, 13, 24, "down");
            }
        }
        //small house 2 datalona
        else if(gp.currentMap ==11){
            if(hit(24,30, "down")== true
            ||hit(23,30, "down")== true
            ||hit(25,30, "down")== true
            ){
                teleport(8, 57, 12, "down");
            }
        }
        //INSIDE the DATACENTER
        else if(gp.currentMap == 9){
            if(hit(27,30, "down")== true
            ||hit(28,30, "down")== true
            ||hit(29,30, "down")== true
            ||hit(30,30, "down")== true
            ||hit(31,30, "down")== true
            ){
                teleport(8, 31, 16, "down");
            }
            //clue mission
            if(gp.clueCounter ==3 && gp.currentState == 40){
                if(hit(26,21, "any")== true
                ||hit(27,21, "any")== true
                ||hit(28,21, "any")== true
                ||hit(29,21, "any")== true
                ||hit(30,21, "any")== true
                ||hit(31,21, "any")== true
                ||hit(31,20, "any")== true
                ||hit(31,19, "any")== true
                ||hit(31,18, "any")== true
                ){
                    ClueDiscussion(gp.dialogueState, gp.ui.currentChatStep);              
                }
            }
        }
        
    }   

    public boolean hit(int col, int row, String reqDirection){

        boolean hit = false;

        //convertion from double to int
        int worldXint=(int)gp.player.worldX; 
        int worldYint=(int)gp.player.worldY; 

        gp.player.solidArea.x = worldXint + gp.player.solidArea.x;
        gp.player.solidArea.y = worldYint + gp.player.solidArea.y;
        eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row * gp.tileSize + eventRect[col][row].y;

        if(gp.player.solidArea.intersects(eventRect[col][row])){
            if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                hit = true;
            }
        }

        //mise à jours du cube
        gp.player.solidArea.x = (gp.tileSize/4) - gp.tileSize/8;
        gp.player.solidArea.y = gp.tileSize;

        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;
    }
 
    //tp function between maps
    public void teleport(int map, double col, double row, String direction){
        //sound transition
        gp.playSE(2);
        gp.currentMap = map;

        //Data saved
        gp.posAfterTpX = col* gp.tileSize;
        gp.posAfterTpY = row* gp.tileSize;

        gp.player.worldX = gp.tileSize * col;
        gp.player.worldY = gp.tileSize * row;
        gp.player.direction = direction;

        //SELECTION BETWEEN OUTSIDE AND INSIDE
        if(gp.currentMap == 2 || gp.currentMap == 4 || gp.currentMap == 8){
            gp.mapTypeOI = "O";
        }else{
            gp.mapTypeOI = "I";
        }
        musicEvent();
    }

    public void teleportBattle(int map, double col, double row, String direction){
        
        gp.playMusic(17);
        gp.currentMap = map;
        
        gp.player.worldX = gp.tileSize * col;
        gp.player.worldY = gp.tileSize * row;
        gp.player.direction = direction;
    }

    public void musicEvent(){
        if(gp.currentMap != mapSetter && gp.mapTypeOI == "O" && gp.mapTypeOI != changeMusicIfTypeChange){
            
            if(gp.sound.soundURL[0] != null){gp.playMusic(0);}

            changeMusicIfTypeChange= gp.mapTypeOI ;        
                mapSetter = gp.currentMap;
        }

        else if(gp.currentMap != mapSetter && gp.mapTypeOI == "I" && gp.mapTypeOI != changeMusicIfTypeChange){
            
            if(gp.sound.soundURL[1] != null){gp.playMusic(1);}

            changeMusicIfTypeChange= gp.mapTypeOI ;        
                mapSetter = gp.currentMap;
        }
    }

    public void dialogueFirstRoom( int gameState, int currentChatStep){
            gp.gameState = gp.dialogueState;
            gp.EventCHat =1;
            if(gp.sound.soundURL[11] != null && gp.passMusicEvent == 0){gp.playMusic(11);
                gp.ui.currentChatStep = 0;
                gp.passMusicEvent = 1;
            }
            gp.keyH.upPressed = false;
            gp.keyH.downPressed = false;
            gp.keyH.leftPressed = false;
            gp.keyH.rightPressed = false;

            if(gp.ui.currentChatStep == 0){
                gp.ui.currentDialogue = " " + gp.playerName +", DO YOU ONLY WAKE UP NOW !?";
            }
            if(gp.ui.currentChatStep == 1){
                gp.ui.currentDialogue = "Hurry, it's the D-Day";
            }
            if(gp.ui.currentChatStep == 2){
                gp.ui.currentDialogue = "We will finally receive the necessary equipment to travel ! \n See you at the lab.";
            }
            if(gp.keyH.enterPressed == true){
                gp.ui.currentChatStep++;
                gp.keyH.enterPressed = false;
            }
            if(gp.ui.currentChatStep == 3){
                
                gp.gameState = gp.cinematicState;
                
                gp.ui.currentChatStep =0;
                gp.currentState =2;
                gp.EventCHat =0;
                gp.speakWith = 0;

                //music part
                        gp.passMusicEvent = 0;
                
                if(gp.sound.soundURL[1] != null){
                    gp.playMusic(1);
                }
            }
    }

    public void profIntro1( int gameState, int currentChatStep){
        gp.gameState = gp.dialogueState;
        gp.EventCHat =1;

        // procedure to configure the music
        
        if(gp.sound.soundURL[10] != null && gp.passMusicEvent == 0){
            gp.playMusic(10);
            gp.passMusicEvent = 1;
        }

        //  currentState = 1; 
        if(gp.ui.currentChatStep == 0){
            gp.ui.currentDialogue = "Here you are ";
        }
        if(gp.ui.currentChatStep == 1){
            gp.ui.currentDialogue = "Welcome to my lab "+ gp.playerName+ "!";
        }
        if(gp.ui.currentChatStep == 2){
            gp.ui.currentDialogue = "Let me introduce myself";
        }
        if(gp.ui.currentChatStep == 3){
            gp.ui.currentDialogue = "I am Professor Karlos, one of the founding fathers \n of virtual reality ";
        }
        if(gp.ui.currentChatStep == 4){
            gp.ui.currentDialogue = "Throughout my career, I have been able to work \n on the Zeno project, which is becoming more widely \n available today under the name of virtual reality.";
        }
        if(gp.ui.currentChatStep == 5){
            gp.ui.currentDialogue = "Zeno is the heart of everything, \n it has allowed a tremendous advance in our world by facilitating \n the proximity of everything in few steps";
        }
        if(gp.ui.currentChatStep == 6){
            gp.ui.currentDialogue = "New events have been created around Zeno \n and new competitions have appeared!";
        }
        if(gp.ui.currentChatStep == 7){
            gp.ui.currentDialogue = "Now we can find a large number of Zenover \n dreaming of one thing, become the best Zeno fighter \n of all happenst1ce";
        }
        if(gp.ui.currentChatStep == 8){
            gp.ui.currentDialogue = "Each Zenover is unique through his character, \n his specialties and his talent";
        }
        if(gp.ui.currentChatStep == 9){
            gp.ui.currentDialogue = "Be smarter and see further to overcome \n the various challenges that the world has for you";
        }
        if(gp.ui.currentChatStep == 10){
            gp.ui.currentDialogue = "Good news for you !";
        }
        if(gp.ui.currentChatStep == 11){
            gp.ui.currentDialogue = "I finally received all the devices";
        }
        if(gp.ui.currentChatStep == 12){
            gp.ui.currentDialogue = "But";
        }
        if(gp.ui.currentChatStep == 13){
            gp.ui.currentDialogue = "I lost my bag in which are the activation \n code";
        }
        if(gp.ui.currentChatStep == 14){
            gp.ui.currentDialogue = "Last time I saw it, it was in the south of here";

        }
        if(gp.ui.currentChatStep == 15){
            gp.ui.currentDialogue = "Help me to get it back";
        }
        if(gp.keyH.enterPressed == true){
            gp.ui.currentChatStep++;
            gp.keyH.enterPressed = false;
        }
        if(gp.ui.currentChatStep == 16){
            gp.gameState = gp.playState;
            gp.ui.currentChatStep =0;
            gp.EventCHat =0;
            gp.speakWith = 0;
            gp.currentState =5;

            //music part
                gp.passMusicEvent = 0;
            
            if(gp.sound.soundURL[1] != null){
                gp.playMusic(1);
                    }
        }
    }

    public void profIntro2p1( int gameState, int currentChatStep){
        gp.gameState = gp.dialogueState;
        gp.EventCHat =1;
        gp.keyH.upPressed = false;

        // procedure to configure the music
        
        if(gp.sound.soundURL[10] != null && gp.passMusicEvent == 0){gp.playMusic(10);
        gp.passMusicEvent = 1;
        }

        if(gp.ui.currentChatStep == 0){
            gp.ui.currentDialogue = "You found it ?";
        }
        if(gp.ui.currentChatStep == 1){
            gp.ui.currentDialogue = "Great !";
        }
        if(gp.ui.currentChatStep == 2){
            gp.ui.currentDialogue = "To thank you, I leave you this";
        }

        if(gp.keyH.enterPressed == true){
            gp.ui.currentChatStep++;
            gp.keyH.enterPressed = false;
        }
        if(gp.ui.currentChatStep == 3){
            gp.currentState = 8;
            gp.gameState = gp.cinematicState;
            gp.ui.currentChatStep =0;
            gp.EventCHat =0;
            gp.speakWith = 0;
            gp.currentState =8;

        }
    }

    public void profIntro2p2( int gameState, int currentChatStep){
        gp.gameState = gp.dialogueState;
        gp.EventCHat =1;

        if(gp.ui.currentChatStep == 0){
            gp.ui.currentDialogue = "A beautiful ring from ReapO VR !";
        }
        if(gp.ui.currentChatStep == 1){
            gp.ui.currentDialogue = "It will allow you to connect to Zeno";
        }
        if(gp.ui.currentChatStep == 2){
            gp.ui.currentDialogue = "Meet me upstairs when it's okay, to create your avatar";
        }
        if(gp.keyH.enterPressed == true){
            gp.ui.currentChatStep++;
            gp.keyH.enterPressed = false;
        }
        if(gp.ui.currentChatStep == 3){
            gp.gameState = gp.cinematicState;
            gp.ui.currentChatStep =0;
            gp.currentState = 10;
            gp.EventCHat =0;
            gp.speakWith = 0;

            //music part
                gp.passMusicEvent = 0;
        }
    }

    //Allow us to bloc the player
    public void stopMapFirstCity( int gameState, int currentChatStep){
        gp.gameState = gp.dialogueState;
        gp.EventCHat =1;
        //  currentState = 1; 
        if(gp.currentState<= 23){
            if(gp.ui.currentChatStep == 0){
                gp.ui.currentDialogue = "Where are you going ?";
            }
            if(gp.ui.currentChatStep == 1){
                gp.ui.currentDialogue = "The professor still needs you";
            }
        }
        else if(gp.currentState > 23){
            if(gp.ui.currentChatStep == 0){
                gp.ui.currentDialogue = "You are still too low level to leave Calmeria";
            }
            if(gp.ui.currentChatStep == 1){
                gp.ui.currentDialogue = "Try to train yourself";
            }
        }

        if(gp.keyH.enterPressed == true){
            gp.ui.currentChatStep++;
            gp.keyH.enterPressed = false;
        }
        if(gp.ui.currentChatStep == 2){
            gp.gameState = gp.playState;
            gp.ui.currentChatStep =0;
            gp.EventCHat =0;
            gp.speakWith = 0;
        }
    }

    //Allow us to bloc the player
    public void stopMapLabo( int gameState, int currentChatStep){
        gp.gameState = gp.dialogueState;
        gp.EventCHat =1;
        //  currentState = 1; 

         if(gp.ui.currentChatStep == 0){
            gp.ui.currentDialogue = "You can't access here yet";
        }

        if(gp.keyH.enterPressed == true){
            gp.ui.currentChatStep++;
            gp.keyH.enterPressed = false;
        }
        if(gp.ui.currentChatStep == 1){
            gp.gameState = gp.playState;
            gp.ui.currentChatStep =0;
            gp.EventCHat =0;
            gp.speakWith = 0;
            teleport(3, 29.5, 37.5, "up");
        }
    }
    //Allow us to bloc the player
    public void stopMapLaboOut( int gameState, int currentChatStep){
        gp.gameState = gp.dialogueState;
        gp.EventCHat =1;
        //  currentState = 1; 
        if(gp.ui.currentChatStep == 0){
            gp.ui.currentDialogue = "Wait before leaving ";
        }

        if(gp.keyH.enterPressed == true){
            gp.ui.currentChatStep++;
            gp.keyH.enterPressed = false;
        }
        if(gp.ui.currentChatStep == 1){
            gp.gameState = gp.playState;
            gp.ui.currentChatStep =0;
            gp.EventCHat =0;
            gp.speakWith = 0;
            teleport(3, 29.5, 37.5, "up");
        }
    }

    public void profSecondFloorLabop1( int gameState, int currentChatStep){
        gp.gameState = gp.dialogueState;
        gp.EventCHat =1;

        // procedure to configure the music
        
        if(gp.sound.soundURL[10] != null && gp.passMusicEvent == 0){gp.playMusic(10);
        gp.passMusicEvent = 1;
        }

        //  currentState = 1; 
        if(gp.ui.currentChatStep == 0){
            gp.ui.currentDialogue = "Now avatar creation ";
        }
        if(gp.ui.currentChatStep == 1){
            gp.ui.currentDialogue = "You have the choice between three tribes";
        }
        if(gp.ui.currentChatStep == 2){
            gp.ui.currentDialogue = "Orcs, a fantastic, powerful and feared creatures";
        }
        if(gp.ui.currentChatStep == 3){
            gp.ui.currentDialogue = "Pandas, wise and respected creatures but sometimes a little lazy";
        }
        if(gp.ui.currentChatStep == 4){
            gp.ui.currentDialogue = "Last but not least";
        }
        if(gp.ui.currentChatStep == 5){
            gp.ui.currentDialogue = "Anivians, known for their cunning, their agility and their crazy \n mania to ravage everything.";
        }

        if(gp.ui.currentChatStep == 6){
            gp.ui.currentDialogue = " ";
            gp.keyH.upPressed = false;
            gp.keyH.enterPressed = false;
            gp.keyH.downPressed = false;
            gp.keyH.leftPressed = false;

            gp.gameState = gp.cinematicState;
        }


        if(gp.keyH.enterPressed == true && gp.ui.currentChatStep != 6){
            gp.ui.currentChatStep++;
            gp.keyH.enterPressed = false;
        }
        if(gp.ui.currentChatStep == 7){
            gp.keyH.upPressed = false;
            gp.keyH.enterPressed = false;
            gp.keyH.downPressed = false;
            gp.player.direction = "left";

            gp.gameState = gp.cinematicState;
            gp.ui.currentChatStep =0;
            gp.EventCHat =0;
            gp.speakWith = 0;
            gp.currentState =13;
        }
    }

    public void profSecondFloorLabop2( int gameState, int currentChatStep){
        gp.gameState = gp.dialogueState;
        gp.EventCHat =1;

        //  currentState = 1; 
        if(gp.ui.currentChatStep == 0 && gp.tribeValue==1){
            gp.ui.currentDialogue = "The Orcs, a very good choice ! ";
        }
        if(gp.ui.currentChatStep == 0 && gp.tribeValue==2){
            gp.ui.currentDialogue = "The Pandas, a very good choice ! ";
        }
        if(gp.ui.currentChatStep == 0 && gp.tribeValue==3){
            gp.ui.currentDialogue = "The Anivians, a very good choice ! ";
        }

        if(gp.ui.currentChatStep == 1){
            gp.ui.currentDialogue = "To train your avatar, \n a training station has been placed in your room, \n to activate it, nothing could be simpler";
        }
        if(gp.ui.currentChatStep == 2){
            gp.ui.currentDialogue = "Sit on the chair and on the keyboard click on (E) ";
        }
        if(gp.ui.currentChatStep == 3){
            gp.ui.currentDialogue = "I let you go on an adventure, travel, discover... ";
        }
        if(gp.ui.currentChatStep == 4){
            gp.ui.currentDialogue = "Climbing the ladder to become a Master may be a goal !";
        }
        if(gp.ui.currentChatStep == 5){
            gp.ui.currentDialogue = "If you need help, don't hesitate to come back to see me";
        }
        if(gp.ui.currentChatStep == 6){
            gp.ui.currentDialogue = "To know, during your trip you will find training stations, \n they allow you to train but also to have a return point \n in case of defeat";
        }

        if(gp.keyH.enterPressed == true){
            gp.ui.currentChatStep++;
            gp.keyH.enterPressed = false;
        }
        if(gp.ui.currentChatStep == 7){
            gp.keyH.downPressed = false;
            gp.keyH.upPressed = false;
            gp.keyH.leftPressed = false;

            gp.player.direction = "left";

            gp.gameState = gp.playState;
            gp.ui.currentChatStep =0;
            gp.EventCHat =0;
            gp.speakWith = 0;
            gp.currentState =17;

            //music part
                gp.passMusicEvent = 0;
            
            gp.playMusic(1);
            }
    }

    public void startFirstFightWithRival( int gameState, int currentChatStep){
        gp.player.direction = "up";
        gp.gameState = gp.dialogueState;
        gp.EventCHat =1;
        
        if(gp.sound.soundURL[10] != null && gp.passMusicEvent == 0){gp.playMusic(8);
        gp.passMusicEvent = 1;
        }

        //  currentState = 1; 
        if(gp.ui.currentChatStep == 0 ){
            gp.ui.currentDialogue = "Hey wait ";
        }
        if(gp.ui.currentChatStep == 1 ){
            gp.ui.currentDialogue = "You can't leave calmeria without trying your avatar!";
        }
        if(gp.ui.currentChatStep == 2 ){
            gp.ui.currentDialogue = "Let's fight ";
        }

        if(gp.keyH.enterPressed == true){
            gp.ui.currentChatStep++;
            gp.keyH.enterPressed = false;
        }
        if(gp.ui.currentChatStep == 3){
            gp.keyH.downPressed = false;
            gp.keyH.upPressed = false;
            gp.keyH.leftPressed = false;
            gp.keyH.rightPressed = false;

            gp.player.direction = "right";
            positionSAVER();
            gp.BattleStep = 1;
            gp.gameState = gp.cinematicState;

            gp.ui.currentChatStep =0;
            gp.EventCHat =0;
            gp.speakWith = 0;
            gp.currentState =20;

            //music part
                gp.passMusicEvent = 0;
            
            gp.playMusic(17);
            }
    }

    public void endFirstFightWithRival( int gameState, int currentChatStep){
        gp.player.direction = "up";
        gp.gameState = gp.dialogueState;
        gp.EventCHat =1;
        // procedure to configure the music
        
        if(gp.sound.soundURL[10] != null && gp.passMusicEvent == 0){gp.playMusic(8);
        gp.passMusicEvent = 1;
        }

        //  currentState = 1; 
        if(gp.ui.currentChatStep == 0 ){
            gp.ui.currentDialogue = "Oh no... ";
        }
        if(gp.ui.currentChatStep == 1 ){
            gp.ui.currentDialogue = "You were lucky... ";
        }
        if(gp.ui.currentChatStep == 2 ){
            gp.ui.currentDialogue = "But next time you will see "+ gp.playerName +" !";
        }
        if(gp.keyH.enterPressed == true){
            gp.ui.currentChatStep++;
            gp.keyH.enterPressed = false;
        }
        if(gp.ui.currentChatStep == 3){
            gp.keyH.downPressed = false;
            gp.keyH.upPressed = false;
            gp.keyH.leftPressed = false;

            gp.gameState = gp.cinematicState;

            gp.ui.currentChatStep =0;
            gp.EventCHat =0;
            gp.speakWith = 0;
            gp.currentState =23;

            //music part
                gp.passMusicEvent = 0;
            
            gp.playMusic(0);
            }
    }

    public void explosionInTheForest( int gameState, int currentChatStep){
        gp.gameState = gp.dialogueState;
        gp.EventCHat =1;

        //  currentState = 1; 
        if(gp.ui.currentChatStep == 0 ){
            gp.ui.currentDialogue = gp.playerName;
        }
        if(gp.ui.currentChatStep == 1 ){
            gp.ui.currentDialogue = "Did you hear that ?";
        }
        if(gp.ui.currentChatStep == 2 ){
            gp.ui.currentDialogue = "It came from the north, from the data center";
        }
        if(gp.keyH.enterPressed == true){
            gp.ui.currentChatStep++;
            gp.keyH.enterPressed = false;
        }
        if(gp.ui.currentChatStep == 3){
            gp.keyH.downPressed = false;
            gp.keyH.upPressed = false;
            gp.keyH.leftPressed = false;

            gp.player.direction = "up";
            gp.player.orientation = 1;

            gp.gameState = gp.playState;
            gp.ui.currentChatStep =0;
            gp.EventCHat =0;
            gp.speakWith = 0;
            gp.currentState =33;

            //music part
                gp.passMusicEvent = 0;
            
            gp.playMusic(0);
            }
    }

    public void InFrontOfDataCenter( int gameState, int currentChatStep){
        gp.gameState = gp.dialogueState;
        gp.EventCHat =1;
        if(gp.passMusicEvent == 0){
            gp.playMusic(11);
            gp.passMusicEvent = 1;
        }
        //  currentState = 1; 
        if(gp.ui.currentChatStep == 0 ){
            gp.ui.currentDialogue = "I didn't think he would go this far";
        }
        if(gp.ui.currentChatStep == 1 ){
            gp.ui.currentDialogue = "One of Zeno's main hearts has been stolen";
        }
        if(gp.ui.currentChatStep == 2 ){
            gp.ui.currentDialogue = "The heart charged of the security of the Zeno ecosystem";
        }
        if(gp.ui.currentChatStep == 3 ){
            gp.ui.currentDialogue = "We have to do something NOW";
        }
        if(gp.ui.currentChatStep == 4 ){
            gp.ui.currentDialogue = "Hmmm...";
        }
        if(gp.ui.currentChatStep == 5 ){
            gp.ui.currentDialogue = "We have to try to find clues";
        }
        if(gp.keyH.enterPressed == true){
            gp.ui.currentChatStep++;
            gp.keyH.enterPressed = false;
        }
        if(gp.ui.currentChatStep == 6){
            gp.keyH.downPressed = false;
            gp.keyH.upPressed = false;
            gp.keyH.leftPressed = false;

            gp.gameState = gp.cinematicState;
            gp.ui.currentChatStep =0;
            gp.EventCHat =0;
            gp.speakWith = 0;
            gp.currentState =39;

            //music part
            gp.passMusicEvent = 0;
            gp.playMusic(0);
            }
    }
  
    public void ClueDiscussion( int gameState, int currentChatStep){
        gp.gameState = gp.dialogueState;
        gp.EventCHat =1;
        if(gp.passMusicEvent == 0){
            gp.playMusic(10);
            gp.passMusicEvent = 1;
        }
        //  currentState = 1; 
        if(gp.ui.currentChatStep == 0 ){
            gp.ui.currentDialogue = "Oh, hmmm, uh";
        }
        if(gp.ui.currentChatStep == 1 ){
            gp.ui.currentDialogue = "Pieces of crystals, screws, a card of...";
        }
        if(gp.ui.currentChatStep == 2 ){
            gp.ui.currentDialogue = "That's what I suspected.";
        }
        if(gp.ui.currentChatStep == 3 ){
            gp.ui.currentDialogue = "During the development of the Zeno project, we were five.";
        }
        if(gp.ui.currentChatStep == 4 ){
            gp.ui.currentDialogue = "The project was to simplify the world as much as possible in an accessible way for all.";
        }
        if(gp.ui.currentChatStep == 5 ){
            gp.ui.currentDialogue = "But a misunderstanding arose...";
        }
        if(gp.ui.currentChatStep == 6 ){
            gp.ui.currentDialogue = "One of our colleagues absolutely wanted to recover \n all the information and thoughts of the users to resell them.";
        }
        if(gp.ui.currentChatStep == 7 ){
            gp.ui.currentDialogue = "But we didn't agree and then he disappeared to reappear today.";
        }
        if(gp.ui.currentChatStep == 8 ){
            gp.ui.currentDialogue = "That's strange, but we have to find the security core to avoid a disaster.";
        }
        if(gp.ui.currentChatStep == 9 ){
            gp.ui.currentDialogue = "I managed to convert a part of the power of the remaining \n cores to be able to secure the network, but it is not durable, \n and they can deteriorate over time.";
        }
        if(gp.ui.currentChatStep == 10 ){
            gp.ui.currentDialogue = "...";
        }
        if(gp.ui.currentChatStep == 11 ){
            gp.ui.currentDialogue = ".....";
        }
        if(gp.ui.currentChatStep == 11 ){
            gp.ui.currentDialogue = "Thanks for playing this alpha";
        }
        if(gp.ui.currentChatStep == 12 ){
            gp.ui.currentDialogue = "The following quests are not available yet, but I think \n I showed the project idea in a global way.  the happenst1ce world is still \n perfectible but the game engine is set up.";
        }
        if(gp.keyH.enterPressed == true){
            gp.ui.currentChatStep++;
            gp.keyH.enterPressed = false;
        }
        if(gp.ui.currentChatStep == 13){
            gp.keyH.downPressed = false;
            gp.keyH.upPressed = false;
            gp.keyH.leftPressed = false;

            gp.gameState = gp.playState;
            gp.ui.currentChatStep =0;
            gp.EventCHat =0;
            gp.speakWith = 0;
            gp.currentState =41;

            //music part
            gp.passMusicEvent = 0;
            gp.playMusic(1);
        }
    }
  
    //Allow us to bloc the player in the forest
    public void stopMap( int gameState, int currentChatStep, int wichWay){
        gp.gameState = gp.dialogueState;
        gp.EventCHat =1;
        //  currentState = 1; 

        if(gp.ui.currentChatStep == 0){
            if(gp.currentMap == 4){
                if(gp.currentState != 41){
                    gp.ui.currentDialogue = "Hey, wait before leaving";
                }
                if(gp.currentState == 41){
                    gp.ui.currentDialogue = "Thanks for playing this alpha \n the next zone is not available yet ";
                }
            }
            if(gp.currentMap == 2){
                gp.ui.currentDialogue = "The lab seems closed";
            }
        }

        if(gp.keyH.enterPressed == true){
            gp.ui.currentChatStep++;
            gp.keyH.enterPressed = false;
        }
        if(gp.ui.currentChatStep == 1){
            if(wichWay==1){
                teleport(gp.currentMap, gp.player.worldX/gp.tileSize, gp.player.worldY/gp.tileSize-1, "up");
            }
            if(wichWay==2){
                teleport(gp.currentMap, gp.player.worldX/gp.tileSize+1, gp.player.worldY/gp.tileSize, "right");
            }
            if(wichWay==3){
                teleport(gp.currentMap, gp.player.worldX/gp.tileSize-1, gp.player.worldY/gp.tileSize, "left");
            }
            if(wichWay==4){
                teleport(gp.currentMap, gp.player.worldX/gp.tileSize, gp.player.worldY/gp.tileSize+1, "down");
            }
            gp.gameState = gp.playState;
            gp.ui.currentChatStep =0;
            gp.EventCHat =0;
            gp.speakWith = 0;
        }
    }

    public void positionSAVER(){
        gp.BefforFightMap = gp.currentMap;
        gp.BefforFightX = gp.player.worldX;
        gp.BefforFightY = gp.player.worldY;
    }

}
