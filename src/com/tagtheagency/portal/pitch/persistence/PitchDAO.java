package com.tagtheagency.portal.pitch.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tagtheagency.portal.model.Pitch;

@Repository
public interface PitchDAO extends CrudRepository<Pitch, Integer>{

}
