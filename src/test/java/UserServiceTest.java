import salary.controller.AppState;
import salary.model.entity.User;
import salary.model.entity.enums.*;
import salary.model.services.UserService;

import java.time.LocalDate;

public class UserServiceTest {
    public static void main(String[] args) throws Exception {
        User user = User.builder()
                .id(6)
                .firstName("someyeh")
                .lastName("hajizafeh")
                .nationalId("001548963")
                .fatherName("ali")
                .certificateNumber("4540")
                .birthDate(LocalDate.of(1980, 02, 02))
                .birthPlace(City.تهران)
                .gender(Gender.زن)
                .education(Education.ديپلم)
                .major(Major.موسیقی)
                .marriage(Marriage.مجرد)
                .numberOfChildren(0)
                .phoneNumber("09633405279")
                .username("1")
                .password("1")
                .build();
        //UserService.save(user);
        UserService.edit(user);
        AppState.userSelected = UserService.findById(user.getId());

        //System.out.println(UserService.findByUserAndPassword("1","1"));
//        System.out.println(AppState.user);

        System.out.println(AppState.userSelected);
    }
}
