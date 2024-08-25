package com.validation.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.validation.dao.PropertyRepo;
import com.validation.entities.Property;


@Service
public class PropertyService { 
	
	@Autowired
    private PropertyRepo propertyrepo;
	
	public void saveProperty(Property property) {
	
		propertyrepo.save(property);
		
	}
	public List<Property> getAllProperties() {
        return propertyrepo.findAll();
    }
	public void updateProperty(int id ,Property property) {
		Property existingProperty = propertyrepo.findById(id).orElse(null);
        if (existingProperty != null) {
            BeanUtils.copyProperties(property, existingProperty, "propertyId");
            propertyrepo.save(existingProperty);
        }
	}
	public void deleteProperty(int id) {
		propertyrepo.deleteById(id);
	}
	public Property getPropertyById(int id) {
		return propertyrepo.findById(id).orElse(null);
	}
	
}
