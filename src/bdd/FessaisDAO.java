package bdd;

import java.sql.*;

/**
 * Classe qui d�crit les m�thodes pour acc�der aux tables fessais ou f99essais
 * au travers de JDBC.
 *
 * @author Thierry Baribaud.
 * @version Juin 2015.
 */
public class FessaisDAO extends PaternDAO {

    private String MyTable = "fessais";

    /**
     * Requ�te SQL pour r�cup�rer un �lement de cl�ture d'appel.
     */
    private String PartOfEOMStatement;
    
    /**
     * Requ�te SQL pour r�cup�rer la premi�re transmission.
     */
    private String FirstTransmissionStatement;

    /**
     * Requ�te SQL pr�par�e pour r�cup�rer la premi�re transmission.
     */
    private PreparedStatement FirstTransmissionPreparedStatement;

    /**
     * ResultSet pour r�cup�rer la premi�re transmission.
     */
    private ResultSet FirstTransmissionResultSet;

    /**
     * Requ�te SQL pour r�cup�rer la derni�re transmission.
     */
    private String LastTransmissionStatement;

    /**
     * Requ�te SQL pr�par�e pour r�cup�rer la derni�re transmission.
     */
    private PreparedStatement LastTransmissionPreparedStatement;

    /**
     * ResultSet pour r�cup�rer la derni�re transmission.
     */
    private ResultSet LastTransmissionResultSet;

    /**
     * ResultSet pour r�cup�rer un �l�ment de cl�ture d'appel.
     */
    private ResultSet PartOfEOMResultSet;

    /**
     * Requ�te SQL pr�par�e pour r�cup�rer un �l�ment de cl�ture d'appel.
     */
    private PreparedStatement PartOfEOMPreparedStatement;

