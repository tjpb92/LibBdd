package bdd;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * ClotureAppel est une classe qui d�crit une cl�ture d'appel standard. Les
 * cl�tures d'appel sp�cifiques h�riteront de cette classe.
 *
 * @version Juillet 2016
 * @author Thierry Baribaud
 */
public class ClotureAppel {

    /**
     * Format de date "aaaa-mm-dd".
     */
    private static final DateFormat MyDateFormat = new SimpleDateFormat("yyyy-MM-dd ");

    /**
     * Format d'heure "hh:mm".
     */
    private static final DateFormat MyHourFormat = new SimpleDateFormat("hh:mm ");

    /**
     * Date de saisie de la demande d'intervetion.
     */
    private Timestamp DateSaisie;

    /**
     * Heure de d�but d'intervention relev�e.
     */
    private String HeureDebutRelevee;

    /**
     * Date de d�but d'intervention.
     */
    private Timestamp BegDate;

    /**
     * Heure de fin d'intervention relev�e.
     */
    private String HeureFinRelevee;

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
    private String Resultat = "#N/A";

    /**
     * Nature de l'intervention.
     * <P>
     * Modalit�s : </P>
     * <UL><LI>1 : "Panne mat�riel",</LI>
     * <LI>2 : "Vandalisme",</LI>
     * <LI>3 : "Autre". </LI></UL>
     */
    private String Nature = "#N/A";

    /**
     * Rapport d'intervention.
     */
    private String Rapport = "#N/A";

    /**
     * Le technicien est-il encore sur site ?
     * <P>
     * Modalit�s : </P>
     * <UL><LI>1 : "Oui", </LI>
     * <LI>2 : "Non",  </LI>
     * <LI>3 : "Ne sait pas". </LI></UL>
     */
    private String OnSite = "#N/A";

    /**
     * Delai d'intervention exprim� en secondes.
     *
     * C'est l'�cart entre l'heure de d�but d'intervention et l'heure de saisie
     * de la demande d'intervention.
     */
    private int delaiIntervention;

    /**
     * Dur�e d'intervention exprim� en secondes.
     *
     * C'est l'�cart entre l'heure de fin et de d�but d'intervention.
     */
    private int dureeIntervention;

