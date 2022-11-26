package hu.webuni.kamarasd.config;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hu.webuni.kamarasd.xml.SystemXmlWs;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebserviceConfig {
	
	private final Bus bus;
	private final SystemXmlWs systemXmlWs;
	
	@Bean
	public Endpoint endpoint() { 
		EndpointImpl endpoint = new EndpointImpl(bus, systemXmlWs);
		endpoint.publish("/semesterSetter");
		
		return endpoint;	
	}
	
	
	

}
