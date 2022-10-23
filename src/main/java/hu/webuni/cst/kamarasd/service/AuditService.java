package hu.webuni.cst.kamarasd.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.RevisionType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import hu.webuni.cst.kamarasd.model.Course;
import hu.webuni.cst.kamarasd.model.HistoryData;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class AuditService {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	@SuppressWarnings({"rawtypes", "unchecked"})
	public List<HistoryData<Course>> getCourseHistory(long id) {
		List resultList = AuditReaderFactory.get(entityManager)
			.createQuery()
			.forRevisionsOfEntity(Course.class, false, true)
			.add(AuditEntity.property("id").eq(id))
			.getResultList()
			.stream()
				.map(obj -> {
					Object[] objArray = (Object[])obj;
					DefaultRevisionEntity revEnt = (DefaultRevisionEntity)objArray[1];
							return new HistoryData<Course>(
								(Course)objArray[0],
								(RevisionType)objArray[2],
								revEnt.getId(),
								revEnt.getRevisionDate()
							);
				}).toList();
		
		return resultList;
	}
}
