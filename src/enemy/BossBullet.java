package enemy;
import java.awt.*;

import javax.swing.*;
 
public class BossBullet {
        int x, y;
         Image img;
        boolean visible;
        Rectangle hitbox;
       
        public BossBullet(int startX, int startY)
        {
                ImageIcon newBullet = new ImageIcon(getClass().getResource("/resources/bullet2.png"));
                img = newBullet.getImage();
                x = startX;
                y = startY;
                visible = true;
        }
        
        public void move(){

                x = x - 12; //x + bullet speed
                if (x < 0)
                        visible = false;
                hitbox = new Rectangle(x,y,40,40);
        }
        
        public int getX()
        {
                return x;
        }
 
        public boolean getVisible()
        {
                return visible;
        }
       
        public int getY()
        {
                return y;
        }
       
        public Image getImage()
        {
                return img;
        }
        
}