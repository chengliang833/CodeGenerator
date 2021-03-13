package wang.ulane.gen.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.mybatis.generator.api.IntrospectedTable.TargetRuntime;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.config.JavaTypeResolverConfiguration;
import org.mybatis.generator.config.ModelType;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import wang.ulane.gen.generator.CustomizeJavaMapperGenerator;
import wang.ulane.gen.main.TableDef;
import wang.ulane.gen.service.impl.ModelAndMapperGenerator;
import wang.ulane.gen.service.impl.ServiceGenerator;

/**
 * 代码生成器基础项 (常量信息 & 通用方法)
 */
public class CodeGeneratorManager extends CodeGeneratorConfig {

    protected static final Logger logger        = LoggerFactory.getLogger(CodeGeneratorManager.class);

    private static Configuration  configuration = null;
    
    /**
     * The primary columns of current generated table
     */
    public static List<String> primaryColumnsList = new ArrayList<String>();
    /**
     * all columns of current generated table.
     */
    public static List<String> allColumnsList = new ArrayList<String>();

    static {
        // 初始化配置信息
        init();
    }

    /**
     * 获取 Freemarker 模板环境配置
     * 
     * @return
     */
    public Configuration getFreemarkerConfiguration() {
        if (configuration == null) {
            configuration = initFreemarkerConfiguration();
        }
        return configuration;
    }

    /**
     * Mybatis 代码自动生成基本配置
     * 
     * @return
     */
    public Context initMybatisGeneratorContext(String mapperStorePackge) {
        Context context = new Context(ModelType.FLAT);
        context.setId("Potato");
        // context.setTargetRuntime("MyBatis3Simple");
        context.setTargetRuntime(TargetRuntime.MYBATIS3.name());
        context.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, "`");
        context.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, "`");
        context.addProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING, "UTF-8");

        Properties p = new Properties();
        // 生成时去掉所有注释
        p.setProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS, "false");
        context.getCommentGenerator().addConfigurationProperties(p);
        JavaTypeResolverConfiguration javaTypeResolverConfiguration = new JavaTypeResolverConfiguration();
        javaTypeResolverConfiguration.addProperty(PropertyRegistry.TYPE_RESOLVER_FORCE_BIG_DECIMALS,"false");
        context.setJavaTypeResolverConfiguration(javaTypeResolverConfiguration);

        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(JDBC_URL);
        jdbcConnectionConfiguration.setUserId(JDBC_USERNAME);
        jdbcConnectionConfiguration.setPassword(JDBC_PASSWORD);
        jdbcConnectionConfiguration.setDriverClass(JDBC_DRIVER_CLASS_NAME);
        //mysql列注释
		jdbcConnectionConfiguration.addProperty("useInformationSchema", "true");
		//oracle列注释
        jdbcConnectionConfiguration.addProperty("remarksReporting", "true");
//        jdbcConnectionConfiguration.addProperty("reportRemarks", "true");
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetProject(PROJECT_PATH + RESOURCES_PATH);
        sqlMapGeneratorConfiguration.setTargetPackage(mapperStorePackge);
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

        return context;
    }

    /**
     * 通过数据库表名, 生成代码 如表名为 gen_test_demo 将生成 Demo & DemoMapper & DemoService & DemoServiceImpl & DemoController
     * 
     * @param flag 标志
     * @param tableNames 表名数组
     */
//    public void genCodeMain(String tableName) {
//      genCodeByTableName(tableName, null, null, null);
//	}
//    public void genCodeMain(String tableName, String modelName) {
//        genCodeByTableName(tableName, modelName, null, null);
//    }
//    public void genCodeMain(String tableName, String modelName, String mapperSuf) {
//        genCodeByTableName(tableName, modelName, mapperSuf, null);
//    }
//    public void genCodeMain(String tableName, String modelName, String mapperSuf, String genKey) {
//    	genCodeByTableName(tableName, modelName, mapperSuf, genKey);
//    }
    public void genCodeMain() {
        for(TableDef td:TABLES){
        	genCodeByTableName(td);
        }
    }

    /**
     * 通过数据库表名, 和自定义 modelName 生成代码 如表名为 gen_test_demo, 自定义 modelName 为 IDemo 将生成 IDemo & IDemoMapper & IDemoService &
     * IDemoServiceImpl & IDemoController
     * 
     * @param tableName 表名
     * @param modelName 实体类名
     * @param mapperSuf mapper文件后缀
     * @param genKey 自增key自动返回
     */
