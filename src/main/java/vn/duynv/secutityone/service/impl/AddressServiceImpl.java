package vn.duynv.secutityone.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.duynv.secutityone.mapper.AddressMapper;
import vn.duynv.secutityone.modal.Address;
import vn.duynv.secutityone.payload.request.CreationAddressDto;
import vn.duynv.secutityone.payload.response.AddressResponse;
import vn.duynv.secutityone.repository.AddressRepository;
import vn.duynv.secutityone.service.AddressService;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Override
    public AddressResponse createNewAddress(CreationAddressDto addressDto) {
        Address address = addressMapper.toEntity(addressDto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        log.info("Creating address for user {}", username);
        return addressMapper.toResponse(addressRepository.save(address));
    }
}
