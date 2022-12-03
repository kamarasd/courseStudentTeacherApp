package hu.webuni.cst.kamarasd.jms;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JmsConfig {

    @Bean
    public MessageConverter jacksonJmsMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(objectMapper);
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    @Bean
    public ConnectionFactory financeConnectionFactory() {
        ActiveMQConnectionFactory cF = new ActiveMQConnectionFactory("tcp://localhost:9999");
        return cF;
    }

    @Bean
    public ConnectionFactory neptunConnectionFactory() {
        ActiveMQConnectionFactory cF = new ActiveMQConnectionFactory("tcp://localhost:9998");
        return cF;
    }

    @Bean
    public JmsTemplate neptunTemplate(ObjectMapper objectMapper) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(neptunConnectionFactory());
        jmsTemplate.setMessageConverter(jacksonJmsMessageConverter(objectMapper));
        return jmsTemplate;
    }

    @Bean
   public JmsListenerContainerFactory<?> financeFactory(ConnectionFactory financeConnectionFactory,
                                                  DefaultJmsListenerContainerFactoryConfigurer config) {
        return setPubSubDurableSubscription(financeConnectionFactory, config, "cst-app");
    }

    @Bean
    public JmsListenerContainerFactory<?> neptunFactory(ConnectionFactory neptunConnectionFactory,
                                                     DefaultJmsListenerContainerFactoryConfigurer config) {
        return setPubSubDurableSubscription(neptunConnectionFactory, config, "cst-app");
    }

    private JmsListenerContainerFactory<?> setPubSubDurableSubscription(ConnectionFactory cF,
                                                                        DefaultJmsListenerContainerFactoryConfigurer config,
                                                                        String clientId) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setClientId(clientId);
        config.configure(factory, cF);
        factory.setPubSubDomain(true);
        factory.setSubscriptionDurable(true);

        /*((SingleConnectionFactory)cF).setClientId(clientId);

        config.configure(factory, cF);
        factory.setSubscriptionDurable(true);*/
        return factory;
    }
}
