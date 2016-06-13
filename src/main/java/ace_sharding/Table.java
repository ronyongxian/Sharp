package ace_sharding;

public class Table {
    public Table(String name, int numInOneDB) {
		super();
		this.name = name;
		this.numInOneDB = numInOneDB;
	}

	private String name;
    private int numInOneDB;

   

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumInOneDB() {
		return numInOneDB;
	}

	public void setNumInOneDB(int numInOneDB) {
		this.numInOneDB = numInOneDB;
	}

}
