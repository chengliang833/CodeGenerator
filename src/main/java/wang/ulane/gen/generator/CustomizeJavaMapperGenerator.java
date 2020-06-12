package wang.ulane.gen.generator;

import java.util.Arrays;
import java.util.List;

import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.JavaMapperGenerator;

import wang.ulane.gen.service.CodeGeneratorConfig;

public class CustomizeJavaMapperGenerator extends JavaMapperGenerator {
	
	public static List<String> GenColListFunc = Arrays.asList("checkByIdDate","getByCardNo","selectByCondition");
	
    @Override
    public List<CompilationUnit> getCompilationUnits() {
    	if(CodeGeneratorConfig.SIMPL_FUNC_NAME){
    		introspectedTable.setSelectByPrimaryKeyStatementId("getByKey");
    		introspectedTable.setUpdateByPrimaryKeyStatementId("updateByKey");
    		introspectedTable.setUpdateByPrimaryKeySelectiveStatementId("updateByKeySelective");
    		introspectedTable.setUpdateByPrimaryKeyWithBLOBsStatementId("updateByKeyWithBLOBs");
    		introspectedTable.setDeleteByPrimaryKeyStatementId("deleteByKey");
    	}
        List<CompilationUnit> answer = super.getCompilationUnits();
        for(CompilationUnit unit:answer) {
            if(unit.getClass().equals(Interface.class)) {
            	if(CodeGeneratorConfig.CUSTOM_FUNC.getOrDefault("batchInsert", false)){
            		initializeAndExecuteGenerator(new BatchInsertMethodGenerator(), (Interface)unit);
            	}
            	if(CodeGeneratorConfig.CUSTOM_FUNC.getOrDefault("insertOrUpdate", false)){
            		initializeAndExecuteGenerator(new InsertOrUpdateMethodGenerator(), (Interface)unit);
            	}
            	if(CodeGeneratorConfig.CUSTOM_FUNC.getOrDefault("checkByIdDate", false)){
            		initializeAndExecuteGenerator(new CheckByIdDateMethodGenerator(), (Interface)unit);
            	}
            	if(CodeGeneratorConfig.CUSTOM_FUNC.getOrDefault("countByCondition", false)){
            		initializeAndExecuteGenerator(new CountByConditionMethodGenerator(), (Interface)unit);
            	}
            	if(CodeGeneratorConfig.CUSTOM_FUNC.getOrDefault("deleteByCondition", false)){
            		initializeAndExecuteGenerator(new DeleteByConditionMethodGenerator(), (Interface)unit);
            	}
            	if(CodeGeneratorConfig.CUSTOM_FUNC.getOrDefault("getByCardNo", false)){
            		initializeAndExecuteGenerator(new GetByCardNoMethodGenerator(), (Interface)unit);
            	}
            	if(CodeGeneratorConfig.CUSTOM_FUNC.getOrDefault("selectByCondition", false)){
            		initializeAndExecuteGenerator(new SelectByConditionMethodGenerator(), (Interface)unit);
            	}
            }
        }
        return answer;
    }
    
    @Override
    public AbstractXmlGenerator getMatchedXMLGenerator(){
        return new CustomizeXMLMapperGenerator();
    }
}
