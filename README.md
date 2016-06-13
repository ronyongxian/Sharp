# Sharp
a sharding framework for distribute db and cache


##use FNV1_64_HASH and Consistency algorithm


##feature
custom table number;
support master and slave;
random hit slave;
support cache


##api use case
    
    final DB mysql1 = new DB(new String[]{"m1"}, new String[]{"s11","s12"});
    	final DB mysql2 = new DB(new String[]{"m2"}, new String[]{"s21","s22"});
    	DBSharding<Long> userSharding = new DBSharding<Long>() {
    		Table userTable = new Table("user", 16);
        	DBLocator hl1 = new DBLocator(userTable, new DB[]{mysql1});
        	DBLocator hl2 = new DBLocator(userTable, new DB[]{mysql1,mysql2});
			public String[] rule(Long conditionKey, String shardingKey,boolean write) {
				return conditionKey>100000000?hl1.locate(shardingKey, write):hl2.locate(shardingKey, write);
			}
		};
		//return [dbAlias,tableName]
		logger.info(JsonUtil.toJson(userSharding.rule(2342524233333333l, String.valueOf(2342524233333333l), false)));
		
		CacheSharding<Long> userCacheSharding = new CacheSharding<Long>() {

			CacheLocator cl = new CacheLocator(new String[]{"redis1","redis2"});
			public String rule(String shardingKey) {
				// TODO Auto-generated method stub
				return cl.locate(shardingKey);
			}
		};
		//return cacheAlias
		logger.info(JsonUtil.toJson(userCacheSharding.rule(String.valueOf(2342524233333333l))));

