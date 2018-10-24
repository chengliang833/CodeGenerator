package com.codegen.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.codegen.service.CodeGenerator;
import com.codegen.service.CodeGeneratorManager;
import com.codegen.util.DataUtil;
import com.codegen.util.FileUtil;
import com.codegen.util.MethodUtil;
import com.codegen.util.StringUtils;

import freemarker.template.Configuration;

/**
 * Controller层 代码生成器 Created by zhh on 2017/09/20.
 */
public class ControllerGenerator extends CodeGeneratorManager implements CodeGenerator {

    @Override
    public void genCode(String tableName) {
        String modelName = StringUtils.tableNameConvertUpperCamel(tableName);
        Configuration cfg = getFreemarkerConfiguration();
        String customMapping = "/";
        String modelNameUpperCamel = modelName;

        Map<String, Object> data = DataUtil.getDataMapInit(tableName, modelName, modelNameUpperCamel);
        try {
            List<String> controllerMethodsList = getControllerMethods(getMapperJavaFilePath(tableName, modelName,
                                                                                            customMapping,
                                                                                            modelNameUpperCamel),
                                                                      modelNameUpperCamel);
            data.put("controllerMethodsList", controllerMethodsList);

            File controllerFile = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_CONTROLLER + customMapping
                                           + modelNameUpperCamel + "Controller.java");
            if (!controllerFile.getParentFile().exists()) {
                controllerFile.getParentFile().mkdirs();
            }
            cfg.getTemplate("controller.ftl").process(data, new FileWriter(controllerFile));
            logger.info(modelNameUpperCamel + "Controller.java 生成成功!");
        } catch (Exception e) {
            throw new RuntimeException("Controller 生成失败!", e);
        }
    }

    private String getMapperJavaFilePath(String tableName, String modelName, String customMapping,
                                         String modelNameUpperCamel) {
        String path = PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_MAPPER + customMapping + modelNameUpperCamel
                      + "Mapper.java";
        return path;
    }

    /**
     * 从给定的Mapper及其中的方法，生成Controller需要的方法
     * 
     * @param mapperJavaFilePath
     * @return
     * @throws IOException
     */
    public static List<String> getControllerMethods(String mapperJavaFilePath, String modelName) throws IOException {
        Map<String, String> methodActionTypeMap = new HashMap<String, String>();
        Map<String, String> methodContentMap = new HashMap<String, String>();
        String methodPattern = "([^import|^package]).*;";
        List<String> contentList = FileUtil.readFile2List(mapperJavaFilePath);
        for (String content : contentList) {
            boolean match = Pattern.matches(methodPattern, content);
            if (match) {
                // 去掉前后的空格
                content = content.trim();
                String methodReturnType = MethodUtil.getMethodReturnType(content);
                String methodName = MethodUtil.getMethodName(content);
                String methodParamType = MethodUtil.getMethodParamType(content);
                String methodParanName = MethodUtil.getMethodParamName(content);
                String methodActionType = MethodUtil.getMethodActionType(content);
                String serviceName = StringUtils.toLowerCaseFirstOne(modelName) + "Service";
                // 对相同功能的方法进行去除，如insert和insertSelective方法功能上相同，但考虑到insertSelective
                // 具有更多的灵活性，因而在生成Controller的时候，就考虑保留了它
                String oldMethodName = methodActionTypeMap.get(methodActionType);
                if (oldMethodName != null) {
                    if (methodName.indexOf("Selective") > 0) {
                        String methodNameWithoutSelective = methodName.replace("Selective", "");
                        if (oldMethodName.equals(methodNameWithoutSelective)) {
                            methodActionTypeMap.remove(methodActionType);
                            methodContentMap.remove(oldMethodName);
                        }
                    } else {
                        String methodNameWithSelective = methodName + "Selective";
                        if (oldMethodName.equals(methodNameWithSelective)) {
                            continue;
                        }
                        methodActionType = methodName;
                    }
                    /*
                     * if (methodActionTypeMap.get(methodActionType).length() > methodName.length()) { continue; } else
                     * if (methodActionTypeMap.get(methodActionType).length() < methodName.length()) {
                     * methodActionTypeMap.remove(methodActionType); }
                     */
                }
                methodActionTypeMap.put(methodActionType, methodName);
                StringBuilder methodContent = new StringBuilder();
                if ("selectByPage".equals(methodName)) {
                    methodContent.append(StringUtils.FOUR_SPACES).append("@RequestMapping(\"" + methodActionType
                                                                         + "\")\n");
                    methodContent.append(StringUtils.FOUR_SPACES).append("@ResponseBody\n");
                    methodContent.append(StringUtils.FOUR_SPACES).append("public Result<Page<").append(MethodUtil.typeConvert(methodReturnType)).append(">> ").append(methodName).append("(").append(MethodUtil.getMethodParamType(methodName,
                                                                                                                                                                                                                                   methodParamType,
                                                                                                                                                                                                                                   modelName)).append(" ").append(methodParanName).append(")").append("{\n");
                    methodContent.append(StringUtils.FOUR_SPACES).append(StringUtils.FOUR_SPACES).append(MethodUtil.typeConvert(methodReturnType)).append(" list = null;\n");
                    methodContent.append(StringUtils.FOUR_SPACES).append(StringUtils.FOUR_SPACES).append(methodParanName).append(" = ").append(methodParanName).append(".sequentialDisplayCalculate(").append(serviceName).append(".count(").append(methodParanName).append(".getObj()));\n");
                    methodContent.append(StringUtils.FOUR_SPACES).append(StringUtils.FOUR_SPACES).append("if (").append(methodParanName).append(".getTotalCount() > 0) {\n");
                    methodContent.append(StringUtils.FOUR_SPACES).append(StringUtils.FOUR_SPACES).append(StringUtils.FOUR_SPACES).append("list = ").append(serviceName).append(".selectByPage(").append(methodParanName).append(");\n");
                    methodContent.append(StringUtils.FOUR_SPACES).append(StringUtils.FOUR_SPACES).append("}\n");
                    methodContent.append(StringUtils.FOUR_SPACES).append(StringUtils.FOUR_SPACES).append("Page<List<").append(modelName).append(">> result = new Page<List<").append(modelName).append(">>(").append(methodParanName).append(", list);\n");
                    methodContent.append(StringUtils.FOUR_SPACES).append(StringUtils.FOUR_SPACES).append("return Result.success(result);\n");
                    methodContent.append(StringUtils.FOUR_SPACES).append("}\n");
                } else {
                    methodContent.append(StringUtils.FOUR_SPACES).append("@RequestMapping(\"" + methodActionType
                                                                         + "\")\n");
                    methodContent.append(StringUtils.FOUR_SPACES).append("@ResponseBody\n");
                    methodContent.append(StringUtils.FOUR_SPACES).append("public Result<").append(MethodUtil.typeConvert(methodReturnType)).append("> ").append(methodName).append("(").append(MethodUtil.getMethodParamType(methodName,
                                                                                                                                                                                                                             methodParamType,
                                                                                                                                                                                                                             modelName)).append(" ").append(methodParanName).append(")").append("{\n");
                    methodContent.append(StringUtils.FOUR_SPACES).append(StringUtils.FOUR_SPACES).append("return Result.success(").append(serviceName).append("."
                                                                                                                                                              + methodName).append("(").append(methodParanName).append("))").append(";\n");
                    methodContent.append(StringUtils.FOUR_SPACES).append("}\n");
                }
                methodContentMap.put(methodActionType, methodContent.toString());
            }
        }
        List<String> serviceMethodsContentList = new ArrayList<String>();
        serviceMethodsContentList.addAll(methodContentMap.values());
        return serviceMethodsContentList;
    }
}
