/**
 * SnakeRunner Class : Runs the game
 * Created- 19/07/2015
 */
package snakeGame;

import org.lwjgl.*;
import org.lwjgl.opengl.*;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author Zain
 */
public class SnakeRunner {
    public static void main(String[] args) {
        final int X = 1000;
        final int Y = 600;
//        final int FRAME_RATE = 7;
        Game snakeManager = new Game(X, Y);
        snakeManager.startGame();
        
//        try {
//            Display.setDisplayMode(new DisplayMode(X, Y));
//            Display.setTitle("Snake Game");
//            Display.create();
//        } catch (LWJGLException ex) {
//            System.out.println("LWJGL Exception Thrown");
//            System.exit(1);
//        }
//        
//        Keyboard.enableRepeatEvents(false);
//        
//        //initialize opengl
//        GL11.glMatrixMode(GL11.GL_PROJECTION);
//        GL11.glLoadIdentity();
//        GL11.glOrtho(0, 1000, 0, 600, 1, -1);
//        GL11.glMatrixMode(GL11.GL_MODELVIEW);
//        
//        //Square sq = new Square(100, 100);
//        
//        // game loop
//        while(!Display.isCloseRequested()) {
//            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
//            snakeManager.startGame();
//            Display.update();
//            Display.sync(FRAME_RATE);
//        }
//        
//        // close window
//        Display.destroy();
//        System.exit(0);
    }
}
