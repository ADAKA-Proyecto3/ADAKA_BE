package com.cenfotec.adaka.app.service;

import com.cenfotec.adaka.app.domain.MedicalCenter;
import com.cenfotec.adaka.app.domain.Role;
import com.cenfotec.adaka.app.domain.Status;
import com.cenfotec.adaka.app.domain.User;
import com.cenfotec.adaka.app.repository.MedicalCenterRepository;
import com.cenfotec.adaka.app.repository.UserRepository;
import com.cenfotec.adaka.app.service.impl.MedicalCenterImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicalCenterServiceTest {

    @Mock
    private MedicalCenterRepository medicalCenterRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private MedicalCenterImpl medicalCenterImp;

    private User user;
    private MedicalCenter medicalCenter;
    @BeforeEach
    public void Init(){
        user = User.builder().name("UserName")
                .role(Role.valueOf("ADMIN"))
                .status(Status.valueOf("ACTIVE"))
                .phone("88888888")
                .email("email@gmail.com")
                .password("password123").build();

        medicalCenter = MedicalCenter.builder()
                .email("email@gmail.com")
                .name("Centro Medico")
                .longitude("9°51'43")
                .latitude("83°55'14")
                .status(Status.valueOf("ACTIVE"))
                .direction("Cartago Centro")
                .user(user).build();
    }

    @Test
    public void MedicalCenterService_CreateMedicalCenter_ReturnMedicalCenter(){
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(medicalCenterRepository.save(Mockito.any(MedicalCenter.class))).thenReturn(medicalCenter);

        MedicalCenter savedMedicalCenter = medicalCenterImp.saveMedicalCenter(medicalCenter, user.getId());

        Assertions.assertThat(savedMedicalCenter).isNotNull();

    }
    @Test
    public void MedicalCenterService_GetAllMedicalCentersByUserId_ReturnMedicalCenterList(){
        int medicalId = 1;
        when(medicalCenterRepository.findByUserId(medicalId)).thenReturn(Arrays.asList(medicalCenter));

        // Llama al método que deseas probar
        List<MedicalCenter> medicalCenterList = medicalCenterImp.getAllMedicalCentersByUserId(medicalId);

        // Verifica que la lista devuelta no sea nula y contenga al menos un centro médico
        Assertions.assertThat(medicalCenterList).isNotNull();
        Assertions.assertThat(medicalCenterList).isNotEmpty();
    }

    @Test
    public void MedicalCenterService_GetMedicalCenterById_ReturnMedicalCenter(){
        int medicalId = 1;

        when(medicalCenterRepository.findById(medicalId)).thenReturn(Optional.of(medicalCenter));
        // Llama al método que deseas probar
        MedicalCenter medicalCenter = medicalCenterImp.getMedicalCenterById(medicalId);

        // Verifica que la lista devuelta no sea nula y contenga al menos un centro médico
        Assertions.assertThat(medicalCenter).isNotNull();
    }

    @Test
    public void MedicalCenterService_updateMedicalCenter_ReturnMedicalCenter(){
        int medicalId = 1;
        when(medicalCenterRepository.findById(medicalId)).thenReturn(Optional.of(medicalCenter));
        when(medicalCenterRepository.save(medicalCenter)).thenReturn(medicalCenter);

        MedicalCenter newMedicalCenter = medicalCenterImp.updateMedicalCenter(medicalId,medicalCenter);

        Assertions.assertThat(newMedicalCenter).isNotNull();
    }

    @Test
    public void MedicalCenterService_updateMedicalStatus_ReturnMedicalCenter(){
        int medicalId = 1;
        when(medicalCenterRepository.findById(medicalId)).thenReturn(Optional.of(medicalCenter));
        when(medicalCenterRepository.save(medicalCenter)).thenReturn(medicalCenter);

        MedicalCenter newMedicalCenter = medicalCenterImp.updateMedicalStatus(medicalId,"ACTIVE");

        Assertions.assertThat(newMedicalCenter).isNotNull();
    }
    @Test
    public void MedicalCenterService_deleteMedicalCenter_ReturnVoid(){
        int medicalId = 1;
        when(medicalCenterRepository.findById(medicalId)).thenReturn(Optional.of(medicalCenter));

        assertAll(()-> medicalCenterImp.deleteMedicalCenter(medicalId));
    }

}
