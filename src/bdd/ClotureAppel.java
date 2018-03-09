package bdd;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * ClotureAppel est une classe qui d�crit une cl�ture d'appel standard. Les
 * cl�tures d'appel sp�cifiques h�riteront de cette classe.
 *
 * @author Thierry Baribaud
 * @version 0.20
 */
public class ClotureAppel {

    /**
     * Format de date "aaaa-mm-jj".
     */
    private static final DateFormat Y4MMDD_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd ");

    /**
     * Format de date "jj/mm/aaaa".
     */
    private static final DateFormat DDMMY4_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy ");

    /**
     * Format d'heure "hh:mm".
     */
    private static final DateFormat HHMM_HOUR_FORMAT = new SimpleDateFormat("HH:mm");

    /**
     * Format d'heure "hh:mm:ss".
     */
    private static final DateFormat HHMMSS_HOUR_FORMAT = new SimpleDateFormat("HH:mm:ss");

    /**
     * Format "jj/mm/aaaa hh:mm:ss".
     */
    private static final DateTimeFormatter DDMMY4_HHMMSS = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");

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
     * Date d'intervention � d�faut de d�but/fin.
     */
    private Timestamp interventionDate = null;

    /**
     * Date d'intervention relev�e (format jj/mm/aaaa) (premier mod�le).
     */
    private String dateInterventionRelevee = null;

    /**
     * Heure d'intervention relev�e (format hh:mm:ss) (premier mod�le).
     */
    private String heureInterventionRelevee = null;

    /**
     * Date de d�but d'intervention relev�e (format jj/mm/aaaa, second mod�le).
     */
    private String dateDebutInterventionRelevee = null;

    /**
     * Heure de d�but d'intervention relev�e (format hh:mm:ss, second mod�le).
     */
    private String heureDebutInterventionRelevee = null;

    /**
     * Date de fin d'intervention relev�e (format jj/mm/aaaa, second mod�le).
     */
    private String dateFinInterventionRelevee = null;

    /**
     * Heure de fin d'intervention relev�e (format hh:mm:ss, second mod�le).
     */
    private String heureFinInterventionRelevee = null;

    /**
     * Date de la premi�re transmission ou prise en charge
     */
    private String datePremiereTransmission = null;

    /**
     * Heure de la premi�re transmission ou prise en charge
     */
    private String heurePremiereTransmission = null;

    /**
     * Date de la derni�re transmission ou prise en charge
     */
    private String dateDerniereTransmission = null;

    /**
     * Heure de la derni�re transmission ou prise en charge
     */
    private String heureDerniereTransmission = null;

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
     * D�lai d'intervention pour le donneur d'ordre (exprim� en secondes). C'est
     * l'�cart entre la derni�re prise en charge et la date des travaux.
     */
    private int delaiIntervention2;

    /**
     * Dur�e d'intervention exprim� en secondes.
     *
     * C'est l'�cart entre l'heure de fin et de d�but d'intervention.
     */
    private int dureeIntervention;

    /**
     * Cl�ture technique ou non
     */
    private boolean clotureTechnique = false;

