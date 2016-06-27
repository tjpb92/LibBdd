package bdd;

import com.informix.jdbc.IfmxStatement;
import java.sql.*;

/**
 * Classe qui d�crit les m�thodes pour acc�der � la table furgent avec JDBC.
 *
 * @author Thierry Baribaud
 * @version Juin 2016
 */
public class FurgentDAO extends PatternDAO {

    /**
     * Constructeur de la classe FurgentDAO.
     *
     * @param MyConnection connexion � la base de donn�es courante.
     * @throws ClassNotFoundException en cas de classe non trouv�e.
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public FurgentDAO(Connection MyConnection)
            throws ClassNotFoundException, SQLException {

        this.MyConnection = MyConnection;

        setInvariableSelectStatement("select unum, uabbname, uname, unewurg"
                + " from furgent");

        setUpdateStatement("update furgent"
                + " set uabbname=?, uname=?, unewurg=?"
                + " where unum=?;");

        setInsertStatement("insert into furgent"
                + " (uabbname, uname, unewurg)"
                + " values(?, ?, ?);");

        setDeleteStatement("delete from furgent"
                + " where unum=?;");
    }

    /**
     * Selectionne un service d'urgence.
     *
     * @return un service d'urgence.
     */
    @Override
    public Furgent select() {
        Furgent MyFurgent = null;

        try {
            if (SelectResultSet.next()) {
                MyFurgent = new Furgent();
                MyFurgent.setUnum(SelectResultSet.getInt("unum"));
                MyFurgent.setUabbname(SelectResultSet.getString("uabbname"));
                MyFurgent.setUname(SelectResultSet.getString("uname"));
                MyFurgent.setUnewurg(SelectResultSet.getInt("unewurg"));
            } else {
                System.out.println("Lecture de furgent termin�e");
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur en lecture de furgent "
                    + MyException.getMessage());
        }
        return MyFurgent;
    }

    /**
     * Met � jour un service d'urgence.
     *
     * @param MyFurgent service d'urgence � mettre � jour.
     */
    public void update(Furgent MyFurgent) {
        try {
            UpdatePreparedStatement.setString(1, MyFurgent.getUabbname());
            UpdatePreparedStatement.setString(2, MyFurgent.getUname());
            UpdatePreparedStatement.setInt(3, MyFurgent.getUnewurg());
            UpdatePreparedStatement.setInt(4, MyFurgent.getUnum());
            setNbAffectedRow(UpdatePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de mettre � jour furgent");
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de la mise � jour de furgent "
                    + MyException.getMessage());
        }
    }

    /**
     * Supprime d�finitivement un service d'urgence.
     *
     * @param unum identiant du service d'urgence � supprimer.
     */
    @Override
    public void delete(int unum) {
        try {
            DeletePreparedStatement.setInt(1, unum);
            setNbAffectedRow(DeletePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de d�truire un service d'urgence dans furgent");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression d'un service d'urgence dans furgent "
                    + e.getMessage());
        }
    }

    /**
     * Ajoute un service d'urgence dans la table furgent.
     *
     * @param MyFurgent service d'urgence � ajouter � la table furgent.
     */
    public void insert(Furgent MyFurgent) {
//        ResultSet MyKeyResultSet = null;

        try {
            System.out.println("uname=" + MyFurgent.getUname());
            InsertPreparedStatement.setString(1, MyFurgent.getUabbname());
            InsertPreparedStatement.setString(2, MyFurgent.getUname());
            InsertPreparedStatement.setInt(3, MyFurgent.getUnewurg());
            setNbAffectedRow(InsertPreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible d'ajouter un service d'urgence dans furgent");
            } else {
//                Does not work with Informix IDS2000
//                MyKeyResultSet = InsertPreparedStatement.getGeneratedKeys();
//                if (MyKeyResultSet.next()) {
//                    MyFurgent.setUnum((int) MyKeyResultSet.getInt(1));
//                MyKeyResultSet.close();
                MyFurgent.setUnum(
                        ((IfmxStatement) InsertPreparedStatement).getSerial()
                );
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de l'insertion d'un service d'urgence dans furgent "
                    + MyException.getMessage());
        }
    }

    /**
     * M�thode pour filter les r�sultats par identifiant.
     *
     * @param id l'identifiant � utiliser pour le filtrage.
     */
    @Override
    public void filterById(int id){
        StringBuffer Stmt;
        
        Stmt = new StringBuffer(InvariableSelectStatement);
        Stmt.append(" where unum = ").append(id).append(";");
        setSelectStatement(Stmt.toString());
    }

    /**
     * M�thode pour filter les r�sultats par code.
     *
     * @param Code � utiliser pour le filtrage.
     */
    @Override
    public void filterByCode(String Code){
        StringBuffer Stmt;
        
        Stmt = new StringBuffer(InvariableSelectStatement);
        Stmt.append(" where uabbname = '").append(Code).append("';");
        setSelectStatement(Stmt.toString());
    }

    /**
     * M�thode pour filter les r�sultats par identifiant et par code.
     *
     * @param id l'identifiant � utiliser pour le filtrage.
     * @param Code � utiliser pour le filtrage.
     */
    @Override
    public void filterByCode(int id, String Code){
        throw new UnsupportedOperationException("Non support� actuellement"); 
    }

    /**
     * M�thode pour filter les r�sultats par identifiant et par nom.
     *
     * @param id l'identifiant � utiliser pour le filtrage.
     * @param Name � utiliser pour le filtrage.
     */
    @Override
    public void filterByName(int id, String Name) {
        StringBuffer Stmt;
        
        Stmt = new StringBuffer(InvariableSelectStatement);
        Stmt.append(" where uname like '").append(Name).append("%';");
        setSelectStatement(Stmt.toString());
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
