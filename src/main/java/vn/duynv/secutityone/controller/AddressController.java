package vn.duynv.secutityone.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.duynv.secutityone.payload.request.CreationAddressDto;
import vn.duynv.secutityone.payload.response.AddressResponse;
import vn.duynv.secutityone.payload.response.ApiResponse;
import vn.duynv.secutityone.service.AddressService;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @PostMapping()
    public ResponseEntity<ApiResponse<AddressResponse>> createNewAddress(@Valid @RequestBody CreationAddressDto addressDto) {
        AddressResponse response = addressService.createNewAddress(addressDto);
        ApiResponse<AddressResponse> result = ApiResponse.success("Create address successfully", response);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
