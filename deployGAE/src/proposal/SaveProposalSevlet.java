package proposal;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
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
import javax.persistence.EntityManager;
import javax.servlet.http.*;


import model.ProposalDb;
import model.ProposalInteres;
import model.ProposalInteresPK;

@SuppressWarnings("serial")
public class SaveProposalSevlet extends HttpServlet {
		private static final Map<String, Integer> interesesMap;
	    static
	    {
	        interesesMap = new HashMap<String, Integer>();
	        interesesMap.put("IOS",1);
	        interesesMap.put("Android",2);
	        interesesMap.put("BlackBerry",3);
	        interesesMap.put("WindowsPhone",4);
	        interesesMap.put("MobileWeb",5);
	        interesesMap.put("CrossPlataforms",6);
	        interesesMap.put("ASP.NET",7);
	        interesesMap.put("Java",8);
	        interesesMap.put("GoogleApps",9);
	        interesesMap.put("GoogleCloudSQL",10);
	    }	

	    public void doPost(HttpServletRequest req, HttpServletResponse resp)
	                throws IOException {
	    	resp.sendRedirect(req.getRequestURL().reverse().replace(0, 12, "").reverse().toString());
	    	
	    	//Crear Objeto Proposal 
	    	Proposal p=new Proposal();
			p.setFullname(req.getParameter("fullname"));
			p.setEmail(req.getParameter("email"));
			p.setPhone(req.getParameter("phone"));
			p.setCompany(req.getParameter("company"));
			
			if(req.getParameterValues("mobileDevelopment")!=null)
				p.setMobileDevelopment(req.getParameterValues("mobileDevelopment"));
			else
				p.setMobileDevelopment(new String[0]);
			
			if(req.getParameter("webDevelopment")!=null)
				p.setWebDevelopment(req.getParameterValues("webDevelopment"));
			else
				p.setWebDevelopment(new String[0]);
			
			if(req.getParameterValues("cloudSolution")!=null)
				p.setCloudSolution(req.getParameterValues("cloudSolution"));
			else
				p.setCloudSolution(new String[0]);
			
			p.setRangoMin(req.getParameter("min"));
			p.setRangoMax(req.getParameter("max"));
			p.setObservaciones(req.getParameter("observacion"));
			p.setIpHost(req.getRemoteHost());
			
			//Mostrar Informacion en pantalla
			/*resp.getWriter().println("FullName:"+p.getFullname());			
			resp.getWriter().println("Email:"+p.getEmail());			
			resp.getWriter().println("Phone:"+p.getPhone());			
			resp.getWriter().println("Company:"+p.getCompany());			
			resp.getWriter().println();			
			resp.getWriter().println("Interese:");
			resp.getWriter().println("MobileDevelopment:" + p.getMobileDevelopmentToString());
			resp.getWriter().println("WebDevelopment:" + p.getWebDevelopmentToString());
			resp.getWriter().println("CloudSolution:" + p.getCloudSolutionToString());
			resp.getWriter().println("Observaciones:"+p.getObservaciones());
			resp.getWriter().println("Min:"+p.getRangoMin());
			resp.getWriter().println("Max:"+p.getRangoMax());
			resp.getWriter().println();
			resp.getWriter().println("IP de origen:"+p.getIpHost());
			*/
			
			if (insertDb(p,resp))
			{
				this.enviarCorreo(p);
			}
	    }
	    public boolean insertDb(Proposal p,HttpServletResponse resp)
	    {
	    	boolean estado=false;
	    	List<Integer> intereses=new ArrayList<Integer>();
	    	
	    	//Crear Entidad de la base de datos
	    	ProposalDb proposalDb=new ProposalDb();
			proposalDb.setEmail(p.getEmail());
			proposalDb.setMax(Float.parseFloat(p.getRangoMax()));
			proposalDb.setMin(Float.parseFloat(p.getRangoMin()));
			proposalDb.setNombre(p.getFullname());
			proposalDb.setOrganizacion(p.getCompany());
			proposalDb.setTelefono(p.getPhone());
			
			//Crear objeto de intereses
			if(p.getMobileDevelopment().length!=0)
    		{
    			for(String a:p.getMobileDevelopment())
    			{
    				intereses.add(this.interesesMap.get(a));
    			}
    		}
    		if(p.getWebDevelopment().length!=0)
    		{
    			for(String a:p.getWebDevelopment())
    			{
    				intereses.add(this.interesesMap.get(a));
    			}
    		}
    		if(p.getCloudSolution().length!=0)
    		{
    			for(String a:p.getCloudSolution())
    			{
    				intereses.add(this.interesesMap.get(a));
    			}
    		}
    		
	    	
	    	//Inserta en Cloud SQl           
	        EntityManager mgr = EMF.get().createEntityManager();
	        try {
	            mgr.getTransaction().begin();
	            mgr.persist(proposalDb);
	            mgr.getTransaction().commit();      
	            estado=true;
	        }  finally {
	            mgr.close();
	        }
	       
	        int id=proposalDb.getId();
            //Insertar intereses
	        mgr=EMF.get().createEntityManager();
            try
            {
            	mgr.getTransaction().begin();
	            for(Integer i:intereses)
	            {
	            	ProposalInteresPK interes=new ProposalInteresPK();
	            	interes.setIdInteres(i);
	            	interes.setIdProposal(proposalDb.getId());
	            	ProposalInteres proposal=new ProposalInteres();
	            	proposal.setId(interes);
	            	mgr.persist(proposal);
	            }
            	mgr.getTransaction().commit();
            }finally
            {
            	mgr.close();
            }
	       
	    	return estado;
	    }
	    private String arrayToHtmlList(String[] array,String titulo)
	    {
	    	String mensaje="<ul>"+titulo;
	    	for(String a:array)
	    	{
	    		mensaje+="<li margin-left=\"30px\" style=\"\">"+a+"</li>";
	    	}
	    	mensaje+="</ul>";
	    	return mensaje;
	    }
	    public boolean enviarCorreo(Proposal p) throws IOException
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
	    		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(p.getEmail()));
	    		msg.setSubject("Proposal Verfication");	    		
	            
	    		//Create multipart messege
	    		MimeMultipart multipart=new MimeMultipart();
	    		//Create Bodypart
	    		BodyPart bodyPart=new MimeBodyPart();
	    		
	    		//Lista de Interese seleccionados pen lista html
	    		String intereses="<ul>";
	    		if(p.getMobileDevelopment().length!=0)
	    		{
	    			intereses+=this.arrayToHtmlList(p.getMobileDevelopment(), "Mobile Development");
	    		}
	    		if(p.getWebDevelopment().length!=0)
	    		{
	    			intereses+=this.arrayToHtmlList(p.getWebDevelopment(), "Mobile Development");
	    		}
	    		if(p.getCloudSolution().length!=0)
	    		{
	    			intereses+=this.arrayToHtmlList(p.getCloudSolution(), "Mobile Development");
	    		}
	    		intereses+="</ul>";
	    		//Crear el tml con el link de las imagenes
	    		String html="" +
	    				"<html><body>"+
	    				"<center>"+
	    				"<div style=\"width:600px; text-align:left\">"+
	    				"<table border=\"0\" width=\"600px\" cellpadding=\"0\" cellspacing=\"0\">"+
	    					"<tbody>"+
							"<tr>"+
								"<a href=\"http://www.solitsa.com\">" +
								"<img src=\"http://pruebassolit.appspot.com/img/header-reply.jpg\" width=\"100%\" aling=\"center\">" +
								"</a>"+
							"</tr>"+
							"<tr>"+
								"<br>"+
								"<br>"+
								"Hi Ms "+p.getFullname()+","+
								"<br>"+
								"<br>"+
								"You has just sent a proposal to Solit S.A with the following information:"+
								"<br>"+
								"<br>"+
								//Aqui va el ul con la informacion
								"<strong>Proposal:</strong>"+
								"<br>"+
								"<br>"+
								//Informacion ul
								"Full Name: "+p.getFullname()+"<br>"+
								"Email: "+p.getEmail()+"<br>"+
								"Phone: "+p.getPhone()+"<br>"+
								"Company: "+p.getCompany()+"<br>"+
								"<br>"+
								"<strong>Interest:</strong>"+
								intereses+
								"<br>"+
								"Project Objectives: "+p.getObservaciones()+"<br>"+
								"Budget: <strong>$"+p.getRangoMin()+"</strong> to <strong>$"+p.getRangoMax()+"</strong>"+
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
}
