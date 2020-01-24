package control.progetto;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import manager.progetto.ProgettoModelDM;
import model.Progetto;
import model.Utente;


@WebServlet("/Modifica")
@MultipartConfig(maxFileSize=16177216)
public class Modifica extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public Modifica() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProgettoModelDM model = new ProgettoModelDM();
		PrintWriter out = response.getWriter();
		
		
		Utente utente = (Utente) request.getSession().getAttribute("utente");
		if(utente == null)
		{	
			response.sendRedirect("./HomePage.jsp");
			return;
		}
		
		String username = utente.getUsername();
		
		String titolo = request.getParameter("titolo");		
		String descrizione = request.getParameter("descrizione");
		String consigli = request.getParameter("consigli");
		String categoria = request.getParameter("categoria");
		Part file_modello = request.getPart("progetto");
		Part immagine = request.getPart("immagine");
		
		Progetto p = new Progetto();
		p.setId_progetto(Integer.parseInt(request.getParameter("id")));
		p.setTitolo(titolo);
		p.setDescrizione(descrizione);
		p.setConsigli(consigli);
		p.setCategoria(categoria);
		p.setVersione(1);
		p.setUsername(username);
		
		
		try 
		{
			model.modificaProgetto(p, file_modello.getInputStream(), immagine.getInputStream());
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			out.println("<script>");
			out.println("window.open('http://localhost:8080/Simplify3D/ProgettoView.jsp?id="+p.getId_progetto()+"','_self')");
			out.println("alert('Modifica effettuata con successo')");
			out.println("</script>");
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}