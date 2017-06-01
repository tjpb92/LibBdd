package bdd;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Survey est une classe d�crivant les r�sultats d'une enqu�te de satisfaction
 *
 * @version 0.17
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
    
    private String qualiteDelaiIntervention = null;
    private String respectRendezVous = null;
    private String qualiteIntervention = null;
    private String satisfactionGlobale = null;
    private String commentaire = null;

    /**
     * Constructeur de la classe Survey
     * @param connection une connexion � la base de donn�es locale.
     * @param cnum r�f�rence � l'appel en cours.
     * @param c6int4 indique si l'enqu�te a �t� faite Oui=1, Non sinon
     * @param etatTicket �tat du ticket.
     * @throws SQLException SQLException en cas d'erreur SQL.
     * @throws ClassNotFoundException  en cas de classe non trouv�e.
     */
    public Survey(Connection connection, int cnum, int c6int4, EtatTicket etatTicket) throws SQLException, ClassNotFoundException {
        FessaisDAO fessaisDAO;
        Fessais fessais;

        // L'enqu�te a-t-elle �t� effectu�e ?
        if (c6int4 == 1) setEnqueteEffectuee("Oui");

        // R�cup�re la qualit� de l'accueil t�l�phonique
        fessaisDAO = new FessaisDAO(connection, etatTicket);
        fessaisDAO.setLastTrialPreparedStatement(cnum, 201);
        fessais = fessaisDAO.getLastTrial();
        fessaisDAO.closeLastTrialPreparedStatement();
        if (fessais != null) {
            setQualiteAccueilTelephonique(fessais.getEmessage());
        }

        // R�cup�re la qualit� du d�lai d'intervention
        fessaisDAO.setLastTrialPreparedStatement(cnum, 202);
        fessais = fessaisDAO.getLastTrial();
        fessaisDAO.closeLastTrialPreparedStatement();
        if (fessais != null) {
            setQualiteDelaiIntervention(fessais.getEmessage());
        }

        // R�cup�re le respect du rendez-vous
        fessaisDAO.setLastTrialPreparedStatement(cnum, 203);
        fessais = fessaisDAO.getLastTrial();
        fessaisDAO.closeLastTrialPreparedStatement();
        if (fessais != null) {
            setRespectRendezVous(fessais.getEmessage());
        }

        // Qualit� de l'intervention
        fessaisDAO.setLastTrialPreparedStatement(cnum, 204);
        fessais = fessaisDAO.getLastTrial();
        fessaisDAO.closeLastTrialPreparedStatement();
        if (fessais != null) {
            setQualiteIntervention(fessais.getEmessage());
        }

        // Satisfaction globale
        fessaisDAO.setLastTrialPreparedStatement(cnum, 205);
        fessais = fessaisDAO.getLastTrial();
        fessaisDAO.closeLastTrialPreparedStatement();
        if (fessais != null) {
            setSatisfactionGlobale(fessais.getEmessage());
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
     * @param qualiteAccueilTelephonique d�finit la qualit� de l'accueil t�l�phonique
     */
    public void setQualiteAccueilTelephonique(String qualiteAccueilTelephonique) {
        this.qualiteAccueilTelephonique = qualiteAccueilTelephonique;
    }

    /**
     * @return the qualiteDelaiIntervention
     */
    public String getQualiteDelaiIntervention() {
        return qualiteDelaiIntervention;
    }

    /**
     * @param qualiteDelaiIntervention the qualiteDelaiIntervention to set
     */
    public void setQualiteDelaiIntervention(String qualiteDelaiIntervention) {
        this.qualiteDelaiIntervention = qualiteDelaiIntervention;
    }

    /**
     * @return the respectRendezVous
     */
    public String getRespectRendezVous() {
        return respectRendezVous;
    }

    /**
     * @param respectRendezVous the respectRendezVous to set
     */
    public void setRespectRendezVous(String respectRendezVous) {
        this.respectRendezVous = respectRendezVous;
    }

    /**
     * @return the qualiteIntervention
     */
    public String getQualiteIntervention() {
        return qualiteIntervention;
    }

    /**
     * @param qualiteIntervention the qualiteIntervention to set
     */
    public void setQualiteIntervention(String qualiteIntervention) {
        this.qualiteIntervention = qualiteIntervention;
    }

    /**
     * @return the satisfactionGlobale
     */
    public String getSatisfactionGlobale() {
        return satisfactionGlobale;
    }

    /**
     * @param satisfactionGlobale the satisfactionGlobale to set
     */
    public void setSatisfactionGlobale(String satisfactionGlobale) {
        this.satisfactionGlobale = satisfactionGlobale;
    }

    /**
     * @return the commentaire
     */
    public String getCommentaire() {
        return commentaire;
    }

    /**
     * @param commentaire the commentaire to set
     */
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
}
