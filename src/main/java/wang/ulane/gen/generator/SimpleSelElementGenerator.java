package wang.ulane.gen.generator;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

import wang.ulane.gen.service.CodeGeneratorConfig;

public class SimpleSelElementGenerator extends AbstractXmlElementGenerator {

    @Override
    public void addElements(XmlElement parentElement) {
        List<IntrospectedColumn> blobs = introspectedTable.getBLOBColumns();
        List<String> blobNames = new ArrayList<String>();
        for(IntrospectedColumn blob:blobs){
        	blobNames.add(MyBatis3FormattingUtilities.getEscapedColumnName(blob));
        }
        
        boolean blobResult = false;
        StringBuilder sb = new StringBuilder("  ");
        int i = 0;
        for(String field:CodeGeneratorConfig.CUSTOM_FUNC_SIMPSEL.getSelectFields()){
        	if(i != 0){
        		sb.append(", ");
        	}
        	if(blobNames.contains(field)){
        		blobResult = true;
        	}
        	sb.append(field);
        	i++;
        }
    	
        XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

        answer.addAttribute(new Attribute("id", "simpleSel")); //$NON-NLS-1$
//        answer.addAttribute(new Attribute("parameterType", new FullyQualifiedJavaType(String.class.getName()).getFullyQualifiedName()));
        if (blobResult) {
            answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
                    introspectedTable.getResultMapWithBLOBsId()));
        } else {
            answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
                    introspectedTable.getBaseResultMapId()));
        }
        context.getCommentGenerator().addComment(answer);

        answer.addElement(new TextElement("select"));
        answer.addElement(new TextElement(sb.toString()));

        answer.addElement(new TextElement("from"));
        answer.addElement(new TextElement("  " + introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime()));

        answer.addElement(new TextElement("where"));
        
        sb = new StringBuilder("  ");
        i = 0;
        for(String field:CodeGeneratorConfig.CUSTOM_FUNC_SIMPSEL.getWhereFields()){
        	if(i != 0){
        		sb.append(" and ");
        	}
        	sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedTable.getColumn(field)));
        	sb.append(" = "); //$NON-NLS-1$
        	sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedTable.getColumn(field)));
        	i++;
        }
        
		answer.addElement(new TextElement(sb.toString()));

        if (context.getPlugins().sqlMapSelectAllElementGenerated(answer, introspectedTable)) {
            parentElement.addElement(answer);
        }

    }

}
