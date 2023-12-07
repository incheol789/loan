package com.example.loan.service;

import com.example.loan.domain.Application;
import com.example.loan.dto.ApplicationDTO.Request;
import com.example.loan.dto.ApplicationDTO.Response;
import com.example.loan.repository.ApplicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceTest {

	@InjectMocks
	ApplicationServiceImpl applicationService;

	@Mock
	private ApplicationRepository applicationRepository;

	@Spy
	private ModelMapper modelMapper;

	@Test
	void Should_ReturnResponseOfNewApplicationEntity_When_RequestCreateApplication() throws Exception {
		// given
		Application entity = Application.builder()
				.name("Member kim")
				.cellPhone("010-1111-2222")
				.email("mail@abcd.efg")
				.hopeAmount(BigDecimal.valueOf(50000000))
				.build();

		Request request = Request.builder()
				.name("Member kim")
				.cellPhone("010-1111-2222")
				.email("mail@abcd.efg")
				.hopeAmount(BigDecimal.valueOf(50000000))
				.build();

		// when
		when(applicationRepository.save(any(Application.class))).thenReturn(entity);

		Response actual = applicationService.create(request);

		// then
		assertThat(actual.getHopeAmount()).isSameAs(entity.getHopeAmount());
		assertThat(actual.getName()).isSameAs(entity.getName());

	}

	@Test
	void Should_ReturnResponseOfExistApplicationEntity_When_RequestExistApplicationId() throws Exception {
		// given
		Long findId = 1L;

		Application entity = Application.builder()
				.applicationId(1L)
				.build();

		// when
		when(applicationRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

		Response actual = applicationService.get(findId);

		// then
		assertThat(actual.getApplicationId()).isSameAs(findId);

	}

	@Test
	void Should_ReturnUpdatedResponseOfExistApplicationEntity_When_RequestUpdatedExistApplicationInfo() throws Exception {
		// given
		Long findId = 1L;

		Application entity = Application.builder()
				.applicationId(1L)
				.hopeAmount(BigDecimal.valueOf(50000000))
				.build();

		Request request = Request.builder()
				.hopeAmount(BigDecimal.valueOf(5000000))
				.build();

		// when
		when(applicationRepository.save(any(Application.class))).thenReturn(entity);
		when(applicationRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

		Response actual = applicationService.update(findId, request);

		// then
		assertThat(actual.getApplicationId()).isSameAs(findId);
		assertThat(actual.getHopeAmount()).isSameAs(request.getHopeAmount());

	}

	@Test
	void Should_DeletedApplicationEntity_When_RequestDeleteExistApplicationInfo() throws Exception {
		// given
		Long targetId = 1L;

		Application entity = Application.builder()
				.applicationId(1L)
				.build();

		// when
		when(applicationRepository.save(any(Application.class))).thenReturn(entity);
		when(applicationRepository.findById(targetId)).thenReturn(Optional.ofNullable(entity));

		applicationService.delete(targetId);

		// then
		assertThat(entity.getIsDeleted()).isSameAs(true);

	}

}