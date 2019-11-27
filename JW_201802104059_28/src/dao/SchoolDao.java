package dao;


import domain.School;
import util.JdbcHelper;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public final class SchoolDao {
	private static SchoolDao schoolDao =
			new SchoolDao();
	private SchoolDao(){}
	public static SchoolDao getInstance(){
		return schoolDao;
	}

	public Set<School> findAll() throws SQLException {
		Set<School> schools = new HashSet<School>();
		//获得连接对象
		Connection connection = JdbcHelper.getConn();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("select * from School");
		//若结果集仍然有下一条记录，则执行循环体
		while (resultSet.next()) {
			//向Schools集合中添加School对象
			schools.add(new School(resultSet.getInt("id"), resultSet.getString("description"), resultSet.getString("no"), resultSet.getString("remarks")));
		}
		//关闭资源
		JdbcHelper.close(resultSet, statement, connection);
		return schools;
	}

	public School find(Integer id) throws SQLException {
		School school = null;
		Connection connection = JdbcHelper.getConn();
		String deleteSchool_sql = "SELECT * FROM School WHERE id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(deleteSchool_sql);
		//为预编译参数赋值
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		//由于id不能取重复值，故结果集中最多有一条记录
		//若结果集有一条记录，则以当前记录中的id,description,no,remarks值为参数，创建School对象
		//若结果集中没有记录，则本方法返回null
		if (resultSet.next()) {
			school = new School(resultSet.getInt("id"), resultSet.getString("description"), resultSet.getString("no"), resultSet.getString("remarks"));
		}
		//关闭资源
		JdbcHelper.close(resultSet, preparedStatement, connection);
		return school;
	}

	public boolean add(School school) throws SQLException{
		Connection connection = JdbcHelper.getConn();
		String addSchool_sql = "INSERT INTO School (description,no,remarks) VALUES" + " (?,?,?)";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(addSchool_sql);
		//为预编译参数赋值
		preparedStatement.setString(1, school.getDescription());
		preparedStatement.setString(2, school.getNo());
		preparedStatement.setString(3, school.getRemarks());
		//执行预编译语句，获取添加记录行数并赋值给affectedRowNum
		int affectedRowNum = preparedStatement.executeUpdate();
		//关闭资源
		JdbcHelper.close(preparedStatement, connection);
		return affectedRowNum > 0;
	}


	//delete方法，根据School的id值，删除数据库中对应的School对象
	public boolean delete(int id) throws  SQLException {
		Connection connection = JdbcHelper.getConn();
		//写sql语句
		String deleteSchool_sql = "DELETE FROM School WHERE id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(deleteSchool_sql);
		//为预编译参数赋值
		preparedStatement.setInt(1, id);
		//执行预编译语句，获取删除记录行数并赋值给affectedRowNum
		int affectedRows = preparedStatement.executeUpdate();
		//关闭资源
		JdbcHelper.close(preparedStatement, connection);
		return affectedRows > 0;
	}

	public boolean update(School school) throws  SQLException {
		Connection connection = JdbcHelper.getConn();
		//写sql语句
		String updateSchool_sql = " update School set description=?,no=?,remarks=? where id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(updateSchool_sql);
		//为预编译参数赋值
		preparedStatement.setString(1, school.getDescription());
		preparedStatement.setString(2, school.getNo());
		preparedStatement.setString(3, school.getRemarks());
		preparedStatement.setInt(4, school.getId());
		//执行预编译语句，获取改变记录行数并赋值给affectedRowNum
		int affectedRows = preparedStatement.executeUpdate();
		//关闭资源
		JdbcHelper.close(preparedStatement, connection);
		return affectedRows > 0;
	}
}

