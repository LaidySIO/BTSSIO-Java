package InterfaceUtilisateur;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import utilitaires.ligneDeCommande.*;
import inscriptions.*;

/*** IMPLEMENTATION DU DIALOGUE EN LIGNE DE COMMANDE ****/

public class Interface {

	static boolean db = true;
	public static final Inscriptions inscript = Inscriptions.getInscriptions(db);
	public static ArrayList<String> a;

	/**
	 * constitue la page d'acceuil de l'application en regroupant les 3
	 * principaux menus -candidat, competitions et inscription
	 */
	public static Menu getMenuPrincipal() {
		Menu menuPrincipal = new Menu("Menu Principal");
		menuPrincipal.ajoute(getMenuPersonne());
		menuPrincipal.ajoute(getMenuEquipe());
		menuPrincipal.ajoute(getMenuCompetition());
		menuPrincipal.ajouteQuitter("Q");
		return menuPrincipal;
	}

	static Menu getMenuPersonne() {
		Menu personne = new Menu("Les Personnes", "LP");
		personne.ajoute(getOptionAfficherPersonne());
		personne.ajoute(getOptionModifierPersonne());
		personne.ajoute(getOptionCreerPersonne());
		personne.ajoute(getOptionSupprimerPersonne());
		personne.ajouteRevenir("P");
		personne.ajouteQuitter("Q");
		return personne;
	}

	static Menu getMenuEquipe() {
		Menu equipe = new Menu("Les Equipes", "LE");
		equipe.ajoute(getOptionAfficherEquipe());
		equipe.ajoute(getMenuModifierPersonneEquipe());
		equipe.ajoute(getOptionCreerEquipe());
		equipe.ajoute(getOptionSupprimerEquipe());
		equipe.ajouteRevenir("P");
		;
		equipe.ajouteQuitter("Q");
		;
		return equipe;
	}

	static Option getOptionAfficherPersonne() {

		Option afficherCandidats = new Option("Afficher les personnes", "AP",
				getActionAfficherPersonne());

		return afficherCandidats;
	}

	static Action getActionAfficherPersonne() {

		return new Action() {
			public void optionSelectionnee() {

				int i = 0;
				int count = 1;
				for (inscriptions.Candidat c : inscript.getCandidats()) {
					if (c instanceof Personne) {

						System.out.println(count + "- " + c.getNom() + " "
								+ ((Personne) c).getPrenom());
						System.out.println("\tCOMPETITIONS:"
								+ NomCompetitions(c) + "\n");
						System.out.println("\tEQUIPES:"
								+ NomEquipes((Personne)c) + "\n");
						count++;
						i++;

					}
					
				}

				if (i == 0)
					System.out.println("\tAucune personne enregistr�e!!\n");
			}

		};

	}

	static Option getOptionAfficherEquipe() {
		Option afficherCandidats = new Option("Afficher les �quipes", "AE",
				getActionAfficherEquipe());
		return afficherCandidats;
	}

	static Action getActionAfficherEquipe() {
		
		return new Action() {
			public void optionSelectionnee() {
				
				int i = 0;
				int count = 1;
				for (inscriptions.Candidat c : inscript.getCandidats()) {
					
					
					if (c instanceof Equipe) {
						System.out.println(count + "- " + c.getNom());
						System.out.println("\tMembres:"+NomMembres((Equipe) c)+"\n");
						System.out.println("\tComp�titions:"+NomCompetitions(c)+"\n");
					if (((Equipe)c).getEntraineur() != null)
						 System.out.println("Entraineur :" + ((Equipe) c).getEntraineur().getNom());
						count++;
						i++;
					}
				}

				if (i == 0)
					System.out.println("\tAucune Equipe enregistr�e!\n");
			}
		};
	}

	static Option getOptionCreerPersonne() {
		Option creationPersonne = new Option("Cr�er une personne", "CP",
				getActionCreerPersonne());
		return creationPersonne;
	}

