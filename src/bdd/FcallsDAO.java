package bdd;

import com.informix.jdbc.IfmxStatement;
import java.sql.*;

/**
 * Classe qui décrit les méthodes pour accéder aux tables fcalls ou f99calls au
 * travers de JDBC.
 *
 * @author Thierry Baribaud
 * @version 0.13
 */
public class FcallsDAO extends PatternDAO {

    /**
     * Table à gérér : fcalls ou f99calls.
     */
    private String MyTable = "fcalls";

    /**
     * Indice de la date de début d'extraction.
     */
    private int idxBegDate = 0;

    /**
     * Indice de la date de fin d'extraction.
     */
    private int idxEndDate = 0;

    /**
     * Date de début d'extraction.
     */
    private Timestamp BegDate = null;

    /**
     * Date de fin d'extraction.
     */
    private Timestamp EndDate = null;

    /**
     * Constructeur de la classe FcallsDAO.
     *
     * @param MyConnection connexion à la base de données courante.
     * @param MyEtatTicket indique si l'on travaille sur les tickets en cours ou
     * archivés.
     * @throws ClassNotFoundException en cas de classe non trouvée.
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public FcallsDAO(Connection MyConnection, EtatTicket MyEtatTicket)
            throws ClassNotFoundException, SQLException {

        MyTable = EtatTicket.EN_COURS.equals(MyEtatTicket) ? "fcalls" : "f99calls";

        this.MyConnection = MyConnection;

        setInvariableSelectStatement("select cnum, cunum, cname, ctel, caddress, caddress2,"
                + " caccess, cposcode, city, csympt,"
                + " cnumber4, cc6num, cdate, ctime, cdate2, ctime2,"
                + " corp, cnumber5, cseqno, cquery1, cquery2, czone, cage, ctype,"
                + " ctnum, cnote,"
                + " cdelay1, cdelay2, cduration, conum, ccallertype,"
                + " cnumber6, cnumber7, cnumber8, cnumber9, cnumber10,"
                + " csector1, csector2, cextnum, iso8601_weeknum(cdate) cweeknum,"
                + " curglevel, cuuid"
                + " from " + MyTable
                + " where (cinternal = 0 or cinternal is null)"
                + " and (ctest = 0 or ctest is null)");
//        if (cnum > 0) {
//            Stmt.append(" and cnum = ").append(cnum);
//        }
//        if (cunum > 0) {
//            Stmt.append(" and cunum = ").append(cunum);
//        }
//        if (BegDate != null) {
//            Stmt.append(" and cdate >= ?");
//            idx++;
//            idxBegDate = idx;
//        }
//        if (EndDate != null) {
//            Stmt.append(" and cdate < ?");
//            idx++;
//            idxEndDate = idx;
//        }
//        Stmt.append(" order by cdate, ctime;");
////        System.out.println(Stmt);
//        setSelectStatement(Stmt.toString());
//        setSelectPreparedStatement();
//        if (idxBegDate > 0) {
//            SelectPreparedStatement.setTimestamp(idxBegDate, BegDate);
//        }
//        if (idxEndDate > 0) {
//            SelectPreparedStatement.setTimestamp(idxEndDate, EndDate);
//        }
//        setSelectResultSet();

        setUpdateStatement("update " + MyTable
                + " set cunum=?, cname=?, ctel=?, caddress=?, caddress2=?,"
                + " caccess=?, cposcode=?, city=?, csympt=?,"
                + " cnumber4=?, cc6num=?, cdate=?, ctime=?, cdate2=?, ctime2=?,"
                + " corp=?, cnumber5=?, cseqno=?, cquery1=?, cquery2=?,"
                + " czone=?, cage=?, ctype=?, ctnum=?, cnote=?,"
                + " cdelay1=?, cdelay2=?, cduration=?, conum=?, ccallertype=?,"
                + " cnumber6=?, cnumber7=?, cnumber8=?, cnumber9=?, cnumber10=?,"
                + " csector1=?, csector2=?, cextnum=?,"
                + " curglevel=?, cuuid=?"
                + " where cnum=?;");
//        setUpdatePreparedStatement();

        setInsertStatement("insert into " + MyTable
                + " (cunum, cname, ctel, caddress, caddress2,"
                + " caccess, cposcode, city, csympt,"
                + " cnumber4, cc6num, cdate, ctime, cdate2, ctime2,"
                + " corp, cnumber5, cseqno, cquery1, cquery2, czone,"
                + " cage, ctype, ctnum, cnote,"
                + " cdelay1, cdelay2, cduration, conum, ccallertype,"
                + " cnumber6, cnumber7, cnumber8, cnumber9, cnumber10,"
                + " csector1, csector2, cextnum,"
                + " curglevel, cuuid"
                + ")"
                + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
                + " ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
//        setInsertPreparedStatement();

        setDeleteStatement("delete from " + MyTable + " where cnum=?;");
//        setDeletePreparedStatement();
    }

    /**
     * Selectionne un appel.
     *
     * @return l'appel sélectionné.
     */
    @Override
    public Fcalls select() {
        Fcalls MyFcalls = null;

        try {
            if (SelectResultSet.next()) {
                MyFcalls = new Fcalls();
                MyFcalls.setCnum(SelectResultSet.getInt("cnum"));
                MyFcalls.setCunum(SelectResultSet.getInt("cunum"));
                MyFcalls.setCname(SelectResultSet.getString("cname"));
                MyFcalls.setCtel(SelectResultSet.getString("ctel"));
                MyFcalls.setCaddress(SelectResultSet.getString("caddress"));
                MyFcalls.setCaddress2(SelectResultSet.getString("caddress2"));
                MyFcalls.setCaccess(SelectResultSet.getString("caccess"));
                MyFcalls.setCposcode(SelectResultSet.getString("cposcode"));
                MyFcalls.setCity(SelectResultSet.getString("city"));
                MyFcalls.setCsympt(SelectResultSet.getString("csympt"));
                MyFcalls.setCnumber4(SelectResultSet.getString("cnumber4"));
                MyFcalls.setCc6num(SelectResultSet.getInt("cc6num"));
                MyFcalls.setCdate(SelectResultSet.getTimestamp("cdate"));
                MyFcalls.setCtime(SelectResultSet.getString("ctime"));
                MyFcalls.setCdate2(SelectResultSet.getTimestamp("cdate2"));
                MyFcalls.setCtime2(SelectResultSet.getString("ctime2"));
                MyFcalls.setCorp(SelectResultSet.getString("corp"));
                MyFcalls.setCnumber5(SelectResultSet.getString("cnumber5"));
                MyFcalls.setCseqno(SelectResultSet.getInt("cseqno"));
                MyFcalls.setCquery1(SelectResultSet.getInt("cquery1"));
                MyFcalls.setCquery2(SelectResultSet.getInt("cquery2"));
                MyFcalls.setCzone(SelectResultSet.getInt("czone"));
                MyFcalls.setCage(SelectResultSet.getInt("cage"));
                MyFcalls.setCtype(SelectResultSet.getInt("ctype"));
                MyFcalls.setCtnum(SelectResultSet.getInt("ctnum"));
                MyFcalls.setCnote(SelectResultSet.getInt("cnote"));
                MyFcalls.setCdelay1(SelectResultSet.getInt("cdelay1"));
                MyFcalls.setCdelay2(SelectResultSet.getInt("cdelay2"));
                MyFcalls.setCduration(SelectResultSet.getInt("cduration"));
                MyFcalls.setConum(SelectResultSet.getInt("conum"));
                MyFcalls.setCcallertype(SelectResultSet.getInt("ccallertype"));
                MyFcalls.setCnumber6(SelectResultSet.getString("cnumber6"));
                MyFcalls.setCnumber7(SelectResultSet.getString("cnumber7"));
                MyFcalls.setCnumber8(SelectResultSet.getString("cnumber8"));
                MyFcalls.setCnumber9(SelectResultSet.getString("cnumber9"));
                MyFcalls.setCnumber10(SelectResultSet.getString("cnumber10"));
                MyFcalls.setCsector1(SelectResultSet.getString("csector1"));
                MyFcalls.setCsector2(SelectResultSet.getString("csector2"));
                MyFcalls.setCextnum(SelectResultSet.getString("cextnum"));
                MyFcalls.setCWeekNum(SelectResultSet.getString("cweeknum"));
                MyFcalls.setCUrgLevel(SelectResultSet.getString("curglevel"));
                MyFcalls.setCUuid(SelectResultSet.getString("cuuid"));
            } else {
                System.out.println("Lecture de " + MyTable + " terminée");
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur en lecture de " + MyTable + " "
                    + MyException.getMessage());
        }
        return MyFcalls;
    }

