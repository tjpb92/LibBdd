package bdd;

/**
 * Ftoubib est une classe décrivant un intervenant.
 *
 * @author Thierry Baribaud
 * @version 0.16
 */
public class Ftoubib {

    /**
     * Identifiant de l'intervenant.
     */
    private int tnum;

    /**
     * Référence au service d'urgence (furgent).
     */
    private int tunum;

    /**
     * Référence à l'agence (fagency).
     */
    private int ta6num;

    /**
     * Référence à l'activité (factivity).
     */
    private int ta4num;

    /**
     * Nom de l'intervenant.
     */
    private String Tlname;

    /**
     * Prénom de l'intervenant.
     */
    private String Tfname;

    /**
     * Matricule de l'intervenant.
     */
    private String Tabbname;

    /**
     * Premier numéro de téléphone de l'intervenant.
     */
    private String Tel;

    /**
     * Second numéro de téléphone de l'intervenant.
     */
    private String Tel2;

    /**
     * Troisième numéro de téléphone de l'intervenant.
     */
    private String Telper;

    /**
     * Quatrième numéro de téléphone de l'intervenant.
     */
    private String tel4;

    /**
     * Coinquième numéro de téléphone de l'intervenant.
     */
    private String Tel5;

    /**
     * Sixième numéro de téléphone de l'intervenant.
     */
    private String Tel6;

    /**
     * Numéro de télécopieur de l'intervenant.
     */
    private String Telfax;

    /**
     * Adresse électronique de l'intervenant.
     */
    private String Temail;

    /**
     * Consigne fixe n°1 de l'intervenant.
     */
    private String Taddress;

    /**
     * Consigne fixe n°2 de l'intervenant.
     */
    private String Taddress2;

    /**
     * Consigne temporaire de l'intervenant.
     */
    private String Tcomment;

    /**
     * Délai d'intervention contractuel exprimé en minute
     */
    private int tdelay1;

    /**
     * Délai de remise en état contractuel exprimé en minute
     */
    private int tdelay2;

    /**
     * Identifiant Performance Immo
     */
    private String TUuid;

    /**
     * @return tnum l'identifiant de l'intervenant.
     */
    public int getTnum() {
        return tnum;
    }

    /**
     * @param tnum définit l'identifiant de l'intervenant.
     */
    public void setTnum(int tnum) {
        this.tnum = tnum;
    }

    /**
     * @return tunum l'identifiant du client.
     */
    public int getTunum() {
        return tunum;
    }

    /**
     * @param tunum déinit l'identifiant du client.
     */
    public void setTunum(int tunum) {
        this.tunum = tunum;
    }

    /**
     * @return ta6num l'identifiant de l'agence.
     */
    public int getTa6num() {
        return ta6num;
    }

    /**
     * @param ta6num définit l'identifiant de l'agence.
     */
    public void setTa6num(int ta6num) {
        this.ta6num = ta6num;
    }

    /**
     * @return ta4num l'identifiant de l'activité de l'intervenant.
     */
    public int getTa4num() {
        return ta4num;
    }

    /**
     * @param ta4num définit l'identifiant de l'activité de l'intervenant.
     */
    public void setTa4num(int ta4num) {
        this.ta4num = ta4num;
    }

    /**
     * @return Tlname le nom de l'intervenant.
     */
    public String getTlname() {
        return Tlname;
    }

    /**
     * @param Tlname définit le nom de l'intervenant.
     */
    public void setTlname(String Tlname) {
        this.Tlname = (Tlname != null) ? Tlname.trim() : null;
    }

    /**
     * @return Tfname le prénom de l'intervenant.
     */
    public String getTfname() {
        return Tfname;
    }

    /**
     * @param Tfname définit le prénom de l'intervenant.
     */
    public void setTfname(String Tfname) {
        this.Tfname = (Tfname != null) ? Tfname.trim() : null;
    }

    /**
     * @return Tabbname le matricule de l'intervenant.
     */
    public String getTabbname() {
        return Tabbname;
    }

    /**
     * @param Tabbname définit le matricule de l'intervenant.
     */
    public void setTabbname(String Tabbname) {
        this.Tabbname = (Tabbname != null) ? Tabbname.trim() : null;
    }

    /**
     * @return Tel le téléphone n°1 de l'intervenant.
     */
    public String getTel() {
        return Tel;
    }

    /**
     * @param Tel définit le téléphone n°1 de l'intervenant.
     */
    public void setTel(String Tel) {
        this.Tel = (Tel != null) ? Tel.trim() : null;
    }

    /**
     * @return Tel2 le téléphone n°2 de l'intervenant.
     */
    public String getTel2() {
        return Tel2;
    }

