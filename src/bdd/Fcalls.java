package bdd;

import java.sql.*;

/**
 * Fcalls est une classe d�crivant un appel.
 * @version Juin 2016
 * @author Thierry Baribaud
 */
public class Fcalls {

  /**
   * Identifiant de l'appel. 
   */
  private int cnum;

  /**
   * Identifiant du client (furgent). 
   */
  private int cunum;

  /**
   * Nom de l'appelant. 
   */
  private String Cname;

  /**
   * T�l�phone de l'appelant. 
   */
  private String Ctel;

  /**
   * Adresse de l'appelant. 
   */
  private String Caddress;

  /**
   * Compl�ment d'adresse de l'appelant. 
   */
  private String Caddress2;

  /**
   * Acc�s � l'adresse. 
   */
  private String Caccess;

  /**
   * Code postal. 
   */
  private String Cposcode;

  /**
   * Ville. 
   */
  private String City;

  /**
   * Description de l'appel. 
   */
  private String Csympt;

  /**
   * ... 
   */
  private String Cnumber4;

  /**
   * Identifiant du compl�ment d'appel (fcomplmt). 
   */
  private int cc6num;

  /**
   * Date de saisie d'appel. 
   */
  private Timestamp Cdate;

  /**
   * Heure de saisie d'appel. 
   */
  private String Ctime;

  /**
   * Date de la derni�re modification de l'appel. 
   */
  private Timestamp Cdate2;

  /**
   * Heure de la derni�re modification de l'appel. 
   */
  private String Ctime2;

  /**
   * Raison sociale de l'appelant. 
   */
  private String Corp;

  /**
   * ... 
   */
  private String Cnumber5;

  /**
   * Num�ro de ticket/appel. 
   */
  private int cseqno;

  /**
   * ... 
   */
  private int cquery1;

  /**
   * ... 
   */
  private int cquery2;

  /**
   * Zone d'astreinte ou identifiant de l'agence (fagency). 
   */
  private int czone;

  /**
   * Age du patient. 
   */
  private int cage;

  /**
   * Identifiant ou indice du type d'appel (ftype). 
   */
  private int ctype;

  /**
   * Identifiant de l'intervenant (ftoubib). 
   */
  private int ctnum;

  /**
   * Appel cl�tur� 1=OUI, 0=NON. 
   */
  private int cnote;

  /**
   * D�lai d'intervention exprim� en minute. 
   */
  private int cdelay1;

  /**
   * D�lai de remise en �tat exprim� en minute. 
   */
  private int cdelay2;

  /**
   * Dur�e de l'appel exprim�e en seconde. 
   */
  private int cduration;

  /**
   * Identifiant de l'op�rateur (foperat). 
   */
  private int conum;

  /**
   * ... 
   */
  private int ccallertype;

  /**
   * ... 
   */
  private String Cnumber6;

  /**
   * ... 
   */
  private String Cnumber7;

  /**
   * ... 
   */
  private String Cnumber8;

  /**
   * ... 
   */
  private String Cnumber9;

  /**
   * ... 
   */
  private String Cnumber10;

  /**
   * Code secteur astreinte jour. 
   */
  private String Csector1;

  /**
   * Code secteur astreinte nuit. 
   */
  private String Csector2;

  /**
   * Num�ro de ticket/appel chez le client. 
   */
  private String Cextnum;
  
  
  public int getCnum() {
    return cnum;
    }

  public int getCunum() {
    return cunum;
    }

  public Timestamp getCdate() {
    return Cdate;
    }

  public String getCname() {
    return Cname;
    }

  public String getCtel() {
    return Ctel;
    }

  public String getCaddress() {
    return Caddress;
    }

  public String getCaddress2() {
    return Caddress2;
    }

  public String getCaccess() {
    return Caccess;
    }

  public String getCposcode() {
    return Cposcode;
    }

  public String getCity() {
    return City;
    }

  public String getCsympt() {
    return Csympt;
    }

  public String getCtime() {
    return Ctime;
    }

  public String getCtime2() {
    return Ctime2;
    }

  public String getCorp() {
    return Corp;
    }

  public String getCnumber5() {
    return Cnumber5;
    }

  public String getCnumber4() {
    return Cnumber4;
    }

  public int getCc6num() {
    return cc6num;
    }

  public Timestamp getCdate2() {
    return Cdate2;
    }

  public void setCnum(int cnum) {
    this.cnum = cnum;
    }

  public void setCunum(int cunum) {
    this.cunum = cunum;
    }

  public void setCdate(Timestamp Cdate) {
    this.Cdate = Cdate;
    }

