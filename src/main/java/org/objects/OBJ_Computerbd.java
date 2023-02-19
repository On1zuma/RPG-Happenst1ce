package org.objects;
import javax.imageio.ImageIO;

public class OBJ_Computerbd extends SuperObject{

    public OBJ_Computerbd(){

        name = "Computerbd";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/tiles/objects/Computerbd.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        collision = true;

    }
    
}