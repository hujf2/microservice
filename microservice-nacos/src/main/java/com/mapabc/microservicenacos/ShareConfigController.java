package com.mapabc.microservicenacos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RefreshScope
@RequestMapping(value = "/conf")
public class ShareConfigController {
    /**
     * foo.yaml配置内容
     */
    @Value(value = "${foo:}")
    private String foo;

    @Value(value = "${bar:}")
    private String bar;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 输出配置信息
     */
    @RequestMapping(value = "/get")
    @ResponseBody
    public String getConfig() {
        return foo +" " + bar;
    }

    @RequestMapping(value = "/hello")
    @ResponseBody
    public String hello() {
        try {
            return restTemplate.getForObject("http://microservice-nacos/conf/get", String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}