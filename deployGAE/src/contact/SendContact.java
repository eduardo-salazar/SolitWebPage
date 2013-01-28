package contact;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import proposal.Proposal;

public class SendContact extends HttpServlet{
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
		//Redireccionar a la pagina anterior

    	resp.sendRedirect(req.getRequestURL().reverse().replace(0, 11, "").reverse().toString());//11 es la logitud del map del servler -sendContact-
		
		//Creando objeto contacto
		Contact contacto=new Contact();
		contacto.setName(req.getParameter("username"));
		contacto.setEmail(req.getParameter("usermail"));
		contacto.setMessage(req.getParameter("message"));
		contacto.setSubject(req.getParameter("subject"));
		contacto.setWebsite(req.getParameter("usersite"));
		
		enviarCorreo(contacto);
		
	}
	
	   public boolean enviarCorreo(Contact c) throws IOException
	    {
	    	boolean estado=false;
	    	
	    	//Enviar Mensaje
	    	Properties porps=new Properties();
	    	Session session=Session.getDefaultInstance(porps,null);
	    	
	    	try
	    	{
	    		Message msg=new MimeMessage(session);
	    		msg.setFrom(new InternetAddress("eduardo.salazar@solitsa.com","Solit S.A"));
	    		msg.addRecipient(Message.RecipientType.BCC, new InternetAddress("info@solitsa.com"));
	    		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(c.getEmail()));
	    		msg.setSubject("Contact Information-"+c.getSubject());	    		
	            
	    		//Create multipart messege
	    		MimeMultipart multipart=new MimeMultipart();
	    		//Create Bodypart
	    		BodyPart bodyPart=new MimeBodyPart();
	    		
	    		//Crear el tml con el link de las imagenes
	    		String html="" +
	    				"<html><body>"+
	    				"<center>"+
	    				"<div style=\"width:600px; text-align:left\">"+
	    				"<table border=\"0\" width=\"600px\" cellpadding=\"0\" cellspacing=\"0\">"+
	    					"<tbody>"+
							"<tr>"+
								"<a href=\"http://www.solitsa.com\">" +
								"<img src=\"http://pruebassolit.appspot.com/img/header-contact.jpg\" width=\"100%\" aling=\"center\">" +
								"</a>"+
							"</tr>"+
							"<tr>"+
								"<br>"+
								"<br>"+
								"Hi Ms "+c.getName()+","+
								"<br>"+
								"<br>"+
								"Our Team is going to contact you as soon as possible to this address"+
								"<br>"+
								"<br>"+
								//Aqui va el ul con la informacion
								"<strong>Contact Information:</strong>"+
								"<br>"+
								"<br>"+
								//Informacion ul
								"Name: "+c.getName()+"<br>"+
								"Email: "+c.getEmail()+"<br>"+
								"Website: "+c.getWebsite()+"<br>"+
								"Subject: "+c.getSubject()+"<br>"+
								"<br>"+
								"<strong>Message:</strong>"+
								"<br>"+c.getMessage()+
								"<br>"+
								"<br>"+
							"</tr>"+
							"<tr>"+
								"<img src=\"http://pruebassolit.appspot.com/img/footer-reply.jpg\" width=\"100%\" aling=\"center\" height=\"80px\">"+
							"</tr>"+
							"</tbody>"+
						"</table>" +
						"</div>"+
						"</body></html>"+
						"</center>";
	    		
	    		//asignar tipo de contenido y el contenido
	    		bodyPart.setContent(html,"text/html");
	    		
	    		multipart.addBodyPart(bodyPart);
	    		//add it
	    		msg.setContent(multipart);
	    		Transport.send(msg);
	    	}catch(AddressException e)	{
	    		
	    	} catch (MessagingException e) {
	            // ...
	        } catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	return estado;
	    }
	
	private class Contact
	{
		private String name;
		private String email;
		private String website;
		private String subject;
		private String message;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getWebsite() {
			return website;
		}
		public void setWebsite(String website) {
			this.website = website;
		}
		public String getSubject() {
			return subject;
		}
		public void setSubject(String subject) {
			this.subject = subject;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		
		
	}
}

