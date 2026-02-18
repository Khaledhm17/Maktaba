package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Book

/**
 * Repository for managing book data
 * This follows the Repository pattern to abstract data sources
 */
class BookRepository {

    /**
     * TODO for Students (TP1 - Exercise 1):
     * Complete the book information for each book in the list below.
     * Add the following information for each book:
     * - isbn: Use a valid ISBN-13 format (e.g., "978-3-16-148410-0")
     * - nbPages: Add the actual number of pages
     *
     * Example:
     * Book(
     *     isbn = "978-0-13-468599-1",
     *     title = "Clean Code",
     *     nbPages = 464
     * )
     */
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

    /**
     * TODO for Students (TP1 - Exercise 2):
     * Add 5 more books to the list above.
     * Choose books related to Computer Science, Programming, or any topic you like.
     * Remember to include complete information (ISBN, title, nbPages).
     *
     * Tip: You can find ISBN numbers for books on:
     * - Google Books
     * - Amazon
     * - GoodReads
     */

    /**
     * Get all books from the repository
     * @return List of all books
     */
    fun getAllBooks(): List<Book> {
        return booksList
    }

    /**
     * Get a book by ISBN
     * @param isbn The ISBN of the book to find
     * @return The book if found, null otherwise
     */
    fun getBookByIsbn(isbn: String): Book? {
        return booksList.find { it.isbn == isbn }
    }

    /**
     * Search books by title
     * @param query The text to search for in the book title
     * @return List of books whose titles contain the query string
     */
    fun searchBooksByTitle(query: String): List<Book> {
        return booksList.filter { book ->
            book.title.contains(query, ignoreCase = true)
        }
    }

}
