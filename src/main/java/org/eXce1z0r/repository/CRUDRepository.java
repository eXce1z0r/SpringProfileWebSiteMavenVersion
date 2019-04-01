package org.eXce1z0r.repository;

import java.util.List;
import java.util.Map;

public interface CRUDRepository<T>
{
	public void create(T object);
	public List<T> read(Map<String, String> searchBy);
	public void update(Map<String, String> searchBy, T updates);
	public void remove(T object);
}
