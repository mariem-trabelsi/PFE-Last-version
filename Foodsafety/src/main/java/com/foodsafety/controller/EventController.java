package com.foodsafety.controller;

import com.foodsafety.dto.EventDTO;
import com.foodsafety.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("goods/v1/events")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO eventDTO) {
        EventDTO createdEvent = eventService.createEvent(eventDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        List<EventDTO> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @Transactional(readOnly = true)
    @GetMapping("/groupId/{groupId}")
    public ResponseEntity<List<EventDTO>> getAllEventsByGroupId(@PathVariable String groupId) {
        List<EventDTO> events = eventService.getAllEventsByGroupId(groupId);
        return ResponseEntity.ok(events);
    }
}
