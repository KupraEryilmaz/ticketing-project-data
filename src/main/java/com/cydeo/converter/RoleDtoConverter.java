package com.cydeo.converter;

import com.cydeo.dto.RoleDTO;
import com.cydeo.service.RoleService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class RoleDtoConverter implements Converter<String, RoleDTO> {

    RoleService roleService;

    public RoleDtoConverter(@Lazy RoleService roleService) {//Diyoruz ki yani RoleService bean i RoleDto ya injecte etme henuz.Cunku daha hazir degil.Ne zaman RoleDTOConverter a ihtiyac duyarsan o zaman injecte et diyoruz.
        this.roleService = roleService;
    }

    @Override
    public RoleDTO convert(String source) {

        if (source == null || source.equals("")) {  //  Select  -> ""
            return null;
        }

        return roleService.findById(Long.parseLong(source));

    }

    //uygulamami calistirdim.butun beans ler spring boot tarafindan yurutulecek.Roledto converter->roleServiceImpl->roleRepository-------->spring.datasource. Bu siniflarin sira ile bu dependencylere ihtiyaclari var.pom.xml e indirdigimiz bazi dependencyler springin bize ihtiyac duymadan otomatik olarak obje olusturup yonetmesine olanak tanir.Yukaridaki dongunun en sonunda olan spring.datasource.... spring bunun icin bean olusturmaya calisiyor.Fakat bu bean Roledtoconverter bean e ihtiyac duyuyor.onlardan biri hazir degil.Fakat digeri hazir omayani injecte etmeye calisiyor.(spring.datasource hazir olmayan RoleDtoConverter e injecte etmeye calisiyor.Fakat RoleDto henuz hazir degil o da diger beanlere ihtiyac duyuyor.)Buda siralama hatasi almamiza neden oluyor.(Bir bean olusturulmadan diger bir bean o olusturulmayan bean i istiyor.Bizde @Lazy annotation u koyarak bu injection dongusune baslama diyoruzCunku RoleDTOConverter daha calistirilmadi.Uygulama calisir calismaz RoleDtoConverter kullanilmiyor.Ne zaman kullanmaya ihtiyac duyuyoruz.Save butonuna basip roleDTO yu stringten RoleDTO ya cevirecegimiz zaman.

}
