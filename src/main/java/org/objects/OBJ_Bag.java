package org.objects;

import javax.imageio.ImageIO;

public class OBJ_Bag extends SuperObject{

    public OBJ_Bag(){
        name = "Bag";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/tiles/objects/bag_sprite.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // collision = true;
    }
    
}
