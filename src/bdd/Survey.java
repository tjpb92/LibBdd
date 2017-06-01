package bdd;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Survey est une classe décrivant les résultats d'une enquête de satisfaction
 *
 * @version 0.17
 * @author Thierry Baribaud
 */
public class Survey {

    /**
     * Indique si l'enquête a été effectuée : Oui/Non
     */
    private String enqueteEffectuee = "Non";
    
    /**
     * Indique la qualité de l'accueil téléphonique
     */
    private String qualiteAccueilTelephonique = null;
    
    private String qualiteDelaiIntervention = null;
    private String respectRendezVous = null;
    private String qualiteIntervention = null;
    private String satisfactionGlobale = null;
    private String commentaire = null;

    /**
     * Constructeur de la classe Survey
     * @param connection une connexion à la base de données locale.
     * @param cnum référence à l'appel en cours.
     * @param c6int4 indique si l'enquête a été faite Oui=1, Non sinon
     * @param etatTicket état du ticket.
     * @throws SQLException SQLException en cas d'erreur SQL.
     * @throws ClassNotFoundException  en cas de classe non trouvée.
     */
    public Survey(Connection connection, int cnum, int c6int4, EtatTicket etatTicket) throws SQLException, ClassNotFoundException {
        FessaisDAO fessaisDAO;
        Fessais fessais;

        // L'enquête a-t-elle été effectuée ?
        if (c6int4 == 1) setEnqueteEffectuee("Oui");

        // Récupère la qualité de l'accueil téléphonique
        fessaisDAO = new FessaisDAO(connection, etatTicket);
        fessaisDAO.setLastTrialPreparedStatement(cnum, 201);
        fessais = fessaisDAO.getLastTrial();
        fessaisDAO.closeLastTrialPreparedStatement();
        if (fessais != null) {
            setQualiteAccueilTelephonique(fessais.getEmessage());
        }

        // Récupère la qualité du délai d'intervention
        fessaisDAO.setLastTrialPreparedStatement(cnum, 202);
        fessais = fessaisDAO.getLastTrial();
        fessaisDAO.closeLastTrialPreparedStatement();
        if (fessais != null) {
            setQualiteDelaiIntervention(fessais.getEmessage());
        }

        // Récupère le respect du rendez-vous
        fessaisDAO.setLastTrialPreparedStatement(cnum, 203);
        fessais = fessaisDAO.getLastTrial();
        fessaisDAO.closeLastTrialPreparedStatement();
        if (fessais != null) {
            setRespectRendezVous(fessais.getEmessage());
        }

        // Qualité de l'intervention
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
     * @return l'indication sur enquête effectuée ou non
     */
    public String getEnqueteEffectuee() {
        return enqueteEffectuee;
    }

    /**
     * @param enqueteEffectuee indique si l'enquête est effectuée ou non
     */
    public void setEnqueteEffectuee(String enqueteEffectuee) {
        this.enqueteEffectuee = enqueteEffectuee;
    }

    /**
     * @return la qualité de l'accueil téléphonique
     */
    public String getQualiteAccueilTelephonique() {
        return qualiteAccueilTelephonique;
    }

    /**
     * @param qualiteAccueilTelephonique définit la qualité de l'accueil téléphonique
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
