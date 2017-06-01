package bdd;

import com.informix.jdbc.IfmxStatement;
import java.sql.*;

/**
 * Classe qui décrit les méthodes pour accéder aux tables fessais ou f99essais
 * au travers de JDBC.
 *
 * @author Thierry Baribaud.
 * @version 0.17
 */
public class FessaisDAO extends PatternDAO {

    /**
     * Table à gérér : fessais ou f99essais.
     */
    private String MyTable = "fessais";

    /**
     * Identifiant d'un appel.
     */
    private int cnum = 0;

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
     * Requête SQL pour récupérer le dernier essai.
     */
    private String LastTrialStatement;

    /**
     * Requête SQL préparée pour récupérer le dernier essai.
     */
    private PreparedStatement LastTrialPreparedStatement;

    /**
     * ResultSet pour récupérer le dernier essai.
     */
    private ResultSet LastTrialResultSet;

    /**
     * Constructeur de la classe FessaisDAO.
     *
     * @param MyConnection connexion à la base de données courante.
     * @param MyEtatTicket indique si l'on travaille sur les tickets en cours ou
     * archivés.
     * @throws ClassNotFoundException en cas de classe non trouvée.
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public FessaisDAO(Connection MyConnection, EtatTicket MyEtatTicket)
            throws ClassNotFoundException, SQLException {

        MyTable = EtatTicket.EN_COURS.equals(MyEtatTicket) ? "fessais" : "f99essais";

        this.MyConnection = MyConnection;

        setInvariableSelectStatement("select enumabs, ecnum, eptr, eunum, edate, etime,"
                + " emessage, etnum, eonum, eresult, eduration, etest, einternal,"
                + " em3num, egid"
                + " from " + MyTable);
//        if (enumabs > 0) {
//            Stmt.append(" and enumabs = ").append(enumabs);
//        }
//        if (ecnum > 0) {
//            Stmt.append(" and ecnum = ").append(ecnum);
//        }
//        if (egid > 0) {
//            Stmt.append(" and egid = ").append(egid);
//        }
//        Stmt.append(" order by enumabs;");
////        System.out.println(Stmt);
//        setSelectStatement(Stmt.toString());
//        setSelectPreparedStatement();
//        setSelectResultSet();

        setUpdateStatement("update " + MyTable
                + " set ecnum=?, eptr=?, eunum=?, edate=?, etime=?,"
                + " emessage=?, etnum=?, eonum=?, eresult=?,"
                + " eduration=?, etest=?, einternal=?, em3num=?, egid=?"
                + " where enumabs=?;");
//        setUpdatePreparedStatement();

        setInsertStatement("insert into " + MyTable
                + " (ecnum, eptr, eunum, edate, etime,"
                + " emessage, etnum, eonum, eresult, eduration,"
                + " etest, einternal, em3num, egid)"
                + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
//        setInsertPreparedStatement();

        setDeleteStatement("delete from " + MyTable + " where enumabs=?;");
//        setDeletePreparedStatement();

        // Préparation pour la récupération de la première transmission.
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
                + " where ecnum = ?"
                + " and eresult = 1"
                + " order by edate, etime;");
//            setFirstTransmissionPreparedStatement();
//            setFirstTransmissionResultSet();

        // Préparation pour la récupération de la dernière transmission.
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
                + " where ecnum = ?"
                + " and eresult = 1"
                + " order by edate desc, etime desc;");
//            setLastTransmissionPreparedStatement();
//            setLastTransmissionResultSet();
        
//        // Préparation pour la récupération d'un élément de clôture d'appel.
        setPartOfEOMStatement("select a.enumabs, "
                + " a.ecnum, a.eptr, a.eunum, a.edate, a.etime,"
                + " a.emessage, a.etnum, a.eonum, a.eresult, a.eduration,"
                + " a.etest, a.einternal, a.em3num, a.egid"
                + " from " + MyTable + " a"
                + " where a.enumabs = (select max(b.enumabs) from "
                + MyTable + " b where b.ecnum = ?"
                + " and b.eresult in (69,70,71,72,73,90,91,92,93));");
//            System.out.println("  PartOfEOMStatement=" + getPartOfEOMStatement());
//            setPartOfEOMPreparedStatement();
//            setPartOfEOMResultSet();

        // Préparation pour la récupération du dernier essai pour un résultat donné
        setLastTrialStatement("select enumabs, "
                + " ecnum, eptr, eunum, edate, etime,"
                + " emessage, etnum, eonum, eresult, eduration,"
                + " etest, einternal, em3num, egid"
                + " from " + MyTable 
                + " where ecnum = ?"
                + " and eresult = ?"
                + " order by edate desc, etime desc;");
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
            if (SelectResultSet.next()) {
                MyFessais = new Fessais();
                MyFessais.setEnumabs(SelectResultSet.getInt("enumabs"));
                MyFessais.setEcnum(SelectResultSet.getInt("ecnum"));
                MyFessais.setEptr(SelectResultSet.getInt("eptr"));
                MyFessais.setEunum(SelectResultSet.getInt("eunum"));
                MyFessais.setEdate(SelectResultSet.getTimestamp("edate"));
                MyFessais.setEtime(SelectResultSet.getString("etime"));
                MyFessais.setEmessage(SelectResultSet.getString("emessage"));
                MyFessais.setEtnum(SelectResultSet.getInt("etnum"));
                MyFessais.setEonum(SelectResultSet.getInt("eonum"));
                MyFessais.setEresult(SelectResultSet.getInt("eresult"));
                MyFessais.setEduration(SelectResultSet.getInt("eduration"));
                MyFessais.setEtest(SelectResultSet.getInt("etest"));
                MyFessais.setEinternal(SelectResultSet.getInt("einternal"));
                MyFessais.setEm3num(SelectResultSet.getInt("em3num"));
                MyFessais.setEgid(SelectResultSet.getInt("egid"));
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
//        ResultSet MyKeyResultSet;

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
//                Does not work with Informix IDS2000
//                MyKeyResultSet = InsertPreparedStatement.getGeneratedKeys();
//                if (MyKeyResultSet.next()) {
//                    MyFessais.setEnumabs((int) MyKeyResultSet.getInt(1));
//                }
//                MyKeyResultSet.close();
                MyFessais.setEnumabs(
                        ((IfmxStatement) InsertPreparedStatement).getSerial());
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
     * @param cnum identifiant de l'appel courant.
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void setFirstTransmissionPreparedStatement(int cnum) throws SQLException {
        FirstTransmissionPreparedStatement = MyConnection.prepareStatement(getFirstTransmissionStatement());
        FirstTransmissionPreparedStatement.setInt(1, cnum);
        setFirstTransmissionResultSet();
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
     * Prépare la requête SQL pour rechercher la dernière transmission.
     *
     * @param cnum identifiant de l'appel courant.
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void setLastTransmissionPreparedStatement(int cnum) throws SQLException {
        LastTransmissionPreparedStatement = MyConnection.prepareStatement(getLastTransmissionStatement());
        LastTransmissionPreparedStatement.setInt(1, cnum);
        setLastTransmissionResultSet();
    }

    /**
     * Prépare la requête SQL pour rechercher un élément de clôture d'appel.
     *
     * @param cnum identifiant de l'appel courant.
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void setPartOfEOMPreparedStatement(int cnum) throws SQLException {
        PartOfEOMPreparedStatement = MyConnection.prepareStatement(getPartOfEOMStatement());
        PartOfEOMPreparedStatement.setInt(1, cnum);
        setPartOfEOMResultSet();
    }

    /**
     * @return the LastTrialStatement
     */
    public String getLastTrialStatement() {
        return LastTrialStatement;
    }

