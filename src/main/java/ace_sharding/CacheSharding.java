package ace_sharding;


public interface CacheSharding<T> {

	/**
	 * 
	 * @param shardingKey
	 * @return cacheAlias
	 */
    public String rule(String shardingKey);

}
