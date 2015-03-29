package br.com.retaguardaWeb.agendador;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MailInformation extends HttpServlet {

	private static final long serialVersionUID = 1557994365346229670L;

	public void doGet(HttpServletRequest req, HttpServletResponse res, byte[] streamArquivo)
			throws ServletException, IOException {

		PrintWriter out = res.getWriter();
		res.setContentType("text/html");
		try {

			String to = req.getParameter("to");
			to = "sosthenesweb@gmail.com";
			String from = "sosthenesweb@integracondominios.com.br";

			 Properties props = new Properties();  
			  props.put("mail.smtp.host", "localhost");  
			  props.put("mail.smtp.auth","true");  
			  
			  
			Session session = Session.getInstance(props, null);

			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(from));
			Address toAddress = new InternetAddress(to);
			message.addRecipient(Message.RecipientType.TO, toAddress);

			message.setSubject("teste de envio de e-mails");

			message.setContent("este eh um teste de envio", "text/plain");

			
			BodyPart parteEMailArquivo  = null;  
			String nomeArquivo  = "relatorioContasParaHoje.pdf"  ;

		  
			parteEMailArquivo   = new MimeBodyPart();  
			message.setDataHandler(new DataHandler(new ByteArrayDataSource(streamArquivo, "application/pdf"))); 
			parteEMailArquivo.setFileName(nomeArquivo); 
			    
			
			
			
			Transport.send(message);

			out.println("E-mail enviado");
		} catch (MessagingException e) {
			out.println("Email nao pode ser enviado! " + e.getMessage());
		}
	}

	
	
}
