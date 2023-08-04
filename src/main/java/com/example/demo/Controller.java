package com.example.demo;

import java.net.http.HttpRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Customerdto;
import com.example.demo.service.Servicess;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping(value = "/home")
public class Controller {
	@Autowired
	Servicess servicess;
	@Autowired
	Custrepo crepo;

	@PostMapping(value = "/addcust")
	public String addcust(@RequestBody Customerdto cust) {
		servicess.registercustomer(cust);

		return "customer added successsfully";
	}
    
	@GetMapping(value = "/getcust/{pageno}/{pagesize}")
	 @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<Customer> apllypagination(@PathVariable("pageno") int pagenumber,
			@PathVariable("pagesize") int pagesize/* @PathVariable("byid")int id */ ) {

		// PageRequest of = PageRequest.of(pagenumber,
		// pagesize,Sort.by(Sort.Direction.DESC,String.valueOf(id)));
		PageRequest of = PageRequest.of(pagenumber, pagesize);
		Page<Customer> pagecust = crepo.findAll(of);
		List<Customer> content = pagecust.getContent();
		return content;

	}
	@GetMapping(value="/getemail")
	 @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getemail(HttpServletRequest request) {
	  request.getHeader("Access-Control-Allow-Origin");
		
		return"added";
		
	}
	
	

}
