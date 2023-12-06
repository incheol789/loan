package com.example.loan.service;

import com.example.loan.domain.Counsel;
import com.example.loan.dto.CounselDTO.Request;
import com.example.loan.dto.CounselDTO.Response;
import com.example.loan.exception.BaseException;
import com.example.loan.exception.ResultType;
import com.example.loan.repository.CounselRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CounselServiceTest {

	@InjectMocks
	CounselServiceImpl counselService;

	@Mock
	private CounselRepository counselRepository;

	@Spy
	private ModelMapper modelMapper;

	@Test
	void Should_ReturnResponseOfNewCounselEntity_When_RequestCounsel() throws Exception {
		// given
		Counsel entity = Counsel.builder()
				.name("Member Kim")
				.cellPhone("010-1111-2222")
				.email("mail@abc.de")
				.memo("I hope to get a loan")
				.zipCode("123456")
				.address("Somewhere in Gangnam-gu, Seoul")
				.addressDetail("What Apartment No. 101, 1st floor No. 101")
				.build();

		Request request = Request.builder()
				.name("Member Kim")
				.cellPhone("010-1111-2222")
				.email("mail@abc.de")
				.memo("I hope to get a loan")
				.zipCode("123456")
				.address("Somewhere in Gangnam-gu, Seoul")
				.addressDetail("What Apartment No. 101, 1st floor No. 101")
				.build();

		// when
		when(counselRepository.save(any(Counsel.class))).thenReturn(entity);

		Response actual = counselService.create(request);

		// then
		assertThat(actual.getName()).isSameAs(entity.getName());
	}

	@Test
	void Should_ReturnResponseOfExistCounselEntity_When_RequestExistCounselId() throws Exception {
		// given
		Long findId = 1L;

		Counsel entity = Counsel.builder()
				.counselId(1L)
				.build();

		// when
		when(counselRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

		Response actual = counselService.get(findId);

		// then
		assertThat(actual.getCounselId()).isSameAs(findId);

	}

	@Test
		// 없는 아이디 조회를 요청했을 때 실패되는 테스트 케이스
	void Should_ThrowException_When_RequestNotExistCounselId() throws Exception {
		// given
		Long findId = 2L;

		// when
		when(counselRepository.findById(findId)).thenThrow(new BaseException(ResultType.SYSTEM_ERROR));

		// then
		Assertions.assertThrows(BaseException.class, () -> counselService.get(findId));

	}

	@Test
	void Should_ReturnUpdatedResponseOfExistCounselEntity_WhenRequestUpdateExistCounselInfo() throws Exception {
		// given
		Long findId = 1L;

		Counsel entity = Counsel.builder()
				.counselId(1L)
				.name("Member Kim")
				.build();

		Request request = Request.builder()
				.name("Member Kang")
				.build();

		// when
		when(counselRepository.save(any(Counsel.class))).thenReturn(entity);
		when(counselRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

		Response actual = counselService.update(findId, request);

		// then
		assertThat(actual.getCounselId()).isSameAs(findId);
		assertThat(actual.getName()).isSameAs(request.getName());

	}

	@Test
	void Should_DeletedCounselEntity_When_RequestDeleteExistCounselInfo() throws Exception {
		// given
		Long targetId = 1L;

		Counsel entity = Counsel.builder()
				.counselId(1L)
				.build();

		// when
		when(counselRepository.save(any(Counsel.class))).thenReturn(entity);
		when(counselRepository.findById(targetId)).thenReturn(Optional.ofNullable(entity));

		counselService.delete(targetId);

		// then
		assertThat(entity.getIsDeleted()).isSameAs(true);

	}
}