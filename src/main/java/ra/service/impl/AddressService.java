package ra.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ra.model.dto.request.AddressRegister;
import ra.model.entity.Address;
import ra.model.entity.User;
import ra.repository.AddressRepository;
import ra.security.principals.CustomUserDetail;
import ra.service.UserService;

import java.util.List;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    UserService userService;

    public Address findById(Long id) throws Exception
    {
        return addressRepository.findById(id).orElseThrow(() -> new Exception("Địa chỉ không tồn tại"));
    }

    public List<Address> findByUser() {
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserByUserName(userDetails.getUsername());
       return addressRepository.findByUser(user);
    }

    public Boolean deleteById(Long id)   {
        addressRepository.deleteById(id);
        return true;
    }

    public Address add(AddressRegister addressRegister)   {
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserByUserName(userDetails.getUsername());
        Address address = Address.builder()
                .addressId(null)
                .fullAddress(addressRegister.getFullAddress())
                .receiveName(addressRegister.getReceiveName())
                .phone(addressRegister.getPhone())
                .user(user)
                .build();
        return addressRepository.save(address);
    }



}
