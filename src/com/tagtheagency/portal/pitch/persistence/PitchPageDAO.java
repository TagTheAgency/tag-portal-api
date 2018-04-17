package com.tagtheagency.portal.pitch.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tagtheagency.portal.model.Pitch;
import com.tagtheagency.portal.model.PitchPage;

@Repository
public interface PitchPageDAO extends CrudRepository<PitchPage, Integer>{

	List<PitchPage> findByPitch(Pitch pitch);
	
}
