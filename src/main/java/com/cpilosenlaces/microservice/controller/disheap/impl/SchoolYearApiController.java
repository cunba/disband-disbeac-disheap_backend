package com.cpilosenlaces.microservice.controller.disheap.impl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.cpilosenlaces.microservice.controller.disheap.SchoolYearApi;
import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disheap.SchoolYear;
import com.cpilosenlaces.microservice.model.disheap.dto.SchoolYearDTO;
import com.cpilosenlaces.microservice.model.util.HandledResponse;
import com.cpilosenlaces.microservice.service.disheap.SchoolYearService;

@Controller
public class SchoolYearApiController implements SchoolYearApi {

    @Autowired
    private SchoolYearService sys;

    @Override
    public ResponseEntity<SchoolYear> getById(UUID id) throws NotFoundException {
        try {
            return new ResponseEntity<>(sys.findById(id), HttpStatus.OK);
        } catch (NotFoundException nfe) {
            throw new NotFoundException("School year with ID " + id + " does not exists.");
        }
    }

    @Override
    public ResponseEntity<List<SchoolYear>> getAll() {
        return new ResponseEntity<>(sys.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SchoolYear> save(SchoolYearDTO schoolYearDTO) throws NotFoundException {
        ModelMapper mapper = new ModelMapper();
        SchoolYear schoolYear = mapper.map(schoolYearDTO, SchoolYear.class);
        return new ResponseEntity<>(sys.save(schoolYear), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<HandledResponse> update(UUID id, SchoolYearDTO schoolYearDTO) throws NotFoundException {
        SchoolYear schoolYear = null;
        try {
            schoolYear = sys.findById(id);
        } catch (NotFoundException nfe) {
            throw new NotFoundException("School year with ID " + id + " does not exists.");
        }

        schoolYear.setName(schoolYearDTO.getName());
        schoolYear.setStudy(schoolYearDTO.getStudy());

        sys.save(schoolYear);

        return new ResponseEntity<>(new HandledResponse("School year updated", 1), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SchoolYear> delete(UUID id) throws NotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<List<SchoolYear>> deleteAll() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
