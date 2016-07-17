package bdd;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * ClotureAppel est une classe qui décrit une clôture d'appel standard. Les
 * clôtures d'appel spécifiques hériteront de cette classe.
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
     * Heure de début d'intervention relevée.
     */
    private String HeureDebutRelevee;

    /**
     * Date de début d'intervention.
     */
    private Timestamp BegDate;

    /**
     * Heure de fin d'intervention relevée.
     */
    private String HeureFinRelevee;

    /**
     * Date de fin d'intervention.
     */
    private Timestamp EndDate;

    /**
     * Résultat de l'intervention.
     * <P>
     * Modalités : </P>
     * <UL><LI> 1 : "Réparation définitive",</LI>
     * <LI> 2 : "Réparation partielle",</LI>
     * <LI> 3 : "Réparation impossible",</LI>
     * <LI> 4 : "Réparation reportée en HO",</LI>
     * <LI> 98 : "Cloture admin - appel hors périmètre",</LI>
     * <LI> 99 : "Cloture infructueuse malgré relances ANSTEL".</LI></UL>
     */
    private String Resultat = "#N/A";

    /**
     * Nature de l'intervention.
     * <P>
     * Modalités : </P>
     * <UL><LI>1 : "Panne matériel",</LI>
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
     * Modalités : </P>
     * <UL><LI>1 : "Oui", </LI>
     * <LI>2 : "Non",  </LI>
     * <LI>3 : "Ne sait pas". </LI></UL>
     */
    private String OnSite = "#N/A";

    /**
     * Delai d'intervention exprimé en secondes.
     *
     * C'est l'écart entre l'heure de début d'intervention et l'heure de saisie
     * de la demande d'intervention.
     */
    private int delaiIntervention;

    /**
     * Durée d'intervention exprimé en secondes.
     *
     * C'est l'écart entre l'heure de fin et de début d'intervention.
     */
    private int dureeIntervention;

    /**
     * Constructeur de la classe clôture d'appel.
     *
     * @param DateSaisie date de saisie de la demande d'intervention.
     */
    public ClotureAppel(Timestamp DateSaisie) {
        this.DateSaisie = DateSaisie;
    }

    /**
     * @return BegDate la date de début d'intervention.
     */
    public Timestamp getBegDate() {
        return BegDate;
    }

    /**
     * @param BegDate définit la date de début d'intervention.
     */
    public void setBegDate(Timestamp BegDate) {
        this.BegDate = BegDate;
    }

    /**
     * @param MyFessais définit la date de début d'intervention à partir d'un
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
     * @param EndDate définit la date de fin d'intervention.
     */
    public void setEndDate(Timestamp EndDate) {
        this.EndDate = EndDate;
    }

    /**
     * @param MyFessais définit la date de fin d'intervention à partir d'un
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
     * @param MyFessais définit la date de début/fin d'intervention à partir
     * d'un essai.
     * 
     * @return date de début/fin d'intervention.
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
     * @param Rapport définit le rapport d'intervention.
     */
    public void setRapport(String Rapport) {
        this.Rapport = Rapport;
    }

    /**
     * @return Resultat le résultat de l'intervention.
     */
    public String getResultat() {
        return Resultat;
    }

    /**
     * @param Resultat Définit le résultat de l'intervention.
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
     * @param Nature définit la nature de l'intervention.
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
     * @param OnSite définit la localisation du technicien.
     */
    public void setOnSite(String OnSite) {
        this.OnSite = OnSite;
    }

    /**
     * @return HeureDebutRelevee l'heure de début d'intervention relevée.
     */
    public String getHeureDebutRelevee() {
        return HeureDebutRelevee;
    }

    /**
     * @param HeureDebutRelevee définit l'heure de début d'intervention relevée..
     */
    public void setHeureDebutRelevee(String HeureDebutRelevee) {
        this.HeureDebutRelevee = HeureDebutRelevee;
    }

    /**
     * @return HeureFinRelevee l'heure de début d'intervention relevée.
     */
    public String getHeureFinRelevee() {
        return HeureFinRelevee;
    }

    /**
     * @param HeureFinRelevee définit l'heure de début d'intervention relevée.
     */
    public void setHeureFinRelevee(String HeureFinRelevee) {
        this.HeureFinRelevee = HeureFinRelevee;
    }

    /**
     * Valide la clôture d'appel.
     * 
     * <OL><LI>Si la durée d'intervention est inférieure à une minute, alors
     * les heures de début et de fin n'ont pas été mises à jour lors de la saisie 
     * de la clôture. Dans ce cas, on les suppriment.</LI>
     * <LI> à compléter ...</LI></OL>
     */
    public void ValideLaCloture() {
        calculeDelaiIntervention();
        calculeDureeIntervention();
        
        // Règle n°1
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
     * Renvoie la clôture d'appel sous forme de texte.
     *
     * @return la clôture d'appel sous forme de texte.
     */
    @Override
    public String toString() {
        return (this.getClass().getName()
                + ":{début=" + getBegDate()
                + ", fin=" + getEndDate()
                + ", nature=" + getNature()
                + ", résultat=" + getResultat()
                + ", rapport=" + getRapport()
                + ", tech/site=" + getOnSite()
                + "}");
    }

    /**
     * @return delaiIntervention delai d'intervention exprimé en secondes.
     */
    public int getDelaiIntervention() {
        return delaiIntervention;
    }

    /**
     * @param delaiIntervention définit le delai d'intervention exprimé en
     * secondes.
     */
    public void setDelaiIntervention(int delaiIntervention) {
        this.delaiIntervention = delaiIntervention;
    }

    /**
     * @return dureeIntervention durée d'intervention exprimée en secondes.
     */
    public int getDureeIntervention() {
        return dureeIntervention;
    }

    /**
     * @param dureeIntervention définit la durée d'intervention exprimée en
     * secondes.
     */
    public void setDureeIntervention(int dureeIntervention) {
        this.dureeIntervention = dureeIntervention;
    }

    /**
     * Calcule la durée d'intervention exprimée en secondes.
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
     * @param DateSaisie définit la date de la demande d'intervention.
     */
    public void setDateSaisie(Timestamp DateSaisie) {
        this.DateSaisie = DateSaisie;
    }

    /**
     * Calcule le délai d'intervention exprimée en secondes.
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
