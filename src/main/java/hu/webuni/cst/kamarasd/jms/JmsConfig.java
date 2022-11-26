package hu.webuni.cst.kamarasd.jms;

import javax.jms.ConnectionFactory;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
//@EnableAutoConfiguration(exclude={JmsAutoConfiguration.class, ActiveMQAutoConfiguration.class})
public class JmsConfig {

    @Bean
    public MessageConverter jacksonJmsMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(objectMapper);
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

   public JmsListenerContainerFactory<?> theFactory(ConnectionFactory connectionFactory,
                                                  DefaultJmsListenerContainerFactoryConfigurer config) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();

        ((SingleConnectionFactory)connectionFactory).setClientId("cst-app");
        
        config.configure(factory, connectionFactory);
        factory.setSubscriptionDurable(true);
        return factory;
    }
}
