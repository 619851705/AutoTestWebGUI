package com.dcits.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcits.dao.OperationInterfaceDao;

@Service
public class OperationInterfaceService {
	@Autowired
	private OperationInterfaceDao dao;
}