    /**
     * Constructeur de la classe FessaisDAO.
     *
     * @param MyConnection connexion � la base de donn�es courante.
     * @param enumabs identifiant de l'essai,
     * @param ecnum identifiant de l'appel,
     * @param egid identifiant d'un groupe d'essais,
     * @param MyEtatTicket indique si l'on travaille sur les tickets en cours ou
     * archiv�s.
     * @throws ClassNotFoundException en cas de classe non trouv�e.
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public FessaisDAO(Connection MyConnection, int enumabs, int ecnum, int egid, EtatTicket MyEtatTicket)
            throws ClassNotFoundException, SQLException {

        StringBuffer Stmt;

        MyTable = EtatTicket.EN_COURS.equals(MyEtatTicket) ? "fessais" : "f99essais";

        this.MyConnection = MyConnection;

        Stmt = new StringBuffer("select enumabs, ecnum, eptr, eunum, edate, etime,"
                + " emessage, etnum, eonum, eresult, eduration, etest, einternal,"
                + " em3num, egid"
                + " from " + MyTable
                + " where enumabs > 0");
        if (enumabs > 0) {
            Stmt.append(" and enumabs = ").append(enumabs);
        }
        if (ecnum > 0) {
            Stmt.append(" and ecnum = ").append(ecnum);
        }
        if (egid > 0) {
            Stmt.append(" and egid = ").append(egid);
        }
        Stmt.append(" order by enumabs;");
//        System.out.println(Stmt);
        setReadStatement(Stmt.toString());
        setReadPreparedStatement();
        setReadResultSet();

        setUpdateStatement("update " + MyTable
                + " set ecnum=?, eptr=?, eunum=?, edate=?, etime=?,"
                + " emessage=?, etnum=?, eonum=?, eresult=?,"
                + " eduration=?, etest=?, einternal=?, em3num=?, egid=?"
                + " where enumabs=?;");
        setUpdatePreparedStatement();

        setInsertStatement("insert into " + MyTable
                + " (ecnum, eptr, eunum, edate, etime,"
                + " emessage, etnum, eonum, eresult, eduration,"
                + " etest, einternal, em3num, egid)"
                + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
        setInsertPreparedStatement();

        setDeleteStatement("delete from " + MyTable + " where enumabs=?;");
        setDeletePreparedStatement();

        // R�cup�re la premi�re transmission
        if (ecnum > 0) {
// A r�tablir lorsque le bug touchant enumabs sera corrig�
//            setFirstTransmissionStatement("select a.* from " + MyTable + " a"
//                    + " where a.enumabs = (select min(b.enumabs) from "
//                    + MyTable + " b where b.ecnum = " + ecnum
//                    + " and b.eresult = 1);");
            setFirstTransmissionStatement("select enumabs, "
                    + " ecnum, eptr, eunum, edate, etime,"
                    + " emessage, etnum, eonum, eresult, eduration,"
                    + " etest, einternal, em3num, egid"
                    + " from " + MyTable 
                    + " where ecnum = " + ecnum
                    + " and eresult = 1"
                    + " order by edate, etime;");
            setFirstTransmissionPreparedStatement();
            setFirstTransmissionResultSet();
        }

        // R�cup�re la derni�re transmission
        if (ecnum > 0) {
// A r�tablir lorsque le bug touchant enumabs sera corrig�
//            setLastTransmissionStatement("select a.* from " + MyTable + " a"
//                    + " where a.enumabs = (select max(b.enumabs) from "
//                    + MyTable + " b where b.ecnum = " + ecnum
//                    + " and b.eresult = 1);");
            setLastTransmissionStatement("select enumabs, "
                    + " ecnum, eptr, eunum, edate, etime,"
                    + " emessage, etnum, eonum, eresult, eduration,"
                    + " etest, einternal, em3num, egid"
                    + " from " + MyTable 
                    + " where ecnum = " + ecnum
                    + " and eresult = 1"
                    + " order by edate desc, etime desc;");
            setLastTransmissionPreparedStatement();
            setLastTransmissionResultSet();
        }
        
        // R�cup�re un �l�ment de cl�ture d'appel
        if (ecnum > 0) {
            setPartOfEOMStatement("select a.enumabs, "
                    + " a.ecnum, a.eptr, a.eunum, a.edate, a.etime,"
                    + " a.emessage, a.etnum, a.eonum, a.eresult, a.eduration,"
                    + " a.etest, a.einternal, a.em3num, a.egid"
                    + " from " + MyTable + " a"
                    + " where a.enumabs = (select max(b.enumabs) from "
                    + MyTable + " b where b.ecnum = " + ecnum
                    + " and b.eresult in (69,70,71,72,73,93));");
//            System.out.println("  PartOfEOMStatement=" + getPartOfEOMStatement());
            setPartOfEOMPreparedStatement();
            setPartOfEOMResultSet();
        }
    }

    /**
     * Selectionne un essai.
     *
     * @return l'essai s�lectionn�.
     */
    @Override
    public Fessais select() {
        Fessais MyFessais = null;

        try {
            if (ReadResultSet.next()) {
                MyFessais = new Fessais();
                MyFessais.setEnumabs(ReadResultSet.getInt("enumabs"));
                MyFessais.setEcnum(ReadResultSet.getInt("ecnum"));
                MyFessais.setEptr(ReadResultSet.getInt("eptr"));
                MyFessais.setEunum(ReadResultSet.getInt("eunum"));
                MyFessais.setEdate(ReadResultSet.getTimestamp("edate"));
                MyFessais.setEtime(ReadResultSet.getString("etime"));
                MyFessais.setEmessage(ReadResultSet.getString("emessage"));
                MyFessais.setEtnum(ReadResultSet.getInt("etnum"));
                MyFessais.setEonum(ReadResultSet.getInt("eonum"));
                MyFessais.setEresult(ReadResultSet.getInt("eresult"));
                MyFessais.setEduration(ReadResultSet.getInt("eduration"));
                MyFessais.setEtest(ReadResultSet.getInt("etest"));
                MyFessais.setEinternal(ReadResultSet.getInt("einternal"));
                MyFessais.setEm3num(ReadResultSet.getInt("em3num"));
                MyFessais.setEgid(ReadResultSet.getInt("egid"));
            } else {
                System.out.println("Lecture de " + MyTable + " termin�e");
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur en lecture de " + MyTable + " "
                    + MyException.getMessage());
        }
        return MyFessais;
    }

