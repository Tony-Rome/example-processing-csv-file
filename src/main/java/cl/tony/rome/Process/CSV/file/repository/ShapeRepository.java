package cl.tony.rome.Process.CSV.file.repository;

import cl.tony.rome.Process.CSV.file.model.ShapeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShapeRepository extends JpaRepository<ShapeModel, Integer> {
    // TODO: Hay dos formar de escribir queries, con JPQL o con NativeSQL que es deaful false.
    @Query(value = "SELECT json_object_keys(row_to_json(columns)) FROM (SELECT * FROM shape.shape LIMIT 1) AS columns",
        nativeQuery = true)
    public List<String> getHeaders();
}
