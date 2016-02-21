package de.moaiosbeer.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.moaiosbeer.helper.MoaHttpClient;
import de.moaiosbeer.helper.SessionOwner;

/**
 * Servlet implementation class RegistrationController
 */
@WebServlet("/RegistrationController")
public class RegistrationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/registrierung.jsp");
		//Username und Password
		String username = (String)request.getParameter("username");
		String password = (String)request.getParameter("passsword");
		MoaHttpClient client = new MoaHttpClient();
		// zum testen ob der nutzer schon im System ist
		SessionOwner owner = new SessionOwner();
		boolean isUser = owner.isValidUser(username,password);
		if( isUser == false){
			// dem Owner sein Auth String geben 
			owner.setAuth(client.getEncodeedAuthString(username, password));
			// Test ob der nutzer reqistriert werden konnte
			if(owner.registerMeAtServer()){
				// Antwort schreiben
				response.setStatus(201);
				rd.forward(request, response);
				return;
			}	
		}
		response.setStatus(400);
		rd.forward(request, response);
		return ;
		
	}

}
