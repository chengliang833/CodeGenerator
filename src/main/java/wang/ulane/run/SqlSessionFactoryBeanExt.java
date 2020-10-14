package wang.ulane.run;

import static org.springframework.util.Assert.notNull;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.Resource;


/**
 * SqlSessionFactoryBean扩展
 */
public class SqlSessionFactoryBeanExt extends SqlSessionFactoryBean {
	
	private MapperLocationsBuilder mapperLocationsBuilder = new MapperLocationsBuilder();
	
	private String basePackage;

	/**
	 * key 包名
	 * value 包名对应下的排除Mapper,即entity
	 */
	private Map<String, List<String>> excludeMapper = null;
	
	@Override
	public void setMapperLocations(Resource[] mapperLocations) {
		mapperLocationsBuilder.storeMapperFile(mapperLocations);
	}
	
	@Override
	protected SqlSessionFactory buildSqlSessionFactory() throws IOException {
		notNull(this.basePackage, "属性 'basePackage' 必填");
		
		Resource[] allMapperLocations = mapperLocationsBuilder.build(this.basePackage, this.excludeMapper);
		// 重新设置mapperLocation属性
		super.setMapperLocations(allMapperLocations);
		
		return super.buildSqlSessionFactory();
	}
	
	
	/**
	 * @param basePackage
	 *            指定哪些包需要被扫描,支持多个包"package.a,package.b"并对每个包都会递归搜索
	 */
	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public Map<String, List<String>> getExcludeMapper() {
		return excludeMapper;
	}
	public void setExcludeMapper(Map<String, List<String>> excludeMapper) {
		this.excludeMapper = excludeMapper;
	}
}
