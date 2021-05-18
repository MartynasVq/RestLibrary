package com.martynasvq.restlibrary.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public class BookDto {
    private String name;
    private String author;
    private String category;
    private String language;
    private LocalDate publicationDate;
    private String isbn;
    private UUID id;
    private boolean available = true;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String borrowedBy = null;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate borrowedUntil = null;


    public BookDto(String name, String author, String category, String language, LocalDate publicationDate, String isbn, UUID uuid) {
        this.name = name;
        this.author = author;
        this.category = category;
        this.language = language;
        this.publicationDate = publicationDate;
        this.isbn = isbn;
        this.id = uuid;
    }

    public BookDto(String name, String author, String category, String language, LocalDate publicationDate, String isbn, UUID uuid, boolean available,
                   String borrowedBy, LocalDate borrowedUntil) {
        this.name = name;
        this.author = author;
        this.category = category;
        this.language = language;
        this.publicationDate = publicationDate;
        this.isbn = isbn;
        this.id = uuid;
        if(borrowedBy == null)
            this.available = true;
        else
            this.available = false;
        this.borrowedBy = borrowedBy;
        this.borrowedUntil = borrowedUntil;
    }

    public BookDto(){}

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof BookDto)) return false;
        final BookDto other = (BookDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$author = this.getAuthor();
        final Object other$author = other.getAuthor();
        if (this$author == null ? other$author != null : !this$author.equals(other$author)) return false;
        final Object this$category = this.getCategory();
        final Object other$category = other.getCategory();
        if (this$category == null ? other$category != null : !this$category.equals(other$category)) return false;
        final Object this$language = this.getLanguage();
        final Object other$language = other.getLanguage();
        if (this$language == null ? other$language != null : !this$language.equals(other$language)) return false;
        final Object this$publicationDate = this.getPublicationDate();
        final Object other$publicationDate = other.getPublicationDate();
        if (this$publicationDate == null ? other$publicationDate != null : !this$publicationDate.equals(other$publicationDate))
            return false;
        final Object this$isbn = this.getIsbn();
        final Object other$isbn = other.getIsbn();
        if (this$isbn == null ? other$isbn != null : !this$isbn.equals(other$isbn)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        if (this.isAvailable() != other.isAvailable()) return false;
        final Object this$borrowedBy = this.getBorrowedBy();
        final Object other$borrowedBy = other.getBorrowedBy();
        if (this$borrowedBy == null ? other$borrowedBy != null : !this$borrowedBy.equals(other$borrowedBy))
            return false;
        final Object this$borrowedUntil = this.getBorrowedUntil();
        final Object other$borrowedUntil = other.getBorrowedUntil();
        if (this$borrowedUntil == null ? other$borrowedUntil != null : !this$borrowedUntil.equals(other$borrowedUntil))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BookDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $author = this.getAuthor();
        result = result * PRIME + ($author == null ? 43 : $author.hashCode());
        final Object $category = this.getCategory();
        result = result * PRIME + ($category == null ? 43 : $category.hashCode());
        final Object $language = this.getLanguage();
        result = result * PRIME + ($language == null ? 43 : $language.hashCode());
        final Object $publicationDate = this.getPublicationDate();
        result = result * PRIME + ($publicationDate == null ? 43 : $publicationDate.hashCode());
        final Object $isbn = this.getIsbn();
        result = result * PRIME + ($isbn == null ? 43 : $isbn.hashCode());
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        result = result * PRIME + (this.isAvailable() ? 79 : 97);
        final Object $borrowedBy = this.getBorrowedBy();
        result = result * PRIME + ($borrowedBy == null ? 43 : $borrowedBy.hashCode());
        final Object $borrowedUntil = this.getBorrowedUntil();
        result = result * PRIME + ($borrowedUntil == null ? 43 : $borrowedUntil.hashCode());
        return result;
    }

    public String toString() {
        return "BookDto(name=" + this.getName() + ", author=" + this.getAuthor() + ", category=" + this.getCategory() + ", language=" + this.getLanguage() + ", publicationDate=" + this.getPublicationDate() + ", isbn=" + this.getIsbn() + ", id=" + this.getId() + ", available=" + this.isAvailable() + ", borrowedBy=" + this.getBorrowedBy() + ", borrowedUntil=" + this.getBorrowedUntil() + ")";
    }

    public String getName() {
        return this.name;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getCategory() {
        return this.category;
    }

    public String getLanguage() {
        return this.language;
    }

    public LocalDate getPublicationDate() {
        return this.publicationDate;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public UUID getId() {
        return this.id;
    }

    public boolean isAvailable() {
        return this.available;
    }

    public String getBorrowedBy() {
        return this.borrowedBy;
    }

    public LocalDate getBorrowedUntil() {
        return this.borrowedUntil;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setBorrowedBy(String borrowedBy) {
        this.borrowedBy = borrowedBy;
    }

    public void setBorrowedUntil(LocalDate borrowedUntil) {
        this.borrowedUntil = borrowedUntil;
    }
}
