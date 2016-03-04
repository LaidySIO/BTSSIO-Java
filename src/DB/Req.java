package DB;

import inscriptions.*;
import InterfaceUtilisateur.Interface;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import utilitaires.ligneDeCommande.ActionListe;

public class Req {

	public static ResultSet rs;
	public static String req;
	public static Statement smt;
	public static Connection con;


	public Req()
	{

	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////Fonctions EXE///////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	public static void addPers(String nom, String prenom,String mail)
	{
		String createP  = "Insert into Personne (nomPersonne,prenomPersonne,mail) "
				+ "values ('"+ nom +"','"+prenom+"','"+ mail + "');";
		Base.connexionExe(createP);
	}

	public static void addEqui(String nom)
	{
		String createE  = "Insert into Personne (nomPersonne,prenomPersonne,mail) "
				+ "values ('"+ nom +"');";
		Base.connexionExe(createE);
	}

	public static void addComp(String nom, LocalDate dateCloture,Boolean enEquipe)
	{
		String createC  = "Insert into Competition (nomCompetition,dateCloture,enEquipe) "
				+ "values ('"+ nom +"','"+dateCloture+"','"+ enEquipe + "');";
		Base.connexionExe(createC);
	}





	///////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////Fonctions Query//////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////



	public static void chargeEquipes()
	{
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		try {
			req = "Select * from Equipe;";
			con = DB.Base.connexion();
			smt = con.createStatement();
			rs =smt.executeQuery(req);
			while (rs.next())
				inscriptions.createEquipe(rs.getString("nomEquipe"));
		}
		catch (Exception e) {
			// gestion des exceptions
			System.out.println( e.getMessage() );
		}
	}
	public static ArrayList<String> affEqui() throws SQLException
	{
		ArrayList<String> a = new ArrayList<>();
		try {
			req = "Select * from Equipe;";
			String b;
			con = DB.Base.connexion();
			smt = con.createStatement();
			rs =smt.executeQuery(req);


			while (rs.next())
			{
				b = rs.getString("nomEquipe");
				a.add(b);

			}

			//			for (String value : a)
			//		{
			//			System.out.println(value);
			//		}	
		}
		catch (Exception e) {
			// gestion des exceptions
			System.out.println( e.getMessage() );
		}
		return a;


	}


}