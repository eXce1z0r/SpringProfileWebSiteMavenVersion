package org.eXce1z0r.service;

import java.util.List;
import java.util.Map;

public interface WebSitePageService <T>
{
	public boolean registerNewAccount(T account);
	
	public List<T> getAccountBy(Map<String, String> searchBy);
	
	public void editAccount(Map<String, String> searchBy, T accountUpdates);
}
