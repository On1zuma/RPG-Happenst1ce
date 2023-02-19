package org.objects;
import javax.imageio.ImageIO;

public class OBJ_Computerhg extends SuperObject{

    public OBJ_Computerhg(){

        name = "Computerhg";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/tiles/objects/Computerhg.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        collision = true;

    }
    
}