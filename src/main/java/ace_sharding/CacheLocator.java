package ace_sharding;

import java.util.SortedMap;
import java.util.TreeMap;

public class CacheLocator {

	private SortedMap<Long, String> circle = new TreeMap<Long, String>();
	
	private static final int numberOfReplicas = 200;
	
	public CacheLocator(String[] caches) {

		for(String cache:caches){
			
			for (int i = 0; i < numberOfReplicas; i++) {
					String nodeId = cache+"vn"+i;
					circle.put(hash(nodeId), cache);
			}
		}	
	}
	
	public String locate(String key) {
		if (circle.isEmpty()) {
			return null;
		}
		long hash = hash(key);
		if (!circle.containsKey(hash)) {
			SortedMap<Long,String> tailMap = circle.tailMap(hash);
			hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
		}
		String value = circle.get(hash);
		return value;
		
	}
	
	
	private static final long FNV_64_INIT = 0xcbf29ce484222325L;  
    private static final long FNV_64_PRIME = 0x100000001b3L;  

	/** 
     * 使用FNV1_64_HASH算法
     */ 
    private static long hash(String k) 
    { 
    	long rv = FNV_64_INIT;  
        int len = k.length();  
        for (int i = 0; i < len; i++) {  
            rv *= FNV_64_PRIME;  
            rv ^= k.charAt(i);  
        }  
        return rv;
    } 
}
