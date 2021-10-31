package com.veera.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.veera.data.Employee;
import com.veera.util.Utils;

import io.awspring.cloud.messaging.core.QueueMessageChannel;

@Service
public class MessageSender {
	private static final Logger logger = LoggerFactory.getLogger(MessageSender.class);


	@Autowired
	private final AmazonSQSAsync amazonSqs;
	
	@Value("${SQS_QUEUE_ID}")
	private String queueName;
	
	@Value("${SQS_BASE_URL}")
	private String queueBaseUrl;

	@Autowired
	public MessageSender(final AmazonSQSAsync amazonSQSAsync) {
		this.amazonSqs = amazonSQSAsync;
	}

	public boolean send(final String messagePayload) {
		MessageChannel messageChannel = new QueueMessageChannel(amazonSqs, queueBaseUrl+queueName);

		Message<String> msg = MessageBuilder.withPayload(messagePayload)
				.setHeader("sender", "app1")
				.setHeaderIfAbsent("country", "AE")
				.build();
       
		long waitTimeoutMillis = 5000;
		boolean sentStatus = messageChannel.send(msg,waitTimeoutMillis);
		logger.info("message sent");
		return sentStatus;
	}

	public boolean sendObject(Employee emp) {
		String encodedEmpObj=null;
		boolean sentStatus = false;
		try {
			encodedEmpObj = Utils.serializeToBase64(emp);
			MessageChannel messageChannel = new QueueMessageChannel(amazonSqs, queueBaseUrl+queueName);

			Message<String> msg = MessageBuilder.withPayload(encodedEmpObj)
					.setHeader("sender", "app1")
					.setHeaderIfAbsent("country", "AE")
					.build();
	       
			long waitTimeoutMillis = 5000;
			sentStatus = messageChannel.send(msg,waitTimeoutMillis);
			logger.info("message sent");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sentStatus;
	}

}