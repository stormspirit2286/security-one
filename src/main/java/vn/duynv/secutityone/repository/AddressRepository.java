package vn.duynv.secutityone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.duynv.secutityone.modal.Address;
import vn.duynv.secutityone.payload.response.AddressResponse;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<AddressResponse> findByUserId(Long userId);
}