	static Action getActionCreerPersonne() {
		return new Action() {
			public void optionSelectionnee() {
				String nom = utilitaires.EntreesSorties
						.getString("entrer le nom");
				String prenom = utilitaires.EntreesSorties
						.getString("entrer le prenom");
				String mail = utilitaires.EntreesSorties
						.getString("entrer l'adresse mail");
				if (nom.isEmpty() || prenom.isEmpty() || mail.isEmpty()) {
					System.out.println("\tD�MENT REMPLIR LES CHAMPS!!!");
					getMenuPersonne().start();
				} else
					inscript.createPersonne(nom, prenom, mail,true);	//TRUE pour utiliser la BD
			}
		};
	}

	static Option getOptionCreerEquipe() {
		Option creationEquipe = new Option("Cr�er une Equipe", "CE",
				getActionCreerEquipe());
		return creationEquipe;
	}

	static Action getActionCreerEquipe() {
		return new Action() {
			public void optionSelectionnee() {
				String nom = utilitaires.EntreesSorties
						.getString("entrer le nom");
				// LocalDate date =
				// LocalDate.parse(utilitaires.EntreesSorties.getString("entrer la date de cl�ture"));
				inscript.createEquipe(nom,true,null);	//TRUE pour utiliser la BD
			}
		};
	}

	static Option getOptionModifierPersonne() {
		Option modifierPersonne = new Liste<Personne>("Modifier Une Personne", "MP", getActionListeModification());
//		modifierPersonne.ajoute(getOptionListerPersonne());
//		modifierPersonne.ajouteRevenir("P");
//		modifierPersonne.ajouteQuitter("Q");
		// modifierEquipe.ajoute(getOptionSupprimerPersonneAequipe());
		return modifierPersonne;
	}

//	static Option getOptionListerPersonne() {
//		Option ListePersonne = new Liste<Personne>("Liste Des Personnes", "LP",
//				getActionListeModification());
//	
//		return ListePersonne;
//	}

	static ActionListe<Personne> getActionListeModification() {
		return new ActionListe<Personne>() {
			
			@Override
			public List<Personne> getListe() {
				ArrayList<Personne> personnes = new ArrayList<>();
				for (Candidat candidat : inscript.getCandidats())
					if (candidat instanceof Personne)
						personnes.add((Personne) candidat);

					
				if (personnes.isEmpty()) {
					System.out.println("\tACUNE PERSONNE ENREGISTREE!!\n");
					getMenuEquipe().start();
				} else
					System.out
							.println("\tchoisir une personne en saisissant son num�ro\n");
				return personnes;
			}
			
			@Override
			public void elementSelectionne(int indice, Personne element) {
				indice = indice + 1;
				System.out
						.println("\tVous avez selectionn� le membre "
								+ NomCandidat(element) + " � l'indice "
								+ indice + "\n");
				Menu modifMembre = getModifierMembre(element);
				modifMembre.start();

			}

			@Override
			public void add(String b) {
				// TODO Auto-generated method stub
				
			}

		};
	}

	static Menu getModifierMembre(Personne membre) {
		Menu modifierMembre = new Menu("Modifier un membre");
		modifierMembre.ajoute(getOptionModifierNom(membre));
		modifierMembre.ajoute(getOptionModifierPrenom(membre));
		modifierMembre.ajoute(getOptionModifierMail(membre));
		modifierMembre.ajouteRevenir("P");
		modifierMembre.ajouteQuitter("Q");
		return modifierMembre;
	}

	static Option getOptionModifierNom(Personne membre) {
		Option modificationNom = new Option("Modifier son nom", "MN",
				getActionModifierNom(membre));
		return modificationNom;
	}

	static Action getActionModifierNom(Personne membre) {
		return new Action() {

			@Override
			public void optionSelectionnee() {
				String newLastName = utilitaires.EntreesSorties
						.getString("Entrer le nouveau nom");
				membre.setNom(newLastName);
				System.out.println("\tLe nom a bien �t� modifi�!\n");
			}

		};
	}

	static Option getOptionModifierPrenom(Personne membre) {
		Option modificationNom = new Option("Modifier son pr�nom", "MP",
				getActionModifierPrenom(membre));
		return modificationNom;
	}

