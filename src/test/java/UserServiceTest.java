import salary.controller.AppState;
import salary.model.entity.User;
import salary.model.entity.enums.Education;
import salary.model.entity.enums.Gender;
import salary.model.entity.enums.Married;
import salary.model.services.UserService;

import java.time.LocalDate;

public class UserServiceTest {
    public static void main(String[] args) throws Exception {
        User user = User.builder()
                .id(1)
                .firstName("narges")
                .lastName("hajizadeh")
                .nationalId("0")
                .education(Education.كارشناسي)
                .married(Married.متاهل)
                .numberOfChildren(2)
                .gender(Gender.زن)
                .birthDate(LocalDate.of(1986,02,02))
                .username("1")
                .password("1")
                .build();
        //UserService.save(user);
//        UserService.edit(user);
//        AppState.user=UserService.findById(user.getId());

       System.out.println(UserService.findByUserAndPassword("1","1"));
//        System.out.println(AppState.user);

//        System.out.println(AppState.user);
    }
}
