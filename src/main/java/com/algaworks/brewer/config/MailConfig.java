package com.algaworks.brewer.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.algaworks.brewer.mail.Mailer;

/**
 * 
 * @author Marques
 * Para funcionamento do arquivo externo, o mesmo é necessário ser colocado dentro da pasta do usuário em que for rodar o sistema
 * ex: C:\Users\Marques\brewer-mail.properties
 */
@Configuration
@ComponentScan(basePackageClasses = Mailer.class)
@PropertySource({ "classpath:env/mail-${ambiente:local}.properties" })
@PropertySource(value = { "file:${USERPROFILE}/brewer-mail.properties" }, ignoreResourceNotFound = true)
public class MailConfig {
	
	@Autowired
	private Environment env;

	@Bean
	public JavaMailSender mailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.sendgrid.net");
		mailSender.setPort(587);
		mailSender.setUsername(env.getProperty("email.username"));
		mailSender.setPassword(env.getProperty("email.password"));
		
		System.out.println(env.getProperty("email.username"));
		System.out.println(env.getProperty("email.password"));
		
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.debug", false);
		props.put("mail.smtp.connectiontimeout", 10000); // miliseconds

		mailSender.setJavaMailProperties(props);
		
		return mailSender;
	}

}
