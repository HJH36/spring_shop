package kr.co.mtshop.common;

import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class CommonMail {
	
	public CommonMail(){
		
	}
	
	/**
	 * Google 메일로 메일 보내기
	 * @param param
	 * @throws Exception
	 */
	public void SendMail(Map<String, String> param) throws Exception {
		
		LocalValue Lv = new LocalValue();
		System.out.println(param);
		
//		String fromAddress = Lv.G_FROM_ADDRESS;
		String fromAddress = param.get("from_email");
		String toAddress = param.get("to_email");
		String mail_subject = param.get("subject");
		String mail_contents = param.get("email_contents");

		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.host", Lv.G_MAIL_SMTP_SERVER);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", Lv.G_MAIL_PORT);
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.quitwait", "false");

		Authenticator auth = new Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {
				LocalValue lv = new LocalValue();
				return new PasswordAuthentication(lv.G_MAIL_ID, lv.G_MAIL_PWD);
			}

		};

		Session session = Session.getDefaultInstance(props, auth);
		
		MimeMessage message = new MimeMessage(session);
		message.setSender(new InternetAddress(fromAddress));
		message.setSubject(mail_subject);
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
		
		Multipart mp = new MimeMultipart();
		MimeBodyPart mbp1 = new MimeBodyPart();
		mbp1.setText(mail_contents);
		mp.addBodyPart(mbp1);
		
		MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
		mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
		mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
		mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
		mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
		mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
		CommandMap.setDefaultCommandMap(mc);
		
		message.setContent(mp);
		Transport.send(message);

	}

	
	
	/**
	 * 카페24 메일로 메일 보내기
	 * @param param
	 * @throws Exception
	 */
	public void SendSmtpMail(Map<String, String> param) throws Exception {
		
		LocalValue lv = new LocalValue();
		
		String fromAddress = lv.FROM_ADDRESS;
		String toAddress = param.get("toAddress");
		String mail_subject = param.get("mail_subject");
		String mail_contents = param.get("mail_contents");
		
		String toEmail=null;
		StringTokenizer st = new StringTokenizer(toAddress, ";");

		// smtp 정보
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.put("mail.smtp.port", lv.SMTP_PORT);

		Session session = Session.getDefaultInstance(props, null);
		
		while(st.hasMoreElements()){
			
			toEmail = (String)st.nextToken();
			
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromAddress));
//			message.setSender(new InternetAddress(fromAddress));
			message.setSubject(mail_subject);
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			
			Multipart mp = new MimeMultipart();
			MimeBodyPart mbp1 = new MimeBodyPart();
//			mbp1.setText(mail_contents);
			mbp1.setContent(mail_contents, "text/html;charset=EUC-KR");
			mp.addBodyPart(mbp1);
			
			MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
			mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
			mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
			mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
			mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
			mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
			CommandMap.setDefaultCommandMap(mc);
			
			message.setContent(mp);
			
			Transport transport = session.getTransport("smtp");
			transport.connect(lv.SMTP_SERVER, lv.MAIL_ID, lv.MAIL_PWD);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();

		}

		
	}
	
}
