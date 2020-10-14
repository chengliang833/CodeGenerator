package wang.ulane.gen.main;

import java.util.List;

public class FuncSimpSel {
	
	private boolean flag = false;
	private List<String> whereFields;
	private List<String> selectFields;
	
	public FuncSimpSel() {
		super();
	}
	
	public FuncSimpSel(boolean flag, List<String> whereFields, List<String> selectFields) {
		super();
		this.flag = flag;
		this.whereFields = whereFields;
		this.selectFields = selectFields;
	}

	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public List<String> getWhereFields() {
		return whereFields;
	}
	public void setWhereFields(List<String> whereFields) {
		this.whereFields = whereFields;
	}
	public List<String> getSelectFields() {
		return selectFields;
	}
	public void setSelectFields(List<String> selectFields) {
		this.selectFields = selectFields;
	}
	
}
