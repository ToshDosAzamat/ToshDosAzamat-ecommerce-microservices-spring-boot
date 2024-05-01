package com.example.paymentservice.controller;

import com.example.paymentservice.dto.ProductDto;
import com.example.paymentservice.model.MyPay;
import com.example.paymentservice.service.MyPayService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MyPayController {

    private MyPayService myPayService;
    private RestTemplate restTemplate;

    @Value("${order.url}")
    private String orderUrl;

    @Value("${product.url}")
    private String productUrl;
    public static final String SUCCESS_URL = "pay/success";
    public static final String CANCEL_URL = "pay/cancel";

    public MyPayController(MyPayService myPayService, RestTemplate restTemplate) {
        this.myPayService = myPayService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{orderId}")
    public String home(@PathVariable long orderId, Model model) {
        ResponseEntity<ProductDto[]> responseEntity = restTemplate.getForEntity(
                orderUrl+orderId, ProductDto[].class);
        ProductDto[] productList = responseEntity.getBody();
        if(responseEntity.getStatusCode() != HttpStatus.OK){
            throw new RuntimeException("Product not found!");
        }
        int totalQuety = 0;
        double totalPrice=0;
        List<String> urlOfProduct = new ArrayList<>();
        for(ProductDto productDto : productList){
            totalPrice += productDto.getPrice();
            totalQuety += productDto.getQuanty();
            var url = productUrl+productDto.getProductId();
            System.out.println(url+"        "+productDto.getProductId());
            urlOfProduct.add(url);
        }
        model.addAttribute("producturls", urlOfProduct);
        model.addAttribute("totalprice", totalPrice);
        model.addAttribute("totalquenty", totalQuety);
        return "home";
    }

    @PostMapping("/pay")
    public String payment(@ModelAttribute("order") MyPay myPay) throws PayPalRESTException {

        Payment payment = myPayService.createPayment(myPay.getPrice(), myPay.getCurrency(), myPay.getMethod(),
                myPay.getIntent(), myPay.getDescription(), "http://localhost:8082/" + CANCEL_URL,
                "http://localhost:8082/" + SUCCESS_URL);
        for(Links link:payment.getLinks()) {
            if(link.getRel().equals("approval_url")) {
                return "redirect:"+link.getHref();
            }
        }

        return "redirect:/";
    }

    @GetMapping(value = CANCEL_URL)
    public String cancelPay() {
        return "cancel";
    }

    @GetMapping(value = SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) throws PayPalRESTException {
        Payment payment = myPayService.executePayment(paymentId, payerId);
        System.out.println(payment.toJSON());
        if (payment.getState().equals("approved")) {
            return "success";
        }
        return "redirect:/";
    }

}
