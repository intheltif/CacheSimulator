import java.util.ArrayList;

public class Cache {
    private ArrayList<ArrayList<CacheSets>> cache;

    public Cache(){
        cache = new ArrayList<>();
    }


    public ArrayList<ArrayList<CacheSets>> getCache(){
        return cache;
    }

    public void setupCache(int[] cacheSetup){
        int numSets = cacheSetup[0];
        int setSize = cacheSetup[1];
        int lineSize = cacheSetup[2];
        for(int i = 0; i < numSets; i++){
            ArrayList<CacheSets> cacheSets = new ArrayList<>();
            for(int j = 0; j < setSize; j++){
                CacheSets cacheLine = new CacheSets(lineSize);
                cacheLine.setIndex(i);
                cacheSets.add(cacheLine);

            }
            cache.add(cacheSets);
        }

    }
}
