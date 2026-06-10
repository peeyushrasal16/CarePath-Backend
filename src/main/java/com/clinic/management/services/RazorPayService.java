package com.clinic.management.services;

import com.razorpay.Order;
import com.razorpay.RazorpayException;

public interface RazorPayService {
Order createOrder(double amount) throws RazorpayException; //create order

boolean verifyPayment(String paymentId,String orderId,String signature) throws RazorpayException; //to check razorpay payment is done 
}
