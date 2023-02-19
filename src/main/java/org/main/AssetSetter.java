package org.main;

import org.entity.NPC_OldMan;
import org.entity.NPC_OldManForest;
import org.entity.NPC_Mother;
import org.entity.NPC_Blocker;
import org.entity.NPC_Datolana1;
import org.entity.NPC_Datolana2;
import org.entity.NPC_Datolana3;
import org.entity.NPC_Datolana4;
import org.entity.NPC_FirstCity1;
import org.entity.NPC_FirstCity2;
import org.entity.NPC_FirstCity3;
import org.entity.NPC_ForestMenRoad1;
import org.entity.NPC_ForestRoad1Animal;
import org.entity.NPC_Rival;
import org.entity.NPC_RivalForest;
import org.entity.NPC_RivalLabo;
import org.entity.NPC_TrainingMap1;
import org.entity.NPC_TrainingMap2;
import org.objects.OBJ_Box;
import org.objects.OBJ_Clue;
import org.objects.OBJ_Bag;

import org.monster.MON_RivalM;
import org.monster.MON_SlimeB;
import org.monster.MON_SlimeG;
import org.monster.MON_SlimeR;
import org.monster.MON_SlimeW;

import org.objects.OBJ_Computerbd;
import org.objects.OBJ_Computerbg;
import org.objects.OBJ_Computerhd;
import org.objects.OBJ_Computerhg;

