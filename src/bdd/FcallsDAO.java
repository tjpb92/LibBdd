package bdd;

import com.informix.jdbc.IfmxStatement;
import java.sql.*;

/**
 * Classe qui décrit les méthodes pour accéder aux tables fcalls ou f99calls au
 * travers de JDBC.
 *
 * @author Thierry Baribaud
 * @version 0.24
 */
public class FcallsDAO extends PatternDAO {

    /**
     * Table à gérér : fcalls ou f99calls.
     */
    private static String table = "fcalls";

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
     * @param connection connexion à la base de données courante.
     * @param etatTicket indique si l'on travaille sur les tickets en cours ou
     * archivés.
     * @throws ClassNotFoundException en cas de classe non trouvée.
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public FcallsDAO(Connection connection, EtatTicket etatTicket)
            throws ClassNotFoundException, SQLException {

        table = EtatTicket.EN_COURS.equals(etatTicket) ? "fcalls" : "f99calls";

        this.connection = connection;

        setInvariableSelectStatement("select cnum, cunum, cname, ctel, caddress, caddress2,"
                + " caccess, cposcode, city, csympt,"
                + " cnumber4, cc6num, cdate, ctime, cdate2, ctime2,"
                + " corp, cnumber5, cseqno, cquery1, cquery2, czone, cage, ctype,"
                + " ctnum, cnote,"
                + " cdelay1, cdelay2, cduration, conum, ccallertype,"
                + " cnumber6, cnumber7, cnumber8, cnumber9, cnumber10,"
                + " csector1, csector2, cextnum, iso8601_weeknum(cdate) cweeknum,"
                + " curglevel, cuuid"
                + " from " + table
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

        setUpdateStatement("update " + table
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

        setInsertStatement("insert into " + table
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

        setDeleteStatement("delete from " + table + " where cnum=?;");
//        setDeletePreparedStatement();
    }

    /**
     * Selectionne un appel.
     *
     * @return l'appel sélectionné.
     */
    @Override
    public Fcalls select() {
        Fcalls fcalls = null;

        try {
            if (SelectResultSet.next()) {
                fcalls = new Fcalls();
                fcalls.setCnum(SelectResultSet.getInt("cnum"));
                fcalls.setCunum(SelectResultSet.getInt("cunum"));
                fcalls.setCname(SelectResultSet.getString("cname"));
                fcalls.setCtel(SelectResultSet.getString("ctel"));
                fcalls.setCaddress(SelectResultSet.getString("caddress"));
                fcalls.setCaddress2(SelectResultSet.getString("caddress2"));
                fcalls.setCaccess(SelectResultSet.getString("caccess"));
                fcalls.setCposcode(SelectResultSet.getString("cposcode"));
                fcalls.setCity(SelectResultSet.getString("city"));
                fcalls.setCsympt(SelectResultSet.getString("csympt"));
                fcalls.setCnumber4(SelectResultSet.getString("cnumber4"));
                fcalls.setCc6num(SelectResultSet.getInt("cc6num"));
                fcalls.setCdate(SelectResultSet.getTimestamp("cdate"));
                fcalls.setCtime(SelectResultSet.getString("ctime"));
                fcalls.setCdate2(SelectResultSet.getTimestamp("cdate2"));
                fcalls.setCtime2(SelectResultSet.getString("ctime2"));
                fcalls.setCorp(SelectResultSet.getString("corp"));
                fcalls.setCnumber5(SelectResultSet.getString("cnumber5"));
                fcalls.setCseqno(SelectResultSet.getInt("cseqno"));
                fcalls.setCquery1(SelectResultSet.getInt("cquery1"));
                fcalls.setCquery2(SelectResultSet.getInt("cquery2"));
                fcalls.setCzone(SelectResultSet.getInt("czone"));
                fcalls.setCage(SelectResultSet.getInt("cage"));
                fcalls.setCtype(SelectResultSet.getInt("ctype"));
                fcalls.setCtnum(SelectResultSet.getInt("ctnum"));
                fcalls.setCnote(SelectResultSet.getInt("cnote"));
                fcalls.setCdelay1(SelectResultSet.getInt("cdelay1"));
                fcalls.setCdelay2(SelectResultSet.getInt("cdelay2"));
                fcalls.setCduration(SelectResultSet.getInt("cduration"));
                fcalls.setConum(SelectResultSet.getInt("conum"));
                fcalls.setCcallertype(SelectResultSet.getInt("ccallertype"));
                fcalls.setCnumber6(SelectResultSet.getString("cnumber6"));
                fcalls.setCnumber7(SelectResultSet.getString("cnumber7"));
                fcalls.setCnumber8(SelectResultSet.getString("cnumber8"));
                fcalls.setCnumber9(SelectResultSet.getString("cnumber9"));
                fcalls.setCnumber10(SelectResultSet.getString("cnumber10"));
                fcalls.setCsector1(SelectResultSet.getString("csector1"));
                fcalls.setCsector2(SelectResultSet.getString("csector2"));
                fcalls.setCextnum(SelectResultSet.getString("cextnum"));
                fcalls.setCWeekNum(SelectResultSet.getString("cweeknum"));
                fcalls.setCUrgLevel(SelectResultSet.getString("curglevel"));
                fcalls.setCUuid(SelectResultSet.getString("cuuid"));
            } else {
                System.out.println("Lecture de " + table + " terminée");
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur en lecture de " + table + " "
                    + MyException.getMessage());
        }
        return fcalls;
    }

    /**
     * Met à jour un appel.
     *
     * @param fcalls appel à mettre à jour.
     */
    public void update(Fcalls fcalls) {
        try {
            UpdatePreparedStatement.setInt(1, fcalls.getCunum());
            UpdatePreparedStatement.setString(2, fcalls.getCname());
            UpdatePreparedStatement.setString(3, fcalls.getCtel());
            UpdatePreparedStatement.setString(4, fcalls.getCaddress());
            UpdatePreparedStatement.setString(5, fcalls.getCaddress2());
            UpdatePreparedStatement.setString(6, fcalls.getCaccess());
            UpdatePreparedStatement.setString(7, fcalls.getCposcode());
            UpdatePreparedStatement.setString(8, fcalls.getCity());
            UpdatePreparedStatement.setString(9, fcalls.getCsympt());
            UpdatePreparedStatement.setString(10, fcalls.getCnumber4());
            UpdatePreparedStatement.setInt(11, fcalls.getCc6num());
            UpdatePreparedStatement.setTimestamp(12, fcalls.getCdate());
            UpdatePreparedStatement.setString(13, fcalls.getCtime());
            UpdatePreparedStatement.setTimestamp(14, fcalls.getCdate2());
            UpdatePreparedStatement.setString(15, fcalls.getCtime2());
            UpdatePreparedStatement.setString(16, fcalls.getCorp());
            UpdatePreparedStatement.setString(17, fcalls.getCnumber5());
            UpdatePreparedStatement.setInt(18, fcalls.getCseqno());
            UpdatePreparedStatement.setInt(19, fcalls.getCquery1());
            UpdatePreparedStatement.setInt(20, fcalls.getCquery2());
            UpdatePreparedStatement.setInt(21, fcalls.getCzone());
            UpdatePreparedStatement.setInt(22, fcalls.getCage());
            UpdatePreparedStatement.setInt(23, fcalls.getCtype());
            UpdatePreparedStatement.setInt(24, fcalls.getCtnum());
            UpdatePreparedStatement.setInt(25, fcalls.getCnote());
            UpdatePreparedStatement.setInt(26, fcalls.getCdelay1());
            UpdatePreparedStatement.setInt(27, fcalls.getCdelay2());
            UpdatePreparedStatement.setInt(28, fcalls.getCduration());
            UpdatePreparedStatement.setInt(29, fcalls.getConum());
            UpdatePreparedStatement.setInt(30, fcalls.getCcallertype());
            UpdatePreparedStatement.setString(31, fcalls.getCnumber6());
            UpdatePreparedStatement.setString(32, fcalls.getCnumber7());
            UpdatePreparedStatement.setString(33, fcalls.getCnumber8());
            UpdatePreparedStatement.setString(34, fcalls.getCnumber9());
            UpdatePreparedStatement.setString(35, fcalls.getCnumber10());
            UpdatePreparedStatement.setString(36, fcalls.getCsector1());
            UpdatePreparedStatement.setString(37, fcalls.getCsector2());
            UpdatePreparedStatement.setString(38, fcalls.getCextnum());
            UpdatePreparedStatement.setString(39, fcalls.getCUrgLevel());
            UpdatePreparedStatement.setString(40, fcalls.getCUuid());
            UpdatePreparedStatement.setInt(41, fcalls.getCnum());
            setNbAffectedRow(UpdatePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de mettre à jour " + table);
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de la mise à jour de " + table
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
                System.out.println("Impossible de détruire un appel dans " + table);
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de la suppression d'un appel dans "
                    + table + " " + MyException.getMessage());
        }
    }

    /**
     * Ajoute un appel dans la table fcalls ou f99calls.
     *
     * @param fcalls appel à ajouter à la table fcalls ou f99calls.
     */
    public void insert(Fcalls fcalls) {
//        ResultSet MyKeyResultSet;

        try {
            System.out.println("cname=" + fcalls.getCname());
            InsertPreparedStatement.setInt(1, fcalls.getCunum());
            InsertPreparedStatement.setString(2, fcalls.getCname());
            InsertPreparedStatement.setString(3, fcalls.getCtel());
            InsertPreparedStatement.setString(4, fcalls.getCaddress());
            InsertPreparedStatement.setString(5, fcalls.getCaddress2());
            InsertPreparedStatement.setString(6, fcalls.getCaccess());
            InsertPreparedStatement.setString(7, fcalls.getCposcode());
            InsertPreparedStatement.setString(8, fcalls.getCity());
            InsertPreparedStatement.setString(9, fcalls.getCsympt());
            InsertPreparedStatement.setString(10, fcalls.getCnumber4());
            InsertPreparedStatement.setInt(11, fcalls.getCc6num());
            InsertPreparedStatement.setTimestamp(12, fcalls.getCdate());
            InsertPreparedStatement.setString(13, fcalls.getCtime());
            InsertPreparedStatement.setTimestamp(14, fcalls.getCdate2());
            InsertPreparedStatement.setString(15, fcalls.getCtime2());
            InsertPreparedStatement.setString(16, fcalls.getCorp());
            InsertPreparedStatement.setString(17, fcalls.getCnumber5());
            InsertPreparedStatement.setInt(18, fcalls.getCseqno());
            InsertPreparedStatement.setInt(19, fcalls.getCquery1());
            InsertPreparedStatement.setInt(20, fcalls.getCquery2());
            InsertPreparedStatement.setInt(21, fcalls.getCzone());
            InsertPreparedStatement.setInt(22, fcalls.getCage());
            InsertPreparedStatement.setInt(23, fcalls.getCtype());
            InsertPreparedStatement.setInt(24, fcalls.getCtnum());
            InsertPreparedStatement.setInt(25, fcalls.getCnote());
            InsertPreparedStatement.setInt(26, fcalls.getCdelay1());
            InsertPreparedStatement.setInt(27, fcalls.getCdelay2());
            InsertPreparedStatement.setInt(28, fcalls.getCduration());
            InsertPreparedStatement.setInt(29, fcalls.getConum());
            InsertPreparedStatement.setInt(30, fcalls.getCcallertype());
            InsertPreparedStatement.setString(31, fcalls.getCnumber6());
            InsertPreparedStatement.setString(32, fcalls.getCnumber7());
            InsertPreparedStatement.setString(33, fcalls.getCnumber8());
            InsertPreparedStatement.setString(34, fcalls.getCnumber9());
            InsertPreparedStatement.setString(35, fcalls.getCnumber10());
            InsertPreparedStatement.setString(36, fcalls.getCsector1());
            InsertPreparedStatement.setString(37, fcalls.getCsector2());
            InsertPreparedStatement.setString(38, fcalls.getCextnum());
            InsertPreparedStatement.setString(39, fcalls.getCUrgLevel());
            InsertPreparedStatement.setString(40, fcalls.getCUuid());
            setNbAffectedRow(InsertPreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible d'ajouter un appel dans " + table);
            } else {
//                Does not work with Informix IDS2000
//                MyKeyResultSet = InsertPreparedStatement.getGeneratedKeys();
//                if (MyKeyResultSet.next()) {
//                    MyFcalls.setCnum((int) MyKeyResultSet.getInt(1));
//                }
//                MyKeyResultSet.close();
                fcalls.setCnum(
                        ((IfmxStatement) InsertPreparedStatement).getSerial());
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de l'insertion d'un appel dans "
                    + table + " " + MyException.getMessage());
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
        Stmt.append(" and cuuid = '").append(Uuid).append("';");
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
     * @param begDate date de début d'extraction,
     * @param endDate date de fin d'extraction.
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void filterByDate(int gid, Timestamp begDate, Timestamp endDate) throws SQLException {
        StringBuffer stmt;
        int idx = 0;

        if (SelectStatement != null) {
            stmt = new StringBuffer(SelectStatement);
        } else {
            stmt = new StringBuffer(InvariableSelectStatement);
        }
        stmt.append(" and cunum = ").append(gid);
        if (begDate != null) {
            stmt.append(" and cdate >= ?");
            idx++;
            idxBegDate = idx;
            this.BegDate = begDate;
        }
        if (endDate != null) {
            stmt.append(" and cdate < ?");
            idx++;
            idxEndDate = idx;
            this.EndDate = endDate;
        }
        stmt.append(" order by cdate, ctime;");
        setSelectStatement(stmt.toString());
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
     * Méthode pour filtrer les appels ouverts
     */
    public void filterOpenedTicket() {
        StringBuffer stmt;

        if (SelectStatement != null) {
            stmt = new StringBuffer(SelectStatement);
        } else {
            stmt = new StringBuffer(InvariableSelectStatement);
        }
        stmt.append(" and cnote = 0");
        setSelectStatement(stmt.toString());
    }

    /**
     * Méthode pour filtrer par intervenant
     * ATTENTION : A réécrire en passant par un paramètre x.setInt(p, tnum)
     * @param tnum référence à l'intervenant à filter
     */
    public void filterByProvider(int tnum) {
        StringBuffer stmt;

        if (tnum > 0) {
            if (SelectStatement != null) {
                stmt = new StringBuffer(SelectStatement);
            } else {
                stmt = new StringBuffer(InvariableSelectStatement);
            }
            stmt.append(" and ctnum = ").append(tnum);
            setSelectStatement(stmt.toString());
        }
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
        SelectPreparedStatement = connection.prepareStatement(getSelectStatement());
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
