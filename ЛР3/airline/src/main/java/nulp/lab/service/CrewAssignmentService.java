package nulp.lab.service;

import nulp.lab.model.CrewMember;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CrewAssignmentService {
    private final Map<Long, List<CrewMember>> assignments = new ConcurrentHashMap<>();
    private final AtomicLong memberIdGen = new AtomicLong();

    public List<CrewMember> getCrew(Long flightId) {
        return assignments.getOrDefault(flightId, Collections.emptyList());
    }
    public List<CrewMember> assign(Long flightId, CrewMember member) {
        long id = memberIdGen.incrementAndGet();
        member.setId(id);
        assignments.computeIfAbsent(flightId, k -> new ArrayList<>()).add(member);
        return getCrew(flightId);
    }
    public boolean remove(Long flightId, Long memberId) {
        List<CrewMember> list = assignments.get(flightId);
        if (list == null) return false;
        return list.removeIf(m -> m.getId().equals(memberId));
    }
}
