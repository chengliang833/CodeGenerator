package wang.ulane.gen.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import freemarker.template.Configuration;
import wang.ulane.gen.main.FuncSelWithDeatil;
import wang.ulane.gen.service.CodeGenerator;
import wang.ulane.gen.service.CodeGeneratorConfig;
import wang.ulane.gen.service.CodeGeneratorManager;
import wang.ulane.gen.util.DataUtil;
import wang.ulane.gen.util.FileUtil;
import wang.ulane.gen.util.MethodUtil;
import wang.ulane.gen.util.StringUtils;

public class ServiceGenerator extends CodeGeneratorManager implements CodeGenerator {
	
	private String sufName = "Mapper";
	
    public ServiceGenerator() {
		super();
	}
	public ServiceGenerator(String sufName) {
		super();
		if(sufName != null){
			this.sufName = sufName;
		}
	}
	@Override
    public void genCode(String tableName, String modelName) {
    	if(modelName == null){
    		modelName = StringUtils.tableNameConvertUpperCamel(tableName);
    	}
        Configuration cfg = getFreemarkerConfiguration();
        String customMapping = "/";
        String modelNameUpperCamel = modelName;

        Map<String, Object> data = DataUtil.getDataMapInit(tableName, modelName, modelNameUpperCamel);
        try {
        	List<String> serviceMethodsList = getServiceMethods(getMapperJavaFilePath(tableName, modelName,
										        			customMapping,
										        			modelNameUpperCamel),
										        			modelNameUpperCamel);
        	data.put("serviceMethodsList", serviceMethodsList);
        	data.put("sufName", this.sufName);
            if(CodeGeneratorConfig.CUSTOM_FUNC_SELWITHDEATIL.containModel(modelName)){
            	Iterator<String> it = CodeGeneratorConfig.CUSTOM_FUNC_SELWITHDEATIL.getSubModel(modelName).iterator();
            	StringBuilder detailImportService = new StringBuilder();
            	StringBuilder detailAutowired = new StringBuilder();
            	while(it.hasNext()){
            		String subModelName = it.next();
            		detailImportService.append("import ").append(data.get("servicePackage")).append(".").append(subModelName).append("Service;\n");
            		detailAutowired.append("\n    @Autowired\n    private ").append(subModelName).append("Service ").append(FuncSelWithDeatil.getFirstLower(subModelName)).append("Service;\n");
            	}
            	data.put("detailImportService", detailImportService.toString());
            	data.put("detailAutowired", detailAutowired.toString());
        	}else{
        		data.put("detailImportService", "");
        		data.put("detailAutowired", "");
        	}
        	
        	// 创建 Service 接口
        	File serviceFile = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_SERVICE + customMapping
        			+ modelNameUpperCamel + "Service.java");
        	// 查看父级目录是否存在, 不存在则创建
        	if (!serviceFile.getParentFile().exists()) {
        		serviceFile.getParentFile().mkdirs();
        	}
        	cfg.getTemplate("service.ftl").process(data, new FileWriter(serviceFile));
            logger.info(modelNameUpperCamel + "Service.java 生成成功!");

        	List<String> serviceImplMethodsList = getServiceImplMethods(getMapperJavaFilePath(tableName, modelName,
															customMapping,
															modelNameUpperCamel),
															modelNameUpperCamel);
            data.put("serviceImplMethodsList", serviceImplMethodsList);
            data.put("impl", "impl");

            // 创建 ServiceImpl 接口
            File serviceImplFile = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_SERVICE_IMPL + customMapping
                                        + modelNameUpperCamel + "ServiceImpl.java");
            // 查看父级目录是否存在, 不存在则创建
            if (!serviceImplFile.getParentFile().exists()) {
                serviceImplFile.getParentFile().mkdirs();
            }
            cfg.getTemplate("serviceImpl.ftl").process(data, new FileWriter(serviceImplFile));
            logger.info(modelNameUpperCamel + "ServiceImpl.java 生成成功!");

        } catch (Exception e) {
            throw new RuntimeException("Service 生成失败!", e);
        }
    }

    private String getMapperJavaFilePath(String tableName, String modelName, String customMapping,
                                         String modelNameUpperCamel) {
        String path = PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_MAPPER + customMapping + modelNameUpperCamel
                      + this.sufName + ".java";
        return path;
    }

    /**
     * 从给定的Mapper及其中的方法，生成Service需要的方法
     * 
     * @param mapperJavaFilePath
     * @return
     * @throws IOException
     */
    public List<String> getServiceMethods(String mapperJavaFilePath, String modelName) throws IOException {
        List<String> serviceMethodsContentList = new ArrayList<String>();
        String methodPattern = "([^import|^package]).*;";
        List<String> contentList = FileUtil.readFile2List(mapperJavaFilePath);
        for (String content : contentList) {
            boolean match = Pattern.matches(methodPattern, content);
            if (match) {
                // 去掉前后的空格
                content = content.trim();
                // 去掉后面的";"
                content = content.substring(0, content.length() - 1);
                String methodType = MethodUtil.getMethodReturnType(content);
                String methodName = MethodUtil.getMethodName(content);
                List<String> methodParamTypeList = MethodUtil.getMethodParamTypeList(content);
                List<String> methodParanNameList = MethodUtil.getMethodParamNameList(content);
                String mapperName = StringUtils.toLowerCaseFirstOne(modelName) + this.sufName;
                StringBuilder methodContent = new StringBuilder();                
                methodContent.append(StringUtils.FOUR_SPACES).append("public ").append(methodType).append(" ");

                StringBuilder methodContent2 = null;
                if(checkSelMethodName(methodName) && CodeGeneratorConfig.CUSTOM_FUNC_SELWITHDEATIL.containModel(modelName)){
                	methodContent2 = new StringBuilder(methodContent);
                	methodContent2.append("selWithDeatil").append("(");
                }
                
                methodContent.append(methodName).append("(");
                
                int index = 0;
                for(String methodParamType : methodParamTypeList) {
                    if(checkSelMethodName(methodName) && CodeGeneratorConfig.CUSTOM_FUNC_SELWITHDEATIL.containModel(modelName)){
                        methodContent2.append(MethodUtil.getMethodParamType(methodName,methodParamType,modelName));
                        methodContent2.append(" ").append(methodParanNameList.get(index));
                        if(index<methodParamTypeList.size()-1) {
                            methodContent2.append(", ");
                        }
                    }
                	
                    methodContent.append(MethodUtil.getMethodParamType(methodName,methodParamType,modelName));
                    methodContent.append(" ").append(methodParanNameList.get(index));
                    if(index<methodParamTypeList.size()-1) {
                        methodContent.append(", ");
                    }
                    index++;                     
                }                    
                methodContent.append(")").append(";");
                serviceMethodsContentList.add(methodContent.toString());
                
                if(checkSelMethodName(methodName) && CodeGeneratorConfig.CUSTOM_FUNC_SELWITHDEATIL.containModel(modelName)){
                	methodContent2.append(")").append(";");
                	serviceMethodsContentList.add(methodContent2.toString());
                }
            }
        }
        return serviceMethodsContentList;
    }
    public List<String> getServiceImplMethods(String mapperJavaFilePath, String modelName) throws IOException {
    	List<String> serviceMethodsContentList = new ArrayList<String>();
    	String methodPattern = "([^import|^package]).*;";
    	List<String> contentList = FileUtil.readFile2List(mapperJavaFilePath);
    	for (String content : contentList) {
    		boolean match = Pattern.matches(methodPattern, content);
    		if (match) {
    			// 去掉前后的空格
    			content = content.trim();
    			// 去掉后面的";"
    			content = content.substring(0, content.length() - 1);
    			String methodType = MethodUtil.getMethodReturnType(content);
    			String methodName = MethodUtil.getMethodName(content);
    			List<String> methodParamTypeList = MethodUtil.getMethodParamTypeList(content);
    			List<String> methodParanNameList = MethodUtil.getMethodParamNameList(content);
    			String mapperName = StringUtils.toLowerCaseFirstOne(modelName) + this.sufName;
    			StringBuilder methodContent = new StringBuilder();                
    			methodContent.append(StringUtils.FOUR_SPACES).append("public ").append(methodType).append(" ");

                StringBuilder methodContent2 = null;
                if(checkSelMethodName(methodName) && CodeGeneratorConfig.CUSTOM_FUNC_SELWITHDEATIL.containModel(modelName)){
                	methodContent2 = new StringBuilder(methodContent);
                	methodContent2.append("selWithDeatil").append("(");
                }
    			
    			methodContent.append(methodName).append("(");
    			int index = 0;
    			for(String methodParamType : methodParamTypeList) {
                    if(checkSelMethodName(methodName) && CodeGeneratorConfig.CUSTOM_FUNC_SELWITHDEATIL.containModel(modelName)){
        				methodContent2.append(MethodUtil.getMethodParamType(methodName,methodParamType,modelName));
        				methodContent2.append(" ").append(methodParanNameList.get(index));
        				if(index<methodParamTypeList.size()-1) {
        					methodContent2.append(", ");
        				}
                    }
    				methodContent.append(MethodUtil.getMethodParamType(methodName,methodParamType,modelName));
    				methodContent.append(" ").append(methodParanNameList.get(index));
    				if(index<methodParamTypeList.size()-1) {
    					methodContent.append(", ");
    				}
    				index++;                     
    			}   
    			
    			methodContent.append(")").append("{\n");
    			methodContent.append(StringUtils.FOUR_SPACES).append(StringUtils.FOUR_SPACES);
    			methodContent.append("return ").append(mapperName).append("."
    					+ methodName).append("(").append(MethodUtil.listToString(methodParanNameList)).append(")").append(";\n");
    			methodContent.append(StringUtils.FOUR_SPACES).append("}\n");
    			serviceMethodsContentList.add(methodContent.toString());

                if(checkSelMethodName(methodName) && CodeGeneratorConfig.CUSTOM_FUNC_SELWITHDEATIL.containModel(modelName)){
        			methodContent2.append(")").append("{\n");
        			methodContent2.append(StringUtils.FOUR_SPACES).append(StringUtils.FOUR_SPACES);
        			methodContent2.append(methodType).append(" record = ").append(mapperName).append("."
        					+ methodName).append("(").append(MethodUtil.listToString(methodParanNameList)).append(")").append(";\n");
        			
        			for(String subModelName:CodeGeneratorConfig.CUSTOM_FUNC_SELWITHDEATIL.getSubModel(modelName)){
        				methodContent2.append(StringUtils.FOUR_SPACES).append(StringUtils.FOUR_SPACES)
        				.append("record.set").append(subModelName).append("(").append(FuncSelWithDeatil.getFirstLower(subModelName))
        				.append("Service.").append(getSelMethodName(methodName)).append("(").append(MethodUtil.listToString(methodParanNameList)).append("));\n");
        			}
        			
        			methodContent2.append(StringUtils.FOUR_SPACES).append(StringUtils.FOUR_SPACES).append("return record;\n");
        			methodContent2.append(StringUtils.FOUR_SPACES).append("}\n");
        			serviceMethodsContentList.add(methodContent2.toString());
                }
    		}
    	}
    	return serviceMethodsContentList;
    }
    
    private boolean checkSelMethodName(String methodName){
    	return methodName.equals(getSelMethodName(methodName));
    }
    private String getSelMethodName(String methodName){
		return CodeGeneratorConfig.SIMPL_FUNC_NAME ? "getByKey" : "selectByPrimaryKey";
    }
}
