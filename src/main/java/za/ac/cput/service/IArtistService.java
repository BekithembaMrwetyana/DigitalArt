package za.ac.cput.service;


import za.ac.cput.domain.Artist;

import java.util.List;

public interface IArtistService extends IService<Artist,Long>{

    List<Artist> getAll();


}
