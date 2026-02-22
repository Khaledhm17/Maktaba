package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Book

/**
 * Repository for managing book data
 * This follows the Repository pattern to abstract data sources
 */
class BookRepositoryImpl : BookRepository  {


    private val booksList = listOf(
        Book(isbn = "978-0-13-235088-4", title = "Clean Code", nbPages = 464),
        Book(isbn = "978-0-13-595705-9", title = "The Pragmatic Programmer", nbPages = 352),
        Book(isbn = "978-0-20-163361-0", title = "Design Patterns", nbPages = 416),
        Book(isbn = "978-0-13-475759-9", title = "Refactoring", nbPages = 448),
        Book(isbn = "978-1-49-207800-5", title = "Head First Design Patterns", nbPages = 672),
        Book(isbn = "978-0-26-204630-5", title = "Introduction to Algorithms", nbPages = 1312),
        Book(isbn = "978-0-20-191465-4", title = "Hacker's Delight", nbPages = 494),
        Book(isbn = "978-0-13-434001-2", title = "How to Solve It by Computer", nbPages = 442),
        Book(isbn = "978-0-19-974044-1", title = "Algorithmic Puzzles", nbPages = 296),
    )


    override fun getAllBooks(): List<Book> {
        return booksList
    }


    override fun getBookByIsbn(isbn: String): Book? {
        return booksList.find { it.isbn == isbn }
    }


    fun searchBooksByTitle(query: String): List<Book> {
        return booksList.filter { book ->
            book.title.contains(query, ignoreCase = true)
        }
    }

}
