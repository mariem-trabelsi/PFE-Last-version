package com.foodsafety.service;

import com.foodsafety.dto.CategoryDTO;
import com.foodsafety.dto.EventDTO;
import com.foodsafety.mapper.EventMapper;
import com.foodsafety.model.Category;
import com.foodsafety.model.Event;
import com.foodsafety.repository.EventRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

//    public EventDTO createEvent(EventDTO eventDTO) {
//        Event event = eventMapper.toEntity(eventDTO);
//        Event savedEvent = eventRepository.save(event);
//        return eventMapper.toDTO(savedEvent);
//    }

    public EventDTO createEvent(EventDTO eventDTO) {
        try {
            log.debug("Request to save Event: {}", eventDTO);
            Event event = eventMapper.fromDTOToEntity(eventDTO);
            event = eventRepository.saveAndFlush(event);
            log.info("event added successfully  ");
            EventDTO eventDTO1 = new EventDTO();
            eventDTO1 = eventMapper.fromEntityToDTO(event);
            return eventDTO1;
        } catch (Exception e) {
            log.error("Cannot add event ", e);
            return null;
        }
    }

//    public List<EventDTO> getAllEvents() {
//        List<Event> events = eventRepository.findAll();
//        return events.stream()
//                .map(eventMapper::toDTO)
//                .collect(Collectors.toList());
//    }
    public List<EventDTO> getAllEvents() {
        try {
            List<Event> eventList = eventRepository.findAll();
            List<EventDTO> eventDTOS = eventMapper.fromEntitiesToDTOList(eventList);


            log.info("Events gotten successfully");
            return eventDTOS;
        } catch (Exception e) {
            log.error("Cannot get events from source", e);
            return null;
        }
    }

//    public List<EventDTO> getAllEventsByGroupId(String groupId) {
//        List<Event> events = eventRepository.findAllByGroupId(groupId);
//        return events.stream()
//                .map(eventMapper::toDTO)
//                .collect(Collectors.toList());
//    }

    public List<EventDTO> getAllEventsByGroupId(String groupId) {
        try {
            List<Event> eventList = eventRepository.findAllByGroupId(groupId);
            List<EventDTO> eventDTOS = eventMapper.fromEntitiesToDTOList(eventList);

            log.info("Found {} events with groupId {}", eventList.size(), groupId);
            return eventDTOS;
        } catch (Exception e) {
            log.error("Cannot get events from source", e);
            return null;
        }
    }
}
