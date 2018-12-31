package com.moszis.template.service.services;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.moszis.template.service.dao.TestDao;

@Stateless
public class TestBusinessService {

	@EJB
	TestDao testDao;
	
}
