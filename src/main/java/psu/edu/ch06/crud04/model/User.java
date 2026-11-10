package psu.edu.ch06.crud04.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

// "user" is a reserved word in MySQL, so the table is called "users"
@Table("users")
public record User(
		@Id Integer id,
		String username,
		String password
) {
}
