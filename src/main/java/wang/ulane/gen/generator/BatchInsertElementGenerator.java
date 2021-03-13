package wang.ulane.gen.generator;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

import wang.ulane.gen.service.CodeGeneratorConfig;
import wang.ulane.gen.util.MethodUtil;

public class BatchInsertElementGenerator extends AbstractXmlElementGenerator {

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("insert"); //$NON-NLS-1$

        answer.addAttribute(new Attribute("id", "batchInsert")); //$NON-NLS-1$
        FullyQualifiedJavaType parameterType = FullyQualifiedJavaType.getNewListInstance();
        answer.addAttribute(new Attribute("parameterType", parameterType.getFullyQualifiedName()));

        context.getCommentGenerator().addComment(answer);
        
        /*
         * 	    insert into drug_ord_brg (drug_ord_no, drug_id, drug_name, 
	      strength, quantity, `usage`, 
	      amt, tm_smp)
	    values 
		<foreach collection="list" item="item" separator=","> 
		    (#{item.drugOrdNo,jdbcType=VARCHAR}, #{item.drugId,jdbcType=INTEGER}, #{item.drugName,jdbcType=VARCHAR}, 
		      #{item.strength,jdbcType=VARCHAR}, #{item.quantity,jdbcType=INTEGER}, #{item.usage,jdbcType=VARCHAR}, 
		      #{item.amt,jdbcType=DECIMAL}, #{item.tmSmp,jdbcType=VARCHAR})
	   	</foreach> 
         */
        
//        answer.addElement(new TextElement(sb.toString()));
//        
//        XmlElement dynamicElement = new XmlElement("where"); //$NON-NLS-1$
//        answer.addElement(dynamicElement);

        StringBuilder titles = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        int i = 0;
        for (IntrospectedColumn introspectedColumn : ListUtilities.removeGeneratedAlwaysColumns(introspectedTable.getAllColumns())) {
        	if(i != 0){
        		titles.append(", ");
        		values.append(", ");
        	}
        	titles.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
        	values.append(MethodUtil.getListParameterClause(introspectedColumn));
            i++;
        }
        titles.append(")");
        values.append(")");
        
        if(CodeGeneratorConfig.JDBC_URL.contains("jdbc:oracle")){
        	generateOracle(answer, titles, values);
        }else{
        	generateMysql(answer, titles, values);
        }
        
        if (context.getPlugins().sqlMapSelectAllElementGenerated(answer, introspectedTable)) {
            parentElement.addElement(answer);
        }

    }
    
    /**
     * 默认生成mysql格式batchInsert
     * @return
     */
    private void generateMysql(XmlElement answer, StringBuilder titles, StringBuilder values){
    	List<Element> eles = new ArrayList<Element>();
        StringBuilder sb = new StringBuilder();
        sb.append("insert into ");
        sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime()).append(" ");
        sb.append(titles);
        sb.append(" values ");
        XmlElement foreachElem = new XmlElement("foreach");
        foreachElem.addAttribute(new Attribute("collection", "list"));
        foreachElem.addAttribute(new Attribute("item", "item"));
        foreachElem.addAttribute(new Attribute("separator", ","));
        foreachElem.addElement(new TextElement(values.toString()));
        answer.addElement(new TextElement(sb.toString()));
        answer.addElement(foreachElem);
    }
    
    private void generateOracle(XmlElement answer, StringBuilder titles, StringBuilder values){
    	List<Element> eles = new ArrayList<Element>();
        XmlElement foreachElem = new XmlElement("foreach");
        foreachElem.addAttribute(new Attribute("collection", "list"));
        foreachElem.addAttribute(new Attribute("item", "item"));
        foreachElem.addAttribute(new Attribute("separator", ""));
        
        StringBuilder sb = new StringBuilder();
        sb.append("insert into ");
        sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime()).append(" ");
        sb.append(titles);
        sb.append(" values ");
        foreachElem.addElement(new TextElement(sb.toString()));
        foreachElem.addElement(new TextElement(values.append(";").toString()));
        answer.addElement(new TextElement("begin"));
        answer.addElement(foreachElem);
        answer.addElement(new TextElement("end;"));
    }
}
