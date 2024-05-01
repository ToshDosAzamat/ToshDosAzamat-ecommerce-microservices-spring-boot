package com.example.paymentservice.service.impelement;


import com.example.paymentservice.dto.ProductDto;
import com.example.paymentservice.model.MyPay;
import com.example.paymentservice.repository.MyPayRepository;
import com.example.paymentservice.service.MyPayService;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class MyPayServiceImpelement implements MyPayService {

    private APIContext apiContext;
    private MyPayRepository myPayRepository;

    public MyPayServiceImpelement(APIContext apiContext, MyPayRepository myPayRepository) {
        this.apiContext = apiContext;
        this.myPayRepository = myPayRepository;
    }

    @Override
    public Payment createPayment(Double total, String currency,
                                 String method, String intent,
                                 String description, String cancelUrl,
                                 String successUrl) throws PayPalRESTException {

        Amount amount = new Amount();
        amount.setCurrency(currency);
        total = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
        amount.setTotal(String.format("%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method.toString());

        Payment payment = new Payment();
        payment.setIntent(intent.toString());
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);
        MyPay myPay = MyPay.builder()
                .price(total)
                .currency(currency)
                .method(method)
                .intent(intent)
                .description(description)
                .build();
        myPayRepository.save(myPay);
        return payment.create(apiContext);
    }

    @Override
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecute);
    }
}