	static Action getActionModifierPrenom(Personne membre) {
		return new Action() {

			@Override
			public void optionSelectionnee() {
				String newFirstName = utilitaires.EntreesSorties
						.getString("Entrer le nouveau pr�nom");
				membre.setPrenom(newFirstName);
				System.out.println("\tLe pr�nom a bien �t� modifi�!\n");
			}

		};
	}

	static Option getOptionModifierMail(Personne membre) {
		Option modificationNom = new Option("Modifier son mail", "MM",
				getActionModifierMail(membre));
		return modificationNom;
	}

	static Action getActionModifierMail(Personne membre) {
		return new Action() {

			@Override
			public void optionSelectionnee() {
				String newMail = utilitaires.EntreesSorties
						.getString("Entrer le nouveau mail");
				membre.setMail(newMail);
				System.out.println("\tLe mail a bien �t� modifi�!\n");
			}

		};
	}

	static Menu getMenuModifierPersonneEquipe() {
		Menu modifierEquipe = new Menu("Modifier l'�quipe", "ME");
		modifierEquipe.ajoute(getOptionListerEquipe());
		modifierEquipe.ajoute(getOptionModifierEntraineur());
		modifierEquipe.ajouteRevenir("P");
		modifierEquipe.ajouteQuitter("Q");
		return modifierEquipe;
	}
	static Option getOptionModifierEntraineur()
	{
		Option ModifEntrain = new Liste<Equipe>("Modifier Entraineur", "ME", getActionModifEntraineur());
		return ModifEntrain;
	}
	static ActionListe<Equipe> getActionModifEntraineur() {
		return new ActionListe<Equipe> () {

			@Override
			public List<Equipe> getListe() {
				ArrayList<Equipe> equipes = new ArrayList<>();
				for (Candidat candidat : inscript.getCandidats())
					if (candidat instanceof Equipe)
						equipes.add((Equipe) candidat);
				if (equipes.isEmpty()) {
					System.out.println("\tAucune Equipe enregistr�e!!\n");
				}
				return equipes;
			}
			@Override
			public void elementSelectionne(int indice, Equipe equipe) {	
				Menu choisirEntraineur = getMenuChoisirEntraineur(equipe);
				choisirEntraineur.start();
			}
			@Override
			public void add(String b) {
				// TODO Auto-generated method stub
				
			}
			
		};
	}

	private static Menu getMenuChoisirEntraineur(final Equipe equipe)
	{
		return new Liste<>("S�lectionnez un entra�neur", "", 
				new ActionListe<Personne>()
		{
			@Override
			public List<Personne> getListe() {
				List<Personne> liste = new ArrayList<>(inscript.getPersonnes());
				liste.remove(equipe.getEntraineur());
				return liste;
			}

			@Override
			public void elementSelectionne(int indice, Personne entraineur) {
				equipe.setEntraineur(entraineur);
			}

			@Override
			public void add(String b) {
				// TODO Auto-generated method stub
				
			}
		});
	}
//		{
//			return new ActionListe<Personne>()
//				{
//				@Override
//				public List<Personne> getListe(){
//					ArrayList<Personne> personnes = new ArrayList<>();
//					for (candidat candidat : inscript.getCandidats())
//						if (candidat instance of Personne)
//							
//						}
//				}
//		}
//		element.setEntraineur(P);
//	}
	
	static Option getOptionListerEquipe() {
		Option ListeEquipes = new Liste<Equipe>("Lister les Equipes", "LE",
				getActionListeEquipe());
		return ListeEquipes;
	}

	static ActionListe<Equipe> getActionListeEquipe() {
		return new ActionListe<Equipe>() {

			@Override
			public List<Equipe> getListe() {
				ArrayList<Equipe> equipe = new ArrayList<>();
				for (Candidat candidat : inscript.getCandidats())
					if (candidat instanceof Equipe)
						equipe.add((Equipe) candidat );
				if (equipe.isEmpty()) {
					System.out.println("\tAucune Equipe enregistr�e!!\n");
					getMenuEquipe().start();
				}
				System.out
						.println("\tChoisir l'�quipe en saisissant son num�ro\n");
				return equipe;
			}

			@Override
			public void elementSelectionne(int indice, Equipe element) {
				Menu modifMembre = getModifierMembresEquipe(element);
				modifMembre.start();

			}

			@Override
			public void add(String b) {
				// TODO Auto-generated method stub
				
			}

		};
	}