    /**
     * Met � jour un essai.
     *
     * @param MyFessais essai � mettre � jour.
     */
    public void update(Fessais MyFessais) {
        try {
            UpdatePreparedStatement.setInt(1, MyFessais.getEcnum());
            UpdatePreparedStatement.setInt(2, MyFessais.getEptr());
            UpdatePreparedStatement.setInt(3, MyFessais.getEunum());
            UpdatePreparedStatement.setTimestamp(4, MyFessais.getEdate());
            UpdatePreparedStatement.setString(5, MyFessais.getEtime());
            UpdatePreparedStatement.setString(6, MyFessais.getEmessage());
            UpdatePreparedStatement.setInt(7, MyFessais.getEtnum());
            UpdatePreparedStatement.setInt(8, MyFessais.getEonum());
            UpdatePreparedStatement.setInt(9, MyFessais.getEresult());
            UpdatePreparedStatement.setInt(10, MyFessais.getEduration());
            UpdatePreparedStatement.setInt(11, MyFessais.getEtest());
            UpdatePreparedStatement.setInt(12, MyFessais.getEinternal());
            UpdatePreparedStatement.setInt(13, MyFessais.getEm3num());
            UpdatePreparedStatement.setInt(14, MyFessais.getEgid());
            UpdatePreparedStatement.setInt(15, MyFessais.getEnumabs());
            setNbAffectedRow(UpdatePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de mettre � jour " + MyTable);
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de la mise � jour de " + MyTable
                    + " " + MyException.getMessage());
        }
    }

    /**
     * Supprime d�finitivement un essai.
     *
     * @param enumabs identiant de l'essai � supprimer.
     */
    @Override
    public void delete(int enumabs) {
        try {
            DeletePreparedStatement.setInt(1, enumabs);
            setNbAffectedRow(DeletePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de d�truire un essai dans " + MyTable);
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de la suppression d'un essai dans "
                    + MyTable + " " + MyException.getMessage());
        }
    }

    /**
     * Ajoute un essai dans la table fessais ou f99essais.
     *
     * @param MyFessais essai � ajouter � la table fessais ou f99essais.
     */
    public void insert(Fessais MyFessais) {
        ResultSet MyKeyResultSet;

        try {
            System.out.println("enumabs=" + MyFessais.getEnumabs());
            InsertPreparedStatement.setInt(1, MyFessais.getEcnum());
            InsertPreparedStatement.setInt(2, MyFessais.getEptr());
            InsertPreparedStatement.setInt(3, MyFessais.getEunum());
            InsertPreparedStatement.setTimestamp(4, MyFessais.getEdate());
            InsertPreparedStatement.setString(5, MyFessais.getEtime());
            InsertPreparedStatement.setString(6, MyFessais.getEmessage());
            InsertPreparedStatement.setInt(7, MyFessais.getEtnum());
            InsertPreparedStatement.setInt(8, MyFessais.getEonum());
            InsertPreparedStatement.setInt(9, MyFessais.getEresult());
            InsertPreparedStatement.setInt(10, MyFessais.getEduration());
            InsertPreparedStatement.setInt(11, MyFessais.getEtest());
            InsertPreparedStatement.setInt(12, MyFessais.getEinternal());
            InsertPreparedStatement.setInt(13, MyFessais.getEm3num());
            InsertPreparedStatement.setInt(14, MyFessais.getEgid());
            setNbAffectedRow(InsertPreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible d'ajouter un essai dans " + MyTable);
            } else {
                MyKeyResultSet = InsertPreparedStatement.getGeneratedKeys();
                if (MyKeyResultSet.next()) {
                    MyFessais.setEnumabs((int) MyKeyResultSet.getInt(1));
                }
                MyKeyResultSet.close();
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de l'insertion d'un essai dans "
                    + MyTable + " " + MyException.getMessage());
        }
    }

    /**
     * @return the FirstTransmissionStatement
     */
    public String getFirstTransmissionStatement() {
        return FirstTransmissionStatement;
    }

    /**
     * @param FirstTransmissionStatement the FirstTransmissionStatement to set
     */
    public void setFirstTransmissionStatement(String FirstTransmissionStatement) {
        this.FirstTransmissionStatement = FirstTransmissionStatement;
    }

    /**
     * @return the FirstTransmissionPreparedStatement
     */
    public PreparedStatement getFirstTransmissionPreparedStatement() {
        return FirstTransmissionPreparedStatement;
    }

    /**
     * Pr�pare la requ�te SQL pour rechercher la premi�re transmission.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void setFirstTransmissionPreparedStatement() throws SQLException {
        FirstTransmissionPreparedStatement = MyConnection.prepareStatement(getFirstTransmissionStatement());
    }

    /**
     * @return the FirstTransmissionResultSet
     */
    public ResultSet getFirstTransmissionResultSet() {
        return FirstTransmissionResultSet;
    }

