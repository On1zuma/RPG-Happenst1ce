package org.objects;
import javax.imageio.ImageIO;

public class OBJ_Clue extends SuperObject{

    public OBJ_Clue(){
        name = "Clue";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/tiles/objects/clue.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // collision = true;
    }
}
