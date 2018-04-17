package bdd;

import com.informix.jdbc.IfmxStatement;
import java.sql.*;

/**
 * Classe qui d�crit les m�thodes pour acc�der aux tables fessais ou f99essais
 * au travers de JDBC.
 *
 * ATTENTION : Classe � r��crire totalement car trop rigide.
 * 
 * @author Thierry Baribaud.
 * @version 0.22
 */
public class FessaisDAO extends PatternDAO {

    /**
     * Table � g�r�r : fessais ou f99essais.
     */
    private String MyTable = "fessais";

    /**
     * Identifiant d'un appel.
     */
    private int cnum = 0;

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
     * Requ�te SQL pour r�cup�rer le dernier essai.
     */
    private String LastTrialStatement;

    /**
     * Requ�te SQL pr�par�e pour r�cup�rer le dernier essai.
     */
    private PreparedStatement LastTrialPreparedStatement;

    /**
     * ResultSet pour r�cup�rer le dernier essai.
     */
    private ResultSet LastTrialResultSet;

    /**
     * Requ�te SQL pour r�cup�rer un essai.
     */
    private String trialStatement;

    /**
     * Requ�te SQL pr�par�e pour r�cup�rer un essai.
     */
    private PreparedStatement trialPreparedStatement;

    /**
     * ResultSet pour r�cup�rer un essai.
     */
    private ResultSet trialResultSet;

    /**
     * Requ�te SQL pour rechercher les essais d'annulation.
     */
    private String ticketCanceledStatement;

    /**
     * Requ�te SQL pr�par�e pour rechercher les essais d'annulation.
     */
    private PreparedStatement ticketCanceledPreparedStatement;

    /**
     * ResultSet pour rechercher les essais d'annulation.
     */
    private ResultSet ticketCanceledResultSet;

    /**
     * Constructeur de la classe FessaisDAO.
     *
     * @param MyConnection connexion � la base de donn�es courante.
     * @param MyEtatTicket indique si l'on travaille sur les tickets en cours ou
     * archiv�s.
     * @throws ClassNotFoundException en cas de classe non trouv�e.
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public FessaisDAO(Connection MyConnection, EtatTicket MyEtatTicket)
            throws ClassNotFoundException, SQLException {

        MyTable = EtatTicket.EN_COURS.equals(MyEtatTicket) ? "fessais" : "f99essais";

        this.connection = MyConnection;

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

        // Pr�paration pour la r�cup�ration de la premi�re transmission.
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
                + " where ecnum = ?"
                + " and eresult = 1"
                + " order by edate, etime;");
//            setFirstTransmissionPreparedStatement();
//            setFirstTransmissionResultSet();

        // Pr�paration pour la r�cup�ration de la derni�re transmission.
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
                + " where ecnum = ?"
                + " and eresult = 1"
                + " order by edate desc, etime desc;");
//            setLastTransmissionPreparedStatement();
//            setLastTransmissionResultSet();

//        // Pr�paration pour la r�cup�ration d'un �l�ment de cl�ture d'appel.
        setPartOfEOMStatement("select a.enumabs, "
                + " a.ecnum, a.eptr, a.eunum, a.edate, a.etime,"
                + " a.emessage, a.etnum, a.eonum, a.eresult, a.eduration,"
                + " a.etest, a.einternal, a.em3num, a.egid"
                + " from " + MyTable + " a"
                + " where a.enumabs = (select max(b.enumabs) from "
                + MyTable + " b where b.ecnum = ?"
                + " and b.eresult in (69,70,71,72,73,90,91,92,93,101,102,103,104));");
//            System.out.println("  PartOfEOMStatement=" + getPartOfEOMStatement());
//            setPartOfEOMPreparedStatement();
//            setPartOfEOMResultSet();

        // Pr�paration pour la r�cup�ration du dernier essai pour un r�sultat donn�
        setLastTrialStatement("select enumabs, "
                + " ecnum, eptr, eunum, edate, etime,"
                + " emessage, etnum, eonum, eresult, eduration,"
                + " etest, einternal, em3num, egid"
                + " from " + MyTable
                + " where ecnum = ?"
                + " and eresult = ?"
                + " order by edate desc, etime desc;");

        // Pr�paration pour la r�cup�ration d'un essai pour un r�sultat donn�
        setTrialStatement("select enumabs, "
                + " ecnum, eptr, eunum, edate, etime,"
                + " emessage, etnum, eonum, eresult, eduration,"
                + " etest, einternal, em3num, egid"
                + " from " + MyTable
                + " where ecnum = ?"
                + " and eresult = ?;");

        // Pr�paration pour la recherche d'un essai d'annulation de ticket
        setTicketCanceledStatement("select enumabs, "
                + " ecnum, eptr, eunum, edate, etime,"
                + " emessage, etnum, eonum, eresult, eduration,"
                + " etest, einternal, em3num, egid"
                + " from " + MyTable
                + " where ecnum = ?"
                + " and eresult in (6, 7, 8, 10, 14);");
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
     * Pr�pare la requ�te SQL pour rechercher la premi�re transmission.
     *
     * @param cnum identifiant de l'appel courant.
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void setFirstTransmissionPreparedStatement(int cnum) throws SQLException {
        FirstTransmissionPreparedStatement = connection.prepareStatement(getFirstTransmissionStatement());
        FirstTransmissionPreparedStatement.setInt(1, cnum);
        setFirstTransmissionResultSet();
    }

    /**
     * Pr�pare la requ�te SQL pour rechercher l'annulation d'un ticket.
     *
     * @param cnum identifiant de l'appel courant.
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void prepareTicketCanceledStatement(int cnum) throws SQLException {
        ticketCanceledPreparedStatement = connection.prepareStatement(getTicketCanceledStatement());
        ticketCanceledPreparedStatement.setInt(1, cnum);
        ticketCanceledResultSet = ticketCanceledPreparedStatement.executeQuery();
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
     * Pr�pare la requ�te SQL pour rechercher la derni�re transmission.
     *
     * @param cnum identifiant de l'appel courant.
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void setLastTransmissionPreparedStatement(int cnum) throws SQLException {
        LastTransmissionPreparedStatement = connection.prepareStatement(getLastTransmissionStatement());
        LastTransmissionPreparedStatement.setInt(1, cnum);
        setLastTransmissionResultSet();
    }

    /**
     * Pr�pare la requ�te SQL pour rechercher un �l�ment de cl�ture d'appel.
     *
     * @param cnum identifiant de l'appel courant.
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void setPartOfEOMPreparedStatement(int cnum) throws SQLException {
        PartOfEOMPreparedStatement = connection.prepareStatement(getPartOfEOMStatement());
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
     * @return the trialStatement
     */
    public String getTrialStatement() {
        return trialStatement;
    }

