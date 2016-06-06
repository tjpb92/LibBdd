package bdd;

import bdd.PaternDAO;
import java.io.IOException;
import java.sql.*;
import utils.ApplicationProperties;
import utils.DBManager;
import utils.DBServer;
import utils.DBServerException;

/**
 * Classe qui d�crit les m�thodes pour acc�der � la table fcomplmt au
 * travers de JDBC.
 *
 * @author Thierry Baribaud.
 * @version May 2016.
 */
public class FcomplmtDAO extends PaternDAO {

    /**
     * Class constructor.
     *
     * @param MyConnection an active connection to a database.
     * @param myC6num identiant du compl�ment d'appel.
     * @throws ClassNotFoundException classe non trouv�e.
     * @throws java.sql.SQLException erreur SQL.
     */
    public FcomplmtDAO(Connection MyConnection, int myC6num)
            throws ClassNotFoundException, SQLException {

        StringBuffer Stmt;

        this.MyConnection = MyConnection;

        Stmt = new StringBuffer("select c6num, c6int2, c6alpha1, c6alpha2, c6name, c6access,"
                + " c6city, c6tel, c6alpha3, c6alpha4,"
                + " c6alpha5, c6alpha6, c6alpha7,"
                + " c6int1, c6date, c6date1, c6int3, c6int4, c6onum,"
                + " c6corp, c6address, c6address2, c6poscode"
                + " from fcomplmt");
        if (myC6num > 0) {
            Stmt.append(" where c6num = ").append(myC6num);
        }
        Stmt.append(" order by c6num;");
        setReadStatement(Stmt.toString());
        setReadPreparedStatement();
//        System.out.println(Stmt);
        setReadResultSet();

        setUpdateStatement("update fcomplmt"
                + " set c6int2=?, c6alpha1=?, c6alpha2=?, c6name=?, c6access=?,"
                + " c6city=?, c6tel=?, c6alpha3=?, c6alpha4=?,"
                + " c6alpha5=?, c6alpha6=?, c6alpha7=?,"
                + " c6int1=?, c6date=?, c6date1=?, c6int3=?, c6int4=?, c6onum=?,"
                + " c6corp=?, c6address=?, c6address2=?, c6poscode=?"
                + " where c6num=?;");
        setUpdatePreparedStatement();

        setInsertStatement("insert into fcomplmt"
                + " (c6int2, c6alpha1, c6alpha2, c6name, c6access,"
                + " c6city, c6tel, c6alpha3, c6alpha4,"
                + " c6alpha5, c6alpha6, c6alpha7,"
                + " c6int1, c6date, c6date1, c6int3, c6int4, c6onum,"
                + " c6corp, c6address, c6address2, c6poscode)"
                + " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?" 
                + ",?, ?, ?, ?, ?, ?, ?);");
        setInsertPreparedStatement();

        setDeleteStatement("delete from fcomplmt where c6num=?;");
        setDeletePreparedStatement();
    }

