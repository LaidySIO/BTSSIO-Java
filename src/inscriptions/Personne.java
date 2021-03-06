package inscriptions;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;



/**
 * Représente une personne physique pouvant s'inscrire à une compétition.
 */

public class Personne extends Candidat
{
	private static final long serialVersionUID = 4434646724271327254L;
	private String prenom, mail, nom;
	private Set<Equipe> equipes;
	
	Personne(Inscriptions inscriptions, String nom, String prenom, String mail)
	{
		super(inscriptions, nom);
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
		equipes = new TreeSet<>();
	}
	

	/**
	 * Retourne le prénom de la personne.
	 * @return 
	 * @return
	 */
	

	public String getPrenom()
	{
		
		return prenom;
	}

	/**
	 * Modifie le prénom de la personne.
	 * @param prenom
	 */
	
	public void setPrenom(String prenom)
	{
//		String setP  = "UPDATE Personne SET prenomPersonne = '"+ prenom + "' "
//				+ "WHERE nomPersonne ='"+ this.mail+"';";
//		DB.Base.connexionExe(setP);
		DB.Req.modifPren(getPrenom(), prenom, this);

		this.prenom = prenom;
	}

	/**
	 * Retourne l'adresse électronique de la personne.
	 * @return
	 */
	
	public String getMail()
	{
		return mail;
	}

	/**
	 * Modifie l'adresse électronique de la personne.
	 * @param mail
	 */
	
	public void setMail(String mail)
	{
		String setM  = "UPDATE Personne SET mail = '"+ mail + "' "
				+ "WHERE nomPersonne ='"+ this.mail+"';";
		DB.Base.connexionExe(setM);

		//this.prenom = prenom;
		this.mail = mail;
	}
	
	public void setNom(String nom)
	{
		DB.Req.modifnom(this.nom, nom, this);
	}

	/**
	 * Retoure les équipes dont cette personne fait partie.
	 * @return
	 */
	
	public Set<Equipe> getEquipes()
	{
		return Collections.unmodifiableSet(equipes);
	}
	
	boolean add(Equipe equipe)
	{
		return equipes.add(equipe);
	}

	boolean remove(Equipe equipe)
	{
		return equipes.remove(equipe);
	}
	
	@Override
	public void delete()
	{
		DB.Req.suppPers(this.getNom(), this.getPrenom(), this.getMail());
		super.delete();
		for (Equipe e : equipes)
			e.remove(this);
	}
	
	@Override
	public String toString()
	{
		return super.toString() + " membre de " + equipes.toString();
	}
}