    /**
     * Met à jour un appel.
     *
     * @param MyFcalls appel à mettre à jour.
     */
    public void update(Fcalls MyFcalls) {
        try {
            UpdatePreparedStatement.setInt(1, MyFcalls.getCunum());
            UpdatePreparedStatement.setString(2, MyFcalls.getCname());
            UpdatePreparedStatement.setString(3, MyFcalls.getCtel());
            UpdatePreparedStatement.setString(4, MyFcalls.getCaddress());
            UpdatePreparedStatement.setString(5, MyFcalls.getCaddress2());
            UpdatePreparedStatement.setString(6, MyFcalls.getCaccess());
            UpdatePreparedStatement.setString(7, MyFcalls.getCposcode());
            UpdatePreparedStatement.setString(8, MyFcalls.getCity());
            UpdatePreparedStatement.setString(9, MyFcalls.getCsympt());
            UpdatePreparedStatement.setString(10, MyFcalls.getCnumber4());
            UpdatePreparedStatement.setInt(11, MyFcalls.getCc6num());
            UpdatePreparedStatement.setTimestamp(12, MyFcalls.getCdate());
            UpdatePreparedStatement.setString(13, MyFcalls.getCtime());
            UpdatePreparedStatement.setTimestamp(14, MyFcalls.getCdate2());
            UpdatePreparedStatement.setString(15, MyFcalls.getCtime2());
            UpdatePreparedStatement.setString(16, MyFcalls.getCorp());
            UpdatePreparedStatement.setString(17, MyFcalls.getCnumber5());
            UpdatePreparedStatement.setInt(18, MyFcalls.getCseqno());
            UpdatePreparedStatement.setInt(19, MyFcalls.getCquery1());
            UpdatePreparedStatement.setInt(20, MyFcalls.getCquery2());
            UpdatePreparedStatement.setInt(21, MyFcalls.getCzone());
            UpdatePreparedStatement.setInt(22, MyFcalls.getCage());
            UpdatePreparedStatement.setInt(23, MyFcalls.getCtype());
            UpdatePreparedStatement.setInt(24, MyFcalls.getCtnum());
            UpdatePreparedStatement.setInt(25, MyFcalls.getCnote());
            UpdatePreparedStatement.setInt(26, MyFcalls.getCdelay1());
            UpdatePreparedStatement.setInt(27, MyFcalls.getCdelay2());
            UpdatePreparedStatement.setInt(28, MyFcalls.getCduration());
            UpdatePreparedStatement.setInt(29, MyFcalls.getConum());
            UpdatePreparedStatement.setInt(30, MyFcalls.getCcallertype());
            UpdatePreparedStatement.setString(31, MyFcalls.getCnumber6());
            UpdatePreparedStatement.setString(32, MyFcalls.getCnumber7());
            UpdatePreparedStatement.setString(33, MyFcalls.getCnumber8());
            UpdatePreparedStatement.setString(34, MyFcalls.getCnumber9());
            UpdatePreparedStatement.setString(35, MyFcalls.getCnumber10());
            UpdatePreparedStatement.setString(36, MyFcalls.getCsector1());
            UpdatePreparedStatement.setString(37, MyFcalls.getCsector2());
            UpdatePreparedStatement.setString(38, MyFcalls.getCextnum());
            UpdatePreparedStatement.setString(39, MyFcalls.getCUrgLevel());
            UpdatePreparedStatement.setString(40, MyFcalls.getCUuid());
            UpdatePreparedStatement.setInt(41, MyFcalls.getCnum());
            setNbAffectedRow(UpdatePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de mettre à jour " + MyTable);
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de la mise à jour de " + MyTable
                    + " " + MyException.getMessage());
        }
    }

