package com.example.demo.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserUpdateRequest;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private UserEntity sampleUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        sampleUser = new UserEntity();
        sampleUser.setId(1);
        sampleUser.setName("テスト太郎");
        sampleUser.setAddress("東京都江東区青海２丁目７−１");
        sampleUser.setPhone("000-2017-1017");
    }

    /**
    * 対象 ○○
    * 条件 △△
    * 結果 □□
    */
    @Test
    public void testSearchAll() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(sampleUser));

        List<UserEntity> users = userService.searchAll();
        assert(users.size() == 1);
        assert(users.get(0).getName().equals("テスト太郎"));
    }

    @Test
    public void testFindById() {
        when(userRepository.getOne(1)).thenReturn(sampleUser);

        UserEntity user = userService.findById(1);
        assert(user.getName().equals("テスト太郎"));
    }

    @Test
    public void testCreate() {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("テスト一郎");
        userRequest.setAddress("東京都江東区青海２丁目７−１");
        userRequest.setPhone("1234-5678-9012");

        userService.create(userRequest);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void testUpdate() {
        when(userRepository.getOne(1)).thenReturn(sampleUser);

        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setId(1);
        userUpdateRequest.setName("テスト二郎");
        userUpdateRequest.setAddress("東京都江東区");
        userUpdateRequest.setPhone("0987654321");

        userService.update(userUpdateRequest);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void testDelete() {
        when(userRepository.getOne(1)).thenReturn(sampleUser);

        userService.delete(1);
        verify(userRepository, times(1)).delete(sampleUser);
    }
}
