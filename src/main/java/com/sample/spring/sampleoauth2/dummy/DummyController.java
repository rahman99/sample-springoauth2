package com.sample.spring.sampleoauth2.dummy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="api")
public class DummyController {
	
	@Autowired
	private DummyDao dummyDao;
	
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public Authentication me(Authentication currentUser){
        return currentUser;
    }

//	@PreAuthorize("hasAuthority('ROLE_USER_EDIT')")
	@RequestMapping(value="dummy", method=RequestMethod.GET)
	public Iterable<Dummy> getAll(Pageable p){
		return dummyDao.findAll(p);
	}
	
//	@PreAuthorize("hasAuthority('USER_VIEW')")
	@GetMapping("dummy/id={id}")
	public Dummy getById(@PathVariable("id") String id){
		return dummyDao.findOne(id);
	}
}
