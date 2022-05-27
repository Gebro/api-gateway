package com.stc.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {
	
	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p
						.path("/get")
						.filters(f -> f
								.addRequestHeader("MyHeader", "MyURI")
								.addRequestParameter("Param", "MyValue"))
						.uri("http://httpbin.org:80"))
				.route(p -> p.path("/leaves/**")
						.uri("lb://leaves"))
				.route(p -> p.path("/employee/**")
						.uri("lb://employee"))
				.route(p -> p.path("/employee-feign/**")
						.uri("lb://employee"))
				.route(p -> p.path("/employee-new/**")
						.filters(f -> f.rewritePath(
								"/employee-new/(?<segment>.*)",
								"/employee-feign/${segment}"))
						.uri("lb://employee"))
				.build();
	}

}
