package com.tafh.ecommerce.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagingResponse {

  private int currentPage;
  private int pageSize;
  private int totalPages;

}