    /**
     * @param LastTrialStatement the LastTrialStatement to set
     */
    public void setLastTrialStatement(String LastTrialStatement) {
        this.LastTrialStatement = LastTrialStatement;
    }

    /**
     * @param trialStatement the trialStatement to set
     */
    public void setTrialStatement(String trialStatement) {
        this.trialStatement = trialStatement;
    }

    /**
     * @param orderByClause the order by clause
     */
    public void setTrialStatementOrderby(String orderByClause) {
        StringBuffer statement;
        if (trialStatement != null && orderByClause != null) {
            if (!trialStatement.contains("order  by")) {
                statement = new StringBuffer(trialStatement);
                if (statement.indexOf(";") == -1) {
                    statement.append(";");
                }
                setTrialStatement(statement.insert(statement.indexOf(";"), orderByClause).toString());
            }
        }
    }

    /**
     * @return the LastTrialPreparedStatement
     */
    public PreparedStatement getLastTrialPreparedStatement() {
        return LastTrialPreparedStatement;
    }

    /**
     * @return the trialPreparedStatement
     */
    public PreparedStatement getTrialPreparedStatement() {
        return trialPreparedStatement;
    }

    /**
     * Pr�pare la requ�te SQL pour rechercher la derni�re transmission.
     *
     * @param cnum identifiant de l'appel courant.
     * @param eresult r�sultat associ� � l'essai.
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void setLastTrialPreparedStatement(int cnum, int eresult) throws SQLException {
        LastTrialPreparedStatement = connection.prepareStatement(getLastTrialStatement());
        LastTrialPreparedStatement.setInt(1, cnum);
        LastTrialPreparedStatement.setInt(2, eresult);
        setLastTrialResultSet();
    }

    /**
     * Pr�pare la requ�te SQL pour rechercher un essai
     *
     * @param cnum identifiant de l'appel courant.
     * @param eresult r�sultat associ� � l'essai.
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void setTrialPreparedStatement(int cnum, int eresult) throws SQLException {
        trialPreparedStatement = connection.prepareStatement(getTrialStatement());
        trialPreparedStatement.setInt(1, cnum);
        trialPreparedStatement.setInt(2, eresult);
        setTrialResultSet();
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
     * @return transmissionResultSet
     */
    public ResultSet getTrialResultSet() {
        return trialResultSet;
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
     * Ex�cute la requ�te SQL pour rechercher le dernier essai
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void setLastTrialResultSet() throws SQLException {
        LastTrialResultSet = LastTrialPreparedStatement.executeQuery();
    }

    /**
     * Ex�cute la requ�te SQL pour rechercher un essai
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void setTrialResultSet() throws SQLException {
        trialResultSet = trialPreparedStatement.executeQuery();
    }

    /**
     * R�cup�re l'essai correspondant au dernier essai, s'il existe.
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
     * R�cup�re un essai
     *
     * @return un essai
     */
    public Fessais getTrial() {
        Fessais MyFessais = null;

        try {
            if (trialResultSet.next()) {
                MyFessais = new Fessais();
                MyFessais.setEnumabs(trialResultSet.getInt("enumabs"));
                MyFessais.setEcnum(trialResultSet.getInt("ecnum"));
                MyFessais.setEptr(trialResultSet.getInt("eptr"));
                MyFessais.setEunum(trialResultSet.getInt("eunum"));
                MyFessais.setEdate(trialResultSet.getTimestamp("edate"));
                MyFessais.setEtime(trialResultSet.getString("etime"));
                MyFessais.setEmessage(trialResultSet.getString("emessage"));
                MyFessais.setEtnum(trialResultSet.getInt("etnum"));
                MyFessais.setEonum(trialResultSet.getInt("eonum"));
                MyFessais.setEresult(trialResultSet.getInt("eresult"));
                MyFessais.setEduration(trialResultSet.getInt("eduration"));
                MyFessais.setEtest(trialResultSet.getInt("etest"));
                MyFessais.setEinternal(trialResultSet.getInt("einternal"));
                MyFessais.setEm3num(trialResultSet.getInt("em3num"));
                MyFessais.setEgid(trialResultSet.getInt("egid"));
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur en lecture de " + MyTable + " "
                    + MyException.getMessage());
        }
        return (MyFessais);
    }


    /**
     * R�cup�re un essai
     *
     * @return un essai
     */
    public Fessais getTicketCanceled() {
        Fessais MyFessais = null;

        try {
            if (ticketCanceledResultSet.next()) {
                MyFessais = new Fessais();
                MyFessais.setEnumabs(ticketCanceledResultSet.getInt("enumabs"));
                MyFessais.setEcnum(ticketCanceledResultSet.getInt("ecnum"));
                MyFessais.setEptr(ticketCanceledResultSet.getInt("eptr"));
                MyFessais.setEunum(ticketCanceledResultSet.getInt("eunum"));
                MyFessais.setEdate(ticketCanceledResultSet.getTimestamp("edate"));
                MyFessais.setEtime(ticketCanceledResultSet.getString("etime"));
                MyFessais.setEmessage(ticketCanceledResultSet.getString("emessage"));
                MyFessais.setEtnum(ticketCanceledResultSet.getInt("etnum"));
                MyFessais.setEonum(ticketCanceledResultSet.getInt("eonum"));
                MyFessais.setEresult(ticketCanceledResultSet.getInt("eresult"));
                MyFessais.setEduration(ticketCanceledResultSet.getInt("eduration"));
                MyFessais.setEtest(ticketCanceledResultSet.getInt("etest"));
                MyFessais.setEinternal(ticketCanceledResultSet.getInt("einternal"));
                MyFessais.setEm3num(ticketCanceledResultSet.getInt("em3num"));
                MyFessais.setEgid(ticketCanceledResultSet.getInt("egid"));
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur en lecture de " + MyTable + " "
                    + MyException.getMessage());
        }
        return (MyFessais);
    }

    /**
     * R�cup�re l'essai correspondant � un �l�ment de cl�ture d'appel, s'il
     * existe.
     *
     * @return l'essai correspondant � un �l�ment de cl�ture d'appel, s'il
     * existe.
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
     * M�thode pour fermer les ressources associ�es � la requ�te de s�lection de
     * la premi�re transmission.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void closeFirstTransmissionPreparedStatement() throws SQLException {
        FirstTransmissionResultSet.close();
        FirstTransmissionPreparedStatement.close();
    }

    /**
     * M�thode pour fermer les ressources associ�es � la requ�te de s�lection de
     * la derni�re transmission.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void closeLastTransmissionPreparedStatement() throws SQLException {
        LastTransmissionResultSet.close();
        LastTransmissionPreparedStatement.close();
    }

    /**
     * M�thode pour fermer les ressources associ�es � la requ�te de s�lection de
     * la derni�re transmission.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void closePartOfEOMPreparedStatement() throws SQLException {
        PartOfEOMResultSet.close();
        PartOfEOMPreparedStatement.close();
    }

    /**
     * M�thode pour filter les r�sultats par identifiant.
     *
     * @param id l'identifiant � utiliser pour le filtrage.
     */
    @Override
    public void filterById(int id) {
        StringBuffer Stmt;

        Stmt = new StringBuffer(InvariableSelectStatement);
        Stmt.append(" where enumabs = ").append(id).append(";");
        setSelectStatement(Stmt.toString());
    }

    /**
     * M�thode pour fermer les ressources associ�es � la requ�te de s�lection du
     * dernier essai.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void closeLastTrialPreparedStatement() throws SQLException {
        LastTrialResultSet.close();
        LastTrialPreparedStatement.close();
    }

    /**
     * M�thode pour fermer les ressources associ�es � la requ�te de s�lection
     * d'un essai.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void closeTrialPreparedStatement() throws SQLException {
        trialResultSet.close();
        trialPreparedStatement.close();
    }

    /**
     * M�thode pour fermer les ressources associ�es � la requ�te de recherche
     * d'essais d'annulation.
     *
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public void closeTicketCanceledPreparedStatement() throws SQLException {
        ticketCanceledResultSet.close();
        ticketCanceledPreparedStatement.close();
    }
    /**
     * M�thode pour filter les r�sultats par identifiant de groupe.
     *
     * @param gid l'identifiant de groupe � utiliser pour le filtrage.
     */
    @Override
    public void filterByGid(int gid) {
        StringBuffer Stmt;

        Stmt = new StringBuffer(InvariableSelectStatement);
        Stmt.append(" where ecnum = ").append(gid).append(";");
        setSelectStatement(Stmt.toString());
    }

    /**
     * M�thode pour filter les r�sultats par appel et groupe d'essais.
     *
     * @param cnum l'identifiant de l'appel � utiliser pour le filtrage.
     * @param egid l'identifiant groupe d'essais � utiliser pour le filtrage.
     */
    public void filterByGid(int cnum, int egid) {
        StringBuffer Stmt;

        Stmt = new StringBuffer(InvariableSelectStatement);
        Stmt.append(" where ecnum = ").append(cnum);
        Stmt.append(" and egid = ").append(egid).append(";");
        setSelectStatement(Stmt.toString());
    }

    /**
     * M�thode pour filter les r�sultats par identifiant de groupe et par code.
     *
     * @param gid l'identifiant de groupe � utiliser pour le filtrage.
     * @param Code � utiliser pour le filtrage.
     */
    @Override
    public void filterByCode(int gid, String Code) {
        throw new UnsupportedOperationException("Non support� actuellement");
    }

    /**
     * M�thode pour filter les r�sultats par identifiant de groupe et par nom.
     *
     * @param gid l'identifiant de groupe � utiliser pour le filtrage.
     * @param Name � utiliser pour le filtrage.
     */
    @Override
    public void filterByName(int gid, String Name) {
        throw new UnsupportedOperationException("Non support� actuellement");
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
     * @return PartOfEOMStatement requ�te SQL qui retourne un �l�ment de cl�ture
     * d'appel.
     */
    public String getPartOfEOMStatement() {
        return PartOfEOMStatement;
    }

    /**
     * @param PartOfEOMStatement d�finit la requ�te SQL qui retourne un �l�ment
     * de cl�ture d'appel.
     */
    public void setPartOfEOMStatement(String PartOfEOMStatement) {
        this.PartOfEOMStatement = PartOfEOMStatement;
    }

    /**
     * @return the ticketCanceledStatement
     */
    public String getTicketCanceledStatement() {
        return ticketCanceledStatement;
    }

    /**
     * @param ticketCanceledStatement the ticketCanceledStatement to set
     */
    public void setTicketCanceledStatement(String ticketCanceledStatement) {
        this.ticketCanceledStatement = ticketCanceledStatement;
    }

    /**
     * @return the ticketCanceledPreparedStatement
     */
    public PreparedStatement getTicketCanceledPreparedStatement() {
        return ticketCanceledPreparedStatement;
    }

    /**
     * @param ticketCanceledPreparedStatement the ticketCanceledPreparedStatement to set
     */
    public void setTicketCanceledPreparedStatement(PreparedStatement ticketCanceledPreparedStatement) {
        this.ticketCanceledPreparedStatement = ticketCanceledPreparedStatement;
    }

    /**
     * @return the ticketCanceledResultSet
     */
    public ResultSet getTicketCanceledResultSet() {
        return ticketCanceledResultSet;
    }

    /**
     * @param ticketCanceledResultSet the ticketCanceledResultSet to set
     */
    public void setTicketCanceledResultSet(ResultSet ticketCanceledResultSet) {
        this.ticketCanceledResultSet = ticketCanceledResultSet;
    }
}
