package br.com.ControllerFX;


import br.com.cliente.Dao.ClienteDao;
import br.com.cliente.Util.ValidadorCep;
import br.com.cliente.vo.ClientesVO;
import clientes.Clientes;
import static clientes.Clientes.SCENE;
import clientes.TableModel;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JFormattedTextField;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Fabio
 */
public class ClienteControlle implements Initializable{
   
    @FXML
    TextField tf_codigo;
    @FXML
    TextField tf_nome;
    @FXML
    TextField tf_email;
    @FXML
    TextField tf_site ;
    @FXML
    TextField tf_fone;  
    @FXML
    TextField tf_cep;
    @FXML
    TextField tfPesquisar ;
    @FXML
    ComboBox ComboBoxPesquisa;
    @FXML
    Label lbQtd;
    @FXML
    private Button bt_sair;
    @FXML
    private Button bt_excluir;
    @FXML
    private Button bt_salvar;
    @FXML
    private Button bt_novo;
    @FXML
    private Button bt_alterar;   
    @FXML
    private Button bt_maps;
    @FXML
    private Button bt_email;
    @FXML
    private TableColumn tcolummEmail;
    @FXML
    private TableColumn tcolummFone;
    @FXML
    private TableColumn tcolummCodigo;
    @FXML
    private TableColumn tcolummNome;
    @FXML
    private TableColumn tcolummCep;
    @FXML
    private TableView<TableModel> tbViewConsulta; 

    // Criar uma lista
     ObservableList<TableModel> dados = FXCollections.observableArrayList(); 
           
     String tipoCadastro = null;
     String Consulta = "from ClientesVO";
     
     
     ClientesVO cli = new ClientesVO();
     ClienteDao cliDao = new ClienteDao();
     
     private Session session = null;
     private Transaction tx = null;
     SessionFactory factory = new Configuration().configure().buildSessionFactory();
    

     @FXML
     void EnviarEmail(ActionEvent event) throws IOException{
        System.out.println("Enviar Email");
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/cliente/view/Email.fxml"));
      
        Scene scene = new Scene(root);
       
        Node node = (Node) event.getSource();

        
        Stage stage = (Stage) node.getScene().getWindow();
        Stage showDECORATED = new Stage(StageStyle.UTILITY);  
       // scene.getStylesheets().add(Clientes.class.getResource("/css/baju.css").toExternalForm());
        stage.setScene(scene);

       // stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
     }
    
    @FXML
    void Novo(ActionEvent event){

        tipoCadastro = "novo";
        bt_salvar.setDisable(false);
        bt_excluir.setDisable(true);
        bt_novo.setDisable(true);
        bt_alterar.setDisable(true);
       
        tfPesquisar.setDisable(true);
        tf_nome.requestFocus();
        LimparCampos();
        habilitarCampos();    
    }

    @FXML
    void Alterar(ActionEvent event){ 
        tipoCadastro = "alterar";
        bt_salvar.setDisable(false);
        habilitarCampos();
    }
  
    @FXML
    void Salvar(ActionEvent event) {
     
     cli.setNome(tf_nome.getText());
     cli.setEmail(tf_email.getText());
     cli.setSite(tf_site.getText());
     cli.setFone(tf_fone.getText());
     cli.setCep(tf_cep.getText());
 
        if (tipoCadastro.equals("novo")){
            cliDao.InserirCliente(cli);           
           
        }else if(tipoCadastro.equals("alterar")){
            cli.setId(Integer.parseInt(tf_codigo.getText()));
            cliDao.AlterarCliente(cli);     
        }
        LimparCampos();
        DesabilitarCampos();
        bt_salvar.setDisable(true);
        bt_excluir.setDisable(false);
        bt_novo.setDisable(false);
        bt_alterar.setDisable(false);
        tfPesquisar.setDisable(false);
         BuscarTodos();
     
    }
    
    @FXML
    void Excluir(){
        session = factory.openSession();
        int selectedIndex= tbViewConsulta.getSelectionModel().getFocusedIndex();     
   
      if(selectedIndex >=0){       
         TableModel linha = tbViewConsulta.getSelectionModel().getSelectedItem();
         Integer id   = linha.idProperty().getValue();
         tf_codigo.setText(String.valueOf(id));
         Query query = session.createQuery("from cliente in class br.com.cliente.vo.ClientesVO where cliente.id ='" +id+"'"); 
        
         List listaCliente = query.list();      
         Iterator it = listaCliente.iterator();
         ClientesVO cli = (ClientesVO)it.next();
         cliDao.ExcluirCliente(cli);
         
          tbViewConsulta.getItems().remove(selectedIndex);
         BuscarTodos();
      }
     }  
    
