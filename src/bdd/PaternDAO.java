package bdd;

import java.sql.*;

/**
 * Classe qui décrit le patern DAO peremttant l'accès aux bases de données.
 * Elle sera progressivement remplacée par la classe PatternDAO plus performante.
 * 
 * @author Thierry Baribaud
 * @version Juin 2016
 */
public abstract class PaternDAO {

  /**
   * Connection to the database.
   */
  protected Connection MyConnection;

  /**
   * Number of affected row after an insert/update/delete operation.
   */
  protected int nbAffectedRow;

  /**
   * SQL statement used to select date
   */
  protected String ReadStatement;

  /**
   * SQL statement used to update date
   */
  protected String UpdateStatement;

  /**
   * SQL statement used to insert date
   */
  protected String InsertStatement;

  /**
   * SQL statement used to delete date
   */
  protected String DeleteStatement;

  /**
   * Select SQL statement prepared.
   */
  protected PreparedStatement ReadPreparedStatement;

  /**
   * Update SQL statement prepared.
   */
  protected PreparedStatement UpdatePreparedStatement;

  /**
   * Insert SQL statement prepared.
   */
  protected PreparedStatement InsertPreparedStatement;

  /**
   * Delete SQL statement prepared.
   */
  protected PreparedStatement DeletePreparedStatement;

  /**
   * ResultSet.
   */
  protected ResultSet ReadResultSet;

  /*
   * @param Number of affected row after an insert/update/delete operation.
   */
  protected void setNbAffectedRow(int nbAffectedRow) {
    this.nbAffectedRow = nbAffectedRow;
    }

  /*
   * @param SQL statement to select data.
   */
  public void setReadStatement(String Statement) {
    this.ReadStatement = Statement;
    }

  /**
   * @param Statement SQL statement to update data.
   */
  public void setUpdateStatement(String Statement) {
    this.UpdateStatement = Statement;
    }

  /**
   * @param Statement SQL statement to insert data.
   */
  public void setInsertStatement(String Statement) {
    this.InsertStatement = Statement;
    }

  /**
   * @param Statement SQL statement to delete data.
   */
  public void setDeleteStatement(String Statement) {
    this.DeleteStatement = Statement;
    }

  /**
   * @return number of affected rows after an insert/update/delete operation
   */
  public int getNbAffectedRow() {
    return nbAffectedRow;
    }

  /**
   * @return SQL statement to select data.
   */
  public String getReadStatement() {
    return ReadStatement;
    }

  /**
   * @return SQL statement to update data.
   */
  public String getUpdateStatement() {
    return UpdateStatement;
    }

  /**
   * @return SQL statement to insert data.
   */
  public String getInsertStatement() {
    return InsertStatement;
    }

  /**
   * @return SQL statement to delete data.
   */
  public String getDeleteStatement() {
    return DeleteStatement;
    }

  /** 
   * Prepare resource to select data for a given SQL statement.
   * @throws java.sql.SQLException en cas d'erreur SQL.
   */
  public void setReadPreparedStatement() throws SQLException {
    ReadPreparedStatement = MyConnection.prepareStatement(getReadStatement());
    }

  /** 
   * Prepare resource to update data with a given SQL statement.
   * @throws java.sql.SQLException en cas d'erreur SQL.
   */
  public void setUpdatePreparedStatement() throws SQLException {
    UpdatePreparedStatement = MyConnection.prepareStatement(getUpdateStatement());
    }

  /** 
   * Prepare resource to insert data with a given SQL statement.
   * @throws java.sql.SQLException en cas d'erreur SQL.
   */
  public void setInsertPreparedStatement() throws SQLException {
    InsertPreparedStatement = MyConnection.prepareStatement(getInsertStatement());

//  public void setInsertPreparedStatement() throws SQLException {
//    InsertPreparedStatement = MyConnection.prepareStatement(getInsertStatement(),
//      PreparedStatement.RETURN_GENERATED_KEYS);
    }
  
  /** 
   * Prepare resource to delete data with a given SQL statement.
   * @throws java.sql.SQLException en cas d'erreur SQL.
   */
  public void setDeletePreparedStatement() throws SQLException {
    DeletePreparedStatement = MyConnection.prepareStatement(getDeleteStatement());
    }

  /**
   * Associate the result of the request to a ResultSet.
   * @throws java.sql.SQLException en cas d'erreur SQL.
   */
  public void setReadResultSet() throws SQLException {
    ReadResultSet = ReadPreparedStatement.executeQuery();
    }

  /**
   * Select a record.
   * @return selected object.
   */
  abstract public Object select();

  /**
   * Update a record.
   * @param MyObject object to update.
   */
  abstract public void update(Object MyObject);

  /**
   * Delete a record.
   * @param MyId Object's ID
   */
  abstract public void delete(int MyId);

  /**
   * Insert a record.
   * @param MyObject Object to insert.
   */
  abstract public void insert(Object MyObject);
//  abstract public void insertTb(Object MyObject);

  /**
   * Method that closes all active resources except database connection.
   * @throws java.sql.SQLException en cas d'erreur SQL.
   */
  public void close() throws SQLException {

    ReadResultSet.close();
    ReadPreparedStatement.close();
    UpdatePreparedStatement.close();
    InsertPreparedStatement.close();
    DeletePreparedStatement.close();
  }
}
