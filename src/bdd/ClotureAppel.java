package bdd;

import java.sql.Timestamp;

/**
 * ClotureAppel est une classe qui d�crit une cl�ture d'appel standard. Les
 * cl�tures d'appel sp�cifique h�riteront de cette classe.
 *
 * @version Juin 2016.
 * @author Thierry Baribaud.
 */
public class ClotureAppel {

    /**
     * Date de d�but d'intervention.
     */
    private Timestamp BegDate;

    /**
     * Date de fin d'intervention.
     */
    private Timestamp EndDate;

    /**
     * R�sultat de l'intervention.
     * <P>
     * Modalit�s : </P>
     * <UL><LI> 1 : "R�paration d�finitive",</LI>
     * <LI> 2 : "R�paration partielle",</LI>
     * <LI> 3 : "R�paration impossible",</LI>
     * <LI> 4 : "R�paration report�e en HO",</LI>
     * <LI> 98 : "Cloture admin - appel hors p�rim�tre",</LI>
     * <LI> 99 : "Cloture infructueuse malgr� relances ANSTEL".</LI></UL>
     */
    private String Resultat;

    /**
     * Nature de l'intervention.
     * <P>
     * Modalit�s : </P>
     * <UL><LI>1 : "Panne mat�riel",</LI>
     * <LI>2 : "Vandalisme",</LI>
     * <LI>3 : "Autre". </LI></UL>
     */
    private String Nature;

    /**
     * Rapport d'intervention.
     */
    private String Rapport;

    /**
     * Le technicien est-il encore sur site ?
     * <P>
     * Modalit�s : </P>
     * <UL><LI>1 : "Oui", </LI>
     * <LI>2 : "Non",  </LI>
     * <LI>3 : "Ne sait pas". </LI></UL>
     */
    private String OnSite;

    /**
     * @return BegDate la date de d�but d'intervention.
     */
    public Timestamp getBegDate() {
        return BegDate;
    }

    /**
     * @param BegDate d�finit la date de d�but d'intervention.
     */
    public void setBegDate(Timestamp BegDate) {
        this.BegDate = BegDate;
    }

    /**
     * @return EndDate la date de fin d'intervention.
     */
    public Timestamp getEndDate() {
        return EndDate;
    }

    /**
     * @param EndDate d�finit la date de fin d'intervention.
     */
    public void setEndDate(Timestamp EndDate) {
        this.EndDate = EndDate;
    }

    /**
     * @return Rapport le rapport d'intervention.
     */
    public String getRapport() {
        return Rapport;
    }

    /**
     * @param Rapport d�finit le rapport d'intervention.
     */
    public void setRapport(String Rapport) {
        this.Rapport = Rapport;
    }

    public ClotureAppel() {

    }

    /**
     * @return Resultat le r�sultat de l'intervention.
     */
    public String getResultat() {
        return Resultat;
    }

    /**
     * @param Resultat D�finit le r�sultat de l'intervention.
     */
    public void setResultat(String Resultat) {
        this.Resultat = Resultat;
    }

    /**
     * @return Nature la nature de l'intervention.
     */
    public String getNature() {
        return Nature;
    }

    /**
     * @param Nature d�finit la nature de l'intervention.
     */
    public void setNature(String Nature) {
        this.Nature = Nature;
    }

    /**
     * @return OnSite la localisation du technicien.
     */
    public String getOnSite() {
        return OnSite;
    }

    /**
     * @param OnSite d�finit la localisation du technicien.
     */
    public void setOnSite(String OnSite) {
        this.OnSite = OnSite;
    }
}
