package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findAllByIsDeletedOrderByFirstNameDesc(Boolean deleted);

    //get user based on username
    User findByUserNameAndIsDeleted(String username,Boolean deleted);
    @Transactional//bir seyleri silmaye calisiyoruz birseyler yanlis giderse ne olacak.Diyelim ki userinterfacede sildik ama data basede silinmedi ne olacak.bu nedenle genellikle delete update islemlerinde bu annotation u kullaniyyoruz..istersek sinaf levele(userservice impl class i uzerine koyacagiz) da koyabiliriz .Siniftaki her method buna tabi olur.
    void deleteByUserName(String username);
    List<User> findByRoleDescriptionIgnoreCaseAndIsDeleted(String description, Boolean deleted);


    //where annotation kullanmak aslinda riskli cunku bu annotation u kullandigimiz zaman silinen entity e ihtiyac duydugumuz herhangi bir seneryo olmamali.Fakat bizde durum farkli biz hala o entity e ihtiyac duydugumuz seneryolara sahibiz.Biz employee ve manager i siliyoruz.Fakat task ve project hala onlara ihtiyac duyuyor.Biz soft delete yapiyoruz.UI da goremesek dahi table da sahibiz.
}