    /**
     * Supprime définitivement un appel.
     *
     * @param cnum identiant de l'appel à supprimer.
     */
    @Override
    public void delete(int cnum) {
        try {
            DeletePreparedStatement.setInt(1, cnum);
            setNbAffectedRow(DeletePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de détruire un appel dans " + MyTable);
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de la suppression d'un appel dans "
                    + MyTable + " " + MyException.getMessage());
        }
    }

    /**
     * Ajoute un appel dans la table fcalls ou f99calls.
     *
     * @param MyFcalls appel à ajouter à la table fcalls ou f99calls.
     */
    public void insert(Fcalls MyFcalls) {
//        ResultSet MyKeyResultSet;

        try {
            System.out.println("cname=" + MyFcalls.getCname());
            InsertPreparedStatement.setInt(1, MyFcalls.getCunum());
            InsertPreparedStatement.setString(2, MyFcalls.getCname());
            InsertPreparedStatement.setString(3, MyFcalls.getCtel());
            InsertPreparedStatement.setString(4, MyFcalls.getCaddress());
            InsertPreparedStatement.setString(5, MyFcalls.getCaddress2());
            InsertPreparedStatement.setString(6, MyFcalls.getCaccess());
            InsertPreparedStatement.setString(7, MyFcalls.getCposcode());
            InsertPreparedStatement.setString(8, MyFcalls.getCity());
            InsertPreparedStatement.setString(9, MyFcalls.getCsympt());
            InsertPreparedStatement.setString(10, MyFcalls.getCnumber4());
            InsertPreparedStatement.setInt(11, MyFcalls.getCc6num());
            InsertPreparedStatement.setTimestamp(12, MyFcalls.getCdate());
            InsertPreparedStatement.setString(13, MyFcalls.getCtime());
            InsertPreparedStatement.setTimestamp(14, MyFcalls.getCdate2());
            InsertPreparedStatement.setString(15, MyFcalls.getCtime2());
            InsertPreparedStatement.setString(16, MyFcalls.getCorp());
            InsertPreparedStatement.setString(17, MyFcalls.getCnumber5());
            InsertPreparedStatement.setInt(18, MyFcalls.getCseqno());
            InsertPreparedStatement.setInt(19, MyFcalls.getCquery1());
            InsertPreparedStatement.setInt(20, MyFcalls.getCquery2());
            InsertPreparedStatement.setInt(21, MyFcalls.getCzone());
            InsertPreparedStatement.setInt(22, MyFcalls.getCage());
            InsertPreparedStatement.setInt(23, MyFcalls.getCtype());
            InsertPreparedStatement.setInt(24, MyFcalls.getCtnum());
            InsertPreparedStatement.setInt(25, MyFcalls.getCnote());
            InsertPreparedStatement.setInt(26, MyFcalls.getCdelay1());
            InsertPreparedStatement.setInt(27, MyFcalls.getCdelay2());
            InsertPreparedStatement.setInt(28, MyFcalls.getCduration());
            InsertPreparedStatement.setInt(29, MyFcalls.getConum());
            InsertPreparedStatement.setInt(30, MyFcalls.getCcallertype());
            InsertPreparedStatement.setString(31, MyFcalls.getCnumber6());
            InsertPreparedStatement.setString(32, MyFcalls.getCnumber7());
            InsertPreparedStatement.setString(33, MyFcalls.getCnumber8());
            InsertPreparedStatement.setString(34, MyFcalls.getCnumber9());
            InsertPreparedStatement.setString(35, MyFcalls.getCnumber10());
            InsertPreparedStatement.setString(36, MyFcalls.getCsector1());
            InsertPreparedStatement.setString(37, MyFcalls.getCsector2());
            InsertPreparedStatement.setString(38, MyFcalls.getCextnum());
            InsertPreparedStatement.setString(39, MyFcalls.getCUrgLevel());
            InsertPreparedStatement.setString(40, MyFcalls.getCUuid());
            setNbAffectedRow(InsertPreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible d'ajouter un appel dans " + MyTable);
            } else {
//                Does not work with Informix IDS2000
//                MyKeyResultSet = InsertPreparedStatement.getGeneratedKeys();
//                if (MyKeyResultSet.next()) {
//                    MyFcalls.setCnum((int) MyKeyResultSet.getInt(1));
//                }
//                MyKeyResultSet.close();
                MyFcalls.setCnum(
                        ((IfmxStatement) InsertPreparedStatement).getSerial());
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de l'insertion d'un appel dans "
                    + MyTable + " " + MyException.getMessage());
        }
    }

