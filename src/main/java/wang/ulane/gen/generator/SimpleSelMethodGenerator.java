package wang.ulane.gen.generator;

import java.util.Set;
import java.util.TreeSet;

import org.apache.ibatis.annotations.Param;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import wang.ulane.gen.service.CodeGeneratorConfig;

public class SimpleSelMethodGenerator extends AbstractJavaMapperMethodGenerator {

    public SimpleSelMethodGenerator(){
        super();
    }

    @Override
    public void addInterfaceElements(Interface interfaze) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		importedTypes.add(new FullyQualifiedJavaType(Param.class.getName()));

		FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());

		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(returnType);
		method.setName("simpleSel");

		FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(String.class.getName());
		for(String field:CodeGeneratorConfig.CUSTOM_FUNC_SIMPSEL.getWhereFields()){
			String fieldUpper = field;
			if(!CodeGeneratorConfig.USE_ACTUAL_COLUMN_NAMES 
					|| !(CodeGeneratorConfig.USE_ACTUAL_COLUMN_NAMES_REGEX == null || 
           				 	(CodeGeneratorConfig.USE_ACTUAL_COLUMN_NAMES_REGEX != null 
           				 	&& field.matches(CodeGeneratorConfig.USE_ACTUAL_COLUMN_NAMES_REGEX))) ){
				fieldUpper = JavaBeansUtil.getCamelCaseString(field, false);
			}
			method.addParameter(new Parameter(parameterType, fieldUpper, "@Param(\""+fieldUpper+"\")")); //$NON-NLS-1$
		}

		context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);

		addMapperAnnotations(interfaze, method);

		if (context.getPlugins().clientSelectAllMethodGenerated(method, interfaze, introspectedTable)) {
			interfaze.addImportedTypes(importedTypes);
			interfaze.addMethod(method);
		}
    }

    public void addMapperAnnotations(Interface interfaze, Method method) {
    }
}
