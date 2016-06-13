package ace_sharding;

import java.util.SortedMap;
import java.util.TreeMap;

public class DBLocator {

	private SortedMap<Long, Object[]> circle = new TreeMap<Long, Object[]>();
	
	private static final int numberOfReplicas = 200;
	
	public DBLocator(Table table,DB[] dbs) {
		String tableName = table.getName();
		for(DB db:dbs){
			
			for(int j=1,l=table.getNumInOneDB();j<=l;j++){
				Object[] value = new Object[]{db,tableName+"_"+j};
				for (int i = 0; i < numberOfReplicas; i++) {
					String nodeId = db.hashCode()+tableName+j+"vn"+i;
					circle.put(hash(nodeId), value);
				}
			}
		}
	}
	
	public String[] locate(String key, boolean write) {
		if (circle.isEmpty()) {
			return null;
		}
		long hash = hash(key);
		if (!circle.containsKey(hash)) {
			SortedMap<Long,Object[]> tailMap = circle.tailMap(hash);
			hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
		}
		Object[] value = circle.get(hash);
		return new String[]{((DB)value[0]).getDB(write),(String) value[1]};
		
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
