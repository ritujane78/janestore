package com.jane.janestore.service;


import com.jane.janestore.dto.PaymentIntentRequestDto;
import com.jane.janestore.dto.PaymentIntentResponseDto;

public interface IPaymentService {

    PaymentIntentResponseDto createPaymentIntent(PaymentIntentRequestDto requestDto);
}
