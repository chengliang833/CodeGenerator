package wang.ulane.gen.generator;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

public class GetByCardNoElementGenerator extends AbstractXmlElementGenerator {

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

        answer.addAttribute(new Attribute("id", "getByCardNo")); //$NON-NLS-1$
        answer.addAttribute(new Attribute("parameterType", new FullyQualifiedJavaType(String.class.getName()).getFullyQualifiedName()));
        answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
                                          introspectedTable.getBaseResultMapId()));
        context.getCommentGenerator().addComment(answer);

        answer.addElement(new TextElement("select"));
        answer.addElement(getBaseColumnListElement());

        answer.addElement(new TextElement("from"));
        answer.addElement(new TextElement(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime()));

        answer.addElement(new TextElement("where"));
        
        StringBuilder sb = new StringBuilder();
        sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedTable.getColumn("card_no")));
        sb.append(" = "); //$NON-NLS-1$
        sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedTable.getColumn("card_no")));
        
		answer.addElement(new TextElement(sb.toString()));
		
		answer.addElement(new TextElement("ORDER BY checkdate DESC,upddtm DESC"));
		answer.addElement(new TextElement("LIMIT 1"));
		
        if (context.getPlugins().sqlMapSelectAllElementGenerated(answer, introspectedTable)) {
            parentElement.addElement(answer);
        }

    }

}
