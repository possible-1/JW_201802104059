package service;


import dao.ProfTitleDao;
import domain.ProfTitle;

import java.sql.SQLException;
import java.util.Set;

public final class ProfTitleService {
	private static ProfTitleDao profTitleDao= ProfTitleDao.getInstance();
	private static ProfTitleService profTitleService=new ProfTitleService();
	private ProfTitleService(){}

	public static ProfTitleService getInstance(){
		return profTitleService;
	}

	public Set<ProfTitle> findAll() throws SQLException {
		return profTitleDao.findAll();
	}

	public ProfTitle find(Integer id) throws SQLException {
		return profTitleDao.find(id);
	}

	public boolean update(ProfTitle profTitle) throws SQLException {
		return profTitleDao.update(profTitle);
	}

	public boolean add(ProfTitle profTitle) throws SQLException {
		return profTitleDao.add(profTitle);
	}

	public boolean delete(Integer id) throws SQLException {
		return profTitleDao.delete(id);
	}

}

