package com.example.demo.services;

import com.example.demo.controllers.commands.VehicleFilter;
import com.example.demo.models.ScheduledTreatment;
import com.example.demo.models.Treatment;
import com.example.demo.repositories.ScheduledTreatmentRepository;
import com.example.demo.repositories.TreatmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;

@Service
public class TreatmentServiceImpl implements TreatmentService {

    @Autowired
    private TreatmentRepository treatmentRepository;

    @Autowired
    private ScheduledTreatmentRepository scheduledTreatmentRepository;

    @Override
    public Page<Treatment> getAllTreatments(Pageable pageable) {
        return treatmentRepository.findAll(pageable);
    }

    @Override
    public Treatment save(Treatment treatment){
        return treatmentRepository.save(treatment);
    }

    @Override
    public Treatment getById(Long id) {
        return treatmentRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id){ treatmentRepository.deleteById(id);
    }

    @Override
    public boolean exists(Long id){
        return treatmentRepository.existsById(id);
    }

    @Override
    public boolean isAssignedToAnyScheduledTreatment(Long id) {

        return scheduledTreatmentRepository.findScheduledTreatmentByTreatment_Id(id)!=null;
    }

    @Override
    public Page<Treatment> getSearch(VehicleFilter search, Pageable pageable) {
        Page page;
        if(search.isEmpty()){
            page = treatmentRepository.findAll(pageable);
        }else{
            page = treatmentRepository.findAllVehiclesUsingFilter(search.getPhraseLIKE(), search.getMinPrice(), search.getMaxPrice(), pageable);
        }

        return page;

    }
}
