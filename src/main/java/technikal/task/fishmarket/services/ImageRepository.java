package technikal.task.fishmarket.services;

import org.springframework.data.jpa.repository.JpaRepository;
import technikal.task.fishmarket.models.ImageFile;

public interface ImageRepository extends JpaRepository<ImageFile, Integer> {
}
