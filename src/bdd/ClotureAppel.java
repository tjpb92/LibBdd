package bdd;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

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
    private static final DateFormat MyHourFormat = new SimpleDateFormat("HH:mm");

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
     * @param MyConnection une connexion � la base de donn�es locale.
     * @param cnum r�f�rence � l'appel en cours.
     * @param DateSaisie date de saisie de l'appel en cours.
     * @param MyEtatTicket �tat du ticket.
     * @throws SQLException en cas d'erreur SQL.
     * @throws ClassNotFoundException en cas de classe non trouv�e.
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
//            System.out.println("    Une cl�ture d'appel trouv�e : egid=" + egid);

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
                    case 69:    // Heure de d�but d'intervention.
                        setBegDate(MyFessais);
                        break;
                    case 70:    // Heure de fin d'intervention.
                        setEndDate(MyFessais);
                        break;
                    case 71:    // R�sultat de l'intervention.
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
                RapportIntervention.append(" d�but=").append(MyBegDate);
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
//            System.out.println("  (apr�s validation) " + MyClotureAppel);
        
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
