package hu.webuni.cst.kamarasd.service;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.order.AuditOrder;
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
	
	@Transactional
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Course getHistoryForDefinedClass(long id, OffsetDateTime date) {
		
		long millis = date.toInstant().toEpochMilli();
		
		List resultList = AuditReaderFactory.get(entityManager)
				.createQuery()
				.forRevisionsOfEntity(Course.class, true, false)
				.add(AuditEntity.property("id").eq(id))
				.add(AuditEntity.property("timestamp").le(millis))
				.addOrder(AuditEntity.revisionProperty("timestamp").desc())
					.setMaxResults(1)
					.getResultList();
		
		if(!resultList.isEmpty()) {
			Course course = (Course) resultList.get(0);
			course.getStudents();
			course.getTeachers();
			return course;
		}
		
		return null;
		
	}
}
