package service;


import dao.DegreeDao;
import domain.Degree;

import java.sql.SQLException;
import java.util.Set;

public final class DegreeService {
    private static DegreeDao degreeDao
            = DegreeDao.getInstance();
    private static DegreeService degreeService
            =new DegreeService();
    private DegreeService(){}

    public static DegreeService getInstance(){
        return degreeService;
    }

    public Set<Degree> findAll() throws SQLException {
        return degreeDao.findAll();
    }

    public Degree find(Integer id) throws SQLException {
        return degreeDao.find(id);
    }

    public boolean update(Degree degree) throws SQLException{
        return degreeDao.update(degree);
    }

    public boolean add(Degree degree) throws SQLException {
        return degreeDao.add(degree);
    }

    public boolean delete(Integer id) throws SQLException {
        return degreeDao.delete(id);
    }

}

