package nulp.lab.contoller;

import nulp.lab.model.Flight;
import nulp.lab.service.FlightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/flights")
public class FlightController {
    private final FlightService flightService;
    public FlightController(FlightService service) { this.flightService = service; }

    @GetMapping
    public List<Flight> all() {
        return flightService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> get(@PathVariable("id") Long id) {
        Optional<Flight> f = flightService.findById(id);
        return f.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Flight> create(@RequestBody Flight flight) {
        Flight created = flightService.create(flight);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Flight> update(@PathVariable("id") Long id, @RequestBody Flight flight) {
        Optional<Flight> updated = flightService.update(id, flight);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        if (flightService.delete(id)) return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }
}
