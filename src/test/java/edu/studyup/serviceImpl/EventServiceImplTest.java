package edu.studyup.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import edu.studyup.entity.Event;
import edu.studyup.entity.Location;
import edu.studyup.entity.Student;
import edu.studyup.util.DataStorage;
import edu.studyup.util.StudyUpException;

class EventServiceImplTest {

	EventServiceImpl eventServiceImpl;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		eventServiceImpl = new EventServiceImpl();
		//Create Student
		Student student = new Student();
		student.setFirstName("John");
		student.setLastName("Doe");
		student.setEmail("JohnDoe@email.com");
		student.setId(1);
		
		//Create Event1
		Event event = new Event();
		event.setEventID(1);
		event.setDate(new Date());
		event.setName("Event 1");
		Location location = new Location(-122, 37);
		event.setLocation(location);
		List<Student> eventStudents = new ArrayList<>();
		eventStudents.add(student);
		event.setStudents(eventStudents);
		
		
		
		DataStorage.eventData.put(event.getEventID(), event);
	}

	@AfterEach
	void tearDown() throws Exception {
		DataStorage.eventData.clear();
	}

	@Test
	void testUpdateEventName_GoodCase() {
		Event event = new Event();
		event.setEventID(1);
		event.setName("Renamed Event 1");
		eventServiceImpl.updateEvent(event);
		assertEquals("Renamed Event 1", DataStorage.eventData.get(event.getEventID()).getName());
	}
	
	@Test
	@Disabled
	void testUpdateEvent_badCase() {
		Event event = null;
		Assertions.assertThrows(StudyUpException.class, () -> {
			eventServiceImpl.updateEvent(event);
		  });
	}
	
	@Test
	void testUpdateEventID_BadCase() {
		Event event1 = DataStorage.eventData.get(1);
		Event event2 = new Event();
		event2.setEventID(1);
		event2.setName("Event 2");
		eventServiceImpl.updateEvent(event2);
		assertEquals("Event 1", DataStorage.eventData.get(event1.getEventID()).getName());
	}
	
	@Test
	void testUpdateEventID_GoodCase() {
		Event event2 = new Event();
		event2.setEventID(1);
		event2.setName("Event 2");
		eventServiceImpl.updateEvent(event2);
		assertEquals("Event 2", DataStorage.eventData.get(event2.getEventID()).getName());
	}
	
	@Test
	void testGetActiveEvents_BadCase() {
		Event event2 = new Event();
		event2.setEventID(0);
		event2.setDate(new Date(999999999999999999L));
		event2.setName("Event 2");
		DataStorage.eventData.put(event2.getEventID(), event2);
		
		Event event = DataStorage.eventData.get(1);
		event.setDate(new Date(0L));
		eventServiceImpl.updateEvent(event);
		List<Event> activeEvents = eventServiceImpl.getActiveEvents();
		assert activeEvents.contains(event);
	}
	@Test
	void testGetActiveEvents_GoodCase() {
		Event event = new Event();
		event.setEventID(0);
		event.setDate(new Date(999999999999999999L));
		eventServiceImpl.updateEvent(event);
		List<Event> activeEvents = eventServiceImpl.getActiveEvents();
		assert activeEvents.contains(event);
	}
	@Test
	void testGetPastEvents_GoodCase() {
		Event event = new Event();
		event.setEventID(0);
		event.setDate(new Date(0L));
		eventServiceImpl.updateEvent(event);
		List<Event> activeEvents = eventServiceImpl.getPastEvents();
		assert activeEvents.contains(event);
	}
	
}
