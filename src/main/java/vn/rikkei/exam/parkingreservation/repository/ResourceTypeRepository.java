package vn.rikkei.exam.parkingreservation.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.rikkei.exam.parkingreservation.model.ResourceType;
public interface ResourceTypeRepository extends JpaRepository<ResourceType, String> { }