//    private void genCodeByTableName(String tableName, String modelName, String mapperSuf, String genKey) {
//        new ModelAndMapperGenerator(mapperSuf, genKey).genCode(tableName, modelName);
//        new ServiceGenerator(mapperSuf).genCode(tableName, modelName);
////        new ControllerGenerator(mapperSuf).genCode(tableName, modelName);
//    }
    private void genCodeByTableName(TableDef td) {
        new ModelAndMapperGenerator(td.getMapperSufName(), td.getGenKey(), td.getRootClass()).genCode(td.getTableName(), td.getModelName());
        new ServiceGenerator(td.getMapperSufName()).genCode(td.getTableName(), td.getModelName());
    }

    /**
     * Freemarker 模板环境配置
     * 
     * @return
     * @throws IOException
     */
    private Configuration initFreemarkerConfiguration() {
        Configuration cfg = null;
        try {
            cfg = new Configuration(Configuration.VERSION_2_3_23);
            File file = null;
            if(!StringUtils.isEmpty(TEMPLATE_FILE_PATH) && (file = new File(TEMPLATE_FILE_PATH)).exists()){
            	cfg.setDirectoryForTemplateLoading(file);
            }else{
            	cfg.setClassForTemplateLoading(this.getClass(), "/generator/template");
            	cfg.setTemplateLoader(new ClassTemplateLoader(this.getClass(), "/generator/template"));
            }
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        } catch (Exception e) {
            throw new RuntimeException("Freemarker 模板环境初始化异常!", e);
        }
        return cfg;
    }

    /**
     * 包转成路径 eg: com.bigsea.sns ==> com/bigsea/sns
     * 
     * @param packageName
     * @return
     */
    private static String packageConvertPath(String packageName) {
        return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
    }

    /**
     * 初始化配置信息
     */
    private static void init() {
        Properties prop = loadProperties();

        JDBC_URL = prop.getProperty("jdbc.url");
        JDBC_USERNAME = prop.getProperty("jdbc.username");
        JDBC_PASSWORD = prop.getProperty("jdbc.password");
        JDBC_DRIVER_CLASS_NAME = prop.getProperty("jdbc.driver.class.name");

        JAVA_PATH = prop.getProperty("java.path");
        File javaPath = new File(PROJECT_PATH+JAVA_PATH);
        if(!javaPath.exists()){
        	javaPath.mkdirs();
        }
        RESOURCES_PATH = prop.getProperty("resources.path");
        File resourcesPath = new File(PROJECT_PATH+RESOURCES_PATH);
        if(!resourcesPath.exists()){
        	resourcesPath.mkdirs();
        }
        
        TEMPLATE_FILE_PATH = StringUtils.isEmpty(prop.getProperty("template.file.path"))?"":(PROJECT_PATH + prop.getProperty("template.file.path"));

        MODEL_PACKAGE = prop.getProperty("model.package");
        MAPPER_PACKAGE = prop.getProperty("mapper.package");
        MAPPERXML_PACKAGE = prop.getProperty("mapperxml.package");
        SERVICE_PACKAGE = prop.getProperty("service.package");
        SERVICE_PACKAGE_IMPL = prop.getProperty("serviceimpl.package", SERVICE_PACKAGE+".impl");
//        CONTROLLER_PACKAGE = prop.getProperty("controller.package");

        PACKAGE_PATH_SERVICE = packageConvertPath(SERVICE_PACKAGE);
        PACKAGE_PATH_SERVICE_IMPL = packageConvertPath(SERVICE_PACKAGE_IMPL);
//        PACKAGE_PATH_CONTROLLER = packageConvertPath(CONTROLLER_PACKAGE);
        PACKAGE_PATH_MAPPER = packageConvertPath(MAPPER_PACKAGE);
        
        SIMPL_FUNC_NAME = Boolean.valueOf(prop.getProperty("custom.simplifyName", "false"));
        
        String USE_ACTUAL_COLUMN_NAMES_STR = prop.getProperty("custom.useActualColumnNames", "false");
        if(USE_ACTUAL_COLUMN_NAMES_STR.contains(",")){
        	int comIdx = USE_ACTUAL_COLUMN_NAMES_STR.indexOf(",");
        	USE_ACTUAL_COLUMN_NAMES = Boolean.valueOf(USE_ACTUAL_COLUMN_NAMES_STR.substring(0,comIdx));
        	USE_ACTUAL_COLUMN_NAMES_REGEX = USE_ACTUAL_COLUMN_NAMES_STR.substring(comIdx+1);
        }else{
        	//兼容前一个版本
        	USE_ACTUAL_COLUMN_NAMES = Boolean.valueOf(USE_ACTUAL_COLUMN_NAMES_STR);
        	USE_ACTUAL_COLUMN_NAMES_REGEX = prop.getProperty("custom.useActualColumnNamesRegex");
        }
        
        String RETAIN_PART_COLUMN_NAMES_STR = prop.getProperty("custom.retainPartColumnNames", "false");
        if(RETAIN_PART_COLUMN_NAMES_STR.contains(",")){
        	String[] strs = RETAIN_PART_COLUMN_NAMES_STR.split(",");
        	RETAIN_PART_COLUMN_NAMES = Boolean.valueOf(strs[0]);
        	if(RETAIN_PART_COLUMN_NAMES){
        		RETAIN_PART_COLUMN_NAMES_LIST = analysisForRetainColumn(1, strs);
//        		for(int i=1;i<strs.length;){
//        			Map<String,String> map = new HashMap<>();
//        			map.put("from", strs[i++].replace("[", ""));
//        			map.put("to", strs[i++].replace("]", ""));
//        			RETAIN_PART_COLUMN_NAMES_LIST.add(map);
//        		}
        	}
        }
        
        String RETAIN_COLUMN_TYPE_STR = prop.getProperty("custom.retainColumnType", "false");
        if(RETAIN_COLUMN_TYPE_STR.contains(",")){
        	String[] strs = RETAIN_COLUMN_TYPE_STR.split(",");
        	RETAIN_COLUMN_TYPE = Boolean.valueOf(strs[0]);
        	if(RETAIN_COLUMN_TYPE){
        		RETAIN_COLUMN_TYPE_LIST = analysisForRetainColumn(1, strs);
//        		for(int i=1;i<strs.length;){
//        			Map<String,String> map = new HashMap<>();
//        			map.put("from", strs[i++].replace("[", ""));
//        			map.put("to", strs[i++].replace("]", ""));
//        			RETAIN_COLUMN_TYPE_LIST.add(map);
//        		}
        	}
        }
        
        JSONObject funcSimpSel = JSON.parseObject(prop.getProperty("custom.funcSimpSel","{}"));
        if(funcSimpSel.getBoolean("flag") != null && funcSimpSel.getBooleanValue("flag")){
        	CUSTOM_FUNC_SIMPSEL.setFlag(true);
        	CUSTOM_FUNC_SIMPSEL.setSelectFields(Arrays.asList(funcSimpSel.getString("select").split(",")));
        	CUSTOM_FUNC_SIMPSEL.setWhereFields(Arrays.asList(funcSimpSel.getString("where").split(",")));
        }
        
        JSONObject funcSelwithdeatil = JSON.parseObject(prop.getProperty("custom.funcSelWithDeatil","{}"));
        if(funcSelwithdeatil.getBoolean("flag") != null && funcSelwithdeatil.getBooleanValue("flag")){
        	CUSTOM_FUNC_SELWITHDEATIL.setFlag(true);
        	funcSelwithdeatil.remove("flag");
        	CUSTOM_FUNC_SELWITHDEATIL.setMainModels(funcSelwithdeatil.keySet());
//        	CUSTOM_FUNC_SELWITHDEATIL.setModelRelate(JSON.toJavaObject(funcSelwithdeatil, Map.class));
        	Map<String,List<String>> map = new HashMap<>();
        	for(String key:CUSTOM_FUNC_SELWITHDEATIL.getMainModels()){
        		map.put(key, Arrays.asList(funcSelwithdeatil.getString(key).split(",")));
        	}
        	CUSTOM_FUNC_SELWITHDEATIL.setModelRelate(map);
        }else{
        	CUSTOM_FUNC_SELWITHDEATIL.setMainModels(new HashSet<>());
        	CUSTOM_FUNC_SELWITHDEATIL.setModelRelate(new HashMap<>());
        }
        
        MODEL_SWAGGER_ANNOTATION = Boolean.valueOf(prop.getProperty("custom.modelSwaggerAnnotation", "false"));
        
        CUSTOM_FUNC = JSON.parseObject(prop.getProperty("custom.func","{}")).toJavaObject(Map.class);
        
        GEN_DEFAULT = JSON.parseObject(prop.getProperty("custom.genDefault","{}")).toJavaObject(Map.class);
        
        for(String func:CUSTOM_FUNC.keySet()){
        	if(CUSTOM_FUNC.getOrDefault(func, false)){
        		CUSTOM_FUNC_GEN = true;
        		if(CustomizeJavaMapperGenerator.GenColListFunc.contains(func)){
        			GEN_COLUMN_LIST_SQL = true;
        			break;
        		}
        	}
        }
        
        AUTHOR = "";
        String dateFormat = StringUtils.isEmpty(prop.getProperty("date-format")) ? "yyyy/MM/dd" : prop.getProperty("date-format");
        DATE = new SimpleDateFormat(dateFormat).format(new Date());
        
        Set<Object> propSet = prop.keySet();
        Map<String, String[]> mapTemp = new HashMap<>();
        for(Object p:propSet){
        	String key = p.toString();
        	String[] values = prop.getProperty(p.toString()).split(",");
        	if(key.startsWith("tablecolname_")){
        		mapTemp.put(key.substring(13), values);
        	}
        }
        for(Object p:propSet){
        	String key = p.toString();
        	String[] values = prop.getProperty(p.toString()).split(",");
        	if(key.startsWith("table_")){
        		TableDef td = new TableDef(key.substring(6), 
											values[0], 
											values.length>1?values[1]:null, 
											values.length>2?("null".equals(values[2])?null:values[2]):null, 
											values.length>3?values[3]:null);
        		if(mapTemp.containsKey(key.substring(6))){
        			td.setRetainPartColumnNamesList(analysisForRetainColumn(0, mapTemp.get(key.substring(6))));
        		}
        		TABLES.add(td);
        		TABLESMAP.put(key.substring(6), td);
        		
        	}
        }
        
    }

    /**
     * 加载配置文件
     * 
     * @return
     */
    private static Properties loadProperties() {
        Properties prop = null;
        try {
            prop = new Properties();
            File file = new File(PROJECT_PATH+"/src/main/resources/generatorConfig.properties");
            File file2 = null;
            InputStream in;
            if(file.exists()){
            	System.out.println("use custom file");
            	in = new FileInputStream(file);
            }else if((file2 = new File(PROJECT_PATH+"/generatorConfig.properties")).exists()){
        		System.out.println("use custom2 file");
        		in = new FileInputStream(file2);
            }else{
            	System.out.println("use exists file");
            	in = CodeGeneratorManager.class.getClassLoader().getResourceAsStream("generatorConfig.properties");
            }
            prop.load(in);
        } catch (Exception e) {
            throw new RuntimeException("加载配置文件异常!", e);
        }
        return prop;
    }
    
    private static List<Map<String,String>> analysisForRetainColumn(int iStart, String[] strs){
    	List<Map<String,String>> list = new ArrayList<>();
		for(int i=iStart;i<strs.length;){
			Map<String,String> map = new HashMap<>();
			map.put("from", strs[i++].replace("[", ""));
			map.put("to", strs[i++].replace("]", ""));
			list.add(map);
		}
		return list;
    }

}