    @FXML
    void Sair(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void BuscaMaps(ActionEvent event){
        System.out.println("Buscar CEP");
        String cep = tf_cep.getText();
        
        ValidadorCep validaCep = new ValidadorCep();
        validaCep.verificarCep(cep);
    }
     
     String pesquisarPor = "Nome";
    @FXML
    void FiltrarComboBox(){
       pesquisarPor = (String) ComboBoxPesquisa.getValue();
       //System.out.println("Pesquisar "+pesquisarPor);
      
    }
    
    @FXML
    void Pesquisar(ActionEvent event){
        tbViewConsulta.getItems().clear();
        String pesquisar = tfPesquisar.getText(); 
        pesquisar = pesquisar.replaceAll("[./-]", "");   
       
        if(pesquisar.equals("")){
            Consulta = "from ClientesVO";
        }else{
            if (pesquisarPor.equals("Nome")) {
                System.out.println("Pesquisar ok "+pesquisarPor);
                Consulta = "from cliente in class br.com.cliente.vo.ClientesVO where cliente.nome.email like '%" + pesquisar +"%'";            
              }else if(pesquisarPor.equals("Email")){
                  System.out.println("Pesquisar ok "+pesquisarPor);
                  Consulta = "from cliente in class br.com.cliente.vo.ClientesVO where cliente.email like '%" + pesquisar +"%'";
             }else if(pesquisarPor.equals("Telefone")){
                 System.out.println("Pesquisar ok"+pesquisarPor);
                 Consulta = "from cliente in class br.com.cliente.vo.ClientesVO where cliente.fone like '%" + pesquisar +"%'";
            }
                                                              
        }       
        BuscarClienteID();
    }
    
    @FXML
    private void showClienteVODetalhe(TableModel newValue){
     
        TableModel linha = tbViewConsulta.getSelectionModel().getSelectedItem();
     
        Integer id   = linha.idProperty().getValue();
        String nome  = linha.nomeProperty().getValue();
        String email = linha.emailProperty().getValue();
        String fone  = linha.foneProperty().getValue();
        String site  = linha.siteProperty().getValue();
        String cep   = linha.cepProperty().getValue();
      
        tf_codigo.setText(String.valueOf(id));
        tf_nome.setText(String.valueOf(nome));
        tf_email.setText(email);
        tf_fone.setText("67 "+fone);
        tf_site.setText(site);
        tf_cep.setText(cep);
    }

    @FXML
    void LimparCampos(){
        tf_codigo.setText("Novo");
        tf_nome.setText(null);
        tf_email.setText(null);
        tf_site.setText(null);
        tf_fone.setText(null);
        tf_cep.setText(null);
      
    }
   
    @FXML
    void habilitarCampos(){
        tf_nome.setDisable(false);
        tf_email.setDisable(false);
        tf_site.setDisable(false);
        tf_fone.setDisable(false);
        tf_cep.setDisable(false);

    }
   
    
    @FXML
    void DesabilitarCampos(){
        tf_nome.setDisable(true);
        tf_email.setDisable(true);
        tf_site.setDisable(true);
        tf_fone.setDisable(true);
        tf_cep.setDisable(true);
        tfPesquisar.setDisable(true);

    }
    
    @FXML
    void BuscarTodos(){
        tbViewConsulta.getItems().clear();
        session = factory.openSession(); 
        Query query = session.createQuery("from ClientesVO"); 
        
        List listaCliente = query.list();      
        Iterator it = listaCliente.iterator();
      
           
        while(it.hasNext())
            {
                ClientesVO cli = (ClientesVO)it.next();
                dados.add(new TableModel(cli.getId(), cli.getNome(), cli.getEmail(), cli.getFone(), cli.getSite(),cli.getCep()));
            }
  
        tcolummCodigo.setCellValueFactory(new PropertyValueFactory("id"));
        tcolummNome.  setCellValueFactory(new PropertyValueFactory("nome"));
        tcolummEmail. setCellValueFactory(new PropertyValueFactory("email"));
        tcolummFone.  setCellValueFactory(new PropertyValueFactory("fone"));
        tcolummCep.   setCellValueFactory(new PropertyValueFactory("cep"));
        
        
        tbViewConsulta.setItems(dados);   
        tbViewConsulta.getSelectionModel().selectedItemProperty().addListener(
        (Observable, oldValue, newValue ) ->showClienteVODetalhe(newValue));
        
        lbQtd.setText(String.valueOf(dados.size()));
        
    }
   
    @FXML
    void BuscarClienteID(){
        session = factory.openSession(); 
        Query query = session.createQuery(Consulta); 
        
        List listaCliente = query.list();      
        Iterator it = listaCliente.iterator();
           
        while(it.hasNext())
            {
                ClientesVO cli = (ClientesVO)it.next();
              dados.add(new TableModel(cli.getId(), cli.getNome(), cli.getEmail(), cli.getFone(), cli.getSite(),cli.getCep()));
            }
  
        tcolummCodigo.setCellValueFactory(new PropertyValueFactory("id"));
        tcolummNome.  setCellValueFactory(new PropertyValueFactory("nome"));
        tcolummEmail. setCellValueFactory(new PropertyValueFactory("email"));
        tcolummFone.  setCellValueFactory(new PropertyValueFactory("fone"));
        tcolummCep.   setCellValueFactory(new PropertyValueFactory("cep"));
        
        tbViewConsulta.setItems(dados);   
        tbViewConsulta.getSelectionModel().selectedItemProperty().addListener(
        (Observable, oldValue, newValue ) ->showClienteVODetalhe(newValue));
        
        lbQtd.setText(String.valueOf(dados.size()));
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       DesabilitarCampos();
       BuscarTodos();
       tfPesquisar.setDisable(false);
       ComboBoxPesquisa.getItems().addAll("Nome","Email","Telefone");
    }   

    

}