package com.example.demo;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

	@Autowired
	Consigmentrepo workallotedrepo;
	@Autowired
	ModelMapper modelMapper;
	

	
	
	@PostMapping(value = "/addcust")
	public String addcust(@RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("product") String product,
            @RequestParam("proweight") double proweight,
            @RequestParam("prodistance")double prodistance,
			 @RequestParam("image") MultipartFile image
            ) throws IOException {
    servicess.registercustomer(name,email,password,product,proweight,prodistance,image);
      System.out.println(email);
		return "customer added successsfully";
	}
    
	@GetMapping(value = "/download/getcust")
	public List<Customer> apllypagination(@RequestParam(value = "pageNo",defaultValue = "0",required = false)int pagenumber
			,@RequestParam(value="pageSize",defaultValue = "5",required = false) int pagesize
			,@RequestParam(value="sortBy",defaultValue = "id",required = false)  String sortBy
			) {
         
		    
		
		
		// PageRequest of = PageRequest.of(pagenumber,
		// pagesize,Sort.by(Sort.Direction.DESC,String.valueOf(id)));
		PageRequest of = PageRequest.of(pagenumber, pagesize,Sort.by(sortBy));
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
	@GetMapping(value="/findall")
	 @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<Customer> findallcust(){
		       List<Customer> findAll = crepo.findAll();
//				/*
//				 * List<Workalloted> collect =
//				 * findAll.stream().map(i->i.getListwork()).flatMap(i->i.stream()).collect(
//				 * Collectors.toList()); System.out.println(collect);
//				 */
	   List<Customer> collect2 = findAll.stream().map(i->i)
      .collect(Collectors.toList());  
//	   System.out.println(collect2);
	           List<Userdto> collect = findAll.stream().map(cust->modelMapper.map(cust,Userdto.class)).collect(Collectors.toList());
	   
	           
	List<Consigment> collect3 = collect2.stream().map(cust->cust.getListofconsigment()).flatMap(cust->cust.stream()).collect(Collectors.toList());        
	           
	           
	   
   
		    return findAll ;	
		
		
		
	}
	
	
	

	  @PostMapping(value="/consigment/{email}")
	  @PreAuthorize("hasAuthority('ROLE_ADMIN')") 
	  public String workaddedsuccessfully(@PathVariable String email, @RequestBody Consigment consigment) 
	  { 
		  servicess.workaddedsuccesssfully(email,consigment); 
		  Customer cust = crepo.findByEmail(email); return"successfully-added";
	  
	  }
	
	/*
	 * @GetMapping(value="/worked/{email}")
	 * 
	 * @PreAuthorize("hasAuthority('ROLE_ADMIN')") public List<Productalloted>
	 * getmap(@PathVariable String email) { Customer cust =
	 * crepo.findByEmail(email); return workallotedrepo.findAllByCustomer(cust);
	 * 
	 * 
	 * 
	 * 
	 * }
	 */
	

  	
		/*
		 * @GetMapping(value="/workofuser/{email}")
		 * 
		 * @PreAuthorize("hasAuthority('ROLE_USER')") public List<Productalloted>
		 * getmap2(@PathVariable String email) { Customer cust =
		 * crepo.findByEmail(email); return workallotedrepo.findAllByCustomer(cust);
		 * 
		 * }
		 */
	  
		@GetMapping(value= {"/download/{email}"})
	  	public ResponseEntity<byte[]> downloadimge(@PathVariable String email){
	  		Customer cust = crepo.findByEmail(email);
	  	
	  		byte[] images = cust.getImage();
	  		
	  		return  ResponseEntity.status(HttpStatus.OK)
	  			.contentType(MediaType.IMAGE_JPEG).body(images);
	  		
	  	}
		@PostMapping(value= {"/download/update/{id}"})
	public String update(@PathVariable int id,@RequestBody Userdto userdto) {
			Customer customer = crepo.findById(id).get();
			customer.setEmail(userdto.getEmail());
			customer.setName(userdto.getName());
			customer.setProduct(userdto.getProduct());
			customer.setProweight(userdto.getProweight());
			customer.setProdistance(userdto.getProdistance());
			crepo.save(customer);
		
		
		return"success";
	}
		@GetMapping(value= {"/download/show/{id}"})
	public Userdto find(@PathVariable int id) {
			Customer customer = crepo.findById(id).get();
			//Userdto userdto=new Userdto();
			
			/*
			 * userdto.setName(customer.getName()); userdto.setEmail(customer.getEmail());
			 * userdto.setProduct(customer.getProduct());
			 * userdto.setProductdistance(customer.getProdistance());
			 * userdto.setProductweight(customer.getProweight());
			 */
			
			Userdto map = modelMapper.map(customer,Userdto.class);
			
			
		return map;
	}
		@DeleteMapping(value="/download/delete/{id}")
		public String deletebyid(@PathVariable int id) {
		        Customer customer = crepo.findById(id).get();
		        
		        crepo.delete(customer);
		
		return "deleted";
	
		
		}
		
	  
	  
	  
	
	@GetMapping(value="/showprice/{email}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public double  getprice(@PathVariable String email) {
		   Customer cust = crepo.findByEmail(email);        
		String prod=     cust.getProduct();
		double weight=     cust.getProweight();
      double distance =cust.getProdistance();
      
      if(weight<10.00 || distance<100.0) {
    	  return (weight*distance)+distance*20;
      }
      else if(weight>100 && weight<10|| distance<1000) {
    	  
    	return  (weight*distance)-distance;
      }
      else {
    	  return 0.0;
      }
      
		     
		
  
		
		
		
		
	}
	
	
	

	
	

}
