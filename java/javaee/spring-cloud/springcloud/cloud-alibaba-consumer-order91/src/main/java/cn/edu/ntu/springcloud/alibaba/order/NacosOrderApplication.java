package cn.edu.ntu.springcloud.alibaba.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zack <br>
 * @create 2020-04-12 14:04 <br>
 */
@SpringBootApplication
@EnableDiscoveryClient
public class NacosOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosOrderApplication.class, args);
    }
}