	static Menu getModifierMembresEquipe(Equipe equipe) {
		Menu modifierEquipe = new Menu("Modifier les membres d'une equipe");
		modifierEquipe.ajoute(getOptionEnleverPersonne(equipe));
		modifierEquipe.ajoute(getOptionAjouterPersonne(equipe));
		modifierEquipe.ajouteRevenir("P");
		modifierEquipe.ajouteQuitter("Q");
		return modifierEquipe;
	}

	static Option getOptionEnleverPersonne(Equipe equipe) {
		Option enleverPersonne = new Liste<>("Enlever une personne", "EP",
				getActionEnleverPersonne(equipe));
		return enleverPersonne;
	}

	static ActionListe<Personne> getActionEnleverPersonne(final Equipe equipe) {
		return new ActionListe<Personne>() {

			@Override
			public List<Personne> getListe() {
				ArrayList<Personne> liste = new ArrayList<Personne>();
				if (liste.isEmpty()) {
					System.out.println("\tAucune personne dans l'�quipe\n");
					getMenuModifierPersonneEquipe().start();
				}
				return liste;
			}

			@Override
			public void elementSelectionne(int indice, Personne element) {
				equipe.remove(element);
				System.out.println("Le membre a bien �t� enlev� de l'�quipe");
			}

			@Override
			public void add(String b) {
				// TODO Auto-generated method stub
				
			}


		};
	}

	static Option getOptionAjouterPersonne(Equipe equipe) {
		Option ajouterPersonne = new Liste<>("ajouter une personne", "AP",
				getActionAjouterPersonne(equipe));
		return ajouterPersonne;
	}

//	static ActionListe<Personne> getActionAjouterPersonne(final Equipe equipe) {
//		return new ActionListe<Personne>() {
//
//			@Override
//			public List<Personne> getListe() {
//				ArrayList<Personne> personnes = new ArrayList<>();
//				for (Candidat candidat : inscript.getCandidats())
//					if (candidat instanceof Equipe)
//						personnes.add((Personne) candidat);
//				if (personnes.isEmpty()) {
//					System.out.println("\tAucun membre enregistr�!!\n");
//					getMenuModifierPersonneEquipe().start();
//				}
//
//				return personnes;
//			}
//
//			@Override
//			public void elementSelectionne(int indice, Personne element) {
//				DB.Req.addToEquipe(equipe.getNom(),element.getNom() );
//				equipe.add(element);
//			}
//
//			@Override
//			public void add(String b) {
//				// TODO Auto-generated method stub
//				
//			}
//		};
//	}
	static ActionListe<Personne> getActionAjouterPersonne(final Equipe equipe) {
		return new ActionListe<Personne>() {

			@Override
			public List<Personne> getListe() {
				ArrayList<Personne> personnes = new ArrayList<>();
				for (Candidat candidat : inscript.getCandidats())
					if (candidat instanceof Personne)
						personnes.add((Personne) candidat);
				if (personnes.isEmpty()) {
					System.out.println("\tAucun membre enregistr�!!\n");
					getMenuModifierPersonneEquipe().start();
				}

				return personnes;
			}

			@Override
			public void elementSelectionne(int indice, Personne element) {
				DB.Req.addToEquipe(element,equipe );
				equipe.add(element);
			}

			@Override
			public void add(String b) {
				// TODO Auto-generated method stub
				
			}
		};
	}

	static Option getOptionSupprimerPersonne() {
		Option supprimerCandidat = new Liste<Personne>(
				"Supprimer une personne", "SP", getActionListeSuppression());
		return supprimerCandidat;
	}

