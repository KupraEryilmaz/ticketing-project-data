package com.cydeo.mapper;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {//neden bu sinifi olusturduk neden her seyi role service impl icerisinde yapmadik Solid prenciple dan dolayi .conversion methodu buraya tanimlayacagim ne zamn ihtiyacim olursa cagiracagim

     private final ModelMapper modelMapper;

    public RoleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Role convertToEntity(RoleDTO dto){
       return modelMapper.map(dto, Role.class);
    }

    public RoleDTO convertToDto(Role entity){
        return modelMapper.map(entity,RoleDTO.class);
    }
}