    /**
     * Méthode pour filter les résultats par identifiant.
     *
     * @param id l'identifiant à utiliser pour le filtrage.
     */
    @Override
    public void filterById(int id) {
        StringBuffer Stmt;

        Stmt = new StringBuffer(InvariableSelectStatement);
        Stmt.append(" where cnum = ").append(id).append(";");
        setSelectStatement(Stmt.toString());
    }

    /**
     * Méthode pour filter les résultats par identifiant de groupe.
     *
     * @param gid l'identifiant de groupe à utiliser pour le filtrage.
     */
    @Override
    public void filterByGid(int gid) {
        StringBuffer Stmt;

        razDateFilter();
        Stmt = new StringBuffer(InvariableSelectStatement);
        Stmt.append(" and cunum = ").append(gid).append(";");
        setSelectStatement(Stmt.toString());
    }

    /**
     * Méthode pour filter les résultats par identifiant de groupe et par code.
     *
     * @param gid l'identifiant de groupe à utiliser pour le filtrage.
     * @param Code à utiliser pour le filtrage.
     */
    @Override
    public void filterByCode(int gid, String Code) {
        throw new UnsupportedOperationException("Non supporté actuellement");
    }

    /**
     * Méthode pour filter les résultats par identifiant Performance Immo.
     *
     * @param Uuid à utiliser pour le filtrage.
     */
    public void filterByUuid(String Uuid) {
        StringBuffer Stmt;

        Stmt = new StringBuffer(InvariableSelectStatement);
        Stmt.append(" where cuuid = '").append(Uuid).append("';");
        setSelectStatement(Stmt.toString());
    }

