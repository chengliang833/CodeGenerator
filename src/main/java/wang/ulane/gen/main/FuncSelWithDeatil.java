package wang.ulane.gen.main;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class FuncSelWithDeatil {
	
	private boolean flag = false;
	private Set<String> mainModels;
	private Map<String, List<String>> modelRelate;
	
	public boolean containModel(String modelName){
		return mainModels.contains(modelName);
	}
	
	public List<String> getSubModel(String mainModel){
		return modelRelate.get(mainModel);
	}
	
	public static String getFirstLower(String modelName){
		return modelName.substring(0,1).toLowerCase() + modelName.substring(1);
	}
	
	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Set<String> getMainModels() {
		return mainModels;
	}

	public void setMainModels(Set<String> mainModels) {
		this.mainModels = mainModels;
	}

	public Map<String, List<String>> getModelRelate() {
		return modelRelate;
	}

	public void setModelRelate(Map<String, List<String>> modelRelate) {
		this.modelRelate = modelRelate;
	}
	
}
