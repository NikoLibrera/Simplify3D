package manager.valcom;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import manager.progetto.ProgettoModelDM;
import model.Commento;
import model.DriverManagerConnectionPool;
import model.Notifica;
import model.Progetto;
import model.RispostaCommento;
import model.Utente;
import model.Valutazione;

public class ValcomModelDM 
{	
	public Integer getNumeroValutazioniByIdProgetto(int idProgetto) throws SQLException 
	{
	    Connection connection = null;
	    Integer n=null;
	    PreparedStatement preparedStatement = null;

	    final String select_sql = "SELECT count(*)as n FROM valutazione WHERE id_progetto= ?";

	    try 
	    {
	      connection = DriverManagerConnectionPool.getConnection();
	      preparedStatement = connection.prepareStatement(select_sql);

	      preparedStatement.setInt(1, idProgetto);

	      System.out.println("getNumeroValutazioniByIdProgetto: "+preparedStatement.toString());

	      ResultSet rs = preparedStatement.executeQuery();

	      if (rs.next())
	      {
	    	  n=rs.getInt("n");
	      }
	    } 
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	    finally 
	    {
	      try 
	      {
	        if (preparedStatement != null) 
	        {
	          preparedStatement.close();
	        }
	      } 
	      finally 
	      {
	        DriverManagerConnectionPool.releaseConnection(connection);
	      }
	    }
	    return n;
	  }
	
	public Integer getNumeroCommentiByIdProgetto(int idProgetto) throws SQLException 
	{
	    Connection connection = null;
	    Integer n=null;
	    PreparedStatement preparedStatement = null;

	    final String select_sql = "SELECT count(*)as n FROM commento WHERE id_progetto= ?";

	    try 
	    {
	      connection = DriverManagerConnectionPool.getConnection();
	      preparedStatement = connection.prepareStatement(select_sql);

	      preparedStatement.setInt(1, idProgetto);

	      System.out.println("getNumeroCommentiByIdProgetto: "+preparedStatement.toString());

	      ResultSet rs = preparedStatement.executeQuery();

	      if (rs.next())
	      {
	    	  n=rs.getInt("n");
	      }
	    } 
	    catch(Exception e){
	    	e.printStackTrace();
	    }
	    finally 
	    {
	      try 
	      {
	        if (preparedStatement != null) 
	        {
	          preparedStatement.close();
	        }
	      } 
	      finally 
	      {
	        DriverManagerConnectionPool.releaseConnection(connection);
	      }
	    }
	    return n;
	  }
	
	public Integer getNumeroRisposteByIdCommento(int idCommento) throws SQLException 
	{
	    Connection connection = null;
	    Integer n=null;
	    PreparedStatement preparedStatement = null;

	    final String select_sql = "SELECT count(*)as n FROM risposta_commento WHERE id_commento= ?";

	    try 
	    {
	      connection = DriverManagerConnectionPool.getConnection();
	      preparedStatement = connection.prepareStatement(select_sql);

	      preparedStatement.setInt(1, idCommento);

	      System.out.println("getNumeroRisposteByIdCommento: "+preparedStatement.toString());

	      ResultSet rs = preparedStatement.executeQuery();

	      if (rs.next())
	      {
	    	  n=rs.getInt("n");
	      }
	    } 
	    catch(Exception e){
	    	e.printStackTrace();
	    }
	    finally 
	    {
	      try 
	      {
	        if (preparedStatement != null) 
	        {
	          preparedStatement.close();
	        }
	      } 
	      finally 
	      {
	        DriverManagerConnectionPool.releaseConnection(connection);
	      }
	    }
	    return n;
	  }

	public Integer getMediaValutazioniById(int id) throws SQLException 
	{
	    Connection connection = null;
	    Float n=null;
	    Integer media=null;
	    PreparedStatement preparedStatement = null;

	    final String select_sql = "SELECT AVG(voto)as n FROM valutazione WHERE id_progetto= ?";

	    try 
	    {
	      connection = DriverManagerConnectionPool.getConnection();
	      preparedStatement = connection.prepareStatement(select_sql);

	      preparedStatement.setInt(1, id);

	      System.out.println("getMediaValutazioniById: "+preparedStatement.toString());

	      ResultSet rs = preparedStatement.executeQuery();

	      if (rs.next())
	      {
	    	  n=rs.getFloat("n");
	    	  media=Math.round(n);
	      }
	    } 
	    catch(Exception e){
	    	e.printStackTrace();
	    }
	    finally 
	    {
	      try 
	      {
	        if (preparedStatement != null) 
	        {
	          preparedStatement.close();
	        }
	      } 
	      finally 
	      {
	        DriverManagerConnectionPool.releaseConnection(connection);
	      }
	    }
	    return media;
	  }

