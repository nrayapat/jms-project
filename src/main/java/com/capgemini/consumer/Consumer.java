package com.capgemini.consumer;


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Consumer {
	
	public static void main(String[] args) {
		
		/*if(args.length == 0) {
			System.out.println("Must supply a message");
			System.out.println("From gradle run with: pom -Pargs=\"Hello World\" run");
			return ;
		}else {
			System.out.println(args[0]);
		}*/
		
		ConnectionFactory connectionFactory = null;
		Connection connection = null;
		
		try {
			InitialContext intialContext = new InitialContext();
			String name;
			Queue queue = (Queue) intialContext.lookup("jmsproduce");
			connectionFactory =  
					(ConnectionFactory) intialContext.lookup("jms/__defaultConnectionFactory");
			
			connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			connection.start();
			MessageConsumer messageConsumer = session.createConsumer(queue);
			TextMessage textmessage = (TextMessage) messageConsumer.receive();
			
			String body = textmessage.getText();
			System.out.println(body);
			
			
			
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}finally {
			if (connection != null) try {
				connection.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

}
