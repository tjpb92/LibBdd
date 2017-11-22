package bdd;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Survey est une classe d�crivant les r�sultats d'une enqu�te de satisfaction
 *
 * @version 0.19
 * @author Thierry Baribaud
 */
public class Survey {

    /**
     * Indique si l'enqu�te a �t� effectu�e : Oui/Non
     */
    private String enqueteEffectuee = "Non";

    /**
     * Indique la qualit� de l'accueil t�l�phonique
     */
    private String qualiteAccueilTelephonique = null;

    /**
     * Indique la qualit� du d�lai d'intervention
     */
    private String qualiteDelaiIntervention = null;

    /**
     * Indique la qualit� du respect du rendez-vous
     */
    private String respectRendezVous = null;

    /**
     * Indique la qualit� de l'intervention
     */
    private String qualiteIntervention = null;

    /**
     * Indique le niveau de satisfaction globale du client
     */
    private String satisfactionGlobale = null;

    /**
     * Commentaire optionnel indiqu� par le client
     */
    private String commentaire = null;

    /**
     * Indique le mois du jour de r�alisation de l'enqu�te de satisfaction
     */
    private String moisDeLEnquete = null;

    /**
     * Constructeur de la classe Survey
     *
     * @param connection une connexion � la base de donn�es locale.
     * @param cnum r�f�rence � l'appel en cours.
     * @param c6int4 indique si l'enqu�te a �t� faite Oui=1, Non sinon
     * @param etatTicket �tat du ticket.
     * @throws SQLException SQLException en cas d'erreur SQL.
     * @throws ClassNotFoundException en cas de classe non trouv�e.
     */
    public Survey(Connection connection, int cnum, int c6int4, EtatTicket etatTicket) throws SQLException, ClassNotFoundException {
        FessaisDAO fessaisDAO;
        Fessais fessais;
        Timestamp edate = null;
        DateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.FRENCH);

        // L'enqu�te a-t-elle �t� effectu�e ?
        if (c6int4 == 1) {
            setEnqueteEffectuee("Oui");
        }

        // R�cup�re la qualit� de l'accueil t�l�phonique
        fessaisDAO = new FessaisDAO(connection, etatTicket);
        fessaisDAO.setLastTrialPreparedStatement(cnum, 201);
        fessais = fessaisDAO.getLastTrial();
        fessaisDAO.closeLastTrialPreparedStatement();
        if (fessais != null) {
            setQualiteAccueilTelephonique(fessais.getEmessage());
            if (edate == null) {
                edate = fessais.getEdate();
            }
        }

        // R�cup�re la qualit� du d�lai d'intervention
        fessaisDAO.setLastTrialPreparedStatement(cnum, 202);
        fessais = fessaisDAO.getLastTrial();
        fessaisDAO.closeLastTrialPreparedStatement();
        if (fessais != null) {
            setQualiteDelaiIntervention(fessais.getEmessage());
            if (edate == null) {
                edate = fessais.getEdate();
            }
        }

        // R�cup�re le respect du rendez-vous
        fessaisDAO.setLastTrialPreparedStatement(cnum, 203);
        fessais = fessaisDAO.getLastTrial();
        fessaisDAO.closeLastTrialPreparedStatement();
        if (fessais != null) {
            setRespectRendezVous(fessais.getEmessage());
            if (edate == null) {
                edate = fessais.getEdate();
            }
        }

        // Qualit� de l'intervention
        fessaisDAO.setLastTrialPreparedStatement(cnum, 204);
        fessais = fessaisDAO.getLastTrial();
        fessaisDAO.closeLastTrialPreparedStatement();
        if (fessais != null) {
            setQualiteIntervention(fessais.getEmessage());
            if (edate == null) {
                edate = fessais.getEdate();
            }
        }

        // Satisfaction globale
        fessaisDAO.setLastTrialPreparedStatement(cnum, 205);
        fessais = fessaisDAO.getLastTrial();
        fessaisDAO.closeLastTrialPreparedStatement();
        if (fessais != null) {
            setSatisfactionGlobale(fessais.getEmessage());
            if (edate == null) {
                edate = fessais.getEdate();
            }
        }

