package com.hatchways.blog.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
@Table(
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["_username"]),
        UniqueConstraint(columnNames = ["_password"])
    ]
)
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", initialValue = 6)
    var id: Long? = null,

    private var _username: String? = null,

    var _password: String? = null,

    @ManyToMany(mappedBy = "users")
    var posts: Set<Post>? = null
) : UserDetails {
    constructor(username: String, password: String) : this() {
        this._username = username
        this._password = password
    }

    override fun getUsername(): String? {
        return _username
    }

    override fun getPassword(): String? {
        return _password
    }

    override fun getAuthorities(): Collection<out GrantedAuthority> {
        return emptyList() // Return user's authorities here
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
