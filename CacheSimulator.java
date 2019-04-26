import java.util.Scanner;
//import java.util.ArrayBlockingQueue;
/**
 * Simulates a cache.
 *
 * @author Evert Ball
 * @author Jake Ginn
 *
 * @version 05/01/2019
 */
public class CacheSimulator {

    /** Scanner stuff */
    Scanner input;

    /** Represents the number of sets */
    int numSets = 0;

    /** Represents the size of the set */
    int setSize = 0;

    /** Represents the line size */
    int lineSize = 0;

    /** Reps cache info */
    String[] cacheInfo = new String[3];

    //ArrayBlockingQueue<String[]> abq;
    
    /**
     * The constructor for the cache simulator
     *
     * @param numSets  The number of sets in the cache.
     * @param setSize  The number of cache blocks in a set.
     * @param lineSize The number of bytes per block.
     */
    /*
    public CacheSimulator(int numSets, int setSize, int lineSize ) {

        //First three lines of input
        this.numSets = numSets;
        this.setSize = setSize;
        this.lineSize = lineSize;

    } //end CacheSim constructor
    */

    public CacheSimulator(Scanner stdin) {

        this.input = stdin;
        //this.abq = new ArrayBlockingQueue<String[]>(this.setSize);
        //System.out.println("Size of qArr: " + qArr.length);
        

    } // end constructor with scanner

    public void getInput() {
        
        String line  = input.nextLine();
        String[] arr = line.split(": ");
        this.numSets = Integer.parseInt(arr[1]);
        System.out.println("numSets: " + numSets);


        line = input.nextLine();
        arr  = line.split(": ");
        this.setSize = Integer.parseInt(arr[1]);
        System.out.println("setSize: " + setSize);

        line = input.nextLine();
        arr  = line.split(": ");
        this.lineSize = Integer.parseInt(arr[1]);
        System.out.println("lineSize: " + lineSize);

    } // end getInput() method

    public void doAddressStuff(){
        String addrInfo;
        String address;
        int bytesToUse;
        String readOrWrite;
        int count = 1;
        
        int pot = powerOfTwo(this.setSize);
        System.out.println("Power: " + pot);

            
        while(input.hasNextLine()) {
            System.out.println("\n### Line " + count + " ###");
            addrInfo = input.nextLine();

/*
            this.cacheInfo = addrInfo.split(":");
            try{
                this.abq.add(this.cacheinfo);
            } catch(IllegalStateException ise) {
                //TODO finish catch
            }
            System.out.println("abq: " + this.abq.toString());
            address = cacheInfo[0];
            System.out.println("addr: " + address);

            readOrWrite = cacheInfo[1];
            System.out.println("Read or write?: " + readOrWrite);

            bytesToUse = Integer.parseInt(cacheInfo[2]);
            System.out.println("Bytes to R/W: " + bytesToUse);
*/           
            count++;
        }

    } // end getAddressStuff method

    public int powerOfTwo(int numSets) {
        
        int power = 0;
        int temp = numSets;

        while( temp != 1 ) {
            temp = temp / 2;
            power++;
        }

        return power;
    } // end powerOfTwo method

} // end CacheSimulator class
