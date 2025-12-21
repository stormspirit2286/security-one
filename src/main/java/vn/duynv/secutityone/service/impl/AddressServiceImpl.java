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
import vn.duynv.secutityone.modal.User;
import vn.duynv.secutityone.modal.UserPrincipal;
import vn.duynv.secutityone.payload.request.CreationAddressDto;
import vn.duynv.secutityone.payload.response.AddressResponse;
import vn.duynv.secutityone.repository.AddressRepository;
import vn.duynv.secutityone.repository.UserRepository;
import vn.duynv.secutityone.service.AddressService;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final UserRepository userRepository;

    @Override
    public AddressResponse createNewAddress(CreationAddressDto addressDto) {
        UserPrincipal userPrincipal = getCurrentUserPrincipal();
        User user = userRepository.getReferenceById(userPrincipal.getUserId());
        Address address = addressMapper.toEntity(addressDto);
        address.setUser(user);

        if (Boolean.TRUE.equals(addressDto.getIsDefault())) {
            address.setIsDefault(true);
        }
        return addressMapper.toResponse(addressRepository.save(address));
    }

    private UserPrincipal getCurrentUserPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserPrincipal) authentication.getPrincipal();
    }
}
