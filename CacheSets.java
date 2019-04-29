import java.util.Date;

/**
 * This class models how the sets are setup in a cache
 * @author - Jacob Ginn
 * @author - Evert Ball
 *
 * @version 4/28/2019
 */

public class CacheSets {
    private Date lru;
    private int tag;
    private int index;
    private boolean validBit;
    private int dirtyBit;
    private int line [];
    private int address;

    public CacheSets(int size){
        lru = new Date(0);
        tag = 0;
        index = 0;
        validBit = false;
        dirtyBit = 0;
        line = new int[size];
        address = 0;

    }

    public Date getLru() {
        return lru;
    }

    public void setLru(Date lru) {
        this.lru = lru;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isValidBit() {
        return validBit;
    }

    public void setValidBit(boolean validBit) {
        this.validBit = validBit;
    }

    public int getDirtyBit() {
        return dirtyBit;
    }

    public void setDirtyBit(int dirtyBit) {
        this.dirtyBit = dirtyBit;
    }

    public int[] getLine() {
        return line;
    }

    public void setLine(int[] line) {
        this.line = line;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public String toString(){
        return "index is:  " + index + "   Lru is:  " + lru.getTime() + "  Tag is:  " + tag;
    }

}
