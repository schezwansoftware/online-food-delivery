package com.codesetters.account.service;

import com.codesetters.account.AbstractCassandraTest;
import com.codesetters.account.AccountApp;
import com.codesetters.account.config.Constants;
import com.codesetters.account.domain.User;
import com.codesetters.account.repository.UserRepository;
import com.codesetters.account.service.dto.UserDTO;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Test class for the UserResource REST controller.
 *
 * @see UserService
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountApp.class)
public class UserServiceIntTest extends AbstractCassandraTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private User user;

    @Before
    public void init() {
        userRepository.deleteAll();
        user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setLogin("johndoe");
        user.setPassword(RandomStringUtils.random(60));
        user.setActivated(true);
        user.setEmail("johndoe@localhost");
        user.setFirstName("john");
        user.setLastName("doe");
        user.setLangKey("en");
    }

    @Test
    public void assertThatAnonymousUserIsNotGet() {
        user.setLogin(Constants.ANONYMOUS_USER);
        if (!userRepository.findOneByLogin(Constants.ANONYMOUS_USER).isPresent()) {
            userRepository.save(user);
        }
        final List<UserDTO> allManagedUsers = userService.getAllManagedUsers();
        assertThat(allManagedUsers.stream()
            .noneMatch(user -> Constants.ANONYMOUS_USER.equals(user.getLogin())))
            .isTrue();
    }


}
