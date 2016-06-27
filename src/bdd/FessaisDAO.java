package bdd;

import java.sql.*;

/**
 * Classe qui décrit les méthodes pour accéder aux tables fessais ou f99essais
 * au travers de JDBC.
 *
 * @author Thierry Baribaud.
 * @version Juin 2015.
 */
public class FessaisDAO extends PaternDAO {

    private String MyTable = "fessais";

    /**
     * Requête SQL pour récupérer un élement de clôture d'appel.
     */
    private String PartOfEOMStatement;
    
    /**
     * Requête SQL pour récupérer la première transmission.
     */
    private String FirstTransmissionStatement;

    /**
     * Requête SQL préparée pour récupérer la première transmission.
     */
    private PreparedStatement FirstTransmissionPreparedStatement;

    /**
     * ResultSet pour récupérer la première transmission.
     */
    private ResultSet FirstTransmissionResultSet;

    /**
     * Requête SQL pour récupérer la dernière transmission.
     */
    private String LastTransmissionStatement;

    /**
     * Requête SQL préparée pour récupérer la dernière transmission.
     */
    private PreparedStatement LastTransmissionPreparedStatement;

    /**
     * ResultSet pour récupérer la dernière transmission.
     */
    private ResultSet LastTransmissionResultSet;

    /**
     * ResultSet pour récupérer un élément de clôture d'appel.
     */
    private ResultSet PartOfEOMResultSet;

    /**
     * Requête SQL préparée pour récupérer un élément de clôture d'appel.
     */
    private PreparedStatement PartOfEOMPreparedStatement;

    /**
     * Constructeur de la classe FessaisDAO.
     *
     * @param MyConnection connexion à la base de données courante.
     * @param enumabs identifiant de l'essai,
     * @param ecnum identifiant de l'appel,
     * @param egid identifiant d'un groupe d'essais,
     * @param MyEtatTicket indique si l'on travaille sur les tickets en cours ou
     * archivés.
     * @throws ClassNotFoundException en cas de classe non trouvée.
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

        // Récupère la première transmission
        if (ecnum > 0) {
// A rétablir lorsque le bug touchant enumabs sera corrigé
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

        // Récupère la dernière transmission
        if (ecnum > 0) {
// A rétablir lorsque le bug touchant enumabs sera corrigé
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
        
        // Récupère un élément de clôture d'appel
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
     * @return l'essai sélectionné.
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
                System.out.println("Lecture de " + MyTable + " terminée");
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur en lecture de " + MyTable + " "
                    + MyException.getMessage());
        }
        return MyFessais;
    }

    /**
     * Met à jour un essai.
     *
     * @param MyFessais essai à mettre à jour.
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
                System.out.println("Impossible de mettre à jour " + MyTable);
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de la mise à jour de " + MyTable
                    + " " + MyException.getMessage());
        }
    }

    /**
     * Supprime définitivement un essai.
     *
     * @param enumabs identiant de l'essai à supprimer.
     */
    @Override
    public void delete(int enumabs) {
        try {
            DeletePreparedStatement.setInt(1, enumabs);
            setNbAffectedRow(DeletePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de détruire un essai dans " + MyTable);
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de la suppression d'un essai dans "
                    + MyTable + " " + MyException.getMessage());
        }
    }

    /**
     * Ajoute un essai dans la table fessais ou f99essais.
     *
     * @param MyFessais essai à ajouter à la table fessais ou f99essais.
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
     * Prépare la requête SQL pour rechercher la première transmission.
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
     * Exécute la requête SQL pour rechercher la première transmission.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void setFirstTransmissionResultSet() throws SQLException {
        FirstTransmissionResultSet = FirstTransmissionPreparedStatement.executeQuery();
    }

    /**
     * Récupère l'essai correspondant à la première transmission, s'il existe.
     *
     * @return l'essai correspondant à la première transmission, s'il existe.
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
     * Prépare la requête SQL pour rechercher la première transmission.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void setLastTransmissionPreparedStatement() throws SQLException {
        LastTransmissionPreparedStatement = MyConnection.prepareStatement(getLastTransmissionStatement());
    }

    /**
     * Prépare la requête SQL pour rechercher la première transmission.
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
     * Exécute la requête SQL pour rechercher la dernière transmission.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void setLastTransmissionResultSet() throws SQLException {
        LastTransmissionResultSet = LastTransmissionPreparedStatement.executeQuery();
    }

    /**
     * Récupère l'essai correspondant à la dernière transmission, s'il existe.
     *
     * @return l'essai correspondant à la dernière transmission, s'il existe.
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
     * Récupère l'essai correspondant à un élément de clôture d'appel, s'il existe.
     *
     * @return l'essai correspondant à un élément de clôture d'appel, s'il existe.
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
     * Exécute la requête SQL pour rechercher un élément de clôture d'appel.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void setPartOfEOMResultSet() throws SQLException {
        PartOfEOMResultSet = PartOfEOMPreparedStatement.executeQuery();
    }

    /**
     * Méthode qui ferme toutes les ressources de bases de données.
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
        throw new UnsupportedOperationException("Non supporté actuellement"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Object MyObject) {
        throw new UnsupportedOperationException("Non supporté actuellement"); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return PartOfEOMStatement requête SQL qui retourne un élément de clôture d'appel.
     */
    public String getPartOfEOMStatement() {
        return PartOfEOMStatement;
    }

    /**
     * @param PartOfEOMStatement définit la requête SQL qui retourne un élément de clôture d'appel.
     */
    public void setPartOfEOMStatement(String PartOfEOMStatement) {
        this.PartOfEOMStatement = PartOfEOMStatement;
    }
}