    /**
     * Constructeur de la classe cl�ture d'appel.
     *
     * @param connection une connexion � la base de donn�es locale.
     * @param cnum r�f�rence � l'appel en cours.
     * @param dateSaisie date de saisie de l'appel en cours.
     * @param datePremiereTransmission date de premi�re transmission
     * @param heurePremiereTransnmission heure de premi�re transmission
     * @param dateDerniereTransmission date de derni�re transmission
     * @param heureDerniereTransnmission heure de derni�re transmission
     * @param etatTicket �tat du ticket.
     * @throws SQLException en cas d'erreur SQL.
     * @throws ClassNotFoundException en cas de classe non trouv�e.
     */
    public ClotureAppel(Connection connection, int cnum, Timestamp dateSaisie,
            String datePremiereTransmission, String heurePremiereTransnmission,
            String dateDerniereTransmission, String heureDerniereTransnmission,
            EtatTicket etatTicket) throws SQLException, ClassNotFoundException {
        FessaisDAO fessaisDAO;
        Fessais fessais;
        int egid;
        StringBuffer rapportIntervention;
        int eresult;
        String emessage;
        Timestamp begDate;
        Timestamp endDate;

        this.DateSaisie = dateSaisie;
        this.datePremiereTransmission = datePremiereTransmission;
        this.heurePremiereTransmission = heurePremiereTransnmission;
        this.dateDerniereTransmission = dateDerniereTransmission;
        this.heureDerniereTransmission = heureDerniereTransnmission;

        fessaisDAO = new FessaisDAO(connection, etatTicket);
        fessaisDAO.setPartOfEOMPreparedStatement(cnum);
        fessais = fessaisDAO.getPartOfEOM();
        fessaisDAO.closePartOfEOMPreparedStatement();
        if (fessais != null) {
            egid = fessais.getEgid();
//            System.out.println("    Une cl�ture d'appel trouv�e : egid=" + egid);

            // For debug purpose only (begin)
//            RapportIntervention = new StringBuffer("egid=" + egid);
            rapportIntervention = new StringBuffer();
            // For debug purpose only (end)

//            MyFessaisDAO = new FessaisDAO(MyConnection, MyEtatTicket);
            fessaisDAO.filterByGid(cnum, egid);
            fessaisDAO.setSelectPreparedStatement();
            while ((fessais = fessaisDAO.select()) != null) {
                eresult = fessais.getEresult();
                emessage = fessais.getEmessage();
//                System.out.println("      eresult=" + eresult + ", emessage=" + Emessage);
                switch (eresult) {
                    case 69:    // Heure de d�but d'intervention.
                        setBegDate(fessais);
                        break;
                    case 70:    // Heure de fin d'intervention.
                        setEndDate(fessais);
                        break;
                    case 71:    // R�sultat de l'intervention.
                        setResultat(emessage);
                        break;
                    case 72:    // Rapport d'intervention.
                        if (emessage.length() > 0) {
                            if (rapportIntervention.length() > 0) {
                                rapportIntervention.append(" ").append(emessage);
                            } else {
                                rapportIntervention.append(emessage);
                            }
                        }
                        break;
                    case 73:    // Le technicien est-il encore sur site ?
                        setOnSite(emessage);
                        break;
                    case 90:    // Date d'intervention relev�e
                        setDateInterventionRelevee(emessage);
                        break;
                    case 91:    // Heure d'intervention relev�e
                        setHeureInterventionRelevee(emessage);
                        break;
                    case 93:    // Nature de la panne.
                        setNature(emessage);
                        break;
                    case 101:    // Date de d�but d'intervention relev�e (second mod�le)
                        setDateDebutInterventionRelevee(emessage);
                        break;
                    case 102:    // Heure de d�but d'intervention relev�e (second mod�le)
                        setHeureDebutInterventionRelevee(emessage);
                        break;
                    case 103:    // Date de fin d'intervention relev�e (second mod�le)
                        setDateFinInterventionRelevee(emessage);
                        break;
                    case 104:    // Heure de fin d'intervention relev�e (second mod�le)
                        setHeureFinInterventionRelevee(emessage);
                        break;
                }
            }
            fessaisDAO.closeSelectPreparedStatement();

            // For debug purpose only (begin)
//            if ((MyBegDate = getBegDate()) != null) {
//                RapportIntervention.append(" d�but=").append(MyBegDate);
//            }
//            if ((MyEndDate = getEndDate()) != null) {
//                RapportIntervention.append(" fin=").append(MyEndDate);
//            }
            // For debug purpose only (end)
            if (rapportIntervention.length() > 0) {
                setRapport(rapportIntervention.toString());
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
        if (this.BegDate != null) {
            setHeureDebutRelevee(HHMM_HOUR_FORMAT.format(getBegDate()));
        } else {
            setHeureDebutRelevee(null);
        }
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
        if (this.EndDate != null) {
            setHeureFinRelevee(HHMM_HOUR_FORMAT.format(getEndDate()));
        } else {
            setHeureFinRelevee(null);
        }
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
                MyDate = Timestamp.valueOf(Y4MMDD_DATE_FORMAT.format(MyFessais.getEdate()) + MyHour + ".0");
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
     * @param resultat D�finit le r�sultat de l'intervention.
     */
    public void setResultat(String resultat) {
        this.Resultat = resultat;

        setClotureTechnique("R�paration d�finitive".equals(resultat)
                || "R�paration partielle".equals(resultat)
                || "R�paration impossible".equals(resultat)
                || "R�paration report�e en HO".equals(resultat)
        );
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
     * @return HeureDebutRelevee l'heure de d�but d'intervention relev�e
     * (premier mod�le).
     */
    public String getHeureDebutRelevee() {
        return HeureDebutRelevee;
    }

    /**
     * @param HeureDebutRelevee d�finit l'heure de d�but d'intervention relev�e
     * (premier mod�le).
     */
    public void setHeureDebutRelevee(String HeureDebutRelevee) {
        this.HeureDebutRelevee = HeureDebutRelevee;
    }

    /**
     * @return HeureFinRelevee l'heure de d�but d'intervention relev�e (premier
     * mod�le).
     */
    public String getHeureFinRelevee() {
        return HeureFinRelevee;
    }

    /**
     * @param HeureFinRelevee d�finit l'heure de d�but d'intervention relev�e
     * (premier mod�le).
     */
    public void setHeureFinRelevee(String HeureFinRelevee) {
        this.HeureFinRelevee = HeureFinRelevee;
    }

    /**
     * @return heureDebutInterventionRelevee l'heure de d�but d'intervention
     * relev�e (second mod�le).
     */
    public String getHeureDebutInterventionRelevee() {
        return heureDebutInterventionRelevee;
    }

    /**
     * @param heureDebutInterventionRelevee d�finit l'heure de d�but
     * d'intervention relev�e (second mod�le).
     */
    public void setHeureDebutInterventionRelevee(String heureDebutInterventionRelevee) {
        this.heureDebutInterventionRelevee = heureDebutInterventionRelevee;
    }

    /**
     * @return heureFinInterventionRelevee l'heure de d�but d'intervention
     * relev�e (second mod�le).
     */
    public String getHeureFinInterventionRelevee() {
        return heureFinInterventionRelevee;
    }

    /**
     * @param heureFinInterventionRelevee d�finit l'heure de d�but
     * d'intervention relev�e (second mod�le).
     */
    public void setHeureFinInterventionRelevee(String heureFinInterventionRelevee) {
        this.heureFinInterventionRelevee = heureFinInterventionRelevee;
    }

    /**
     * Valide la cl�ture d'appel.
     *
     * <OL><LI>Si la dur�e d'intervention est inf�rieure � une minute, alors les
     * heures de d�but et de fin n'ont pas �t� mises � jour lors de la saisie de
     * la cl�ture. Dans ce cas, on les supprime.</LI>
     * <LI> � compl�ter ...</LI></OL>
     */
    public void ValideLaCloture() {
        calculeDelaiIntervention();
        calculeDelaiIntervention2();
        calculeDureeIntervention();

        // R�gle n�1
        if (getDureeIntervention() < 60) {
            setHeureDebutRelevee(null);
            setBegDate((Timestamp) null);
            setHeureFinRelevee(null);
            setEndDate((Timestamp) null);
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
        return "Cloture:{"
                + "d�but=" + getBegDate()
                + ", fin=" + getEndDate()
                + ", dateIntervention=" + getInterventionDate()
                + ", nature=" + getNature()
                + ", r�sultat=" + getResultat()
                + ", rapport=" + getRapport()
                + ", tech/site=" + getOnSite()
                + ", clotureTechnique=" + isClotureTechnique()
                + "}";
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
     * Calcule le d�lai d'intervention (exprim� en secondes)
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

    /**
     * Calcule le d�lai d'intervention pour le donneur d'ordre (exprim� en
     * secondes)
     */
    public void calculeDelaiIntervention2() {
        DateTime premiereTansmission = null;
        DateTime derniereTransmission = null;
        DateTime dateDesTravaux = null;
        String hour;

        if (isClotureTechnique()) {
            if (datePremiereTransmission != null && heurePremiereTransmission != null) {
                try {
                    premiereTansmission = DDMMY4_HHMMSS.parseDateTime(datePremiereTransmission + " " + heurePremiereTransmission);
                } catch (org.joda.time.IllegalFieldValueException MyException) {
                    System.out.println("WARNING : date incorrecte PremiereTransmission="
                            + datePremiereTransmission + " " + heurePremiereTransmission
                            + ", raison : "
                            + MyException.getMessage());
                    premiereTansmission = null;
                }
            }

            if (dateDerniereTransmission != null && heureDerniereTransmission != null) {
                try {
                    derniereTransmission = DDMMY4_HHMMSS.parseDateTime(dateDerniereTransmission + " " + heureDerniereTransmission);
                } catch (org.joda.time.IllegalFieldValueException MyException) {
                    System.out.println("WARNING : date incorrecte DerniereTransmission="
                            + dateDerniereTransmission + " " + heureDerniereTransmission
                            + ", raison : "
                            + MyException.getMessage());
                    derniereTransmission = null;
                }
            }

            if (dateInterventionRelevee != null && heureInterventionRelevee != null) {
                if (heureInterventionRelevee.matches("[0-2][0-9]:[0-5][0-9]:[0-5][0-9]")) {
                    hour = heureInterventionRelevee;
                } else {
                    hour = "12:00:00";
                }
                try {
                    dateDesTravaux = DDMMY4_HHMMSS.parseDateTime(dateInterventionRelevee + " " + hour);
                } catch (org.joda.time.IllegalFieldValueException MyException) {
                    System.out.println("WARNING : date incorrecte InterventionRelevee="
                            + dateInterventionRelevee + " " + hour
                            + ", raison : "
                            + MyException.getMessage());
                    dateDesTravaux = null;
                }
            } else if (BegDate != null) {
                dateDesTravaux = new DateTime(BegDate.getTime());
            } else if (EndDate != null) {
                dateDesTravaux = new DateTime(EndDate.getTime());
            }

            if (dateDesTravaux != null && premiereTansmission != null) {
                if (dateDesTravaux.isAfter(premiereTansmission)) {
                    setDelaiIntervention2((int) (dateDesTravaux.getMillis() - premiereTansmission.getMillis()) / 1000);
                }
            }

            if (dateDesTravaux != null && derniereTransmission != null) {
                if (dateDesTravaux.isAfter(derniereTransmission)) {
                    setDelaiIntervention2((int) (dateDesTravaux.getMillis() - derniereTransmission.getMillis()) / 1000);
                }
            }

        }

    }

    /**
     * @return la date d'intervention
     */
    public Timestamp getInterventionDate() {
        return interventionDate;
    }

    /**
     * @param interventionDate d�finit la date d'intervention
     */
    public void setInterventionDate(Timestamp interventionDate) {
        this.interventionDate = interventionDate;
    }

    /**
     * @return la date d'intervention relev�e
     */
    public String getDateInterventionRelevee() {
        return dateInterventionRelevee;
    }

    /**
     * @return la date de d�but d'intervention relev�e
     */
    public String getDateDebutInterventionRelevee() {
        return dateDebutInterventionRelevee;
    }

    /**
     * @return la date de fin d'intervention relev�e
     */
    public String getDateFinInterventionRelevee() {
        return dateDebutInterventionRelevee;
    }

    /**
     * @param dateInterventionRelevee d�finit la date d'intervention relev�e
     */
    public void setDateInterventionRelevee(String dateInterventionRelevee) {
        this.dateInterventionRelevee = dateInterventionRelevee;
        setDateDebutInterventionRelevee(dateInterventionRelevee);
        setDateFinInterventionRelevee(dateInterventionRelevee);
    }

    /**
     * @param dateDebutInterventionRelevee d�finit la date de d�but
     * d'intervention relev�e
     */
    public void setDateDebutInterventionRelevee(String dateDebutInterventionRelevee) {
        this.dateDebutInterventionRelevee = dateDebutInterventionRelevee;
    }

    /**
     * @param dateFinInterventionRelevee d�finit la date de fin d'intervention
     * relev�e
     */
    public void setDateFinInterventionRelevee(String dateFinInterventionRelevee) {
        this.dateFinInterventionRelevee = dateFinInterventionRelevee;
    }

    /**
     * @return l'heure d'intervention relev�e
     */
    public String getHeureInterventionRelevee() {
        return heureInterventionRelevee;
    }

    /**
     * @param heureInterventionRelevee d�finit l'heure d'intervention relev�e
     */
    public void setHeureInterventionRelevee(String heureInterventionRelevee) {
        this.heureInterventionRelevee = heureInterventionRelevee;
        setHeureDebutInterventionRelevee(heureInterventionRelevee);
        setHeureFinInterventionRelevee(heureInterventionRelevee);
    }

    /**
     * @return s'il s'agit d'une cl�ture technique ou non
     */
    public boolean isClotureTechnique() {
        return clotureTechnique;
    }

    /**
     * @param clotureTechnique d�finit s'il s'agit d'une cl�ture technique ou
     * non
     */
    public void setClotureTechnique(boolean clotureTechnique) {
        this.clotureTechnique = clotureTechnique;
    }

    /**
     * @return la date de la premi�re transmission ou prise en charge
     */
    public String getDatePremiereTransmission() {
        return datePremiereTransmission;
    }

    /**
     * @param datePremiereTransmission d�finit la date de la premi�re
     * transmission ou prise en charge
     */
    public void setDatePremiereTransmission(String datePremiereTransmission) {
        this.datePremiereTransmission = datePremiereTransmission;
    }

    /**
     * @return l'heure de la premi�re transmission ou prise en charge
     */
    public String getHeurePremiereTransmission() {
        return heurePremiereTransmission;
    }

    /**
     * @param heurePremiereTransmission d�finit l'heure de la premi�re
     * transmission ou prise en charge
     */
    public void setHeurePremiereTransmission(String heurePremiereTransmission) {
        this.heurePremiereTransmission = heurePremiereTransmission;
    }

    /**
     * @return la date de la derni�re transmission ou prise en charge
     */
    public String getDateDerniereTransmission() {
        return dateDerniereTransmission;
    }

    /**
     * @param dateDerniereTransmission d�finit la date de la derni�re
     * transmission ou prise en charge
     */
    public void setDateDerniereTransmission(String dateDerniereTransmission) {
        this.dateDerniereTransmission = dateDerniereTransmission;
    }

    /**
     * @return l'heure de la derni�re transmission ou prise en charge
     */
    public String getHeureDerniereTransmission() {
        return heureDerniereTransmission;
    }

    /**
     * @param heureDerniereTransmission d�finit l'heure de la derni�re
     * transmission ou prise en charge
     */
    public void setHeureDerniereTransmission(String heureDerniereTransmission) {
        this.heureDerniereTransmission = heureDerniereTransmission;
    }

    /**
     * @return le d�lai d'intervention pour le donneur d'ordre (exprim� en
     * secondes).
     */
    public int getDelaiIntervention2() {
        return delaiIntervention2;
    }

    /**
     * @param delaiIntervention2 d�finit le d�lai d'intervention pour le donneur
     * d'ordre (exprim� en secondes).
     */
    public void setDelaiIntervention2(int delaiIntervention2) {
        this.delaiIntervention2 = delaiIntervention2;
    }

}
