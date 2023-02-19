package org.objects;
import javax.imageio.ImageIO;

public class OBJ_Chair extends SuperObject{

    public OBJ_Chair(){

        name = "Chair";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/tiles/objects/chair.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
}
