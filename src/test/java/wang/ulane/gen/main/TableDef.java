package wang.ulane.gen.main;

public class TableDef {
	private String tableName;
	private String modelName;
	private String mapperSufName = "Mapper";
	
	public TableDef(String tableName, String modelName, String mapperSufName) {
		this.tableName = tableName;
		this.modelName = modelName;
		if(mapperSufName != null){
			this.mapperSufName = mapperSufName;
		}
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
	
	
}
