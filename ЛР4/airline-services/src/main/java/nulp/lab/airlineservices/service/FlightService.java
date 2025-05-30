package nulp.lab.airlineservices.service;

import nulp.lab.airlineservices.model.Flight;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class FlightService {
    private final Map<Long, Flight> flights = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong();

    public List<Flight> findAll() {
        return new ArrayList<>(flights.values());
    }
    public Optional<Flight> findById(Long id) {
        return Optional.ofNullable(flights.get(id));
    }
    public Flight create(Flight flight) {
        long id = idGen.incrementAndGet();
        flight.setId(id);
        flights.put(id, flight);
        return flight;
    }
    public Optional<Flight> update(Long id, Flight flight) {
        if (!flights.containsKey(id)) return Optional.empty();
        flight.setId(id);
        flights.put(id, flight);
        return Optional.of(flight);
    }
    public boolean delete(Long id) {
        return flights.remove(id) != null;
    }
}


