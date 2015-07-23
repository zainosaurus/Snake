package snakeGame;

/**
 * Square Class: The square that makes up the snake and food
 * Created- 18/07/2015
 */

import org.lwjgl.opengl.*;

/**
 *
 * @author Zain
 */
public class Square {
    // Variables
    public static final double SIZE = 10;
    private Location location;
    
    // Accessors, Mutators
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location loc) {
        location = loc;
    }
    public void setLocation(double x, double y) {
        location.setX(x);
        location.setY(y);
    }
    
    // Constructors
    public Square() {
        location = new Location();
    }
    public Square(double x, double y) {
        location = new Location(x, y);
    }
    
    // Methods
    public void draw() {
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2d(location.getX(), location.getY());
        GL11.glVertex2d(location.getX() + SIZE, location.getY());
        GL11.glVertex2d(location.getX() + SIZE, location.getY() + SIZE);
        GL11.glVertex2d(location.getX(), location.getY() + SIZE);
        GL11.glEnd();
    }
    
    public void draw(int x, int y) {
        setLocation(x, y);
        draw();
    }
}
