package org.objects;

import javax.imageio.ImageIO;

public class OBJ_Box extends SuperObject{

    public OBJ_Box(){
        name = "Box";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/tiles/objects/box_sprite.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // collision = true;
    }
    
}
