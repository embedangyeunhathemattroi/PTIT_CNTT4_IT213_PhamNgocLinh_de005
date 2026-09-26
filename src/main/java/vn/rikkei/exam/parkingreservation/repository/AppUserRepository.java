package vn.rikkei.exam.parkingreservation.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.rikkei.exam.parkingreservation.model.AppUser;
public interface AppUserRepository extends JpaRepository<AppUser, String> { }
