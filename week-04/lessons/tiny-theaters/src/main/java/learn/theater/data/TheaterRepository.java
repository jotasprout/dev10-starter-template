package learn.theater.data;

import learn.theater.models.Theater;

import java.util.List;

public interface TheaterRepository {
    List<Theater> findAll();

    Theater findById(int theaterId);

    Theater add(Theater theater);

    boolean update(Theater theater);

    boolean deleteById(int theaterId);
}