public class AssetSetter {
    GamePanel gp;
    
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){
        if(gp.objectINI == 0){
            gp.obj[9][3][0] = new OBJ_Box();
            gp.obj[10][3][0] = new OBJ_Box();
            gp.obj[11][3][0] = new OBJ_Box();

            gp.obj[5][2][5] = new OBJ_Bag();
            //computer
            gp.objectINI = 1;
        }
        //box           public SuperObject obj[][][] = new SuperObject[maxState][maxMap][20];
        //OBJ_Box
        if(gp.currentState >= 24){
            gp.objWhithoutState[0][1] = new OBJ_Computerbd();
            gp.objWhithoutState[0][2] = new OBJ_Computerbg();
            gp.objWhithoutState[0][3] = new OBJ_Computerhg();
            gp.objWhithoutState[0][4] = new OBJ_Computerhd();
        }

        //clues
        if(gp.currentState == 40){
            if(gp.clueCounter ==0){
                gp.obj[40][8][0] = new OBJ_Clue();
                gp.obj[40][8][1] = new OBJ_Clue();
                gp.obj[40][9][0] = new OBJ_Clue();   
            }


            //first clue
            if(gp.obj[40][8][0] != null){
                gp.obj[40][8][0].worldX = gp.tileSize * 53;
                gp.obj[40][8][0].worldY = gp.tileSize * 42;  
            }


            //second clue
            if(gp.obj[40][8][1] != null){
                gp.obj[40][8][1].worldX = gp.tileSize * 50;
                gp.obj[40][8][1].worldY = gp.tileSize * 9;
            }

            //third clue
            if(gp.obj[40][9][0] != null){
                gp.obj[40][9][0].worldX = gp.tileSize * 32;
                gp.obj[40][9][0].worldY = gp.tileSize * 26;
            }
        }

        gp.obj[9][3][0].worldX = gp.tileSize * 28;
        gp.obj[9][3][0].worldY = gp.tileSize * 35.5;
        gp.obj[10][3][0].worldX = gp.tileSize * 28;
        gp.obj[10][3][0].worldY = gp.tileSize * 35.5;
        gp.obj[11][3][0].worldX = gp.tileSize * 28;
        gp.obj[11][3][0].worldY = gp.tileSize * 35.5;

        //OBJ_Bag
        gp.obj[5][2][5].worldX = gp.tileSize * 56;
        gp.obj[5][2][5].worldY = gp.tileSize * 50;

        //desk with computer
        // OBJ_Computerbd
        if(gp.objWhithoutState[0][1] != null){
            gp.objWhithoutState[0][1].worldX = gp.tileSize * 25;
            gp.objWhithoutState[0][1].worldY = gp.tileSize * 40;   

            // OBJ_Computerbg
            gp.objWhithoutState[0][2].worldX = gp.tileSize * 24;
            gp.objWhithoutState[0][2].worldY = gp.tileSize * 40;   

            // OBJ_Computerhg
            gp.objWhithoutState[0][3].worldX = gp.tileSize * 24;
            gp.objWhithoutState[0][3].worldY = gp.tileSize * 39;   

            // OBJ_Computerhd
            gp.objWhithoutState[0][4].worldX = gp.tileSize * 25;
            gp.objWhithoutState[0][4].worldY = gp.tileSize * 39;  
        }
    }

    public void setNPC(){ //map/number npc
        if(gp.npcINI == 0){
            gp.npc[3][0] = new NPC_OldMan(gp);
            gp.npc[3][1] = new NPC_RivalLabo(gp);

            gp.npc[1][1] = new NPC_Mother(gp);
            
            //labo second floor
            gp.npc[5][0] = new NPC_OldMan(gp);

            gp.npc[2][0] = new NPC_Blocker(gp);
            gp.npc[2][1] = new NPC_FirstCity1(gp);
            gp.npc[2][2] = new NPC_FirstCity2(gp);
            gp.npc[2][3] = new NPC_FirstCity3(gp);

            if(gp.currentState >=33){
                gp.npc[4][0] = new NPC_ForestMenRoad1(gp);
                gp.npc[4][1] = new NPC_ForestRoad1Animal(gp);
            }

            gp.npc[8][0] = new NPC_ForestRoad1Animal(gp);
            gp.npc[8][1] = new NPC_Datolana1(gp);
            gp.npc[8][2] = new NPC_Datolana2(gp);
            gp.npc[8][3] = new NPC_Datolana3(gp);
            
            gp.npc[10][0] = new NPC_Datolana4(gp);

            //training MAP
            gp.npc[7][0] = new NPC_TrainingMap1(gp);
            gp.npc[7][1] = new NPC_TrainingMap2(gp);
            gp.npcINI = 1;

        }
        
        //Training MAP
        //War
        if(gp.npc[7][0] != null){
            gp.npc[7][0].worldX = gp.tileSize * 53;
            gp.npc[7][0].worldY = gp.tileSize * 5;
        }
        //Orc
        if(gp.npc[7][1] != null){
            gp.npc[7][1].worldX = gp.tileSize * 54;
            gp.npc[7][1].worldY = gp.tileSize * 42;
        }
        
        //DataLona
        //red girl inside house
        if(gp.npc[10][0] != null){
            gp.npc[10][0].worldX = gp.tileSize * 23;
            gp.npc[10][0].worldY = gp.tileSize * 24;
        }
        //squirrel
        if(gp.npc[8][0] != null){
            gp.npc[8][0].worldX = gp.tileSize * 51;
            gp.npc[8][0].worldY = gp.tileSize * 58;
        }
        //yellow women
        if(gp.npc[8][1] != null){
            gp.npc[8][1].worldX = gp.tileSize * 11;
            gp.npc[8][1].worldY = gp.tileSize * 27;
        }
        //red black men
        if(gp.npc[8][2] != null){
            gp.npc[8][2].worldX = gp.tileSize * 45;
            gp.npc[8][2].worldY = gp.tileSize * 44;
        }
        //spy
        if(gp.npc[8][3] != null){
            gp.npc[8][3].worldX = gp.tileSize * 38;
            gp.npc[8][3].worldY = gp.tileSize * 16;
        }
        //old man
        if(gp.currentState >= 33 && gp.currentState <= 38){
            gp.npc[8][4] = new NPC_OldManForest(gp);
            gp.npc[8][4].worldX = gp.tileSize * 31;
            gp.npc[8][4].worldY = gp.tileSize * 18;
        }
        if(gp.currentState < 33 || gp.currentState > 38){
            gp.npc[8][4] = null;
        }

        //INSIDE data center
        //old man
        if(gp.currentState ==40){
            gp.npc[9][0] = new NPC_OldManForest(gp);
            gp.npc[9][0].worldX = gp.tileSize * 28;
            gp.npc[9][0].worldY = gp.tileSize * 20;
        }
        if(gp.currentState != 40){
            gp.npc[9][0] = null;
        }

        //FOREST 1
        //men walking
        if(gp.npc[4][0] != null){
            gp.npc[4][0].worldX = gp.tileSize * 13;
            gp.npc[4][0].worldY = gp.tileSize * 51;
        }

        //squirrel
        if(gp.npc[4][1] != null){
            gp.npc[4][1].worldX = gp.tileSize * 40;
            gp.npc[4][1].worldY = gp.tileSize * 16;
        }

        // NPC_OldManForest
        if(gp.currentState != 25){
            gp.npc[4][2] = null;
        }
        if(gp.currentState == 25){
            gp.npc[4][2] = new NPC_OldManForest(gp);
            gp.npc[4][2].worldX = gp.tileSize * gp.player.worldX/gp.tileSize;
            gp.npc[4][2].worldY = gp.tileSize * 62.5;
        }

        // NPC_RivalForest
        if(gp.currentState <31 || gp.currentState> 33){
            gp.npc[4][3] = null;
        }
        if(gp.currentState >= 31 && gp.currentState <= 33){
            if(gp.npc[4][3] ==null){
                gp.npc[4][3] = new NPC_RivalForest(gp);
                gp.npc[4][3].worldX = gp.tileSize * gp.player.worldX/gp.tileSize;
                gp.npc[4][3].worldY = gp.tileSize * 62.5;
            }
        }

        if(gp.currentState == 11){
            gp.npc[3][0] = null;
        }
        
        if(gp.currentState < 11 && gp.npc[3][0] != null){
            // NPC_OldMan
            gp.npc[3][0].worldX = gp.tileSize * 29.5;
            gp.npc[3][0].worldY = gp.tileSize * 35;
        }

        //FIRST CITY
        // npc rival labo
        gp.npc[3][1].worldX = gp.tileSize * 37;
        gp.npc[3][1].worldY = gp.tileSize * 32;

        // NPC_OldMan MAP 5 second labo floor
        gp.npc[5][0].worldX = gp.tileSize * 25.5;
        gp.npc[5][0].worldY = gp.tileSize * 35;

        // NPC_Mother
        gp.npc[1][1].worldX = gp.tileSize * 24;
        gp.npc[1][1].worldY = gp.tileSize * 34;
        
        // NPC_Rival
        if(gp.currentState == 0){
            gp.npc[0][0] = new NPC_Rival(gp);
            gp.npc[0][0].worldX = gp.tileSize * 27.5;
            gp.npc[0][0].worldY = gp.tileSize * 44.86;
        }
        if(gp.currentState == 3){
            gp.npc[0][0] = null;
        }
        
        // NPC_Blocker
        if( gp.npc[2][0] != null){
        gp.npc[2][0].worldX = gp.tileSize * 59;
        gp.npc[2][0].worldY = gp.tileSize * 0.5;
        }

        // NPC_FirstCity1
        gp.npc[2][1].worldX = gp.tileSize * 46;
        gp.npc[2][1].worldY = gp.tileSize * 30.1;

        // NPC_FirstCity2
        gp.npc[2][2].worldX = gp.tileSize * 45;
        gp.npc[2][2].worldY = gp.tileSize * 30.1;

        // NPC_FirstCity3
        gp.npc[2][3].worldX = gp.tileSize * 37;
        gp.npc[2][3].worldY = gp.tileSize * 24;

        if(gp.currentState == 18 || gp.currentState == 19 || gp.currentState == 20){
            gp.npc[2][4] = new NPC_RivalLabo(gp);
        }
        if( gp.npc[2][4] !=null){
            gp.npc[2][4].worldX = gp.tileSize * 35;
            gp.npc[2][4].worldY = gp.tileSize * 15;
        }
        if(gp.currentState >= 23){
            gp.npc[2][4] =null;
            if(gp.currentState > 24){
                gp.npc[2][0]= null;
            }
        }
    }

    public void setMonster(){
        // public Entity monster[][] = new Entity[maxMap][20];
        if(gp.monsterINI == 0){
            gp.monster[6][0] = new MON_RivalM(gp);
            gp.monsterINI =1;

            gp.monster[7][0] = new MON_SlimeB(gp);
            gp.monster[7][1] = new MON_SlimeR(gp);
            gp.monster[7][2] = new MON_SlimeG(gp);
            gp.monster[7][3] = new MON_SlimeW(gp);
        }

        //rival monster
        if(gp.monster[6][0] != null){
            gp.monster[6][0].worldX = gp.tileSize * 18;
            gp.monster[6][0].worldY = gp.tileSize * 15;
        }  
        if(gp.currentState >= 24){ //rival is null until next fight with him
            gp.monster[6][0] = null;
        }

        //slime B
        if(gp.monster[7][0] != null){
            gp.monster[7][0].worldX = gp.tileSize * 21;
            gp.monster[7][0].worldY = gp.tileSize * 50;
        }
        //slime R
        if(gp.monster[7][1] != null){
            gp.monster[7][1].worldX = gp.tileSize * 31;
            gp.monster[7][1].worldY = gp.tileSize * 41;
        }
        //slime G
        if(gp.monster[7][2] != null){
            gp.monster[7][2].worldX = gp.tileSize * 38;
            gp.monster[7][2].worldY = gp.tileSize * 16;
        }
        //slime W
        if(gp.monster[7][3] != null){
            gp.monster[7][3].worldX = gp.tileSize * 10;
            gp.monster[7][3].worldY = gp.tileSize * 13;
        }
    }

    public void respawn(){
        if(gp.respawn ==2){
            if(gp.monster[7][0]==null){
                gp.monster[7][0] = new MON_SlimeB(gp);
                if(gp.monster[7][0] != null){
                    gp.monster[7][0].worldX = gp.tileSize * 21;
                    gp.monster[7][0].worldY = gp.tileSize * 50;
                }
                gp.respawn =0;
            }
            if(gp.monster[7][1]==null){
                gp.monster[7][1] = new MON_SlimeR(gp);
                if(gp.monster[7][1] != null){
                    gp.monster[7][1].worldX = gp.tileSize * 31;
                    gp.monster[7][1].worldY = gp.tileSize * 41;
                }
                gp.respawn =0;
            }
            if(gp.monster[7][2]==null){
                gp.monster[7][2] = new MON_SlimeG(gp);
                if(gp.monster[7][2] != null){
                    gp.monster[7][2].worldX = gp.tileSize * 38;
                    gp.monster[7][2].worldY = gp.tileSize * 16;
                }
                gp.respawn =0;
            }
            if(gp.monster[7][3]==null){
                gp.monster[7][3] = new MON_SlimeW(gp);
                if(gp.monster[7][3] != null){
                    gp.monster[7][3].worldX = gp.tileSize * 10;
                    gp.monster[7][3].worldY = gp.tileSize * 13;
                }
                gp.respawn =0;
            } 
        }
    }
}
