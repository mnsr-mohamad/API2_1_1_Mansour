package mvp.model;

import java.util.List;

public interface DAO<T> {

    T add(T t);

    boolean remove(T t);

    T update(T t);

    T read(int rech);

    List<T> getAll();
}
