package com.keen.ma.utils;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * 自动发送邮件
 * @author KEEN
 * @lastChangeTime 2014年8月19日 上午10:43:28
 */
public class AutoSendEmail {

	private Properties properties;
	private Session session;
	private Message message;
	private MimeMultipart multipart;
	private static String errorMessage = "";
	
	public AutoSendEmail() {
		super();
		this.properties = new Properties();
	}
	private void setProperties(String host,String post){
		this.properties.put("mail.smtp.host",host);
		this.properties.put("mail.smtp.post",post);
		this.properties.put("mail.smtp.auth",true);
		this.session=Session.getInstance(properties);
		this.message = new MimeMessage(session);
		this.multipart = new MimeMultipart("mixed");
	}
	/**
	 * 设置收件人
	 */
	private void setReceiver(String[] receiver) {
		try {
			Address[] address = new InternetAddress[receiver.length];
			for(int i=0;i<receiver.length;i++){
				address[i] = new InternetAddress(receiver[i]);
			}
			this.message.setRecipients(Message.RecipientType.TO, address);
		} catch (MessagingException e) {
			if(errorMessage.equals("")){
				errorMessage = "邮件发送失败：（请检查收件人地址或网络超时）"+e.getMessage();
			}
		}
	}
	/**
	 * 设置邮件
	 */
	private void setMessage(String from,String title,String content) throws AddressException, MessagingException{
		this.message.setFrom(new InternetAddress(from));
		this.message.setSubject(title);
		MimeBodyPart textBody = new MimeBodyPart();
		textBody.setContent(content,"text/html;charset=gbk");
		this.multipart.addBodyPart(textBody);
	}
	/**
	 * 发送邮件
	 */
	private void sendEmail(String host,String account,String pwd){
		//发送设置
		try {
			this.message.setSentDate(new Date());
			this.message.setContent(this.multipart);
			this.message.saveChanges();
			Transport transport=session.getTransport("smtp");  
			transport.connect(host,account,pwd);  
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (MessagingException e) {
			errorMessage = "发送邮件失败(请检查收件人邮箱地址或网络超时):"+e.getMessage();
			e.printStackTrace();
		}
		
		
	}
	
	public void SendEmail(final String code,final String emailAdds){
        new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					setProperties("smtp.163.com", "25");
					//发件人（只能是163邮箱，因为用的是163服务器和端口）
					String msg = "【世界触手可及】用户账户密码找回验证码："+code+"，请您及时完成验证，如非本人操作，请忽略本短信。";
					setMessage("autoemailsender@163.com", "EmailSender", msg);
					setReceiver(new String[]{emailAdds});//收件人
					sendEmail("smtp.163.com", "autoemailsender@163.com", "sender123");//发件人帐号密码
				} catch (AddressException e) {
					if(errorMessage.equals("")){
						errorMessage = "邮箱发送失败（请检查收件人邮箱地址或网络超时）："+e.getMessage();
					}
					e.printStackTrace();
				} catch (MessagingException e) {
					if(errorMessage.equals("")){
						errorMessage = "邮箱发送失败（请检查收件人邮箱地址或网络超时）："+e.getMessage();
					}
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	public static String getErrorMessage(){
		return errorMessage;
	}

}