    /**
     * Méthode pour filter les résultats par identifiant de groupe et par numéro
     * d'appel.
     *
     * @param gid l'identifiant de groupe à utiliser pour le filtrage.
     * @param seqno à utiliser pour le filtrage.
     */
    public void filterByCode(int gid, int seqno) {
        StringBuffer Stmt;

        razDateFilter();
        Stmt = new StringBuffer(InvariableSelectStatement);
        Stmt.append(" and cunum = ").append(gid);
        if (seqno > 0) {
            Stmt.append(" and cseqno = ").append(seqno);
        }
        Stmt.append(" order by cseqno;");
        setSelectStatement(Stmt.toString());
    }

    /**
     * Méthode pour filter les résultats par identifiant de groupe et par dates.
     *
     * @param gid l'identifiant de groupe à utiliser pour le filtrage.
     * @param BegDate date de début d'extraction,
     * @param EndDate date de fin d'extraction.
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void filterByDate(int gid, Timestamp BegDate, Timestamp EndDate) throws SQLException {
        StringBuffer Stmt;
        int idx = 0;

        Stmt = new StringBuffer(InvariableSelectStatement);
        Stmt.append(" and cunum = ").append(gid);
        if (BegDate != null) {
            Stmt.append(" and cdate >= ?");
            idx++;
            idxBegDate = idx;
            this.BegDate = BegDate;
        }
        if (EndDate != null) {
            Stmt.append(" and cdate < ?");
            idx++;
            idxEndDate = idx;
            this.EndDate = EndDate;
        }
        Stmt.append(" order by cdate, ctime;");
        setSelectStatement(Stmt.toString());
    }

    /**
     * Méthode pour effacer le filtre sur les dates.
     *
     * Cela permet d'éviter des conflits lors de l'invocation de la méthode
     * setSelectPreparedStatement().
     */
    private void razDateFilter() {
        idxBegDate = 0;
        idxEndDate = 0;
        BegDate = null;
        EndDate = null;
    }

    /**
     * Méthode pour filter les résultats par identifiant de groupe et par nom.
     *
     * @param gid l'identifiant de groupe à utiliser pour le filtrage.
     * @param Name à utiliser pour le filtrage.
     */
    @Override
    public void filterByName(int gid, String Name) {
        throw new UnsupportedOperationException("Non supporté actuellement");
    }

    /**
     * Prepare la requête SQL pour sélectionner des données ainsi que le
     * ResultSet associé.
     *
     * <p>
     * <b>ATTENTION</b> : Nous avons ici une exception car les dates ne peuvent
     * pas être inscrites en dur dans le texte de la requête SQL. Il faut les
     * passer en paramètres après la préparation de la requête SQL.<p>
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    @Override
    public void setSelectPreparedStatement() throws SQLException {
        SelectPreparedStatement = MyConnection.prepareStatement(getSelectStatement());
        if (idxBegDate > 0) {
            SelectPreparedStatement.setTimestamp(idxBegDate, BegDate);
        }
        if (idxEndDate > 0) {
            SelectPreparedStatement.setTimestamp(idxEndDate, EndDate);
        }
        setSelectResultSet();
    }

    @Override
    public void update(Object MyObject) {
        throw new UnsupportedOperationException("Non supporté actuellement"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Object MyObject) {
        throw new UnsupportedOperationException("Non supporté actuellement"); //To change body of generated methods, choose Tools | Templates.
    }
}
