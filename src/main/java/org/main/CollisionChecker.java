package org.main;

import org.entity.Entity;

public class CollisionChecker {

    GamePanel gp;


    public CollisionChecker (GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity){

        //convertion from double to int
        int worldXint=(int)entity.worldX; 
        int worldYint=(int)entity.worldY; 
        int speedInt=(int)entity.speed; 

        int entityLeftWorldX = worldXint + entity.solidArea.x - 1;
        int entityRightWorldX = worldXint + entity.solidArea.x + entity.solidArea.width +1;
        int entityTopWorldY = worldYint + entity.solidArea.y -1;//
        int entityBottomWorldY = worldYint + entity.solidArea.y + entity.solidArea.height +1;

        int entityLeftCol= entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum1, tileNum2;

        switch(entity.direction){
            //Cube around entity
            case "up":
                entityTopRow = (entityTopWorldY - speedInt)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
               
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collision = true;
                }
            break;
            case "down":
                entityBottomRow = (entityBottomWorldY + speedInt)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
           
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collision = true;
                }
            break;
            case "left":
                entityLeftCol = (entityLeftWorldX - speedInt)/gp.tileSize;
                //we set collision to true when any corner of the cube hite something hard
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];

                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true ){
                    entity.collision = true;
                }

            break;
            case "right":
                entityRightCol = (entityRightWorldX + speedInt)/gp.tileSize;

                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true ){
                    entity.collision = true;
                }

            break;

        }
    }
    
    public int checkObject(Entity entity, boolean player){

        int index = 999;

        for(int i = 0; i < gp.obj[2].length; i++){
  
            if(gp.obj[gp.currentState][gp.currentMap][i] != null){
                int worldXint=(int)entity.worldX; 
                int worldYint=(int)entity.worldY;

                int worldXObj=(int)gp.obj[gp.currentState][gp.currentMap][i].worldX;
                int worldYObj=(int)gp.obj[gp.currentState][gp.currentMap][i].worldY;

                //get entity's solid area position
                entity.solidArea.x = worldXint+ entity.solidArea.x;
                entity.solidArea.y = worldYint+ entity.solidArea.y;

                //get the object's solid area position
                gp.obj[gp.currentState][gp.currentMap][i].solidArea.x = worldXObj + gp.obj[gp.currentState][gp.currentMap][i].solidArea.x;
                gp.obj[gp.currentState][gp.currentMap][i].solidArea.y = worldYObj + gp.obj[gp.currentState][gp.currentMap][i].solidArea.y;
                
                switch(entity.direction){
                    case "up":
                        entity.solidArea.y -= entity.speed;
                    break;

                    case "down":
                        entity.solidArea.y += entity.speed;
                    break;

                    case "left":
                        entity.solidArea.x -= entity.speed;
                    break;

                    case "right":
                        entity.solidArea.y += entity.speed;
                    break;
                }
                if(entity.solidArea.intersects(gp.obj[gp.currentState][gp.currentMap][i].solidArea)){
                    // System.out.println("right collision");
                    if(gp.obj[gp.currentState][gp.currentMap][i].collision == true){
                        entity.collision = true;
                    }
                    if(player ==true){
                        index =i;
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[gp.currentState][gp.currentMap][i].solidArea.x = 0;
                gp.obj[gp.currentState][gp.currentMap][i].solidArea.y = 0;
            }
    }
        return index;
    }

    //check if we tuch entity
    public int checkEntity(Entity entity, Entity[][] target){

        int index = 999;

        for(int i = 0; i < target[1].length; i++){
  
            if(target[gp.currentMap][i] != null){
                //DERIVATION OF THE POSITION
                int worldXint=(int)entity.worldX-1; 
                int worldYint=(int)entity.worldY-1;

                int worldXObj=(int)target[gp.currentMap][i].worldX;
                int worldYObj=(int)target[gp.currentMap][i].worldY;

                //get entity's solid area position
                entity.solidArea.x = worldXint+ entity.solidArea.x;
                entity.solidArea.y = worldYint+ entity.solidArea.y;

                //get the object's solid area position
                target[gp.currentMap][i].solidArea.x = worldXObj + target[gp.currentMap][i].solidArea.x;
                target[gp.currentMap][i].solidArea.y = worldYObj + target[gp.currentMap][i].solidArea.y;
                
                switch(entity.direction){
                    case "up":
                        entity.solidArea.y -= entity.speed;
                    break;

                    case "down":
                        entity.solidArea.y += entity.speed;
                    break;

                    case "left":
                        entity.solidArea.x -= entity.speed;
                    break;

                    case "right":
                        entity.solidArea.y += entity.speed;
                    break;
                }
                if(entity.solidArea.intersects(target[gp.currentMap][i].solidArea)){
                    if(target[gp.currentMap][i] != entity){
                        entity.collision = true;
                        index =i;
                    }
                } 

                target[gp.currentMap][i].solidArea.x = 0;
                target[gp.currentMap][i].solidArea.y = 0;
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
            }
    }
        return index;
    }

    public boolean checkPlayer(Entity entity){

        boolean contactPlayer = false;

        int worldXint=(int)entity.worldX; 
        int worldYint=(int)entity.worldY;

        int worldXObj=(int)gp.player.worldX;
        int worldYObj=(int)gp.player.worldY;

        //get entity's solid area position
        entity.solidArea.x = worldXint+ entity.solidArea.x;
        entity.solidArea.y = worldYint+ entity.solidArea.y;

        //get the object's solid area position
        gp.player.solidArea.x = worldXObj + gp.player.solidArea.x;
        gp.player.solidArea.y = worldYObj + gp.player.solidArea.y;
        
        switch(entity.direction){
            case "up":
                entity.solidArea.y -= entity.speed;                             
            break;

            case "down":
                entity.solidArea.y += entity.speed;
            break;

            case "left":
                entity.solidArea.x -= entity.speed;
            break;

            case "right":
                entity.solidArea.y += entity.speed;
            break;

        }
        if(entity.solidArea.intersects(gp.player.solidArea)){
            contactPlayer = true;
            entity.collision = true;
        }

        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        //ca retire partiellement le bug des collisions
        gp.player.solidArea.x = gp.tileSize/8 +1/2;
        gp.player.solidArea.y = gp.tileSize+3 -1/2;

        return contactPlayer;
    }
}
