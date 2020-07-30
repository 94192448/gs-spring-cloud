package com.example.demo;

import com.ctrip.framework.apollo.openapi.client.ApolloOpenApiClient;
import com.ctrip.framework.apollo.openapi.dto.NamespaceReleaseDTO;
import com.ctrip.framework.apollo.openapi.dto.OpenItemDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DemoApplicationTests {

	@Autowired
	ApolloOpenApiClient apolloOpenApiClient;

	@Test
	public void contextLoads() {

	}

	@Test
	public void testCreate(){

		OpenItemDTO dto = new OpenItemDTO();
		dto.setKey("max");
		dto.setValue("ac2");
		dto.setDataChangeLastModifiedBy("apollo");
		dto.setDataChangeCreatedBy("apollo");

		apolloOpenApiClient.createOrUpdateItem("test-a","DEV","default","application",dto);
	}

	@Test
	public void testUpdate(){
		OpenItemDTO dto = new OpenItemDTO();
		dto.setKey("timeout");
		dto.setValue("1112");
		dto.setDataChangeLastModifiedBy("apollo");

		//apolloOpenApiClient.updateItem("SampleApp","DEV","","",dto);
		apolloOpenApiClient.updateItem("test-a","DEV","default","application",dto);
	}

	@Test
	public void testPublish(){
		NamespaceReleaseDTO dto = new NamespaceReleaseDTO();

		dto.setReleaseTitle(new Date().toString());
		dto.setReleaseComment("auto publish");
		dto.setReleasedBy("apollo");

		apolloOpenApiClient.publishNamespace("test-a","DEV","default","application",dto);

	}


	//加载yml中所有属性到apollo中
	@Test
	public void   loadSnakeyaml() throws FileNotFoundException {

		Map<String,String> result = new HashMap<>();

		InputStream inputStream = new FileInputStream("/Users/zqy/codes/yusys/tmp/gs-spring-cloud/service-b/src/test/resources/application.yml");

		Yaml yaml = new Yaml();

		Map<String, Object> param = (Map<String, Object>) yaml.load(inputStream);

		for(Map.Entry<String,Object> entry:param.entrySet()){
			String key = entry.getKey();
			Object val = entry.getValue();
			if(val instanceof Map){
				forEachYaml(key,(Map<String, Object>) val,result);
			}else{
				result.put(key, val.toString());
			}
		}

		//TODO 自定义循环调用openapi
		result.keySet().forEach(k->{
			log.info(k+"-----"+result.get(k));

			OpenItemDTO dto = new OpenItemDTO();
			dto.setKey(k);
			dto.setValue(result.get(k));
			dto.setDataChangeLastModifiedBy("apollo");
			dto.setDataChangeCreatedBy("apollo");

			apolloOpenApiClient.createOrUpdateItem("test-a","DEV","default","application",dto);
		});

	}


	public  void forEachYaml(String key_str,Map<String, Object> obj,Map<String,String> result ){
		for(Map.Entry<String,Object> entry:obj.entrySet()){
			String key = entry.getKey();
			Object val = entry.getValue();
			String str_new = "";
			if(!StringUtils.isEmpty(key_str)){
				str_new = key_str+ "."+key;
			}else{
				str_new = key;
			}
			if(val instanceof Map){
				forEachYaml(str_new,(Map<String, Object>) val,result);
			}else{
				result.put(str_new,val.toString());
			}
		}
	}

}
