package wang.ulane.gen.generator;

import java.util.List;

import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.JavaMapperGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.SelectAllMethodGenerator;

public class CustomizeJavaMapperGenerator extends JavaMapperGenerator {

    @Override
    public List<CompilationUnit> getCompilationUnits() {
    	introspectedTable.setSelectByPrimaryKeyStatementId("getByKey");
    	introspectedTable.setUpdateByPrimaryKeyStatementId("updateByKey");
    	introspectedTable.setUpdateByPrimaryKeySelectiveStatementId("updateByKeySelective");
    	introspectedTable.setUpdateByPrimaryKeyWithBLOBsStatementId("updateByKeyWithBLOBs");
    	introspectedTable.setDeleteByPrimaryKeyStatementId("deleteByKey");
        List<CompilationUnit> answer = super.getCompilationUnits();
//        for(CompilationUnit unit:answer) {
//            if(unit.getClass().equals(Interface.class)) {
////                addSelectByConditionMethod((Interface)unit);
////                addSelectAllMethod((Interface)unit);
////                addSelectByPageMethod((Interface)unit);
////                addCountByConditionMethod((Interface)unit);
////                addDeleteByConditionMethod((Interface)unit);
//            }
//        }
        return answer;
    }
    
//    protected void addSelectByConditionMethod(Interface interfaze) {
//        AbstractJavaMapperMethodGenerator methodGenerator = new SelectByConditionMethodGenerator();
//        initializeAndExecuteGenerator(methodGenerator, interfaze);
//    }
//    
//    protected void addSelectAllMethod(Interface interfaze) {
//        AbstractJavaMapperMethodGenerator methodGenerator = new SelectAllMethodGenerator();
//        initializeAndExecuteGenerator(methodGenerator, interfaze);
//    }
//    
//    protected void addSelectByPageMethod(Interface interfaze) {
//        AbstractJavaMapperMethodGenerator methodGenerator = new SelectByPageMethodGenerator();
//        initializeAndExecuteGenerator(methodGenerator, interfaze);
//    }
//    
//    protected void addCountByConditionMethod(Interface interfaze) {
//        AbstractJavaMapperMethodGenerator methodGenerator = new CountByConditionMethodGenerator();
//        initializeAndExecuteGenerator(methodGenerator, interfaze);
//    }
//    
//    protected void addDeleteByConditionMethod(Interface interfaze) {
//        AbstractJavaMapperMethodGenerator methodGenerator = new DeleteByConditionMethodGenerator();
//        initializeAndExecuteGenerator(methodGenerator, interfaze);
//    }
    
    @Override
    public AbstractXmlGenerator getMatchedXMLGenerator(){
        return new CustomizeXMLMapperGenerator();
    }
}
