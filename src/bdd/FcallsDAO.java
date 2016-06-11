package bdd;

import java.sql.*;

/**
 * Classe qui décrit les méthodes pour accéder aux tables fcalls ou f99calls au
 * travers de JDBC.
 *
 * @author Thierry Baribaud
 * @version Juin 2016
 */
public class FcallsDAO extends PaternDAO {

    private String MyTable = "fcalls";

    /**
     * Constructeur de la classe FcallsDAO.
     *
     * @param MyConnection connexion à la base de données courante.
     * @param cnum identifiant de l'appel,
     * @param cunum identifiant du client,
     * @param BegDate date de début d'extraction,
     * @param EndDate date de fin d'extraction,
     * @param MyEtatTicket indique si l'on travaille sur les tickets en cours ou
     * archivés.
     * @throws ClassNotFoundException en cas de classse non trouvée.
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public FcallsDAO(Connection MyConnection, int cnum, int cunum,
            Timestamp BegDate, Timestamp EndDate, EtatTicket MyEtatTicket)
            throws ClassNotFoundException, SQLException {

        StringBuffer Stmt;

        MyTable = EtatTicket.EN_COURS.equals(MyEtatTicket) ? "fcalls" : "f99calls";

        int idxBegDate = 0;
        int idxEndDate = 0;
        int idx = 0;

        this.MyConnection = MyConnection;

        Stmt = new StringBuffer("select cnum, cunum, cname, ctel, caddress, caddress2,"
                + " caccess, cposcode, city, csympt,"
                + " cnumber4, cc6num, cdate, ctime, cdate2, ctime2,"
                + " corp, cnumber5, cseqno, cquery1, cquery2, czone, cage, ctype,"
                + " ctnum, cnote,"
                + " cdelay1, cdelay2, cduration, conum, ccallertype,"
                + " cnumber6, cnumber7, cnumber8, cnumber9, cnumber10,"
                + " csector1, csector2, cextnum"
                + " from " + MyTable
                + " where (cinternal = 0 or cinternal is null)"
                + " and (ctest = 0 or ctest is null)");
        if (cnum > 0) {
            Stmt.append(" and cnum = ").append(cnum);
        }
        if (cunum > 0) {
            Stmt.append(" and cunum = ").append(cunum);
        }
        if (BegDate != null) {
            Stmt.append(" and cdate >= ?");
            idx++;
            idxBegDate = idx;
        }
        if (EndDate != null) {
            Stmt.append(" and cdate < ?");
            idx++;
            idxEndDate = idx;
        }
        Stmt.append(" order by cnum;");
//        System.out.println(Stmt);
        setReadStatement(Stmt.toString());
        setReadPreparedStatement();
        if (idxBegDate > 0) {
            ReadPreparedStatement.setTimestamp(idxBegDate, BegDate);
        }
        if (idxEndDate > 0) {
            ReadPreparedStatement.setTimestamp(idxEndDate, EndDate);
        }
        setReadResultSet();

        setUpdateStatement("update " + MyTable
                + " set cunum=?, cname=?, ctel=?, caddress=?, caddress2=?,"
                + " caccess=?, cposcode=?, city=?, csympt=?,"
                + " cnumber4=?, cc6num=?, cdate=?, ctime=?, cdate2=?, ctime2=?,"
                + " corp=?, cnumber5=?, cseqno=?, cquery1=?, cquery2=?,"
                + " czone=?, cage=?, ctype=?, ctnum=?, cnote=?,"
                + " cdelay1=?, cdelay2=?, cduration=?, conum=?, ccallertype=?,"
                + " cnumber6=?, cnumber7=?, cnumber8=?, cnumber9=?, cnumber10=?,"
                + " csector1=?, csector2=?, cextnum=?"
                + " where cnum=?;");
        setUpdatePreparedStatement();

        setInsertStatement("insert into " + MyTable
                + " (cunum, cname, ctel, caddress, caddress2,"
                + " caccess, cposcode, city, csympt,"
                + " cnumber4, cc6num, cdate, ctime, cdate2, ctime2,"
                + " corp, cnumber5, cseqno, cquery1, cquery2, czone,"
                + " cage, ctype, ctnum, cnote,"
                + " cdelay1, cdelay2, cduration, conum, ccallertype,"
                + " cnumber6, cnumber7, cnumber8, cnumber9, cnumber10,"
                + " csector1, csector2, cextnum"
                + ")"
                + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
                + " ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
        setInsertPreparedStatement();

        setDeleteStatement("delete from " + MyTable + " where cnum=?;");
        setDeletePreparedStatement();
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
            if (ReadResultSet.next()) {
                MyFcalls = new Fcalls();
                MyFcalls.setCnum(ReadResultSet.getInt("cnum"));
                MyFcalls.setCunum(ReadResultSet.getInt("cunum"));
                MyFcalls.setCname(ReadResultSet.getString("cname"));
                MyFcalls.setCtel(ReadResultSet.getString("ctel"));
                MyFcalls.setCaddress(ReadResultSet.getString("caddress"));
                MyFcalls.setCaddress2(ReadResultSet.getString("caddress2"));
                MyFcalls.setCaccess(ReadResultSet.getString("caccess"));
                MyFcalls.setCposcode(ReadResultSet.getString("cposcode"));
                MyFcalls.setCity(ReadResultSet.getString("city"));
                MyFcalls.setCsympt(ReadResultSet.getString("csympt"));
                MyFcalls.setCnumber4(ReadResultSet.getString("cnumber4"));
                MyFcalls.setCc6num(ReadResultSet.getInt("cc6num"));
                MyFcalls.setCdate(ReadResultSet.getTimestamp("cdate"));
                MyFcalls.setCtime(ReadResultSet.getString("ctime"));
                MyFcalls.setCdate2(ReadResultSet.getTimestamp("cdate2"));
                MyFcalls.setCtime2(ReadResultSet.getString("ctime2"));
                MyFcalls.setCorp(ReadResultSet.getString("corp"));
                MyFcalls.setCnumber5(ReadResultSet.getString("cnumber5"));
                MyFcalls.setCseqno(ReadResultSet.getInt("cseqno"));
                MyFcalls.setCquery1(ReadResultSet.getInt("cquery1"));
                MyFcalls.setCquery2(ReadResultSet.getInt("cquery2"));
                MyFcalls.setCzone(ReadResultSet.getInt("czone"));
                MyFcalls.setCage(ReadResultSet.getInt("cage"));
                MyFcalls.setCtype(ReadResultSet.getInt("ctype"));
                MyFcalls.setCtnum(ReadResultSet.getInt("ctnum"));
                MyFcalls.setCnote(ReadResultSet.getInt("cnote"));
                MyFcalls.setCnote(ReadResultSet.getInt("cdelay1"));
                MyFcalls.setCnote(ReadResultSet.getInt("cdelay2"));
                MyFcalls.setCnote(ReadResultSet.getInt("cduration"));
                MyFcalls.setCnote(ReadResultSet.getInt("conum"));
                MyFcalls.setCnote(ReadResultSet.getInt("ccallertype"));
                MyFcalls.setCnumber5(ReadResultSet.getString("cnumber6"));
                MyFcalls.setCnumber5(ReadResultSet.getString("cnumber7"));
                MyFcalls.setCnumber5(ReadResultSet.getString("cnumber8"));
                MyFcalls.setCnumber5(ReadResultSet.getString("cnumber9"));
                MyFcalls.setCnumber5(ReadResultSet.getString("cnumber10"));
                MyFcalls.setCnumber5(ReadResultSet.getString("csector1"));
                MyFcalls.setCnumber5(ReadResultSet.getString("csector2"));
                MyFcalls.setCnumber5(ReadResultSet.getString("cextnum"));
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
            UpdatePreparedStatement.setInt(13, MyFcalls.getCc6num());
            UpdatePreparedStatement.setTimestamp(14, MyFcalls.getCdate());
            UpdatePreparedStatement.setString(11, MyFcalls.getCtime());
            UpdatePreparedStatement.setTimestamp(15, MyFcalls.getCdate2());
            UpdatePreparedStatement.setString(12, MyFcalls.getCtime2());
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
            UpdatePreparedStatement.setInt(39, MyFcalls.getCnum());
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
        ResultSet MyKeyResultSet = null;

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
            InsertPreparedStatement.setInt(13, MyFcalls.getCc6num());
            InsertPreparedStatement.setTimestamp(14, MyFcalls.getCdate());
            InsertPreparedStatement.setString(11, MyFcalls.getCtime());
            InsertPreparedStatement.setTimestamp(15, MyFcalls.getCdate2());
            InsertPreparedStatement.setString(12, MyFcalls.getCtime2());
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
            setNbAffectedRow(InsertPreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible d'ajouter un appel dans " + MyTable);
            } else {
                MyKeyResultSet = InsertPreparedStatement.getGeneratedKeys();
                if (MyKeyResultSet.next()) {
                    MyFcalls.setCnum((int) MyKeyResultSet.getInt(1));
                }
            }
            MyKeyResultSet.close();
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de l'insertion d'un appel dans "
                    + MyTable + " " + MyException.getMessage());
        }
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
