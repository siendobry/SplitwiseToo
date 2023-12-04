package pl.edu.agh.utp.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import pl.edu.agh.utp.model.nodes.Category;

public interface CategoryRepository extends Neo4jRepository<Category, Long> {}
