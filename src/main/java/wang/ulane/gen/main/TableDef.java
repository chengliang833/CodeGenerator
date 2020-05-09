package wang.ulane.gen.main;

public class TableDef {
	private String tableName;
	private String modelName;
	private String mapperSufName = "Mapper";
	private String genKey;
	
	public TableDef(String tableName, String modelName, String mapperSufName, String genKey) {
		this.tableName = tableName;
		this.modelName = modelName;
		if(mapperSufName != null){
			this.mapperSufName = mapperSufName;
		}
		this.genKey = genKey;
	}
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getMapperSufName() {
		return mapperSufName;
	}
	public void setMapperSufName(String mapperSufName) {
		this.mapperSufName = mapperSufName;
	}
	public String getGenKey() {
		return genKey;
	}
	public void setGenKey(String genKey) {
		this.genKey = genKey;
	}
	
	
}
