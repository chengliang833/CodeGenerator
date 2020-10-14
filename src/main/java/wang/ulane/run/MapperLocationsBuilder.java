package wang.ulane.run;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.QName;
import org.dom4j.dom.DOMAttribute;
import org.dom4j.io.SAXReader;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import wang.ulane.file.ConvertUtil;

/**
 * mapper构建
 *
 */
public class MapperLocationsBuilder {

	private static Log LOGGER = LogFactory.getLog(MapperLocationsBuilder.class);
	
	private static final String XML_SUFFIX = ".xml";
	private static final String MAPPER_START = "<mapper>";
	private static final String MAPPER_END = "</mapper>";
	
	private static final String TEMPLATE_SUFFIX = ".vm";
	
	private static final String COMMON_SQL_CLASSPATH = "ecmybatis/commonSql.xml";
	
	private static String DEFAULT_CLASS_PATH = "/ecmybatis/tpl/";
	
	private String commonSqlClasspath = COMMON_SQL_CLASSPATH;
	
	private Map<String, Resource> extMappers = new HashMap<String, Resource>();
	
	private SAXReader saxReader;
	
	private Attribute NAMESPACE;
	
	private DataSource dataSource;
	
	private String templateClasspath;
	
	
	/**
	 * 初始化mybatis配置文件
	 * 
	 * @param basePackage
	 *            实体类的包目录.指定哪些包需要被扫描,支持多个包"package.a,package.b"并对每个包都会递归搜索
	 */
	public Resource[] build(String basePackage, Map<String, List<String>> excludeMapper) {
		init();
		try {
			String[] basePackages = StringUtils.tokenizeToStringArray(basePackage,
					ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
			ClassScanner classScanner = new ClassScanner(basePackages, null);
			if(excludeMapper != null){
				classScanner.setExcludeMapper(excludeMapper);
			}
			Set<Class<?>> clazzsSet = classScanner.getClassSet();

			Resource[] mapperLocations = this.buildMapperLocations(clazzsSet);

			return mapperLocations;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally {
			distroy();
		}
	}
	
	private void init() {
		saxReader = new SAXReader();
		NAMESPACE = new DOMAttribute(new QName("namespace"));
	}
	
	private void distroy() {
		saxReader = null;
		NAMESPACE = null;
		extMappers.clear();
	}

	
	// 存储xml文件
	public void storeMapperFile(Resource[] mapperLocations) {
		for (Resource mapperLocation : mapperLocations) {
			String filename = mapperLocation.getFilename();// XxDao.xml /XxMapper.xml
			
			extMappers.put(filename, mapperLocation);
		}
	}
	
	private Resource getMapperFile(String mapperFileName) {
		return extMappers.get(mapperFileName);
	}

	private Resource[] buildMapperLocations(Set<Class<?>> clazzsSet) {
		
		List<Resource> mapperLocations = new ArrayList<>(clazzsSet.size());
		
		for (Class<?> daoClass : clazzsSet) {

//			String xml = codeClient.genMybatisXml(daoClass, templateClasspath).replaceAll("[\\u4e00-\\u9fa5]", "");
			String xml = "";

			xml = this.modifyFileContent(daoClass, xml);
			
			if (LOGGER.isDebugEnabled()) {
//				LOGGER.debug("生成mybatis文件:\r\n" + xml);
			}
			
			Resource resource = new MapperXmlResource(xml, daoClass);
			mapperLocations.add(resource);
		}
		
		return mapperLocations.toArray(new Resource[mapperLocations.size()]);
	}
	
	// 修改文件内容
	private String modifyFileContent(Class<?> mapperClass, String xml) {
		// 自定义文件
		String mapperFileName = mapperClass.getSimpleName() + XML_SUFFIX;
		Resource mapperLocation = this.getMapperFile(mapperFileName);

		if (mapperLocation == null) {
			return xml;
		}
		StringBuilder sb = new StringBuilder();

		sb.append(xml.replace(MAPPER_END, "")); // 先去掉</mapper>
		// 追加内容
		String extFileContent = this.getExtFileContentByStr(mapperLocation);
		
		if (LOGGER.isDebugEnabled()) {
//			LOGGER.debug("追加自定义sql:\r\n " + extFileContent);
		}
		sb.append(extFileContent).append(MAPPER_END); // 追加自定义sql,加上</mapper>

		return sb.toString();

	}
	
//	/**
//	 * 获取已有Mapper文件内容,saxReader读取太慢,改用getExtFileContentByStr
//	 * @param mapperLocation
//	 * @return
//	 */
//	private String getExtFileContent(Resource mapperLocation) {
//		try {
//			InputStream in = mapperLocation.getInputStream();
//		    Document document = saxReader.read(in); 
//		    Element mapperNode = document.getRootElement();
//		    
//		    String rootNodeName = mapperNode.getName();
//		    
//		    if(!"mapper".equals(rootNodeName)) {
//		    	throw new Exception("mapper文件必须含有<mapper>节点,是否缺少<mapper></mapper>?");
//		    }
//		    
//		    mapperNode.remove(NAMESPACE);
//		    
//		    String rootXml = mapperNode.asXML();
//		    // 去除首尾mapper标签
//		    rootXml = rootXml.replace(MAPPER_START, "").replace(MAPPER_END, "");
//		    
//			return rootXml;
//		} catch (Exception e) {
//			LOGGER.error(e.getMessage(),e);
//			throw new RuntimeException("加载资源文件出错," + e.getMessage());
//		} 
//	}
	
	private String getExtFileContentByStr(Resource mapperLocation){
		try {
		    String rootXml = ConvertUtil.parseString(mapperLocation.getInputStream());
		    // 去除首尾mapper标签
			rootXml = rootXml.substring(rootXml.indexOf("<mapper")+7, rootXml.lastIndexOf("</mapper>"));
			rootXml = rootXml.substring(rootXml.indexOf(">")+1);
		    
			return rootXml;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			throw new RuntimeException("加载资源文件出错," + e.getMessage());
		}
	}
	
}
