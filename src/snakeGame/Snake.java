package snakeGame;

/**
 * Snake Class: The snake
 * Created- 18/07/2015
 */

import java.util.*;

/**
 *
 * @author Zain
 */
public class Snake {
    // Variables
    private int numSegments;
    private ArrayList segments;
    private Square head;
    private Square tail;
    private Direction dir;
//    private final double SHIFT = 2;
    public final double BUFFER = 3;
    private Location location;
    private final int DEFAULT_START_SIZE = 9;
    
    // Accessors and Mutators
    public Direction getDirection() {
        return dir;
    }
    public Location getLocation() {
        return location;
    }
    public Square[] getSegments() {
        Square[] arr = new Square[numSegments];
        for (int i = 0; i < numSegments; i++) {
            arr[i] = (Square)segments.get(i);
        }       
        return arr;
    }
    
    public void setDirection(Direction direction) {
        dir = direction;
    }
    
    // Constructor:
    // Creates snake at location in parameters
    public Snake(double x, double y) {
        numSegments = 1;
        segments = new ArrayList(numSegments);
        segments.add(new Square(x, y));
        head = (Square)segments.get(0);
        tail = (Square)segments.get(segments.size() - 1);
        dir = Direction.RIGHT;
        updateLocation();
        initSnakeSegments(DEFAULT_START_SIZE);
    }
    
    
    // Methods
    
    // updates the location of the snake with the location of the head (first element in the arraylist)
    public void updateLocation() {
        location = head.getLocation();
    }
    
    // initializes the snake with the number of EXTRA segments specified -> method is only for when snake starts with more than one square
    public void initSnakeSegments(int segments) {
        for (int i = 0; i < segments; i++) {
            addSegment();
        }
    }
    
    // Moves the last segment of the snake to the front to move the snake forward
    public void move() {
        if (dir == Direction.UP) {
            tail.setLocation(location.getX(), location.getY() + Square.SIZE + BUFFER);
        } else if (dir == Direction.DOWN) {
            tail.setLocation(location.getX(), location.getY() - Square.SIZE - BUFFER);
        } else if (dir == Direction.LEFT) {
            tail.setLocation(location.getX() - Square.SIZE - BUFFER, location.getY());
        } else if (dir == Direction.RIGHT) {
            tail.setLocation(location.getX() + Square.SIZE + BUFFER, location.getY());
        }

        // changing the position of the head and tail in the array to match the layout on screen
        // Shifting all the elements of the array up, placing tail at the front (to mirror what happens on screen)
        // first keep in mind that the last element in the array is stored in a var called 'tail'
        if (numSegments > 1) {
            for (int i = numSegments - 2; i >= 0; i--) {
                segments.set(i + 1, segments.get(i));
            }
            segments.set(0, tail);
        }
        
        // updating the head and tail values        
        head = (Square)segments.get(0);
        tail = (Square)segments.get(numSegments - 1);
        
        // keeping this class's location up to date with the head
        updateLocation();
        
        // drawing
        //draw();
    }
    
    // Adds a Segment to the snake. Called when food is eaten.
    public void addSegment() {
        Location endLocation = tail.getLocation();
        
        if (dir == Direction.UP) {
            segments.add(new Square(endLocation.getX(), endLocation.getY() - Square.SIZE - BUFFER));
        } else if (dir == Direction.DOWN) {
            segments.add(new Square(endLocation.getX(), endLocation.getY() + Square.SIZE + BUFFER));
        } else if (dir == Direction.LEFT) {
            segments.add(new Square(endLocation.getX() + Square.SIZE + BUFFER, endLocation.getY()));
        } else if (dir == Direction.RIGHT) {
            segments.add(new Square(endLocation.getX() - Square.SIZE - BUFFER, endLocation.getY()));
        }
        
        numSegments ++;
        tail = (Square)segments.get(numSegments - 1);
        //tail.draw();
    }
    
    // Removes a segment from the snake. Can't think of when this will be used unless I add cheat codes or something.
    // probably doesn't work btw
    public void removeSegment() {
        segments.remove(numSegments - 1);
        numSegments--;
        tail = (Square)segments.get(numSegments - 1);
    }
    
    // Draws the entire snake
    public void draw() {
        // different for loop for each case:
//        if (dir == Direction.UP) {
//            for (int i = 0; i < numSegments; i++) {
//                ((Square)segments.get(i)).draw(location.getX(), location.getY() - i * (Square.SIZE + BUFFER));
//            }
//        } else if (dir == Direction.DOWN) {
//            for (int i = 0; i < numSegments; i++) {
//                ((Square)segments.get(i)).draw(location.getX(), location.getY() + i * (Square.SIZE + BUFFER));
//            }
//        } else if (dir == Direction.LEFT) {
//            for (int i = 0; i < numSegments; i++) {
//                ((Square)segments.get(i)).draw(location.getX() + i * (Square.SIZE + BUFFER), location.getY());
//            }
//        } else if (dir == Direction.RIGHT) {
//            for (int i = 0; i < numSegments; i++) {
//                ((Square)segments.get(i)).draw(location.getX() - i * (Square.SIZE + BUFFER), location.getY());
//            }
//        }
        
        for (int i = 0; i < numSegments; i++) {
            ((Square)segments.get(i)).draw();
        }
    }
    
}
