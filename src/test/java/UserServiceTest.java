import salary.model.entity.User;
import salary.model.entity.enums.Education;
import salary.model.entity.enums.Gender;
import salary.model.entity.enums.Married;
import salary.model.services.UserService;

import java.time.LocalDate;

public class UserServiceTest {
    public static void main(String[] args) throws Exception {
        User user = User.builder()
                .id(4)
                .firstName("donya")
                .lastName("hajizadeh")
                .nationalId("0080386822")
                .education(Education.كارشناسي)
                .married(Married.متاهل)
                .numberOfChildren(2)
                .gender(Gender.زن)
                .birthDate(LocalDate.of(1986,02,02))
                .username("1")
                .password("1")
                .build();
        //UserService.save(user);
        UserService.edit(user);

        //System.out.println(UserService.findAll());

        System.out.println(user);
    }
}
