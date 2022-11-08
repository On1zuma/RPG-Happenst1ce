package com.app.main;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.app.ai.PathFinder;
import com.app.entity.Entity;
import com.app.entity.Player;
import com.app.objects.SuperObject;
import com.app.tile.TileManager;
import java.awt.image.BufferedImage;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;

public class GamePanel  extends JPanel implements Runnable{
    
    //SCREEN SETTINGS
    final int originalTileSize = 16;                                //16x16 tiles 
    final int scale = 4; 

    public  int tileSize = originalTileSize * scale;                //tile display on the screen = zoom
    public  int maxScreenCol = 20;                                  // number of column possible of each tile, verticaly //16
    public  int maxScreenRow = 12;                                  // number of row possible of each tile, horizontaly //12
    public  int screenWidth = tileSize * maxScreenCol;              //768 pixels
    public  int screenHeight = tileSize *  maxScreenRow;            // 576 pixels
    //WORLD SETTINGS
    public final int maxWorldCol = 64;
    public final int maxWorldRow = 64;
    //STOP CAMERA
    public  int worldWidth = 46 * maxWorldCol;
    public  int worldHeight = 46 * maxWorldRow;
    //FULL SCREEN
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    int BufferedImageSet =0;
    //MAX data readed 
    public final int maxMap = 20;
    public final int maxState = 60;
    //PLAYER DATA SAVED
    public int saveSet =0;                                          //check if a save already exist 1 yes 0 no
    public int gameStateSaver;
    public int currentMap = 2;                                      //initial map
    public int currentState = 0;                                    //initial state
    public int genderValue = 1;                                     //initial gender
    public int tribeValue = 0;
    public int level = 1;
    public int maxPV = 100;
    public int playerPV = maxPV;
    public double maxXP = 100;
    public double playerXP = 1;
    public double playerXPPrec = playerXP;
    public String playerName = "";
    public int currentMusicSaver;                                   //last music played AND LAST DATA SAVED
    public int BattleStep = 0;                                      // 1 = battle starting, 2 = battle, 3 = battle end
    //if fightState or playState we save data
    public double savePlayerPosX=0; 
    public double savePlayerPosY=0;
    public int saveMap=0;
    // save player position beffore fight state
    public double BefforFightX=0;
    public double BefforFightY=0;
    public int BefforFightMap=0;
    public int tpAfterfight =0;
    int xpWin =0;                                                   //++xp if xpWin = 1
    public double playerXpNextLVL;                                  //we calculate if player is going to lvl up or not
    public int nameStep=0;                                          //step inside the player creation
    public int KeyHelper =1;                                        // the player has key assists if enabled =1 else no help
    //RESPAWN
    public int respawn =0;                                          //if respawn =1 then monster allowed are gona to respawn
    public int respawnCount =0;                                     //counter before new respawn
    public int setFullScreenSet =0;                                 //1 set full screen else 0 no full screen
    public int fullScreenWarningMessage =setFullScreenSet;          //full screen WARNING text
    public String mapTypeOI;                                        //I/O inside / outside
    //SYSTEM
    int FPS = 60;                                                   //FPS
    Thread gameThread;                                              //add of the clock
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);                  //when we press a key
    Sound sound = new Sound();                                      //General Music and effects
    Sound sound2 = new Sound();                                     //effects set for the settings
    public UI ui = new UI(this);                                    //MESSAGE
    public Player player = new Player(this, keyH);                  //Set Player
    //Our Objects (20 slots of objects display at the same time)
    public SuperObject obj[][][] = new SuperObject[maxState][maxMap][20];
    public SuperObject objWhithoutState[][] = new SuperObject[maxMap][20];
    public Entity npc[][] = new Entity[maxMap][20];                 //NPC
    public Entity monster[][] = new Entity[maxMap][20];             // MONSTER
    public Entity aEntity = new Entity(this);                       //set entity
    public CollisionChecker cChecker = new CollisionChecker(this);  //collision checker
    public EventHandler eHandler = new EventHandler(this);          //set events/check
    public AssetSetter aSetter = new AssetSetter(this);             //reset/set assets
    //backup procedure 
    Config config = new Config(this);
    DataSave dataSave = new DataSave(this);
    public PathFinder pFinder = new PathFinder(this);               //Extension for the pathfinding algo
    public int musicSetter = 0;                                     //allows us to know if a music is launched or not
    //INITIALISATION
    int pass = 0;
    int titleIni = 0;                                               //show logo on launch
    public int npcINI =0;
    public int monsterINI =0;
    public int objectINI =0;
    public int genderIni =-1;
    public int tribeIni =-1;
    public int passMusicEvent =0;                                   //permit us to launch music only one time inside loops
    //ZOOM
    public int zoomLimit = 0;                                       //Zoom Limits
    int zoomIni = 0;
    int zoomPrec= 0;
    //update after a zoom or a passage between areas
    int mapIni = currentMap;
    int mapPrec= currentMap;
    int currentStateIni = currentState;
    int currentChatStepPrec = ui.currentChatStep;
    public int speakWith =0;                                        //to update only dialogue not npc position when we speak with an npc
    public int EventCHat =0;                                        //When dialogue start we update only the dialogue
    public int EventEdge =0;                                        //if the player is at the limit of the map, allow us to disable photoState
    //  GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int cinematicState = 4;
    public final int fightState = 5;
    public final int photoState = 6;
    public final int gameOverState = 7;
    //position saved after a tp
    public double posAfterTpX=tileSize*23;                          //X pos after a teleportation
    public double posAfterTpY=tileSize* 41;                         //Y pos after a teleportation 
    public int clueCounter=0;                                       //Clues counter for quests
    //DEBUG
    public int debug =0;                                            // 1 = debug Set else if 0 debug not set
    public int debugCurrentState =24;                               //24 before forest event// 33 after event //41 end game // 6 new avatar
    public int debugMap =8;                                         // 2 main board // 8 datalona
    public int debugPosX =23;
    public int debugPosY =41;
    public int debugTribV =1;
    public int lvlDebug =1;

    //function called in main
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);                         // improve game randering
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame(){
        //Set the bufferedImage screen
        if(BufferedImageSet == 0){
            tempScreen = new BufferedImage(screenWidth , screenHeight , BufferedImage.TYPE_INT_ARGB);
            g2 = (Graphics2D)tempScreen.getGraphics();
            //set full screen
            if(setFullScreenSet ==1){
                setFullScreen();
            }
            BufferedImageSet = 1;
        }

        if(gameState == playState  || gameState == fightState || gameState == photoState){
            if(speakWith == 0){
            aSetter.setObject();
            aSetter.setNPC();
            aSetter.setMonster();
            }
        }
        if(gameState == dialogueState){
            speakWith =1;
            eHandler.checkEvent();
        }

        if(titleIni == 0){//To launch the game directly into the main screen
            gameState = titleState;
            titleIni = 1;
        }
    }

    public void setFullScreen(){
        //Get Local screen Device
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        //Get full screen Width and height
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }

    public void startGameThread(){
        gameThread = new Thread(this);//this = gamePanel
        gameThread.start();

        //initialisation of tileSize
        if(pass==0){
            setupTileSize();
        }
    }

    @Override
    public void run(){
        // FPS = 60;
        // final double drawInterval = 1000000000/FPS;

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount =0;

        while(gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);

            lastTime = currentTime;
                            
            //we test and test delta and implement it to have that when delta = 1 
            if(delta >= 1){
                update();
                // repaint();
                drawToTempScreen(); //draw everything to the buffered IMAGE
                drawToScreen();// the draw the buffured image to the screen

                delta--; // -- = -1
                drawCount++; // ++ = +1
            }

            if(timer >= 1000000000){
                if(debug ==1){
                    System.out.println("FPS: " + drawCount);
                    System.out.println("  position X col: " + player.worldX/tileSize + "  position Y row: " + player.worldY/tileSize + 
                    " current map: " + currentMap + " currentState: " + currentState
                    + "  currentChatStep  " + ui.currentChatStep + " tribeValue "+ tribeValue + " xpWin " + xpWin  + " ui.commandNum "+ ui.commandNum);
                    System.out.println("tileSize: " + tileSize +" z " + gameState +"BufferedImageSet "+ BufferedImageSet + " gameState " + gameState + " BattleStep "+BattleStep + "mapCounter" + ui.mapCounter);
                    System.out.println("cluecounter: " + clueCounter);

                }

                drawCount = 0;
                timer = 0;
            }
        }
    }
    
    public void update(){
        //respawn systeme
        if(respawn ==1){
            respawnCount++;
            if(respawnCount >=500){
                respawnCount=0;
                respawn =2;
                aSetter.respawn();
            }
        }
        //if player is dead GAME OVER
        if(playerPV <=0 && currentState >= 24){
            ui.currentChatStep = 0;
            checkIfMusicIsSet();
            if(gameState != gameOverState){
                ui.commandNum = 0;
                playSE(19);
            }
            gameState = gameOverState;
        }
        //initialisation of tileSize
        if(pass==0 && gameState == playState){
            setupTileSize();
        }
        //event xp
        if(xpWin ==1){
            xpWin++;
            if(level==1 ){
                playerXP += Math.sqrt(maxXP)*4;
            }
            if(level>1 ){
                playerXP += Math.sqrt(maxXP)*2;
            }
            xpWin=0;
        }
        //lvl UP
        if(playerXPPrec != playerXP){
            playerXpNextLVL = maxXP -playerXP;
            if(playerXpNextLVL <= 0){
                if(playerXP >= maxXP){
                    playSE(20);
                    level = level+1;
                    player.invincible = true;
                    playerXP = (-playerXpNextLVL);
                    maxXP = maxXP+ maxXP/2;
                    playerXPPrec = playerXP;
                }
            }
        }

        //UPDATE GENDER, BOY OR GIRL
        // or if fightState is select we take the good skin
        zoomPrec = zoomLimit;
        mapPrec = currentMap;

        if(genderIni != genderValue || tribeIni != tribeValue || mapIni != mapPrec || currentStateIni != currentState){
            playerSkin();
        }

        //SELECTION BETWEEN OUTSIDE AND INSIDE
        if(mapIni != mapPrec){
            if(currentMap == 2 || currentMap == 4 || currentMap == 8){
                mapTypeOI = "O";
            }else{
                mapTypeOI = "I";
            }
        }
        //SAVE DATA
        if(gameState== playState || gameState == fightState){
            savePlayerPosX = player.worldX;
            savePlayerPosY = player.worldY;
            saveMap = currentMap;
        }

        if(gameState == playState || gameState == fightState || gameState == photoState){
            //PLAYER
            gameStateSaver = gameState;
            player.update();
            //NPC
            for(int i = 0; i< npc[1].length; i++){
                if(npc[currentMap][i] != null){
                    npc[currentMap][i].update();
                }
            }
            // monster
            for(int i = 0; i< monster[1].length; i++){
                if(monster[currentMap][i] != null){
                    if(monster[currentMap][i].alive == true && monster[currentMap][i].dying == false){
                        monster[currentMap][i].update();
                    }else if(monster[currentMap][i].alive == false){
                        if(currentMap != 7 && currentState <24){
                            BattleStep =3;
                            tpAfterfight=1;
                            keyH.downPressed = true;
                            gameState = cinematicState;
                        }
                        if(respawn==0 && currentMap == 7 ){
                            respawn =1;
                        }
                        //xp win
                        xpWin=1;
                        monster[currentMap][i] = null;

                    }
                }
            }
        }

        if(gameState == cinematicState){
            //PLAYER
            player.update();
            
            //NPC
            for(int i = 0; i< npc[1].length; i++){
                if(npc[currentMap][i] != null){
                    npc[currentMap][i].update();
                }
            }
        }

        // UPDATE THE GAME ON CHANGE !
        if(zoomIni != zoomPrec || mapIni != mapPrec || currentStateIni != currentState || currentChatStepPrec != ui.currentChatStep){
            setupGame();
            zoomIni =zoomPrec;
            mapIni = mapPrec;
            currentStateIni = currentState;
            currentChatStepPrec = ui.currentChatStep;

            if(currentState >1){
                //hide movement instruction
                ui.movInstruction =1;
            }
        }
    }

    //first draw
    public void drawToTempScreen(){
    //TITLE SCREEN
    if(gameState == titleState){
        tileM.draw(g2);
        ui.draw(g2);
    }
    //OTHERS
    else if(gameState != titleState){
        //TILE
        tileM.draw(g2);

        //OBJECT //we see if the object is not null, if it's not we draw it
        for(int i = 0; i<obj[2].length; i++){
            if(obj[currentState][currentMap][i] != null && obj[currentMap] != null && obj[i] != null ){
                obj[currentState][currentMap][i].draw(g2, this);
                } 
        }
        //OBJECT without state
        for(int i = 0; i<objWhithoutState[1].length; i++){
            if(objWhithoutState[currentMap][i] != null && objWhithoutState[currentMap] != null && objWhithoutState[i] != null ){
                objWhithoutState[currentMap][i].draw(g2, this);
                } 
        }

        //NPC
        for(int i = 0; i< npc[1].length; i++){
            if(npc[currentMap][i] != null){
                npc[currentMap][i].draw(g2);
            }
        }

        //MONSTER
        for(int i = 0; i< monster[2].length; i++){
            if(monster[currentMap][i] != null){
                monster[currentMap][i].draw(g2);
            }
        }
        //PLAYER
        player.draw(g2);

        //UI
        ui.draw(g2);
        }
    }

    //final draw for the player
    public void drawToScreen(){
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    } 

    //MUSIC
    public void playMusic(int i){
        if(musicSetter ==1 ){
        stopMusic();
        }
        musicSetter = 1;
        sound.setFile(i);
        sound.play();
        sound.loop();
        if(gameState == playState || gameState == fightState){
            currentMusicSaver =i;
        }
    }
    public void stopMusic(){
        if(musicSetter ==1 ){
            sound.stop();
            musicSetter = 0;
        }
    }
    public void playSE(int i){  //playSE play sound2 *
        sound2.setFile(i);
        sound2.play();
    }
    public void playSE2(int i){ //playSE2 play sound1 *
        sound.setFile(i);
        sound.play();
    }
    public void checkIfMusicIsSet(){ //Stop music if the music is set
        if(musicSetter == 1){
            stopMusic();
        }
    }
    
    //zoom in && zoom out limit
    public void zoomLimit(int i ){
        zoomLimit += i;
        System.out.println("zoomLimit: " + zoomLimit);
        if( zoomLimit < -2){
            zoomLimit = -4;
        }
        if( zoomLimit > 34){
            zoomLimit = 36;
        }
    }

    //zoom in && zoom out function
    public void zoomInOut(int i ){
        if( zoomLimit >= -2 && zoomLimit <= 34){
            int oldWorldWidht = tileSize* maxWorldCol; //exp 2400
            tileSize += i;

            int newWorldWidth = tileSize * maxWorldCol; // 2350

            player.speed = (double)newWorldWidth/800;
            
            //solid cube around player
            player.solidArea.x = (tileSize/4) - tileSize/8;
            player.solidArea.y = tileSize;
            player.solidArea.width = tileSize/2 + tileSize/4;
            player.solidArea.height = tileSize/2;


            double multiplier = (double)newWorldWidth/oldWorldWidht;
            
            double newPlaterWorldX = player.worldX * multiplier;
            double newPlaterWorldY = player.worldY * multiplier;

            player.worldX = newPlaterWorldX;
            player.worldY = newPlaterWorldY;
        }
    }

    //TILES SET UP 
    public void setupTileSize(){
        tileSize = 46;
        BufferedImageSet=0;
        setupGame();
        int oldWorldWidht = tileSize* maxWorldCol; //exp 2400
        int newWorldWidth = tileSize * maxWorldCol; // 2350
        
        //set limits
        worldWidth = tileSize * maxWorldCol;
        worldHeight = tileSize * maxWorldRow;

        player.defaultSpeed = (double)newWorldWidth/800;
        player.speed = player.defaultSpeed;

        //solid cube around player
        player.solidArea.x = (tileSize/4) - tileSize/8;
        player.solidArea.y = tileSize;
        player.solidArea.width = tileSize/2 + tileSize/4;
        player.solidArea.height = tileSize/2;

        double multiplier = (double)newWorldWidth/oldWorldWidht;
        
        double newPlaterWorldX = player.worldX * multiplier;
        double newPlaterWorldY = player.worldY * multiplier;

        player.worldX = newPlaterWorldX;
        player.worldY = newPlaterWorldY;

        if(pass==0){
            pass = 2;
        }
    }

    public void playerSkin(){

        if(gameState == playState || gameState == cinematicState){
            // girl
            if(genderValue ==2 && genderIni != genderValue){
                player.up0 = player.up0g;
                player.up1 = player.up1g;
                player.up2 = player.up2g;
                player.up3 = player.up3g;

                player.down0 = player.down0g;
                player.down1 = player.down1g;
                player.down2 = player.down2g;
                player.down3 = player.down3g;

                player.left0 = player.left0g;
                player.left1 = player.left1g;
                player.left2 = player.left2g;
                player.left3 = player.left3g;

                player.right0 = player.right0g;
                player.right1 = player.right1g;
                player.right2 = player.right2g;
                player.right3 = player.right3g;

                player.face0 = player.face0g;
                genderIni = genderValue;
                tribeIni =-1;
            }
            // boy
            else if(genderValue ==1 && genderIni != genderValue){
                player.up0 = player.up0b;
                player.up1 = player.up1b;
                player.up2 = player.up2b;
                player.up3 = player.up3b;

                player.down0 = player.down0b;
                player.down1 = player.down1b;
                player.down2 = player.down2b;
                player.down3 = player.down3b;

                player.left0 = player.left0b;
                player.left1 = player.left1b;
                player.left2 = player.left2b;
                player.left3 = player.left3b;

                player.right0 = player.right0b;
                player.right1 = player.right1b;
                player.right2 = player.right2b;
                player.right3 = player.right3b;

                player.face0 = player.face0b;
                genderIni = genderValue;
                tribeIni =-1;
            }
        }
        // orc
        else if(gameState == fightState){
            if(tribeValue ==1 && tribeIni != tribeValue ){
                // ATTACK
                player.attackUp1 = player.attackUp1o;
                player.attackUp2 = player.attackUp2o;

                player.attackDown1 = player.attackDown1o;
                player.attackDown2 = player.attackDown2o;

                player.attackLeft1 = player.attackLeft1o;
                player.attackLeft2 = player.attackLeft2o;

                player.attackRight1 = player.attackRight1o;
                player.attackRight2 = player.attackRight2o;

                //WALK
                player.up0 = player.up0o;
                player.up1 = player.up1o;
                player.up2 = player.up2o;
                player.up3 = player.up3o;

                player.down0 = player.down0o;
                player.down1 = player.down1o;
                player.down2 = player.down2o;
                player.down3 = player.down3o;

                player.left0 = player.left0o;
                player.left1 = player.left1o;
                player.left2 = player.left2o;
                player.left3 = player.left3o;

                player.right0 = player.right0o;
                player.right1 = player.right1o;
                player.right2 = player.right2o;
                player.right3 = player.right3o;

                tribeIni = tribeValue;
                genderIni =-1;

            }
            // panda
            else if(tribeValue ==2 && tribeIni != tribeValue){
                //ATTACK
                player.attackUp1 = player.attackUp1p;
                player.attackUp2 = player.attackUp2p;

                player.attackDown1 = player.attackDown1p;
                player.attackDown2 = player.attackDown2p;

                player.attackLeft1 = player.attackLeft1p;
                player.attackLeft2 = player.attackLeft2p;

                player.attackRight1 = player.attackRight1p;
                player.attackRight2 = player.attackRight2p;
                //WALK
                player.up0 = player.up0p;
                player.up1 = player.up1p;
                player.up2 = player.up2p;
                player.up3 = player.up3p;

                player.down0 = player.down0p;
                player.down1 = player.down1p;
                player.down2 = player.down2p;
                player.down3 = player.down3p;

                player.left0 = player.left0p;
                player.left1 = player.left1p;
                player.left2 = player.left2p;
                player.left3 = player.left3p;

                player.right0 = player.right0p;
                player.right1 = player.right1p;
                player.right2 = player.right2p;
                player.right3 = player.right3p;

                tribeIni = tribeValue;
                genderIni =-1;

            }
            // avian
            else if(tribeValue ==3 && tribeIni != tribeValue && gameState == fightState){
                //ATTACK
                player.attackUp1 = player.attackUp1a;
                player.attackUp2 = player.attackUp2a;

                player.attackDown1 = player.attackDown1a;
                player.attackDown2 = player.attackDown2a;

                player.attackLeft1 = player.attackLeft1a;
                player.attackLeft2 = player.attackLeft2a;

                player.attackRight1 = player.attackRight1a;
                player.attackRight2 = player.attackRight2a;
                //WALK

                player.up0 = player.up0a;
                player.up1 = player.up1a;
                player.up2 = player.up2a;
                player.up3 = player.up3a;

                player.down0 = player.down0a;
                player.down1 = player.down1a;
                player.down2 = player.down2a;
                player.down3 = player.down3a;

                player.left0 = player.left0a;
                player.left1 = player.left1a;
                player.left2 = player.left2a;
                player.left3 = player.left3a;

                player.right0 = player.right0a;
                player.right1 = player.right1a;
                player.right2 = player.right2a;
                player.right3 = player.right3a;

                tribeIni = tribeValue;
                genderIni =-1;
            }
        }
    }

}