	public static ArrayList<Commento> getCommentiByIdProgetto(int idProgetto) throws SQLException
	{
		Commento c=null;
		ArrayList<Commento> commenti=new ArrayList<Commento>();
		Connection conn = DriverManagerConnectionPool.getConnection();
		try 
		{
			Statement st=conn.createStatement();
			System.out.println("getCommentiByIdProgetto: " + "select * from commento where id_progetto='"+idProgetto+"'");
			ResultSet result =st.executeQuery("select * from commento where id_progetto='"+idProgetto+"'");
			while(result.next())
			{
				int id_commento=result.getInt("id_commento");
				String contenuto=result.getString("contenuto");
				String username=result.getString("username");
				c=new Commento(id_commento, contenuto, username, idProgetto);
				commenti.add(c);
			}
			DriverManagerConnectionPool.releaseConnection(conn);
			
			return commenti;
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static ArrayList<RispostaCommento> getRisposteByIdCommento(int idCommento) throws SQLException
	{
		RispostaCommento r=null;
		ArrayList<RispostaCommento> risposte=new ArrayList<RispostaCommento>();
		Connection conn = DriverManagerConnectionPool.getConnection();
		try 
		{
			Statement st=conn.createStatement();
			 System.out.println("getRisposteByIdCommento:" + "select * from risposta_commento where id_commento='"+idCommento+"'");
			ResultSet result =st.executeQuery("select * from risposta_commento where id_commento='"+idCommento+"'");
			while(result.next())
			{
				int id_risposta=result.getInt("id_risposta_commento");
				String contenuto=result.getString("contenuto");
				String username=result.getString("username");
				
				r=new RispostaCommento(id_risposta, contenuto, username, idCommento);
				risposte.add(r);
			}
			DriverManagerConnectionPool.releaseConnection(conn);
			return risposte;
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public void inserisciCommento(Commento commento,int idProgetto) throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ValcomModelDM model=new ValcomModelDM();

		String insertSQL = "INSERT INTO commento (id_commento, contenuto, username, id_progetto) VALUES (?, ?, ?, ?)"; 
		try 
		{
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			commento.setId_commento(model.getLastIdCommento()+1);
			preparedStatement.setInt(1, commento.getId_commento());
			preparedStatement.setString(2, commento.getContenuto());
			preparedStatement.setString(3, commento.getUsername());
			preparedStatement.setInt(4, idProgetto);

			System.out.println("inserisciCommento: "+preparedStatement.toString());
			preparedStatement.executeUpdate();
			connection.commit();
			
			model.creaNotificaCommento(commento);
		}
		finally 
		{
			try 
			{
				if (preparedStatement != null)
					preparedStatement.close();
			} 
			finally 
			{
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}
	
	public int getLastIdCommento() 
	{
		int idCommento=0;
		try 
		{
	        Connection connection=DriverManagerConnectionPool.getConnection();
	        
	        Statement statement=connection.createStatement();
	        ResultSet r=statement.executeQuery("SELECT MAX(id_commento) AS id FROM commento");
	        if(r.next())
			{
	        	idCommento=r.getInt("id");
			}
			
			connection.close();				
	
		}
		catch (SQLException e) 
		{
			System.out.println("Errore durante la connessione." + e.getMessage());
		}
		return idCommento;
	}
	
	public void inserisciRisposta(RispostaCommento risposta,int idCommento) throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ValcomModelDM model=new ValcomModelDM();

		String insertSQL = "INSERT INTO risposta_commento (id_risposta_commento, contenuto, username, id_commento) VALUES (?, ?, ?, ?)"; 
		try 
		{
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			risposta.setId_risposta(model.getLastIdRisposta()+1);
			risposta.setId_commento(idCommento);
			preparedStatement.setInt(1, risposta.getId_risposta());
			preparedStatement.setString(2, risposta.getContenuto());
			preparedStatement.setString(3, risposta.getUsername());
			preparedStatement.setInt(4, idCommento);

			System.out.println("inserisciRisposta: "+preparedStatement.toString());
			preparedStatement.executeUpdate();
			connection.commit();
			
			model.creaNotificaRisposta(risposta);
		}
		finally 
		{
			try 
			{
				if (preparedStatement != null)
				preparedStatement.close();
			} 
			finally 
			{
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}
	
	public int getLastIdRisposta() 
	{
		int idRisposta=0;
		try 
		{
	        Connection connection=DriverManagerConnectionPool.getConnection();
	        
	        Statement statement=connection.createStatement();
	        ResultSet r=statement.executeQuery("SELECT MAX(id_risposta_commento) AS id FROM risposta_commento");
	        if(r.next())
			{
	        	idRisposta=r.getInt("id");
			}
			
			connection.close();				
	
		}
		catch (SQLException e) 
		{
			System.out.println("Errore durante la connessione." + e.getMessage());
		}
		return idRisposta;
	}

	public void cancellaRisposta(int idRisposta) throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String deleteSQL = "DELETE FROM risposta_commento WHERE id_risposta_commento = ?";
		try
		{
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
		
			preparedStatement.setInt(1, idRisposta);
		
			preparedStatement.executeUpdate();
			System.out.println("cancellaRisposta: "+ preparedStatement.toString());
			connection.commit();
		}
		finally 
		{
			try 
			{
				if(preparedStatement != null)
					preparedStatement.close();
			} 
			finally 
			{
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}
	
	public void cancellaCommento(int idCommento) throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String deleteSQL = "DELETE FROM commento WHERE id_commento = ?";
		try
		{
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
		
			preparedStatement.setInt(1, idCommento);
		
			preparedStatement.executeUpdate();
			System.out.println("cancellaCommento: "+preparedStatement.toString());
			connection.commit();
		}
		finally 
		{
			try 
			{
				if(preparedStatement != null)
					preparedStatement.close();
			} 
			finally 
			{
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}
	
	public void inserisciValutazione(Valutazione valutazione,int idProgetto) throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ValcomModelDM model=new ValcomModelDM();
		
		String insertSQL = "INSERT INTO valutazione (id_valutazione, voto, id_progetto, username) VALUES (?, ?, ?, ?)"; 
		try 
		{
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			valutazione.setId_valutazione(model.getLastIdValutazione()+1);
			valutazione.setId_progetto(idProgetto);
			preparedStatement.setInt(1, valutazione.getId_valutazione());
			preparedStatement.setInt(2, valutazione.getVoto());
			preparedStatement.setInt(3, idProgetto);
			preparedStatement.setString(4, valutazione.getUsername());
			

			System.out.println("inserisciValutazione: "+preparedStatement.toString());
			preparedStatement.executeUpdate();
			connection.commit();
			
			model.creaNotificaValutazione(valutazione);
		}
		finally 
		{
			try 
			{
				if (preparedStatement != null)
				preparedStatement.close();
			} 
			finally 
			{
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}
	
	public int getLastIdValutazione() 
	{
		int idValutazione=0;
		try 
		{
	        Connection connection=DriverManagerConnectionPool.getConnection();
	        
	        Statement statement=connection.createStatement();
	        ResultSet r=statement.executeQuery("SELECT MAX(id_valutazione) AS id FROM valutazione");
	        if(r.next())
			{
	        	idValutazione=r.getInt("id");
			}
			connection.close();				
		}
		catch (SQLException e) 
		{
			System.out.println("Errore durante la connessione." + e.getMessage());
		}
		return idValutazione;
	}
	
	public boolean isValutato(Progetto progetto, Utente utente) throws SQLException 
	{
	    Connection connection = null;
	    boolean n=false;
	    PreparedStatement preparedStatement = null;

	    final String select_sql = "SELECT * FROM valutazione WHERE username=? AND id_progetto=?";

	    try 
	    {
	      connection = DriverManagerConnectionPool.getConnection();
	      preparedStatement = connection.prepareStatement(select_sql);

		  preparedStatement.setString(1, utente.getUsername());
		  preparedStatement.setInt(2, progetto.getId_progetto());

	      System.out.println("isValutato: "+preparedStatement.toString());

	      ResultSet rs = preparedStatement.executeQuery();

	      if (rs.next())
	      {
	    	  n=true;
	      }
	    } 
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	    finally 
	    {
	      try 
	      {
	        if (preparedStatement != null) 
	        {
	          preparedStatement.close();
	        }
	      } 
	      finally 
	      {
	        DriverManagerConnectionPool.releaseConnection(connection);
	      }
	    }
	    return n;
	}

	public void aggiornaValutazione(Valutazione valutazione,int idProgetto) throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ValcomModelDM model=new ValcomModelDM();
		
		String updateSQL = "UPDATE valutazione set id_valutazione=? , voto = ? where username = ? and id_progetto = ?"; 

		try 
		{
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(updateSQL);
			preparedStatement.setInt(1, model.getLastIdValutazione()+1);
			preparedStatement.setInt(2, valutazione.getVoto());
			preparedStatement.setInt(4, idProgetto);
			preparedStatement.setString(3, valutazione.getUsername());

			System.out.println("aggiornaValutazione: "+preparedStatement.toString());
			preparedStatement.executeUpdate();
			connection.commit();
		}
		finally 
		{
			try 
			{
				if (preparedStatement != null)
				preparedStatement.close();
			} 
			finally 
			{
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}
	
	public void eliminaValutazione(int idProgetto, String username) throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String deleteSQL = "DELETE FROM valutazione WHERE id_progetto = ? AND username = ?";
		try
		{
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
		
			preparedStatement.setInt(1, idProgetto);
			preparedStatement.setString(2, username);
		
			preparedStatement.executeUpdate();
			System.out.println("eliminaValutazione: "+preparedStatement.toString());
			connection.commit();
		}
		finally 
		{
			try 
			{
				if(preparedStatement != null)
					preparedStatement.close();
			} 
			finally 
			{
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}

	public Notifica creaNotificaCommento(Commento commento) throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ValcomModelDM model=new ValcomModelDM();
		ProgettoModelDM progettoModel=new ProgettoModelDM();
		Notifica notifica=new Notifica();
		Progetto progetto=progettoModel.getProgettoById(commento.getId_progetto());
		if(progetto.getUsername().equalsIgnoreCase(commento.getUsername()))
			return null;
		notifica.setId_commento(commento.getId_commento());
		notifica.setId_notifica(model.getLastIdNotifica()+1);
		notifica.setTipo("commento");
		notifica.setId_progetto(progetto.getId_progetto());
		notifica.setUsername(progetto.getUsername());
		notifica.setTitolo(commento.getUsername()+" ha lasciato un commento al progetto: "+progetto.getTitolo());
		notifica.setImmagine(progetto.getImmagine());

		String insertSQL = "INSERT INTO notifica (id_notifica,immagine,titolo,tipo,isClicked,id_commento, id_progetto, username ) VALUES (?,?,?,?,?, ?, ?, ?)"; 
		try 
		{
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setInt(1,notifica.getId_notifica());
			preparedStatement.setBlob(2, notifica.getImmagine());
			preparedStatement.setString(3, notifica.getTitolo());
			preparedStatement.setString(4, notifica.getTipo());
			preparedStatement.setInt(5, notifica.isClicked());
			preparedStatement.setInt(6, notifica.getId_commento());
			preparedStatement.setInt(7, notifica.getId_progetto());
			preparedStatement.setString(8, notifica.getUsername());

			System.out.println("creaNotificaCommento: "+preparedStatement.toString());
			preparedStatement.executeUpdate();
			connection.commit();
		}
		finally 
		{
			try 
			{
				if (preparedStatement != null)
				preparedStatement.close();
			} 
			finally 
			{
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		return notifica;
	}
	
	public int getLastIdNotifica() 
	{
		int idNotifica=0;
		try 
		{
	        Connection connection=DriverManagerConnectionPool.getConnection();
	        
	        Statement statement=connection.createStatement();
	        ResultSet r=statement.executeQuery("SELECT MAX(id_notifica) AS id FROM notifica");
	        if(r.next())
			{
	        	idNotifica=r.getInt("id");
			}
			
			connection.close();				
		}
		catch (SQLException e) 
		{
			System.out.println("Errore durante la connessione." + e.getMessage());
		}
		return idNotifica;
	}
	
	public Notifica creaNotificaRisposta(RispostaCommento risposta) throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ValcomModelDM model=new ValcomModelDM();
		ProgettoModelDM progettoModel=new ProgettoModelDM();
		Notifica notifica=new Notifica();
		Commento commento=model.getCommentoById(risposta.getId_commento());
		Progetto progetto=progettoModel.getProgettoById(commento.getId_progetto());
		if(risposta.getUsername().equalsIgnoreCase(commento.getUsername()))
			return null;
		notifica.setId_commento(commento.getId_commento());
		notifica.setId_notifica(model.getLastIdNotifica()+1);
		notifica.setTipo("risposta");
		notifica.setId_risposta(risposta.getId_risposta());
		notifica.setId_progetto(progetto.getId_progetto());
		notifica.setUsername(commento.getUsername());
		notifica.setTitolo(risposta.getUsername()+" ha lasciato una risposta al tuo commento sul progetto: "+progetto.getTitolo());
		notifica.setImmagine(progetto.getImmagine());

		String insertSQL = "INSERT INTO notifica (id_notifica,immagine,titolo,tipo,isClicked,id_commento, id_risposta, username,id_progetto ) VALUES (?,?,?,?,?,?, ?, ?, ?)"; 
		try 
		{
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setInt(1,notifica.getId_notifica());
			preparedStatement.setBlob(2, notifica.getImmagine());
			preparedStatement.setString(3, notifica.getTitolo());
			preparedStatement.setString(4, notifica.getTipo());
			preparedStatement.setInt(5, notifica.isClicked());
			preparedStatement.setInt(6, notifica.getId_commento());
			preparedStatement.setInt(7, notifica.getId_risposta());
			preparedStatement.setString(8, notifica.getUsername());
			preparedStatement.setInt(9, notifica.getId_progetto());
			

			System.out.println("creaNotificaRisposta: "+preparedStatement.toString());
			preparedStatement.executeUpdate();
			connection.commit();
		}
		finally 
		{
			try 
			{
				if (preparedStatement != null)
				preparedStatement.close();
			} 
			finally 
			{
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		return notifica;
	}
	
	public Commento getCommentoById(int idCommento) throws SQLException 
	{
	    Connection connection = null;
	    Commento commento =null;
	    PreparedStatement preparedStatement = null;

	    final String select_sql = "SELECT * FROM commento WHERE id_commento= ?";

	    try 
	    {
	      connection = DriverManagerConnectionPool.getConnection();
	      preparedStatement = connection.prepareStatement(select_sql);

	      preparedStatement.setInt(1, idCommento);

	      System.out.println("getCommentoById: "+preparedStatement.toString());

	      ResultSet rs = preparedStatement.executeQuery();

	      if (rs.next())
	      {
	    	  commento=new Commento();
	    	  commento.setId_commento(idCommento);
	    	  commento.setContenuto(rs.getString("contenuto"));
	    	  commento.setUsername(rs.getString("username"));
	    	  commento.setId_progetto(rs.getInt("id_progetto"));
	      }
	    } 
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	    finally 
	    {
	      try 
	      {
	        if (preparedStatement != null) 
	        {
	          preparedStatement.close();
	        }
	      } 
	      finally 
	      {
	        DriverManagerConnectionPool.releaseConnection(connection);
	      }
	    }
	    return commento;
	  }
	
	public Notifica creaNotificaValutazione(Valutazione valutazione) throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ValcomModelDM model=new ValcomModelDM();
		ProgettoModelDM progettoModel=new ProgettoModelDM();
		Notifica notifica=new Notifica();
		Progetto progetto=progettoModel.getProgettoById(valutazione.getId_progetto());
		if(progetto.getUsername().equalsIgnoreCase(valutazione.getUsername()))
			return null;
		notifica.setId_valutazione(valutazione.getId_valutazione());
		notifica.setId_notifica(model.getLastIdNotifica()+1);
		notifica.setTipo("valutazione");
		notifica.setId_progetto(progetto.getId_progetto());
		notifica.setUsername(progetto.getUsername());
		notifica.setTitolo(valutazione.getUsername()+" ha lasciato una valutazione di "+valutazione.getVoto()+" stelle/a al progetto: "+progetto.getTitolo());
		notifica.setImmagine(progetto.getImmagine());

		String insertSQL = "INSERT INTO notifica (id_notifica,immagine,titolo,tipo,isClicked,id_valutazione, id_progetto, username ) VALUES (?,?,?,?,?, ?, ?, ?)"; 
		try 
		{
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setInt(1,notifica.getId_notifica());
			preparedStatement.setBlob(2, notifica.getImmagine());
			preparedStatement.setString(3, notifica.getTitolo());
			preparedStatement.setString(4, notifica.getTipo());
			preparedStatement.setInt(5, notifica.isClicked());
			preparedStatement.setInt(6, notifica.getId_valutazione());
			preparedStatement.setInt(7, notifica.getId_progetto());
			preparedStatement.setString(8, notifica.getUsername());

			System.out.println("creaNotificaValutazione: "+preparedStatement.toString());
			preparedStatement.executeUpdate();
			connection.commit();
		}
		finally 
		{
			try 
			{
				if (preparedStatement != null)
				preparedStatement.close();
			} 
			finally 
			{
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		return notifica;
	}
	
	public static ArrayList<Notifica> getNotificheByUsername(String username) throws SQLException
	{
		Notifica n=null;
		ArrayList<Notifica> notifiche=new ArrayList<Notifica>();
		Connection conn = DriverManagerConnectionPool.getConnection();
		try 
		{
			Statement st=conn.createStatement();
			 System.out.println("getNotificheByUsername:" + "select * from notifica where username="+username);
			ResultSet result =st.executeQuery("select * from notifica where username='"+username+"'");
			while(result.next())
			{
				int id=result.getInt("id_notifica");
				String titolo=result.getString("titolo");
				String tipo=result.getString("tipo");
				Blob immagine=result.getBlob("immagine");
				int isClicked=result.getInt("isClicked");
				String u=result.getString("username");
				int id_commento=result.getInt("id_commento");
				int id_risposta=result.getInt("id_risposta");
				int id_valutazione=result.getInt("id_valutazione");
				int id_progetto=result.getInt("id_progetto");
				
				n=new Notifica(id, immagine, titolo, tipo, isClicked, id_commento, id_risposta, id_progetto, id_valutazione, u);
				notifiche.add(n);
			}
			DriverManagerConnectionPool.releaseConnection(conn);
			return notifiche;
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public Integer getNumeroNotificheNonLette(String username) throws SQLException 
	{
	    Connection connection = null;
	    Integer n=null;
	    PreparedStatement preparedStatement = null;

	    final String select_sql = "SELECT count(*)as n FROM notifica WHERE username= ? and isClicked=0";

	    try 
	    {
	      connection = DriverManagerConnectionPool.getConnection();
	      preparedStatement = connection.prepareStatement(select_sql);

	      preparedStatement.setString(1, username);
	      
	      System.out.println("getNumeroNotificheNonLette: "+preparedStatement.toString());
	      ResultSet rs = preparedStatement.executeQuery();

	      if (rs.next())
	      {
	    	  n=rs.getInt("n");
	      }
	    } 
	    catch(Exception e){
	    	e.printStackTrace();
	    }
	    finally 
	    {
	      try 
	      {
	        if (preparedStatement != null) 
	        {
	          preparedStatement.close();
	        }
	      } 
	      finally 
	      {
	        DriverManagerConnectionPool.releaseConnection(connection);
	      }
	    }
	    return n;
	  }

	public void setClickedNotifica(Notifica notifica) throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String updateSQL = "UPDATE notifica set isClicked=1 where id_notifica = ?"; 
		try 
		{
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(updateSQL);
			preparedStatement.setInt(1, notifica.getId_notifica());

			System.out.println("setClickedNotifica: "+preparedStatement.toString());
			preparedStatement.executeUpdate();
			connection.commit();
		}
		finally 
		{
			try 
			{
				if (preparedStatement != null)
				preparedStatement.close();
			} 
			finally 
			{
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}
}