package wang.ulane.gen.generator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

public class CheckByIdDateElementGenerator extends AbstractXmlElementGenerator {

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

        answer.addAttribute(new Attribute("id", "checkByIdDate")); //$NON-NLS-1$
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
//		sb.append(" and ");
//		sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedTable.getColumn("crtdt")));
//		sb.append(" = "); //$NON-NLS-1$
//		sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedTable.getColumn("crtdt")));
		sb.append(" and ");
		sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedTable.getColumn("upddtm")));
		sb.append(" > "); //$NON-NLS-1$
		sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedTable.getColumn("upddtm")));
        
		answer.addElement(new TextElement(sb.toString()));
//        StringBuilder sb = new StringBuilder();
//        XmlElement dynamicElement = new XmlElement("where"); //$NON-NLS-1$
//        answer.addElement(dynamicElement);
//
//        for (IntrospectedColumn introspectedColumn : ListUtilities.removeGeneratedAlwaysColumns(introspectedTable.getAllColumns())) {
//            XmlElement isNotNullElement = new XmlElement("if"); //$NON-NLS-1$
//            sb.setLength(0);
//            sb.append("" + introspectedColumn.getJavaProperty());
//            sb.append(" != null"); //$NON-NLS-1$
//            isNotNullElement.addAttribute(new Attribute("test", sb.toString())); //$NON-NLS-1$
//            dynamicElement.addElement(isNotNullElement);
//
//            sb.setLength(0);
//            sb.append("and ");
//            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
//            sb.append(" = "); //$NON-NLS-1$
//            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
//
//            isNotNullElement.addElement(new TextElement(sb.toString()));
//        }

        if (context.getPlugins().sqlMapSelectAllElementGenerated(answer, introspectedTable)) {
            parentElement.addElement(answer);
        }

    }

}
