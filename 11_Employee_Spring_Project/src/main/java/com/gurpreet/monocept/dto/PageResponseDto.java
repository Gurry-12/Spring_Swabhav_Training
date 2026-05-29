package com.gurpreet.monocept.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDto<T> {

	private List<T> content;
	private int pageNumber;
	private int pageSize;
	private Long totalCount;
	private int totalPages;
}