    /**
     * Ex�cute la requ�te SQL pour rechercher la premi�re transmission.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void setFirstTransmissionResultSet() throws SQLException {
        FirstTransmissionResultSet = FirstTransmissionPreparedStatement.executeQuery();
    }

    /**
     * R�cup�re l'essai correspondant � la premi�re transmission, s'il existe.
     *
     * @return l'essai correspondant � la premi�re transmission, s'il existe.
     */
    public Fessais getFirstTransmission() {
        Fessais MyFessais = null;

        try {
            if (FirstTransmissionResultSet.next()) {
                MyFessais = new Fessais();
                MyFessais.setEnumabs(FirstTransmissionResultSet.getInt("enumabs"));
                MyFessais.setEcnum(FirstTransmissionResultSet.getInt("ecnum"));
                MyFessais.setEptr(FirstTransmissionResultSet.getInt("eptr"));
                MyFessais.setEunum(FirstTransmissionResultSet.getInt("eunum"));
                MyFessais.setEdate(FirstTransmissionResultSet.getTimestamp("edate"));
                MyFessais.setEtime(FirstTransmissionResultSet.getString("etime"));
                MyFessais.setEmessage(FirstTransmissionResultSet.getString("emessage"));
                MyFessais.setEtnum(FirstTransmissionResultSet.getInt("etnum"));
                MyFessais.setEonum(FirstTransmissionResultSet.getInt("eonum"));
                MyFessais.setEresult(FirstTransmissionResultSet.getInt("eresult"));
                MyFessais.setEduration(FirstTransmissionResultSet.getInt("eduration"));
                MyFessais.setEtest(FirstTransmissionResultSet.getInt("etest"));
                MyFessais.setEinternal(FirstTransmissionResultSet.getInt("einternal"));
                MyFessais.setEm3num(FirstTransmissionResultSet.getInt("em3num"));
                MyFessais.setEgid(FirstTransmissionResultSet.getInt("egid"));
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur en lecture de " + MyTable + " "
                    + MyException.getMessage());
        }
        return (MyFessais);
    }

    /**
     * @return the LastTransmissionStatement
     */
    public String getLastTransmissionStatement() {
        return LastTransmissionStatement;
    }

    /**
     * @param LastTransmissionStatement the LastTransmissionStatement to set
     */
    public void setLastTransmissionStatement(String LastTransmissionStatement) {
        this.LastTransmissionStatement = LastTransmissionStatement;
    }

    /**
     * @return the LastTransmissionPreparedStatement
     */
    public PreparedStatement getLastTransmissionPreparedStatement() {
        return LastTransmissionPreparedStatement;
    }

    /**
     * Pr�pare la requ�te SQL pour rechercher la premi�re transmission.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void setLastTransmissionPreparedStatement() throws SQLException {
        LastTransmissionPreparedStatement = MyConnection.prepareStatement(getLastTransmissionStatement());
    }

    /**
     * Pr�pare la requ�te SQL pour rechercher la premi�re transmission.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void setPartOfEOMPreparedStatement() throws SQLException {
        PartOfEOMPreparedStatement = MyConnection.prepareStatement(getPartOfEOMStatement());
    }

    /**
     * @return LastTransmissionResultSet 
     */
    public ResultSet getLastTransmissionResultSet() {
        return LastTransmissionResultSet;
    }

