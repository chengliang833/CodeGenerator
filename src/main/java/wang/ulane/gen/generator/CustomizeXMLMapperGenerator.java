package wang.ulane.gen.generator;

import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

import wang.ulane.gen.service.CodeGeneratorConfig;

public class CustomizeXMLMapperGenerator extends XMLMapperGenerator{
    public CustomizeXMLMapperGenerator() {
        super();
    }
    protected XmlElement getSqlMapElement() {
    	if(CodeGeneratorConfig.SIMPL_FUNC_NAME){
	    	introspectedTable.setSelectByPrimaryKeyStatementId("getByKey");
	    	introspectedTable.setUpdateByPrimaryKeyStatementId("updateByKey");
	    	introspectedTable.setUpdateByPrimaryKeySelectiveStatementId("updateByKeySelective");
	    	introspectedTable.setUpdateByPrimaryKeyWithBLOBsStatementId("updateByKeyWithBLOBs");
	    	introspectedTable.setDeleteByPrimaryKeyStatementId("deleteByKey");
    	}
        XmlElement xmlElement = super.getSqlMapElement();
        
    	if(CodeGeneratorConfig.CUSTOM_FUNC.getOrDefault("preBaseColum", false)){
    		initializeAndExecuteGenerator(new BaseColumPreElementGenerator(), xmlElement);
    	}
    	if(CodeGeneratorConfig.CUSTOM_FUNC.getOrDefault("batchInsert", false)){
    		initializeAndExecuteGenerator(new BatchInsertElementGenerator(), xmlElement);
    	}
    	if(CodeGeneratorConfig.CUSTOM_FUNC.getOrDefault("insertOrUpdate", false)){
    		initializeAndExecuteGenerator(new InsertOrUpdateElementGenerator(), xmlElement);
    	}
    	if(CodeGeneratorConfig.CUSTOM_FUNC.getOrDefault("checkByIdDate", false)){
    		initializeAndExecuteGenerator(new CheckByIdDateElementGenerator(), xmlElement);
    	}
    	if(CodeGeneratorConfig.CUSTOM_FUNC.getOrDefault("countByCondition", false)){
    		initializeAndExecuteGenerator(new CountByConditionElementGenerator(), xmlElement);
    	}
    	if(CodeGeneratorConfig.CUSTOM_FUNC.getOrDefault("deleteByCondition", false)){
    		initializeAndExecuteGenerator(new DeleteByConditionElementGenerator(), xmlElement);
    	}
    	if(CodeGeneratorConfig.CUSTOM_FUNC.getOrDefault("getByCardNo", false)){
    		initializeAndExecuteGenerator(new GetByCardNoElementGenerator(), xmlElement);
    	}
    	if(CodeGeneratorConfig.CUSTOM_FUNC.getOrDefault("selectByCondition", false)){
    		initializeAndExecuteGenerator(new SelectByConditionElementGenerator(), xmlElement);
    	}
    	
        return xmlElement;
    }
    
}
