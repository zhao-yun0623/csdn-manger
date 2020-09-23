package com.tju.csdnmanger;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.tju.csdnmanger.mapper")
public class CsdnMangerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsdnMangerApplication.class, args);
	}

}
