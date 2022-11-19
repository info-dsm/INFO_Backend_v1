package com.info.info_v1_backend.domain.auth.data.entity.user

import com.info.info_v1_backend.domain.auth.data.entity.type.Role
import com.info.info_v1_backend.global.base.entity.BaseTimeEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Where(clause = "user_is_delete = false")
@SQLDelete(sql = "UPDATE `user` SET user_is_delete = true where id = ?")
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type")
@Entity
abstract class User(
    name: String,
    email: String,
    password: String,
    role: Role
): BaseTimeEntity(), UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    var name: String = name
        protected set

    var email: String = email
        protected set

    private var password: String = password

    override fun getPassword(): String {
        return this.password
    }

    @ElementCollection(fetch = FetchType.EAGER)
    var roleList: MutableList<Role> = ArrayList()
        protected set

    @Column(name = "user_is_delete")
    var isDeleted: Boolean = false
        protected set

    init {
        this.roleList.add(role)
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        var authorityList: MutableList<GrantedAuthority> = ArrayList()
        this.roleList.map{
            authorityList.add(SimpleGrantedAuthority(it.toString()))
        }
        return authorityList
    }

    override fun getUsername(): String {
        return this.id.toString()
    }

    override fun isAccountNonLocked(): Boolean {
        return this.roleList.contains(Role.BLOCK)
    }

    override fun isAccountNonExpired(): Boolean {
        return !this.isDeleted
    }

    override fun isCredentialsNonExpired(): Boolean {
        return false
    }

    override fun isEnabled(): Boolean {
        return !this.isDeleted
    }

    fun editPassword(password: String){
        this.password = password
    }
    fun changeEmail(email: String){
        email.let {
            this.email = it
        }
    }

}
