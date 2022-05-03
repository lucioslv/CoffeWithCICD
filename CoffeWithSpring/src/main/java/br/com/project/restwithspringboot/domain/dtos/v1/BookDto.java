package br.com.project.restwithspringboot.domain.dtos.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@JsonPropertyOrder({"id", "author", "launch_date", "price", "title"})
public class BookDto extends RepresentationModel<BookDto> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Mapping("id")
    @JsonProperty("id")
    private Long key;

    private String author;

    @JsonProperty("launch_date")
    private Date launchDate;

    private BigDecimal price;

    private String title;

    public BookDto() {
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(Date launchDate) {
        this.launchDate = launchDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDto bookDto = (BookDto) o;
        return Objects.equals(key, bookDto.key) && Objects.equals(author, bookDto.author) && Objects.equals(launchDate, bookDto.launchDate) && Objects.equals(price, bookDto.price) && Objects.equals(title, bookDto.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, author, launchDate, price, title);
    }
}