    /**
     * Constructeur de la classe cl�ture d'appel.
     *
     * @param DateSaisie date de saisie de la demande d'intervention.
     */
    public ClotureAppel(Timestamp DateSaisie) {
        this.DateSaisie = DateSaisie;
    }

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
     * @param MyFessais d�finit la date de d�but d'intervention � partir d'un
     * essai.
     */
    public void setBegDate(Fessais MyFessais) {
        this.BegDate = calcDate(MyFessais);
        
        // ATTENTION : faire mieux plus tard, TB, le 17/07/2016.
        if (this.BegDate != null)
            setHeureDebutRelevee(MyHourFormat.format(getBegDate()));
        else
            setHeureDebutRelevee(null);
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
     * @param MyFessais d�finit la date de fin d'intervention � partir d'un
     * essai.
     */
    public void setEndDate(Fessais MyFessais) {
        this.EndDate = calcDate(MyFessais);
        
        // ATTENTION : faire mieux plus tard, TB, le 17/07/2016.
        if (this.EndDate != null)
            setHeureFinRelevee(MyHourFormat.format(getEndDate()));
        else
            setHeureFinRelevee(null);
    }

    /**
     * @param MyFessais d�finit la date de d�but/fin d'intervention � partir
     * d'un essai.
     * 
     * @return date de d�but/fin d'intervention.
     */
    private Timestamp calcDate(Fessais MyFessais) {
        String Emessage;
//        char c;
        String MyHour;
        Timestamp MyDate;

        MyDate = null;
        Emessage = MyFessais.getEmessage();
        if (Emessage.length() > 0) {
//            c = Emessage.charAt(0);
//            if (c >= '0' && c <= '9') {
            if (Emessage.matches("[0-2][0-9]:[0-5][0-9]")) {
                MyHour = Emessage + ":00";
                MyDate = Timestamp.valueOf(MyDateFormat.format(MyFessais.getEdate()) + MyHour + ".0");
                if (MyHour.compareTo(MyFessais.getEtime()) == 1) {
                    MyDate.setTime(MyDate.getTime() - 86400000);
                }
            }
        }
        return (MyDate);
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

    /**
     * @return HeureDebutRelevee l'heure de d�but d'intervention relev�e.
     */
    public String getHeureDebutRelevee() {
        return HeureDebutRelevee;
    }

    /**
     * @param HeureDebutRelevee d�finit l'heure de d�but d'intervention relev�e..
     */
    public void setHeureDebutRelevee(String HeureDebutRelevee) {
        this.HeureDebutRelevee = HeureDebutRelevee;
    }

    /**
     * @return HeureFinRelevee l'heure de d�but d'intervention relev�e.
     */
    public String getHeureFinRelevee() {
        return HeureFinRelevee;
    }

    /**
     * @param HeureFinRelevee d�finit l'heure de d�but d'intervention relev�e.
     */
    public void setHeureFinRelevee(String HeureFinRelevee) {
        this.HeureFinRelevee = HeureFinRelevee;
    }

    /**
     * Valide la cl�ture d'appel.
     * 
     * <OL><LI>Si la dur�e d'intervention est inf�rieure � une minute, alors
     * les heures de d�but et de fin n'ont pas �t� mises � jour lors de la saisie 
     * de la cl�ture. Dans ce cas, on les suppriment.</LI>
     * <LI> � compl�ter ...</LI></OL>
     */
    public void ValideLaCloture() {
        calculeDelaiIntervention();
        calculeDureeIntervention();
        
        // R�gle n�1
        if (getDureeIntervention() < 60) {
            setHeureDebutRelevee(null);
            setBegDate((Timestamp)null);
            setHeureFinRelevee(null);
            setEndDate((Timestamp)null);
            calculeDelaiIntervention();
            calculeDureeIntervention();
        }
    }
    
    /**
     * Renvoie la cl�ture d'appel sous forme de texte.
     *
     * @return la cl�ture d'appel sous forme de texte.
     */
    @Override
    public String toString() {
        return (this.getClass().getName()
                + ":{d�but=" + getBegDate()
                + ", fin=" + getEndDate()
                + ", nature=" + getNature()
                + ", r�sultat=" + getResultat()
                + ", rapport=" + getRapport()
                + ", tech/site=" + getOnSite()
                + "}");
    }

    /**
     * @return delaiIntervention delai d'intervention exprim� en secondes.
     */
    public int getDelaiIntervention() {
        return delaiIntervention;
    }

    /**
     * @param delaiIntervention d�finit le delai d'intervention exprim� en
     * secondes.
     */
    public void setDelaiIntervention(int delaiIntervention) {
        this.delaiIntervention = delaiIntervention;
    }

    /**
     * @return dureeIntervention dur�e d'intervention exprim�e en secondes.
     */
    public int getDureeIntervention() {
        return dureeIntervention;
    }

    /**
     * @param dureeIntervention d�finit la dur�e d'intervention exprim�e en
     * secondes.
     */
    public void setDureeIntervention(int dureeIntervention) {
        this.dureeIntervention = dureeIntervention;
    }

    /**
     * Calcule la dur�e d'intervention exprim�e en secondes.
     */
    public void calculeDureeIntervention() {
        Timestamp MyBegDate;
        Timestamp MyEndDate;
        long myLongBegDate = 0;
        long myLongEndDate = 0;

        if ((MyBegDate = getBegDate()) != null) {
            myLongBegDate = MyBegDate.getTime();
        }
        if ((MyEndDate = getEndDate()) != null) {
            myLongEndDate = MyEndDate.getTime();
        }
        setDureeIntervention((int) Math.abs(myLongEndDate - myLongBegDate) / 1000);
    }

    /**
     * @return DateSaisie la date de saisie de la demande d'intervention.
     */
    public Timestamp getDateSaisie() {
        return DateSaisie;
    }

    /**
     * @param DateSaisie d�finit la date de la demande d'intervention.
     */
    public void setDateSaisie(Timestamp DateSaisie) {
        this.DateSaisie = DateSaisie;
    }

    /**
     * Calcule le d�lai d'intervention exprim�e en secondes.
     */
    public void calculeDelaiIntervention() {
        Timestamp MyDateSaisie;
        Timestamp MyBegDate;
        long myLongDateSaisie = 0;
        long myLongBegDate = 0;

        if ((MyDateSaisie = getDateSaisie()) != null) {
            myLongDateSaisie = MyDateSaisie.getTime();
        }
        if ((MyBegDate = getBegDate()) != null) {
            myLongBegDate = MyBegDate.getTime();
        }
        setDelaiIntervention((int) Math.abs(myLongBegDate - myLongDateSaisie) / 1000);
    }

}