	static ActionListe<Personne> getActionListeSuppression() {
		return new ActionListe<Personne>() {

			@Override
			public List<Personne> getListe() {
				ArrayList<Personne> personnes = new ArrayList<>();
				for (Candidat candidat : inscript.getCandidats())
					if (candidat instanceof Personne)
						personnes.add((Personne) candidat);
				if (personnes.isEmpty()) {
					System.out.println("\tAucune Personne Enregistr�e\n");
					getMenuPersonne().start();
				}
				return personnes;
			}

			@Override
			public void elementSelectionne(int indice, Personne element) {
			
				System.out.println("SELECTION :" + element.getPrenom()+" "+element.getNom());
				element.delete();
				System.out.println("Suppression effectu�e");

			}

			@Override
			public void add(String b) {
				// TODO Auto-generated method stub
				
			}
		};
	}

	static Option getOptionSupprimerEquipe() {
		Option supprimerEquipe = new Liste<Equipe>("Supprimer une Equipe",
				"SE", getActionListeSupprimerEquipe());
		return supprimerEquipe;
	}

	static ActionListe<Equipe> getActionListeSupprimerEquipe() {
		return new ActionListe<Equipe>() {

			@Override
			public List<Equipe> getListe() {
				ArrayList<Equipe> ListeEquipe = new ArrayList<Equipe>();
				for (Candidat c : inscript.getCandidats())
					if (c instanceof Equipe)
						ListeEquipe.add((Equipe) c);
				if (ListeEquipe.isEmpty()) {
					System.out.println("\tAucune Equipe Enregistr�e\n");
					getMenuEquipe().start();
				}
				return ListeEquipe;
			}

			@Override
			public void elementSelectionne(int indice, Equipe element) {
				System.out.println("\tVous avez s�lectionn� l'�l�ment"
						+ element + "� l'indice " + indice + "\n");
				element.delete();
				System.out.println(element + " a bien �t� supprim�e!\n");

			}

			@Override
			public void add(String b) {
				// TODO Auto-generated method stub
				
			}
		};
	}

	static Menu getMenuCompetition() {
		Menu competition = new Menu("COMPETITIONS", "CO");
		competition.ajoute(getOptionAfficherCompetition());
		competition.ajoute(getOptionCreerCompetition());
		competition.ajoute(getOptionSupprimerCompetition());
		competition.ajoute(getMenuInscrireCandidat());
		competition.ajouteRevenir("P");
		competition.ajouteQuitter("Q");
		return competition;
	}

	static Option getOptionAfficherCompetition() {
		Option afficherCompetition = new Option("Afficher les comp�titions",
				"AC", getActionAfficherCompetition());
		return afficherCompetition;
	}

	static Action getActionAfficherCompetition() {
		return new Action() {

			@Override
			public void optionSelectionnee() {
				String ch;
				String stat;
				int count = 1;
				for (inscriptions.Competition c : inscript.getCompetitions()) {
					if (c.ouvert())
						ch = "ouvertes";
					else
						ch = "ferm�es";
					if (c.estEnEquipe())
						stat = "�quipe";
					stat = "individuel";
					System.out.println("\t" + count + "-La comp�titon\t"
							+ c.toString() + "\n\t\tles inscriptions sont\t"
							+ ch + "\n\t\t\t\tjusqu'� la date:\t"
							+ c.getDateCloture()
							+ "\n\t\telles se d�roulent en\t" + stat + "\n\n");
					count++;
				}
				//if (db)
					inscript.getCompetitions();
			}
		};
	}

	static Option getOptionCreerCompetition() {
		Option creationCompetition = new Option("Nouvelle Comp�tition", "NC",
				getActionCreerCompetition());
		return creationCompetition;
	}

	static Action getActionCreerCompetition() {
		return new Action() {
			@Override
			public void optionSelectionnee() {
				String nom = utilitaires.EntreesSorties
						.getString("entrer le nom");
				LocalDate date = LocalDate.parse(utilitaires.EntreesSorties
						.getString("entrer la date"));
				boolean enEquipe = (utilitaires.EntreesSorties
						.getString("en Equipe?")).matches("true|1|vrai|oui") ? true
						: false;
				inscript.createCompetition(nom, date, enEquipe,true);
			}
		};
	}

