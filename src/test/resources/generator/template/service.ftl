package ${servicePackage};

import ${modelPackage}.${modelNameUpperCamel};

import java.util.List;

public interface ${modelNameUpperCamel}Service {
    
	<#list serviceMethodsList as serviceMethod>
${serviceMethod!''}
	</#list>
}