    /**
   * Selectionne un compl�ment d'appel.
     *
     * @return le compl�ment d'appel s�lectionn�.
     */
    @Override
    public Fcomplmt select() {
        Fcomplmt MyFcomplmt = null;

        try {
            if (ReadResultSet.next()) {
                MyFcomplmt = new Fcomplmt();
                MyFcomplmt.setC6num(ReadResultSet.getInt("c6num"));
                MyFcomplmt.setC6int2(ReadResultSet.getInt("c6int2"));
                MyFcomplmt.setC6alpha1(ReadResultSet.getString("c6alpha1"));
                MyFcomplmt.setC6alpha2(ReadResultSet.getString("c6alpha2"));
                MyFcomplmt.setC6name(ReadResultSet.getString("c6name"));
                MyFcomplmt.setC6access(ReadResultSet.getString("c6access"));
                MyFcomplmt.setC6city(ReadResultSet.getString("c6city"));
                MyFcomplmt.setC6tel(ReadResultSet.getString("c6tel"));
                MyFcomplmt.setC6alpha3(ReadResultSet.getString("c6alpha3"));
                MyFcomplmt.setC6alpha4(ReadResultSet.getString("c6alpha4"));
                MyFcomplmt.setC6alpha5(ReadResultSet.getString("c6alpha5"));
                MyFcomplmt.setC6alpha6(ReadResultSet.getString("c6alpha6"));
                MyFcomplmt.setC6alpha7(ReadResultSet.getString("c6alpha7"));
                MyFcomplmt.setC6int1(ReadResultSet.getInt("c6int1"));
                MyFcomplmt.setC6date(ReadResultSet.getTimestamp("c6date"));
                MyFcomplmt.setC6date1(ReadResultSet.getTimestamp("c6date1"));
                MyFcomplmt.setC6int3(ReadResultSet.getInt("c6int3"));
                MyFcomplmt.setC6int4(ReadResultSet.getInt("c6int4"));
                MyFcomplmt.setC6onum(ReadResultSet.getInt("c6onum"));
                MyFcomplmt.setC6corp(ReadResultSet.getString("c6corp"));
                MyFcomplmt.setC6address(ReadResultSet.getString("c6address"));
                MyFcomplmt.setC6address(ReadResultSet.getString("c6address2"));
                MyFcomplmt.setC6poscode(ReadResultSet.getString("c6poscode"));
            } else {
                System.out.println("Lecture de fcomplmt termin�e");
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur en lecture de fcomplmt " + MyException.getMessage());
        }
        return MyFcomplmt;
    }

    /**
     * Met � jour un compl�ment d'appel.
     *
     * @param MyFcomplmt compl�ment d'appel � mettre � jour.
     */
    public void update(Fcomplmt MyFcomplmt) {
        try {
            UpdatePreparedStatement.setInt(1, MyFcomplmt.getC6int2());
            UpdatePreparedStatement.setString(2, MyFcomplmt.getC6alpha1());
            UpdatePreparedStatement.setString(3, MyFcomplmt.getC6alpha2());
            UpdatePreparedStatement.setString(4, MyFcomplmt.getC6name());
            UpdatePreparedStatement.setString(5, MyFcomplmt.getC6access());
            UpdatePreparedStatement.setString(6, MyFcomplmt.getC6city());
            UpdatePreparedStatement.setString(7, MyFcomplmt.getC6tel());
            UpdatePreparedStatement.setString(8, MyFcomplmt.getC6alpha3());
            UpdatePreparedStatement.setString(9, MyFcomplmt.getC6alpha4());
            UpdatePreparedStatement.setString(10, MyFcomplmt.getC6alpha5());
            UpdatePreparedStatement.setString(11, MyFcomplmt.getC6alpha6());
            UpdatePreparedStatement.setString(12, MyFcomplmt.getC6alpha7());
            UpdatePreparedStatement.setInt(13, MyFcomplmt.getC6int1());
            UpdatePreparedStatement.setTimestamp(14, MyFcomplmt.getC6date());
            UpdatePreparedStatement.setTimestamp(15, MyFcomplmt.getC6date1());
            UpdatePreparedStatement.setInt(16, MyFcomplmt.getC6int3());
            UpdatePreparedStatement.setInt(17, MyFcomplmt.getC6int4());
            UpdatePreparedStatement.setInt(18, MyFcomplmt.getC6onum());
            UpdatePreparedStatement.setString(19, MyFcomplmt.getC6corp());
            UpdatePreparedStatement.setString(20, MyFcomplmt.getC6address());
            UpdatePreparedStatement.setString(21, MyFcomplmt.getC6address2());
            UpdatePreparedStatement.setString(22, MyFcomplmt.getC6poscode());
            UpdatePreparedStatement.setInt(23, MyFcomplmt.getC6num());
            setNbAffectedRow(UpdatePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de mettre � jour fcomplmt");
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de la mise � jour de fcomplmt "
                    + MyException.getMessage());
        }
    }

    /**
     * Supprime d�finitivement un compl�ment d'appel.
     *
     * @param c6num identiant du compl�ment d'appel � supprimer.
     */
    @Override
    public void delete(int c6num) {
        try {
            DeletePreparedStatement.setInt(1, c6num);
            setNbAffectedRow(DeletePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de d�truire un compl�ment d'appel dans fcomplmt");
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de la suppression d'un compl�ment d'appel dans fcomplmt " 
                    + MyException.getMessage());
        }
    }

    /**
     * Ajoute un compl�ment d'appel dans la table fcomplmt.
     *
     * @param MyFcomplmt compl�ment d'appel � ajouter � la table fcomplmt.
     */
    public void insert(Fcomplmt MyFcomplmt) {
        ResultSet MyKeyResultSet = null;

        try {
            System.out.println("c6alpha2=" + MyFcomplmt.getC6alpha2());
            InsertPreparedStatement.setInt(1, MyFcomplmt.getC6int2());
            InsertPreparedStatement.setString(2, MyFcomplmt.getC6alpha1());
            InsertPreparedStatement.setString(3, MyFcomplmt.getC6alpha2());
            InsertPreparedStatement.setString(4, MyFcomplmt.getC6name());
            InsertPreparedStatement.setString(5, MyFcomplmt.getC6access());
            InsertPreparedStatement.setString(6, MyFcomplmt.getC6city());
            InsertPreparedStatement.setString(7, MyFcomplmt.getC6tel());
            InsertPreparedStatement.setString(8, MyFcomplmt.getC6alpha3());
            InsertPreparedStatement.setString(9, MyFcomplmt.getC6alpha4());
            InsertPreparedStatement.setString(10, MyFcomplmt.getC6alpha5());
            InsertPreparedStatement.setString(11, MyFcomplmt.getC6alpha6());
            InsertPreparedStatement.setString(12, MyFcomplmt.getC6alpha7());
            InsertPreparedStatement.setInt(13, MyFcomplmt.getC6int1());
            InsertPreparedStatement.setTimestamp(14, MyFcomplmt.getC6date());
            InsertPreparedStatement.setTimestamp(15, MyFcomplmt.getC6date1());
            InsertPreparedStatement.setInt(16, MyFcomplmt.getC6int3());
            InsertPreparedStatement.setInt(17, MyFcomplmt.getC6int4());
            InsertPreparedStatement.setInt(18, MyFcomplmt.getC6onum());
            InsertPreparedStatement.setString(19, MyFcomplmt.getC6corp());
            InsertPreparedStatement.setString(20, MyFcomplmt.getC6address());
            InsertPreparedStatement.setString(21, MyFcomplmt.getC6address2());
            InsertPreparedStatement.setString(22, MyFcomplmt.getC6poscode());
            setNbAffectedRow(InsertPreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible d'ajouter un compl�ment d'appel dans fcomplmt");
            } else {
                MyKeyResultSet = InsertPreparedStatement.getGeneratedKeys();
                if (MyKeyResultSet.next()) {
                    MyFcomplmt.setC6num((int) MyKeyResultSet.getInt(1));
                }
            }
            MyKeyResultSet.close();
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de l'insertion d'un compl�ment d'appel dans fcomplmt " + MyException.getMessage());
        }
    }

    /**
     * Main method to test FcomplmtDAO class.
     *
     * @param Args command line arguments. First argument must be the type of
     * Database server : dev/pre-prod/prod. Second argument must be the name of
     * the application properties file to load.
     */
    public static void main(String[] Args) {
        ApplicationProperties MyApplicationProperties;
        DBServer MyDBServer;
        DBManager MyDBManager;
        FcomplmtDAO MyFcomplmtDAO;
        Fcomplmt MyFcomplmt1;
        Fcomplmt MyFcomplmt;
        long i;
        int myC6num = 552;

        if (Args.length != 2) {
            System.out.println("Usage : java FcomplmtDAO server-type filename");
            System.exit(0);
        }

        try {
            MyApplicationProperties = new ApplicationProperties(Args[1]);

            MyDBServer = new DBServer(Args[0], MyApplicationProperties);
            MyDBManager = new DBManager(MyDBServer);

// Essai insertion
            MyFcomplmtDAO = new FcomplmtDAO(MyDBManager.getConnection(), myC6num);
            MyFcomplmt1 = new Fcomplmt();
            MyFcomplmt1.setC6int2(99999);
            MyFcomplmt1.setC6alpha1("terra incognita");
            MyFcomplmt1.setC6alpha2("utopia");
            MyFcomplmt1.setC6name("UTOPIA");
            MyFcomplmt1.setC6access("porte B");
            MyFcomplmt1.setC6city("12, rue des r�ves");
            MyFcomplmt1.setC6tel("b�timent B");
            MyFcomplmt1.setC6alpha3("92400");
            MyFcomplmt1.setC6alpha4("UTOPIA CITY");
            MyFcomplmt1.setC6alpha5("01.01.01.01.01");
            MyFcomplmt1.setC6alpha6("02.02.02.02.02");
            MyFcomplmt1.setC6alpha7("03.03.03.03.03");
            MyFcomplmt1.setC6int1(1);
            MyFcomplmt1.setC6date(new Timestamp(new java.util.Date().getTime()));
            MyFcomplmt1.setC6date1(Timestamp.valueOf("2050-12-31 23:59:59.0"));
            MyFcomplmt1.setC6int3(3);
            MyFcomplmt1.setC6int4(4);
            MyFcomplmt1.setC6onum(36);
            MyFcomplmt1.setC6corp("UTOPIA DISTRIBUTION");
            MyFcomplmt1.setC6address("14, rue de la r�alit�");
            MyFcomplmt1.setC6address2("porte 1");
            MyFcomplmt1.setC6poscode("92130");
            System.out.println("Fcomplmt(before insert)=" + MyFcomplmt1);
            MyFcomplmtDAO.insert(MyFcomplmt1);
            System.out.println("Fcomplmt(after insert)=" + MyFcomplmt1);
            System.out.println("Affected row(s)=" + MyFcomplmtDAO.getNbAffectedRow());

// Essai mise � jour
            MyFcomplmt1.setC6access(MyFcomplmt1.getC6access() + ", escalier 9");
            MyFcomplmtDAO.update(MyFcomplmt1);
            System.out.println("Fcomplmt(after update)=" + MyFcomplmt1);
            System.out.println("Affected row(s)=" + MyFcomplmtDAO.getNbAffectedRow());
            MyFcomplmtDAO.close();

// Essai lecture
            MyFcomplmtDAO = new FcomplmtDAO(MyDBManager.getConnection(), myC6num);
            i = 0;
            while ((MyFcomplmt = MyFcomplmtDAO.select()) != null) {
                i++;
                System.out.println("Fcomplmt(" + i + ")=" + MyFcomplmt);
                System.out.println("  getC6num()=" + MyFcomplmt.getC6num());
                System.out.println("  getC6int2()=" + MyFcomplmt.getC6int2());
                System.out.println("  getC6alpha1()=" + MyFcomplmt.getC6alpha1());
                System.out.println("  getC6alpha2()=" + MyFcomplmt.getC6alpha2());
                System.out.println("  getC6name()=" + MyFcomplmt.getC6name());
                System.out.println("  getC6access()=" + MyFcomplmt.getC6access());
                System.out.println("  getC6city()=" + MyFcomplmt.getC6city());
                System.out.println("  getC6tel()=" + MyFcomplmt.getC6tel());
                System.out.println("  getC6alpha3()=" + MyFcomplmt.getC6alpha3());
                System.out.println("  getC6alpha4()=" + MyFcomplmt.getC6alpha4());
                System.out.println("  getC6alpha5()=" + MyFcomplmt.getC6alpha5());
                System.out.println("  getC6alpha6()=" + MyFcomplmt.getC6alpha6());
                System.out.println("  getC6alpha7()=" + MyFcomplmt.getC6alpha7());
                System.out.println("  getC6int1()=" + MyFcomplmt.getC6int1());
                System.out.println("  getC6date()=" + MyFcomplmt.getC6date());
                System.out.println("  getC6date1()=" + MyFcomplmt.getC6date1());
                System.out.println("  getC6int3()=" + MyFcomplmt.getC6int3());
                System.out.println("  getC6int4()=" + MyFcomplmt.getC6int4());
                System.out.println("  getC6onum()=" + MyFcomplmt.getC6onum());
                System.out.println("  getC6corp()=" + MyFcomplmt.getC6corp());
                System.out.println("  getC6address()=" + MyFcomplmt.getC6address());
                System.out.println("  getC6address2()=" + MyFcomplmt.getC6address2());
                System.out.println("  getC6poscode()=" + MyFcomplmt.getC6poscode());
            }

// Essai suppression
            System.out.println("Deleting : " + MyFcomplmt1);
            MyFcomplmtDAO.delete(MyFcomplmt1.getC6num());
            System.out.println("Affected row(s)=" + MyFcomplmtDAO.getNbAffectedRow());

        } catch (IOException MyException) {
            System.out.println("Problem while reading database properties " + MyException);
        } catch (DBServerException MyException) {
            System.out.println("Problem while reading DBserver properties " + MyException);
        } catch (ClassNotFoundException MyException) {
            System.out.println("Problem while creating FcomplmtDAO " + MyException);
        } catch (SQLException MyException) {
            System.out.println("Problem while creating FcomplmtDAO " + MyException);
        }
    }

    @Override
    public void update(Object MyObject) {
        throw new UnsupportedOperationException("Non support� actuellement"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Object MyObject) {
        throw new UnsupportedOperationException("Non support� actuellement"); //To change body of generated methods, choose Tools | Templates.
    }
}
