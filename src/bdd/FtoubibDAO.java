package bdd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe qui décrit les méthodes pour accéder à la table ftoubib au travers de
 * JDBC.
 *
 * @author Thierry Baribaud
 * @version Juin 2016
 */
public class FtoubibDAO extends PaternDAO {

    /**
     * Constructeur de la classe FtoubibDAO.
     *
     * @param MyConnection connexion à la base de données courante.
     * @param tnum identifiant de l'intervenant,
     * @param tunum identifiant du service d'urgence,
     * @throws ClassNotFoundException en cas de classse non trouvée.
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public FtoubibDAO(Connection MyConnection, int tnum, int tunum)
            throws ClassNotFoundException, SQLException {

        StringBuffer Stmt;

        this.MyConnection = MyConnection;

        Stmt = new StringBuffer("select tnum, tunum, ta6num, ta4num,"
                + " tlname, tfname, tabbname, tel,"
                + " tel2, telper, tel4, tel5, tel6,"
                + " telfax, temail, taddress, taddress2, tcomment"
                + " from ftoubib"
                + " where tnum > 0");
        if (tunum > 0) {
            Stmt.append(" and tunum = ").append(tunum);
        }
        if (tnum > 0) {
            Stmt.append(" and tnum = ").append(tnum);
        } else {
            Stmt.append(" order by tnum");
        }
        Stmt.append(";");
        
//        System.out.println(Stmt);
        setReadStatement(Stmt.toString());
        setReadPreparedStatement();
        setReadResultSet();

        setUpdateStatement("update ftoubib"
                + " set tunum=?, ta6num=?, ta4num=?, tlname=?, tfname=?,"
                + " tabbname=?, tel=?,"
                + " tel2=?, telper=?, tel4=?, tel5=?, tel6=?,"
                + " telfax=?, temail=?, taddress=?, taddress2=?, tcomment=?"
                + " where tnum=?;");
        setUpdatePreparedStatement();

        setInsertStatement("insert into ftoubib"
                + " (tunum, ta6num, ta4num, tlname, tfname, tabbname, tel,"
                + " tel2, telper, tel4, tel5, tel6,"
                + " telfax, temail, taddress, taddress2, tcomment)"
                + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
        setInsertPreparedStatement();

        setDeleteStatement("delete from ftoubib where tnum=?;");
        setDeletePreparedStatement();
    }

    /**
     * Selectionne un intervenant.
     *
     * @return l'intervenant sélectionné.
     */
    @Override
    public Ftoubib select() {
        Ftoubib MyFtoubib = null;

        try {
            if (ReadResultSet.next()) {
                MyFtoubib = new Ftoubib();
                MyFtoubib.setTnum(ReadResultSet.getInt("tnum"));
                MyFtoubib.setTunum(ReadResultSet.getInt("tunum"));
                MyFtoubib.setTa6num(ReadResultSet.getInt("ta6num"));
                MyFtoubib.setTa4num(ReadResultSet.getInt("ta4num"));
                MyFtoubib.setTlname(ReadResultSet.getString("tlname"));
                MyFtoubib.setTfname(ReadResultSet.getString("tfname"));
                MyFtoubib.setTabbname(ReadResultSet.getString("tabbname"));
                MyFtoubib.setTel(ReadResultSet.getString("tel"));
                MyFtoubib.setTel2(ReadResultSet.getString("tel2"));
                MyFtoubib.setTelper(ReadResultSet.getString("telper"));
                MyFtoubib.setTel4(ReadResultSet.getString("tel4"));
                MyFtoubib.setTel5(ReadResultSet.getString("tel5"));
                MyFtoubib.setTel6(ReadResultSet.getString("tel6"));
                MyFtoubib.setTelfax(ReadResultSet.getString("telfax"));
                MyFtoubib.setTemail(ReadResultSet.getString("temail"));
                MyFtoubib.setTaddress(ReadResultSet.getString("taddress"));
                MyFtoubib.setTaddress2(ReadResultSet.getString("taddress2"));
                MyFtoubib.setTcomment(ReadResultSet.getString("tcomment"));
            } else {
                System.out.println("Lecture de ftoubib terminée");
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur en lecture de ftoubib "
                    + MyException.getMessage());
        }
        return MyFtoubib;
    }

