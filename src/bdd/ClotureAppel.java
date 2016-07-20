package bdd;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

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
    private static final DateFormat MyHourFormat = new SimpleDateFormat("HH:mm");

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
    private String Resultat;

    /**
     * Nature de l'intervention.
     * <P>
     * Modalités : </P>
     * <UL><LI>1 : "Panne matériel",</LI>
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
     * Modalités : </P>
     * <UL><LI>1 : "Oui", </LI>
     * <LI>2 : "Non",  </LI>
     * <LI>3 : "Ne sait pas". </LI></UL>
     */
    private String OnSite;

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
     * @param MyConnection une connexion à la base de données locale.
     * @param cnum référence à l'appel en cours.
     * @param DateSaisie date de saisie de l'appel en cours.
     * @param MyEtatTicket état du ticket.
     * @throws SQLException en cas d'erreur SQL.
     * @throws ClassNotFoundException en cas de classe non trouvée.
     */
    public ClotureAppel(Connection MyConnection, int cnum, Timestamp DateSaisie, EtatTicket MyEtatTicket) throws SQLException, ClassNotFoundException {
        FessaisDAO MyFessaisDAO;
        Fessais MyFessais;
        int egid;
        StringBuffer RapportIntervention;
        int eresult;
        String Emessage;
        Timestamp MyBegDate;
        Timestamp MyEndDate;
        
        this.DateSaisie = DateSaisie;

        MyFessaisDAO = new FessaisDAO(MyConnection, MyEtatTicket);
        MyFessaisDAO.setPartOfEOMPreparedStatement(cnum);
        MyFessais = MyFessaisDAO.getPartOfEOM();
        MyFessaisDAO.closePartOfEOMPreparedStatement();
        if (MyFessais != null) {
            egid = MyFessais.getEgid();
//            System.out.println("    Une clôture d'appel trouvée : egid=" + egid);

            // For debug purpose only (begin)
            RapportIntervention = new StringBuffer("egid=" + egid);
            // For debug purpose only (end)

//            MyFessaisDAO = new FessaisDAO(MyConnection, MyEtatTicket);
            MyFessaisDAO.filterByGid(cnum, egid);
            MyFessaisDAO.setSelectPreparedStatement();
            while ((MyFessais = MyFessaisDAO.select()) != null) {
                eresult = MyFessais.getEresult();
                Emessage = MyFessais.getEmessage();
//                System.out.println("      eresult=" + eresult + ", emessage=" + Emessage);
                switch (eresult) {
                    case 69:    // Heure de début d'intervention.
                        setBegDate(MyFessais);
                        break;
                    case 70:    // Heure de fin d'intervention.
                        setEndDate(MyFessais);
                        break;
                    case 71:    // Résultat de l'intervention.
                        setResultat(Emessage);
                        break;
                    case 72:    // Rapport d'intervention.
                        if (Emessage.length() > 0) {
                            if (RapportIntervention.length() > 0) {
                                RapportIntervention.append(" ").append(Emessage);
                            } else {
                                RapportIntervention.append(Emessage);
                            }
                        }
                        break;
                    case 73:    // Le technicien est-il encore sur site ?
                        setOnSite(Emessage);
                        break;
                    case 93:    // Nature de la panne.
                        setNature(Emessage);
                        break;
                }
            }
            MyFessaisDAO.closeSelectPreparedStatement();

            // For debug purpose only (begin)
            if ((MyBegDate = getBegDate()) != null) {
                RapportIntervention.append(" début=").append(MyBegDate);
            }
            if ((MyEndDate = getEndDate()) != null) {
                RapportIntervention.append(" fin=").append(MyEndDate);
            }
            // For debug purpose only (end)

            if (RapportIntervention.length() > 0) {
                setRapport(RapportIntervention.toString());
            }
//            System.out.println("  (avant validation) " + MyClotureAppel);

        }
        ValideLaCloture();
//            System.out.println("  (après validation) " + MyClotureAppel);
        
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
        String MyHour;
        Timestamp MyDate;

        MyDate = null;
        Emessage = MyFessais.getEmessage();
        if (Emessage.length() > 0) {
            if (Emessage.matches("[0-2][0-9]:[0-5][0-9]")) {
                MyHour = Emessage + ":00";
                MyDate = Timestamp.valueOf(MyDateFormat.format(MyFessais.getEdate()) + MyHour + ".0");
//                System.out.printf("    MyHOur="+MyHour+", Etime="+MyFessais.getEtime()+",compareTo="+MyHour.compareTo(MyFessais.getEtime()));
                if (MyHour.compareTo(MyFessais.getEtime()) > 0) {
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
