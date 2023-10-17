package com.hcmute.ecommerce.universeshop.base.config;

import com.hcmute.ecommerce.universeshop.UniverseShopApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(UniverseShopApplication.class);
	}

}
