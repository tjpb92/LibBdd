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
 * ClotureAppel est une classe qui décrit une clôture d'appel standard. Les
 * clôtures d'appel spécifiques hériteront de cette classe.
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
     * Date d'intervention à défaut de début/fin.
     */
    private Timestamp interventionDate = null;

    /**
     * Date d'intervention relevée (format jj/mm/aaaa) (premier modèle).
     */
    private String dateInterventionRelevee = null;

    /**
     * Heure d'intervention relevée (format hh:mm:ss) (premier modèle).
     */
    private String heureInterventionRelevee = null;

    /**
     * Date de début d'intervention relevée (format jj/mm/aaaa, second modèle).
     */
    private String dateDebutInterventionRelevee = null;

    /**
     * Heure de début d'intervention relevée (format hh:mm:ss, second modèle).
     */
    private String heureDebutInterventionRelevee = null;

    /**
     * Date de fin d'intervention relevée (format jj/mm/aaaa, second modèle).
     */
    private String dateFinInterventionRelevee = null;

    /**
     * Heure de fin d'intervention relevée (format hh:mm:ss, second modèle).
     */
    private String heureFinInterventionRelevee = null;

    /**
     * Date de la première transmission ou prise en charge
     */
    private String datePremiereTransmission = null;

    /**
     * Heure de la première transmission ou prise en charge
     */
    private String heurePremiereTransmission = null;

    /**
     * Date de la dernière transmission ou prise en charge
     */
    private String dateDerniereTransmission = null;

    /**
     * Heure de la dernière transmission ou prise en charge
     */
    private String heureDerniereTransmission = null;

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
     * Délai d'intervention pour le donneur d'ordre (exprimé en secondes). C'est
     * l'écart entre la dernière prise en charge et la date des travaux.
     */
    private int delaiIntervention2;

    /**
     * Durée d'intervention exprimé en secondes.
     *
     * C'est l'écart entre l'heure de fin et de début d'intervention.
     */
    private int dureeIntervention;

    /**
     * Clôture technique ou non
     */
    private boolean clotureTechnique = false;

    /**
     * Constructeur de la classe clôture d'appel.
     *
     * @param connection une connexion à la base de données locale.
     * @param cnum référence à l'appel en cours.
     * @param dateSaisie date de saisie de l'appel en cours.
     * @param datePremiereTransmission date de première transmission
     * @param heurePremiereTransnmission heure de première transmission
     * @param dateDerniereTransmission date de dernière transmission
     * @param heureDerniereTransnmission heure de dernière transmission
     * @param etatTicket état du ticket.
     * @throws SQLException en cas d'erreur SQL.
     * @throws ClassNotFoundException en cas de classe non trouvée.
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
//            System.out.println("    Une clôture d'appel trouvée : egid=" + egid);

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
                    case 69:    // Heure de début d'intervention.
                        setBegDate(fessais);
                        break;
                    case 70:    // Heure de fin d'intervention.
                        setEndDate(fessais);
                        break;
                    case 71:    // Résultat de l'intervention.
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
                    case 90:    // Date d'intervention relevée
                        setDateInterventionRelevee(emessage);
                        break;
                    case 91:    // Heure d'intervention relevée
                        setHeureInterventionRelevee(emessage);
                        break;
                    case 93:    // Nature de la panne.
                        setNature(emessage);
                        break;
                    case 101:    // Date de début d'intervention relevée (second modèle)
                        setDateDebutInterventionRelevee(emessage);
                        break;
                    case 102:    // Heure de début d'intervention relevée (second modèle)
                        setHeureDebutInterventionRelevee(emessage);
                        break;
                    case 103:    // Date de fin d'intervention relevée (second modèle)
                        setDateFinInterventionRelevee(emessage);
                        break;
                    case 104:    // Heure de fin d'intervention relevée (second modèle)
                        setHeureFinInterventionRelevee(emessage);
                        break;
                }
            }
            fessaisDAO.closeSelectPreparedStatement();

            // For debug purpose only (begin)
//            if ((MyBegDate = getBegDate()) != null) {
//                RapportIntervention.append(" début=").append(MyBegDate);
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
        if (this.EndDate != null) {
            setHeureFinRelevee(HHMM_HOUR_FORMAT.format(getEndDate()));
        } else {
            setHeureFinRelevee(null);
        }
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
     * @param resultat Définit le résultat de l'intervention.
     */
    public void setResultat(String resultat) {
        this.Resultat = resultat;

        setClotureTechnique("Réparation définitive".equals(resultat)
                || "Réparation partielle".equals(resultat)
                || "Réparation impossible".equals(resultat)
                || "Réparation reportée en HO".equals(resultat)
        );
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
     * @return HeureDebutRelevee l'heure de début d'intervention relevée
     * (premier modèle).
     */
    public String getHeureDebutRelevee() {
        return HeureDebutRelevee;
    }

    /**
     * @param HeureDebutRelevee définit l'heure de début d'intervention relevée
     * (premier modèle).
     */
    public void setHeureDebutRelevee(String HeureDebutRelevee) {
        this.HeureDebutRelevee = HeureDebutRelevee;
    }

    /**
     * @return HeureFinRelevee l'heure de début d'intervention relevée (premier
     * modèle).
     */
    public String getHeureFinRelevee() {
        return HeureFinRelevee;
    }

    /**
     * @param HeureFinRelevee définit l'heure de début d'intervention relevée
     * (premier modèle).
     */
    public void setHeureFinRelevee(String HeureFinRelevee) {
        this.HeureFinRelevee = HeureFinRelevee;
    }

    /**
     * @return heureDebutInterventionRelevee l'heure de début d'intervention
     * relevée (second modèle).
     */
    public String getHeureDebutInterventionRelevee() {
        return heureDebutInterventionRelevee;
    }

    /**
     * @param heureDebutInterventionRelevee définit l'heure de début
     * d'intervention relevée (second modèle).
     */
    public void setHeureDebutInterventionRelevee(String heureDebutInterventionRelevee) {
        this.heureDebutInterventionRelevee = heureDebutInterventionRelevee;
    }

    /**
     * @return heureFinInterventionRelevee l'heure de début d'intervention
     * relevée (second modèle).
     */
    public String getHeureFinInterventionRelevee() {
        return heureFinInterventionRelevee;
    }

    /**
     * @param heureFinInterventionRelevee définit l'heure de début
     * d'intervention relevée (second modèle).
     */
    public void setHeureFinInterventionRelevee(String heureFinInterventionRelevee) {
        this.heureFinInterventionRelevee = heureFinInterventionRelevee;
    }

    /**
     * Valide la clôture d'appel.
     *
     * <OL><LI>Si la durée d'intervention est inférieure à une minute, alors les
     * heures de début et de fin n'ont pas été mises à jour lors de la saisie de
     * la clôture. Dans ce cas, on les supprime.</LI>
     * <LI> à compléter ...</LI></OL>
     */
    public void ValideLaCloture() {
        calculeDelaiIntervention();
        calculeDelaiIntervention2();
        calculeDureeIntervention();

        // Règle n°1
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
     * Renvoie la clôture d'appel sous forme de texte.
     *
     * @return la clôture d'appel sous forme de texte.
     */
    @Override
    public String toString() {
        return "Cloture:{"
                + "début=" + getBegDate()
                + ", fin=" + getEndDate()
                + ", dateIntervention=" + getInterventionDate()
                + ", nature=" + getNature()
                + ", résultat=" + getResultat()
                + ", rapport=" + getRapport()
                + ", tech/site=" + getOnSite()
                + ", clotureTechnique=" + isClotureTechnique()
                + "}";
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
     * Calcule le délai d'intervention (exprimé en secondes)
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
     * Calcule le délai d'intervention pour le donneur d'ordre (exprimé en
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
     * @param interventionDate définit la date d'intervention
     */
    public void setInterventionDate(Timestamp interventionDate) {
        this.interventionDate = interventionDate;
    }

    /**
     * @return la date d'intervention relevée
     */
    public String getDateInterventionRelevee() {
        return dateInterventionRelevee;
    }

    /**
     * @return la date de début d'intervention relevée
     */
    public String getDateDebutInterventionRelevee() {
        return dateDebutInterventionRelevee;
    }

    /**
     * @return la date de fin d'intervention relevée
     */
    public String getDateFinInterventionRelevee() {
        return dateDebutInterventionRelevee;
    }

    /**
     * @param dateInterventionRelevee définit la date d'intervention relevée
     */
    public void setDateInterventionRelevee(String dateInterventionRelevee) {
        this.dateInterventionRelevee = dateInterventionRelevee;
        setDateDebutInterventionRelevee(dateInterventionRelevee);
        setDateFinInterventionRelevee(dateInterventionRelevee);
    }

    /**
     * @param dateDebutInterventionRelevee définit la date de début
     * d'intervention relevée
     */
    public void setDateDebutInterventionRelevee(String dateDebutInterventionRelevee) {
        this.dateDebutInterventionRelevee = dateDebutInterventionRelevee;
    }

    /**
     * @param dateFinInterventionRelevee définit la date de fin d'intervention
     * relevée
     */
    public void setDateFinInterventionRelevee(String dateFinInterventionRelevee) {
        this.dateFinInterventionRelevee = dateFinInterventionRelevee;
    }

    /**
     * @return l'heure d'intervention relevée
     */
    public String getHeureInterventionRelevee() {
        return heureInterventionRelevee;
    }

    /**
     * @param heureInterventionRelevee définit l'heure d'intervention relevée
     */
    public void setHeureInterventionRelevee(String heureInterventionRelevee) {
        this.heureInterventionRelevee = heureInterventionRelevee;
        setHeureDebutInterventionRelevee(heureInterventionRelevee);
        setHeureFinInterventionRelevee(heureInterventionRelevee);
    }

    /**
     * @return s'il s'agit d'une clôture technique ou non
     */
    public boolean isClotureTechnique() {
        return clotureTechnique;
    }

    /**
     * @param clotureTechnique définit s'il s'agit d'une clôture technique ou
     * non
     */
    public void setClotureTechnique(boolean clotureTechnique) {
        this.clotureTechnique = clotureTechnique;
    }

    /**
     * @return la date de la première transmission ou prise en charge
     */
    public String getDatePremiereTransmission() {
        return datePremiereTransmission;
    }

    /**
     * @param datePremiereTransmission définit la date de la première
     * transmission ou prise en charge
     */
    public void setDatePremiereTransmission(String datePremiereTransmission) {
        this.datePremiereTransmission = datePremiereTransmission;
    }

    /**
     * @return l'heure de la première transmission ou prise en charge
     */
    public String getHeurePremiereTransmission() {
        return heurePremiereTransmission;
    }

    /**
     * @param heurePremiereTransmission définit l'heure de la première
     * transmission ou prise en charge
     */
    public void setHeurePremiereTransmission(String heurePremiereTransmission) {
        this.heurePremiereTransmission = heurePremiereTransmission;
    }

    /**
     * @return la date de la dernière transmission ou prise en charge
     */
    public String getDateDerniereTransmission() {
        return dateDerniereTransmission;
    }

    /**
     * @param dateDerniereTransmission définit la date de la dernière
     * transmission ou prise en charge
     */
    public void setDateDerniereTransmission(String dateDerniereTransmission) {
        this.dateDerniereTransmission = dateDerniereTransmission;
    }

    /**
     * @return l'heure de la dernière transmission ou prise en charge
     */
    public String getHeureDerniereTransmission() {
        return heureDerniereTransmission;
    }

    /**
     * @param heureDerniereTransmission définit l'heure de la dernière
     * transmission ou prise en charge
     */
    public void setHeureDerniereTransmission(String heureDerniereTransmission) {
        this.heureDerniereTransmission = heureDerniereTransmission;
    }

    /**
     * @return le délai d'intervention pour le donneur d'ordre (exprimé en
     * secondes).
     */
    public int getDelaiIntervention2() {
        return delaiIntervention2;
    }

    /**
     * @param delaiIntervention2 définit le délai d'intervention pour le donneur
     * d'ordre (exprimé en secondes).
     */
    public void setDelaiIntervention2(int delaiIntervention2) {
        this.delaiIntervention2 = delaiIntervention2;
    }

}
