package cn.itcast.common.util.email;

import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {

	/**
	 * 
	 * @param toEmail
	 *            收件人邮箱
	 * @param fromEmail
	 *            发件人邮箱
	 * @param content
	 *            邮件内容
	 * @param subject
	 *            邮件主题
	 * @param emailPassword
	 *            发件人邮箱密码
	 * @throws Exception
	 */
	public static boolean sendMessage(String toEmail, String fromEmail,
			String content, String subject, String emailPassword)
			throws Exception {
		boolean boo = false;
		try {

			// 获取接收域名
			String toEmailDomain = toEmail.substring(toEmail.indexOf("@") + 1);
			// 获取发送域名
			String fromEmailDomain = fromEmail
					.substring(fromEmail.indexOf("@") + 1);

			// 建立邮件回话
			Properties pros = new Properties();
			// 存储接收邮件服务器的smtp地址
			pros.put("mail.smtp.host", "smtp." + toEmailDomain);
			// 存储发送邮件服务器的地址
			pros.put("mail.smtp.localhost", fromEmailDomain);
			// 通过验证
			pros.put("mail.smtp.auth", "true");
			// 根据属性建立一个邮件回话
			Session s = Session.getInstance(pros);
			if (s != null) {
				s.setDebug(true);
				// 由邮件回话建立一个消息对象
				MimeMessage message = new MimeMessage(s);

				// 设置邮件
				InternetAddress newfromEmail = new InternetAddress(fromEmail);
				// 设置发信人
				message.setFrom(newfromEmail);
				InternetAddress newtoEmail = new InternetAddress(toEmail);
				// 设置收信人，并设置其接受类型为TO
				message.setRecipient(Message.RecipientType.TO, newtoEmail);
				// 设置主题
				message.setSubject(subject);
				// 设置内容
				message.setText(content);
				// 设置日期
				message.setSentDate(new Date());
				message.saveChanges();
				Transport transport = s.getTransport("smtp");
				transport.connect("smtp." + fromEmailDomain,
						fromEmail.substring(0, fromEmail.indexOf("@")),
						String.valueOf(emailPassword));
				transport.sendMessage(message, message.getAllRecipients());
				transport.close();
				boo = true;
			}
		} catch (Exception es) {
			boo = false;
			throw es;
		}
		return boo;
	}
}
