package roomescape.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static roomescape.fixture.MemberFixture.DEFAULT_MEMBER;
import static roomescape.fixture.ReservationFixture.DEFAULT_RESERVATION;
import static roomescape.fixture.ReservationTimeFixture.DEFAULT_TIME;
import static roomescape.fixture.ThemeFixture.DEFAULT_THEME;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.domain.Theme;

class JpaReservationRepositoryTest extends DatabaseClearBeforeEachTest {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReservationTimeRepository reservationTimeRepository;
    @Autowired
    private ThemeRepository themeRepository;

    @Override
    public void doAfterClear() {
        reservationTimeRepository.save(DEFAULT_TIME);
        themeRepository.save(DEFAULT_THEME);
    }

    @Test
    @DisplayName("Reservation 을 잘 저장하는지 확인한다.")
    void save() {
        var beforeSave = reservationRepository.findAll();
        Reservation saved = reservationRepository.save(DEFAULT_RESERVATION);
        var afterSave = reservationRepository.findAll();

        Assertions.assertThat(afterSave)
                .containsAll(beforeSave)
                .contains(saved);
    }

    @Test
    @DisplayName("Reservation 을 잘 조회하는지 확인한다.")
    void findAll() {
        List<Reservation> beforeSave = reservationRepository.findAll();
        reservationRepository.save(DEFAULT_RESERVATION);

        List<Reservation> afterSave = reservationRepository.findAll();
        Assertions.assertThat(afterSave.size())
                .isEqualTo(beforeSave.size() + 1);
    }

    @Test
    @DisplayName("Reservation 을 잘 지우는지 확인한다.")
    void delete() {
        List<Reservation> beforeSaveAndDelete = reservationRepository.findAll();
        Reservation saved = reservationRepository.save(DEFAULT_RESERVATION);

        reservationRepository.delete(saved.getId());

        List<Reservation> afterSaveAndDelete = reservationRepository.findAll();

        Assertions.assertThat(beforeSaveAndDelete)
                .containsExactlyElementsOf(afterSaveAndDelete);
    }

    @Test
    @DisplayName("특정 테마에 특정 날짜 특정 시간에 예약 여부를 잘 반환하는지 확인한다.")
    void existsByThemeAndDateAndTime() {
        LocalDate date1 = DEFAULT_RESERVATION.getDate();
        LocalDate date2 = date1.plusDays(1);
        reservationRepository.save(DEFAULT_RESERVATION);

        assertAll(
                () -> Assertions.assertThat(
                                reservationRepository.existsByThemeAndDateAndTime(DEFAULT_THEME, date1, DEFAULT_TIME))
                        .isTrue(),
                () -> Assertions.assertThat(
                                reservationRepository.existsByThemeAndDateAndTime(DEFAULT_THEME, date2, DEFAULT_TIME))
                        .isFalse()
        );
    }

    @Test
    @DisplayName("특정 시간에 예약이 있는지 확인한다.")
    void existsByTime() {
        reservationRepository.save(DEFAULT_RESERVATION);

        assertAll(
                () -> Assertions.assertThat(reservationRepository.existsByTime(DEFAULT_TIME))
                        .isTrue(),
                () -> Assertions.assertThat(
                                reservationRepository.existsByTime(new ReservationTime(2L, LocalTime.of(12, 56))))
                        .isFalse()
        );
    }

    @Test
    @DisplayName("특정 테마에 예약이 있는지 확인한다.")
    void existsByTheme() {
        reservationRepository.save(DEFAULT_RESERVATION);

        assertAll(
                () -> Assertions.assertThat(reservationRepository.existsByTheme(DEFAULT_THEME))
                        .isTrue(),
                () -> Assertions.assertThat(reservationRepository.existsByTheme(new Theme(2L, DEFAULT_THEME)))
                        .isFalse()
        );
    }

    @Test
    @DisplayName("특정 회원의 예약을 잘 조회하는지 확인한다.")
    void findByMemberId() {
        reservationRepository.save(DEFAULT_RESERVATION);

        List<Reservation> reservations = reservationRepository.findByMemberId(DEFAULT_MEMBER.getId());

        Assertions.assertThat(reservations).containsExactly(DEFAULT_RESERVATION);
    }

    @Test
    @DisplayName("특정 회원의 특정 기간 내의 예약을 잘 조회하는지 확인한다.")
    void findByMemberAndThemeBetweenDates() {
        LocalDate startDate = DEFAULT_RESERVATION.getDate();
        LocalDate endDate = startDate.plusDays(1);
        LocalDate notOnPeriodDate = startDate.plusDays(2);
        Reservation notOnPeriodreservation = new Reservation(DEFAULT_MEMBER, notOnPeriodDate,
                DEFAULT_TIME, DEFAULT_THEME);

        reservationRepository.save(DEFAULT_RESERVATION);
        reservationRepository.save(notOnPeriodreservation);

        List<Reservation> reservations = reservationRepository.findByMemberAndThemeBetweenDates(
                DEFAULT_MEMBER.getId(), DEFAULT_THEME.getId(), startDate, endDate);

        Assertions.assertThat(reservations)
                .containsExactly(DEFAULT_RESERVATION);
    }
}
