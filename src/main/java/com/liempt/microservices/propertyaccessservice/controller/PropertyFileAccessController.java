package com.liempt.microservices.propertyaccessservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.liempt.microservices.propertyaccessservice.beans.PropertyAccessBean;
import com.liempt.microservices.propertyaccessservice.beans.PropertyAccessValue;

@RestController
@RequestMapping("/access")
public class PropertyFileAccessController {

	@Autowired
	private PropertyAccessBean bean;

	@GetMapping("accessPropertyFile")
	public PropertyAccessValue accessPropertyFile() {
		refreshActuator();
		return new PropertyAccessValue(bean.getName(), bean.getDescription());
	}

	public void refreshActuator() {
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:8100/actuator/refresh";

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		HttpEntity entity = new HttpEntity(headers);

		ResponseEntity<String> results = restTemplate.postForEntity(baseUrl, entity, String.class);

	}
}
