package contact;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SendContact extends HttpServlet{
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
		resp.getWriter().println("Enviando correo de contacto");
	}
}
