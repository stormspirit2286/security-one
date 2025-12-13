package vn.duynv.secutityone.mapper;

import org.mapstruct.Mapper;
import vn.duynv.secutityone.modal.Address;
import vn.duynv.secutityone.payload.request.CreationAddressDto;
import vn.duynv.secutityone.payload.response.AddressResponse;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address toEntity(CreationAddressDto addressDto);

    AddressResponse toResponse(Address address);
}
