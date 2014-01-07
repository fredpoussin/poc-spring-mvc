package jay.tutorial.web;

import java.io.StringReader;
import java.util.List;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import jay.tutorial.ds.EmployeeDS;
import jay.tutorial.model.Employee;
import jay.tutorial.model.EmployeeList;
import jay.tutorial.util.AtomUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sun.syndication.feed.atom.Feed;

@Controller
public class EmployeeController {

	private EmployeeDS employeeDS;
	
	@Autowired 
	public void setEmployeeDS(EmployeeDS ds) {
		this.employeeDS = ds;
	}
	
	private Jaxb2Marshaller jaxbMarshaller;
	
	@Autowired
	public void setJaxb2Mashaller(Jaxb2Marshaller jaxbMarshaller) {
		this.jaxbMarshaller = jaxbMarshaller;
	}

	@RequestMapping(method=RequestMethod.GET, value="/employees/{id}")
	public ModelAndView getEmployee(@PathVariable String id) {
		Employee e = employeeDS.get(Long.parseLong(id));
		return new ModelAndView("emp/detail", "employee", e);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/employees/{id}")
	public ModelAndView updateEmployee(@RequestBody String body) {
		Source source = new StreamSource(new StringReader(body));
		Employee e = (Employee) jaxbMarshaller.unmarshal(source);
		employeeDS.update(e);
		return new ModelAndView("emp/detail", "employee", e);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/employee")
	public ModelAndView addEmployee(@RequestBody String body) {
		Source source = new StreamSource(new StringReader(body));
		Employee e = (Employee) jaxbMarshaller.unmarshal(source);
		employeeDS.add(e);
		return new ModelAndView("emp/detail", "employee", e);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/employees/{id}")
	public ModelAndView removeEmployee(@PathVariable String id) {
		employeeDS.remove(Long.parseLong(id));
		List<Employee> employees = employeeDS.getAll();
		EmployeeList list = new EmployeeList(employees);
		return new ModelAndView("emp/list", "employees", list);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/employees")
	public ModelAndView getEmployees() {
		List<Employee> employees = employeeDS.getAll();
		EmployeeList list = new EmployeeList(employees);
		return new ModelAndView("emp/list", "employees", list);
	}
	
	////////////////////////// @ResponseBody ////////////////////////
	
	@RequestMapping(method=RequestMethod.GET, value="/emps/{id}", headers="Accept=application/xml, application/json")
	public @ResponseBody Employee getEmp(@PathVariable String id) {
		Employee e = employeeDS.get(Long.parseLong(id));
		return e;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/emps", headers="Accept=application/xml, application/json")
	public @ResponseBody EmployeeList getAllEmp() {
		List<Employee> employees = employeeDS.getAll();
		EmployeeList list = new EmployeeList(employees);
		return list;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/emps", headers="Accept=application/atom+xml")
	public @ResponseBody Feed getEmpFeed() {
		List<Employee> employees = employeeDS.getAll();
		return AtomUtil.employeeFeed(employees, jaxbMarshaller);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/emp")
	public @ResponseBody Employee addEmp(@RequestBody Employee e) {
		employeeDS.add(e);
		return e;
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/emps/{id}")
	public @ResponseBody Employee updateEmp(@RequestBody Employee e, @PathVariable String id) {
		employeeDS.update(e);
		return e;
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/emps/{id}")
	public @ResponseBody void removeEmp(@PathVariable String id) {
		employeeDS.remove(Long.parseLong(id));
	}
}
