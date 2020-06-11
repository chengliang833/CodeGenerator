package ${serviceimplPackage};

import ${modelPackage}.${modelNameUpperCamel};
import ${mapperPackage}.${modelNameUpperCamel}${sufName};
import ${servicePackage}.${modelNameUpperCamel}Service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.apache.ibatis.annotations.Param;

@Service("${modelNameLowerCamel}Service")
public class ${modelNameUpperCamel}ServiceImpl implements ${modelNameUpperCamel}Service{

	@Autowired
    private ${modelNameUpperCamel}${sufName} ${modelNameLowerCamel}${sufName};
    
	<#list serviceImplMethodsList as serviceMethod>
${serviceMethod!''}
	</#list>
}
