import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;

import static java.lang.Integer.parseInt;
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

    Cache cache = new Cache();

    /** Scanner stuff */
    Scanner input;

    /** Reps cache info */
    int[] cacheInfo = new int[3];

    /**The address for each instruction*/
    ArrayList<Integer> cacheAddress;

    /**The cache Read or Write Instructions */
    ArrayList<String> cacheIns;

    /**The cache's line size */
    ArrayList<Integer> lineSize;

    ArrayList<Integer> offsets;
    ArrayList<Integer> indices;
    ArrayList<Integer> tags;
    ArrayList<ArrayList<CacheSets>> cacheLoad = cache.getCache();
    ArrayList<Integer> memRefs;
    ArrayList<String> result;


    /**
     * Constructor for the CacheSimulator
     * @param stdin - The Standard input that is coming in from the file that the user inputs
     */
    public CacheSimulator(Scanner stdin) {

        this.input = stdin;
        cacheAddress = new ArrayList<>();
        cacheIns = new ArrayList<>();
        lineSize = new ArrayList<>();
        offsets = new ArrayList<>();
        indices = new ArrayList<>();
        tags = new ArrayList<>();
        memRefs = new ArrayList<>();
        result = new ArrayList<>();
    } // end constructor with scanner


    /**
     * the method that begins the CacheSimulator
     */
    public void go(){
        getInput();
        cache.setupCache(cacheInfo);

        int offsetBits = findOffsetBits(cacheInfo[2]);
        int indexBits = findIndexBits(cacheInfo[0]);

        //System.out.println("number of bits for offset:  " + offsetBits);
        //System.out.println("number of bits for index:   " + indexBits);

        int offset;//the offset
        int index; //the index
        int tag; //the tag
        String wordLengthaddr;//the word length address

       // System.out.println("  Tag   " +"Index " + "Offset \n------- ----- ------" );
        for(int address : cacheAddress){
            //gets the offset
            wordLengthaddr = padZero(address);
            offset = getEachOffset(offsetBits,wordLengthaddr);
            //System.out.println("Offset:  " + offset);

            //gets the index
            index = getEachIndex(indexBits,offsetBits,wordLengthaddr);
            //System.out.println("Index:  " + index);

            tag = getEachTag(indexBits,offsetBits,wordLengthaddr);
            //System.out.println("Tag:  " + tag);

            offsets.add(offset); //holds the offset to go into the block
            indices.add(index);  //holds the index for each set or block
            tags.add(tag);       //holds the tag for each address

           // System.out.println("      " + tag + "     " + index + "      " + offset);

        }


        instructionHandler();

    }

    public void getInput(){
        String line = " ";
        int counter = 0;
        int toInt;
        String addr = " ";

        while(input.hasNextLine()){
            line = input.nextLine();
            if(counter < 3){
                String[] inf = line.split(": ");
                toInt = parseInt(inf[inf.length - 1]);
                cacheInfo[counter] = toInt;
                counter++;
            } else{
                String[] inf = line.split(":");

                addr = inf[0];
                cacheAddress.add(Integer.parseInt(addr,16));
                //toInt = Integer.parseInt(addr,16);

                //System.out.println(toInt);

                cacheIns.add(inf[1]);

                //System.out.println(toInt);
                lineSize.add(parseInt(inf[2]));
            }
        }

        //testing the input that we recieve
        //System.out.println("\n\nThe first instruction is");
        //System.out.println(cacheAddress.get(0));
        //System.out.println(cacheIns.get(0));
        //System.out.println(lineSize.get(0));
    }



    private int findOffsetBits(int lineSize){
        String binaryOffset = Integer.toBinaryString(lineSize);
        int offsetBits = binaryOffset.toCharArray().length-1;

        return offsetBits;
    }

    private int findIndexBits(int numSets){
        String binaryIndex = Integer.toBinaryString(numSets);
        int index = binaryIndex.toCharArray().length-1;

        return index;

    }

    public int getEachOffset(int offsetBits, String address){
        char[] bits = address.toCharArray();
        String tempOffset = "";

        for(int i = address.length() - offsetBits; i < address.length(); i++){
            tempOffset += bits[i];
        }
        return Integer.parseInt(tempOffset, 2);

    }

    public int getEachIndex(int indexBits, int offsetBits, String address){
        char[] bits = address.toCharArray();
        int addressSize = bits.length;
        String tempIndex = "";

        for(int i = addressSize - indexBits - offsetBits; i < addressSize - offsetBits; i++){
            tempIndex += bits[i];
        }
        return Integer.parseInt(tempIndex, 2);
    }

    public int getEachTag(int indexBits, int offsetBits, String address){
        char[] bits = address.toCharArray();
        int addressSize = bits.length;
        String tempTag = "";

        for(int i = 0; i < addressSize - indexBits - offsetBits; i++){
            tempTag += bits[i];
        }

        return Integer.parseInt(tempTag,2);
    }


    private String padZero(int cacheAddress){
        String binaryAddress = Integer.toBinaryString(cacheAddress);
        String wordLength = String.format("%32s", binaryAddress).replace(' ', '0');
        return wordLength;

    }

    private ArrayList<CacheSets> getSet(ArrayList<ArrayList<CacheSets>> cacheLoad, int index){
        ArrayList<CacheSets> set = new ArrayList<>();
        for(ArrayList<CacheSets> sets : cacheLoad){
            for(CacheSets tempCacheSet : sets){
                if(tempCacheSet.getIndex() == index){
                    set = sets;
                }
            }
        }

        return set;
    }


    private void instructionHandler(){
        int counter = 0;

        for(String ins : cacheIns){
            if(ins.equals("R")){
                readInstruction(counter);
                counter++;
            } else if(ins.equals("W")){
                //writeInstruction();
            }
        }

        printResult();

    }

    private void readInstruction(int counter){

        boolean foundTag;
        boolean done = false;
        ArrayList<CacheSets> set = cacheLoad.get(indices.get(counter));
        //System.out.println("indices:  " +  indices.get(counter));
        foundTag = checkTag(set,counter);



        if(!foundTag){
            //foundTag = checkValid(set, counter);
            leastRecentReplace(set,counter);

        }


        //System.out.println("First Cache Line:  " + set.get(0));
        //System.out.println("Second Cache Line:   " + set.get(1));


    }

    private void tagNotValid(CacheSets s, int counter){

        s.setLru(new Date(counter));
        s.setTag(tags.get(counter));
        result.add("MISS");
        s.setValidBit(true);
    }

