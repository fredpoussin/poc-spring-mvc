package jay.tutorial.ds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jay.tutorial.model.Employee;

public class EmployeeDS {
	private static Map<Long, Employee> allEmployees;
	static {
		allEmployees = new HashMap<>();
		Employee e1 = new Employee(1L, "Nattawan Wattanasin", "nattawan.wattanasin@evry.com");
		Employee e2 = new Employee(2L, "Arne Candelius", "arne.candelius@evry.com");
		allEmployees.put(e1.getId(), e1);
		allEmployees.put(e2.getId(), e2);
	}
	
	public void add(Employee e) {
		allEmployees.put(e.getId(), e);
	}

	public Employee get(long id) {
		return allEmployees.get(id);
	}

	public List<Employee> getAll() {
		List<Employee> employees = new ArrayList<Employee>();
		for( Iterator<Employee> it = allEmployees.values().iterator(); it.hasNext(); ) {
			Employee e = it.next();
			employees.add(e);
		}
		return employees;
	}

	public void remove(long id) {
		allEmployees.remove(id);
	}

	public void update(Employee e) {
		allEmployees.put(e.getId(), e);
	}
}
