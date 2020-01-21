package manager.utente;

import java.sql.SQLException;

import model.Utente;

public interface UtenteModel<T>
{
	public boolean doRegistrazione(T utente) throws SQLException;
	
	public Utente doLogin(String username, String password) throws SQLException;
	
	public Utente doPasswordDimenticata(T utente, String nuovapassword) throws SQLException;
	
	public Utente doModificaPassword(T utente, String nuovapassword) throws SQLException;
	
}
