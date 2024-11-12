package com.estudos.desafios.pontosdeinteresse.controller;

import com.estudos.desafios.pontosdeinteresse.controller.dto.CreatePointOfInterest;
import com.estudos.desafios.pontosdeinteresse.entity.PointOfInterest;
import com.estudos.desafios.pontosdeinteresse.repository.PointsOfInterestRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PointOfInterestController {

    private final PointsOfInterestRepository repository;

    public PointOfInterestController(PointsOfInterestRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/points-of-interest")
    public ResponseEntity<Void>createPoi(@RequestBody CreatePointOfInterest body){
        repository.save(new PointOfInterest(body.name(), body.x(), body.y()));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/points-of-interest")
    public ResponseEntity<Page<PointOfInterest>>listPoi(@RequestParam(name = "page", defaultValue = "0")Integer page,
                                       @RequestParam(name = "pageSize", defaultValue = "10")Integer pageSize){
        var body = repository.findAll(PageRequest.of(page, pageSize));
        return ResponseEntity.ok(body);
    }

    @GetMapping("/near-me")
    public ResponseEntity<List<PointOfInterest>>listPoi(@RequestParam("x") Long x,
                                                        @RequestParam("y") Long y,
                                                        @RequestParam("dMax") Long dMax){

        var xMin = x - dMax;
        var xMax = x + dMax;
        var yMin = y - dMax;
        var yMax = y + dMax;

        var body = repository.findNearMe(xMin, xMax, yMin, yMax)
                .stream()
                .filter(p -> distanceBetweenPoints(x, y, p.getX(), p.getY()) <= dMax)
                .toList();
        return ResponseEntity.ok(body);
    }

    private Double distanceBetweenPoints(Long x1, Long y1, Long x2, Long y2){
        return Math.sqrt(Math.pow(x2 - x1, 2)+ Math.pow(y2 - y1, 2));
    }
}
