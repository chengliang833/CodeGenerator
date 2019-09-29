package wang.ulane.gen.generator;

import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.SimpleSelectAllElementGenerator;

public class CustomizeXMLMapperGenerator extends XMLMapperGenerator{
    public CustomizeXMLMapperGenerator() {
        super();
    }
    protected XmlElement getSqlMapElement() {
    	introspectedTable.setSelectByPrimaryKeyStatementId("getByKey");
    	introspectedTable.setUpdateByPrimaryKeyStatementId("updateByKey");
    	introspectedTable.setUpdateByPrimaryKeySelectiveStatementId("updateByKeySelective");
    	introspectedTable.setUpdateByPrimaryKeyWithBLOBsStatementId("updateByKeyWithBLOBs");
    	introspectedTable.setDeleteByPrimaryKeyStatementId("deleteByKey");
        XmlElement xmlElement = super.getSqlMapElement();
//        addSelectByConditionElement(xmlElement);
//        addSelectAllElement(xmlElement);
//        addSelectByPageElement(xmlElement);
//        addCountByConditionELement(xmlElement);
//        addDeleteByConditionELement(xmlElement);
        return xmlElement;
    }
//    protected void addSelectByConditionElement(XmlElement parentElement) {
//        AbstractXmlElementGenerator elementGenerator = new SelectByConditionElementGenerator();
//        initializeAndExecuteGenerator(elementGenerator, parentElement);
//    }
//    protected void addSelectAllElement(XmlElement parentElement) {
//        AbstractXmlElementGenerator elementGenerator = new SimpleSelectAllElementGenerator();
//        initializeAndExecuteGenerator(elementGenerator, parentElement);
//    }
//    protected void addSelectByPageElement(XmlElement parentElement) {
//        AbstractXmlElementGenerator elementGenerator = new SelectByPageElementGenerator();
//        initializeAndExecuteGenerator(elementGenerator, parentElement);
//    }
//    protected void addCountByConditionELement(XmlElement parentElement) {
//        AbstractXmlElementGenerator elementGenerator = new CountByConditionElementGenerator();
//        initializeAndExecuteGenerator(elementGenerator, parentElement);
//    }
//    protected void addDeleteByConditionELement(XmlElement parentElement) {
//        AbstractXmlElementGenerator elementGenerator = new DeleteByConditionElementGenerator();
//        initializeAndExecuteGenerator(elementGenerator, parentElement);
//    }
}
