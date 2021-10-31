package com.veera.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.event.S3EventNotification;
import com.veera.data.Employee;
import com.veera.util.Utils;

import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;


@Service
public class MessageReceiver {
	
	private static final Logger log = LoggerFactory.getLogger(MessageReceiver.class);
	
	//@SqsListener(value = "${SQS_QUEUE_ID}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS )
	public void receiveStringMessage(final String message, 
	  @Header("SenderId") String senderId) {
		log.info("message received {} {}",senderId,message);
	}

	@SqsListener(value = "${SQS_QUEUE_ID}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS )
	public void receiveObjectMessage(final String message, 
	  @Header("SenderId") String senderId) {
		try {
			Object desObj = Utils.deserializeFromBase64(message);
			Employee emp = (Employee)desObj;
			System.out.println("........emp details......."+emp.getId());
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("object message received {} {}",senderId,message);
	}
	
	@SqsListener(value = "testS3Queue", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
	public void receiveS3Event(S3EventNotification s3EventNotificationRecord) {
		S3EventNotification.S3Entity s3Entity = s3EventNotificationRecord.getRecords().get(0).getS3();
		String objectKey = s3Entity.getObject().getKey();
		log.info("s3 event::objectKey:: {}",objectKey);
	}
}