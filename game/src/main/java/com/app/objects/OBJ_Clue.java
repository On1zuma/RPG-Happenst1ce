package com.app.objects;

import javax.imageio.ImageIO;

public class OBJ_Clue extends SuperObject{

    public OBJ_Clue(){
        name = "Clue";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/objects/clue.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // collision = true;
    }
}
