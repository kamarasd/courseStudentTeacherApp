package hu.webuni.cst.kamarasd.xml;

import lombok.RequiredArgsConstructor;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
@RequiredArgsConstructor
public class WebServiceConfig {
    private final Bus bus;
    private final TimetableWs timetableWs;

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpointImpl = new EndpointImpl(bus, timetableWs);
        endpointImpl.publish("/timetable");
        return endpointImpl;
    }
}