    /**
     * @param LastTrialStatement the LastTrialStatement to set
     */
    public void setLastTrialStatement(String LastTrialStatement) {
        this.LastTrialStatement = LastTrialStatement;
    }

    /**
     * @return the LastTrialPreparedStatement
     */
    public PreparedStatement getLastTrialPreparedStatement() {
        return LastTrialPreparedStatement;
    }

    /**
     * Prépare la requête SQL pour rechercher la dernière transmission.
     *
     * @param cnum identifiant de l'appel courant.
     * @param eresult résultat associé à l'essai.
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void setLastTrialPreparedStatement(int cnum, int eresult) throws SQLException {
        LastTrialPreparedStatement = MyConnection.prepareStatement(getLastTrialStatement());
        LastTrialPreparedStatement.setInt(1, cnum);
        LastTrialPreparedStatement.setInt(2, eresult);
        setLastTrialResultSet();
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
     * @return LastTransmissionResultSet 
     */
    public ResultSet getLastTrialResultSet() {
        return LastTrialResultSet;
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
     * Exécute la requête SQL pour rechercher le dernier essai
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void setLastTrialResultSet() throws SQLException {
        LastTrialResultSet = LastTrialPreparedStatement.executeQuery();
    }

    /**
     * Récupère l'essai correspondant au dernier essai, s'il existe.
     *
     * @return l'essai correspondant au dernier essai, s'il existe.
     */
    public Fessais getLastTrial() {
        Fessais MyFessais = null;

        try {
            if (LastTrialResultSet.next()) {
                MyFessais = new Fessais();
                MyFessais.setEnumabs(LastTrialResultSet.getInt("enumabs"));
                MyFessais.setEcnum(LastTrialResultSet.getInt("ecnum"));
                MyFessais.setEptr(LastTrialResultSet.getInt("eptr"));
                MyFessais.setEunum(LastTrialResultSet.getInt("eunum"));
                MyFessais.setEdate(LastTrialResultSet.getTimestamp("edate"));
                MyFessais.setEtime(LastTrialResultSet.getString("etime"));
                MyFessais.setEmessage(LastTrialResultSet.getString("emessage"));
                MyFessais.setEtnum(LastTrialResultSet.getInt("etnum"));
                MyFessais.setEonum(LastTrialResultSet.getInt("eonum"));
                MyFessais.setEresult(LastTrialResultSet.getInt("eresult"));
                MyFessais.setEduration(LastTrialResultSet.getInt("eduration"));
                MyFessais.setEtest(LastTrialResultSet.getInt("etest"));
                MyFessais.setEinternal(LastTrialResultSet.getInt("einternal"));
                MyFessais.setEm3num(LastTrialResultSet.getInt("em3num"));
                MyFessais.setEgid(LastTrialResultSet.getInt("egid"));
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
     * Méthode pour fermer les ressources associées à la requête de sélection
     * de la première transmission.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void closeFirstTransmissionPreparedStatement() throws SQLException {
        FirstTransmissionResultSet.close();
        FirstTransmissionPreparedStatement.close();
    }

    /**
     * Méthode pour fermer les ressources associées à la requête de sélection
     * de la dernière transmission.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void closeLastTransmissionPreparedStatement() throws SQLException {
        LastTransmissionResultSet.close();
        LastTransmissionPreparedStatement.close();
    }

    /**
     * Méthode pour fermer les ressources associées à la requête de sélection
     * de la dernière transmission.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void closePartOfEOMPreparedStatement() throws SQLException {
        PartOfEOMResultSet.close();
        PartOfEOMPreparedStatement.close();
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
        Stmt.append(" where enumabs = ").append(id).append(";");
        setSelectStatement(Stmt.toString());
    }

    /**
     * Méthode pour fermer les ressources associées à la requête de sélection
     * du dernier essai.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void closeLastTrialPreparedStatement() throws SQLException {
        LastTrialResultSet.close();
        LastTrialPreparedStatement.close();
    }

    /**
     * Méthode pour filter les résultats par identifiant de groupe.
     *
     * @param gid l'identifiant de groupe à utiliser pour le filtrage.
     */
    @Override
    public void filterByGid(int gid) {
        StringBuffer Stmt;

        Stmt = new StringBuffer(InvariableSelectStatement);
        Stmt.append(" where ecnum = ").append(gid).append(";");
        setSelectStatement(Stmt.toString());
    }

    /**
     * Méthode pour filter les résultats par appel et groupe d'essais.
     *
     * @param cnum l'identifiant de l'appel à utiliser pour le filtrage.
     * @param egid l'identifiant groupe d'essais à utiliser pour le filtrage.
     */
    public void filterByGid(int cnum, int egid) {
        StringBuffer Stmt;

        Stmt = new StringBuffer(InvariableSelectStatement);
        Stmt.append(" where ecnum = ").append(cnum);
        Stmt.append(" and egid = ").append(egid).append(";");
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
     * Méthode pour filter les résultats par identifiant de groupe et par nom.
     *
     * @param gid l'identifiant de groupe à utiliser pour le filtrage.
     * @param Name à utiliser pour le filtrage.
     */
    @Override
    public void filterByName(int gid, String Name) {
        throw new UnsupportedOperationException("Non supporté actuellement");
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
