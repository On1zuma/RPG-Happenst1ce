package org.tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import org.main.GamePanel;
import java.awt.Color;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][][];
    boolean drawPath = true;
    public int tilePos = 1;
    public int tileMenos;


    public TileManager(GamePanel gp){
        this.gp = gp;

        tile = new Tile[2000];
        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/mainRoom.txt", 0);
        loadMap("/maps/mainHouseFirstFloor.txt", 1);
        loadMap("/maps/mainboard.txt", 2);
        loadMap("/maps/laboratory.txt", 3);
        loadMap("/maps/forestMap.txt", 4);
        loadMap("/maps/laboratorySecondFloor.txt", 5);
        loadMap("/maps/combatZone.txt", 6);
        loadMap("/maps/trainingMap.txt", 7);
        loadMap("/maps/datoLana.txt", 8);
        loadMap("/maps/dataCenter.txt", 9);
        loadMap("/maps/datalonaFirstRoom.txt", 10);
        loadMap("/maps/datalonaSecondRoom.txt", 11);
    }


    public void getTileImage(){
        try{
            //Our tiles liste outside
            while(tilePos <= 1074){

                if(tilePos<=1074){
                    tileMenos = tilePos -1;
                    tile[tilePos] = new Tile();
                    tile[tilePos].image = ImageIO.read(getClass().getResourceAsStream("/tiles/mainboard/"+tileMenos+".png"));
                    tile[tilePos].collision = true;

                    if(tilePos >= 36 && tilePos<= 215){
                        tile[tilePos].collision = false;
                    }
                }
                tilePos++;
            };

            //Our tiles liste inside
            while(tilePos <= 1293){
                tileMenos = tilePos - 1;
                tile[tilePos] = new Tile();
                tile[tilePos].image = ImageIO.read(getClass().getResourceAsStream("/tiles/insideboard/tile"+tileMenos+".png"));
                tile[tilePos].collision = true;

                    if(tilePos >= 1074 && tilePos<= 1145){
                        tile[tilePos].collision = false;
                    }
                tilePos++;
            };

            //tiles states update
            // tile number +1 
            tile[8].collision = false;
            tile[11].collision = false;
            tile[47].collision = true;
            tile[367].collision = false;
            tile[368].collision = false;
            tile[369].collision = false;
            tile[370].collision = false;
            tile[488].collision = false;

            //chair
            tile[1256].collision = false;

            //door
            tile[1049].collision = false;
            tile[907].collision = false;
            tile[1098].collision = true;

        }catch(IOException e){
            e.printStackTrace();
        }
    }


    public void loadMap(String filePath, int map){
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col =0;
            int row=0;

            while(col< gp.maxWorldCol && row < gp.maxWorldRow){
                String line = br.readLine();

                while(col < gp.maxWorldCol){
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[map][col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }br.close();
        } catch (Exception e) {
        }
    }


    public void draw(Graphics2D g2){
        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){

            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            //we adapt coordinate to the player position 
            double screenX = worldX - gp.player.worldX + gp.player.screenX;
            double screenY = worldY - gp.player.worldY + gp.player.screenY;

            // //stop moving the camera at the edge
            // //left            
            int rightOffset = gp.screenWidth - gp.player.screenX;
            int bottomOffset = gp.screenHeight - gp.player.screenY;
            
            if(gp.gameState != gp.photoState){
                if(gp.player.screenX > gp.player.worldX){
                screenX = worldX;
                }
                //top
                if(gp.player.screenY > gp.player.worldY){
                    screenY = worldY;
                }
                // //right
                if(rightOffset > gp.worldWidth - gp.player.worldX){
                    screenX = gp.screenWidth - (gp.worldWidth - worldX);
                }
                if(bottomOffset > gp.worldHeight - gp.player.worldY){
                    screenY = gp.screenHeight - (gp.worldHeight - worldY);
                }
            }

            if(worldX + gp.tileSize + gp.tileSize + gp.tileSize> gp.player.worldX - gp.player.screenX && 
            worldX - gp.tileSize - gp.tileSize - gp.tileSize< gp.player.worldX + gp.player.screenX && 
            worldY + gp.tileSize + gp.tileSize + gp.tileSize> gp.player.worldY - gp.player.screenY &&
            worldY - gp.tileSize - gp.tileSize - gp.tileSize< gp.player.worldY + gp.player.screenY
              ){
                g2.drawImage(tile[tileNum].image , (int)screenX, (int)screenY, gp.tileSize , gp.tileSize , null);
              }
              //if edge
              else if(gp.player.screenX > gp.player.worldX ||
                      gp.player.screenY > gp.player.worldY ||
                      rightOffset > gp.worldWidth - gp.player.worldX||
                      bottomOffset > gp.worldHeight - gp.player.worldY){
                        g2.drawImage(tile[tileNum].image , (int)screenX, (int)screenY, gp.tileSize , gp.tileSize , null);
                    }

            worldCol++;

            if(worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow++;
            }

            
        }

        if(gp.debug ==1){
            if(drawPath ==true){
                g2.setColor(new Color(255,0,0,70));

                for(int i =0; i< gp.pFinder.pathList.size(); i++){

                    int worldX = gp.pFinder.pathList.get(i).col* gp.tileSize;
                    int worldY = gp.pFinder.pathList.get(i).row* gp.tileSize;
                    int screenX = (int)worldX - (int)gp.player.worldX + gp.player.screenX;
                    int screenY = (int)worldY - (int)gp.player.worldY + gp.player.screenY;

                    g2.fillRect(screenX, screenY,gp.tileSize,gp.tileSize);
                }
            }
        }
    }    
}
