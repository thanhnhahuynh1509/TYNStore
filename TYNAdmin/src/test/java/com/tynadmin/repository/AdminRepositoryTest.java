package com.tynadmin.repository;

import com.tynadmin.service.AdminService;
import com.tynentity.Admin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class AdminRepositoryTest {

    @Autowired
    private AdminRepository adminRepository;

    @Test
    public void testSaveAdmin() {
        Admin admin = new Admin();
        admin.setEmail("thieny@gmail.com");
        admin.setPassword("123456789");
        admin.setFirstName("Y");
        admin.setLastName("Thien");
        admin.setEnabled(false);

        admin = adminRepository.save(admin);
        Assertions.assertTrue(admin.getId() > 0);
    }

}