    /**
     * Met à jour un intervenant.
     *
     * @param MyFtoubib intervenant à mettre à jour.
     */
    public void update(Ftoubib MyFtoubib) {
        try {
            UpdatePreparedStatement.setInt(1, MyFtoubib.getTunum());
            UpdatePreparedStatement.setInt(2, MyFtoubib.getTa6num());
            UpdatePreparedStatement.setInt(3, MyFtoubib.getTa4num());
            UpdatePreparedStatement.setString(4, MyFtoubib.getTlname());
            UpdatePreparedStatement.setString(5, MyFtoubib.getTfname());
            UpdatePreparedStatement.setString(6, MyFtoubib.getTabbname());
            UpdatePreparedStatement.setString(7, MyFtoubib.getTel());
            UpdatePreparedStatement.setString(8, MyFtoubib.getTel2());
            UpdatePreparedStatement.setString(9, MyFtoubib.getTelper());
            UpdatePreparedStatement.setString(10, MyFtoubib.getTel4());
            UpdatePreparedStatement.setString(11, MyFtoubib.getTel5());
            UpdatePreparedStatement.setString(12, MyFtoubib.getTel6());
            UpdatePreparedStatement.setString(13, MyFtoubib.getTelfax());
            UpdatePreparedStatement.setString(14, MyFtoubib.getTemail());
            UpdatePreparedStatement.setString(15, MyFtoubib.getTaddress());
            UpdatePreparedStatement.setString(16, MyFtoubib.getTaddress2());
            UpdatePreparedStatement.setString(17, MyFtoubib.getTcomment());
            UpdatePreparedStatement.setInt(18, MyFtoubib.getTnum());
            setNbAffectedRow(UpdatePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de mettre à jour ftoubib");
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de la mise à jour de ftoubib"
                    + " " + MyException.getMessage());
        }
    }

    /**
     * Supprime définitivement un intervenant.
     *
     * @param tnum identiant de l'intervenant à supprimer.
     */
    @Override
    public void delete(int tnum) {
        try {
            DeletePreparedStatement.setInt(1, tnum);
            setNbAffectedRow(DeletePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de détruire un intervenant dans ftoubib");
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de la suppression d'un intervenant dans "
                    + "ftoubib " + MyException.getMessage());
        }
    }

    /**
     * Ajoute un intervenant dans la table ftoubib.
     *
     * @param MyFtoubib intervenant à ajouter à la table ftoubib.
     */
    public void insert(Ftoubib MyFtoubib) {
        ResultSet MyKeyResultSet = null;

        try {
            System.out.println("tlname=" + MyFtoubib.getTlname());
            InsertPreparedStatement.setInt(1, MyFtoubib.getTunum());
            InsertPreparedStatement.setInt(2, MyFtoubib.getTa6num());
            InsertPreparedStatement.setInt(3, MyFtoubib.getTa4num());
            InsertPreparedStatement.setString(4, MyFtoubib.getTlname());
            InsertPreparedStatement.setString(5, MyFtoubib.getTfname());
            InsertPreparedStatement.setString(6, MyFtoubib.getTabbname());
            InsertPreparedStatement.setString(7, MyFtoubib.getTel());
            InsertPreparedStatement.setString(8, MyFtoubib.getTel2());
            InsertPreparedStatement.setString(8, MyFtoubib.getTelper());
            InsertPreparedStatement.setString(10, MyFtoubib.getTel4());
            InsertPreparedStatement.setString(11, MyFtoubib.getTel5());
            InsertPreparedStatement.setString(12, MyFtoubib.getTel6());
            InsertPreparedStatement.setString(13, MyFtoubib.getTelfax());
            InsertPreparedStatement.setString(14, MyFtoubib.getTemail());
            InsertPreparedStatement.setString(15, MyFtoubib.getTaddress());
            InsertPreparedStatement.setString(16, MyFtoubib.getTaddress2());
            InsertPreparedStatement.setString(17, MyFtoubib.getTcomment());
            setNbAffectedRow(InsertPreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible d'ajouter un intervenant dans ftoubib");
            } else {
                MyKeyResultSet = InsertPreparedStatement.getGeneratedKeys();
                if (MyKeyResultSet.next()) {
                    MyFtoubib.setTnum((int) MyKeyResultSet.getInt(1));
                }
            }
            MyKeyResultSet.close();
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de l'insertion d'un intervenant dans "
                    + "ftoubib " + MyException.getMessage());
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
