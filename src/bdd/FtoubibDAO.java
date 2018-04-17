package bdd;

import com.informix.jdbc.IfmxStatement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Classe qui décrit les méthodes pour accéder à la table ftoubib avec JDBC.
 *
 * @author Thierry Baribaud
 * @version 0.18
 */
public class FtoubibDAO extends PatternDAO {

    /**
     * Constructeur de la classe FtoubibDAO.
     *
     * @param MyConnection connexion à la base de données courante.
     * @throws ClassNotFoundException en cas de classe non trouvée.
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public FtoubibDAO(Connection MyConnection)
            throws ClassNotFoundException, SQLException {

        this.connection = MyConnection;

        setInvariableSelectStatement("select tnum, tunum, ta6num, ta4num,"
                + " tlname, tfname, tabbname, tel,"
                + " tel2, telper, tel4, tel5, tel6,"
                + " telfax, temail, taddress, taddress2, tcomment, tuuid,"
                + " tdelay1, tdelay2"
                + " from ftoubib");
//        if (tunum > 0) {
//            Stmt.append(" and tunum = ").append(tunum);
//        }
//        if (tnum > 0) {
//            Stmt.append(" and tnum = ").append(tnum);
//        } else {
//            Stmt.append(" order by tnum");
//        }
//        Stmt.append(";");

//        System.out.println(Stmt);
//        setSelectStatement(Stmt.toString());
//        setSelectPreparedStatement();
//        setSelectResultSet();
        setUpdateStatement("update ftoubib"
                + " set tunum=?, ta6num=?, ta4num=?, tlname=?, tfname=?,"
                + " tabbname=?, tel=?,"
                + " tel2=?, telper=?, tel4=?, tel5=?, tel6=?,"
                + " telfax=?, temail=?, taddress=?, taddress2=?, tcomment=?,"
                + " tuuid=?, tdelay1=?, tdelay2=?"
                + " where tnum=?;");
//        setUpdatePreparedStatement();

        setInsertStatement("insert into ftoubib"
                + " (tunum, ta6num, ta4num, tlname, tfname, tabbname, tel,"
                + " tel2, telper, tel4, tel5, tel6,"
                + " telfax, temail, taddress, taddress2, tcomment, tuuid,"
                + " tdelay1, tdelay2)"
                + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
//        setInsertPreparedStatement();

        setDeleteStatement("delete from ftoubib where tnum=?;");
//        setDeletePreparedStatement();
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
            if (SelectResultSet.next()) {
                MyFtoubib = new Ftoubib();
                MyFtoubib.setTnum(SelectResultSet.getInt("tnum"));
                MyFtoubib.setTunum(SelectResultSet.getInt("tunum"));
                MyFtoubib.setTa6num(SelectResultSet.getInt("ta6num"));
                MyFtoubib.setTa4num(SelectResultSet.getInt("ta4num"));
                MyFtoubib.setTlname(SelectResultSet.getString("tlname"));
                MyFtoubib.setTfname(SelectResultSet.getString("tfname"));
                MyFtoubib.setTabbname(SelectResultSet.getString("tabbname"));
                MyFtoubib.setTel(SelectResultSet.getString("tel"));
                MyFtoubib.setTel2(SelectResultSet.getString("tel2"));
                MyFtoubib.setTelper(SelectResultSet.getString("telper"));
                MyFtoubib.setTel4(SelectResultSet.getString("tel4"));
                MyFtoubib.setTel5(SelectResultSet.getString("tel5"));
                MyFtoubib.setTel6(SelectResultSet.getString("tel6"));
                MyFtoubib.setTelfax(SelectResultSet.getString("telfax"));
                MyFtoubib.setTemail(SelectResultSet.getString("temail"));
                MyFtoubib.setTaddress(SelectResultSet.getString("taddress"));
                MyFtoubib.setTaddress2(SelectResultSet.getString("taddress2"));
                MyFtoubib.setTcomment(SelectResultSet.getString("tcomment"));
                MyFtoubib.setTUuid(SelectResultSet.getString("tuuid"));
                MyFtoubib.setTdelay1(SelectResultSet.getInt("tdelay1"));
                MyFtoubib.setTdelay2(SelectResultSet.getInt("tdelay2"));
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
            UpdatePreparedStatement.setString(18, MyFtoubib.getTUuid());
            UpdatePreparedStatement.setInt(19, MyFtoubib.getTdelay1());
            UpdatePreparedStatement.setInt(20, MyFtoubib.getTdelay2());
            UpdatePreparedStatement.setInt(21, MyFtoubib.getTnum());
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
//        ResultSet MyKeyResultSet;

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
            InsertPreparedStatement.setString(9, MyFtoubib.getTelper());
            InsertPreparedStatement.setString(10, MyFtoubib.getTel4());
            InsertPreparedStatement.setString(11, MyFtoubib.getTel5());
            InsertPreparedStatement.setString(12, MyFtoubib.getTel6());
            InsertPreparedStatement.setString(13, MyFtoubib.getTelfax());
            InsertPreparedStatement.setString(14, MyFtoubib.getTemail());
            InsertPreparedStatement.setString(15, MyFtoubib.getTaddress());
            InsertPreparedStatement.setString(16, MyFtoubib.getTaddress2());
            InsertPreparedStatement.setString(17, MyFtoubib.getTcomment());
            InsertPreparedStatement.setString(18, MyFtoubib.getTUuid());
            InsertPreparedStatement.setInt(19, MyFtoubib.getTdelay1());
            InsertPreparedStatement.setInt(20, MyFtoubib.getTdelay2());
            setNbAffectedRow(InsertPreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible d'ajouter un intervenant dans ftoubib");
            } else {
//                MyKeyResultSet = InsertPreparedStatement.getGeneratedKeys();
//                if (MyKeyResultSet.next()) {
//                    MyFtoubib.setTnum((int) MyKeyResultSet.getInt(1));
//                }
//                MyKeyResultSet.close();
                MyFtoubib.setTnum(
                        ((IfmxStatement) InsertPreparedStatement).getSerial());
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de l'insertion d'un intervenant dans "
                    + "ftoubib " + MyException.getMessage());
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
        Stmt.append(" where tnum = ").append(id).append(";");
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

        Stmt = new StringBuffer(InvariableSelectStatement);
        Stmt.append(" where tunum = ").append(gid).append(";");
        setSelectStatement(Stmt.toString());
    }

    /**
     * Méthode pour filter les résultats par identifiant Performance Immo.
     *
     * @param Uuid à utiliser pour le filtrage.
     */
    public void filterByUuid(String Uuid) {
        StringBuffer Stmt;

        Stmt = new StringBuffer(InvariableSelectStatement);
        Stmt.append(" where tuuid = '").append(Uuid).append("';");
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
        StringBuffer Stmt;

        Stmt = new StringBuffer(InvariableSelectStatement);
        Stmt.append(" where tunum = ").append(gid);
        if (Code != null) {
            Stmt.append(" and tabbname = '").append(Code.trim()).append("'");
        }
        Stmt.append(" order by tabbname;");
        setSelectStatement(Stmt.toString());
    }

    /**
     * Méthode pour filter les résultats par identifiant de groupe et par nom.
     *
     * @param gid l'identifiant de groupe à utiliser pour le filtrage.
     * @param Name à utiliser pour le filtrage.
     */
    @Override
    public void filterByName(int gid, String Name) {
        StringBuffer Stmt;

        Stmt = new StringBuffer(InvariableSelectStatement);
        Stmt.append(" where tunum = ").append(gid);
        if (Name != null) {
            Stmt.append(" and tlname like '").append(Name.trim()).append("%'");
        }
        Stmt.append(" order by tlname;");
        setSelectStatement(Stmt.toString());
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
