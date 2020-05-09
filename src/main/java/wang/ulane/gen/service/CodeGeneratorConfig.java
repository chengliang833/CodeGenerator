package wang.ulane.gen.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import wang.ulane.gen.main.TableDef;

public class CodeGeneratorConfig {
	// JDBC 相关配置信息
	protected static String JDBC_URL;
	protected static String JDBC_USERNAME;
	protected static String JDBC_PASSWORD;
	protected static String JDBC_DRIVER_CLASS_NAME;
	
	// 项目在硬盘上的基础路径
	protected static final String PROJECT_PATH = System.getProperty("user.dir");
	// java文件路径
	protected static String JAVA_PATH;
	// 资源文件路径
	protected static String RESOURCES_PATH;
	// 模板存放位置
	protected static String TEMPLATE_FILE_PATH;
	
	// 项目 Model 所在包
	protected static String MODEL_PACKAGE;
	// 项目 Mapper 所在包
	protected static String MAPPER_PACKAGE;
	// 项目 MapperXML 所在包
	protected static String MAPPERXML_PACKAGE;
	// 项目 Service 所在包
	protected static String SERVICE_PACKAGE;
	// 项目 Controller 所在包
	protected static String CONTROLLER_PACKAGE;
	
	// 生成的 Service 存放路径
	protected static String PACKAGE_PATH_SERVICE;
	// 生成的 Controller 存放路径
	protected static String PACKAGE_PATH_CONTROLLER;
	// 生成的 Mapper 存放路径
	protected static String PACKAGE_PATH_MAPPER;
	
	// 是否生成自定义方法
	public static Map<String, Boolean> CUSTOM_FUNC;
	
	//简化primarykey方法名
	public static boolean SIMPL_FUNC_NAME = false;
	
	//生成basecolumnlist
	public static boolean GEN_COLUMN_LIST_SQL = false;
	
	
	// 模板注释中 @author
	protected static String AUTHOR;
	// 模板注释中 @date
	protected static String DATE;
	
	public static List<TableDef> TABLES = new ArrayList<TableDef>();
}
