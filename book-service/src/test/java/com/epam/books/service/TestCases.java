package com.epam.books.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.epam.books.dto.BookDTO;
import com.epam.books.entity.Book;
import com.epam.books.repository.BookRepository;
import com.epam.books.utility.BookMapper;

@ExtendWith(MockitoExtension.class)
class TestCases {
    
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private List<Book> bookList;
    private Integer pageNumber = 0;
    private Integer pageSize = 10;

    @BeforeEach
    public void setUp() {
        // Assume you have a Book class
        Book book1 = new Book(); // Initialize with values
        Book book2 = new Book(); // Initialize with values
        bookList = Arrays.asList(book1, book2);

        Page<Book> pageOfBooks = new PageImpl<>(bookList);

        when(bookRepository.findAll(any(Pageable.class))).thenReturn(pageOfBooks);
        when(BookMapper.INSTANCE.convertToBookDTOs(pageOfBooks)).thenReturn(Arrays.asList(new BookDTO(), new BookDTO()));
    }

    @Test
    void testFindAllBooks() {
        // Act
        List<BookDTO> result = bookService.findAllBooks(pageNumber, pageSize);

        // Capture the argument used when calling bookRepository.findAll()
        ArgumentCaptor<Pageable> argumentCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(bookRepository, times(1)).findAll(argumentCaptor.capture());

        // Now we can assert that the used PageRequest matches what we expect
        Pageable usedPageable = argumentCaptor.getValue();
        assertEquals(pageNumber.intValue(), usedPageable.getPageNumber());
        assertEquals(pageSize.intValue(), usedPageable.getPageSize());

        // Assert
        assertEquals(bookList.size(), result.size());
    }
}