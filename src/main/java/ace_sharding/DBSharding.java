package ace_sharding;


public interface DBSharding<T> {

	/**
	 * 
	 * @param conditionKey
	 * @param shardingKey
	 * @param write
	 * @return [dbAlias,tableName]
	 */
    public String[] rule(T conditionKey, String shardingKey,boolean write);

}
