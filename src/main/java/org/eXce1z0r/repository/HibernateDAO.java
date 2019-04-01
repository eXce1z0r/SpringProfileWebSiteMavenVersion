package org.eXce1z0r.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.eXce1z0r.model.AccountModel;
import org.eXce1z0r.model.UserDetailsModel;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateDAO implements CRUDRepository<AccountModel>
{
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void create(AccountModel object) 
	{
		Session session= sessionFactory.openSession();
		session.beginTransaction();
		session.save(object);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public List<AccountModel> read(Map<String, String> searchBy) 
	{
		Session session= sessionFactory.openSession();
		session.beginTransaction();
		
		CriteriaBuilder criteriaBuilder= session.getCriteriaBuilder();
		CriteriaQuery<AccountModel> criteriaQuery= criteriaBuilder.createQuery(AccountModel.class);
		Root<AccountModel> root= criteriaQuery.from(AccountModel.class);
		criteriaQuery.select(root);
		
		List<Predicate> predicates= new ArrayList<>();
		for(String key: searchBy.keySet())
		{
			predicates.add(criteriaBuilder.equal(root.get(key), searchBy.get(key)));
		}
		
		criteriaQuery.where(predicates.toArray(new Predicate[] {}));
		
		Query<AccountModel> query= session.createQuery(criteriaQuery);
		List<AccountModel> accounts= query.list();
		
		session.getTransaction().commit();
		session.close();
		
		return accounts!=null?accounts:new ArrayList<AccountModel>();
	}

	@Override
	public void update(Map<String, String> searchBy, AccountModel updates) 
	{
		Session session= sessionFactory.openSession();
		session.beginTransaction();
		
		List<AccountModel> accounts= read(searchBy);
		
		if(accounts!= null && accounts.size() > 0)
		{
			AccountModel account= accounts.get(0);
			session.update(account);
			
			if(updates.getAccountName() != null && !updates.getAccountName().equals(""))
			{
				account.setAccountName(updates.getAccountName());
			}
			
			if(updates.getMail() != null && !updates.getMail().equals(""))
			{
				account.setMail(updates.getMail());
			}
			
			if(updates.getPassword() != null && !updates.getPassword().equals("") && !updates.getPassword().equals("{noop}"))
			{
				account.setPassword(updates.getPassword());
			}
			
			UserDetailsModel userDetailsModel= updates.getUserDetails();
			if(userDetailsModel.getUserName() != null && !userDetailsModel.getUserName().equals(""))
			{
				account.getUserDetails().setUserName(userDetailsModel.getUserName());
			}
			
			if(userDetailsModel.getUserSurname() != null && !userDetailsModel.getUserSurname().equals(""))
			{
				account.getUserDetails().setUserSurname(userDetailsModel.getUserSurname());
			}
			
			if(userDetailsModel.getUserPatronymic() != null && !userDetailsModel.getUserPatronymic().equals(""))
			{
				account.getUserDetails().setUserPatronymic(userDetailsModel.getUserPatronymic());
			}
		}
		
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public void remove(AccountModel object) 
	{
		Session session= sessionFactory.openSession();
		session.beginTransaction();
		session.remove(object);
		session.getTransaction();
		session.close();
	}
}
