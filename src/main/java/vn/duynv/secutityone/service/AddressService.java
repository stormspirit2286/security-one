package vn.duynv.secutityone.service;

import vn.duynv.secutityone.payload.request.CreationAddressDto;
import vn.duynv.secutityone.payload.response.AddressResponse;

public interface AddressService {
    AddressResponse createNewAddress(CreationAddressDto addressDto);
}