        if (edate != null) {
            setMoisDeLEnquete(monthFormat.format(edate));
        }
    }

    /**
     * @return l'indication sur enqu�te effectu�e ou non
     */
    public String getEnqueteEffectuee() {
        return enqueteEffectuee;
    }

    /**
     * @param enqueteEffectuee indique si l'enqu�te est effectu�e ou non
     */
    public void setEnqueteEffectuee(String enqueteEffectuee) {
        this.enqueteEffectuee = enqueteEffectuee;
    }

    /**
     * @return la qualit� de l'accueil t�l�phonique
     */
    public String getQualiteAccueilTelephonique() {
        return qualiteAccueilTelephonique;
    }

    /**
     * @param qualiteAccueilTelephonique d�finit la qualit� de l'accueil
     * t�l�phonique
     */
    public void setQualiteAccueilTelephonique(String qualiteAccueilTelephonique) {
        this.qualiteAccueilTelephonique = qualiteAccueilTelephonique;
    }

    /**
     * @return la qualit� de l'intervention
     */
    public String getQualiteDelaiIntervention() {
        return qualiteDelaiIntervention;
    }

    /**
     * @param qualiteDelaiIntervention d�finit la qualit� de l'intervention
     */
    public void setQualiteDelaiIntervention(String qualiteDelaiIntervention) {
        this.qualiteDelaiIntervention = qualiteDelaiIntervention;
    }

    /**
     * @return le respect du rendez-vous
     */
    public String getRespectRendezVous() {
        return respectRendezVous;
    }

    /**
     * @param respectRendezVous d�finit le respect du rendez-vous
     */
    public void setRespectRendezVous(String respectRendezVous) {
        this.respectRendezVous = respectRendezVous;
    }

    /**
     * @return la qualit� de l'intervention
     */
    public String getQualiteIntervention() {
        return qualiteIntervention;
    }

    /**
     * @param qualiteIntervention d�finit la qualit� de l'intervention
     */
    public void setQualiteIntervention(String qualiteIntervention) {
        this.qualiteIntervention = qualiteIntervention;
    }

    /**
     * @return la satisfaction globale du client
     */
    public String getSatisfactionGlobale() {
        return satisfactionGlobale;
    }

    /**
     * @param satisfactionGlobale d�finit la satisfaction globale du client
     */
    public void setSatisfactionGlobale(String satisfactionGlobale) {
        this.satisfactionGlobale = satisfactionGlobale;
    }

    /**
     * @return le commentaire du client
     */
    public String getCommentaire() {
        return commentaire;
    }

    /**
     * @param commentaire d�finit le commentaire du client
     */
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    /**
     * Retourne le mois du jour de r�alisation de l'enqu�te de satisfaction
     *
     * @return Retourne le mois du jour de r�alisation de l'enqu�te de
     * satisfaction
     */
    public String getMoisDeLEnquete() {
        return moisDeLEnquete;
    }

    /**
     * D�finit le mois du jour de r�alisation de l'enqu�te de satisfaction
     *
     * @param moisDeLEnquete le mois du jour de r�alisation de l'enqu�te de
     * satisfaction
     */
    public void setMoisDeLEnquete(String moisDeLEnquete) {
        this.moisDeLEnquete = moisDeLEnquete;
    }

    @Override
    public String toString() {
        return "Survey{" + "enqueteEffectuee=" + enqueteEffectuee
                + ", qualiteAccueilTelephonique=" + qualiteAccueilTelephonique
                + ", qualiteDelaiIntervention=" + qualiteDelaiIntervention
                + ", respectRendezVous=" + respectRendezVous
                + ", qualiteIntervention=" + qualiteIntervention
                + ", satisfactionGlobale=" + satisfactionGlobale
                + ", commentaire=" + commentaire
                + ", moisDeLEnquete=" + moisDeLEnquete
                + '}';
    }
}
