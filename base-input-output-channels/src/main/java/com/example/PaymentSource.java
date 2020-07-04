package com.example;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author yangzq80@gmail.com
 * @date 2020-07-04
 */
public interface PaymentSource {
    // Defines methods for sending messages.
    @Output("paymentChannel")
    MessageChannel paymentChannel();
}