    /**
     * @return PartOfEOMResultSet 
     */
    public ResultSet getPartOfEOMResultSet() {
        return PartOfEOMResultSet;
    }
    /**
     * Ex�cute la requ�te SQL pour rechercher la derni�re transmission.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void setLastTransmissionResultSet() throws SQLException {
        LastTransmissionResultSet = LastTransmissionPreparedStatement.executeQuery();
    }

    /**
     * R�cup�re l'essai correspondant � la derni�re transmission, s'il existe.
     *
     * @return l'essai correspondant � la derni�re transmission, s'il existe.
     */
    public Fessais getLastTransmission() {
        Fessais MyFessais = null;

        try {
            if (LastTransmissionResultSet.next()) {
                MyFessais = new Fessais();
                MyFessais.setEnumabs(LastTransmissionResultSet.getInt("enumabs"));
                MyFessais.setEcnum(LastTransmissionResultSet.getInt("ecnum"));
                MyFessais.setEptr(LastTransmissionResultSet.getInt("eptr"));
                MyFessais.setEunum(LastTransmissionResultSet.getInt("eunum"));
                MyFessais.setEdate(LastTransmissionResultSet.getTimestamp("edate"));
                MyFessais.setEtime(LastTransmissionResultSet.getString("etime"));
                MyFessais.setEmessage(LastTransmissionResultSet.getString("emessage"));
                MyFessais.setEtnum(LastTransmissionResultSet.getInt("etnum"));
                MyFessais.setEonum(LastTransmissionResultSet.getInt("eonum"));
                MyFessais.setEresult(LastTransmissionResultSet.getInt("eresult"));
                MyFessais.setEduration(LastTransmissionResultSet.getInt("eduration"));
                MyFessais.setEtest(LastTransmissionResultSet.getInt("etest"));
                MyFessais.setEinternal(LastTransmissionResultSet.getInt("einternal"));
                MyFessais.setEm3num(LastTransmissionResultSet.getInt("em3num"));
                MyFessais.setEgid(LastTransmissionResultSet.getInt("egid"));
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur en lecture de " + MyTable + " "
                    + MyException.getMessage());
        }
        return (MyFessais);
    }

    /**
     * R�cup�re l'essai correspondant � un �l�ment de cl�ture d'appel, s'il existe.
     *
     * @return l'essai correspondant � un �l�ment de cl�ture d'appel, s'il existe.
     */
    public Fessais getPartOfEOM() {
        Fessais MyFessais = null;

        try {
            if (PartOfEOMResultSet.next()) {
                MyFessais = new Fessais();
                MyFessais.setEnumabs(PartOfEOMResultSet.getInt("enumabs"));
                MyFessais.setEcnum(PartOfEOMResultSet.getInt("ecnum"));
                MyFessais.setEptr(PartOfEOMResultSet.getInt("eptr"));
                MyFessais.setEunum(PartOfEOMResultSet.getInt("eunum"));
                MyFessais.setEdate(PartOfEOMResultSet.getTimestamp("edate"));
                MyFessais.setEtime(PartOfEOMResultSet.getString("etime"));
                MyFessais.setEmessage(PartOfEOMResultSet.getString("emessage"));
                MyFessais.setEtnum(PartOfEOMResultSet.getInt("etnum"));
                MyFessais.setEonum(PartOfEOMResultSet.getInt("eonum"));
                MyFessais.setEresult(PartOfEOMResultSet.getInt("eresult"));
                MyFessais.setEduration(PartOfEOMResultSet.getInt("eduration"));
                MyFessais.setEtest(PartOfEOMResultSet.getInt("etest"));
                MyFessais.setEinternal(PartOfEOMResultSet.getInt("einternal"));
                MyFessais.setEm3num(PartOfEOMResultSet.getInt("em3num"));
                MyFessais.setEgid(PartOfEOMResultSet.getInt("egid"));
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur en lecture de " + MyTable + " "
                    + MyException.getMessage());
        }
        return (MyFessais);
    }

    /**
     * Ex�cute la requ�te SQL pour rechercher un �l�ment de cl�ture d'appel.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void setPartOfEOMResultSet() throws SQLException {
        PartOfEOMResultSet = PartOfEOMPreparedStatement.executeQuery();
    }

    /**
     * M�thode qui ferme toutes les ressources de bases de donn�es.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    @Override
    public void close() throws SQLException {

        super.close();
        FirstTransmissionPreparedStatement.close();
        LastTransmissionPreparedStatement.close();
        PartOfEOMPreparedStatement.close();
    }

    @Override
    public void update(Object MyObject) {
        throw new UnsupportedOperationException("Non support� actuellement"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Object MyObject) {
        throw new UnsupportedOperationException("Non support� actuellement"); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return PartOfEOMStatement requ�te SQL qui retourne un �l�ment de cl�ture d'appel.
     */
    public String getPartOfEOMStatement() {
        return PartOfEOMStatement;
    }

    /**
     * @param PartOfEOMStatement d�finit la requ�te SQL qui retourne un �l�ment de cl�ture d'appel.
     */
    public void setPartOfEOMStatement(String PartOfEOMStatement) {
        this.PartOfEOMStatement = PartOfEOMStatement;
    }
}