//    private boolean checkValid(ArrayList<CacheSets> set, int counter){
//        boolean res = false;
//        for(CacheSets s : set){
//            if(!s.isValidBit()){
//                tagNotValid(s, counter);
//
//                memRefs.add(1);
//                s.setAddress(cacheAddress.get(counter));
//                res = true;
//            }
//        }
//        return res;
//
//    }

    private boolean checkTag(ArrayList<CacheSets> set, int counter){
        for(CacheSets s : set){
            //System.out.println("Tag we have:   " + s.getTag() + "\nTag we are looking for:  " + tags.get(counter));
            if(s.getTag() == tags.get(counter)){
                if(s.isValidBit()){
                    result.add("HIT");
                    memRefs.add(0);
                    s.setLru(new Date(counter));

                    s.setAddress(cacheAddress.get(counter));
                    return true;
                }

            }
        }
        return false;
    }


    private void leastRecentReplace(ArrayList<CacheSets> sets, int counter){
        CacheSets lru = findLRU(sets);

        lru.setLru(new Date(counter));

        if(lru.getDirtyBit() == 1){
            memRefs.add(2);
        } else{
            memRefs.add(1);
        }

        tagNotValid(lru, counter);
        lru.setAddress(cacheAddress.get(counter));

    }

    private CacheSets findLRU(ArrayList<CacheSets> sets){
        CacheSets ret = sets.get(0);
        boolean done = false;

        for(CacheSets s : sets){
            for(CacheSets cs : sets){
                System.out.println(s.getLru().getTime());
                System.out.println(cs.getLru().getTime());
                //System.out.println(s.getLru().getTime() < cs.getLru().getTime());
                if(s.getLru().getTime()< cs.getLru().getTime()){
                    ret = s;
                    done = true;
                }
            }
        }

        //System.out.println("Result is:  " + ret);

        return ret;

    }

    private void writeInstruction( int counter){


    }


    private void printResult(){
        for(String s : result){
            System.out.println(s);
        }
    }


} // end CacheSimulator class