	static Option getOptionSupprimerCompetition() {
		return new Liste<Competition>("Supprimer une competition", "SC",
				getActionSupprimerCompetition());
	}

	static ActionListe<Competition> getActionSupprimerCompetition() {
		return new ActionListe<Competition>() {

			public List<Competition> getListe() {
				List<Competition> compet = new ArrayList<Competition>();
				for (Competition competition : inscript.getCompetitions())
					compet.add(competition);
				if (compet.isEmpty()) {
					System.out.println("\tAucune Comp�tition Enregistr�e!!\n");
					getMenuCompetition().start();
				}
				return compet;
			}

			@Override
			public void elementSelectionne(int indice, Competition element) {
				indice++;
				System.out.println("\tVous avez s�lectionnez la comp�tition "
						+ element + "� l'indice " + indice + "\n");
				element.delete();
				System.out.println("\tL'�l�ment a �t� supprim�\n");

			}

			@Override
			public void add(String b) {
				// TODO Auto-generated method stub
				
			}
		};
	}

	static Menu getMenuInscrireCandidat() {
		Menu inscription = new Menu("Inscrire un candidat", "IC");
		inscription.ajoute(getOptionListerCompetitions());
		inscription.ajouteRevenir("P");
		inscription.ajouteQuitter("Q");
		return inscription;
	}

	static Liste<Competition> getOptionListerCompetitions() {
		Liste<Competition> ListeCompetitions = new Liste<Competition>(
				"Liste des comp�titions", "LC", getActionListerCompetitions());
		return ListeCompetitions;
	}

	static ActionListe<Competition> getActionListerCompetitions() {
		//DB.Req.chargeEquipes();
		return new ActionListe<Competition>() {
			
			@Override
			public List<Competition> getListe() {
				ArrayList<Competition> Comp = new ArrayList<Competition>();
				for (Competition co : inscript.getCompetitions())
					Comp.add(co);
				if (Comp.isEmpty())
					System.out.println("Aucune Competition pour le moment");
				return Comp;
			}

			@Override
			public void elementSelectionne(int indice, Competition element) {
				if (!(element.getCandidats().isEmpty()))
					System.out
							.println("\tListe des inscripts � la comp�tition "
									+ element.getNom() + "\n");
				int count = 1;
				for (Candidat c : element.getCandidats()) {
					System.out.println("\t\t" + count + "- " + c.getNom()
							+ "\n");
					count++;
				}
				if (element.estEnEquipe()) {
					System.out
							.println("\tCette competition se d�roule en �quipe, veuillez choisir l'�quipe � inscrire\n");
					MenuInscrireEquipe(element).start();
				} else {
					System.out
							.println("\tCette competition  se d�roule individuellement, veuillez choisir la personne � inscrire\n");
					MenuInscrirePersonne(element).start();
				}
			}

			@Override
			public void add(String b) {
				// TODO Auto-generated method stub
				
			}
		};
	}

	static Menu MenuInscrirePersonne(Competition c) {
		Menu inscrireP = new Menu("Inscrire une personne", "IP");
		inscrireP.ajoute(getOptionListePersonne(c));
		inscrireP.ajouteRevenir("P");
		inscrireP.ajouteQuitter("Q");
		return inscrireP;
	}

	static Liste<Personne> getOptionListePersonne(Competition compet) {
		Liste<Personne> listepersonne = new Liste<Personne>(
				"Liste des Personnes", "L", getActionListePersonne(compet));
		return listepersonne;
	}

	static ActionListe<Personne> getActionListePersonne(Competition comp) {
		return new ActionListe<Personne>() {

			@Override
			public List<Personne> getListe() {
				ArrayList<Personne> pers = new ArrayList<Personne>();
				for (Candidat p : inscript.getCandidats())
					if (p instanceof Personne
							&& !(comp.getCandidats().contains(p)))
						pers.add((Personne) p);
				if (pers.isEmpty())
					System.out.println("Aucune personne enregistr�e");
				return pers;
			}

			@Override
			public void elementSelectionne(int indice, Personne element) {
				if (comp.add(element)) {
					System.out.println("\tLe membre " + element.getNom()
							+ " est maintenant inscrit � la comp�tition "
							+ comp.getNom() + "\n");
					getMenuCompetition().start();
				}

			}

			@Override
			public void add(String b) {
				// TODO Auto-generated method stub
				
			}

		};
	}

