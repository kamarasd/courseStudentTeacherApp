package hu.webuni.kamarasd.financialclient.web;

import hu.webuni.kamarasd.financialclient.dto.FinanceDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class FinanceController {

    private final JmsTemplate jmsTemplate;

    @PostMapping("/api/finances")
    public void registerPayment(@RequestBody FinanceDto financeDto) {
        log.warn("A financial request incoming");
        this.jmsTemplate.convertAndSend("finances", financeDto);
    }


}
