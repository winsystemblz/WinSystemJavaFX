package br.com.cliente.Dao;


import br.com.cliente.Util.HibernateControl;
import br.com.cliente.Util.HibernateUtil;
import br.com.cliente.vo.ClientesVO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Fabio
 */
public class ClienteDao {
    
     private SessionFactory conexao;
     private Session session;
     
     public ClienteDao(){
         conexao = new Configuration().configure().buildSessionFactory();
     }
     
     public void InserirCliente(ClientesVO cli){   
        session = conexao.openSession();
        Transaction tx = session.beginTransaction();
        session.save(cli);
        tx.commit();
        session.close();
     }
     
     public void AlterarCliente(ClientesVO cli){   
        session = conexao.openSession();
        Transaction tx = session.beginTransaction();
        session.update(cli);
        tx.commit();
        session.close();
     }
     
     public void ExcluirCliente(ClientesVO cli){      
        session = conexao.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(cli);
        tx.commit();
        session.close();
     }
     
   
     
     
     public void ListarTodos(){
       Session session = null;
       Transaction tx = null;  
        try {
            SessionFactory factory = new Configuration().configure().buildSessionFactory();
            session = factory.openSession();
            
            Query query = session.createQuery("from ClientesVO");
            List listaCliente = query.list();
            Iterator it = listaCliente.iterator();
            
            while(it.hasNext()){
                ClientesVO cli = (ClientesVO)it.next();
                System.err.println("Nome: "+cli.getNome()+" Fone: "+cli.getFone());
            }
        } catch (Exception e) {
            if(tx != null)
                tx.rollback();
            System.err.println("Erro: "+e.getMessage());
        }
       finally{
            session.close();
        }
       
       
     }
    
     public void BuscarPorCodigo(int id){
         
       Session session = null;
       Transaction tx = null;
         try {
             Query query = session.createQuery("ClientesVO.BuscarPorCodigo");
             
         } catch (Exception e) {
         }
         
     }
    
     public List<ClientesVO> listar(){
         Session sessao = HibernateUtil.getSessionFactory().openSession();
         List<ClientesVO> cli = null;
         
         try {
             Query consulta = sessao.getNamedQuery("Clien.findAll");
             cli = consulta.list();
         } catch (RuntimeException ex) {
             throw ex;
         }finally{
             sessao.close();
         }
         return cli;
     }
    
       
    public List<ClientesVO> obterLista(String filtro) {
//        if(filtro == null || filtro.isEmpty()){
//            return obterLista();
//        }        
        List<ClientesVO> objetos = new ArrayList<ClientesVO>();
        session = HibernateUtil.getSessionFactory().openSession();//TransactionManager.getCurrentSession();   
        
        objetos = session.createQuery("from ClientesVO").list();
        return objetos;
    }
}
