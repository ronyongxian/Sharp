package ace_sharding;

import java.util.Random;

public class DB {
    public DB(String[] master, String[] slave) {
		super();
		this.master = master;
		this.slave = slave;
	}

	private String[] master;
    private String[] slave;
    private static Random random = new Random();
    
    public String getDB(boolean write){
    	if(write){
    		return master[random.nextInt(master.length)];
    	}else{
    		return slave[random.nextInt(slave.length)];
    	}
    }

    

	

	

}
