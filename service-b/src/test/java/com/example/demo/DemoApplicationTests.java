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

import java.util.Date;

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
}
