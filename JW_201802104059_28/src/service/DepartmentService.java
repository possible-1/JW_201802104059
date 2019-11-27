package service;


import dao.DepartmentDao;
import domain.Department;

import java.sql.SQLException;
import java.util.Set;

public final class DepartmentService {
    private static DepartmentDao departmentDao= DepartmentDao.getInstance();
    private static DepartmentService departmentService=new DepartmentService();
    private DepartmentService(){}

    public static DepartmentService getInstance(){
        return departmentService;
    }

    public Set<Department> findAll() throws SQLException {
        return departmentDao.findAll();
    }

    /*public Collection<Department> getAll(School school){
        Collection<Department> departments = new HashSet<Department>();
        for(Department department: departmentDao.findAll()){
            if(department.getSchool()==school){
                departments.add(department);
            }
        }
        return departments;
    }*/

    public Department find(Integer id) throws SQLException {
        return departmentDao.find(id);
    }

    public Set<Department> findAllBySchool(Integer schoolId) throws SQLException {
        return departmentDao.findAllBySchool(schoolId);
    }

    public boolean update(Department department) throws SQLException {
        return departmentDao.update(department);
    }

    public boolean add(Department department) throws SQLException {
        return departmentDao.add(department);
    }

    public boolean delete(Integer id) throws SQLException {
        return departmentDao.delete(id);
    }

}

