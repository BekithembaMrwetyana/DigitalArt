package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.ArtBrand;
import za.ac.cput.domain.CartItem;
import za.ac.cput.service.ArtBrandService;
import za.ac.cput.service.CartItemService;

import java.util.List;

@RestController
@RequestMapping("/art_brands")
public class ArtBrandController {
    private final ArtBrandService service;
    @Autowired
    public ArtBrandController(ArtBrandService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ArtBrand create(@RequestBody ArtBrand brand) {
        return service.create(brand);
    }

    @GetMapping("/read/{brandId}")
    public ArtBrand read(@PathVariable("brandId") Long brandId) {
        return service.read(brandId);
    }

    @PutMapping("/update")
    public ArtBrand update(@RequestBody ArtBrand brand) {
        return service.update(brand);
    }

@DeleteMapping("/delete/{brandId}")
public void delete(@PathVariable Long brandId){
    service.delete(brandId);
}

@GetMapping("/getAll")
public List<ArtBrand> getAll() {
    return service.getAll();
}}