	static Menu MenuInscrireEquipe(Competition c) {
		Menu inscrireE = new Menu("Inscrire une �quipe", "IE");
		inscrireE.ajoute(getOptionListeEquipe(c));
		inscrireE.ajouteRevenir("P");
		inscrireE.ajouteQuitter("Q");
		return inscrireE;
	}

	static Option getOptionListeEquipe(Competition comp) {
		Liste<Personne> equipes = new Liste<Personne>("Listes Equipes", "LE",
				getActionListerEquipe(comp));
		return equipes;
	}

	static ActionListe<Personne> getActionListerEquipe(Competition comp) {
		return new ActionListe<Personne>() {

			@Override
			public List<Personne> getListe() {
				ArrayList<Personne> equi = new ArrayList<Personne>();
				for (Candidat p : inscript.getCandidats())
					if (p instanceof Personne
							&& !(comp.getCandidats().contains(p)))
						equi.add((Personne) p);
				if (equi.isEmpty())
					System.out.println("Aucune Equipe enregistr�e");
				return equi;
			}

			@Override
			public void elementSelectionne(int indice, Personne element) {
				if (comp.add(element)) {
					System.out.println("\tL'�quipe " + element.getNom()
							+ " est maintenant inscrite � la comp�tition "
							+ comp.getNom() + "\n");

				}
			}

			@Override
			public void add(String b) {
				// TODO Auto-generated method stub
				
			}
		};
	}

	// static Menu getMenuInscription() {
	// Menu Inscription = new Menu("INSCRIPTIONS", "I");
	// Inscription.ajoute(getOptionAfficherInscription());
	// Inscription.ajoute(getOptionPrecedent());
	// return Inscription;
	// }

	static Option getOptionAfficherInscription() {
		Option afficherInscription = new Option("Afficher les inscriptions",
				"AI", getActionAfficherInscriptions());
		return afficherInscription;
	}

	static Action getActionAfficherInscriptions() {
		return new Action() {

			@Override
			public void optionSelectionnee() {
				int count = 1;
				for (Candidat p : inscript.getCandidats())
					if (p instanceof Personne) {
						System.out
								.println(count
										+ "- Le membre: <<"
										+ p.getNom()
										+ ">> \n\test inscrit � la (aux)comp�tition(s): <<"
										+ NomCompetitions((Personne) (p))
										+ " >>, \n\test membre de l' (des) �quipe(s): <<"
										+ NomEquipes((Personne) (p)) + ">>\n\n");
						count++;
					}
				for (Candidat e : inscript.getCandidats())
					if (e instanceof Personne) {
						System.out
								.println(count
										+ "- L'�quipe: <<"
										+ e.getNom()
										+ ">> \n\test inscrite � la (aux) comp�tition(s): <<"
										+ NomCompetitions(e)
										+ ">> \n\tses Membres sont: "
										+ NomMembres((Equipe) (e)) + "\n\n");
						count++;
					}

			}
		};
	}

	public static String NomEquipes(Personne c) {
		String msg = " ";
		for (Equipe e : c.getEquipes())
			msg = msg + e.getNom() + "; ";
		return msg;
	}

	public static String NomMembres(Equipe e) {
		String ch = " ";
		for (Personne p : e.getMembres())
			ch = ch + p.getNom() + p.getPrenom() + p.getMail();
		return ch;
	}

	public static String NomCompetitions(Candidat e) {
		String ch = " ";
		for (Competition p : e.getCompetitions())
			ch = ch + p.getNom() + ";";
		return ch;
	}

	public static String NomCandidat(Candidat e) {
		if (e instanceof Personne)
			return ((Personne) (e)).getNom() + " "
					+ ((Personne) (e)).getPrenom();
		return ((Personne) (e)).getNom();
	}

	public static void main(String[] args) {
		getMenuPrincipal().start();
	}
}
