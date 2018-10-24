package ${servicePackage};

import ${modelPackage}.${modelNameUpperCamel};
import ${mapperPackage}.${modelNameUpperCamel}Mapper;
import ${pageClassPath};

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * Created by ${author} on ${date}.
 */
@Service
public class ${modelNameUpperCamel}Service {

	@Autowired
    private ${modelNameUpperCamel}Mapper ${modelNameLowerCamel}Mapper;
    
	<#list serviceMethodsList as serviceMethod>
${serviceMethod!''}
	</#list>
}
