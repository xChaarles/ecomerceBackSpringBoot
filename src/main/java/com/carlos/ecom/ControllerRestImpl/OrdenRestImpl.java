package com.carlos.ecom.ControllerRestImpl;

import com.carlos.ecom.ControllerRest.OrdenRest;
import com.carlos.ecom.dto.OrdenRes;
import com.carlos.ecom.serviceImpl.OrdenServideImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrdenRestImpl implements OrdenRest {

    @Autowired
    OrdenServideImpl ordenServideImpl;

}
