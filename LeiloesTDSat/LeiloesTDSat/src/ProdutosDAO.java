/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.awt.List;
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;




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
    
    
    public void venderProduto(ProdutosDTO p) {
    String sql = "UPDATE produtos SET status = ? WHERE id = ?";
    
    try (Connection conn = new conectaDAO().connectDB();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, "Vendido"); 
        stmt.setInt(2, p.getId());
        
        int rowsAffected = stmt.executeUpdate();
        
        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum produto encontrado com o ID informado.");
        }
        
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + e.getMessage());
    }
}

    
 public void ListarProdutosVendidos() {
    
    String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";

    try (Connection conn = new conectaDAO().connectDB();  
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            ProdutosDTO produto = new ProdutosDTO();
            produto.setId(rs.getInt("id"));  
            produto.setNome(rs.getString("nome"));  
            produto.setValor(rs.getInt("valor"));  // ou rs.getDouble("valor")
            produto.setStatus(rs.getString("status"));  

           
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return;
}



}
