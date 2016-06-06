package bdd;

import java.io.IOException;
import java.sql.*;
import utils.ApplicationProperties;
import utils.DBManager;
import utils.DBServer;
import utils.DBServerException;

/**
 * Class that describes the ways to access table fagency through JDBC.
 *
 * @author Thierry Baribaud.
 * @version Mai 2015.
 */
public class FagencyDAO extends PaternDAO {

    /**
     * Class constructor #1.
     *
     * @param MyConnection an active connection to a database.
     * @param myA6num agency's ID,
     * @throws ClassNotFoundException, SQLException.
     * @throws java.sql.SQLException
     */
    public FagencyDAO(Connection MyConnection, int myA6num)
            throws ClassNotFoundException, SQLException {

        this(MyConnection, myA6num, 0, null);
    }

    /**
     * Class constructor #2.
     *
     * @param MyConnection an active connection to a database.
     * @param MyA6unum customer's ID,
     * @param MyA6name optional parameter that enable filtering on agency's
     * name.
     * @throws ClassNotFoundException, SQLException.
     * @throws java.sql.SQLException
     */
    public FagencyDAO(Connection MyConnection, int MyA6unum, String MyA6name)
            throws ClassNotFoundException, SQLException {

        this(MyConnection, 0, MyA6unum, MyA6name);
    }

