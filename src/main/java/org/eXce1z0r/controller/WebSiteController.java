package org.eXce1z0r.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.eXce1z0r.model.AccountModel;
import org.eXce1z0r.model.UserDetailsModel;
import org.eXce1z0r.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class WebSiteController 
{
	@Autowired
	private ProfileService service;
	
	@RequestMapping(value= {"/", "/index"}, method= {RequestMethod.POST, RequestMethod.GET})
	public String indexPage()
	{
		return "index_page";
	}
	
	@RequestMapping(value= "/profile", method= {RequestMethod.POST, RequestMethod.GET})
	public String profilePage(HttpServletRequest req, Authentication auth)
	{
		UserDetails userCreds= (UserDetails) auth.getPrincipal();
		String username= userCreds.getUsername();
		String password= userCreds.getPassword();
		String role= userCreds.getAuthorities().toString();
		role= role.substring(1, role.length()-1);
		
		Map<String, String> searchPredicates= service.genPredicatesForUserCreds(username, password, role);	
		
		List<AccountModel> foundAccounts= service.getAccountBy(searchPredicates);
		if(foundAccounts!= null && foundAccounts.size()> 0)
		{
			AccountModel currAccount= foundAccounts.get(0);
			if(currAccount!= null)
			{
				req.setAttribute("account_name", currAccount.getAccountName());
				req.setAttribute("mail", currAccount.getMail());
				req.setAttribute("role", currAccount.getRole());
				
				req.setAttribute("user_name", currAccount.getUserDetails().getUserName());
				req.setAttribute("user_surname", currAccount.getUserDetails().getUserSurname());
				req.setAttribute("user_patronymic", currAccount.getUserDetails().getUserPatronymic());
			}
		}

		return "profile_page";
	}
	
	@RequestMapping(value="/profile/edit", method= RequestMethod.GET)
	public String profileEditPageGet(HttpServletRequest req, Authentication auth)
	{
		UserDetails userCreds= (UserDetails) auth.getPrincipal();
		String username= userCreds.getUsername();
		String password= userCreds.getPassword();
		String role= userCreds.getAuthorities().toString();
		role= role.substring(1, role.length()-1);
		
		Map<String, String> searchPredicates= service.genPredicatesForUserCreds(username, password, role);
		
		List<AccountModel> accounts= service.getAccountBy(searchPredicates);
		if(accounts != null && accounts.size() > 0)
		{
			AccountModel account= accounts.get(0);
			UserDetailsModel userDetailsModel= account.getUserDetails();
			
			req.setAttribute("user_name", userDetailsModel.getUserName());
			req.setAttribute("user_surname", userDetailsModel.getUserSurname());
			req.setAttribute("user_patronymic", userDetailsModel.getUserPatronymic());
		}
		
		
		
		return "profile_edit_page";
	}
	
	@RequestMapping(value="/profile/edit", method= RequestMethod.POST)
	public String profileEditPagePost(HttpServletRequest req, Authentication auth)
	{
		UserDetails userCreds= (UserDetails) auth.getPrincipal();
		String username= userCreds.getUsername();
		String password= userCreds.getPassword();
		String role= userCreds.getAuthorities().toString();
		role= role.substring(1, role.length()-1);
		
		Map<String, String> searchPredicates= service.genPredicatesForUserCreds(username, password, role);
		
		AccountModel accountUpdates= new AccountModel();
			
		UserDetailsModel userDetailsModel= new UserDetailsModel();
		userDetailsModel.setUserName(req.getParameter("inputUserName"));
		userDetailsModel.setUserSurname(req.getParameter("inputUserSurname"));
		userDetailsModel.setUserPatronymic(req.getParameter("inputUserPatronymic"));
				
		accountUpdates.setUserDetails(userDetailsModel);
				
		service.editAccount(searchPredicates, accountUpdates);
		
		List<AccountModel> accounts= service.getAccountBy(searchPredicates);
		if(accounts != null && accounts.size() > 0)
		{
			AccountModel account= accounts.get(0);
			userDetailsModel= account.getUserDetails();
			
			req.setAttribute("user_name", userDetailsModel.getUserName());
			req.setAttribute("user_surname", userDetailsModel.getUserSurname());
			req.setAttribute("user_patronymic", userDetailsModel.getUserPatronymic());
		}
				
		return "profile_edit_page";
	}
	
	@RequestMapping(value="/auth", method= {RequestMethod.POST, RequestMethod.GET})
	public String loginPage()
	{
		return "login_page";
	}
	
	@RequestMapping(value="/register", method= RequestMethod.GET)
	public String registerPageGet()
	{
		return "register_page";
	}
	
	@RequestMapping(value="/register", method= RequestMethod.POST)
	public String registerPagePost(HttpServletRequest req)
	{
		String result_message= "";
		
		AccountModel aM= new AccountModel();
				
		String fieldVal= null;
		String wrongFieldsList= "";
		if((fieldVal=req.getParameter("inputAccountName"))!=null)//	additional restrictions may be added
		{
			aM.setAccountName(fieldVal);
		}
		else
		{
			wrongFieldsList+="account name, ";
		}
		
		if((fieldVal=req.getParameter("inputEmail"))!=null)
		{
			aM.setMail(fieldVal);
		}
		else
		{
			wrongFieldsList+="email, ";
		}
			
		if((fieldVal="{noop}"+req.getParameter("inputPassword"))!="{noop}")
		{
			aM.setPassword(fieldVal);
		}
		else
		{
			wrongFieldsList+="password, ";
		}
			
		aM.setRole("ROLE_USER");
		aM.setStatus(true);
			
		UserDetailsModel uD= new UserDetailsModel();
		uD.setUserName(req.getParameter("inputUserName"));
		uD.setUserSurname(req.getParameter("inputUserSurname"));
		uD.setUserPatronymic(req.getParameter("inputUserPatronymic"));		
		aM.setUserDetails(uD);
		
		System.out.println(wrongFieldsList.length());
			
		if(wrongFieldsList.length()>1)
		{
			result_message= "Errors were made in the fields: "+wrongFieldsList.substring(0, wrongFieldsList.length()-3);
		}
		else
		{
			if(service.registerNewAccount(aM))
			{
				result_message= "You successfully registered!!!";
				req.setAttribute("message", result_message);
				
				return "login_page";
			}
			else
			{
				result_message= "Sorry, this user cannot be registered.";
			}
		}
		
		req.setAttribute("message", result_message);

		return "register_page";
	}
	
	/*
	 * custom logout
	 * 
	 * @RequestMapping(value="/logout", method= RequestMethod.POST)
	public String logoutHandler(HttpServletRequest req)
	{
		HttpSession session= req.getSession(false);
		
		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(null);
		
		SecurityContextHolder.clearContext();
		
		session= req.getSession(false);
		
		if(session != null)
		{
			session.invalidate();
		}
		
		for(Cookie cookie: req.getCookies())
		{
			cookie.setMaxAge(0);
		}
		
		return "index_page";		
	}*/
}
