package com.app.objects;
import javax.imageio.ImageIO;

public class OBJ_Computerhd extends SuperObject{

    public OBJ_Computerhd(){

        name = "Computerhds";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/objects/Computerhd.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        collision = true;

    }
    
}