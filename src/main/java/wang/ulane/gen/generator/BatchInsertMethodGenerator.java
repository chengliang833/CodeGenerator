package wang.ulane.gen.generator;

import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;

public class BatchInsertMethodGenerator extends AbstractJavaMapperMethodGenerator {

    public BatchInsertMethodGenerator(){
        super();
    }

    @Override
    public void addInterfaceElements(Interface interfaze) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setName("batchInsert");
        
        //list
        FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType("List");
        importedTypes.add(FullyQualifiedJavaType.getNewListInstance());
        //record
        FullyQualifiedJavaType listArgType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        importedTypes.add(listArgType);
        //list<record>
        parameterType.addTypeArgument(listArgType);
        
        method.addParameter(new Parameter(parameterType, "records")); //$NON-NLS-1$
        
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
