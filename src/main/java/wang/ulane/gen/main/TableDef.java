package wang.ulane.gen.main;

import java.util.List;
import java.util.Map;

public class TableDef {
	private String tableName;
	private String modelName;
	private String mapperSufName = "Mapper";
	private String genKey;
	private String rootClass;
	private List<Map<String,String>> retainPartColumnNamesList;
	
	public TableDef(String tableName, String modelName, String mapperSufName, String genKey, String rootClass) {
		this.tableName = tableName;
		this.modelName = modelName;
		if(mapperSufName != null){
			this.mapperSufName = mapperSufName;
		}
		this.genKey = genKey;
		this.rootClass = rootClass;
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
	public String getRootClass() {
		return rootClass;
	}
	public void setRootClass(String rootClass) {
		this.rootClass = rootClass;
	}

	public List<Map<String, String>> getRetainPartColumnNamesList() {
		return retainPartColumnNamesList;
	}
	public void setRetainPartColumnNamesList(List<Map<String, String>> retainPartColumnNamesList) {
		this.retainPartColumnNamesList = retainPartColumnNamesList;
	}
	
	
}
