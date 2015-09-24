package br.com.cliente.vo;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Fabio
 */
@Entity
@Table(name="CLIENTE")

public class ClientesVO{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
     int id;
    
     @Column(name = "nome", length = 150)
     String nome;
     @Column(name = "email", length = 100)
     String email;
     @Column(name = "site", length = 50)
     String site;
     @Column(name = "fone", length = 20)
     String fone;
     @Column(name = "cep", length = 10)
     String cep;

    public ClientesVO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

     public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ClientesVO other = (ClientesVO) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ClientesVO{" + "id=" + id + ", nome=" + nome + ", email=" + email + ", site=" + site + ", fone=" + fone + '}';
    }

   
 
     
 
   
   
  
    
}
