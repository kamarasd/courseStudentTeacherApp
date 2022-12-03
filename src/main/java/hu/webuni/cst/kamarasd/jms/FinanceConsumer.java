package hu.webuni.cst.kamarasd.jms;

import hu.webuni.cst.kamarasd.service.StudentService;
import hu.webuni.kamarasd.financialclient.dto.FinanceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class FinanceConsumer {

    private final StudentService studentService;

    @JmsListener(destination = "finances", containerFactory = "financeFactory")
    public void onPaymentMessage(FinanceDto financeDto) {
        log.warn("A financial request caught");
        studentService.updateBalance(financeDto.getNeptunId(), financeDto.getAmount());
    }
}
