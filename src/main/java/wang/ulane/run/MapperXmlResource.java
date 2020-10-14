package wang.ulane.run;

import java.io.ByteArrayInputStream;

import org.springframework.core.io.InputStreamResource;

/**
 * mybatis文件resource包装类。
 * 主要解决多个dao报Invalid bound statement (not found)BUG。<br>
 * 经排查是由XMLMapperBuilder.parse()中，configuration.isResourceLoaded(resource)返回true导致<br>
 * 因为InputStreamResource.toString()，始终返回同一个值，需要重写toString()，保证唯一性。
 *
 */
public class MapperXmlResource extends InputStreamResource {

	private String mapperNamespace;

	public MapperXmlResource(String xml, Class<?> daoClass) {
		super(new ByteArrayInputStream(xml.getBytes()));
		this.mapperNamespace = daoClass.getName();
	}

	@Override
	public String toString() {
		return "MapperXmlResource [mapperNamespace=" + mapperNamespace + "]";
	}

}
