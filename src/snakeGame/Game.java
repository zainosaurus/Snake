/**
 * Game Class: Manager the Game
 * Created- 18/07/2015
 */
package snakeGame;

import org.lwjgl.*;
import org.lwjgl.opengl.*;
import java.util.*;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author Zain
 */
public class Game {
    // Variables
    private final int POINTS_PER_MEAL = 10;
    private final int FRAME_RATE = 7;
    private Snake snake;
    private Square food;
    private int points;
    private int maxx;
    private int maxy;
    private Random uberDice;
    
    // Accessors/Mutators
    
    // Constructor: parameters are the max x and y values of the board (pixels)
    public Game(int x, int y) {
        // display stuff
        maxx = x;
        maxy = y;
        
        //opengl/lwjgl stuff
        initDisplay();
        initOpenGL();
        Keyboard.enableRepeatEvents(false);
        
        // game stuff
        uberDice = new Random();
        snake = new Snake(0, 0);
        food = new Square();
        initFood();
        points = 0;
    }
    
    // Methods
    
    // Manages the game
    public void startGame() {
        while(!Display.isCloseRequested()) {
	    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            
            // game management
            manageInput();
            snake.draw();
            growFood();
            handleCollisions();
            snake.move();
            
            
            Display.update();
            Display.sync(FRAME_RATE);
        }
        
        gameOver();
    }
    
    // generates a random coordinate for the food to spawn on. It needs to make sure that it will be 'aligned' with a space that the snake's
    //head will occupy, so itneds to be a multiple of (square size + buffer[in snake class])
    public Location generateRandomCoordinate() {
        Location random;
        double x = 0;
        double y = 0;
        int temp;
        int gridX = maxx / (int)(Square.SIZE + snake.BUFFER);     // number of valid x coordinates on the screen
        int gridY = maxy / (int)(Square.SIZE + snake.BUFFER);     // number of valid y coordinates on the screen
        
        // generating random x and y:
        temp = uberDice.nextInt(gridX);
        x = temp * (Square.SIZE + snake.BUFFER);
        temp = uberDice.nextInt(gridY);
        y = temp * (Square.SIZE + snake.BUFFER);
        
        // create and return location
        random = new Location(x, y);
        return random;
    }
    
    // Manages the input
    public void manageInput() {
        while(Keyboard.next()) {
            if (Keyboard.getEventKey() == Keyboard.KEY_UP && snake.getDirection() != Direction.DOWN) {
                snake.setDirection(Direction.UP);
            } else if (Keyboard.getEventKey() == Keyboard.KEY_DOWN && snake.getDirection() != Direction.UP) {
                snake.setDirection(Direction.DOWN);
            } else if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && snake.getDirection() != Direction.RIGHT) {
                snake.setDirection(Direction.LEFT);
            } else if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && snake.getDirection() != Direction.LEFT) {
                snake.setDirection(Direction.RIGHT);
            }
        }
//        if (Keyboard.isKeyDown(Keyboard.KEY_UP) && snake.getDirection() != Direction.DOWN) {
//            snake.setDirection(Direction.UP);
//        } else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) && snake.getDirection() != Direction.UP) {
//            snake.setDirection(Direction.DOWN);
//        } else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) && snake.getDirection() != Direction.RIGHT) {
//            snake.setDirection(Direction.LEFT);
//        } else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && snake.getDirection() != Direction.LEFT) {
//            snake.setDirection(Direction.RIGHT);
//        }
    }
    
    public void addPoints(int num) {
        points += num;
    }
    
    public void removePoints(int num) {
        points -= num;
    }
    
    // creates new food
    public void growFood(int x, int y) {
        food.setLocation(x, y);
        growFood();
    }
    public void growFood() {
        food.draw();
    }
    public void growFood(Location loc) {
        food.setLocation(loc);
        growFood();
    }
    
    // initializes a new food
    public void initFood() {
        food.setLocation(generateRandomCoordinate());
    }
    
    // Checks and handles collisions
    public void handleCollisions() {
        // handles out of bounds
        if (outOfBounds()) {
            System.out.println("Out of Bounds");
            gameOver();
        }
        //handles food being eaten
        if (foodEaten()) {
            addPoints(POINTS_PER_MEAL);
            initFood();
            snake.addSegment();
        }
        // handles snake eating itself
        if (cannibal()) {
            System.out.println("Cannibal");
            gameOver();
        }
        
    }
    // detects if snake goes out of bounds
    public boolean outOfBounds() {
        boolean out = false;
        Direction dir = snake.getDirection();
        if (dir == Direction.UP && snake.getLocation().getY() > maxy - Square.SIZE) {
            out = true;
        } else if (dir == Direction.DOWN && snake.getLocation().getY() < 0) {
            out = true;
        } else if (dir == Direction.LEFT && snake.getLocation().getX() < 0) {
            out = true;
        } else if (dir == Direction.RIGHT && snake.getLocation().getX() > maxx - Square.SIZE) {
            out = true;
        }
        return out;
    }
    // detects if the snake eats the food
    public boolean foodEaten() {
        if (snake.getLocation().equals(food.getLocation())) {
            return true;
        } else {
            return false;
        }
    }
    //detects when the snake eats itself
    public boolean cannibal() {
        boolean check = false;
        Square[] array = snake.getSegments();
        for (int i = 1; i < array.length; i++) {
            if (snake.getLocation().equals(array[i].getLocation())) {
                check = true;
            }
        }
        return check;
    }
    
    // called when snake touches the edges
    public void gameOver() {
        Display.destroy();
        System.out.println("Game over");
        System.out.println("Points: " + points);
        System.exit(0);
    }
     
    // initializes opengl
    public void initOpenGL() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, 1000, 0, 600, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }
    
    // initializes display
    public void initDisplay() {
        try {
            Display.setDisplayMode(new DisplayMode(maxx, maxy));
            Display.setTitle("Snake Game");
            Display.create();
        } catch (LWJGLException ex) {
            System.out.println("LWJGL Exception Thrown");
            System.exit(1);
        }
    }
}
