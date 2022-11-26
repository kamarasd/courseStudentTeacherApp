package hu.webuni.kamarasd.financial.system.web;

import hu.webuni.kamarasd.financial.system.dto.FinanceDto;
import lombok.AllArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class FinanceController {

    private final JmsTemplate jmsTemplate;

    @PostMapping("/api/finances")
    public void registerPayment(@RequestBody FinanceDto financeDto) {
        this.jmsTemplate.convertAndSend("finances", financeDto);
    }


}
