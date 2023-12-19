package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProjectService projectService;
    private final TaskService taskService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, @Lazy ProjectService projectService, @Lazy TaskService taskService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @Override
    public List<UserDTO> listAllUsers() {
        List<User> userList = userRepository.findAllByIsDeletedOrderByFirstNameDesc(false);

        return userList.stream().map(userMapper::convertToDto).collect(Collectors.toList());

    }

    @Override
    public UserDTO findByUserName(String username) {
        return userMapper.convertToDto(userRepository.findByUserNameAndIsDeleted(username,false));
    }

    @Override
    public void save(UserDTO user) {
        userRepository.save(userMapper.convertToEntity(user));
    }

    @Override
    public void deleteByUserName(String username) {

   // userRepository.delete(userMapper.convertToEntity(findByUserName(username))); daha sonra bak.Neden calismadi?
        userRepository.deleteByUserName(username);

    }

    //    @Override
//    public UserDTO update(UserDTO dto) {
//
//        User user1 = userMapper.convertToEntity(dto);
//        User user = userRepository.save(user1);
//        return findByUserName(user.getUserName());
//    }
//    update methodunu yukaridaki sekilde olusturdugumuz zaman iki hataya sahip oluyoruz.Birincisi update edilen user in bilgisi UI dan UserDTO olarak geliyor. UserDTO ise id ye sahip degil.Cunku UI da user i guncelledigimiz form da id yi guncelleyecegimiz kutucuga sahip degiliz. id kutucugu olmadigi icinde user in id si null degerini aliyor.Biz UserDTO yu database e kaydetmek istedigimiz zaman UserDTO id ye sahip olmadigi icin spring UserDTO ya yeni bir id atiyor.Bunun sonucunda mevcut UserDTO guncellenmiyor.Yeni bir UserDTO olusturuluyor.Ayni username e sahip farkli iki obje oluyor.Fakat username in unique deger olmasi gerekiyor.Cunku biz UI kismindan intelije ye  UserDTO yu username e gore aliyoruz.Yukarida ki methodda findByUserName(user.getUserName()) methodunu kullandigimiz icin query unique degeri getiremedi diye hata veriyor.Cunku ayni username sahip iki farkli data var.Fakat findByUserName(user.getUserName()) bu method sonucunda bize yalnizca bir data donmeli.Aksi taktirde program burada oldugu gibi hata verir.Cunku spring hangisini getirecegini bilemez.Bu problemlerin onune gecmek icin formulu asagidaki sekilde guncelliyoruz.

    @Override
    public UserDTO update(UserDTO user) {

//        Find current user
        User user1 = userRepository.findByUserNameAndIsDeleted(user.getUserName(),false); //has id. Yes
        //Mapper update user dto to entity object
        User convertedUser = userMapper.convertToEntity(user); //has id? No
        //set id to do converted object
        convertedUser.setId(user1.getId());
        //save the updated user in the db
        userRepository.save(convertedUser);
        return findByUserName(user.getUserName());

    }



    @Override
    public void delete(String username) {
        //go to db and get that user with username
        User user = userRepository.findByUserNameAndIsDeleted(username,false);
        //change the is deleted field to true

        if(checkIfUserCanBeDeleted(user)){
            user.setIsDeleted(true);
            user.setUserName(user.getUserName()+"-"+ user.getId());//harold@manager.com-2
            //save the object in the db
            userRepository.save(user);
        }

    }

    @Override
    public List<UserDTO> listAllByRole(String role) {

        List<User> users = userRepository.findByRoleDescriptionIgnoreCaseAndIsDeleted(role,false);
        return users.stream().map(userMapper::convertToDto).collect(Collectors.toList());
    }

    private boolean checkIfUserCanBeDeleted(User user){//method private oldugu icin herhangi bir controllerden veri alis verisi olmayacak o nedenle DTO degilde Entity i parametre olarak kullandik.DTO kullanma zorunlulugumuz ortadan kalkiyor.Fakat istersek DTO da kullanabiliriz.Diger methodlarda yaptigimiz gibi method icerisinde DTO yu Entity e cevirirz.

        switch (user.getRole().getDescription()){
            case "Manager":
                List<ProjectDTO> projectDTOList = projectService.listAllNonCompletedByAssignedManager(userMapper.convertToDto(user));
                return projectDTOList.size()==0;
            case "Employee":
                List<TaskDTO> taskDTOList = taskService.listAllNonCompletedByAssignedEmployee(userMapper.convertToDto(user));
                return taskDTOList.size()==0;
            default:
                return true;
        }



    }
}
