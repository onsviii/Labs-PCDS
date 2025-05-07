package nulp.lab.contoller;

import nulp.lab.model.CrewMember;
import nulp.lab.service.CrewAssignmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights/{flightId}/crew")
public class CrewController {
    private final CrewAssignmentService crewService;
    public CrewController(CrewAssignmentService service) { this.crewService = service; }

    @GetMapping
    public List<CrewMember> getCrew(@PathVariable("flightId") Long flightId) {
        return crewService.getCrew(flightId);
    }

    @PostMapping
    public List<CrewMember> assign(@PathVariable("flightId") Long flightId, @RequestBody CrewMember member) {
        return crewService.assign(flightId, member);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> remove(@PathVariable("flightId") Long flightId, @PathVariable Long memberId) {
        if (crewService.remove(flightId, memberId)) return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }
}
