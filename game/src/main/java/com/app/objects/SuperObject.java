package com.app.objects;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.app.main.GamePanel;

public class SuperObject {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public double worldX, worldY;
    
    //rectangle around objects
    public Rectangle solidArea =new Rectangle (0, 0, 48, 48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;

    public void draw(Graphics2D g2, GamePanel gp){
        solidArea =new Rectangle(0, 0, gp.tileSize, gp.tileSize);
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
            }/////////
        }

        if(worldX + gp.tileSize + gp.tileSize + gp.tileSize> gp.player.worldX - gp.player.screenX && 
        worldX - gp.tileSize - gp.tileSize - gp.tileSize< gp.player.worldX + gp.player.screenX && 
        worldY + gp.tileSize + gp.tileSize + gp.tileSize> gp.player.worldY - gp.player.screenY &&
        worldY - gp.tileSize - gp.tileSize - gp.tileSize< gp.player.worldY + gp.player.screenY
        ){
            g2.drawImage(image , (int)screenX, (int)screenY, gp.tileSize , gp.tileSize , null);
        }       
        //Screen if border
        else if(gp.player.screenX > gp.player.worldX ||
        gp.player.screenY > gp.player.worldY ||
        rightOffset > gp.worldWidth - gp.player.worldX||
        bottomOffset > gp.worldHeight - gp.player.worldY){
            g2.drawImage(image , (int)screenX, (int)screenY, gp.tileSize , gp.tileSize , null);
        }
    }
}
