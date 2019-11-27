package dao;

import domain.Department;
import service.SchoolService;
import util.JdbcHelper;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public final class DepartmentDao {

	private static DepartmentDao departmentDao=new DepartmentDao();
	private DepartmentDao(){}

	public static DepartmentDao getInstance(){
		return departmentDao;
	}


	public Set<Department> findAll() throws SQLException {
		Set<Department> departments = new HashSet<Department>();
		//获得连接对象
		Connection connection = JdbcHelper.getConn();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("select * from Department");
		//若结果集仍然有下一条记录，则执行循环体
		while (resultSet.next()) {
			//向Departments集合中添加Department对象
			departments.add(new Department(
					resultSet.getInt("id"),
					resultSet.getString("description"),
					resultSet.getString("no"),
					resultSet.getString("remarks"),
					SchoolService.getInstance().find(resultSet.getInt("school_id"))));
		}
		//关闭资源
		JdbcHelper.close(resultSet, statement, connection);
		return departments;
	}

	public Department find(Integer id) throws SQLException {
		Department department = null;
		Connection connection = JdbcHelper.getConn();
		String deleteDepartment_sql = "SELECT * FROM Department WHERE id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(deleteDepartment_sql);
		//为预编译参数赋值
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		//由于id不能取重复值，故结果集中最多有一条记录
		//若结果集有一条记录，则以当前记录中的id,description,no,remarks值为参数，创建Department对象
		//若结果集中没有记录，则本方法返回null
		if (resultSet.next()) {
			department = new Department(
					resultSet.getInt("id"),
					resultSet.getString("description"),
					resultSet.getString("no"),
					resultSet.getString("remarks"),
					SchoolService.getInstance().find(resultSet.getInt("school_id")));
		}
		//关闭资源
		JdbcHelper.close(resultSet, preparedStatement, connection);
		return department;
	}

	public Set<Department> findAllBySchool(Integer schoolId) throws SQLException {
		Set<Department> departments = new HashSet<Department>();
		//获得连接对象
		Connection connection = JdbcHelper.getConn();
		//创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement("select * from Department where school_id=?");
		//为预编译参数赋值
		preparedStatement.setInt(1,schoolId);
		ResultSet resultSet=preparedStatement.executeQuery();
		//若结果集仍然有下一条记录，则执行循环体
		while (resultSet.next()) {
			//向Departments集合中添加Department对象
			departments.add(new Department(
					resultSet.getInt("id"),
					resultSet.getString("description"),
					resultSet.getString("no"),
					resultSet.getString("remarks"),
					SchoolService.getInstance().find(resultSet.getInt("school_id"))));
		}
		JdbcHelper.close(resultSet,preparedStatement,connection);
		return departments;
	}

	public boolean add(Department department) throws SQLException{
		Connection connection = JdbcHelper.getConn();
		String addDepartment_sql = "INSERT INTO Department (description,no,remarks,school_id) VALUES" + " (?,?,?,?)";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(addDepartment_sql);
		//为预编译参数赋值
		preparedStatement.setString(1, department.getDescription());
		preparedStatement.setString(2, department.getNo());
		preparedStatement.setString(3, department.getRemarks());
		preparedStatement.setInt(4,department.getSchool().getId());
		//执行预编译语句，获取添加记录行数并赋值给affectedRowNum
		int affectedRowNum = preparedStatement.executeUpdate();
		//关闭资源
		JdbcHelper.close(preparedStatement, connection);
		return affectedRowNum > 0;
	}


	//delete方法，根据Department的id值，删除数据库中对应的Department对象
	public boolean delete(int id) throws  SQLException {
		Connection connection = JdbcHelper.getConn();
		//写sql语句
		String deleteDepartment_sql = "DELETE FROM Department WHERE id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(deleteDepartment_sql);
		//为预编译参数赋值
		preparedStatement.setInt(1, id);
		//执行预编译语句，获取删除记录行数并赋值给affectedRowNum
		int affectedRows = preparedStatement.executeUpdate();
		//关闭资源
		JdbcHelper.close(preparedStatement, connection);
		return affectedRows > 0;
	}

	public boolean update(Department department) throws  SQLException {
		Connection connection = JdbcHelper.getConn();
		//写sql语句
		String updateDepartment_sql = " update Department set description=?,no=?,remarks=?,school_id=? where id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(updateDepartment_sql);
		//为预编译参数赋值
		preparedStatement.setString(1, department.getDescription());
		preparedStatement.setString(2, department.getNo());
		preparedStatement.setString(3, department.getRemarks());
		preparedStatement.setInt(4,department.getSchool().getId());
		preparedStatement.setInt(5, department.getId());
		//执行预编译语句，获取改变记录行数并赋值给affectedRowNum
		int affectedRows = preparedStatement.executeUpdate();
		//关闭资源
		JdbcHelper.close(preparedStatement, connection);
		return affectedRows > 0;
	}
}

