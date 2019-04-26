import java.util.Scanner;
/**
 * Driver for the cache simulator
 *
 * @author Evert Ball
 * @author Jake Ginn
 *
 * @version 05/01/2019
 */
public class Driver {

    public static void main(String[] args) {
        
        // Allows use of non-static method in main
        Driver drive = new Driver();
        drive.go();

    } // end main

    public void go() {
/*
        int numberSets = 0;
        int setSize = 0;
        int lineSize = 0;
*/
        /** The scanner to read input from stdin */
        Scanner input = new Scanner(System.in);

        CacheSimulator cacheSim = new CacheSimulator(input);
        cacheSim.getInput();
        cacheSim.doAddressStuff();
/*        
        // Gets the first 3 lines of input and sets respective variables
        numberSets = input.nextInt();
        setSize = input.nextInt();
        lineSize = input.nextInt();

        CacheSim cacheSim = new CacheSimulator(numberSets, setSize, lineSize);
*/        
    } // end go

} // end Driver class