    /**
     * @param Tel2 définit le téléphone n°2 de l'intervenant.
     */
    public void setTel2(String Tel2) {
        this.Tel2 = (Tel2 != null) ? Tel2.trim() : null;
    }

    /**
     * @return Telper le téléphone n°3 de l'intervenant.
     */
    public String getTelper() {
        return Telper;
    }

    /**
     * @param Telper définit le téléphone n°3 de l'intervenant.
     */
    public void setTelper(String Telper) {
        this.Telper = (Telper != null) ? Telper.trim() : null;
    }

    /**
     * @return tel4 le téléphone n°4 de l'intervenant.
     */
    public String getTel4() {
        return tel4;
    }

    /**
     * @param tel4 définit le téléphone n°4 de l'intervenant.
     */
    public void setTel4(String tel4) {
        this.tel4 = (tel4 != null) ? tel4.trim() : null;
    }

    /**
     * @return Tel5 le téléphone n°5 de l'intervenant.
     */
    public String getTel5() {
        return Tel5;
    }

    /**
     * @param Tel5 définit le téléphone n°5 de l'intervenant.
     */
    public void setTel5(String Tel5) {
        this.Tel5 = (Tel5 != null) ? Tel5.trim() : null;
    }

    /**
     * @return Tel6 le téléphone n°6 de l'intervenant.
     */
    public String getTel6() {
        return Tel6;
    }

    /**
     * @param Tel6 définit le téléphone n°6 de l'intervenant.
     */
    public void setTel6(String Tel6) {
        this.Tel6 = (Tel6 != null) ? Tel6.trim() : null;
    }

    /**
     * @return Telfax le télécopieur de l'intervenant.
     */
    public String getTelfax() {
        return Telfax;
    }

    /**
     * @param Telfax définit le télécopieur de l'intervenant.
     */
    public void setTelfax(String Telfax) {
        this.Telfax = (Telfax != null) ? Telfax.trim() : null;
    }

    /**
     * @return Temail l'email de l'intervenant.
     */
    public String getTemail() {
        return Temail;
    }

    /**
     * @param Temail définit l'email de l'intervenant.
     */
    public void setTemail(String Temail) {
        this.Temail = (Temail != null) ? Temail.trim() : null;
    }

    /**
     * @return Taddress la consigne fixe n°1 de l'intervenant.
     */
    public String getTaddress() {
        return Taddress;
    }

    /**
     * @param Taddress définit la consigne fixe n°1 de l'intervenant.
     */
    public void setTaddress(String Taddress) {
        this.Taddress = (Taddress != null) ? Taddress.trim() : null;
    }

    /**
     * @return Taddress2 la consigne fixe n°2 de l'intervenant.
     */
    public String getTaddress2() {
        return Taddress2;
    }

    /**
     * @param Taddress2 définit la consigne fixe n°2 de l'intervenant.
     */
    public void setTaddress2(String Taddress2) {
        this.Taddress2 = (Taddress2 != null) ? Taddress2.trim() : null;
    }

    /**
     * @return Tcomment la consigne temporaire de l'intervenant.
     */
    public String getTcomment() {
        return Tcomment;
    }

    /**
     * @param Tcomment définit la consigne temporaire de l'intervenant.
     */
    public void setTcomment(String Tcomment) {
        this.Tcomment = (Tcomment != null) ? Tcomment.trim() : null;
    }

    /**
     * @return l'identifiant Performance Immo
     */
    public String getTUuid() {
        return TUuid;
    }

    /**
     * @param TUuid définit l'identifiant Performance Immo
     */
    public void setTUuid(String TUuid) {
        this.TUuid = (TUuid != null) ? TUuid.trim() : null;
    }

    /**
     * @return le délai d'intervention contractuel exprimé en minute
     */
    public int getTdelay1() {
        return tdelay1;
    }

    /**
     * @param tdelay1 définit le délai d'intervention contractuel exprimé en
     * minute
     */
    public void setTdelay1(int tdelay1) {
        this.tdelay1 = tdelay1;
    }

    /**
     * @return le délai de remise en état contractuel exprimé en minute
     */
    public int getTdelay2() {
        return tdelay2;
    }

    /**
     * @param tdelay2 définit le délai de remise en état contractuel exprimé en
     * minute
     */
    public void setTdelay2(int tdelay2) {
        this.tdelay2 = tdelay2;
    }

    /**
     * Retourne l'intervenant sous forme textuelle
     *
     * @return l'intervenant sous forme textuelle
     */
    @Override
    public String toString() {
        return "Ftoubib:{tnum=" + tnum
                + ", tuuid=" + TUuid
                + ", tunum=" + tunum
                + ", Tlname=" + Tlname
                + ", Tfname=" + Tfname
                + ", Tabbname=" + Tabbname
                + "}";
    }
}
