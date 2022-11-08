package com.app.objects;
import javax.imageio.ImageIO;

public class OBJ_Computerhg extends SuperObject{

    public OBJ_Computerhg(){

        name = "Computerhg";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/objects/Computerhg.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        collision = true;

    }
    
}