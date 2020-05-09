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

public class GetByCardNoMethodGenerator extends AbstractJavaMapperMethodGenerator {

    public GetByCardNoMethodGenerator(){
        super();
    }

    @Override
    public void addInterfaceElements(Interface interfaze) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		importedTypes.add(FullyQualifiedJavaType.getNewListInstance());
		importedTypes.add(new FullyQualifiedJavaType(Param.class.getName()));

		FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());

		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(returnType);
		method.setName("getByCardNo");

		FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(String.class.getName());
		method.addParameter(new Parameter(parameterType, "cardNo", "@Param(\"cardNo\")")); //$NON-NLS-1$
//		method.addParameter(new Parameter(parameterType, "crtdt", "@Param(\"crtdt\")")); //$NON-NLS-1$
//		method.addParameter(new Parameter(parameterType, "upddtm", "@Param(\"upddtm\")")); //$NON-NLS-1$

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
