package com.clinic.management.services.impls;

import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.clinic.management.services.RazorPayService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

@Service
public class RazorPayServiceImpl implements RazorPayService{

	@Value("${Razorpay.key}")
	private String razorpayKey;
	
	@Value("${Razorpay.secret}")
	private String razorpaySecret;
	
	@Override
	public Order createOrder(double amount) throws RazorpayException {
		
	 RazorpayClient client =new RazorpayClient(razorpayKey,razorpaySecret);
	 JSONObject jsonObject = new JSONObject();
	 jsonObject.put("amount", amount*100);
	 jsonObject.put("currency", "INR");
	 jsonObject.put("receipt", "enroll_"+System.currentTimeMillis()); //
	 
		return client.orders.create(jsonObject);
	}

	@Override
	public boolean verifyPayment(String paymentId, String orderId, String signature) throws RazorpayException {
		JSONObject jsonObject = new JSONObject(); 
		jsonObject.put("razorpay_order_id", orderId);
		jsonObject.put("razorpay_payment_id", paymentId);
		jsonObject.put("razorpay_signature", signature);
		
//		return Utils.verifyPaymentSignature(jsonObject, razorpaySecret);
		// this down code is optional
		try {
			Utils.verifyPaymentSignature(jsonObject, razorpaySecret);
			System.out.println("Payment Success");
			return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Payment Failed");
			return false;
		}
	
	}

}