  public void setCtel(String Ctel) {
    this.Ctel = (Ctel != null)?Ctel.trim():null;
    }

  public void setCname(String Cname) {
    this.Cname = (Cname != null)?Cname.trim():null;
    }

  public void setCaddress(String Caddress) {
    this.Caddress = (Caddress != null)?Caddress.trim():null;
    }

  public void setCaddress2(String Caddress2) {
    this.Caddress2 = (Caddress2 != null)?Caddress2.trim():null;
    }

  public void setCaccess(String Caccess) {
    this.Caccess = (Caccess != null)?Caccess.trim():null;
    }

  public void setCposcode(String Cposcode) {
    this.Cposcode = (Cposcode != null)?Cposcode.trim():null;
    }

  public void setCity(String City) {
    this.City = (City != null)?City.trim():null;
    }

  public void setCsympt(String Csympt) {
    this.Csympt = (Csympt != null)?Csympt.trim():null;
    }

  public void setCtime(String Ctime) {
    this.Ctime = (Ctime != null)?Ctime.trim():null;
    }

  public void setCtime2(String Ctime2) {
    this.Ctime2 = (Ctime2 != null)?Ctime2.trim():null;
    }

  public void setCorp(String Corp) {
    this.Corp = (Corp != null)?Corp.trim():null;
    }

  public void setCnumber5(String Cnumber5) {
    this.Cnumber5 = (Cnumber5 != null)?Cnumber5.trim():null;
    }

  public void setCnumber4(String Cnumber4) {
    this.Cnumber4 = (Cnumber4 != null)?Cnumber4.trim():null;
    }

  public void setCc6num(int cc6num) {
    this.cc6num = cc6num;
    }

  public void setCdate2(Timestamp Cdate2) {
    this.Cdate2 = Cdate2;
    }

  @Override
  public String toString() {
    return this.getClass().getName() +
           " : {cnum=" + cnum + 
           ", cseqno=" + cseqno +
           ", cunum=" + cunum + 
           ", Cdate=" + Cdate +
           ", Ctime=" + Ctime + 
           ", Cname=" + Cname +
           ", Ctel=" + Ctel +
           ", Csympt=" + Csympt + 
           ", cc6num=" + cc6num +
//           ", Caddress=" + Caddress +
//           ", Caddress2=" + Caddress2 + 
//           ", Caccess=" + Caccess + 
//           ", Cposcode=" + Cposcode + 
//           ", City=" + City + 
//           ", Cnumber4=" + Cnumber4 + 
//           ", Cdate2=" + Cdate2 +
//           ", Ctime2=" + Ctime2 + 
//           ", Corp=" + Corp + 
//           ", Cnumber5=" + Cnumber5 + 
//           ", cquery1=" + cseqno +
//           ", cquery2=" + cseqno +
//           ", czone=" + cseqno +
//           ", cage=" + cseqno +
//           ", ctype=" + cseqno +
//           ", ctnum=" + cseqno +
//           ", cnote=" + cseqno +
           "}";
  }

    /**
     * @return cseqno num�ro de ticket Anstel.
     */
    public int getCseqno() {
        return cseqno;
    }

    /**
     * @param cseqno d�finit le num�ro de ticket Anstel
     */
    public void setCseqno(int cseqno) {
        this.cseqno = cseqno;
    }

    /**
     * @return cquery1 le r�sultat de la premi�re question param�trable.
     */
    public int getCquery1() {
        return cquery1;
    }

    /**
     * @param cquery1 d�finit le r�sultat de la premi�re question param�trable.
     */
    public void setCquery1(int cquery1) {
        this.cquery1 = cquery1;
    }

    /**
     * @return cquery2 le r�sultat de la seconde question param�trable.
     */
    public int getCquery2() {
        return cquery2;
    }

    /**
     * @param cquery2 d�finit le r�sultat de la seconde question param�trable.
     */
    public void setCquery2(int cquery2) {
        this.cquery2 = cquery2;
    }

    /**
     * @return czone la zone ou la r�f�rence � l'agence.
     */
    public int getCzone() {
        return czone;
    }

    /**
     * @param czone d�finit la zone ou la r�f�rence � l'agence.
     */
    public void setCzone(int czone) {
        this.czone = czone;
    }

    /**
     * @return cage l'age du patient.
     */
    public int getCage() {
        return cage;
    }

    /**
     * @param cage d�finit l'age du patient.
     */
    public void setCage(int cage) {
        this.cage = cage;
    }

    /**
     * @return ctype la raison d'appel.
     */
    public int getCtype() {
        return ctype;
    }

