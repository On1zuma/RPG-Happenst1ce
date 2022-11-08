package com.app.objects;
import javax.imageio.ImageIO;

public class OBJ_Computerbg extends SuperObject{

    public OBJ_Computerbg(){

        name = "Computerbg";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/objects/Computerbg.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        collision = true;

    }
    
}