    /**
     * Class constructor #3.
     *
     * @param MyConnection an active connection to a database.
     * @param myA6num agency's ID,
     * @param MyA6unum customer's ID,
     * @param MyA6name optional parameter that enable filtering on agency's
     * name.
     * @throws ClassNotFoundException, SQLException.
     * @throws java.sql.SQLException
     */
    public FagencyDAO(Connection MyConnection, int myA6num, int MyA6unum, String MyA6name)
            throws ClassNotFoundException, SQLException {

        StringBuffer Stmt;

        this.MyConnection = MyConnection;

        Stmt = new StringBuffer("select a6num, a6unum, a6extname, a6name, a6abbname, a6email,"
                + " a6daddress, a6daddress2, a6dposcode, a6dcity,"
                + " a6teloff, a6teldir, a6telfax,"
                + " a6active, a6begactive, a6endactive"
                + " from fagency");

        if (myA6num > 0) {
            Stmt.append(" where a6num = ").append(myA6num);
        } else {
            Stmt.append(" where a6num >0");
        }
        if (MyA6unum > 0) {
            Stmt.append(" and a6unum = ").append(MyA6unum);
        }
        if (MyA6name != null) {
            Stmt.append(" and a6name like \"").append(MyA6name).append("%\"");
        }
        Stmt.append(" order by a6num;");
        setReadStatement(Stmt.toString());
        setReadPreparedStatement();
        setReadResultSet();

        setUpdateStatement("update fagency"
                + " set a6unum=?, a6extname=?, a6name=?, a6abbname=?, a6email=?,"
                + " a6daddress=?, a6daddress2=?, a6dposcode=?, a6dcity=?,"
                + " a6teloff=?, a6teldir=?, a6telfax=?,"
                + " a6active=?, a6begactive=?, a6endactive=?"
                + " where a6num=?;");
        setUpdatePreparedStatement();

        setInsertStatement("insert into fagency"
                + " (a6unum, a6extname, a6name, a6abbname, a6email,"
                + " a6daddress, a6daddress2, a6dposcode, a6dcity,"
                + " a6teloff, a6teldir, a6telfax,"
                + " a6active, a6begactive, a6endactive)"
                + " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
        setInsertPreparedStatement();

        setDeleteStatement("delete from fagency"
                + " where a6num=?;");
        setDeletePreparedStatement();
    }

    /**
     * Select a record.
     *
     * @return the agency selected
     */
    @Override
    public Fagency select() {
        Fagency MyFagency = null;

        try {
            if (ReadResultSet.next()) {
                MyFagency = new Fagency();
                MyFagency.setA6num(ReadResultSet.getInt("a6num"));
                MyFagency.setA6unum(ReadResultSet.getInt("a6unum"));
                MyFagency.setA6extname(ReadResultSet.getString("a6extname"));
                MyFagency.setA6name(ReadResultSet.getString("a6name"));
                MyFagency.setA6abbname(ReadResultSet.getString("a6abbname"));
                MyFagency.setA6email(ReadResultSet.getString("a6email"));
                MyFagency.setA6daddress(ReadResultSet.getString("a6daddress"));
                MyFagency.setA6daddress2(ReadResultSet.getString("a6daddress2"));
                MyFagency.setA6dposcode(ReadResultSet.getString("a6dposcode"));
                MyFagency.setA6dcity(ReadResultSet.getString("a6dcity"));
                MyFagency.setA6teloff(ReadResultSet.getString("a6teloff"));
                MyFagency.setA6teldir(ReadResultSet.getString("a6teldir"));
                MyFagency.setA6telfax(ReadResultSet.getString("a6telfax"));
                MyFagency.setA6active(ReadResultSet.getInt("a6active"));
                MyFagency.setA6begactive(ReadResultSet.getTimestamp("a6begactive"));
                MyFagency.setA6endactive(ReadResultSet.getTimestamp("a6endactive"));
            } else {
                System.out.println("No more record in fagency");
            }
        } catch (SQLException MyException) {
            System.out.println("Problem reading fagency record "
                    + MyException.getMessage());
        }
        return MyFagency;
    }

    /**
     * Update a record.
     *
     * @param MyFagency agency to update
     */
    public void update(Fagency MyFagency) {
        try {
            UpdatePreparedStatement.setInt(1, MyFagency.getA6unum());
            UpdatePreparedStatement.setString(2, MyFagency.getA6extname());
            UpdatePreparedStatement.setString(3, MyFagency.getA6name());
            UpdatePreparedStatement.setString(4, MyFagency.getA6abbname());
            UpdatePreparedStatement.setString(5, MyFagency.getA6email());
            UpdatePreparedStatement.setString(6, MyFagency.getA6daddress());
            UpdatePreparedStatement.setString(7, MyFagency.getA6daddress2());
            UpdatePreparedStatement.setString(8, MyFagency.getA6dposcode());
            UpdatePreparedStatement.setString(9, MyFagency.getA6dcity());
            UpdatePreparedStatement.setString(10, MyFagency.getA6teloff());
            UpdatePreparedStatement.setString(11, MyFagency.getA6teldir());
            UpdatePreparedStatement.setString(12, MyFagency.getA6telfax());
            UpdatePreparedStatement.setInt(13, MyFagency.getA6active());
            UpdatePreparedStatement.setTimestamp(14, MyFagency.getA6begactive());
            UpdatePreparedStatement.setTimestamp(15, MyFagency.getA6endactive());
            UpdatePreparedStatement.setInt(16, MyFagency.getA6num());
            setNbAffectedRow(UpdatePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Failed to update data into fagency");
            }
        } catch (SQLException MyException) {
            System.out.println("Problem reading fagency record "
                    + MyException.getMessage());
        }
    }

    /**
     * Delete a record.
     *
     * @param a6num agency identifier
     */
    @Override
    public void delete(int a6num) {
        try {
            DeletePreparedStatement.setInt(1, a6num);
            setNbAffectedRow(DeletePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Failed to delete data from fagency");
            }
        } catch (SQLException e) {
            System.out.println("Problem deleting fagency record "
                    + e.getMessage());
        }
    }

    /**
     * Insert a record.
     *
     * @param MyFagency agency to insert in database
     */
    public void insert(Fagency MyFagency) {
        ResultSet MyKeyResultSet = null;

        try {
            System.out.println("a6name=" + MyFagency.getA6name());
            InsertPreparedStatement.setInt(1, MyFagency.getA6unum());
            InsertPreparedStatement.setString(2, MyFagency.getA6extname());
            InsertPreparedStatement.setString(3, MyFagency.getA6name());
            InsertPreparedStatement.setString(4, MyFagency.getA6abbname());
            InsertPreparedStatement.setString(5, MyFagency.getA6email());
            InsertPreparedStatement.setString(6, MyFagency.getA6daddress());
            InsertPreparedStatement.setString(7, MyFagency.getA6daddress2());
            InsertPreparedStatement.setString(8, MyFagency.getA6dposcode());
            InsertPreparedStatement.setString(9, MyFagency.getA6dcity());
            InsertPreparedStatement.setString(10, MyFagency.getA6teloff());
            InsertPreparedStatement.setString(11, MyFagency.getA6teldir());
            InsertPreparedStatement.setString(12, MyFagency.getA6telfax());
            InsertPreparedStatement.setInt(13, MyFagency.getA6active());
            InsertPreparedStatement.setTimestamp(14, MyFagency.getA6begactive());
            InsertPreparedStatement.setTimestamp(15, MyFagency.getA6endactive());
            setNbAffectedRow(InsertPreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Failed to insert data into fagency");
            } else {
                MyKeyResultSet = InsertPreparedStatement.getGeneratedKeys();
                if (MyKeyResultSet.next()) {
                    MyFagency.setA6num((int) MyKeyResultSet.getInt(1));
                }
            }
            MyKeyResultSet.close();
        } catch (SQLException MyException) {
            System.out.println("Problem inserting fagency record "
                    + MyException.getMessage());
        }
    }

    /**
     * Main method to test FagencyDAO class.
     *
     * @param Args command line arguments. First argument must be the type of
     * Database server : dev/pre-prod/prod. Second argument must be the name of
     * the application properties file to load.
     */
    public static void main(String[] Args) {
        ApplicationProperties MyApplicationProperties;
        DBServer MyDBServer;
        DBManager MyDBManager;
        FagencyDAO MyFagencyDAO;
        Fagency MyFagency1;
        Fagency MyFagency;
        long i;
        //String MyA6name = "france";
        String MyA6name = null;
        int MyA6unum = 552;

        if (Args.length != 2) {
            System.out.println("Usage : java FagencyDAO server-type filename");
            System.exit(0);
        }

        try {
            MyApplicationProperties = new ApplicationProperties(Args[1]);

            MyDBServer = new DBServer(Args[0], MyApplicationProperties);
            MyDBManager = new DBManager(MyDBServer);

// Essai insertion
            MyFagencyDAO = new FagencyDAO(MyDBManager.getConnection(), MyA6unum, MyA6name);
            MyFagency1 = new Fagency();
            MyFagency1.setA6unum(99999);
            MyFagency1.setA6extname("terra incognita");
            MyFagency1.setA6name("utopia");
            MyFagency1.setA6abbname("UTOPIA");
            MyFagency1.setA6email("utopia@gmail.com");
            MyFagency1.setA6daddress("12, rue des rèves");
            MyFagency1.setA6daddress2("bâtiment B");
            MyFagency1.setA6dposcode("92400");
            MyFagency1.setA6dcity("UTOPIA CITY");
            MyFagency1.setA6teloff("01.01.01.01.01");
            MyFagency1.setA6teldir("02.02.02.02.02");
            MyFagency1.setA6telfax("03.03.03.03.03");
            MyFagency1.setA6active(1);
            MyFagency1.setA6begactive(new Timestamp(new java.util.Date().getTime()));
            MyFagency1.setA6endactive(Timestamp.valueOf("2050-12-31 23:59:59.0"));
            System.out.println("Fagency(before insert)=" + MyFagency1);
            MyFagencyDAO.insert(MyFagency1);
            System.out.println("Fagency(after insert)=" + MyFagency1);
            System.out.println("Affected row(s)=" + MyFagencyDAO.getNbAffectedRow());

// Essai mise Ã  jour
            MyFagency1.setA6email(MyFagency1.getA6email() + ",utopia@free.fr");
            MyFagencyDAO.update(MyFagency1);
            System.out.println("Fagency(after update)=" + MyFagency1);
            System.out.println("Affected row(s)=" + MyFagencyDAO.getNbAffectedRow());
            MyFagencyDAO.close();

// Essai lecture
            MyFagencyDAO = new FagencyDAO(MyDBManager.getConnection(), MyA6unum, MyA6name);
            i = 0;
            while ((MyFagency = MyFagencyDAO.select()) != null) {
                i++;
                System.out.println("Fagency(" + i + ")=" + MyFagency);
                System.out.println("  getA6num()=" + MyFagency.getA6num());
                System.out.println("  getA6unum()=" + MyFagency.getA6unum());
                System.out.println("  getA6extname()=" + MyFagency.getA6extname());
                System.out.println("  getA6name()=" + MyFagency.getA6name());
                System.out.println("  getA6abbname()=" + MyFagency.getA6abbname());
                System.out.println("  getA6email()=" + MyFagency.getA6email());
                System.out.println("  getA6daddress()=" + MyFagency.getA6daddress());
                System.out.println("  getA6daddress2()=" + MyFagency.getA6daddress2());
                System.out.println("  getA6dposcode()=" + MyFagency.getA6dposcode());
                System.out.println("  getA6dcity()=" + MyFagency.getA6dcity());
                System.out.println("  getA6teloff()=" + MyFagency.getA6teloff());
                System.out.println("  getA6teldir()=" + MyFagency.getA6teldir());
                System.out.println("  getA6telfax()=" + MyFagency.getA6telfax());
                System.out.println("  getA6active()=" + MyFagency.getA6active());
                System.out.println("  getA6begactive()=" + MyFagency.getA6begactive());
                System.out.println("  getA6endactive()=" + MyFagency.getA6endactive());
            }

// Essai suppression
            System.out.println("Deleting : " + MyFagency1);
            MyFagencyDAO.delete(MyFagency1.getA6num());
            System.out.println("Affected row(s)=" + MyFagencyDAO.getNbAffectedRow());

        } catch (IOException | DBServerException | ClassNotFoundException | SQLException MyException) {
            System.out.println("Problem while creating FagencyDAO " + MyException);
        }
    }

    @Override
    public void update(Object MyObject) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Object MyObject) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
