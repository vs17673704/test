package com.portal.question.dao.masterdata;

import com.portal.question.model.Company;
import com.portal.question.model.Users;

public interface UserCrudDAO {
	
	public Users findById(String companyId);
	
	public Users save(Users user);
	
	public String deleteById(String userId);
	
}
