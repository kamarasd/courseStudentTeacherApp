package hu.webuni.cst.kamarasd.jms;

import hu.webuni.cst.kamarasd.service.StudentService;
import hu.webuni.financial.system.dto.FinanceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FinanceConsumer {

    private final StudentService studentService;

    public void onPaymentMessage(FinanceDto financeDto) {
        studentService.updateBalance(financeDto.getNeptunId(), financeDto.getAmount());
    }
}
