package com.tagtheagency.portal.pitch.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tagtheagency.portal.model.PitchImage;

@Repository
public interface PitchImageDAO extends CrudRepository<PitchImage, Integer>{

}
