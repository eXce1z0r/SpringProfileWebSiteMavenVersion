package org.eXce1z0r.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eXce1z0r.model.AccountModel;
import org.eXce1z0r.repository.CRUDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService implements WebSitePageService<AccountModel>
{
	@Autowired
	private CRUDRepository<AccountModel> hibernateDAO;

	@Override
	public boolean registerNewAccount(AccountModel account) 
	{
		if(checkUniqueness(account.getAccountName(), account.getMail()))
		{
			hibernateDAO.create(account);
			return true;
		}		
		return false;
	}

	@Override
	public List<AccountModel> getAccountBy(Map<String, String> searchBy) 
	{
		return hibernateDAO.read(searchBy);
	}

	@Override
	public void editAccount(Map<String, String> searchBy, AccountModel accountUpdates) 
	{
		hibernateDAO.update(searchBy, accountUpdates);
	}
	
	public Map<String, String> genPredicatesForUserCreds(String username, String password, String role)
	{
		Map<String, String> searchPredicates= new HashMap<String, String>();
		
		if(username.contains("@"))
		{
			searchPredicates.put("mail", username);		
		}
		else
		{
			searchPredicates.put("accountName", username);	
		}
		searchPredicates.put("password", password);
		searchPredicates.put("role", role);
		
		return searchPredicates;
	}
	
	private boolean checkUniqueness(String username, String mail)
	{
		Map<String, String> checkMap= new HashMap<String, String>();
		checkMap.put("accountName", username);
		
		if(getAccountBy(checkMap).size() == 0)
		{
			checkMap.clear();
			checkMap.put("mail", mail);
			if(getAccountBy(checkMap).size() == 0)
			{
				return true;
			}
		}
		
		return false;
	}
}
