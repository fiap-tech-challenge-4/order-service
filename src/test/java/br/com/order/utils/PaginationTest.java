package br.com.order.utils;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class PaginationTest {

  @Test
  void shouldReturnAscWhenSortIsAsc() {
    Sort.Direction direction = Pagination.getSort("ASC");
    assertThat(direction).isEqualTo(Sort.Direction.ASC);
  }

  @Test
  void shouldReturnDescWhenSortIsNotAsc() {
    Sort.Direction direction = Pagination.getSort("desc");
    assertThat(direction).isEqualTo(Sort.Direction.DESC);
  }

  @Test
  void shouldBuildPageRequestWithCorrectValues() {
    PageRequest request = Pagination.getPageRequest(10, 2, "ASC", "name");

    assertThat(request.getPageNumber()).isEqualTo(1);
    assertThat(request.getPageSize()).isEqualTo(10);
    assertThat(Objects.requireNonNull(request.getSort().getOrderFor("name")).getDirection()).isEqualTo(Sort.Direction.ASC);
  }

  @Test
  void shouldCalculateOffsetForPage1() {
    int offset = Pagination.getOffset(1, 10);
    assertThat(offset).isZero();
  }

  @Test
  void shouldCalculateOffsetForPageGreaterThan1() {
    int offset = Pagination.getOffset(3, 10);
    assertThat(offset).isEqualTo(20);
  }

  @Test
  void shouldNotModifyOffsetForPageZero() {
    int offset = Pagination.getOffset(0, 10);
    assertThat(offset).isZero();
  }
}
