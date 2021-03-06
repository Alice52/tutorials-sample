package cn.edu.ntu.springcloud.sentinel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author zack <br>
 * @create 2020-04-15 22:11 <br>
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class SentinelApplication {

    public static void main(String[] args) {

        SpringApplication.run(SentinelApplication.class, args);
    }
}