    /**
     * @param ctype d�finit  la raison d'appel.
     */
    public void setCtype(int ctype) {
        this.ctype = ctype;
    }

    /**
     * @return ctnum la r�f�rence � l'intervenant courant.
     */
    public int getCtnum() {
        return ctnum;
    }

    /**
     * @param ctnum d�finit  la r�f�rence � l'intervenant courant.
     */
    public void setCtnum(int ctnum) {
        this.ctnum = ctnum;
    }

    /**
     * @return cnote l'�tat de cl�ture de l'appel (0=Non, 1=Oui).
     */
    public int getCnote() {
        return cnote;
    }

    /**
     * @param cnote d�finit l'�tat de cl�ture de l'appel (0=Non, 1=Oui).
     */
    public void setCnote(int cnote) {
        this.cnote = cnote;
    }

    /**
     * @return the cdelay1
     */
    public int getCdelay1() {
        return cdelay1;
    }

    /**
     * @param cdelay1 the cdelay1 to set
     */
    public void setCdelay1(int cdelay1) {
        this.cdelay1 = cdelay1;
    }

    /**
     * @return the cdelay2
     */
    public int getCdelay2() {
        return cdelay2;
    }

    /**
     * @param cdelay2 the cdelay2 to set
     */
    public void setCdelay2(int cdelay2) {
        this.cdelay2 = cdelay2;
    }

    /**
     * @return the cduration
     */
    public int getCduration() {
        return cduration;
    }

    /**
     * @param cduration the cduration to set
     */
    public void setCduration(int cduration) {
        this.cduration = cduration;
    }

    /**
     * @return the conum
     */
    public int getConum() {
        return conum;
    }

    /**
     * @param conum the conum to set
     */
    public void setConum(int conum) {
        this.conum = conum;
    }

    /**
     * @return the ccallertype
     */
    public int getCcallertype() {
        return ccallertype;
    }

    /**
     * @param ccallertype the ccallertype to set
     */
    public void setCcallertype(int ccallertype) {
        this.ccallertype = ccallertype;
    }

    /**
     * @return the Cnumber6
     */
    public String getCnumber6() {
        return Cnumber6;
    }

    /**
     * @param Cnumber6 the Cnumber6 to set
     */
    public void setCnumber6(String Cnumber6) {
        this.Cnumber6 = (Cnumber6 != null)?Cnumber6.trim():null;
    }

    /**
     * @return the Cnumber7
     */
    public String getCnumber7() {
        return Cnumber7;
    }

    /**
     * @param Cnumber7 the Cnumber7 to set
     */
    public void setCnumber7(String Cnumber7) {
        this.Cnumber7 = (Cnumber7 != null)?Cnumber7.trim():null;
    }

    /**
     * @return the Cnumber8
     */
    public String getCnumber8() {
        return Cnumber8;
    }

    /**
     * @param Cnumber8 the Cnumber8 to set
     */
    public void setCnumber8(String Cnumber8) {
        this.Cnumber8 = (Cnumber8 != null)?Cnumber8.trim():null;
    }

    /**
     * @return the Cnumber9
     */
    public String getCnumber9() {
        return Cnumber9;
    }

    /**
     * @param Cnumber9 the Cnumber9 to set
     */
    public void setCnumber9(String Cnumber9) {
        this.Cnumber9 = (Cnumber9 != null)?Cnumber9.trim():null;
    }

    /**
     * @return the Cnumber10
     */
    public String getCnumber10() {
        return Cnumber10;
    }

    /**
     * @param Cnumber10 the Cnumber10 to set
     */
    public void setCnumber10(String Cnumber10) {
        this.Cnumber10 = (Cnumber10 != null)?Cnumber10.trim():null;
    }

    /**
     * @return the Csector1
     */
    public String getCsector1() {
        return Csector1;
    }

    /**
     * @param Csector1 the Csector1 to set
     */
    public void setCsector1(String Csector1) {
        this.Csector1 = (Csector1 != null)?Csector1.trim():null;
    }

    /**
     * @return the Csector2
     */
    public String getCsector2() {
        return Csector2;
    }

    /**
     * @param Csector2 the Csector2 to set
     */
    public void setCsector2(String Csector2) {
        this.Csector2 = (Csector2 != null)?Csector2.trim():null;
    }

    /**
     * @return the Cextnum
     */
    public String getCextnum() {
        return Cextnum;
    }

    /**
     * @param Cextnum the Cextnum to set
     */
    public void setCextnum(String Cextnum) {
        this.Cextnum = (Cextnum != null)?Cextnum.trim():null;
    }
}
