package za.ac.cput.service;

import za.ac.cput.domain.ArtBrand;
import za.ac.cput.domain.CartItem;

import java.util.List;

public interface IArtBrandService extends IService<ArtBrand,Long>{

    List<ArtBrand> getAll();


}
