package bdd;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Survey est une classe décrivant les résultats d'une enquête de satisfaction
 *
 * @version 0.19
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

    /**
     * Indique la qualité du délai d'intervention
     */
    private String qualiteDelaiIntervention = null;

    /**
     * Indique la qualité du respect du rendez-vous
     */
    private String respectRendezVous = null;

    /**
     * Indique la qualité de l'intervention
     */
    private String qualiteIntervention = null;

    /**
     * Indique le niveau de satisfaction globale du client
     */
    private String satisfactionGlobale = null;

    /**
     * Commentaire optionnel indiqué par le client
     */
    private String commentaire = null;

    /**
     * Indique le mois du jour de réalisation de l'enquête de satisfaction
     */
    private String moisDeLEnquete = null;

    /**
     * Constructeur de la classe Survey
     *
     * @param connection une connexion à la base de données locale.
     * @param cnum référence à l'appel en cours.
     * @param c6int4 indique si l'enquête a été faite Oui=1, Non sinon
     * @param etatTicket état du ticket.
     * @throws SQLException SQLException en cas d'erreur SQL.
     * @throws ClassNotFoundException en cas de classe non trouvée.
     */
    public Survey(Connection connection, int cnum, int c6int4, EtatTicket etatTicket) throws SQLException, ClassNotFoundException {
        FessaisDAO fessaisDAO;
        Fessais fessais;
        Timestamp edate = null;
        DateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.FRENCH);

        // L'enquête a-t-elle été effectuée ?
        if (c6int4 == 1) {
            setEnqueteEffectuee("Oui");
        }

        // Récupère la qualité de l'accueil téléphonique
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

        // Récupère la qualité du délai d'intervention
        fessaisDAO.setLastTrialPreparedStatement(cnum, 202);
        fessais = fessaisDAO.getLastTrial();
        fessaisDAO.closeLastTrialPreparedStatement();
        if (fessais != null) {
            setQualiteDelaiIntervention(fessais.getEmessage());
            if (edate == null) {
                edate = fessais.getEdate();
            }
        }

        // Récupère le respect du rendez-vous
        fessaisDAO.setLastTrialPreparedStatement(cnum, 203);
        fessais = fessaisDAO.getLastTrial();
        fessaisDAO.closeLastTrialPreparedStatement();
        if (fessais != null) {
            setRespectRendezVous(fessais.getEmessage());
            if (edate == null) {
                edate = fessais.getEdate();
            }
        }

        // Qualité de l'intervention
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
     * @param qualiteAccueilTelephonique définit la qualité de l'accueil
     * téléphonique
     */
    public void setQualiteAccueilTelephonique(String qualiteAccueilTelephonique) {
        this.qualiteAccueilTelephonique = qualiteAccueilTelephonique;
    }

    /**
     * @return la qualité de l'intervention
     */
    public String getQualiteDelaiIntervention() {
        return qualiteDelaiIntervention;
    }

    /**
     * @param qualiteDelaiIntervention définit la qualité de l'intervention
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
     * @param respectRendezVous définit le respect du rendez-vous
     */
    public void setRespectRendezVous(String respectRendezVous) {
        this.respectRendezVous = respectRendezVous;
    }

    /**
     * @return la qualité de l'intervention
     */
    public String getQualiteIntervention() {
        return qualiteIntervention;
    }

    /**
     * @param qualiteIntervention définit la qualité de l'intervention
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
     * @param satisfactionGlobale définit la satisfaction globale du client
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
     * @param commentaire définit le commentaire du client
     */
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    /**
     * Retourne le mois du jour de réalisation de l'enquête de satisfaction
     *
     * @return Retourne le mois du jour de réalisation de l'enquête de
     * satisfaction
     */
    public String getMoisDeLEnquete() {
        return moisDeLEnquete;
    }

    /**
     * Définit le mois du jour de réalisation de l'enquête de satisfaction
     *
     * @param moisDeLEnquete le mois du jour de réalisation de l'enquête de
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
