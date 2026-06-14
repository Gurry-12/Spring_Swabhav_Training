package com.gurpreet.monocept.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PageResponseDto<T> {
	
	private List<T> content;
	private int pageNumber;
	private int pageSize;
	private Long totalCount;
	private int totalPages;
	
	private boolean lastPage;

//	public PageResponseDto() {
//	}
//
//	public PageResponseDto(List<T> content, int pageNumber, int pageSize, Long totalCount, int totalPages, boolean lastPage) {
//		this.content = content;
//		this.pageNumber = pageNumber;
//		this.pageSize = pageSize;
//		this.totalCount = totalCount;
//		this.totalPages = totalPages;
//		this.lastPage = lastPage;
//	}
//
//	public List<T> getContent() {
//		return content;
//	}
//
//	public int getPageNumber() {
//		return pageNumber;
//	}
//
//	public int getPageSize() {
//		return pageSize;
//	}
//
//	public Long getTotalCount() {
//		return totalCount;
//	}
//
//	public int getTotalPages() {
//		return totalPages;
//	}
//
//	public boolean isLastPage() {
//		return lastPage;
//	}
//
//	public void setContent(List<T> content) {
//		this.content = content;
//	}
//
//	public void setPageNumber(int pageNumber) {
//		this.pageNumber = pageNumber;
//	}
//
//	public void setPageSize(int pageSize) {
//		this.pageSize = pageSize;
//	}
//
//	public void setTotalCount(Long totalCount) {
//		this.totalCount = totalCount;
//	}
//
//	public void setTotalPages(int totalPages) {
//		this.totalPages = totalPages;
//	}
//
//	public void setLastPage(boolean lastPage) {
//		this.lastPage = lastPage;
//	}

	
}
