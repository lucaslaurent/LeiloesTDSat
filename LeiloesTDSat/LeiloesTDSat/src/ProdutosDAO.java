/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.Statement;



public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto(ProdutosDTO produto) {
        
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            
            conn = new conectaDAO().connectDB();
            
            
            String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

           
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());  
            stmt.setInt(2, produto.getValor());   
            stmt.setString(3, produto.getStatus()); 

            
            stmt.executeUpdate();
            System.out.println("Produto cadastrado com sucesso!");

        } catch (SQLException e) {
            
            System.err.println("Erro ao cadastrar produto: " + e.getMessage());
        } finally {
            
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        
        ArrayList<ProdutosDTO> listagem = new ArrayList<>();  
        String sql = "SELECT * FROM produtos";  

        
        try (Connection conn = new conectaDAO().connectDB();  
             Statement stmt = conn.createStatement();  
             ResultSet rs = stmt.executeQuery(sql)) { 
            
            
            while (rs.next()) {
                
                ProdutosDTO produto = new ProdutosDTO();
                
                
                produto.setId(rs.getInt("id"));  
                produto.setNome(rs.getString("nome"));  
                produto.setValor(rs.getInt("valor"));  
                produto.setStatus(rs.getString("status"));  
                
                
                listagem.add(produto);
            }
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
        
        return listagem;  
    }
    
    
    public void Venderproduto(ProdutosDTO p){
    try {
        Connection conn = new conectaDAO().connectDB();
        String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = " + p.getId();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
        JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");
        stmt.close();
        conn.close();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + e.getMessage());
    }
}
    
    public ArrayList<ProdutosDTO> listarProdutosVendidos(){
       ArrayList<ProdutosDTO> listaVendidos = new ArrayList<>();
    
    try {
        Connection conn = new conectaDAO().connectDB(); 
        String sql = "SELECT * FROM produtos WHERE status = 'Vendido'"; 
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            ProdutosDTO produto = new ProdutosDTO();
            produto.setId(rs.getInt("id"));          
            produto.setNome(rs.getString("nome")); 
            produto.setValor(rs.getInt("valor"));   
            produto.setStatus(rs.getString("status")); 
            
            listaVendidos.add(produto); 
        }
        
        stmt.close();
        conn.close();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao listar produtos vendidos: " + e.getMessage());
    }
    
    return listaVendidos;
    }
    
    
    }
        


