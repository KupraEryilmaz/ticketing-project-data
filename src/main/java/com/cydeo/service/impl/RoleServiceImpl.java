package com.cydeo.service.impl;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.mapper.RoleMapper;
import com.cydeo.repository.RoleRepository;
import com.cydeo.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final MapperUtil mapperUtil;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper, MapperUtil mapperUtil) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<RoleDTO> listAllRoles() {

        //Controller called me and requesting all role DTO So it can show in the drop-down in the ui
        // I need to make a call DB and get all the roles from table
        //Go to repository find a service which gives me the roles from db
        //how I will call any service here?List<Role> roleList = roleRepository.findAll();

        List<Role> roleList = roleRepository.findAll();

       //peki mappers e sahip olmazsak onun islevini baska hangi yontemle yapabilirdik.Yeni bir RoleDTO olustururduk.tek tek getRole setRoleDTO yapardik.Ama cok fazla fielde sahip oldugumuzda bu zaman oldurucu.tek tek ugrasamayiz.Bunun icin third partie olan mappers library sine ihtiyacim var bu spring tarafindan bana saglanmiyor.Pom.xml e ekleyecegim.Bu islemi yapan bir cok library var.Bunlardan biri modelmapper library si cogunlukla bu library kullaniliyor.Fakat ise girdigimizde farkli librarylerlede karsilasabiliri.Ama isleyis ayni.Peki bu library i nasil kullanacagiz.pom.xml depandeny i indirdigimize gore mapper bir sinif ve objeye sahip o objede bir methode sahip

        //I have Role entities from DB
        //I need to convert those Role entities to DTOs
        //I need to use Modelmapper.
        //I already created a class called RoleMapper and there are methods for me that will make this conversion
//        return roleList.stream().map(roleMapper::convertToDto).collect(Collectors.toList());
        return roleList.stream().map(role -> mapperUtil.convert(role,new RoleDTO())).collect(Collectors.toList());
//        return roleList.stream().map(role -> mapperUtil.convert(role,RoleDTO.class)).collect(Collectors.toList());
        //return roleRepository.findAll().stream().map(roleMapper::convertToDto).collect(Collectors.toList());



    }

    @Override
    public RoleDTO findById(Long id) {
        return roleMapper.convertToDto(roleRepository.findById(id).get());
    }
}
